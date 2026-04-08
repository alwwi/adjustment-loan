package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dao.AddressRequestRepository;
import com.phincon.talents.app.dao.UserRepository;
import com.phincon.talents.app.dto.FKPRequestDTO;
import com.phincon.talents.app.dto.FKPRequestHistoryApprovalDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.dto.personal.WorkExperienceRequestDTO;
import com.phincon.talents.app.dto.recruitment.FKPDetailDTO;
import com.phincon.talents.app.model.generalnew.SysUser;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.services.dataapproval.DAWorkExperienceService;
import com.phincon.talents.app.services.loan.LoanRequestService;
import com.phincon.talents.app.utils.CustomMessage;
import com.phincon.talents.app.utils.CustomMessageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("api/user/dataApproval/personal/loan")
public class LoanRequestController {
    private static final Logger log = LogManager.getLogger(LoanRequestController.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanRequestService service;

    @Autowired
    AddressRequestRepository addressRequestRepository;

    String identifier = "Loan Request";

    @GetMapping("confirm")
    public ResponseEntity<CustomMessageTest> submitConfirmLoan(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "requestNo", required = true) String requestNo) {
        log.info("============ submit Confirm Loan ============");
        ResponseEntity<CustomMessageTest>  response = service.confirmLoan(requestNo, LoEmployeeLoan.STATUS_IN_PROGRESS);
        log.info("============ submit Confirm Loan ============");
        return response;
    }

    @GetMapping("cancel")
    public ResponseEntity<CustomMessageTest> submitCancelLoan(@AuthenticationPrincipal Jwt jwt, @RequestParam(value = "requestNo", required = true) String requestNo) {
        log.info("============ submit Cancel Loan ============");
        ResponseEntity<CustomMessageTest>  response = service.confirmLoan(requestNo,LoEmployeeLoan.STATUS_CANCEL);
        log.info("============ submit Cancel Loan ============");
        return response;
    }

    @PostMapping("submit")
    public ResponseEntity<CustomMessageTest> submitApproval(@AuthenticationPrincipal Jwt jwt, @RequestBody LoanRequestDTO request) {
        log.info("============ submit " + identifier + " ============");

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));
        request.setEmploymentId(user.getEmployee().getEmployment().stream().findFirst().get().getId());

        //bypassapproval always false based on discussion because there is custom workflow for this loan
        String insertRequest = service.InsertData(identifier,request, user.getEmployeeId(), user.getEmployeeId(),false,false);

        // fuguh
        // start perubahan
        String requestIdReq = addressRequestRepository.findIdUserRequestLoan(insertRequest);
