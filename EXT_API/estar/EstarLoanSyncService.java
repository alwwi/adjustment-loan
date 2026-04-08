package com.phincon.external.app.services.estar;

import com.google.common.collect.Lists;
import com.phincon.external.app.controller.estar.EstarLoanSyncController;
import com.phincon.external.app.dao.EmployeeRepository;
import com.phincon.external.app.dao.VwEmpAssignmentRepository;
import com.phincon.external.app.dao.loan.LoEmployeeLoanRepository;
import com.phincon.external.app.dao.loan.LoLoanRequestRepository;
import com.phincon.external.app.dto.loan.estar.*;
import com.phincon.external.app.model.hr.Employee;
import com.phincon.external.app.model.hr.VwEmpAssignment;
import com.phincon.external.app.model.loan.LoEmployeeLoan;
import com.phincon.external.app.model.loan.LoLoanRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EstarLoanSyncService {
    private static final Logger log = LogManager.getLogger(EstarLoanSyncService.class);
    @Autowired
    LoLoanRequestRepository loLoanRequestRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LoEmployeeLoanRepository repository;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @Autowired
    EstarApiService estarApiService;

    public final String CALL_BATCH_API_EACH = "batch";
    public final String CALL_ALL_AT_ONE = "all";

    public void doLoanSync(String identifier,LocalDate dateFrom, LocalDate dateTo,String callApiType,Integer batchSize) {
        log.info(identifier + "Begin doLoanSync");
        log.info(identifier + "Call Api Type : " + (callApiType == null ? "null" : callApiType));
        List<EstarSubmitLoanEmployeeDataDetailDTO> loanRequestNeedSync = loLoanRequestRepository.findNeedSync(dateFrom,dateTo);
        log.info("loanRequestNeedSync.size = " + loanRequestNeedSync.size());
        if(loanRequestNeedSync.size() == 0){
            log.info(identifier + "No Data To Sync");
            return;
        }

        try{
            EstarSubmitLoanEmployeeDataDTO requestBody = new EstarSubmitLoanEmployeeDataDTO();

            if(callApiType != null && callApiType.toString().equalsIgnoreCase(CALL_BATCH_API_EACH)){
                //if single, it will call batch need sync to api
                //for example if there are 4 need sync, it will trigger api 4 times for each data

                log.info(identifier + "Enter " + CALL_BATCH_API_EACH);
                if(batchSize == null){
                    batchSize = 10;
                }
                log.info(identifier + "Batch Size used : " + batchSize);

                Integer count = 0;
                String batchIdentifier = "";

                for(List<EstarSubmitLoanEmployeeDataDetailDTO> batchData : Lists.partition(loanRequestNeedSync, batchSize)){
                    count++;
                    batchIdentifier = "[" + count + "] ";

                    requestBody = new EstarSubmitLoanEmployeeDataDTO();
                    requestBody.setSubmitLoanEmployeeData(batchData);

                    try{
                        log.info(identifier + batchIdentifier + "Try to sync data :" + (requestBody == null ? "null" : requestBody.toString()));
                        log.info("requestBody : " + requestBody.toString());
                        //call the api
                        EstarResponseSubmitLoanEmployeeDataDTO responseEstar = estarApiService.submitLoanEmployeeData(
                                identifier,
                                requestBody
                        );

                        if(responseEstar == null || responseEstar.getSubmitLoanEmployeeResult() == null) {
                            log.info(identifier + batchIdentifier + "Failed to sync batch data : Estar dont give any response ");
                            break;
                        }

                        if(!responseEstar.getSubmitLoanEmployeeResult().getErrorCode().toString().equalsIgnoreCase("00") ||
                            !responseEstar.getSubmitLoanEmployeeResult().getMessageResponse().toString().equalsIgnoreCase("success")
                        ){
                            log.info(identifier + batchIdentifier + "Failed to sync batch data : " + responseEstar.getSubmitLoanEmployeeResult().getMessageResponse());
                            break;
                        }

                        for(EstarSubmitLoanEmployeeDataDetailDTO singleData : loanRequestNeedSync){
                            Optional<LoLoanRequest> loanRequest = loLoanRequestRepository.findByRequestNo(singleData.getNoRequest());
                            if(loanRequest.isPresent()){
                                loanRequest.get().setNeedSyncEstar(false);
                                loanRequest.get().setResultSyncEstar("Success");
                                loanRequest.get().setSyncDateEstar(new Date());
                                log.info("Update Need Sync Estar to False");
                            }
                            loLoanRequestRepository.save(loanRequest.get());
                        }

                        log.info(identifier + batchIdentifier + "Success sync batch data ");

                    }catch (Exception exSub){
                        log.info(identifier + batchIdentifier + "Failed to sync batch data with error : " + exSub.getMessage());
                    }

                }
            }else {
                log.info(identifier + "Enter " + CALL_ALL_AT_ONE);
                //if all, it will set all data to 1 dto, and only call 1 api with all the data

//                loanRequestNeedSync = new ArrayList<>();
//                EstarSubmitLoanEmployeeDataDetailDTO a = new EstarSubmitLoanEmployeeDataDetailDTO();
//                a.setNik("123456789012345");
//                a.setNip("00009999");
//                a.setNoRequest("1234567891112");
//                loanRequestNeedSync.add(a);
                log.info(loanRequestNeedSync);
//                requestBody.setSubmitLoanEmployeeData(loanRequestNeedSync);
                requestBody.setSubmitLoanEmployeeData(loanRequestNeedSync);
                log.info(requestBody.toString());

                EstarResponseSubmitLoanEmployeeDataDTO responseEstar = estarApiService.submitLoanEmployeeData(
                        identifier,
                        requestBody
                );
                log.info(responseEstar == null ? null : responseEstar.toString());

                if(responseEstar == null || responseEstar.getSubmitLoanEmployeeResult() == null) {
                    log.info(identifier  + "Failed to sync batch data : Estar dont give any response ");
                    throw new RuntimeException(identifier  + "Failed to sync batch data : Estar dont give any response ");
                }

                if(!responseEstar.getSubmitLoanEmployeeResult().getErrorCode().toString().equalsIgnoreCase("00") ||
                        !responseEstar.getSubmitLoanEmployeeResult().getMessageResponse().toString().equalsIgnoreCase("success")
                ){
                    log.info(identifier + "Failed to sync batch data : " + responseEstar.getSubmitLoanEmployeeResult().getMessageResponse());
                    throw new RuntimeException(identifier + "Failed to sync batch data : " + responseEstar.getSubmitLoanEmployeeResult().getMessageResponse());
                }

                for(EstarSubmitLoanEmployeeDataDetailDTO singleData : loanRequestNeedSync){
                    Optional<LoLoanRequest> loanRequest = loLoanRequestRepository.findByRequestNo(singleData.getNoRequest());
                    if(loanRequest.isPresent()){
                        loanRequest.get().setNeedSyncEstar(false);
                        loanRequest.get().setResultSyncEstar("Success");
                        loanRequest.get().setSyncDateEstar(new Date());
                        log.info("Update Need Sync Estar to False");
                    }
                    loLoanRequestRepository.save(loanRequest.get());
                }

                log.info(identifier + "Success sync data ");
            }
        }catch (Exception ex){
            log.info(identifier + "Failed doLoanSync with error : " + ex.getMessage());
        }
        log.info(identifier + "Finish doLoanSync");
    }

    public void doGetLoanSync(String identifier, String employmentId) {
        log.info(identifier + "Begin doGetLoanSync");
        log.info(identifier + "get all data to Upsert for Estar (isSimulation = false)");
        log.info(identifier + "get need To UpSert Estar");
        List<LoLoanRequest> findNeedUpdateInsertDataEstarList = loLoanRequestRepository.findNeedSyncForGetData(employmentId);
        log.info(identifier + "Need To UpSert Estar Size : " + findNeedUpdateInsertDataEstarList.size());

        //initialize variable
        LoEmployeeLoan dataUsed = new LoEmployeeLoan();
        String message=null;
        Integer countFailed = 0;
        Integer countNotFound = 0;
        Integer countSuccess = 0;
        Integer totalData = 0;

        countFailed = 0;
        countNotFound = 0;
        countSuccess = 0;
        totalData = findNeedUpdateInsertDataEstarList.size();
        log.info("totalData : " + totalData);
        for(LoLoanRequest singleRequest : findNeedUpdateInsertDataEstarList){
            try{
                log.info(identifier + "Get Data : " + singleRequest.getRequestNo());

                Optional<VwEmpAssignment> vwEmpAssignment = vwEmpAssignmentRepository.findByEmploymentId(singleRequest.getEmploymentId());
                if (!vwEmpAssignment.isPresent()) {
                    log.info(identifier + "[" + singleRequest.getRequestNo() + "] : " + "Employee Assignment Not Found !");
                    break;
                }

                Optional<Employee> employee = employeeRepository.findById(vwEmpAssignment.get().getEmployeeId());
                if (!employee.isPresent()) {
                    log.info(identifier + "[" + singleRequest.getRequestNo() + "] : " + "Employee Not Found !");
                    break;
                }

                EstarRequestLoanEmployeeDataDTO estarRequestLoanEmployeeDataDTO = new EstarRequestLoanEmployeeDataDTO();
                estarRequestLoanEmployeeDataDTO.setRequestNo(singleRequest.getRequestNo());
                estarRequestLoanEmployeeDataDTO.setNikEmployee(employee.get().getNircNo());

                EstarLoanEmployeeDataResultDTO responseEstar = estarApiService.requestLoanEmployeeData(
                        "[Sync Loan] ",
                        estarRequestLoanEmployeeDataDTO
                );

                log.info(identifier + "response from estar : " + responseEstar.toString());
                if(responseEstar.getLoanEmployeeDataResult().getLoanEmployeeData() != null &&
                        responseEstar.getLoanEmployeeDataResult().getLoanEmployeeData().size() > 0
                ){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//                    //find requestNo from estar that same as this request no from hceazy
//                    EstarLoanEmployeeDataDTO dataToInsert = null ;
//                    for(EstarLoanEmployeeDataDTO singleResponseEstar : responseEstar.getLoanEmployeeDataResult().getLoanEmployeeData()) {
//                        if(singleResponseEstar.getRequestNo().toString().equalsIgnoreCase(singleRequest.getRequestNo())) {
//                            dataToInsert = singleResponseEstar;
//                            break;
//                        }
//                    }

                    EstarLoanEmployeeDataDTO dataToInsert = responseEstar.getLoanEmployeeDataResult().getLoanEmployeeData().get(0) ;

                    if(dataToInsert == null){
                        log.info(identifier + " There are no matching request no from estar response");
                        break;
                    }

                    LoEmployeeLoan loEmployeeLoan ;
                    Optional<LoEmployeeLoan> loEmployeeLoanFound = repository.findByRequestNo(singleRequest.getRequestNo());
                    if(loEmployeeLoanFound.isPresent()){
                        //update data
                        loEmployeeLoan = loEmployeeLoanFound.get();

                        //tenor in loan request is max tenor
                        //tenor in lo employee loan is current tenor

                        if(
                                dataToInsert.getContractStatus().toString().equalsIgnoreCase("exp")
                        ){
                            //contract exp (expired) because its alread paid (information from estar)
                            loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_PAID_OFF);
                        }else {
                            //jika status sebelumnya sudah paid off (mungkin  karna sudah di lunasin di early settlement, tapi tidak terdata di estar),
                            // jangan ubah  ke inprogress lagi
                            if(!loEmployeeLoan.getStatus().equals(LoEmployeeLoan.STATUS_PAID_OFF)) {
                                loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);
                            }
                        }

                        //updated data is found
                        //if different, then this must be sync to sales force in future
                        //jika sudah paid off sebelumnya, tidak boleh disync lagi
                        if(!loEmployeeLoan.getStatus().equals(LoEmployeeLoan.STATUS_PAID_OFF) && (
                                !dataToInsert.getInsNo().equalsIgnoreCase(loEmployeeLoanFound.get().getTenor().toString())
                        )
                        ) {
                            singleRequest.setNeedSync(true);
                            loLoanRequestRepository.save(singleRequest);
                        }


                        //check if paid amount different from before
                        Double estarPaidAmount = (dataToInsert.getPaidAmount() == null ? Double.parseDouble("0") :  Double.parseDouble(dataToInsert.getPaidAmount()));
                        Double existingPaidAmount =  loEmployeeLoan.getEstarPaidAmount() == null ? 0 :  loEmployeeLoan.getEstarPaidAmount();
                        if(existingPaidAmount - estarPaidAmount != 0){
                            //update needsyncSalesForce because there are different value
                            singleRequest.setNeedSync(true);
                            log.info("Set Need Sync to True because there are different value");
                            loLoanRequestRepository.save(singleRequest);
                        }
                    }else {
                        //insert data
                        loEmployeeLoan = new LoEmployeeLoan();
                        loEmployeeLoan.setCreatedDate(new Date());
                        loEmployeeLoan.setCreatedBy("estar");
                        loEmployeeLoan.setGoLiveDate(LocalDateTime.now());
                        loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);

                        loEmployeeLoan.setRequestNo(singleRequest.getRequestNo());
                        loEmployeeLoan.setEmploymentId(singleRequest.getEmploymentId());
                        loEmployeeLoan.setRequestDate(singleRequest.getRequestDate());
                        loEmployeeLoan.setAmount(singleRequest.getAmount());
                        loEmployeeLoan.setLoanCategoryId(singleRequest.getLoanCategoryId());
                        loEmployeeLoan.setLoanTypeId(singleRequest.getLoanTypeId());

                        //new data from estar already created
                        //so, it need to sync to salesforce
                        singleRequest.setNeedSync(true);
                        loLoanRequestRepository.save(singleRequest);
                    }


                    loEmployeeLoan.setEstarAggrementNo(dataToInsert.getAgreementNo());
                    loEmployeeLoan.setEstarContractStatus(dataToInsert.getContractStatus());
                    switch (dataToInsert.getContractStatus()){
                        case "LIV":
                        case "ICP":
                        case "ICL":
                            loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);break;
                        case "RRD":
                        case "EXP":
                            loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_PAID_OFF);break;
                    }

                    loEmployeeLoan.setEstarInsNo(dataToInsert.getInsNo() == null ? null : dataToInsert.getInsNo());
                    loEmployeeLoan.setTenor(dataToInsert.getInsNo() == null ? null : Integer.parseInt(dataToInsert.getInsNo().toString()));

                    loEmployeeLoan.setEstarDownPayment(dataToInsert.getDownPayment() == null ? null : Double.parseDouble(dataToInsert.getDownPayment().toString()));
                    loEmployeeLoan.setDownPayment(dataToInsert.getDownPayment() == null ? null : Double.parseDouble(dataToInsert.getDownPayment().toString()));

                    loEmployeeLoan.setEstarInstallmentAmount(dataToInsert.getInstallmentAmount() == null ? null : Double.parseDouble(dataToInsert.getInstallmentAmount()));
                    loEmployeeLoan.setMonthlyInstallment(dataToInsert.getInstallmentAmount() == null ? null : Double.parseDouble(dataToInsert.getInstallmentAmount()));

                    loEmployeeLoan.setEstarLastPaidDate(dataToInsert.getLastPaidDate() == null ? null : LocalDate.parse(dataToInsert.getLastPaidDate().toString(),formatter).atStartOfDay());
                    loEmployeeLoan.setLastPaidDate(dataToInsert.getLastPaidDate() == null ? null : LocalDate.parse(dataToInsert.getLastPaidDate().toString(),formatter).atStartOfDay());

                    loEmployeeLoan.setEstarName(dataToInsert.getName() == null ? null : dataToInsert.getName());
                    loEmployeeLoan.setEstarOtr(dataToInsert.getOtr() == null ? null : Double.parseDouble(dataToInsert.getOtr().toString()));

                    loEmployeeLoan.setEstarOutstandingAr(dataToInsert.getOutstandingAR() == null ? null : Double.parseDouble(dataToInsert.getOutstandingAR().toString()));
                    loEmployeeLoan.setRemaining(dataToInsert.getOutstandingAR() == null ? null : Double.parseDouble(dataToInsert.getOutstandingAR().toString()));

                    loEmployeeLoan.setEstarOutstandingInsurance(dataToInsert.getOutstandingInsurance() == null ? null : Double.parseDouble(dataToInsert.getOutstandingInsurance().toString()));
                    loEmployeeLoan.setLeftInssurance(dataToInsert.getOutstandingInsurance() == null ? null : Double.parseDouble(dataToInsert.getOutstandingInsurance().toString()));

                    loEmployeeLoan.setEstarPaidAmount(dataToInsert.getPaidAmount() == null ? null : Double.parseDouble(dataToInsert.getPaidAmount().toString()));
                    loEmployeeLoan.setPaidAmount(dataToInsert.getPaidAmount() == null ? null : Double.parseDouble(dataToInsert.getPaidAmount().toString()));

                    loEmployeeLoan.setEstarPrepaidAmount(dataToInsert.getPrepaidAmount() == null ? null : Double.parseDouble(dataToInsert.getPrepaidAmount().toString()));
                    loEmployeeLoan.setEstarTenor(dataToInsert.getTenor() == null ? null : Integer.parseInt(dataToInsert.getTenor().toString()));


                    loEmployeeLoan.setModifiedDate(new Date());
                    loEmployeeLoan.setModifiedBy("estar");
                    repository.save(loEmployeeLoan);
                    countSuccess++;
                }else {
                    countNotFound++;
                    log.info(identifier + "Data Not Found In Estar : " + singleRequest.getRequestNo());
                }

            }catch (Exception ex){
                countFailed++;
                log.info(identifier + "Error Get Data From Estar : " + ex.getMessage());

            }
        }

        message = "Total Data Non Simulation = " + totalData.toString() + " (Success : " + countSuccess + " | Failed : " + countFailed + " | Not Found : " + countNotFound + ")";
        //------------------------------------------------------------------
        log.info(identifier + message);
        log.info(identifier + "Finish doLoanGetSync");
    }

}
