package com.phincon.external.app.controller.estar;

import com.phincon.external.app.services.estar.EstarLoanSyncService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("talents")
public class EstarLoanSyncController {

    private static final Logger log = LogManager.getLogger(EstarLoanSyncController.class);

    String identifier = "[HCEazyToEstar] ";

    @Autowired
    EstarLoanSyncService estarLoanSyncService;

    @RequestMapping(value = "estar/sync/submit", method = RequestMethod.GET)
    public ResponseEntity<?> pushData(
            @RequestParam(value = "dateFrom", required = false) String dateFrom,
            @RequestParam(value = "dateTo", required = false) String dateTo,
            @RequestParam(value = "batchSize", required = false) Integer batchSize
    ) {
        log.info("===============================================");
        log.info(identifier + "Begin Sync Loan HcEazy to Estar (Push Data) ");
        log.info("===============================================");

        LocalDate dateFromUsed = null;
        try {
            dateFromUsed = LocalDate.parse(dateFrom);
        }catch (Exception ex){
            dateFromUsed = LocalDate.parse("1990-01-01");
            log.info(identifier + "Failed to parse variable dateFrom : " + (dateFrom == null ? "null" : dateFrom));
        }
        log.info(identifier + "Use dateFrom : " + (dateFromUsed == null ? "null" : dateFromUsed));

        LocalDate dateToUsed = null;
        try {
            dateToUsed = LocalDate.parse(dateFrom);
        }catch (Exception ex){
            dateFromUsed = LocalDate.now();
            log.info(identifier + "Failed to parse variable dateTo : " + (dateTo == null ? "null" : dateTo));
        }
        log.info(identifier + "Use dateTo : " + (dateToUsed == null ? "null" : dateToUsed));

        if(batchSize == null || batchSize == 0){
            estarLoanSyncService.doLoanSync(identifier,dateFromUsed,dateToUsed, estarLoanSyncService.CALL_ALL_AT_ONE, null);
        }else {
            estarLoanSyncService.doLoanSync(identifier,dateFromUsed,dateToUsed, estarLoanSyncService.CALL_BATCH_API_EACH, batchSize);
        }


        log.info(identifier + "Finish Sync Loan HcEazy to Estar ");
        return new ResponseEntity<String>("failed", HttpStatus.OK);
    }

    @RequestMapping(value = "estar/sync/get", method = RequestMethod.GET)
    public ResponseEntity<?> getData(
            @RequestParam(value = "employmentId", required = false) String employmentId
    ) {
        log.info("===============================================");
        log.info(identifier + "Begin Sync Loan HcEazy From Estar (Get Data) ");
        log.info("===============================================");

        estarLoanSyncService.doGetLoanSync(identifier,employmentId);

        log.info(identifier + "Finish Sync Loan Get Data HcEazy From Estar ");
        return new ResponseEntity<String>("failed", HttpStatus.OK);
    }
}
