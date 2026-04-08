package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.services.loan.LoRatingModelService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/loan/rating-model")
public class LoanRatingModelController {
    private static final Logger log = LogManager.getLogger(LoanRatingModelController.class);

    @Autowired
    LoRatingModelService service;

    String identifier = "Rating Model";

    @GetMapping("lookup-year-before")
    public List<Integer> lookupYearBefore(){
        List<Integer> response = new ArrayList<>();
        response.add(1);
        response.add(2);
        return response;
    }

    @GetMapping()
    public Page<LoanRatingModelListDTO> listData(
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
    public LoanRatingModelDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanRatingModelPostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanRatingModelPostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }


}
