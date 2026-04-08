package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoRatingModelMatrix;
import com.phincon.talents.app.services.loan.LoRatingModelMatrixService;
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
@RequestMapping("api/loan/rating-model-matrix")
public class LoanRatingModelMatrixController {
    private static final Logger log = LogManager.getLogger(LoanRatingModelMatrixController.class);

    @Autowired
    LoRatingModelMatrixService service;

    String identifier = "Rating Model Matrix";

    @GetMapping("lookup-result")
    public List<String> lookupResult(){
        List<String> response = new ArrayList<>();
        response.add(LoRatingModelMatrix.RESULT_BATAS_ATAS);
        response.add(LoRatingModelMatrix.RESULT_BATAS_BAWAH);
        return response;
    }

    @GetMapping()
    public Page<LoanRatingModelMatrixListDTO> listData(

            @RequestParam(value = "ratingModelId", required = false) String ratingModelId,
            @RequestParam(value = "paRatingId1", required = false) String paRatingId1,
            @RequestParam(value = "paRatingId2", required = false) String paRatingId2,
            @RequestParam(value = "result", required = false) String result,

            @RequestParam(value = "page",required = false,defaultValue = "0") String page,
            @RequestParam(value = "size",required = false,defaultValue = "1000") String size
    ) {

        return service.listData(page,size,ratingModelId,paRatingId1,paRatingId2,result);
    }

    @GetMapping("detail")
    public LoanRatingModelMatrixDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanRatingModelMatrixPostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanRatingModelMatrixPostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }


}
