package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.controllers.api.admin.dataapproval.AdDALoanController;
import com.phincon.talents.app.dao.UserRepository;
import com.phincon.talents.app.dao.VwEmpAssignmentRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.integration.salesforce.ResultSyncDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.generalnew.SysUser;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoSyncLoanRequest;
import com.phincon.talents.app.services.loan.LoanSyncService;
import com.phincon.talents.app.utils.CustomMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("api/admin/sync/loan")
public class AdSyncLoanController {
    private static final Logger log = LogManager.getLogger(AdSyncLoanController.class);

    String identifier = "Sync Loan";

    @Autowired
    LoanSyncService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @PostMapping(value = "submit")
    public ResponseEntity<ResultSyncDTO> submitApproval (@AuthenticationPrincipal Jwt jwt, @RequestBody LoanSyncRequestDTO request) {
        log.info("============ submit " + identifier + " ============");

        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Optional<VwEmpAssignment> optAssgn = vwEmpAssignmentRepository.findByEmployeeId(user.getEmployeeId());
        ResultSyncDTO insertRequest;
        try {
            insertRequest = service.InsertData(identifier, request, optAssgn.get().getEmploymentId());
        }catch (Exception ex){
            throw new CustomGenericException(ex.getMessage());
        }

        log.info("============ submit " + identifier + " ============");
        return new ResponseEntity<>(
                insertRequest, HttpStatus.OK);
    }

    @GetMapping()
    public Page<LoanSyncListDTO> listData(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,

            @RequestParam(value = "page",required = false,defaultValue = "0") String page,
            @RequestParam(value = "size",required = false,defaultValue = "1000") String size,
            HttpServletRequest request,@AuthenticationPrincipal Jwt jwt
    ) {

        LocalDate startDateUsed=null;
        if(startDate == null || startDate.isEmpty()){
//            startDateUsed=LocalDate.now();
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        LocalDate endDateUsed=null;
        if(endDate == null || endDate.isEmpty()){
//            endDateUsed=LocalDate.now();
        }else{
            endDateUsed=LocalDate.parse(startDate);
        }

        return service.listData(page,size, startDateUsed,endDateUsed,status,request,jwt);
    }

    @GetMapping("detail")
    public LoanSyncListDTO getDetail(@RequestParam(value = "id") String id,HttpServletRequest request,@AuthenticationPrincipal Jwt jwt){
        return service.getDetail(id,request,jwt);
    }

}
