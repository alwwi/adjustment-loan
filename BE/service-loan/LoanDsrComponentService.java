//package com.phincon.talents.app.services.loan;
//
//import com.phincon.talents.app.dto.CustomGenericException;
//import com.phincon.talents.app.dto.loan.LoanDsrComponentDetailDTO;
//import com.phincon.talents.app.dto.loan.LoanDsrComponentListDTO;
//import com.phincon.talents.app.dto.loan.LoanDsrComponentPostDTO;
//import com.phincon.talents.app.model.loan.LoLoanDsrComponent;
//import com.phincon.talents.app.repository.LoLoanDsrComponentRepository;
//import com.phincon.talents.app.repository.LoLoanTypeRepository;
//import com.phincon.talents.app.utils.CustomMessageWithId;
//import com.phincon.talents.app.utils.RepositoryUtils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import jakarta.transaction.Transactional;
//import java.time.LocalDate;
//
//@Service
//public class LoanDsrComponentService {
//    private static final Logger log = LogManager.getLogger(LoanDsrComponentService.class);
//
//    @Autowired
//    LoLoanDsrComponentRepository repository;
//
//    @Autowired
//    RepositoryUtils repositoryUtils;
//
//    @Transactional
//    public Page<LoanDsrComponentListDTO> listData(
//            String page, String size,
//            String name, LocalDate startDateUsed, LocalDate endDateUsed
//            ,String loanTypeId
//    ) {
//        Page<LoanDsrComponentListDTO> response;
//        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));
//
//        Long loanTypeIdUsed = null;
//        if(loanTypeId != null){
//            loanTypeIdUsed = Long.parseLong(loanTypeId);
//        }
//
//        response = repository.listData(startDateUsed,endDateUsed,loanTypeIdUsed,pageable);
//
//        return response;
//    }
//
//    @Transactional
//    public LoanDsrComponentDetailDTO getDetail(String identifier, String id) {
//        LoanDsrComponentDetailDTO response;
//
//        response = repository.getData(id).orElseThrow(
//                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));
//
//        return response;
//    }
//
//    @Transactional
//    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanDsrComponentPostDTO request) {
//        log.info("Submit Data = "+request.toString());
//        log.warn("Initialization Process Submit");
//
//        //initialize variable
//        LoLoanDsrComponent dataUsed = new LoLoanDsrComponent();
//        String message=null;
//
//        //check mandatory field
//        repositoryUtils.checkMandatoryField("Loan Type", LoLoanTypeRepository.class,request.getLoanTypeId());
//
//        //logic
//        if(request.getId()==null){
//            message="Submit New " + identifier + " Successfully";
//        }else {
//            dataUsed = repository.findById(request.getId()).orElseThrow(
//                    ()-> new CustomGenericException( identifier + " Doesn't Exist")
//            );
//            message="Edit " + identifier + " Successfully";
//        }
//
//        BeanUtils.copyProperties(request,dataUsed);
//        repository.save(dataUsed);
//
//        //return
//        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
//    }
//
//    @Transactional
//    public ResponseEntity<CustomMessageWithId> deleteData(String identifier,Long id) {
//        log.info("Delete " + identifier + " = "+ id.toString());
//        log.warn("Initialization Process Delete");
//
//        LoLoanDsrComponent dataUsed = repository.findById( id).orElseThrow(
//                ()-> new CustomGenericException( identifier + " Doesn't Exist")
//        );
//
//        repository.delete(dataUsed);
//        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
//    }
//}
