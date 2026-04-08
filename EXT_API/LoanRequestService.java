package com.phincon.external.app.services;

import com.phincon.external.app.controller.talents.SyncTransactionLoanController;
import com.phincon.external.app.dao.loan.LoEmployeeLoanRepository;
import com.phincon.external.app.dao.loan.LoLoanRequestRepository;
import com.phincon.external.app.dao.loan.LoLoanRequestSalesForceRepository;

import com.phincon.external.app.dto.*;
import com.phincon.external.app.model.loan.LoEmployeeLoan;
import com.phincon.external.app.model.loan.LoLoanRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class LoanRequestService extends ParentLoanSyncServices<LoanRequestSyncDTO, LoLoanRequest> {
    Logger log = LoggerFactory.getLogger(LoanRequestService.class);
    @Autowired
    LoLoanRequestSalesForceRepository requestRepository;

    @Autowired
    LoLoanRequestRepository loLoanRequestRepository;

    @Autowired
    LoEmployeeLoanRepository loEmployeeLoanRepository;

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

//        //-----------------------------------------------------------
//        // dummy
//        //-----------------------------------------------------------
//        List<Map<String,Object>> dummyData = requestRepository.dummydata(dateFrom,dateTo,paymentDate,requestDate);
//        List<LoanRequestSyncDTO> dummyDataDTO = addListMapToDTO(dummyData);
//        log.info("Dummy = 1 : " + dummyDataDTO.size());
//        //-----------------------------------------------------------

        //-----------------------------------------------------------
        // Tenor = 1
        //-----------------------------------------------------------
        List<Map<String,Object>> needSyncLoanSimulationTrueTenor1 = requestRepository.findNeedSyncLoanTenor1(dateFrom,dateTo,requestDate);
        List<LoanRequestSyncDTO> needSyncLoanSimulationTrueTenor1DTO = addListMapToDTO(needSyncLoanSimulationTrueTenor1);
        log.info("Tenor = 1 : " + needSyncLoanSimulationTrueTenor1.size());
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        // Tenor > 1
        //-----------------------------------------------------------
        List<Map<String,Object>> needSyncLoanSimulationTrueTenorMoreThan1 = requestRepository.findNeedSyncLoanTenorMoreThan1(requestDate);
        List<LoanRequestSyncDTO> needSyncLoanSimulationTrueTenorMoreThan1DTO = addListMapToDTO(needSyncLoanSimulationTrueTenorMoreThan1);
        log.info("Tenor > 1 : " + needSyncLoanSimulationTrueTenorMoreThan1.size());
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        // MOP Tenor = 1
        //-----------------------------------------------------------
        List<Map<String,Object>> needSyncLoanSimulationTrueTenor1MOP = requestRepository.findNeedSyncLoanTenor1MOP(dateFrom,dateTo,requestDate);
        System.out.println(needSyncLoanSimulationTrueTenor1MOP.toString());
        List<LoanRequestSyncDTO> needSyncLoanSimulationTrueTenor1MOPDTO = addListMapToDTO(needSyncLoanSimulationTrueTenor1MOP);
        log.info("MOP Tenor = 1 : " + needSyncLoanSimulationTrueTenor1MOP.size());
        //-----------------------------------------------------------

        //-----------------------------------------------------------
        //MOP Tenor > 1
        //-----------------------------------------------------------
        List<Map<String,Object>> needSyncLoanSimulationTrueTenorMoreThan1MOP = requestRepository.findNeedSyncLoanTenorMoreThan1MOP(requestDate);
        System.out.println(needSyncLoanSimulationTrueTenorMoreThan1MOP.toString());
        List<LoanRequestSyncDTO> needSyncLoanSimulationTrueTenorMoreThan1MOPDTO = addListMapToDTO(needSyncLoanSimulationTrueTenorMoreThan1MOP);
        log.info("MOP Tenor > 1 : " + needSyncLoanSimulationTrueTenorMoreThan1MOP.size());
        //-----------------------------------------------------------
//
        List<LoanRequestSyncDTO> needSyncUsed = new ArrayList<>();
        needSyncUsed.addAll(needSyncLoanSimulationTrueTenor1DTO);
        needSyncUsed.addAll(needSyncLoanSimulationTrueTenorMoreThan1DTO);
        needSyncUsed.addAll(needSyncLoanSimulationTrueTenor1MOPDTO);
        needSyncUsed.addAll(needSyncLoanSimulationTrueTenorMoreThan1MOPDTO);
//        needSyncUsed.addAll(dummyDataDTO);

        requestSyncWrapDTO.setItems(needSyncUsed);
        log.info("size (" + requestSyncWrapDTO.getItems().size()  + ") : " + requestSyncWrapDTO.toString());

        this.requestSyncWrapDTO = requestSyncWrapDTO;
        this.repository = requestRepository;
    }

    @Override
    LoLoanRequest getNewInstance(String requestId) {
        System.out.println("requestId : " + requestId);
        Optional<LoLoanRequest> optReq = requestRepository.findByIdBackup(Long.parseLong(requestId));
        if (optReq.isPresent())
            return optReq.get();
        return new LoLoanRequest();
    }

    public void processResultSalesForce(WrapSalesForceDTOUUID responseFromSalesForce){
        log.info("===============================================");
        log.info("[Loan Monthly] Begin Process Result SalesForce ");
        log.info("===============================================");
        //check if there are any additional Information
        log.info("Loop All Response From SalesForce : " + (responseFromSalesForce == null || responseFromSalesForce.getResults() == null ? "null" : responseFromSalesForce.getResults().size()) + " Records " );

        if(responseFromSalesForce != null && responseFromSalesForce.getResults() != null) {
            for (ResultSyncDTOUUID singleData : responseFromSalesForce.getResults()) {
                if (singleData.getAdditionalInformation() == null) {
                    log.info("Additional Information Not Found In This Reponse. Skip This (Sales Force Id : " + singleData.getId() + ")");
                    break;
                }

                log.info("Additional Information Found In This Reponse. (Sales Force Id : " + singleData.getId() + ")");
                Map<String, Object> additionalInformationSalesForce = singleData.getAdditionalInformation();
                log.info(additionalInformationSalesForce.toString());

                log.info("Is This Additional Information Contain Any Request No : " + additionalInformationSalesForce.containsKey("requestNoLists"));
                if (!additionalInformationSalesForce.containsKey("requestNoLists")) {
                    break;
                }

                String delimiter = ",";
                log.info("Split Request No List Based On delimiter (" + delimiter + ") ");
                if(additionalInformationSalesForce.get("requestNoLists") == null) {
                    break;
                }

                String[] requestNoLists;
                requestNoLists = additionalInformationSalesForce.get("requestNoLists").toString().split(delimiter);

                log.info("Loop All Request From Splitting : " + requestNoLists == null ? "0 Request " : requestNoLists.length + " Request ");
                for (String singleRequest : requestNoLists) {
                    log.info("Process Request : " + singleRequest);

                    Optional<LoLoanRequest> loanRequestFound = loLoanRequestRepository.findByRequestNo(singleRequest);
                    if (loanRequestFound.isPresent()) {
                        log.info("Request Found In Database ");
                        loanRequestFound.get().setSyncDate(new Date());
                        loanRequestFound.get().setResultSync(additionalInformationSalesForce.containsKey("status") ? additionalInformationSalesForce.get("status").toString() : loanRequestFound.get().getResultSync());
                        loanRequestFound.get().setExtId(singleData.getId() == null ? loanRequestFound.get().getExtId() : singleData.getId().toString());

                        String status = (additionalInformationSalesForce.get("status") == null ? "null" : additionalInformationSalesForce.get("status").toString());
                        log.info("status : " + status);
                        if (status == "success") {
                            log.info("Masuk setNeedSync False");
                            loanRequestFound.get().setNeedSync(Boolean.FALSE);
                        }
                        loLoanRequestRepository.save(loanRequestFound.get());
                        log.info("Save Finished");
                    } else {
                        log.info("Request No (Loan Request) Skipped : " + singleRequest + " | SalesForce Id : " + singleData.getId());
                    }

//                    Optional<LoEmployeeLoan> loEmployeeLoanFound = loEmployeeLoanRepository.findByRequestNo(singleRequest);
//                    if (loEmployeeLoanFound.isPresent()) {
//                        log.info("Employee Loan Found In Database ");
//                        //its hceazy loan (not from estar)
//                        Integer previousTenor = loEmployeeLoanFound.get().getTenor() == null ? 0 : loEmployeeLoanFound.get().getTenor();
//                        Double previousPaidAmount = loEmployeeLoanFound.get().getPaidAmount() == null ? 0 : loEmployeeLoanFound.get().getPaidAmount();
//
//                        if (loEmployeeLoanFound.get().getEstarAggrementNo() == null) {
//                            log.info("This Employee Loan From HCEazy");
//                            if (previousTenor == 1) {
//                                //its first tenor
//                                LocalDateTime currentDate = LocalDateTime.now();
//                                LocalDateTime desiredDate = currentDate.withDayOfMonth(25);
//
//                                loEmployeeLoanFound.get().setMaturityDate(desiredDate);
//                            }
//                            log.info("Update Tenor, Maturity Date");
//                            loEmployeeLoanFound.get().setTenor(previousTenor + 1);
//
//                            Double paidAmount = previousPaidAmount + (loEmployeeLoanFound.get().getMonthlyInstallment() == null ? 0 : loEmployeeLoanFound.get().getMonthlyInstallment());
//                            paidAmount = (double) Math.round(paidAmount * 100) / 100;
//
//                            loEmployeeLoanFound.get().setPaidAmount(paidAmount);
//                            loEmployeeLoanFound.get().setLastPaidDate(LocalDateTime.now());
//                        } else {
//                            log.info("This Employee Loan From Estar");
//                            //its estar loan
//                            //tenor based on only estar api
//                            if (previousTenor == 1) {
//                                //its first tenor
//                                LocalDateTime currentDate = LocalDateTime.now();
//                                LocalDateTime desiredDate = currentDate.withDayOfMonth(25);
//
//                                loEmployeeLoanFound.get().setMaturityDate(desiredDate);
//                            }
//
//                            log.info("Update Maturity Date Only");
//                            log.info("Skip Update Tenor, Because it must be consume from estar");
//                        }
//
//                        loEmployeeLoanRepository.save(loEmployeeLoanFound.get());
//                    } else {
//                        log.info("Request No (Employee Loan) Skipped : " + singleRequest + " | SalesForce Id : " + singleData.getId());
//                    }
                }




            }
        }

        log.info("Finish Process Result SalesForce");
        log.info("===============================================");
    }
}
