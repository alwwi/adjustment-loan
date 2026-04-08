package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.DataApprovalRepository;
import com.phincon.talents.app.dao.RequestCategoryTypeRepository;
import com.phincon.talents.app.dao.VwEmpAssignmentRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.DataApprovalAttachmentDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.model.loan.LoLoanType;
import com.phincon.talents.app.repository.*;
import com.phincon.talents.app.services.DataApprovalService;
import com.phincon.talents.app.services.TalentsTransactionNoService;
import com.phincon.talents.app.utils.RepositoryUtils;
import com.phincon.talents.app.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LoanEarlySettlementRequestService {

    private static final Logger log = LogManager.getLogger(LoanEarlySettlementRequestService.class);

    @Autowired
    LoEarlySettlementRequestRepository requestRepository;

    @Autowired
    LoLoanRequestRepository loanRequestRepository;

    @Autowired
    LoEmployeeLoanRepository loEmployeeLoanRepository;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @Autowired
    RequestCategoryTypeRepository requestCategoryTypeRepository;

    @Autowired
    LoLoanTypeRepository loLoanTypeRepository;

    @Autowired
    TalentsTransactionNoService talentsTransactionNoService;

    @Autowired
    LoLoanAttachmentRepository loLoanAttachmentRepository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Autowired
    DataApprovalService dataApprovalService;

    @Autowired
    private Environment env;

    @Autowired
    DataApprovalRepository dataApprovalRepository;

    @Value("${mtf.sc.jwt.secret}")
    private String secret;

    @Transactional
    public String InsertData(String identifier,LoanEarlySettlementRequestDTO request, String claimForEmployeeId,
                             String requesterEmployeeId, Boolean isAdmin, Boolean isByPassApproval) {

        String identifierLog = "[" + request.getRequestNo() + "] ";
        log.info(identifierLog + "loan request payload = " + request.toString());

        //--------------------------------------------------
        //parameter for use
        //--------------------------------------------------
        String requestCategoryType = request.getActionRequest(); //RequestCategoryType.REQUEST_TYPE_EARLY_SETTLEMENT; //For hr_request_category column name

        String dataApprovalModule = RequestCategoryType.MODULE_TYPE_EARLY_SETTLEMENT; //For wf_data_approval Column Module

        //in talents_transaction_no column Module and Type
        String transactionNextValModule = "Early Settlement";
        String transactionNextValType = "Early Settlement";
        //--------------------------------------------------

        //Check if Loan Request Id exists
        Optional<LoLoanRequest> loanRequest =loanRequestRepository.findByRequestNo(request.getLoanRequestNo());
        if(!loanRequest.isPresent()){
            log.info(identifierLog + "Loan Request not found!");
            throw new CustomGenericException("Loan Request not found!");
        }

        //check if this loan request can use early settlement
        Optional<LoLoanType> loanTypeUsed = loLoanTypeRepository.findById(loanRequest.get().getLoanTypeId());
        if(!loanTypeUsed.isPresent()){
            log.info(identifierLog + "Loan Type Not Found");
            throw new CustomGenericException("Loan Type Not Found");
        }
        Boolean canUseEarlySettlement = loanTypeUsed.get().getEarlySettlement();
        if(canUseEarlySettlement != null && !canUseEarlySettlement){
            throw new CustomGenericException("This Loan Request Cannot Use Early Settlement");
        }

        //check if loan request already approved
        Optional<DataApproval> dataApprovalFromRequest = loanRequestRepository.findByObjectRef(loanRequest.get().getId());
        if(dataApprovalFromRequest.isPresent()){
            if(!dataApprovalFromRequest.get().getStatus().equalsIgnoreCase("approved")){
                log.info(identifierLog + "Loan Request Has Not Been Approved Yet");
                throw new CustomGenericException("Loan Request Has Not Been Approved Yet");
            }
        }else {
            log.info(identifierLog + "Loan Request Not Found !");
            throw new CustomGenericException("Loan Request Not Found !");
        }

        //check if loan request already sync
        if(loanRequest.get().getNeedSyncEstar() != null &&
                loanRequest.get().getNeedSyncEstar() == true
        ){
            log.info(identifierLog + "Loan Request Has Not Been Sync Yet !");
            throw new CustomGenericException("Loan Request Has Not Been Sync Yet !");
        }

        //check if loan request already filled in lo_employee_loan (lo_employee_loan filled from estar API)
        Optional<LoEmployeeLoan> employeeLoanData = loEmployeeLoanRepository.findByRequestNo(loanRequest.get().getRequestNo());
        if(!employeeLoanData.isPresent()
        ){
            log.info(identifierLog + "Loan Request Has Not Been Recognize By Estar or HcEazy Yet !");
            throw new CustomGenericException("Loan Request Has Not Been Recognize By Estar or HcEazy Yet !");
        }

        if(!employeeLoanData.get().getConfirmed() || employeeLoanData.get().getStatus().equals(LoEmployeeLoan.STATUS_NOT_STARTED)){
            log.info(identifierLog + "Loan Request Has Not Been Confirmed");
            throw new CustomGenericException("Loan Request Has Not Been Confirmed");
        }

        if(employeeLoanData.get().getStatus().equals(LoEmployeeLoan.STATUS_CANCEL)){
            log.info(identifierLog + "Loan Request Has Been Canceled");
            throw new CustomGenericException("Loan Request Has Been Canceled");
        }

        //check if employee loan already paid out
        if(employeeLoanData.get().getStatus().equalsIgnoreCase("paid off")){
            log.info(identifierLog + "Loan Request Already Paid Off");
            throw new CustomGenericException("Loan Request Already Paid Off");
        }

        //initialize variable
        String requestNo = "";
        LoEarlySettlementRequest requestData;

        Optional<VwEmpAssignment> employeeViewOpt = vwEmpAssignmentRepository.findById(claimForEmployeeId);
        if (!employeeViewOpt.isPresent()) {
            log.info(identifierLog + "Something went wrong,please contact Admin - Employee Detail Not Found");
            throw new CustomGenericException("Something went wrong,please contact Admin - Employee Detail Not Found");
        }

        Integer countPendingAndNeedSync = requestRepository.countPendingAndNeedSync(loanRequest.get().getEmploymentId());
        if(countPendingAndNeedSync>0)
        {
            log.info(identifierLog + "This request can't be completed. There is pending " + identifier +  " for this Employment.");
            throw new CustomGenericException("This request can't be completed. There is pending " + identifier +  " for this Employment.");
        }


        Optional<VwEmpAssignment> requestorViewOpt = vwEmpAssignmentRepository.findById(requesterEmployeeId);
        if (requestorViewOpt.isPresent()) {
            VwEmpAssignment requestorView = requestorViewOpt.get();

            Optional<RequestCategoryType> requestCategoryTypeOpt = requestCategoryTypeRepository.findByName(requestCategoryType);
            if(!requestCategoryTypeOpt.isPresent()){
                log.info(identifierLog + "Request Category Type Not Found");
                throw new CustomGenericException("Request Category Type Not Found");
            }

            requestData = createRequest(identifierLog,
                    transactionNextValModule,transactionNextValType,
                    employeeViewOpt.get().getEmploymentId(),
                    requestCategoryTypeOpt.get().getId(),
                    requestorView.getEmploymentId(),
                    request,employeeLoanData.get(),
                    isAdmin);
            requestNo = requestData.getRequestNo();

            if (!isAdmin) {
                log.info(identifierLog + "Enter isByAdmin false");
                dataApprovalService.createDataApproval(1, requesterEmployeeId,
                        dataApprovalModule, requestData.getId(), DataApproval.IN_PROGRESS,
                        claimForEmployeeId, request.getAttachments(), requestCategoryTypeOpt.get().getId(), requestorView.getWorkLocationType(), requestorView.getPositionLevelId(), requestCategoryTypeOpt.get().getName(), requestorView.getRegionalId()
                );

            }else {
                log.info(identifierLog + "Enter isByAdmin true");
                if(isByPassApproval){
                    log.info(identifierLog + "Enter isByPassApproval true");
                    dataApprovalService.createDataApprovalAdmin(requesterEmployeeId, dataApprovalModule, requestData.getId(), requestCategoryTypeOpt.get().getName(), claimForEmployeeId, request.getAttachments(),Optional.empty(),Optional.empty());
                }else {
                    log.info(identifierLog + "Enter isByPassApproval false");
                    dataApprovalService.createDataApproval(1, requesterEmployeeId,
                            dataApprovalModule, requestData.getId(), DataApproval.IN_PROGRESS, claimForEmployeeId,
                            request.getAttachments(), requestCategoryTypeOpt.get().getId(), requestorView.getWorkLocationType(),
                            requestorView.getPositionLevelId(),requestCategoryTypeOpt.get().getName(),requestorView.getRegionalId());
                }
            }
        }
        return requestNo;
    }


    @Transactional
    public LoEarlySettlementRequest createRequest(String identifierLog,String module, String type,
                                                  String employmentId, String categoryId, String requestEmploymentId,
                                       LoanEarlySettlementRequestDTO request, LoEmployeeLoan loEmployeeLoan,Boolean isAdmin
    ) {
        //-------------------------------------------------
        // Check Mandatory Field
        //-------------------------------------------------

        //-------------------------------------------------

        //-------------------------------------------------
        // Set Data
        //-------------------------------------------------
        LoEarlySettlementRequest requestData = new LoEarlySettlementRequest();

        String requestNo = talentsTransactionNoService.getTransactionNextVal(module,type);
        requestData.setRequestNo(requestNo);
        requestData.setRequestDate(LocalDateTime.now());
        requestData.setLoanRequestNo(request.getLoanRequestNo());
        requestData.setRemainingPayment(request.getRemainingPayment());
        requestData.setRemark(request.getRemark());
        requestData.setEmployeeLoanId(loEmployeeLoan.getId());
        requestData.setCategoryId(categoryId);
        requestData.setRemark(request.getRemark());


//        requestData.setLoanRequestId(request.getLoanRequestId());
        LoEarlySettlementRequest response = requestRepository.save(requestData);
        //-------------------------------------------------

        return response;
    }

    @Transactional
    public Page<LoanEarlySettlementRequestNeedApprovalDTO> needApprovalList(String approverEmployeeId, String status,
                                                             HttpServletRequest request, String requestDateStart, String requestDateEnd, String ppl,
                                                             String loanTypeId,
                                                             PageRequest pageable, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDateStartDate = null;
        Date requestDateEndDate = null;
        if (requestDateStart != null) {
            try {
                requestDateStartDate = sdf.parse(requestDateStart);
                requestDateEndDate = sdf.parse(requestDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get All Need Approval
        //------------------------------------------------------------------------
        Page<LoanEarlySettlementRequestNeedApprovalDTO> resultOpt = requestRepository.findNeedApprovalBy("%" + approverEmployeeId + "%",
                status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate, "%" + ppl + "%",loanTypeId,
                pageable);
        //------------------------------------------------------------------------

        return resultOpt;

    }

    @Transactional
    public Page<LoanEarlySettlementMyRequestDTO> findMyRequestByEmployeeAndModule(String employmentRequest, String status,
                                                              HttpServletRequest request, String requestDateStart, String requestDateEnd, PageRequest pageable,String requestNo, String loanTypeId, String loanCategoryId, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDateStartDate = null;
        Date requestDateEndDate = null;
        if (requestDateStart != null) {
            try {
                requestDateStartDate = sdf.parse(requestDateStart);
                requestDateEndDate = sdf.parse(requestDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get My Request Data
        //------------------------------------------------------------------------
        Page<LoanEarlySettlementMyRequestDTO> findByEmployeeAndStatus = requestRepository.findByEmployeeAndStatus(
                employmentRequest, status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate,
                pageable,'%'+requestNo+'%',loanTypeId,loanCategoryId,secret);
        List<LoanEarlySettlementMyRequestDTO> content = findByEmployeeAndStatus.getContent();
        //------------------------------------------------------------------------

        return findByEmployeeAndStatus;
    }

    @Transactional
    public Optional<LoanEarlySettlementMyRequestDTO> findMyRequestByEmployeeAndId(String empRequest, String dataApprovalId,
                                                              HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanEarlySettlementMyRequestDTO> resultOpt = requestRepository.findByEmployeeAndId(empRequest, dataApprovalId,
                Utils.getServerName(http, request, jwt, secret),secret);
        //------------------------------------------------------------------------

        return resultOpt;
    }

    @Transactional
    public Optional<LoanEarlySettlementMyRequestDTO> findNeedApproval2ById(String approverEmployeeId, String dataApprovalId,
                                                               HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanEarlySettlementMyRequestDTO> resultOpt = requestRepository.findNeedApprovalById(
                "%" + approverEmployeeId + "%", dataApprovalId, Utils.getServerName(http, request, jwt, secret),secret);
        //------------------------------------------------------------------------

        return resultOpt;
    }

    @Transactional
    public Optional<LoanEarlySettlementRemainingDTO> checkRemainingAmount(String employeeId,String requestNo,
                                                                           HttpServletRequest request) {
        VwEmpAssignment vwEmpAssignment = vwEmpAssignmentRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new CustomGenericException("Employee Not Found")
        );
                ;
        Optional<LoanEarlySettlementRemainingDTO> resultOpt = requestRepository.checkRemainingAmount(vwEmpAssignment.getEmploymentId(),requestNo);
        return resultOpt;
    }

    @Transactional
    public Page<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApproval(String approverEmployeeId, String status,
                                                                         HttpServletRequest request, String requestDateStart, String requestDateEnd, String ppl,
                                                                         PageRequest pageable, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDateStartDate = null;
        Date requestDateEndDate = null;
        if (requestDateStart != null) {
            try {
                requestDateStartDate = sdf.parse(requestDateStart);
                requestDateEndDate = sdf.parse(requestDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Page<LoanEarlySettlementRequestHistoryApprovalDTO> resultOpt = requestRepository.findHistoryApproval(approverEmployeeId,
                status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate, "%" + ppl + "%",
                pageable,secret);
        return resultOpt;
    }

    @Transactional
    public Optional<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApprovalByIdAndEmployee(String approverEmployeeId,
                                                                                                     String dataApprovalDetailId, HttpServletRequest request, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        Optional<LoanEarlySettlementRequestHistoryApprovalDTO> resultOpt = requestRepository.findHistoryApprovalByIdAndEmployee(
                approverEmployeeId, dataApprovalDetailId, Utils.getServerName(http, request, jwt, secret),secret);

        return resultOpt;
    }


    @Transactional
    public List<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApprovalByApprovalId(String dataApprovalId,
                                                                                     HttpServletRequest request, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        Sort sort = Sort.by(Sort.Direction.DESC, "actionDate");
        List<LoanEarlySettlementRequestHistoryApprovalDTO> resultOpt = requestRepository
                .findHistoryApprovalByDataApprovalId(dataApprovalId, Utils.getServerName(http, request, jwt, secret), sort,secret);
        return resultOpt;
    }
}
