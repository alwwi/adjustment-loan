package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dao.AddressRequestRepository;
import com.phincon.talents.app.dao.UserRepository;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.generalnew.SysUser;
import com.phincon.talents.app.services.loan.LoanEarlySettlementRequestService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user/dataApproval/personal/loan/early-settlement")
public class LoanEarlySettlementRequestController {
    private static final Logger log = LogManager.getLogger(LoanEarlySettlementRequestController.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanEarlySettlementRequestService service;

    @Autowired
    AddressRequestRepository addressRequestRepository;

    String identifier = "Loan Early Settlement Request";

    @PostMapping("submit")
    public ResponseEntity<CustomMessageTest> submitApproval(@AuthenticationPrincipal Jwt jwt,@RequestBody LoanEarlySettlementRequestDTO request) {
        log.info("============ submit " + identifier + " ============");

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        String insertRequest = service.InsertData(identifier,request, user.getEmployeeId(), user.getEmployeeId(),false,false);

        // fuguh
        // start perubahan
        String requestIdReq = addressRequestRepository.findIdUserRequestEarlySettlement(insertRequest);
        String listApprover = addressRequestRepository.findUserApprover(user.getEmployeeId(),requestIdReq,"Early Settlement");

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

    @GetMapping("check-remaining-amount")
    @ResponseBody
    public ResponseEntity<LoanEarlySettlementRemainingDTO> checkRemainingAmount(@RequestParam("requestNo") String requestNo,
                                                                              @AuthenticationPrincipal Jwt jwt,
                                                                              HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));


        Optional<LoanEarlySettlementRemainingDTO> resultOpt = service.checkRemainingAmount(user.getEmployeeId(),requestNo,request);
        if(resultOpt.isPresent())
        {
            return new ResponseEntity<>(resultOpt.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @GetMapping("needapproval")
    @ResponseBody
    public ResponseEntity<Page<LoanEarlySettlementRequestNeedApprovalDTO>> needApproval(
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
        Page<LoanEarlySettlementRequestNeedApprovalDTO> findByEmployeeAndModule = service.needApprovalList(user.getEmployeeId(),status,request,requestDateStart,requestDateEnd,ppl,loanTypeId,pageRequest,jwt);

        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("needapproval/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<LoanEarlySettlementMyRequestDTO> needApprovalDetail(@PathVariable("dataApprovalId") String dataApprovalId,
                                                             @AuthenticationPrincipal Jwt jwt,
                                                             HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));


        Optional<LoanEarlySettlementMyRequestDTO> resultOpt = service.findNeedApproval2ById(user.getEmployeeId(),dataApprovalId,request,jwt);
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
    public ResponseEntity<Page<LoanEarlySettlementMyRequestDTO>> myRequest(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,
            @RequestParam(value = "loanCategoryId", required = false) String loanCategoryId,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size,@RequestParam(value = "requestNo", required = false,defaultValue="") String requestNo, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Sort sorting = Sort.by(Sort.Direction.DESC, "requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Page<LoanEarlySettlementMyRequestDTO> findByEmployeeAndModule = service.findMyRequestByEmployeeAndModule(user.getEmployee().getEmployment().iterator().next().getId(),status,request,requestDateStart,requestDateEnd,pageRequest,requestNo,loanTypeId,loanCategoryId,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("myrequest/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<LoanEarlySettlementMyRequestDTO> myRequestById(@PathVariable("dataApprovalId") String dataApprovalId,
                                                              @AuthenticationPrincipal Jwt jwt,
                                                              HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        //Sort sorting = Sort.by(Direction.DESC, "requestDate");
        Optional<LoanEarlySettlementMyRequestDTO> resultOpt = service.findMyRequestByEmployeeAndId(user.getEmployee().getEmployment().iterator().next().getId(),dataApprovalId,request,jwt);
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
    public ResponseEntity<Page<LoanEarlySettlementRequestHistoryApprovalDTO>> getHistoryApproval(
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
        Page<LoanEarlySettlementRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApproval(user.getEmployeeId(),status,request,requestDateStart,requestDateEnd,ppl,pageRequest,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("historyApproval/{dataApprovalDetailId}")
    @ResponseBody
    public ResponseEntity<LoanEarlySettlementRequestHistoryApprovalDTO> getHistoryApprovalDetail(
            @PathVariable("dataApprovalDetailId") String dataApprovalDetailId, @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Optional<LoanEarlySettlementRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApprovalByIdAndEmployee(user.getEmployeeId(),dataApprovalDetailId,request,jwt);
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
    public ResponseEntity<List<LoanEarlySettlementRequestHistoryApprovalDTO>> getHistoryApproval(
            @PathVariable("dataApprovalId") String dataApprovalId, //@AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request,@AuthenticationPrincipal Jwt jwt) {

        List<LoanEarlySettlementRequestHistoryApprovalDTO> findByEmployeeAndModule = service.findHistoryApprovalByApprovalId(dataApprovalId,request,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }
}
