//package com.phincon.talents.app.controllers.api.loan;
//
//import com.phincon.talents.app.dto.loan.LoanCategoryPostDTO;
//import com.phincon.talents.app.dto.loan.LoanEarlySettlementPostAttachmentDTO;
//import com.phincon.talents.app.dto.loan.LoanEarlySettlementPostDTO;
//import com.phincon.talents.app.services.loan.LoanEarlySettlementService;
//import com.phincon.talents.app.utils.CustomMessageWithId;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("api/loan/early-settlement")
//public class LoanEarlySettlementController {
//    private static final Logger log = LogManager.getLogger(LoanCategoryController.class);
//
//    @Autowired
//    LoanEarlySettlementService service;
//
//    String identifier = "Loan Early Settlement";
//
//    @PostMapping("submit-data")
//    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanEarlySettlementPostDTO request){
//        log.info("============ submit " + identifier + " ============");
//        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
//        log.info("============ submit " + identifier + " ============");
//        return response;
//    }
//
//    @PostMapping("submit-attachment")
//    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody LoanEarlySettlementPostAttachmentDTO request){
//        log.info("============ submit " + identifier + " ============");
//        ResponseEntity<CustomMessageWithId> response = service.submitAttachment(identifier,request);
//        log.info("============ submit " + identifier + " ============");
//        return response;
//    }
//
//}
