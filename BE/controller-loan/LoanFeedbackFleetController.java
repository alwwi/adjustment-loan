package com.phincon.talents.app.controllers.api.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phincon.talents.app.dto.integration.estar.EstarLoanEmployeeDataResultDTO;
import com.phincon.talents.app.dto.integration.estar.EstarRequestLoanEmployeeDataDTO;
import com.phincon.talents.app.dto.integration.estar.EstarResponseSubmitLoanEmployeeDataDTO;
import com.phincon.talents.app.dto.integration.estar.EstarSubmitLoanEmployeeDataDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.services.integration.EstarApiService;
import com.phincon.talents.app.services.loan.LoanCategoryService;
import com.phincon.talents.app.services.loan.LoanFeedbackFleetService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/loan/feedback-fleet")
public class LoanFeedbackFleetController {
    private static final Logger log = LogManager.getLogger(LoanFeedbackFleetController.class);

    @Autowired
    LoanFeedbackFleetService service;

    String identifier = "Loan Feedback Fleet";


    @GetMapping()
    public Page<LoanFeedbackFleetListDTO> listData(
           @RequestParam(value = "name", required = false) String name,
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,
           @RequestParam(value = "loanCategoryId", required = false) String loanCategoryId,

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

        return service.listData(page,size,name, startDateUsed,endDateUsed,loanCategoryId);
    }

    @GetMapping("detail")
    public LoanFeedbackFleetDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanFeedbackPostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanFeedbackPostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }

}
