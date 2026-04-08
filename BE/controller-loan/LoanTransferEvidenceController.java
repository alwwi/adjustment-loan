package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.generalnew.SysUser;
import com.phincon.talents.app.services.loan.LoanTransferEvidenceService;
import com.phincon.talents.app.services.loan.LoanTypeService;
import com.phincon.talents.app.utils.CustomMessageWithId;
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
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/loan/transfer-evidence")
public class LoanTransferEvidenceController {
    private static final Logger log = LogManager.getLogger(LoanTransferEvidenceController.class);

    @Autowired
    LoanTransferEvidenceService service;

    String identifier = "Loan Transfer Evidence";


    @GetMapping()
    public Page<LoanTransferEvidenceListDTO> listData(
            @RequestParam(value = "requestNo", required = false) String requestNo,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,

            @RequestParam(value = "page",required = false,defaultValue = "0") String page,
            @RequestParam(value = "size",required = false,defaultValue = "1000") String size,
            HttpServletRequest request,@AuthenticationPrincipal Jwt jwt
    ) {

        LocalDate startDateUsed=null;
        LocalDate endDateUsed=null;
        Boolean isAnyTransfer = null;

        if(startDate == null || startDate.isEmpty()){
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        if(endDate == null || endDate.isEmpty()){
        }else{
            endDateUsed=LocalDate.parse(endDate);
        }

        return service.listData(page,size,requestNo, startDateUsed,endDateUsed,request,jwt);
    }

    @GetMapping("loan")
    @ResponseBody
    public ResponseEntity<Page<LoanMyRequestDTO>> myRequest(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "requestDateStart", required = false) String requestDateStart,
            @RequestParam(value = "requestDateEnd", required = false) String requestDateEnd,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,
            @RequestParam(value = "page", required = false,defaultValue="0") Integer page,
            @RequestParam(value = "size", required = false,defaultValue="15") Integer size,
            @RequestParam(value = "requestNo", required = false,defaultValue="") String requestNo,
            @RequestParam(value = "employmentId", required = false) String employmentId,
            @RequestParam(value = "anyTransfer", required = false) String anyTransfer,
            @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) {

        Sort sorting = Sort.by(Sort.Direction.DESC, "requestDate");
        PageRequest pageRequest = PageRequest.of(page, size,sorting);
        Boolean isAnyTransfer = null;
        if(anyTransfer != null && anyTransfer.toLowerCase().equals("true") ){
            isAnyTransfer = true;
        } else if (anyTransfer != null && anyTransfer.toLowerCase().equals("false")) {
            isAnyTransfer = false;
        }

        Page<LoanMyRequestDTO> findByEmployeeAndModule = service.findLoanRequests(employmentId,status,request,requestDateStart,requestDateEnd,pageRequest,requestNo,loanTypeId,isAnyTransfer,jwt);
        return new ResponseEntity<>(findByEmployeeAndModule, HttpStatus.OK);
    }

    @GetMapping("loan/{dataApprovalId}")
    @ResponseBody
    public ResponseEntity<LoanMyRequestDTO> myRequestById(@PathVariable("dataApprovalId") String dataApprovalId,
                                                          @AuthenticationPrincipal Jwt jwt,
                                                          HttpServletRequest request) {

        //Sort sorting = Sort.by(Direction.DESC, "requestDate");
        Optional<LoanMyRequestDTO> resultOpt = service.findMyRequestByEmployeeAndId(dataApprovalId,request,jwt);
        if(resultOpt.isPresent())
        {
            return new ResponseEntity<>(resultOpt.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("detail")
    public LoanTransferEvidenceListDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id, HttpServletRequest request,@AuthenticationPrincipal Jwt jwt){
        return service.getDetail(identifier,id,request,jwt);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanTransferEvidencePostDTO request, HttpServletRequest requestHTTP,@AuthenticationPrincipal Jwt jwt){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request,requestHTTP,jwt);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanTransferEvidencePostDTO request, HttpServletRequest requestHTTP,@AuthenticationPrincipal Jwt jwt){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId(),requestHTTP,jwt);
        log.info("============ delete " + identifier + " ============");
        return response;
    }

}