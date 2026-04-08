package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dao.UserRepository;
import com.phincon.talents.app.dao.VwEmpAssignmentRepository;
import com.phincon.talents.app.dto.TMEmpDailyDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.generalnew.SysUser;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.services.loan.LoanEmployeeService;
import com.phincon.talents.app.utils.CustomMessageWithId;
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

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/loan/my-loan")
public class LoanMyLoanController {
    private static final Logger log = LogManager.getLogger(LoanMyLoanController.class);

    String identifier = "My Loan";

    @Autowired
    LoanEmployeeService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VwEmpAssignmentRepository vwEmpAssignmentRepository;

    @GetMapping()
    public Page<LoanMyLoanListDTO> listData(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(value = "requestNo", required = false) String requestNo,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,

            @RequestParam(value = "status", required = false) String status,

            @RequestParam(value = "loanCategoryId", required = false) String loanCategoryId,
            @RequestParam(value = "loanTypeId", required = false) String loanTypeId,

            @RequestParam(value = "page",required = false,defaultValue = "0") String page,
            @RequestParam(value = "size",required = false,defaultValue = "1000") String size
    ) {
        SysUser user = userRepository
                .findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Optional<VwEmpAssignment> employmentData = vwEmpAssignmentRepository.findById(user.getEmployeeId());;

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

        return service.listData(page,size,requestNo, startDateUsed,endDateUsed,loanTypeId,loanCategoryId,employmentData.get().getEmploymentId(),status);
    }

    @GetMapping("detail")
    public LoanMyLoanDetailDTO getDetail(@AuthenticationPrincipal Jwt jwt,
                                         @RequestParam(value = "id") String id){
        SysUser user = userRepository
                .findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        Optional<VwEmpAssignment> employmentData = vwEmpAssignmentRepository.findById(user.getEmployeeId());;

        return service.getDetail(identifier,id,employmentData.get().getEmploymentId());
    }

    @GetMapping("sync")
    public ResponseEntity<CustomMessageWithId> syncData(@AuthenticationPrincipal Jwt jwt
                                                        ){
        log.info("============ sync " + identifier + " ============");
        SysUser user = userRepository.findByUsernameCaseInsensitiveNew(jwt.getClaimAsString("username"));

        ResponseEntity<CustomMessageWithId> response = service.syncData(identifier,user.getEmployeeId());
        log.info("============ sync " + identifier + " ============");
        return response;
    }

}