//        String listApprover = addressRequestRepository.findUserApprover(user.getEmployeeId(),requestIdReq);
        String listApprover = addressRequestRepository.findUserApprover(user.getEmployeeId(),requestIdReq,"Loan");

        String replaceString = "";
        String listIdApprover = "";
        String listDeviceString = "";

        if(listApprover != null) {
            replaceString = listApprover.replaceAll("##", ",");
            listIdApprover = replaceString.replaceAll("#", "");

            List<String> myList = new ArrayList<String>(Arrays.asList(listIdApprover.split(",")));
            int size = myList.size();

            List deviceIdList = new ArrayList();

            String indexssss = "";
            for (int i = 0; i < size; i++) {

//            indexssss = myList.get(2);
                String listLong = myList.get(i);
                String listMentahDeviceId = addressRequestRepository.findDeviceIdApprover(listLong);

                deviceIdList.add(listMentahDeviceId);

            }

            listDeviceString = deviceIdList.toString();
        }
        //end perubahan

        ResponseEntity<CustomMessageTest> response = new ResponseEntity<>(
                new CustomMessageTest("Submit " + identifier + " Sucessfully (Request No : " + insertRequest + ")", false, listDeviceString),
                HttpStatus.OK);

        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @GetMapping("needapproval")
    @ResponseBody
    public ResponseEntity<Page<LoanRequestNeedApprovalDTO>> needApproval(
            @RequestParam(value = "status", required = false,defaultValue ="In Progress") String status,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,
            @RequestParam(value = "ppl",defaultValue="" ,required = false) String ppl,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Sort sorting = Sort.by(Sort.Direction.ASC, "requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Page<LoanRequestNeedApprovalDTO> findByEmployeeAndModule = service.needApprovalList(user.getEmployeeId(),status,request,requestDateStart,requestDateEnd,ppl,pageRequest,loanTypeId,jwt);

        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("needapproval/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<LoanMyRequestDTO> needApprovalDetail(@PathVariable("dataApprovalId") String dataApprovalId,
                                                             @AuthenticationPrincipal Jwt jwt,
                                                             HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));


        Optional<LoanMyRequestDTO> resultOpt = service.findNeedApproval2ById(user.getEmployeeId(),dataApprovalId,request,jwt);
        if(resultOpt.isPresent())
        {
            return new ResponseEntity<>(resultOpt.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("myrequest")
    @ResponseBody
    public ResponseEntity<Page<LoanMyRequestDTO>> myRequest(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size,@RequestParam(value = "requestNo", required = false,defaultValue="") String requestNo, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Sort sorting = Sort.by(Sort.Direction.DESC, "requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Page<LoanMyRequestDTO> findByEmployeeAndModule = service.findMyRequestByEmployeeAndModule(user.getEmployee().getEmployment().iterator().next().getId(),status,request,requestDateStart,requestDateEnd,pageRequest,requestNo,loanTypeId,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("myrequest/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<LoanMyRequestDTO> myRequestById(@PathVariable("dataApprovalId") String dataApprovalId,
                                                              @AuthenticationPrincipal Jwt jwt,
                                                              HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        //Sort sorting = Sort.by(Direction.DESC, "requestDate");
        Optional<LoanMyRequestDTO> resultOpt = service.findMyRequestByEmployeeAndId(user.getEmployee().getEmployment().iterator().next().getId(),dataApprovalId,request,jwt);
        if(resultOpt.isPresent())
        {
            return new ResponseEntity<>(resultOpt.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("historyApproval")
    @ResponseBody
    public ResponseEntity<Page<LoanRequestHistoryApprovalDTO>> getHistoryApproval(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "ppl",defaultValue="" ,required = false) String ppl,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Sort sorting = Sort.by(Sort.Direction.ASC, "br.requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Page<LoanRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApproval(user.getEmployeeId(),status,request,requestDateStart,requestDateEnd,ppl,pageRequest,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("history-loan")
    @ResponseBody
    public ResponseEntity<Page<LoanHistoryDTO>> getHistoryLoan(
            @RequestParam(value = "requestNo", required = false) String requestNo,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "loanStatus", required = false) String loanStatus,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Sort sorting = Sort.by(Sort.Direction.ASC, "requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Page<LoanHistoryDTO> findByEmployeeAndModule = service.findHistoryLoan(user.getEmployeeId(),requestNo,name,loanStatus,loanTypeId,request,requestDateStart,requestDateEnd,pageRequest,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("history-loan/{loanRequestId}")
    @ResponseBody
    public ResponseEntity<LoanHistoryDetailDTO> getHistoryLoan(
            @PathVariable("loanRequestId") String loanRequestId,@AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request
            ) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        LoanHistoryDetailDTO findByLoanRequest = service.findHistoryLoanDetail(loanRequestId,request,jwt);
        return new ResponseEntity<>(findByLoanRequest, HttpStatus.OK);
    }

    @GetMapping("history-loan/{loanRequestId}/report")
    @ResponseBody
    public ResponseEntity<LoanHistoryDetailDTO> getHistoryLoanReport(
            @PathVariable("loanRequestId") String loanRequestId,@AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request
    ) throws IllegalAccessException {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        LoanHistoryDetailDTO findByLoanRequest = service.findHistoryLoanDetailReport(loanRequestId,request,jwt);
        return new ResponseEntity<>(findByLoanRequest, HttpStatus.OK);
    }

    @GetMapping("historyApproval/{dataApprovalDetailId}")
    @ResponseBody
    public ResponseEntity<LoanRequestHistoryApprovalDTO> getHistoryApprovalDetail(
            @PathVariable("dataApprovalDetailId") String dataApprovalDetailId, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Optional<LoanRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApprovalByIdAndEmployee(user.getEmployeeId(),dataApprovalDetailId,request,jwt);
        if(findByEmployeeAndModule.isPresent())
        {
            return new ResponseEntity<>(findByEmployeeAndModule.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("historyApprovalById/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<List<LoanRequestHistoryApprovalDTO>> getHistoryApproval(
            @PathVariable("dataApprovalId") String dataApprovalId, //@AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request,@AuthenticationPrincipal Jwt jwt) {

        List<LoanRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApprovalByApprovalId(dataApprovalId,request,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("check-eligibility-loan")
    @ResponseBody
    public LoanCheckEligibilityDTO checkEligibility(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(value = "loanAmount") String loanAmount,
            @RequestParam(value = "downPayment") String downPayment,
            @RequestParam(value = "loanTenor") String loanTenor,
            @RequestParam(value = "loanCategoryId") String loanCategoryId,
            @RequestParam(value = "loanTypeId") String loanTypeId,
            HttpServletRequest request) {

        Double loanAmountUsed = Double.parseDouble(loanAmount);
        Double downPaymentUsed = Double.parseDouble(downPayment);

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));
            if(downPayment.length() > 0){
                loanAmountUsed = loanAmountUsed - downPaymentUsed;
            };
        LoanCheckEligibilityDTO response = service.checkEligibilityLoanAmount("[checkEligibility] ",
                user.getEmployee().getEmployment().iterator().next().getId(),loanAmountUsed.toString(),
                loanTenor,
                loanCategoryId,
                (loanTypeId),
                Double.parseDouble(downPayment)
                );

        return response;
    }

    @GetMapping("check-requirements-loan")
    @ResponseBody
    public LoanCheckEligibilityDTO checkRequirements(
            @AuthenticationPrincipal Jwt jwt,
            // @RequestParam(value = "loanAmount") String loanAmount,
            // @RequestParam(value = "downPayment") String downPayment,
            // @RequestParam(value = "loanTenor") String loanTenor,
            @RequestParam(value = "loanCategoryId") String loanCategoryId,
            @RequestParam(value = "loanTypeId") String loanTypeId,
            HttpServletRequest request) {


        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));
  
        LoanCheckEligibilityDTO response = service.checkRequirements(
                user.getEmployee().getEmployment().iterator().next().getId(),
                loanCategoryId,
                (loanTypeId)
                );

        return response;
    }

//    @GetMapping("check-dsr-loan")
//    @ResponseBody
//    public LoanCheckDsrDTO checkDSRLoan(
//            @AuthenticationPrincipal Jwt jwt,
//            @RequestParam(value = "monthlyInstallment") String monthlyInstallment,
//            @RequestParam(value = "loanTypeId") String loanTypeId,
//            HttpServletRequest request) {
//
//        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));
//
//        LoanCheckDsrDTO response = service.checkDsrEligible("[checkDSRLoan] ",
//                (loanTypeId),
//                Double.parseDouble(monthlyInstallment),
//                user.getEmployeeId()
//        );
//        return response;
//    }
}
