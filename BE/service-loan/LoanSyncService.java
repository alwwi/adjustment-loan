package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.integration.salesforce.ListResultSyncDTO;
import com.phincon.talents.app.dto.integration.salesforce.ResultSyncDTO;
import com.phincon.talents.app.dto.integration.salesforce.SalesForceLoanSyncDTO;
import com.phincon.talents.app.dto.loan.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.phincon.talents.app.model.loan.LoSyncLoanRequest;
import com.phincon.talents.app.repository.LoSyncLoanRequestRepository;
import com.phincon.talents.app.services.TalentsTransactionNoService;
import com.phincon.talents.app.services.integration.SalesForceApiService;
import com.phincon.talents.app.utils.GlobalValue;
import com.phincon.talents.app.utils.Utils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@Service
public class LoanSyncService {

    private static final Logger log = LogManager.getLogger(LoanSyncService.class);

    @Autowired
    LoSyncLoanRequestRepository repository;

    @Autowired
    TalentsTransactionNoService talentsTransactionNoService;

    @Autowired
    SalesForceApiService salesForceApiService;

    @Autowired
    private Environment env;

    @Value("${mtf.sc.jwt.secret}")
    private String secret;

    public static String saveListMapToTxt(List<Map<String, Object>> dataList, String root) {
        String path = GlobalValue.PATH_UPLOAD;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String fileName = root + "/" + format + RandomStringUtils.randomAlphanumeric(10) + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, fileName)))) {
            for (Map<String, Object> dataMap : dataList) {
                for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue().toString() + "\n");
                }
                writer.write("\n"); // Add a newline between maps
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception according to your application's requirements
        }

        return fileName;
    }

    @Transactional
    public ResultSyncDTO InsertData(String identifier, LoanSyncRequestDTO request,
                                    String employmentId) {

        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoSyncLoanRequest dataUsed = new LoSyncLoanRequest();
        ResultSyncDTO response = new ResultSyncDTO();
        response.setMessage(null);

        String requestNo = talentsTransactionNoService.getTransactionNextVal("Sync Loan","Sync Loan");

        dataUsed.setRequestDate(LocalDateTime.now());
        dataUsed.setRequestNo(requestNo);
        dataUsed.setRequestorEmploymentId(employmentId);
        dataUsed.setStatus(LoSyncLoanRequest.STATUS_IN_PROGRESS);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime paymentStartDate = LocalDate.parse(request.getPaymentStartDate(),formatter).atStartOfDay();
        dataUsed.setStartDate(paymentStartDate);

        LocalDateTime paymentEndDate = LocalDate.parse(request.getPaymentEndDate(),formatter).atStartOfDay();
        dataUsed.setEndDate(paymentEndDate);

        String inputString = request.getPaymentDate();
        int dayOfMonth = 25;
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime paymentDateUsed = LocalDateTime.parse(inputString + "-" + dayOfMonth + " 00:00:00", inputFormatter);
            dataUsed.setPaymentDate(paymentDateUsed);
        }catch (DateTimeParseException dtpExc){
            throw  new CustomGenericException("Invalid Format Payment Date, Please use yyyy-MM");
        }
        dataUsed.setCreatedBy("SYSTEM");
        dataUsed.setCreatedDate(new Date());
        repository.save(dataUsed);
        response.setMessage(dataUsed.getRequestNo());

        //-------------------------------------------
        //Call MTF-External to sync
        //-------------------------------------------
        DateTimeFormatter salesForceDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        SalesForceLoanSyncDTO queryparams = new SalesForceLoanSyncDTO();
        queryparams.setDateFrom(dataUsed.getStartDate().format(salesForceDateFormat).toString());
        queryparams.setDateTo(dataUsed.getEndDate().format(salesForceDateFormat).toString());
        queryparams.setPaymentDate(dataUsed.getPaymentDate().format(salesForceDateFormat).toString());
        queryparams.setRequestDate(dataUsed.getRequestDate().format(salesForceDateFormat).toString());

        try {
            SyncSalesForceResultDTO responseSF = salesForceApiService.doSyncLoanSalesForce(identifier + " ", queryparams);
            Integer countSuccess = 0;
            Integer countFailed = 0;

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                System.out.println("responseSF : ");
                System.out.println(responseSF);
                if(responseSF == null || responseSF.getStatus().equals("400") || !responseSF.getSuccess()){
//                    Map<String,Object> noDataFile = new HashMap<>();
//                    noDataFile.put("message","No Data To Sync");
//                    List<Map<String,Object>> noDataFileList = new ArrayList<>();
//                    noDataFileList.add(noDataFile);
//                    String fileName = saveListMapToTxt(noDataFileList,"SyncLoan");
//
//                    dataUsed.setLogFile(fileName);
//                    repository.save(dataUsed);
                    throw new CustomGenericException("No Data To Sync");
                }
                List<Map<String, Object>> resultList = objectMapper.readValue(responseSF.getMessage(), new TypeReference<List<Map<String, Object>>>() {});
                if(resultList.size() == 0){
                    throw new CustomGenericException("No Data To Sync !");
                }
                List<ListResultSyncDTO> listResponse = new ArrayList<>();

                List<Map<String,Object>> logFiles = new ArrayList<>();
                for(Map<String,Object> singleResult : resultList){
                    Map<String,Object> additionalInformation = (Map<String, Object>) singleResult.get("additionalInformation");
                    ListResultSyncDTO singleList = new ListResultSyncDTO();
                    singleList.setStatus(additionalInformation.get("status") == null ? null : additionalInformation.get("status").toString());
                    singleList.setEmployeeNo(additionalInformation.get("employeeHcEazy") == null ? null : additionalInformation.get("employeeHcEazy").toString());
                    singleList.setEmployeeSalesForce(additionalInformation.get("employeeSalesForce") == null ? null : additionalInformation.get("employeeSalesForce").toString());
                    singleList.setElementName(additionalInformation.get("elementName") == null ? null : additionalInformation.get("elementName").toString());
                    if(singleList.getStatus().equalsIgnoreCase("success")){
                        countSuccess++;
                    }else {
                        countFailed++;
                    }
                    listResponse.add(singleList);

                    Map<String,Object> logFile = new HashMap<>();
                    logFile.put("employeeNo",additionalInformation.get("employeeHcEazy") == null ? null : additionalInformation.get("employeeHcEazy").toString());
                    logFile.put("employeeSalesForce",additionalInformation.get("employeeSalesForce") == null ? null : additionalInformation.get("employeeSalesForce").toString());
                    logFile.put("elementName",additionalInformation.get("elementName") == null ? null : additionalInformation.get("elementName").toString());
                    logFile.put("status",additionalInformation.get("status") == null ? null : additionalInformation.get("status").toString());
                    logFiles.add(logFile);

                }
                String fileName = saveListMapToTxt(logFiles,"SyncLoan");
                dataUsed.setLogFile(fileName);
                repository.save(dataUsed);

                response.setListResults(listResponse);

            } catch (IOException e) {
                log.info("Submit Error With message : " + e.getMessage());
                e.printStackTrace();
                throw new CustomGenericException(e.getMessage());

            }

            if(responseSF.getSuccess() == null || responseSF.getSuccess() == false){
                dataUsed.setStatus(LoSyncLoanRequest.STATUS_FAILED);
            }else {
                dataUsed.setStatus(LoSyncLoanRequest.STATUS_COMPLETED);
            }
            dataUsed.setRemark(countSuccess + " Success | " + countFailed + " Failed");

            repository.save(dataUsed);
        }catch (Exception ex){
            dataUsed.setStatus(LoSyncLoanRequest.STATUS_FAILED);
            repository.save(dataUsed);
            log.info("Submit Error With message : " + ex.getMessage());
            ex.printStackTrace();
            throw new CustomGenericException(ex.getMessage());
        }
        //-------------------------------------------
        return response;
    }

    @Transactional
    public Page<LoanSyncListDTO> listData(
            String page, String size,
            LocalDate startDateUsed, LocalDate endDateUsed,
            String status, HttpServletRequest request, Jwt jwt
    ) {
        Page<LoanSyncListDTO> response;
        String http = env.getProperty("talents.protocol");
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(status,startDateUsed,endDateUsed, Utils.getServerName(http, request, jwt, secret),pageable,secret);

        return response;
    }

    @Transactional
    public LoanSyncListDTO getDetail(
            String id,
            HttpServletRequest request, Jwt jwt
    ) {
        LoanSyncListDTO response;
        String http = env.getProperty("talents.protocol");
        response = repository.getDetail(id,Utils.getServerName(http, request, jwt, secret),secret);

        return response;
    }

}
