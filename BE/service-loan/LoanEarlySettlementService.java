//package com.phincon.talents.app.services.loan;
//
//import com.phincon.talents.app.dto.CustomGenericException;
//import com.phincon.talents.app.dto.integration.estar.EstarEarlySettlementRemainingPostDTO;
//import com.phincon.talents.app.dto.loan.LoanCategoryPostDTO;
//import com.phincon.talents.app.dto.loan.LoanEarlySettlementPostAttachmentDTO;
//import com.phincon.talents.app.dto.loan.LoanEarlySettlementPostDTO;
//import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
//import com.phincon.talents.app.model.loan.LoEmployeeLoan;
//import com.phincon.talents.app.model.loan.LoLoanCategory;
//import com.phincon.talents.app.model.loan.LoLoanRequest;
//import com.phincon.talents.app.repository.LoEarlySettlementRepository;
//import com.phincon.talents.app.repository.LoEmployeeLoanRepository;
//import com.phincon.talents.app.repository.LoLoanCategoryRepository;
//import com.phincon.talents.app.repository.LoLoanRequestRepository;
//import com.phincon.talents.app.services.integration.EstarApiService;
//import com.phincon.talents.app.utils.CustomMessageWithId;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import jakarta.transaction.Transactional;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class LoanEarlySettlementService {
//
//    private static final Logger log = LogManager.getLogger(LoanEarlySettlementService.class);
//
//    @Autowired
//    LoEarlySettlementRepository repository;
//
//    @Autowired
//    LoEmployeeLoanRepository loEmployeeLoanRepository;
//
//    @Transactional
//    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanEarlySettlementPostDTO request) {
//        log.info("Submit Data = "+request.toString());
//        log.warn("Initialization Process Submit");
//
//        //initialize variable
//        String message=null;
//
//        //logic
//        LoEmployeeLoan loEmployeeLoanFound = loEmployeeLoanRepository.findById(request.getEmployeeLoanId()).orElseThrow(
//                ()-> new CustomGenericException(identifier + " Loan Employee Doesn't Exist")
//        );
//
//        List<LoEarlySettlementRequest> alreadyRequestedData =  repository.findAlreadyRequested(request.getEmployeeLoanId());
//        if(alreadyRequestedData.size() > 0){
//            throw new CustomGenericException("This Loan Already Requested");
//        }
//
//        LoEarlySettlementRequest dataUsed = new LoEarlySettlementRequest();
//        try{
//
//
//            //call api estar to submit data
//            EstarApiService estarApiService = new EstarApiService();
//
//            dataUsed.setEmployeeLoanId(request.getEmployeeLoanId());
//            dataUsed.setRequestDate(LocalDateTime.now());
//            dataUsed.setRequestNo(loEmployeeLoanFound.getRequestNo());
//            dataUsed.setStatus(LoEarlySettlementRequest.STATUS_ATTACHMENT_SUBMITTED_TO_ESTAR);
//            repository.save(dataUsed);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//
//        //return
//        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<CustomMessageWithId> submitAttachment(String identifier, LoanEarlySettlementPostAttachmentDTO request) {
//        log.info("Submit Data = "+request.toString());
//        log.warn("Initialization Process Submit");
//
//        //initialize variable
//        String message=null;
//
//        //logic
//        LoEarlySettlementRequest dataUsed = repository.findById(request.getEarlySettlementId()).orElseThrow(
//                ()-> new CustomGenericException(identifier + " Early Settlement Doesn't Exist")
//        );
//
//        try{
//            //call api estar to submit attachment
//            EstarApiService estarApiService = new EstarApiService();
//
//
//            dataUsed.setAttachment(request.getAttachment());
//            dataUsed.setRemark(request.getRemark());
//            dataUsed.setStatus(LoEarlySettlementRequest.STATUS_ATTACHMENT_SUBMITTED_TO_ESTAR);
//            repository.save(dataUsed);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//
//        //return
//        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<CustomMessageWithId> submitRemaining(String identifier, EstarEarlySettlementRemainingPostDTO request) {
//        log.info("Submit Data = "+request.toString());
//        log.warn("Initialization Process Submit");
//
//        //initialize variable
//        String message=null;
//
//        //logic
//        LoEarlySettlementRequest dataUsed = repository.findByRequestNoForSubmit(request.getRequestNo());
//        if(dataUsed == null) {
//            throw new CustomGenericException(identifier + " Early Settlement Doesn't Exist");
//        };
//
//        try{
//            //call api estar to submit remaining
//            EstarApiService estarApiService = new EstarApiService();
//
//
//            dataUsed.setRemainingPayment(request.getRemainingPayment());
//            dataUsed.setStatus(LoEarlySettlementRequest.STATUS_RECEIVED_FROM_ESTAR);
//            repository.save(dataUsed);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//
//
//        //return
//        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
//    }
//
//}
