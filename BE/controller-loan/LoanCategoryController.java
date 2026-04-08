package com.phincon.talents.app.controllers.api.loan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.phincon.talents.app.dto.integration.estar.*;
import com.phincon.talents.app.dto.loan.LoanCategoryDetailDTO;
import com.phincon.talents.app.dto.loan.LoanCategoryListDTO;
import com.phincon.talents.app.dto.loan.LoanCategoryPostDTO;
import com.phincon.talents.app.dto.loan.ResponseDataDTO;
import com.phincon.talents.app.model.hr.AbsenceBalance;
import com.phincon.talents.app.services.integration.EstarApiService;
import com.phincon.talents.app.services.loan.LoanCategoryService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/loan/category")
public class LoanCategoryController {
    private static final Logger log = LogManager.getLogger(LoanCategoryController.class);

    @Autowired
    LoanCategoryService service;

    String identifier = "Loan Category";

    @Autowired
    EstarApiService estarApiService;


    @GetMapping()
    public Page<LoanCategoryListDTO> listData(
           @RequestParam(value = "name", required = false) String name,
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,
           @RequestParam(value = "requestCategoryName", required = false) String requestCategoryName,

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

        return service.listData(page,size,name, startDateUsed,endDateUsed,requestCategoryName);
    }

    @GetMapping("detail")
    public LoanCategoryDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanCategoryPostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody LoanCategoryPostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }

    //-----------------------------------------------------------------
    //Just For testing, can be deleted if not needed
    //-----------------------------------------------------------------

    @PostMapping("sync-test/submit")
    public EstarResponseSubmitLoanEmployeeDataDTO submitLoanEmployeeData(@RequestBody EstarSubmitLoanEmployeeDataDTO request) throws JsonProcessingException {
        log.info("============ Testing Submit Loan Employee From Estar ============");
//        EstarApiService estarApiService = new EstarApiService();

        EstarResponseSubmitLoanEmployeeDataDTO response = estarApiService.submitLoanEmployeeData("[testing] ",request);
        log.info("============ Testing Submit Loan Employee From Estar ============");
        return response;
    }

    @GetMapping("sync-test/submit")
    public EstarResponseSubmitLoanEmployeeDataDTO submitLoanEmployeeDataGet() throws JsonProcessingException {
        log.info("============ Testing Submit Loan Employee From Estar ============");
//        EstarApiService estarApiService = new EstarApiService();
        EstarSubmitLoanEmployeeDataDTO requestBody = new EstarSubmitLoanEmployeeDataDTO();
        List<EstarSubmitLoanEmployeeDataDetailDTO> requestBodyDetail = new ArrayList<>();
        EstarSubmitLoanEmployeeDataDetailDTO singleRequestBodyDetail = new EstarSubmitLoanEmployeeDataDetailDTO();
        singleRequestBodyDetail.setNoRequest("Test_123");
        singleRequestBodyDetail.setNik("test_NIK");
        singleRequestBodyDetail.setNip("test_NIP");

        EstarResponseSubmitLoanEmployeeDataDTO response = estarApiService.submitLoanEmployeeData("[testing] ",requestBody);
        log.info("============ Testing Submit Loan Employee From Estar ============");
        return response;
    }

    @GetMapping("sync-test/submit2")
    public Map<String,Object> submitLoanEmployeeDataGet2() throws JsonProcessingException {
        log.info("============ Testing Submit Loan Employee From Estar ============");
//        EstarApiService estarApiService = new EstarApiService();
        Map<String,Object> response = estarApiService.callSubmitLoanAPI("testing",
                "9019102",
                "Request"
        );

        log.info("============ Testing Submit Loan Employee From Estar ============");
        return response;
    }

    @PostMapping("sync-test/get")
    public EstarLoanEmployeeDataResultDTO getLoanEmployeeData(@RequestBody EstarRequestLoanEmployeeDataDTO request) throws JsonProcessingException {
        log.info("============ Testing Get Loan Employee From Estar ============");
//        EstarApiService estarApiService = new EstarApiService();

        EstarLoanEmployeeDataResultDTO response = estarApiService.requestLoanEmployeeData("[testing] ",request);
        log.info("============ Testing Get Loan Employee From Estar ============");
        return response;
    }

    //-----------------------------------------------------------------

}
