package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.LoanDisciplineDetailDTO;
import com.phincon.talents.app.dto.loan.LoanDisciplineListDTO;
import com.phincon.talents.app.dto.loan.LoanDisciplinePostDTO;
import com.phincon.talents.app.services.loan.LoanDisciplineService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/loan/discipline")
public class LoanDisciplineController {
    private static final Logger log = LogManager.getLogger(LoanDisciplineController.class);

    @Autowired
    LoanDisciplineService service;

    String identifier = "Loan Discipline";


    @GetMapping()
    public Page<LoanDisciplineListDTO> listData(
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,

           @RequestParam(value = "loanTypeId", required = false) String loanTypeId,

           @RequestParam(value = "page",required = false,defaultValue = "0") String page,
           @RequestParam(value = "size",required = false,defaultValue = "1000") String size
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

        return service.listData(page,size, startDateUsed,endDateUsed,loanTypeId);
    }

    @GetMapping("detail")
    public LoanDisciplineDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanDisciplinePostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanDisciplinePostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }

}
