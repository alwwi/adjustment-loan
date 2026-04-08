package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.EmployeeRepository;
import com.phincon.talents.app.dao.VwEmpAssignmentRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.integration.estar.*;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.Employee;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoCriteria;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.repository.*;
import com.phincon.talents.app.services.integration.EstarApiService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import com.phincon.talents.app.utils.RepositoryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanEmployeeService {

    private static final Logger log = LogManager.getLogger(LoanEmployeeService.class);

    @Autowired
    LoEmployeeLoanRepository repository;

    @Autowired
    LoLoanRequestRepository loLoanRequestRepository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EstarApiService estarApiService;

    @Transactional
    public Page<LoanMyLoanListDTO> listData(
            String page, String size,
            String requestNo, LocalDate startDateUsed, LocalDate endDateUsed
            ,String loanTypeId,String loanCategoryId,
            String employmentId, String status
    ) {
        Page<LoanMyLoanListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String loanTypeIdUsed = null;
        if(loanTypeId != null){
            loanTypeIdUsed = (loanTypeId);
        }

        String loanCategoryIdUsed = null;
        if(loanCategoryId != null){
            loanCategoryIdUsed = (loanCategoryId);
        }

        response = repository.listData(employmentId,requestNo,startDateUsed,endDateUsed,loanTypeIdUsed,loanCategoryIdUsed,status,pageable);

        return response;
    }

    @Transactional
    public LoanMyLoanDetailDTO getDetail(String identifier, String id, String employmentId) {
        LoanMyLoanDetailDTO response;

        response = repository.getData(id,employmentId).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

//    @Transactional
    public ResponseEntity<CustomMessageWithId> syncData(String identifier, String employeeId) {
        log.info(identifier + "Sync Data My Loan : " + (employeeId == null ? "all employee" : employeeId.toString()) );
        log.warn(identifier + "Initialization Process Sync");

        //initialize variable
        LoEmployeeLoan dataUsed = new LoEmployeeLoan();
        String message=null;
        Integer countFailed = 0;
        Integer countNotFound = 0;
        Integer countSuccess = 0;
        Integer totalData = 0;

        //logic
        VwEmpAssignment employeeDataFound =    vwEmpAssignmentRepository.findByEmployeeId(employeeId).orElseThrow(
                ()-> new CustomGenericException( identifier + "Employee Doesn't Exist")
        );

        //------------------------------------------------------------------
        //get all data to Upsert for estar (isSimulation = false)
        //------------------------------------------------------------------
        log.info(identifier + "get all data to Upsert for Estar (isSimulation = false)");
        log.info(identifier + "get need To UpSert Estar");
        List<LoLoanRequest> findNeedUpdateInsertDataEstarList = repository.findNeedUpdateInsertDataEstar(employeeDataFound.getEmploymentId());
        log.info(identifier + "Need To UpSert Estar Size : " + findNeedUpdateInsertDataEstarList.size());

        countFailed = 0;
        countNotFound = 0;
        countSuccess = 0;
        totalData = findNeedUpdateInsertDataEstarList.size();
        for(LoLoanRequest singleRequest : findNeedUpdateInsertDataEstarList){
            try{
                log.info(identifier + "Get Data : " + singleRequest.getRequestNo());

                Optional<VwEmpAssignment> vwEmpAssignment = vwEmpAssignmentRepository.findByEmploymentId(singleRequest.getEmploymentId());
                if (!vwEmpAssignment.isPresent()) {
                    log.info(identifier + "[" + singleRequest.getRequestNo() + "] : " + "Employee Assignment Not Found !");
                    throw new CustomGenericException("Employee Assignment Not Found !");
                }

                Optional<Employee> employee = employeeRepository.findById(vwEmpAssignment.get().getEmployeeId());
                if (!employee.isPresent()) {
                    log.info(identifier + "[" + singleRequest.getRequestNo() + "] : " + "Employee Not Found !");
                    throw new CustomGenericException("Employee Not Found !");
                }

//                EstarApiService estarApiService = new EstarApiService();
                EstarRequestLoanEmployeeDataDTO estarRequestLoanEmployeeDataDTO = new EstarRequestLoanEmployeeDataDTO();
                estarRequestLoanEmployeeDataDTO.setRequestNo(singleRequest.getRequestNo());
                estarRequestLoanEmployeeDataDTO.setNikEmployee(employee.get().getNircNo());

//                estarRequestLoanEmployeeDataDTO.setRequestNo("123456786");
//                estarRequestLoanEmployeeDataDTO.setNikEmployee("6472042303880001");

                EstarLoanEmployeeDataResultDTO responseEstar = estarApiService.requestLoanEmployeeData(
                        "[Sync Loan] ",
                        estarRequestLoanEmployeeDataDTO
                );

                log.info(identifier + " response from estar : " + responseEstar.toString());
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
                        
                        /*
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
                        */

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
                    /* 
                    switch (dataToInsert.getContractStatus()){
                        case "LIV":
                        case "ICP":
                        case "ICL":
                            loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);break;
                        case "RRD":
                        case "EXP":
                            loEmployeeLoan.setStatus(LoEmployeeLoan.STATUS_PAID_OFF);break;
                    }
                    */
                                        
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
                    log.info(identifier + " Data Not Found In Estar : " + singleRequest.getRequestNo());
                }

            }catch (Exception ex){
                countFailed++;
                log.info(identifier + " Error Get Data From Estar : " + ex.getMessage());

            }
        }

        message = "Total Data Non Simulation = " + totalData.toString() + " (Success : " + countSuccess + " | Failed : " + countFailed + " | Not Found : " + countNotFound + ")";
        //------------------------------------------------------------------
        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getUuid()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanEmployeePostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoEmployeeLoan dataUsed = new LoEmployeeLoan();
        String message=null;

        //check requestNo
        if(request.getRequestNo() == null || request.getRequestNo().isEmpty()){
            throw new CustomGenericException("Request No Can't Be Empty !");
        }

        Optional<LoLoanRequest> loanRequest = loLoanRequestRepository.findByRequestNoApproved(request.getRequestNo());
        if(!loanRequest.isPresent()){
            throw new CustomGenericException("RequestNo May Not Approved Yet !");
        }

        //logic
        Optional<LoEmployeeLoan> employeeLoan = repository.findByRequestNo(request.getRequestNo());
        if(!employeeLoan.isPresent()){
            dataUsed = new LoEmployeeLoan();
            dataUsed.setRequestNo(request.getRequestNo());
            dataUsed.setRemaining(request.getRemaining());
            dataUsed.setTenor(request.getTenor());
            if(request.getRemaining() == 0){
                dataUsed.setStatus(LoEmployeeLoan.STATUS_PAID_OFF);
            }else {
                dataUsed.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);
            }
            dataUsed.setAmount(request.getAmount());
            dataUsed.setPaidAmount(request.getPaidAmount());
            if(request.getGoLiveDate() == null){
                dataUsed.setGoLiveDate(LocalDateTime.now());
            }else {
                dataUsed.setGoLiveDate(request.getGoLiveDate());
            }
            dataUsed.setLastPaidDate(request.getLastPaidDate());
            dataUsed.setLeftInssurance(request.getLeftInssurance());

            dataUsed.setRequestDate(loanRequest.get().getRequestDate());
            dataUsed.setEmploymentId(loanRequest.get().getEmploymentId());
            dataUsed.setLoanCategoryId(loanRequest.get().getLoanCategoryId());
            dataUsed.setLoanTypeId(loanRequest.get().getLoanTypeId());
            dataUsed.setBrandId(loanRequest.get().getBrandId());
            dataUsed.setDownPayment(loanRequest.get().getDownPayment());
            dataUsed.setMonthlyInstallment(loanRequest.get().getMonthlyInstallment());
            dataUsed.setLoanDate(loanRequest.get().getLoanDate());

            dataUsed.setCreatedBy("EStar");
            dataUsed.setCreatedDate(new Date());
            dataUsed.setModifiedBy(null);
            dataUsed.setModifiedDate(null);
            message="Submit New " + identifier + " Successfully";
        }else {
            //employee loan already in My Loan, so just update
            dataUsed = employeeLoan.get();
            dataUsed.setRemaining(request.getRemaining());
            dataUsed.setTenor(request.getTenor());
            if(request.getRemaining() == 0){
                dataUsed.setStatus(LoEmployeeLoan.STATUS_PAID_OFF);
            }else {
                dataUsed.setStatus(LoEmployeeLoan.STATUS_IN_PROGRESS);
            }
            dataUsed.setAmount(request.getAmount());
            dataUsed.setPaidAmount(request.getPaidAmount());
            if(request.getGoLiveDate() == null){
                dataUsed.setGoLiveDate(LocalDateTime.now());
            }else {
                dataUsed.setGoLiveDate(request.getGoLiveDate());
            }
            dataUsed.setLastPaidDate(request.getLastPaidDate());
            dataUsed.setLeftInssurance(request.getLeftInssurance());

        }

        repository.save(dataUsed);

        //return
        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getUuid()), HttpStatus.OK);

    }

}
