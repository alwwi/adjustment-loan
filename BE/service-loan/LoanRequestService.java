package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.controllers.api.loan.LoanRequestController;
import com.phincon.talents.app.dao.*;
import com.phincon.talents.app.dao.pa.PAEmployeePerformanceRepository;
import com.phincon.talents.app.dao.pa.PARatingRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.DataApprovalAttachmentDTO;
import com.phincon.talents.app.dto.FKPRequestDTO;
import com.phincon.talents.app.dto.FKPRequestHistoryApprovalDTO;
import com.phincon.talents.app.dto.employment.AssignmentRequestDTO;
import com.phincon.talents.app.dto.integration.estar.EstarResponseSubmitLoanEmployeeDataDTO;
import com.phincon.talents.app.dto.integration.estar.EstarSubmitLoanEmployeeDataDTO;
import com.phincon.talents.app.dto.integration.estar.EstarSubmitLoanEmployeeDataDetailDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.dto.personal.WorkExperienceRequestDTO;
import com.phincon.talents.app.dto.recruitment.FKPDetailDTO;
import com.phincon.talents.app.dto.recruitment.FKPPostDTO;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.TalentsParameter;
import com.phincon.talents.app.model.hr.*;
import com.phincon.talents.app.model.loan.*;
import com.phincon.talents.app.repository.*;
import com.phincon.talents.app.services.DataApprovalService;
import com.phincon.talents.app.services.TalentsTransactionNoService;
import com.phincon.talents.app.services.dataapproval.DAParentService;
import com.phincon.talents.app.services.integration.EstarApiService;
import com.phincon.talents.app.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class LoanRequestService {

    private static final Logger log = LogManager.getLogger(LoanRequestService.class);

    @Autowired
    LoLoanRequestRepository requestRepository;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @Autowired
    RequestCategoryTypeRepository requestCategoryTypeRepository;

    @Autowired
    TalentsTransactionNoService talentsTransactionNoService;

    @Autowired
    LoLoanAttachmentRepository loLoanAttachmentRepository;

    @Autowired
    EmploymentRepository employmentRepository;

    @Autowired
    LoCriteriaRepository loCriteriaRepository;

    @Autowired
    LoPlafondBracketRepository loPlafondBracketRepository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Autowired
    DataApprovalService dataApprovalService;

    @Autowired
    JobTitleRepository jobTitleRepository;

    @Autowired
    JobFamilyRepository jobFamilyRepository;

    @Autowired
    LoEmployeeLoanRepository loEmployeeLoanRepository;

    @Autowired
    LoLoanCategoryRepository loLoanCategoryRepository;

    @Autowired
    LoLoanTypeRepository loLoanTypeRepository;

    @Autowired
    TalentsParameterRepository talentsParameterRepository;

    @Autowired
    PayrollMonthlyHeaderRepository payrollMonthlyHeaderRepository;

    @Autowired
    LoFeedbackFleetRepository loFeedbackFleetRepository;

    @Autowired
    LoLoanRequestDtlRepository loLoanRequestDtlRepository;

    @Autowired
    LoDisciplineRepository loDisciplineRepository;

    @Autowired
    LoAllowanceBracketRepository loAllowanceBracketRepository;

    @Autowired
    private Environment env;

    @Autowired
    EstarApiService estarApiService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    LoVehicleBrandRepository loVehicleBrandRepository;

    @Autowired
    LoRatingModelMatrixRepository loRatingModelMatrixRepository;

    @Autowired
    LoRatingModelRepository loRatingModelRepository;

    @Autowired
    PAEmployeePerformanceRepository paEmployeePerformanceRepository;

    @Value("${mtf.sc.jwt.secret}")
    private String secret;

    @Autowired
    ApplicationPropertiesService applicationPropertiesService;

    @Transactional
    public ResponseEntity<CustomMessageTest>  confirmLoan(String requestNo, String action) {
        String identifierLog = "[" + requestNo + "] ";
        log.info(identifierLog + "Confirm Loan");
        Optional<LoLoanRequest>  request = requestRepository.findByRequestNo(requestNo);
        if(!request.isPresent()){
           throw new CustomGenericException("Request Not Found In Loan Request !");
        }

        Optional<LoEmployeeLoan>  loEmployeeLoan = loEmployeeLoanRepository.findByRequestNo(requestNo);
        if(!loEmployeeLoan.isPresent()){
            throw new CustomGenericException("Request Not Found In Employee Loan !");
        }

        Optional<LoLoanCategory> loLoanCategory = loLoanCategoryRepository.findById(request.get().getLoanCategoryId());
        if(!loLoanCategory.isPresent()){
            throw new CustomGenericException("Loan Category Not Found !");
        }

        //cek status before
        if(loEmployeeLoan.get().getStatus().equals(LoEmployeeLoan.STATUS_NOT_STARTED) || loEmployeeLoan.get().getTenor() == 0) {
            loEmployeeLoan.get().setStatus(action);
            loEmployeeLoan.get().setConfirmed(true);
            loEmployeeLoanRepository.save(loEmployeeLoan.get());

            if(!loLoanCategory.get().getSimulation()) {
                //trigger estar api
                //--------------------------------------------
                Optional<VwEmpAssignment> vwEmpAssignment = vwEmpAssignmentRepository.findByEmploymentId(request.get().getEmploymentId());
                if (!vwEmpAssignment.isPresent()) {
                    throw new CustomGenericException("Employee Assignment Not Found !");
                }

                Optional<Employee> employee = employeeRepository.findById(vwEmpAssignment.get().getEmployeeId());
                if (!employee.isPresent()) {
                    throw new CustomGenericException("Employee Not Found !");
                }

                EstarSubmitLoanEmployeeDataDTO requestBody = new EstarSubmitLoanEmployeeDataDTO();
                List<EstarSubmitLoanEmployeeDataDetailDTO> requestBodyDetail = new ArrayList<>();
                EstarSubmitLoanEmployeeDataDetailDTO singleRequestBodyDetail = new EstarSubmitLoanEmployeeDataDetailDTO();
                singleRequestBodyDetail.setNoRequest(request.get().getRequestNo());
                singleRequestBodyDetail.setNik(employee.get().getNircNo());
                singleRequestBodyDetail.setNip(vwEmpAssignment.get().getEmployeeNo());
                requestBodyDetail.add(singleRequestBodyDetail);
                requestBody.setSubmitLoanEmployeeData(requestBodyDetail);

                System.out.println("Send To Estar :");
                System.out.println(request.get().getRequestNo());
                System.out.println(employee.get().getNircNo());
                System.out.println(vwEmpAssignment.get().getEmployeeNo());
                try {
                    EstarResponseSubmitLoanEmployeeDataDTO responseEstar = estarApiService.submitLoanEmployeeData(
                            "[Confirm Loan] ",
                            requestBody
                    );

                    //save response to result_sync in lo_loan_request (if success, if not just bypass it)
                    // if not sync its expected to syn in scheduler using needsyncestar
                    if (responseEstar.getSubmitLoanEmployeeResult().getErrorCode().equalsIgnoreCase(EstarResponseSubmitLoanEmployeeDataDTO.RESPONSE_SUCCESS)) {
                        request.get().setNeedSyncEstar(Boolean.FALSE); //already success submit to estar, so dont need to submit again
                        request.get().setResultSyncEstar("Success");
                        request.get().setSyncDateEstar(LocalDateTime.now());
                        requestRepository.save(request.get());
                    } else if (responseEstar.getSubmitLoanEmployeeResult().getErrorCode().equalsIgnoreCase(EstarResponseSubmitLoanEmployeeDataDTO.RESPONSE_ERROR)) {
                        request.get().setNeedSyncEstar(Boolean.TRUE); //still error when submit, so still need to submit to estar in future
                        request.get().setResultSyncEstar(responseEstar.getSubmitLoanEmployeeResult().getMessageResponse());
                        request.get().setSyncDateEstar(null);
                        requestRepository.save(request.get());
                    }


                } catch (Exception ex) {
                    //just bypass this if error
                }
                //--------------------------------------------
            }
        }else {
            throw new CustomGenericException("Request Already Confirmed Before !");
        }

        return new ResponseEntity<>(
                new CustomMessageTest("Confirm Loan Sucessfully (Request No : " + requestNo + ")", false,""),
                HttpStatus.OK);
    }

    @Transactional
    public String InsertData(String identifier,LoanRequestDTO request, String claimForEmployeeId,
                             String requesterEmployeeId, Boolean isAdmin, Boolean isByPassApproval) {
        String identifierLog = "[" + request.getEmploymentId() + "] ";
        log.info(identifierLog + "loan request payload = " + request.toString());

        //--------------------------------------------------
        //parameter for use
        //--------------------------------------------------
        String requestCategoryType = null;
        String requestCategoryToFind = request.getActionRequest(); // RequestCategoryType.REQUEST_TYPE_LOAN; //For hr_request_category column name

        String dataApprovalModule = RequestCategoryType.MODULE_TYPE_LOAN; //For wf_data_approval Column Module

        //in talents_transaction_no column Module and Type
        String transactionNextValModule = "Loan";
        String transactionNextValType = "Loan";

        //--------------------------------------------------

        //initialize variable
        String requestNo = "";
        LoLoanRequest requestData;

        Optional<VwEmpAssignment> employeeViewOpt = vwEmpAssignmentRepository.findById(claimForEmployeeId);
        if (!employeeViewOpt.isPresent()) {
            log.info(identifierLog + "Something went wrong,please contact Admin - Employee Detail Not Found");
            throw new CustomGenericException("Something went wrong,please contact Admin - Employee Detail Not Found");
        }

        Integer countPendingAndNeedSync = requestRepository.countPendingAndNeedSync(employeeViewOpt.get().getEmploymentId());
        if(countPendingAndNeedSync>0)
        {
            log.info(identifierLog + "This request can't be completed. There is pending " + identifier +  " for this Employment.");
            throw new CustomGenericException("This request can't be completed. There is pending " + identifier +  " for this Employment.");
        }


        Optional<VwEmpAssignment> requestorViewOpt = vwEmpAssignmentRepository.findById(requesterEmployeeId);
        if (requestorViewOpt.isPresent()) {
            VwEmpAssignment requestorView = requestorViewOpt.get();

            Optional<LoanCategoryDetailDTO> loanCategoryFound = loLoanCategoryRepository.getData(request.getLoanCategoryId());
            if(!loanCategoryFound.isPresent()){
                log.info(identifierLog + "Loan Category Not Found");
                throw new CustomGenericException("Loan Category Not Found");
            }

            requestCategoryType = loanCategoryFound.get().getRequestCategoryTypeName();
            if(requestCategoryType == null){
                log.info(identifierLog + "Request Category Type Not Found");
                throw new CustomGenericException("Request Category Type Not Found");
            }

            Optional<RequestCategoryType> requestCategoryTypeOpt = requestCategoryTypeRepository.findById(loanCategoryFound.get().getRequestCategoryTypeId());
            if(!requestCategoryTypeOpt.isPresent()){
                log.info(identifierLog + "Request Category Type Not Found");
                throw new CustomGenericException("Request Category Type Not Found");
            }

            transactionNextValType = requestCategoryTypeOpt.get().getName();
            log.info("transactionNextValType : " + transactionNextValType);
            log.info("transactionNextValModule : " + transactionNextValModule);


            requestData = createRequest(identifierLog,transactionNextValModule,transactionNextValType,
                    employeeViewOpt.get().getEmploymentId(),
                    requestCategoryTypeOpt.get().getId(),
                    requestorView.getEmploymentId(),
                    request,
                    isAdmin,
                    employeeViewOpt.get());
            requestNo = requestData.getRequestNo();

            //prepare data for multiple attachment
            List<DataApprovalAttachmentDTO> listAttachmentForDataApproval = prepareListAttachmentForDataApproval(identifierLog,request);

            if (!isAdmin) {
                log.info(identifierLog + "Enter isByAdmin false");
                //createDataApprovalMultipleAttachment -> jika menggunakan ini ubah needapproval, myrequest, dan history agar tidak left join langsung ke data approval attachment
                dataApprovalService.createDataApprovalMultipleAttachment(1, requesterEmployeeId,
                        dataApprovalModule, requestData.getId(), DataApproval.IN_PROGRESS,
                        claimForEmployeeId, request.getAttachments(), requestCategoryTypeOpt.get().getId(), requestorView.getWorkLocationType(), requestorView.getPositionLevelId(), requestCategoryTypeOpt.get().getName(), requestorView.getRegionalId(),
                        Optional.of(listAttachmentForDataApproval)
                );

            }else {
                log.info(identifierLog + "Enter isByAdmin true");

//                if(isByPassApproval){
//                    //bypassapproval cannot be implement in this loan because there are custom workflow
////                    List<Map<String,Object>> additionalInformation = new ArrayList<>();
////                        Map<String,Object> singleAddtionalInfo = new HashMap<>();
////                        singleAddtionalInfo.put("monthlyInstallment",request.getMonthlyInstallment());
////                    additionalInformation.add(singleAddtionalInfo);
////
////                    log.info(identifierLog + "Enter isByAdmin true and isByPassApproval true");
////                    dataApprovalService.createDataApprovalAdmin(requesterEmployeeId, dataApprovalModule, requestData.getId(), requestCategoryTypeOpt.get().getName(), claimForEmployeeId, request.getAttachments(), Optional.of(listAttachmentForDataApproval), Optional.of(additionalInformation));
//                }else {
                    log.info(identifierLog + "Enter isByAdmin true and isByPassApproval false");
                    log.info(identifierLog + "Requester will be the employee itself");
                    dataApprovalService.createDataApprovalMultipleAttachment(1, claimForEmployeeId,
                            dataApprovalModule, requestData.getId(), DataApproval.IN_PROGRESS,
                            claimForEmployeeId, request.getAttachments(), requestCategoryTypeOpt.get().getId(), requestorView.getWorkLocationType(), requestorView.getPositionLevelId(), requestCategoryTypeOpt.get().getName(), requestorView.getRegionalId(),
                            Optional.of(listAttachmentForDataApproval)
                    );
//                }
            }
        }
        return requestNo;
    }

    private List<DataApprovalAttachmentDTO> prepareListAttachmentForDataApproval(String identifierLog,LoanRequestDTO request){
        List<DataApprovalAttachmentDTO> listAttachmentForDataApproval = new ArrayList<>();

        String functionIdentifier = "[prepareListAttachmentForDataApproval] ";

        //------------------------------------------------------------------------
        //process detail attachment
        //------------------------------------------------------------------------
        List<LoanAttachmentDetailDTO> allRequiredAttachment = loLoanAttachmentRepository.getAllRequiredAttachmentByLoanType(request.getLoanTypeId());
        if(allRequiredAttachment.size() > 0) {
//            //if dont have any detail attachment in request
//            if (request.getAttachmentDetails() == null || request.getAttachmentDetails().size() <= 0) {
//                log.info(identifierLog + functionIdentifier + "Incomplete Attachments");
//                throw new CustomGenericException("Incomplete Attachments");
//            }

//            //if count of detail attachment less than allRequiredAttachment size
//            if(request.getAttachmentDetails().size() < allRequiredAttachment.size()){
//                log.info(identifierLog + functionIdentifier + "Incomplete Attachments");
//                throw new CustomGenericException("Incomplete Attachments !");
//            }

            //------------------------------------------------------------------------
            //check what incomplete attachment
            //------------------------------------------------------------------------
            List<LoanAttachmentDetailDTO> incompleteRequiredAttachment = new ArrayList<>();
            for( LoanAttachmentDetailDTO singleRequiredAttachment : allRequiredAttachment){
                Boolean isFound = false;

                // if not mandatory then skip this
                if(singleRequiredAttachment.getMandatory() == null || !singleRequiredAttachment.getMandatory()){
                    continue;
                }

                //check if the mandatory attachment already found in request
                for(LoanRequestAttachmentDTO singleRequestAttachment : request.getAttachmentDetails()){
                    if(singleRequiredAttachment.getId().toString().equalsIgnoreCase(singleRequestAttachment.getLoanAttachmentId().toString())){
                        isFound = true;
                        break;
                    }
                }

                if(!isFound){
                    incompleteRequiredAttachment.add(singleRequiredAttachment);
                }

            }

            if(incompleteRequiredAttachment.size() > 0){
                throw  new CustomGenericException("Incomplete Attachments : " + incompleteRequiredAttachment.get(0).getName());
            }
            //------------------------------------------------------------------------

            //------------------------------------------------------------------------
            // prepare data
            //------------------------------------------------------------------------
            for(LoanRequestAttachmentDTO singleAttachment : request.getAttachmentDetails()){
                Optional<LoLoanAttachment> loLoanAttachmentFound = loLoanAttachmentRepository.findById(singleAttachment.getLoanAttachmentId());
                if(!loLoanAttachmentFound.isPresent()) {
                    log.info(identifierLog + functionIdentifier + "Attachment Not Found");
                    throw new CustomGenericException("Attachment Not Found");
                }
                // if not mandatory then skip this
                if(loLoanAttachmentFound.get().getMandatory() == null || !loLoanAttachmentFound.get().getMandatory()){
                    //if its not mandatory, and in request it doesnt have any file, skip this
                    if(singleAttachment.getPath() == null) {
                        continue;
                    }
                }

                DataApprovalAttachmentDTO singleDataApprovalAttachment  = new DataApprovalAttachmentDTO();
//                singleDataApprovalAttachment.setObjectRefId(singleAttachment.getLoanAttachmentId());
                singleDataApprovalAttachment.setObjectRefId(loLoanAttachmentFound.get().getId());
                singleDataApprovalAttachment.setObjectRefName("Loan Attachment");
                singleDataApprovalAttachment.setPath(singleAttachment.getPath());
                listAttachmentForDataApproval.add(singleDataApprovalAttachment);
            }

        }
        //------------------------------------------------------------------------

        return listAttachmentForDataApproval;
    }

    @Transactional
    public LoanRatingModelCheckResultDTO checkRatingModelMatrix(String identifierLog,String loanCategoryId, String employmentId, LocalDate localDate){
        LoanRatingModelCheckResultDTO responses = new LoanRatingModelCheckResultDTO();
        String functionIdentifier = "[checkRatingModelMatrix] ";
        log.info("loanCategoryId : " + loanCategoryId);
        log.info("localDate : " + localDate);
        List<LoanRatingModelListDTO> listRatingModel = loRatingModelRepository.findByLoanCategoryId(loanCategoryId,localDate);
        log.info("listRatingModel.size : " + listRatingModel.size());
        log.info(identifierLog + functionIdentifier + " Employment Id : " + employmentId);
        PageRequest pageable = PageRequest.of(0, 2);
        List<LoanEmploymentPerformanceRating> listPaRatingEmployment = paEmployeePerformanceRepository.getEmploymentPerformanceRating(employmentId,pageable);
        listPaRatingEmployment.stream()
                .forEach(
                        x -> {
                            log.info(identifierLog + functionIdentifier + " Employment Rating (" + x.getFinalRemark() + ")(" + x.getYear() + ") : " + x.getFinalRating() + "(Final Score : " + (x.getFinalScore() == null ? "null" : x.getFinalScore()) + ")" + "(Total Score : " + (x.getTotalScore() == null ? "null" : x.getTotalScore()) + ")");
                        }
                );

        if(listRatingModel.size() == 0){
            log.info(identifierLog + functionIdentifier + " Rating Model Not Found");
            throw new CustomGenericException("Rating Model Not Found");
        }

        String paRatingYear1 = !listPaRatingEmployment.isEmpty() ? listPaRatingEmployment.get(0).getFinalRating() : null;
        Double paRatingFinalScore1 = !listPaRatingEmployment.isEmpty() ? listPaRatingEmployment.get(0).getFinalScore() : null;
        Double paRatingTotalScore1 = !listPaRatingEmployment.isEmpty() ? listPaRatingEmployment.get(0).getTotalScore() : null;
        String paRatingYear2 = listPaRatingEmployment.size() >= 2 ? listPaRatingEmployment.get(1).getFinalRating() : null;

        responses.setScoreYear1(paRatingYear1);
        responses.setScoreYear2(paRatingYear2);
        responses.setScoreFinalYear1(paRatingFinalScore1);
        List<String> listRatingModelIdYear1= new ArrayList<>();
        List<String> listRatingModelIdYear2Bawah= new ArrayList<>();
        List<String> listRatingModelIdYear2Atas= new ArrayList<>();
        List<String> listRatingModelIdYearNull= new ArrayList<>();

        for(LoanRatingModelListDTO singleRatingModel : listRatingModel){
            String loanRatingModelId = singleRatingModel.getId();
            Double paFinalRangeFromMatrix = singleRatingModel.getPaRatingRangeFrom();
            Double paFinalRangeToMatrix = singleRatingModel.getPaRatingRangeTo();

            LoanRatingModelCheckResultDTO response = new LoanRatingModelCheckResultDTO();
            if(singleRatingModel.getYearBefore() == null){
                listRatingModelIdYearNull.add(singleRatingModel.getId());

            }else if (singleRatingModel.getYearBefore() == 1){
                if(paRatingFinalScore1 == null){
                    log.info(identifierLog + functionIdentifier + " Employee dont have Final Score Year 1");
//                    throw new CustomGenericException("Employee dont have Final Score Year 1");
                    continue;
                }
                //if year = 1, then only check with pa rating matrix
                if(
                        (paRatingFinalScore1 >= paFinalRangeFromMatrix && paRatingFinalScore1 <= paFinalRangeToMatrix)
                        || (paRatingTotalScore1 >= paFinalRangeFromMatrix && paRatingTotalScore1 <= paFinalRangeToMatrix)
                ){
                    listRatingModelIdYear1.add(singleRatingModel.getId());
                }
            }else if (singleRatingModel.getYearBefore() == 2){
                //if year = 2, check the matrix
                List<LoanRatingModelMatrixDetailDTO> listRatingModelMatrix = loRatingModelMatrixRepository.findByLoanRatingModel(loanRatingModelId);
                if(listRatingModelMatrix.isEmpty()){
                    log.info(identifierLog + functionIdentifier + " Rating Model Matrix Not Found");
//                    throw new CustomGenericException("Rating Model Matrix Not Found");
                    continue;
                }

                if(paRatingYear1 == null){
                    log.info(identifierLog + functionIdentifier + " Employee dont have Final Score Year 1");
//                    throw new CustomGenericException("Employee dont have Final Score Year 1");
                    continue;
                }

                if(paRatingYear2 == null){
                    log.info(identifierLog + functionIdentifier + " Employee dont have Final Score Year 2");
//                    throw new CustomGenericException("Employee dont have Final Score Year 2");
                    continue;
                }

                for(LoanRatingModelMatrixDetailDTO singleMatrix : listRatingModelMatrix){
                    if(
                            singleMatrix.getPaRatingName1().equalsIgnoreCase(paRatingYear1) &&
                                    singleMatrix.getPaRatingName2().equalsIgnoreCase(paRatingYear2)
                    ){
                        if(singleMatrix.getResult().equals(LoRatingModelMatrix.RESULT_BATAS_ATAS)){
                            listRatingModelIdYear2Atas.add(singleRatingModel.getId());
                        }else {
                            listRatingModelIdYear2Bawah.add(singleRatingModel.getId());
                        }

                        break;
                    }
                }
            }


        }
        responses.setRatingModelIdYearNull(listRatingModelIdYearNull);
        responses.setRatingModelIdYear1(listRatingModelIdYear1);
        responses.setRatingModelIdYear2Atas(listRatingModelIdYear2Atas);
        responses.setRatingModelIdYear2Bawah(listRatingModelIdYear2Bawah);

        log.info(identifierLog + functionIdentifier + " Response From Check Matrix : " + responses.toString());
        return responses;

    }

    @Transactional
    public PlafondBracketListDTO checkLoanPlafondBracketBasedOnEmployment(String identifierLog,String employmentId, String loanCategoryId, String loanTypeId, Double totalLoan,VwEmpAssignment employeeData, Date requestDate, Boolean isCheckRequirement){
        PlafondBracketListDTO response = null;
        String functionIdentifier = "[checkLoanPlafondBracketBasedOnEmployment] ";

        //-----------------------------------------------
        //Get Latest Rating of employment
        //-----------------------------------------------
//        List<LoanRatingEmploymentDTO> listRatingEmployment = loCriteriaRepository.findRatingByEmploymentId(employmentId);
        LoanRatingModelCheckResultDTO checkRatingModelMatrixResult = checkRatingModelMatrix(identifierLog,loanCategoryId,employmentId,LocalDate.now());
        //-----------------------------------------------

        //-----------------------------------------------
        //Get Job Family Based on title id
        //-----------------------------------------------
        Optional<JobTitle> jobTitle = jobTitleRepository.findById(employeeData.getJobTitleId());
        if(!jobTitle.isPresent()){
            log.info(identifierLog + functionIdentifier + " Job Title Not Found");
            throw new CustomGenericException("Job Title Not Found");
        }

        Optional<JobFamily> jobFamily = jobFamilyRepository.findById(jobTitle.get().getJobFamilyId());
        if(!jobFamily.isPresent()){
            log.info(identifierLog + functionIdentifier + " Job Family Not Found");
            throw new CustomGenericException("Job Family Not Found");
        }
        //-----------------------------------------------

        List<PlafondBracketListDTO>  isThereAnyPlafondBracket = loPlafondBracketRepository.findByLoanCategoryId(loanCategoryId);
        if(isThereAnyPlafondBracket.size() == 0){
            response = new PlafondBracketListDTO();
            response.setThereAnyPlafondBracket(false);
            log.info(identifierLog + functionIdentifier + " No Plafond Bracked Found For This Loan Category");
            log.info(identifierLog + functionIdentifier + " All Request Will Be Treated as Eligible Based On Plafond Bracket ");
        }

        List<PlafondBracketListDTO> loEligiblePlafondBracketBasedOnMinMaxValueList = new ArrayList<>();

        if(isThereAnyPlafondBracket.size() > 0) {
            List<String> listKosong = new ArrayList<>();
            listKosong.add("");
            if (isCheckRequirement){
                loEligiblePlafondBracketBasedOnMinMaxValueList = loPlafondBracketRepository.findMinMaxValueByLoanCategoryId(LocalDate.now(), loanCategoryId
                    , employeeData.getOrganizationId()
                    , employeeData.getOrganizationGroupId()
                    , employeeData.getPositionId()
                    , employeeData.getPositionLevelId()
                    , employeeData.getJobTitleId()
                    , jobFamily.get().getId()
                    , employeeData.getWorkLocationType()
                    , employeeData.getCompanyOfficeId()
                    , employeeData.getGradeId()
                    , employeeData.getEmployeeStatus()
//                    , listRatingEmployment.size() > 0 ? listRatingEmployment.get(0).getScore() : null
                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYearNull()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear1()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Atas()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Bawah()
            );  
            } else {
                loEligiblePlafondBracketBasedOnMinMaxValueList = loPlafondBracketRepository.findAllWithMinMaxValueByLoanCategoryId(LocalDate.now(), loanCategoryId
                        , employeeData.getOrganizationId()
                        , employeeData.getOrganizationGroupId()
                        , employeeData.getPositionId()
                        , employeeData.getPositionLevelId()
                        , employeeData.getJobTitleId()
                        , jobFamily.get().getId()
                        , employeeData.getWorkLocationType()
                        , employeeData.getCompanyOfficeId()
                        , employeeData.getGradeId()
                        , employeeData.getEmployeeStatus()
    //                    , listRatingEmployment.size() > 0 ? listRatingEmployment.get(0).getScore() : null
    
                        , totalLoan
                        ,checkRatingModelMatrixResult.getRatingModelIdYearNull().size()
                        ,checkRatingModelMatrixResult.getRatingModelIdYearNull().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYearNull()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear1().size()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear1().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear1()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().size()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Atas()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().size()
                        ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Bawah()
                );
            }
            log.info(identifierLog + functionIdentifier + " loEligiblePlafondBracketBasedOnMinMaxValueList.size = " + loEligiblePlafondBracketBasedOnMinMaxValueList.size());

        Calendar cal = Calendar.getInstance();
        cal.setTime(requestDate);
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String requestDateUsed = sdf.format(cal.getTime());
        log.info(identifierLog + functionIdentifier + "requestDateUsed = " + requestDateUsed.toString());

        log.info(identifierLog + functionIdentifier + "totalLoan : " + totalLoan);
        log.info(identifierLog + functionIdentifier + "cal.get(Calendar.YEAR) : " + cal.get(Calendar.YEAR));
        log.info(identifierLog + functionIdentifier + "cal.get(Calendar.MONTH) : " + (cal.get(Calendar.MONTH) + 1));
        log.info(identifierLog + functionIdentifier + "employmentId : " + employmentId);
        log.info(identifierLog + functionIdentifier + "employeeData.getOrganizationId() : " + employeeData.getOrganizationId());
        log.info(identifierLog + functionIdentifier + "employeeData.getOrganizationGroupId() : " + employeeData.getOrganizationGroupId());
        log.info(identifierLog + functionIdentifier + "employeeData.getPositionId() : " + employeeData.getPositionId());
        log.info(identifierLog + functionIdentifier + "employeeData.getPositionLevelId() : " + employeeData.getPositionLevelId());
        log.info(identifierLog + functionIdentifier + "employeeData.getJobTitleId() : " + employeeData.getJobTitleId());
        log.info(identifierLog + functionIdentifier + "jobFamily.get().getId() : " + jobFamily.get().getId());
        log.info(identifierLog + functionIdentifier + "employeeData.getWorkLocationType() : " + employeeData.getWorkLocationType());
        log.info(identifierLog + functionIdentifier + "employeeData.getCompanyOfficeId() : " + employeeData.getCompanyOfficeId());
        log.info(identifierLog + functionIdentifier + "employeeData.getGradeId() : " + employeeData.getGradeId());
        log.info(identifierLog + functionIdentifier + "employeeData.getEmployeeStatus() : " + employeeData.getEmployeeStatus());
//        log.info(identifierLog + functionIdentifier + "employeeData.listRatingEmployment() : " + (listRatingEmployment.size() > 0 ? listRatingEmployment.get(0).getScore() : null));
        log.info(identifierLog + functionIdentifier + "checkRatingModelMatrixResult.getResult() : " + checkRatingModelMatrixResult.toString());

        List<PlafondBracketListDTO> loEligiblePlafondBracketBasedOnElementNameList = new ArrayList<>();
            loEligiblePlafondBracketBasedOnElementNameList = loPlafondBracketRepository.findAllWithElementNameByLoanCategoryId(LocalDate.now(), loanCategoryId
                    , employeeData.getOrganizationId()
                    , employeeData.getOrganizationGroupId()
                    , employeeData.getPositionId()
                    , employeeData.getPositionLevelId()
                    , employeeData.getJobTitleId()
                    , jobFamily.get().getId()
                    , employeeData.getWorkLocationType()
                    , employeeData.getCompanyOfficeId()
                    , employeeData.getGradeId()
                    , employeeData.getEmployeeStatus()
//                    , listRatingEmployment.size() > 0 ? listRatingEmployment.get(0).getScore() : null

                    , totalLoan

                    , cal.get(Calendar.YEAR)
                    , cal.get(Calendar.MONTH) + 1
                    , employmentId
                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYearNull()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear1()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Atas()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Bawah()
                    ,applicationPropertiesService.getValueSecret()
            );
            log.info(identifierLog + functionIdentifier + " loEligiblePlafondBracketBasedOnElementNameList.size = " + loEligiblePlafondBracketBasedOnElementNameList.size());

            if(loEligiblePlafondBracketBasedOnMinMaxValueList.size() > 0){
                //get the first eligible plafond
                log.info(identifierLog + functionIdentifier + " Use Min Max Value ");
                response = loEligiblePlafondBracketBasedOnMinMaxValueList.get(0);
                response.setThereAnyPlafondBracket(true);
            } else if (loEligiblePlafondBracketBasedOnElementNameList.size() > 0) {
                //get the first eligible plafond
                log.info(identifierLog + functionIdentifier + " Use Element Name Plafond Bracket ");
                response = loEligiblePlafondBracketBasedOnElementNameList.get(0);
                response.setThereAnyPlafondBracket(true);
            }
        }

        log.info(identifierLog + functionIdentifier + " response = " + (response == null ? "null" : response.toString()));
        return response;

    }

    @Transactional
    public Boolean checkLoanCriteriaBasedOnEmployment(String identifierLog,String employmentId,String loanTypeId,String loanCategoryId, VwEmpAssignment employeeData){
        Boolean response = false;
        String functionIdentifier = "[checkLoanCriteriaBasedOnEmployment] ";

        //-----------------------------------------------
        //Get Lenght of Service
        //-----------------------------------------------
        Integer lengthOfService = 0;
        Optional<Employment> employmentData = employmentRepository.findById(employmentId);
        LocalDate effectiveDate = Instant.ofEpochMilli(employmentData.get().getEffectiveDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        if(!employmentData.isPresent()){
            log.info(identifierLog + functionIdentifier + "Employment Not Found");
            throw new CustomGenericException("Employment Not Found");
        }
        long monthsBetween = ChronoUnit.MONTHS.between(
                effectiveDate.withDayOfMonth(1),
                LocalDate.now().withDayOfMonth(1));

        lengthOfService = Integer.parseInt(String.valueOf(monthsBetween));
        //-----------------------------------------------

        //-----------------------------------------------
        //Get Latest Rating of employment
        //-----------------------------------------------
//        List<LoanRatingEmploymentDTO> listRatingEmployment = loCriteriaRepository.findRatingByEmploymentId(employmentId);
        LoanRatingModelCheckResultDTO checkRatingModelMatrixResult = checkRatingModelMatrix(identifierLog,loanCategoryId,employmentId,LocalDate.now());
        log.info("checkRatingModelMatrixResult : " + checkRatingModelMatrixResult.toString());
        //-----------------------------------------------

        //-----------------------------------------------
        //Get Job Family Based on title id
        //-----------------------------------------------
        Optional<JobTitle> jobTitle = jobTitleRepository.findById(employeeData.getJobTitleId());
        if(!jobTitle.isPresent()){
            log.info(identifierLog + functionIdentifier + "Job Title Not Found");
            throw new CustomGenericException("Job Title Not Found");
        }

        Optional<JobFamily> jobFamily = jobFamilyRepository.findById(jobTitle.get().getJobFamilyId());
        if(!jobFamily.isPresent()){
            log.info(identifierLog + functionIdentifier + "Job Family Not Found");
            throw new CustomGenericException("Job Family Not Found");
        }
        //-----------------------------------------------

        List<LoanCriteriaListDTO> isThereAnyLoCriteria = loCriteriaRepository.findByLoanTypeId(loanTypeId);
        if(isThereAnyLoCriteria.size() == 0){
            log.info(identifierLog + functionIdentifier + "No Loan Criteria Found For This Loan Type ");
            log.info(identifierLog + functionIdentifier + "All Request Will Be Eligible Based On Loan Criteria ");
            response = true;
        }

        if(isThereAnyLoCriteria.size() > 0) {
            List<String> listKosong = new ArrayList<>();
            listKosong.add("");

            List<LoanCriteriaListDTO> loCriteriaList = loCriteriaRepository.findAllByLoanTypeId(LocalDate.now(), loanTypeId
                    , employeeData.getOrganizationId()
                    , employeeData.getOrganizationGroupId()
                    , employeeData.getPositionId()
                    , employeeData.getPositionLevelId()
                    , employeeData.getJobTitleId()
                    , jobFamily.get().getId()
                    , employeeData.getWorkLocationType()
                    , employeeData.getCompanyOfficeId()
                    , employeeData.getGradeId()
                    , employeeData.getEmployeeStatus()
//                    , listRatingEmployment.size() > 0 ? listRatingEmployment.get(0).getScore() : null

                    , lengthOfService

                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYearNull().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYearNull()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear1().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear1()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Atas().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Atas()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().size()
                    ,checkRatingModelMatrixResult.getRatingModelIdYear2Bawah().isEmpty() ? listKosong : checkRatingModelMatrixResult.getRatingModelIdYear2Bawah()
            );
            log.info(identifierLog + functionIdentifier + "lengthOfService = " + lengthOfService);
            log.info(identifierLog + functionIdentifier + "loCriteriaList.size() = " + loCriteriaList.size());
            if(loCriteriaList.size() > 0){
                log.info(identifierLog + functionIdentifier + "Eligible Based On Loan Criteria ");
                response = true;
            }
        }

        log.info(identifierLog + functionIdentifier +  "result = " + response);
        return response;
    }

    @Transactional
    public LoLoanRequest createRequest(String identifierLog,
                                       String module, String type,
                                       String employmentId, String categoryId, String requestEmploymentId,
                                        LoanRequestDTO request, Boolean isAdmin,
                                        VwEmpAssignment employeeData
    ) {
        String functionIdentifier = "[createRequest] ";
        //-------------------------------------------------
        // Check Mandatory Field
        //-------------------------------------------------
        LoLoanCategory loLoanCategory =  repositoryUtils.checkMandatoryFieldWithReturn("Loan Category", LoLoanCategoryRepository.class,request.getLoanCategoryId());
        LoLoanType     loLoanType     =  repositoryUtils.checkMandatoryFieldWithReturn("Loan Type", LoLoanTypeRepository.class,request.getLoanTypeId());
        //-------------------------------------------------

        //-------------------------------------------------
        // Check down payment mandatory
        //-------------------------------------------------
        if(loLoanType.getDownPayment() != null && loLoanType.getDownPayment()){
            if(request.getDownPayment() == null){
                log.info(identifierLog + functionIdentifier + "Down Payment Must be filled !");
                throw new CustomGenericException("Down Payment Must be filled !");
            }
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Check If start and end date range still eligible
        //-------------------------------------------------
        LocalDate currentDate = LocalDate.now();
        if(currentDate.isBefore(loLoanCategory.getStartDate().toLocalDate()) ||
                currentDate.isAfter(loLoanCategory.getEndDate().toLocalDate())
        ) {
            log.info(identifierLog + functionIdentifier + "Loan Category Out of Range of Start Date ("  + loLoanCategory.getStartDate().toString() +  ") and End Date (" + loLoanCategory.getEndDate().toString() + ")");
            throw new CustomGenericException("Loan Category Out of Range of Start Date ("  + loLoanCategory.getStartDate().toString() +  ") and End Date (" + loLoanCategory.getEndDate().toString() + ")");
        }

        if(currentDate.isBefore(loLoanType.getStartDate().toLocalDate()) ||
           currentDate.isAfter(loLoanType.getEndDate().toLocalDate())
        ) {
            log.info(identifierLog + functionIdentifier + "Loan Type Out of Range of Start Date ("  + loLoanType.getStartDate().toLocalDate().toString() +  ") and End Date (" + loLoanType.getEndDate().toLocalDate().toString() + ")");
            throw new CustomGenericException("Loan Type Out of Range of Start Date ("  + loLoanType.getStartDate().toLocalDate().toString() +  ") and End Date (" + loLoanType.getEndDate().toLocalDate().toString() + ")");
        }

        //-------------------------------------------------

        //-------------------------------------------------
        // Check discipline
        //-------------------------------------------------
        List<LoanDisciplineListDTO> loanDisciplineListDTOS = loDisciplineRepository.checkLoanDiscipline(
                LocalDate.now()
                ,loLoanType.getId(),employmentId);
        if(loanDisciplineListDTOS != null && loanDisciplineListDTOS.size() > 0){
                log.info(identifierLog + functionIdentifier + "Employee Not Eligible Based On Discipline Requirement !");
                throw new CustomGenericException("Employee Not Eligible Based On Discipline Requirement !");
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Check Loan Criteria based on Employment Id
        //-------------------------------------------------
        Boolean isEligibleCriteria = checkLoanCriteriaBasedOnEmployment(identifierLog,employmentId,request.getLoanTypeId(),request.getLoanCategoryId(), employeeData);
        if(!isEligibleCriteria){
            log.info(identifierLog + functionIdentifier + "Employee Doesn't Meet Loan Criteria Requirement");
            throw new CustomGenericException("Employee Doesn't Meet Loan Criteria Requirement");
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Check Loan Plafond Bracket based on Employment Id
        //-------------------------------------------------

//        PlafondBracketListDTO plafondBracketFound = checkLoanPlafondBracketBasedOnEmployment(employmentId,request.getLoanCategoryId(),request.getAmount(),employeeData,request.getRequestDate());
//        if(plafondBracketFound == null){
//            throw new CustomGenericException("Employee Doesn't Meet Loan Plafond Requirement");
//        }
        LoanCheckEligibilityDTO isEligibleLoan = checkEligibilityLoanAmount(identifierLog,employmentId,request.getAmount().toString(),request.getTenor().toString(),request.getLoanCategoryId(),request.getLoanTypeId(),request.getDownPayment());
        if(!isEligibleLoan.getEligible()){
            log.info(identifierLog + functionIdentifier + isEligibleLoan.getMessage());
            throw new CustomGenericException(isEligibleLoan.getMessage());
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Check Loan Tenor based on min max tenor in loan type table
        //-------------------------------------------------
        Boolean isEligibleTenor = false;
        log.info(identifierLog + functionIdentifier + "Tenor = " + (request.getTenor() == null ? 0 : request.getTenor()));
        log.info(identifierLog + functionIdentifier + "Min Tenor = " + (loLoanType.getMinTenor() == null ? 0 : loLoanType.getMinTenor()));
        log.info(identifierLog + functionIdentifier + "Max Tenor = " + (loLoanType.getMaxTenor() == null ? 0 : loLoanType.getMaxTenor()));

        if(request.getTenor() >=  loLoanType.getMinTenor() &&
           request.getTenor() <=  loLoanType.getMaxTenor()
        ){
            isEligibleTenor = true;
        }else {
            log.info(identifierLog + functionIdentifier + "Employee Doesn't Meet Min Max Tenor Requirement");
            throw new CustomGenericException("Employee Doesn't Meet Min Max Tenor Requirement");
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Set Data
        //-------------------------------------------------
        LoLoanRequest requestData = new LoLoanRequest();
        requestData.setEmploymentId(employmentId);
        requestData.setCategoryId(categoryId);

        String requestNo = talentsTransactionNoService.getTransactionNextVal(module,type);
        requestData.setRequestNo(requestNo);

        requestData.setRequestDate(LocalDateTime.now());
        requestData.setRemark(request.getRemark());

//        requestData.setEmploymentId(request.getEmploymentId());
        requestData.setLoanCategoryId(request.getLoanCategoryId());
        requestData.setLoanTypeId(request.getLoanTypeId());
        requestData.setBrandId(request.getBrandId());
        requestData.setLoanDate(request.getLoanDate());
        requestData.setAmount(request.getAmount());
        requestData.setTenor(request.getTenor());
        requestData.setDownPayment(request.getDownPayment());
        requestData.setMonthlyInstallment(isEligibleLoan.getSimulation().getMonthlyInstallment());
        requestData.setAmountOtr(request.getAmountOtr());

        //in request, needsynct estar will be null first, it will be true when final approve, and become false when successfull sync to estar
        LoLoanRequest response = requestRepository.save(requestData);
        //-------------------------------------------------

        log.info( identifierLog + functionIdentifier + (requestData == null ? "null" : requestData.toString()));
        return response;
    }

    @Transactional
    public Page<LoanRequestNeedApprovalDTO> needApprovalList(String approverEmployeeId, String status,
                                                             HttpServletRequest request, String requestDateStart, String requestDateEnd, String ppl,
                                                             PageRequest pageable, String loanTypeId, Jwt jwt) {
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
        Page<LoanRequestNeedApprovalDTO> resultOpt = requestRepository.findNeedApprovalBy("%" + approverEmployeeId + "%",
                status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate, "%" + ppl + "%",loanTypeId,
                pageable,secret);
        //------------------------------------------------------------------------

        return resultOpt;

    }

    @Transactional
    public Page<LoanMyRequestDTO> findMyRequestByEmployeeAndModule(String employmentRequest, String status,
                                                              HttpServletRequest request, String requestDateStart, String requestDateEnd, PageRequest pageable,String requestNo,
                                                                   String loanTypeId, Jwt jwt) {
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
        Page<LoanMyRequestDTO> findByEmployeeAndStatus = requestRepository.findByEmployeeAndStatus(
                employmentRequest, status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate,
                pageable,'%'+requestNo+'%',loanTypeId,null, secret);
        List<LoanMyRequestDTO> content = findByEmployeeAndStatus.getContent();
        //------------------------------------------------------------------------

        return findByEmployeeAndStatus;
    }

    @Transactional
    public Optional<LoanMyRequestDTO> findMyRequestByEmployeeAndId(String empRequest, String dataApprovalId,
                                                              HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanMyRequestDTO> resultOpt = requestRepository.findByEmployeeAndId(empRequest, dataApprovalId,
                Utils.getServerName(http, request, jwt, secret), secret);
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Detail Attachments
        //------------------------------------------------------------------------
        if(resultOpt.isPresent()) {
            List<LoanRequestAttachmentNeedApprovalDTO> detailAttachment = requestRepository.getDetailAttachments(resultOpt.get().getDataApprovalId());

            detailAttachment.stream()
                    .forEach(singleAttachment -> {
                        if(singleAttachment.getPath() != null){
                            singleAttachment.setPath(singleAttachment.getPath() == null ? null : (Utils.getServerName(http, request, jwt, secret) + singleAttachment.getPath() + "&token="  + JwtWrapperService.createPathJwt(singleAttachment.getPath(),secret)));
                        }
                    });

            resultOpt.get().setAttachmentDetails(detailAttachment);
        }
        //------------------------------------------------------------------------

        return resultOpt;
    }

    @Transactional
    public Optional<LoanMyRequestDTO> findNeedApproval2ById(String approverEmployeeId, String dataApprovalId,
                                                               HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanMyRequestDTO> resultOpt = requestRepository.findNeedApprovalById(
                "%" + approverEmployeeId + "%", dataApprovalId, Utils.getServerName(http, request, jwt, secret), secret);
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Detail Attachments
        //------------------------------------------------------------------------
        if(resultOpt.isPresent()) {
            List<LoanRequestAttachmentNeedApprovalDTO> detailAttachment = requestRepository.getDetailAttachments(resultOpt.get().getDataApprovalId());

            detailAttachment.stream()
            .forEach(singleAttachment -> {
                        if(singleAttachment.getPath() != null){
                            singleAttachment.setPath( Utils.getServerName(http, request, jwt, secret) + singleAttachment.getPath()+"&token=" + JwtWrapperService.createPathJwt(singleAttachment.getPath(),secret));
                        }
                    });

            resultOpt.get().setAttachmentDetails(detailAttachment);

            resultOpt.get().setLoanFeedbackFleetListDTOS(getFeedbackFleetListByRequestNo(resultOpt.get().getRequestNo()));
            resultOpt.get().setLoanRequestDetail( getLoanRequestDetailByRequestNo(resultOpt.get().getRequestNo()) );
        }
        //------------------------------------------------------------------------

        return resultOpt;
    }

    @Transactional
    public Page<LoanHistoryDTO> findHistoryLoan(String employeeId, String requestNo, String nikName,
                                                String loanStatus,String loanTypeId,HttpServletRequest request, String requestDateStart, String requestDateEnd,
                                                                   PageRequest pageable, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDateStartDate = null;
        Date requestDateEndDate = null;
        if (requestDateStart != null) {
            try {
                requestDateStartDate = sdf.parse(requestDateStart);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (requestDateEnd != null) {
            try {
                requestDateEndDate = sdf.parse(requestDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Page<LoanHistoryDTO> resultOpt = requestRepository.findHistoryLoan(employeeId,requestNo,'%' + (nikName == null? "" : nikName) + '%',
                loanStatus,loanTypeId, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate,
                pageable,secret);
        return resultOpt;
    }

    public static Map<String, Object> convertDtoToMap(Object dto) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

        Class<?> dtoClass = dto.getClass();
        Field[] fields = dtoClass.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object fieldValue = field.get(dto);

            if (fieldValue instanceof LocalDate) {
                fieldValue = ((LocalDate) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } else if (fieldValue instanceof LocalDateTime) {
                fieldValue = ((LocalDateTime) fieldValue).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            map.put(fieldName, fieldValue);
        }

        return map;
    }

    @Transactional
    public LoanHistoryDetailDTO findHistoryLoanDetail(String loanRequestId,HttpServletRequest request, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        LoanHistoryDetailDTO resultOpt = requestRepository.findHistoryLoanDetail(loanRequestId,Utils.getServerName(http, request, jwt, secret),secret);
        return resultOpt;
    }

    @Transactional
    public LoanHistoryDetailDTO findHistoryLoanDetailReport(String loanRequestId,HttpServletRequest request, Jwt jwt) throws IllegalAccessException {
        LoanHistoryDetailDTO response = new LoanHistoryDetailDTO();
        String http = env.getProperty("talents.protocol");
        LoanHistoryDetailDTO header = requestRepository.findHistoryLoanDetail(loanRequestId,Utils.getServerName(http, request, jwt, secret),secret);
//        response = convertDtoToMap(header);
        List<String> approvers = new ArrayList<>();
       List<LoanHistoryDetailApproverDTO> approverDTOList =  requestRepository.findApproverByRequestId(header.getRequestNo());
        for(LoanHistoryDetailApproverDTO singleData : approverDTOList){
//            String keyApprover = "Approver" + singleData.getApproverLevel();
            approvers.add(singleData.getApproverEmployeeName());

        }
        response.setApprovers(approvers);

        return response;
    }

    @Transactional
    public Page<LoanRequestHistoryApprovalDTO> findHistoryApproval(String approverEmployeeId, String status,
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
        Page<LoanRequestHistoryApprovalDTO> resultOpt = requestRepository.findHistoryApproval(approverEmployeeId,
                status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate, "%" + ppl + "%",
                pageable,secret);
        return resultOpt;
    }

    @Transactional
    public Optional<LoanRequestHistoryApprovalDTO> findHistoryApprovalByIdAndEmployee(String approverEmployeeId,
                                                                                      String dataApprovalDetailId, HttpServletRequest request, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        Optional<LoanRequestHistoryApprovalDTO> resultOpt = requestRepository.findHistoryApprovalByIdAndEmployee(
                approverEmployeeId, dataApprovalDetailId, Utils.getServerName(http, request, jwt, secret),secret);

        //------------------------------------------------------------------------
        //Get Detail Attachments
        //------------------------------------------------------------------------
        if(resultOpt.isPresent()) {
            List<LoanRequestAttachmentNeedApprovalDTO> detailAttachment = requestRepository.getDetailAttachments(resultOpt.get().getDataApprovalId());

            detailAttachment.stream()
                    .forEach(singleAttachment -> {
                        if(singleAttachment.getPath() != null){
                            singleAttachment.setPath( Utils.getServerName(http, request, jwt, secret) + singleAttachment.getPath());
                        }
                    });

            resultOpt.get().setAttachmentDetails(detailAttachment);
            resultOpt.get().setLoanFeedbackFleetListDTOS(getFeedbackFleetListByRequestNo(resultOpt.get().getRequestNo()));
            resultOpt.get().setLoanRequestDetail( getLoanRequestDetailByRequestNo(resultOpt.get().getRequestNo()) );

        }
        //------------------------------------------------------------------------

        return resultOpt;
    }


    @Transactional
    public List<LoanRequestHistoryApprovalDTO> findHistoryApprovalByApprovalId(String dataApprovalId,
                                                                                     HttpServletRequest request, Jwt jwt) {
        String http = env.getProperty("talents.protocol");
        Sort sort = Sort.by(Sort.Direction.DESC, "actionDate");
        List<LoanRequestHistoryApprovalDTO> resultOpt = requestRepository
                .findHistoryApprovalByDataApprovalId(dataApprovalId, Utils.getServerName(http, request, jwt, secret), sort,secret);
        return resultOpt;
    }

    @Transactional
    public Page<LoanMyRequestDTO> findByEmployeeAndStatusAdmin(HttpServletRequest request,String requestNo,String loanTypeId,String loanCategoryId,String status,String requestDateStart,String requestDateEnd,String ppl,PageRequest pageRequest, Jwt jwt){
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
        Page<LoanMyRequestDTO> findByEmployeeAndStatusAdmin = requestRepository.findByEmployeeAndStatusAdmin(
                status,requestNo,loanTypeId,loanCategoryId, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate, "%" + ppl + "%", pageRequest, secret);
        List<LoanMyRequestDTO> content = findByEmployeeAndStatusAdmin.getContent();
        //------------------------------------------------------------------------

        return findByEmployeeAndStatusAdmin;
    }

    @Transactional
    public Optional<LoanMyRequestDTO> findMyRequestByEmployeeAndIdAdmin(String dataApprovalId,
                                                                   HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanMyRequestDTO> resultOpt = requestRepository.findByEmployeeAndIdAdmin( dataApprovalId,
                Utils.getServerName(http, request, jwt, secret), secret);
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Detail Attachments
        //------------------------------------------------------------------------
        if(resultOpt.isPresent()) {
            List<LoanRequestAttachmentNeedApprovalDTO> detailAttachment = requestRepository.getDetailAttachments(resultOpt.get().getDataApprovalId());

            detailAttachment.stream()
                    .forEach(singleAttachment -> {
                        if(singleAttachment.getPath() != null){
                            singleAttachment.setPath( Utils.getServerName(http, request, jwt, secret) + singleAttachment.getPath());
                        }
                    });

            resultOpt.get().setAttachmentDetails(detailAttachment);

            resultOpt.get().setLoanFeedbackFleetListDTOS(getFeedbackFleetListByRequestNo(resultOpt.get().getRequestNo()));
            resultOpt.get().setLoanRequestDetail( getLoanRequestDetailByRequestNo(resultOpt.get().getRequestNo()) );
        }
        //------------------------------------------------------------------------

        return resultOpt;
    }

    @Transactional
    public Boolean checkEligibilityOccurenceLimit(String employmentId, String requestedLoanCategoryId){
        Boolean response = true;

        //initialize variable
        Integer occurenceLimitUsed = null;
        List<String> listCategoryCheck = new ArrayList<>();
        List<String> listSelfCategoryCheck = new ArrayList<>();

        //logic
        //-------------------------------------------------
        // GET listcategoryname in talents parameter (if any)
        //-------------------------------------------------
        Optional<LoLoanCategory> loanCategoryFound = loLoanCategoryRepository.findById(requestedLoanCategoryId);
        if(!loanCategoryFound.isPresent()){
            throw  new CustomGenericException("Loan Category Not Found !");
        }
        occurenceLimitUsed = loanCategoryFound.get().getOccurenceLimit();
        listSelfCategoryCheck.add(loanCategoryFound.get().getName());

        List<TalentsParameter> talentsParametersList = talentsParameterRepository.findAllOccurenceGroupByCategoryName(loanCategoryFound.get().getName());
        for(TalentsParameter singleTalentsParam : talentsParametersList){
            String valueFound =  singleTalentsParam.getValue();
            String[] listString = valueFound.split(";");
            for(String singleString : listString){
                //add other than itself category name
                if(!singleString.equals(loanCategoryFound.get().getName())) {
                    listCategoryCheck.add(singleString);
                }
            }
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // GET count of request based on category name list
        //-------------------------------------------------
        //only check if there are any link in listCategoryCheck (its get from talent_param)
        if(listCategoryCheck.size() > 0) {
            List<LoanCategoryRequestDTO> foundLoanCategory = requestRepository.countLoanCategoryNameRequest(employmentId, listCategoryCheck);
            if (foundLoanCategory.size() > 0) {
                response = false;
                throw new CustomGenericException("You already have other loan request with name '" + foundLoanCategory.get(0).getName() + "'");
            }
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // check self category name occurence limit
        //-------------------------------------------------
        List<LoanCategoryRequestDTO> selfLoanCategoryRequestCount = requestRepository.countLoanCategoryNameRequest(employmentId,listSelfCategoryCheck);
        if(selfLoanCategoryRequestCount.size() > 0){
            //its already in limit request
            if(occurenceLimitUsed <= selfLoanCategoryRequestCount.get(0).getCountRequest()) {
                response = false;
            }
        }

        return response;
    }

    @Transactional
    public LoanCheckEligibilityDTO checkEligibilityLoanAmount(String identifierLog,String employmentId,String loanAmount,String loanTenor,String loanCategoryId,String loanTypeId,Double downPayment) {
        //initialize variable
        LoanCheckEligibilityDTO response = new LoanCheckEligibilityDTO();
        response.setMessage("");
        response.setEligible(false);

        Double loanAmountUsed = Double.parseDouble(loanAmount);
        if(loanAmountUsed == null){loanAmountUsed = Double.valueOf(0);}
        Integer loanTenorUsed = Integer.parseInt(loanTenor);
        Double minimalLoanUsed = (double) 0;
        Double maximunLoanUsed = (double) 0;
        Boolean isThereAnyPlafondBracket = true;
        Date dateUsed = new Date();
        String identifierFunction = "[checkEligibilityLoanAmount] ";
        LocalDate dateUsedLocalDate = LocalDate.now();

        //logic

        Optional<VwEmpAssignment> employeeViewOpt = vwEmpAssignmentRepository.findByEmploymentId(employmentId);
        if (!employeeViewOpt.isPresent()) {
            log.info(identifierLog + identifierFunction + "Something went wrong,please contact Admin - Employee Detail Not Found");
            throw new CustomGenericException("Something went wrong,please contact Admin - Employee Detail Not Found");
        }

        Optional<LoLoanCategory> loLoanCategory = loLoanCategoryRepository.findById(loanCategoryId);
        Optional<LoLoanType> loLoanType = loLoanTypeRepository.findById(loanTypeId);
        if(!loLoanCategory.isPresent()){
            log.info(identifierLog + identifierFunction + "Loan Category not Found !");
            throw new CustomGenericException("Loan Category not Found !");
        }

        if(loLoanCategory.get().getSimulation()) {

            LoanCheckDsrDTO checkDsrEligible = checkDsrEligible("", loanTypeId, loanAmountUsed, downPayment, loanTenorUsed, employeeViewOpt.get().getEmployeeId(),Double.valueOf(0),Double.valueOf(0),null);
            if (!checkDsrEligible.getEligible()) {
                log.info(identifierLog + identifierFunction + "Employee Doesn't Meet Loan DSR Requirement");
                throw new CustomGenericException("Employee Doesn't Meet Loan DSR Requirement");
            }
        }else{
            log.info(identifierLog + identifierFunction + "Bypass DSR Requirement Because Non Simulation");
        }
        //-------------------------------------------------
        // Check Loan Plafond Bracket based on Employment Id
        //-------------------------------------------------
        Boolean checkOccurenceLimitELigible = checkEligibilityOccurenceLimit(employmentId,loanCategoryId);
        if(!checkOccurenceLimitELigible){
            log.info(identifierLog + identifierFunction + "Employee Doesn't Meet Loan Occurence Limit Requirement");
            throw new CustomGenericException("Employee Doesn't Meet Loan Occurence Limit Requirement");
        }
        //-------------------------------------------------

        //-------------------------------------------------
        // Check Loan Plafond Bracket based on Employment Id
        //-------------------------------------------------

        PlafondBracketListDTO plafondBracketFound = checkLoanPlafondBracketBasedOnEmployment(identifierLog,employmentId,loanCategoryId,loanTypeId,loanAmountUsed,employeeViewOpt.get(),dateUsed, false);
//        if(plafondBracketFound == null ||
//           (plafondBracketFound != null && plafondBracketFound.getThereAnyPlafondBracket() ) //if no plafond bracket in data, then all request will be eligible based on plafond bracket
//        ){
//            log.info(identifierLog + identifierFunction + "Employee Doesn't Meet Loan Plafond Requirement");
//            throw new CustomGenericException("Employee Doesn't Meet Loan Plafond Requirement");
//        }
        if(plafondBracketFound != null && plafondBracketFound.getThereAnyPlafondBracket()){
            if(plafondBracketFound.getId() == null){
                log.info(identifierLog + identifierFunction + "Employee Doesn't Meet Loan Plafond Requirement");
                throw new CustomGenericException("Employee Doesn't Meet Loan Plafond Requirement");
            }
        }else{
            if(plafondBracketFound == null) {
                log.info(identifierLog + identifierFunction + "Employee Doesn't Meet Loan Plafond Requirement");
                throw new CustomGenericException("Employee Doesn't Meet Loan Plafond Requirement");
            }
        }

        minimalLoanUsed = plafondBracketFound.getMinValue() == null ? 0 : plafondBracketFound.getMinValue();
        maximunLoanUsed = !plafondBracketFound.getThereAnyPlafondBracket() ? 0 : (plafondBracketFound.getMaxValue() == null ? (plafondBracketFound.getMultiplier() * plafondBracketFound.getTotalElementValue()) : plafondBracketFound.getMaxValue()) ;
        isThereAnyPlafondBracket = plafondBracketFound.getThereAnyPlafondBracket();
        if(minimalLoanUsed == null){loanAmountUsed = Double.valueOf(0);}
        if(maximunLoanUsed == null){loanAmountUsed = Double.valueOf(0);}
        response.setMinimalLoanUsed(minimalLoanUsed);
        response.setMaximunLoanUsed(maximunLoanUsed);
        //-------------------------------------------------

        //-------------------------------------------------
        // GET simulation loan
        //-------------------------------------------------
        if(!loLoanType.isPresent()){
            log.info(identifierLog + identifierFunction + "Loan Type not Found !");
            throw new CustomGenericException("Loan Type not Found !");
        }

        Boolean isSimulation = loLoanCategory.get().getSimulation();
        LoanSimulationResultsDTO loanSimulationResultsDTO = new LoanSimulationResultsDTO();
        loanSimulationResultsDTO.setLoanAmount(loanAmount == null ? 0 : Double.parseDouble(loanAmount));
        loanSimulationResultsDTO.setInterestRate(loLoanType.get().getInterestRate() == null ? 0 : loLoanType.get().getInterestRate());
        loanSimulationResultsDTO.setTenorInMonth(Integer.parseInt(loanTenor));

        log.info(identifierLog + identifierFunction + "isSimulation : " + isSimulation);

        if(isSimulation){
            log.info(identifierLog + identifierFunction + "loanSimulationResultsDTO.getLoanAmount() : " + loanSimulationResultsDTO.getLoanAmount());
            log.info(identifierLog + identifierFunction + "loanSimulationResultsDTO.getInterestRate() : " + loanSimulationResultsDTO.getInterestRate());
            log.info(identifierLog + identifierFunction + "loanSimulationResultsDTO.getTenorInMonth() : " + loanSimulationResultsDTO.getTenorInMonth());

        //  Double monthlyInstallment = ((loanSimulationResultsDTO.getLoanAmount() - downPayment) + (loanSimulationResultsDTO.getLoanAmount() * loanSimulationResultsDTO.getInterestRate() / 100)) / loanSimulationResultsDTO.getTenorInMonth();
        //  Double monthlyInstallment = (loanSimulationResultsDTO.getLoanAmount() - downPayment) / loanSimulationResultsDTO.getTenorInMonth();
            Double totalLoan = loanSimulationResultsDTO.getLoanAmount() - downPayment;
            Double interestPercentage = loanSimulationResultsDTO.getInterestRate() / 100.0;
            Double totalInterest = totalLoan * interestPercentage;
            Double monthlyInstallment = (totalLoan + totalInterest) / loanSimulationResultsDTO.getTenorInMonth();
            
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.DOWN);
            loanSimulationResultsDTO.setMonthlyInstallment(Double.parseDouble(df.format(monthlyInstallment)));
            log.info(identifierLog + identifierFunction + "monthlyInstallment : " + loanSimulationResultsDTO.getMonthlyInstallment());

        }else {
            log.info(identifierLog + identifierFunction + "monthlyInstallment : null");
            loanSimulationResultsDTO.setMonthlyInstallment(null);
        }
        response.setHasSimulation(isSimulation);
        response.setSimulation(loanSimulationResultsDTO);
        //-------------------------------------------------

        //-------------------------------------------------
        // GET latest loan that still not paid
        //-------------------------------------------------
        //this only use lo_employee_loan (lo_employee_loan filled from estar api)
        //this not include loan from wf_data_approval that already approve but not recognize from estar
        //(if estar recognize loan, it expected to hit api to save into lo_employee_loan)
        Double notPaidLoanPerTenor = loEmployeeLoanRepository.getTotalNotPaidLoanPerTenor(employmentId);
        if(notPaidLoanPerTenor == null){
            notPaidLoanPerTenor = (double) 0;
        }

        Double totalNotPaidLoan = loEmployeeLoanRepository.getTotalNotPaidLoan(employmentId);
        if(totalNotPaidLoan == null){
            totalNotPaidLoan = Double.valueOf(0);
        }
        response.setTotalInProgressLoan(totalNotPaidLoan);
        response.setTotalInProgressLoanPerTenor(notPaidLoanPerTenor);
        //-------------------------------------------------

        //final check
        //check plafon bracket eligible
        if(downPayment == null ){downPayment = Double.valueOf(0);}

        log.info(identifierLog + identifierFunction + "loanAmountUsed = " + loanAmountUsed);
        log.info(identifierLog + identifierFunction + "totalNotPaidLoan = " + totalNotPaidLoan);
        log.info(identifierLog + identifierFunction + "minimalLoanUsed = " + minimalLoanUsed);
        log.info(identifierLog + identifierFunction + "maximunLoanUsed = " + maximunLoanUsed);
        log.info(identifierLog + identifierFunction + "isThereAnyPlafondBracket = " + isThereAnyPlafondBracket);
        log.info(identifierLog + identifierFunction + "downPayment = " + downPayment);

        if(loanAmountUsed + totalNotPaidLoan >= minimalLoanUsed && loanAmountUsed + totalNotPaidLoan <= maximunLoanUsed){
            log.info(identifierLog + identifierFunction + "Enter loan amount in the range of min max ");
            response.setMessage("");
            response.setEligible(true);
        }else {
            //check if loanamount over limit, but the (loanAmount - downpayment) <= maximunloan
            if((loanAmountUsed + totalNotPaidLoan) - downPayment <= maximunLoanUsed){
                log.info(identifierLog + identifierFunction + "Enter loan amount - downpayment still eligible ");
                response.setMessage("");
                response.setEligible(true);
            }else {
                if(!isThereAnyPlafondBracket){
                    log.info(identifierLog + identifierFunction + "No Plafond Bracket Found For This Loan Category");
                    response.setMessage("No Plafond Bracket Found For This Loan Category");
                    response.setEligible(true);
                }else {
                    log.info(identifierLog + identifierFunction + "Your amount not eligible based on plafond bracket");
                    response.setMessage("Your amount not eligible based on plafond bracket");
                    response.setEligible(false);
                }
            }
        }

        log.info(identifierLog + identifierFunction + "eligible amount = " + response.getEligible());
        return response;
    }

    public LoanCheckDsrDTO checkDsrEligible(String identifierLog, String loanTypeId, Double totalLoanAmount, Double downPayment, Integer tenor, String employeeId, Double allowanceBracketElementValue, Double allowanceBracketValue, Double monthlyInstallmentFleet){
        LoanCheckDsrDTO response = new LoanCheckDsrDTO();

        System.out.println(loanTypeId);
        LoLoanType loLoanType = loLoanTypeRepository.findById(loanTypeId).orElseThrow(
                ()-> new CustomGenericException(identifierLog + " Loan Type Doesn't Exist")
        );
        //get employment id
        VwEmpAssignment vwEmpAssignment = vwEmpAssignmentRepository.findByEmployeeId(employeeId).orElseThrow(
                ()-> new CustomGenericException(identifierLog + " Employee Not Found ! ")
        );

        //---------------------------------------------------
        // Set Initial Variable
        //---------------------------------------------------
        Double dsrPercentageUsed = loLoanType.getDsrPercentage();
        response.setLoanTypeId(loanTypeId);
        response.setEmployeeId(employeeId);
        Double monthlyInstallment = (totalLoanAmount - (downPayment == null ? 0 : downPayment))/ (tenor == null || tenor ==0 ? 1 : tenor);
        if(monthlyInstallmentFleet != null){
            monthlyInstallment = monthlyInstallmentFleet;
        }
        log.info("[checkDsrEligible] monthlyInstallmentFleet : " + (monthlyInstallmentFleet == null ? "null" : monthlyInstallmentFleet) );
        log.info("[checkDsrEligible] Monthly Installment : " + (response.getMonthlyInstallment() == null ? "null" : response.getMonthlyInstallment()) );
        log.info("[checkDsrEligible] allowanceBracketElementValue : " + (allowanceBracketElementValue == null ? "null" : allowanceBracketElementValue) );
        log.info("[checkDsrEligible] allowanceBracketValue : " + (allowanceBracketValue == null ? "null" : allowanceBracketValue) );
        monthlyInstallment = monthlyInstallment - (allowanceBracketElementValue == null ? 0 : allowanceBracketElementValue) - (allowanceBracketValue == null ? 0 : allowanceBracketValue);
        response.setMonthlyInstallment(monthlyInstallment);
        log.info("[checkDsrEligible] totalLoanAmount : " + totalLoanAmount );
        log.info("[checkDsrEligible] downPayment : " + (downPayment == null ? 0 : downPayment) );
        log.info("[checkDsrEligible] Tenor : " + (tenor == null || tenor ==0 ? 1 : tenor) );
        log.info("[checkDsrEligible] Monthly Installment : " + (response.getMonthlyInstallment() == null ? "null" : response.getMonthlyInstallment()) );
        //---------------------------------------------------

        //get monthly installment from loan_employee_loan (loan berjalan)
        Optional<Double> monthlyInstallmentBefore =  loEmployeeLoanRepository.findByStatus(LoEmployeeLoan.STATUS_IN_PROGRESS,vwEmpAssignment.getEmploymentId());
        if(monthlyInstallmentBefore.isPresent()) {
            response.setMonthlyInstallment(response.getMonthlyInstallment() + monthlyInstallmentBefore.get());
            response.setMonthlyInstallmentBefore(monthlyInstallmentBefore.get());
            log.info("[checkDsrEligible] Monthly Installment Before : " + (response.getMonthlyInstallmentBefore() == null ? "null" : response.getMonthlyInstallmentBefore()) );
        }

        //get monthly installment from loan request in progress
        Optional<Double> monthlyInstallmentInProgressRequest =  requestRepository.findByStatus("In Progress",vwEmpAssignment.getEmploymentId());
        if(monthlyInstallmentInProgressRequest.isPresent()) {
            response.setMonthlyInstallment(response.getMonthlyInstallment() + monthlyInstallmentInProgressRequest.get());
            response.setMonthlyInstallmentInProgressRequest(Double.valueOf(monthlyInstallmentInProgressRequest.get()));
            log.info("[checkDsrEligible] Monthly In Progress Request : " + (response.getMonthlyInstallmentInProgressRequest() == null ? "null" : response.getMonthlyInstallmentInProgressRequest()) );
        }

        //get take home pay based on employmentId
        List<PayrollMonthlyHeader> payrollMonthlyHeaderUsed = payrollMonthlyHeaderRepository.getPayrollMonthlyHeaderBasedOnEmploymentId(vwEmpAssignment.getEmploymentId());
        if(payrollMonthlyHeaderUsed.size() == 0){
            log.info("[checkDsrEligible] Take Home Pay Not Found ! " );
            throw new CustomGenericException(identifierLog + " Take Home Pay Not Found ! ");
        }

        //calculate dsr (take home pay * dsr percentage)
        Double dsrLimit = payrollMonthlyHeaderUsed.get(0).getTakeHomePay() * dsrPercentageUsed / 100;
        response.setDsrLimit(dsrLimit);
        Double monthlyInstallmentUsed = response.getMonthlyInstallment();

        //check if eligible (dsrUsed must be <= monthlyInstallment)
        log.info("[checkDsrEligible] Final Take Home Pay : " +  payrollMonthlyHeaderUsed.get(0).getTakeHomePay() );
        log.info("[checkDsrEligible] Final dsrPercentageUsed : " + dsrPercentageUsed );
        log.info("[checkDsrEligible] Final DsrLimit : " + response.getDsrLimit() );
        log.info("[checkDsrEligible] Final Monthly Installment : " + monthlyInstallment );

        if(dsrLimit < monthlyInstallmentUsed){
            log.info("Loan Not Eligible Based On DSR Requirement");
            response.setMessage("Loan Not Eligible Based On DSR Requirement");
            response.setEligible(false);
        }else {
            log.info("Loan Eligible Based On DSR Requirement");
            response.setMessage("");
            response.setEligible(true);
        }

        return response;

    }

    private List<LoanFeedbackFleetListDTO> getFeedbackFleetListByRequestNo(String requestNo){
        List<LoanFeedbackFleetListDTO> response = new ArrayList<>();

        Optional<LoLoanRequest> loanRequestData = requestRepository.findByRequestNo(requestNo);
        if(loanRequestData.isPresent()){
            response = loFeedbackFleetRepository.findByLoanCategoryIdExceptMonthlyInstallment(loanRequestData.get().getLoanCategoryId());
        }

        Optional<LoVehicleBrand> loVehicleBrand = Optional.empty();
        if(loanRequestData.get().getBrandId() != null) {
            loVehicleBrand = loVehicleBrandRepository.findById(loanRequestData.get().getBrandId());
            if (!loVehicleBrand.isPresent()) {
                throw new CustomGenericException("Loan Vehicle Brand Not Found");
            }
        }

        Optional<LoVehicleBrand> finalLoVehicleBrand = loVehicleBrand;
        response.stream().forEach(
                x -> {
                    if(x.getValueSource() != null) {
                        switch (x.getValueSource()) {
                            case LoFeedbackFleet.VALUE_SOURCE_COMPANY_PERCENTAGE:
                                Double companyPercentage = !finalLoVehicleBrand.isPresent() ? 0 : (finalLoVehicleBrand.get().getCompanyPercentage());
                                if (finalLoVehicleBrand.isPresent()) {
                                    x.setValue(companyPercentage / 100 * loanRequestData.get().getAmount());
                                }
                                break;
                            case LoFeedbackFleet.VALUE_SOURCE_EMPLOYEE_PERCENTAGE:
                                Double employeePercentage = !finalLoVehicleBrand.isPresent() ? 0 : (finalLoVehicleBrand.get().getEmployeePercentage());
                                if (finalLoVehicleBrand.isPresent()) {
                                    x.setValue(employeePercentage / 100 * loanRequestData.get().getAmount());
                                }
                                break;
                        }
                    }
                }
        );


        return response;
    }

    private List<LoanRequestDetailListDTO> getLoanRequestDetailByRequestNo(String requestNo){
        List<LoanRequestDetailListDTO> response = new ArrayList<>();

        Optional<LoLoanRequest> loanRequestData = requestRepository.findByRequestNo(requestNo);
        if(loanRequestData.isPresent()){
            response = loLoanRequestDtlRepository.findByLoanRequestId(loanRequestData.get().getId());
        }

        return response;
    }

    @Transactional
    public LoanCheckEligibilityDTO checkRequirements(String employmentId,String loanCategoryId,String loanTypeId ){
        // log.info(identifierLog + identifierFunction + "eligible amount = " + response.getEligible());
        LoanCheckEligibilityDTO response = new LoanCheckEligibilityDTO();
        response.setMessage("");
        Double minimalLoanUsed = (double) 0;
        Double maximunLoanUsed = (double) 0;
        Date dateUsed = new Date();
        Boolean isThereAnyPlafondBracket = true;


        Optional<VwEmpAssignment> employeeViewOpt = vwEmpAssignmentRepository.findByEmploymentId(employmentId);
        if (!employeeViewOpt.isPresent()) {
            throw new CustomGenericException("Something went wrong,please contact Admin - Employee Detail Not Found");
        }
        
        Optional<LoLoanCategory> loLoanCategory = loLoanCategoryRepository.findById(loanCategoryId);
        if(!loLoanCategory.isPresent()){
            throw new CustomGenericException("Loan Category not Found !");
        }
        PlafondBracketListDTO plafondBracketFound = checkLoanPlafondBracketBasedOnEmployment("",employmentId,loanCategoryId,loanTypeId,null,employeeViewOpt.get(),dateUsed, true);
        LoanRatingModelCheckResultDTO checkRatingModelMatrixResult = checkRatingModelMatrix("",loanCategoryId,employmentId,LocalDate.now());

        if(plafondBracketFound != null && plafondBracketFound.getThereAnyPlafondBracket()){
            if(plafondBracketFound.getId() == null){
                response.setMessage("Failed");
               return response;
            }
        }else{
            if(plafondBracketFound == null) {
                response.setMessage("Failed");
               return response;
            }
        }

        minimalLoanUsed = plafondBracketFound.getMinValue() == null ? 0 : plafondBracketFound.getMinValue();
        maximunLoanUsed = !plafondBracketFound.getThereAnyPlafondBracket() ? 0 : (plafondBracketFound.getMaxValue() == null ? (plafondBracketFound.getMultiplier() * plafondBracketFound.getTotalElementValue()) : plafondBracketFound.getMaxValue()) ;
        isThereAnyPlafondBracket = plafondBracketFound.getThereAnyPlafondBracket();
        response.setMinimalLoanUsed(minimalLoanUsed);
        response.setMaximunLoanUsed(maximunLoanUsed);
        response.setGrade(employeeViewOpt.get().getGradeName());
        response.setRatingLastYear(checkRatingModelMatrixResult.getScoreYear1());
        response.setRatingLast2Year(checkRatingModelMatrixResult.getScoreYear2());
        response.setMessage("Success");
        return response;
    }
}
