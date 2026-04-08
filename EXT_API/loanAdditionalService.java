package com.phincon.external.app.services;

import com.phincon.external.app.controller.talents.SyncTransactionLoanController;
import com.phincon.external.app.dao.loan.LoLoanRequestDetailSalesForceRepository;
import com.phincon.external.app.dto.*;
import com.phincon.external.app.model.loan.LoLoanRequest;
import com.phincon.external.app.model.loan.LoLoanRequestDtl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class loanAdditionalService extends ParentLoanSyncServices<LoanRequestSyncDTO, LoLoanRequestDtl> {
    Logger log = LoggerFactory.getLogger(loanAdditionalService.class);
    @Autowired
    LoLoanRequestDetailSalesForceRepository requestRepository;

    private  List<LoanRequestSyncDTO>  addListMapToDTO(List<Map<String,Object>> data) {
        List<LoanRequestSyncDTO>  response = new ArrayList<>();

        if(data != null) {
            for (Map<String, Object> singleNeedSyncByDateRaw : data) {
                LoanRequestSyncDTO singleDataToSync = new LoanRequestSyncDTO(
                        singleNeedSyncByDateRaw != null &&  singleNeedSyncByDateRaw.containsKey("extIdLists") ? (singleNeedSyncByDateRaw.get("extIdLists") == null ? null : singleNeedSyncByDateRaw.get("extIdLists").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("loanRequestIdLists") ? (singleNeedSyncByDateRaw.get("loanRequestIdLists") == null ? null : singleNeedSyncByDateRaw.get("loanRequestIdLists").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("loanRequestNoLists") ? (singleNeedSyncByDateRaw.get("loanRequestNoLists") == null ? null : singleNeedSyncByDateRaw.get("loanRequestNoLists").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("employeeNo") ? (singleNeedSyncByDateRaw.get("employeeNo") == null ? null : singleNeedSyncByDateRaw.get("employeeNo").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("totalMonthlyInstallment") ? (singleNeedSyncByDateRaw.get("totalMonthlyInstallment") == null ? null : singleNeedSyncByDateRaw.get("totalMonthlyInstallment").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("paymentDate") ? (singleNeedSyncByDateRaw.get("paymentDate") == null ? null : LocalDate.parse(singleNeedSyncByDateRaw.get("paymentDate").toString())) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("requestDate") ? (singleNeedSyncByDateRaw.get("requestDate") == null ? null : LocalDate.parse(singleNeedSyncByDateRaw.get("requestDate").toString())) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("loanCategoryLists") ? (singleNeedSyncByDateRaw.get("loanCategoryLists") == null ? null : singleNeedSyncByDateRaw.get("loanCategoryLists").toString()) : null
                        , singleNeedSyncByDateRaw != null && singleNeedSyncByDateRaw.containsKey("elementName") ? (singleNeedSyncByDateRaw.get("elementName") == null ? null : singleNeedSyncByDateRaw.get("elementName").toString()) : null
                );
                response.add(singleDataToSync);
            }
        }
        return response;
    }

    @Override
    void doSync() {
        this.setUrl("PYEMPELEMENT__c");
        LocalDate dateFrom = null;
        LocalDate dateTo = null;
        LocalDate paymentDate = null;
        LocalDate requestDate = null;
        if(this.additionalInformation != null){
            dateFrom = LocalDate.parse(additionalInformation.getOrDefault("dateFrom", null).toString());
            dateTo = LocalDate.parse(additionalInformation.getOrDefault("dateTo", null).toString());
            paymentDate = LocalDate.parse(additionalInformation.getOrDefault("paymentDate", null).toString());
            paymentDate = paymentDate.withDayOfMonth(25); //always set to day 25
            requestDate = LocalDate.parse(additionalInformation.getOrDefault("requestDate", null).toString());
        }
        RequestSyncWrapDTO requestSyncWrapDTO = new RequestSyncWrapDTO();



        // logic

        //-----------------------------------------------------------
        // Additional
        //-----------------------------------------------------------
        List<Map<String,Object>> additionalNeedSyncLoanSimulationTrueTenor1 = requestRepository.findAdditionalNeedSyncLoanByDate(paymentDate,requestDate);
        List<LoanRequestSyncDTO> additionalNeedSyncLoanSimulationTrueTenor1DTO = addListMapToDTO(additionalNeedSyncLoanSimulationTrueTenor1);
        log.info("Additional Need Sync : " + additionalNeedSyncLoanSimulationTrueTenor1.size());
        //-----------------------------------------------------------

        List<LoanRequestSyncDTO> needSyncUsed = new ArrayList<>();
        needSyncUsed.addAll(additionalNeedSyncLoanSimulationTrueTenor1DTO);


        requestSyncWrapDTO.setItems(needSyncUsed);
        log.info("size (" + requestSyncWrapDTO.getItems().size()  + ") : " + requestSyncWrapDTO.toString());
        this.requestSyncWrapDTO = requestSyncWrapDTO;
        this.repository = requestRepository;
    }

    @Override
    LoLoanRequestDtl getNewInstance(String requestId) {
        Optional<LoLoanRequestDtl> optReq = requestRepository.findByIdBackup(Long.parseLong(requestId));
        if (optReq.isPresent())
            return optReq.get();
        return new LoLoanRequestDtl();
    }

    public void processResultSalesForce(WrapSalesForceDTOUUID responseFromSalesForce){
        log.info("===============================================");
        log.info("[Loan Additional] Begin Process Result SalesForce ");
        log.info("===============================================");

        //check if there are any additional Information
        log.info("Loop All Response From SalesForce : " + (responseFromSalesForce == null || responseFromSalesForce.getResults() == null ? "null" : responseFromSalesForce.getResults().size()) + " Records " );

        String tabAddtional = "   ";

        if(responseFromSalesForce != null && responseFromSalesForce.getResults() != null) {
            for (ResultSyncDTOUUID singleData : responseFromSalesForce.getResults()) {
                if (singleData.getAdditionalInformation() == null) {
                    log.info(tabAddtional + "Additional Information Not Found In This Reponse. Skip This (Sales Force Id : " + singleData.getId() + ")");
                    break;
                }

                log.info(tabAddtional + "Additional Information Found In This Reponse. (Sales Force Id : " + singleData.getId() + ")");
                Map<String, Object> additionalInformationSalesForce = singleData.getAdditionalInformation();

                log.info(tabAddtional + "Is This Additional Information Contain Any Request No : " + additionalInformationSalesForce.containsKey("requestNoLists"));
                if (!additionalInformationSalesForce.containsKey("requestNoLists")) {
                    break;
                }

                if(additionalInformationSalesForce.get("requestNoLists") == null) {
                    break;
                }

                String delimiter = ",";
                log.info(tabAddtional + "Split Request No List Based On delimiter (" + delimiter + ") ");

                String[] requestNoLists;
                requestNoLists = additionalInformationSalesForce.get("requestNoLists").toString().split(delimiter);

                log.info(tabAddtional + "Loop All Request From Splitting : " + requestNoLists == null ? "0 Request " : requestNoLists.length + " Request ");
                for (String singleRequest : requestNoLists) {
                    log.info(tabAddtional + "Process Request : " + singleRequest);

                    Optional<LoLoanRequestDtl> dataFound = requestRepository.findById(singleRequest);
                    if (dataFound.isPresent()) {
                        log.info(tabAddtional + "Data Found In Database ");
                        dataFound.get().setSyncDate(new Date());
                        dataFound.get().setResultSync(additionalInformationSalesForce.containsKey("status") ? additionalInformationSalesForce.get("status").toString() : dataFound.get().getResultSync());
                        dataFound.get().setExtId(singleData.getId() == null ? dataFound.get().getExtId() : singleData.getId());

                        if (dataFound.get().getResultSync().equalsIgnoreCase("success")) {
                            dataFound.get().setNeedSync(false);
                        }
                        requestRepository.save(dataFound.get());
                    } else {
                        log.info(tabAddtional + "Request No (Loan Additional) Skipped : " + singleRequest + " | SalesForce Id : " + singleData.getId());
                    }

                }
            }
        }

        log.info("[Loan Additional] Finish Process Result SalesForce");
        log.info("===============================================");

    }
}
