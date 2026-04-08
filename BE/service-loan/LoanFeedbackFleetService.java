package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoFeedbackFleet;
import com.phincon.talents.app.repository.LoFeedbackFleetRepository;
import com.phincon.talents.app.repository.LoLoanCategoryRepository;
import com.phincon.talents.app.utils.CustomMessageWithId;
import com.phincon.talents.app.utils.RepositoryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanFeedbackFleetService {
    private static final Logger log = LogManager.getLogger(LoanFeedbackFleetService.class);

    @Autowired
    LoFeedbackFleetRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Transactional
    public Page<LoanFeedbackFleetListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed,
            String loanCategoryId
    ) {
        Page<LoanFeedbackFleetListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(loanCategoryId,name,startDateUsed,endDateUsed,pageable);

        return response;
    }

    @Transactional
    public LoanFeedbackFleetDetailDTO getDetail(String identifier, String id) {
        LoanFeedbackFleetDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanFeedbackPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoFeedbackFleet dataUsed = new LoFeedbackFleet();
        String message=null;

        //logic
        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Category", LoLoanCategoryRepository.class,request.getLoanCategoryId());

//        //check if there are any same element name
//        if(request.getSyncElementName() != null) {
//            List<LoFeedbackFleet> loFeedbackFleetList = repository.getDataBySyncElementName(request.getSyncElementName(),
//                    request.getLoanCategoryId(),
//                    request.getStartDate().toLocalDate(),
//                    request.getEndDate().toLocalDate(),
//                    request.getId()
//            );
//            if (loFeedbackFleetList.size() > 0) {
//                throw new CustomGenericException("There are already same sync element name used in : " + loFeedbackFleetList.get(0).getName());
//            }
//        }

//        //check if value source same as in list
//        if(request.getValueSource() != null){
//            if(
//                    !request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT) &&
//                    !request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_COMPANY_PERCENTAGE) &&
//                    !request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_EMPLOYEE_PERCENTAGE) &&
//                    !request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT_MOP)
//            ){
//                throw new CustomGenericException("Value Source Not Found ! ");
//            }
//        }

        if(
                request.getValueSource() != null && request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT) ||
                        request.getValueSource() != null && request.getValueSource().equals(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT_MOP)
        ){
            //if value is monthlyInstallment, make sure only 1 monthly installment category
            List<String> valueToCheck = new ArrayList<>();
            valueToCheck.add(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT);
            valueToCheck.add(LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT_MOP);
            log.info("value to check : " + valueToCheck.toString());
            log.info("getLoanCategoryId : " + request.getLoanCategoryId());
            log.info("request.getStartDate().toLocalDate(): " + request.getStartDate().toLocalDate());
            log.info("request.getEndDate().toLocalDate(): " + request.getEndDate().toLocalDate());
            log.info("request.getId(): " + request.getId());
            List<LoFeedbackFleet> loFeedbackFleetValueSourceList = new ArrayList<>();
            if(valueToCheck == null || valueToCheck.size() <= 0){
                loFeedbackFleetValueSourceList = repository.getDataByValueSource(
                        request.getLoanCategoryId(),
                        request.getStartDate().toLocalDate(),
                        request.getEndDate().toLocalDate(),
                        request.getId()
                );
            }else {

                loFeedbackFleetValueSourceList = repository.getDataByValueSourceList(valueToCheck,
                        request.getLoanCategoryId(),
                        request.getStartDate().toLocalDate(),
                        request.getEndDate().toLocalDate(),
                        request.getId()
                );
            }
            if (loFeedbackFleetValueSourceList.size() > 0) {
                throw new CustomGenericException("There are already same value source used in : " + loFeedbackFleetValueSourceList.get(0).getName());
            }
        }

        if(request.getId()==null){
            message="Submit New " + identifier + " Successfully";
        }else {
            dataUsed = repository.findById(request.getId()).orElseThrow(
                    ()-> new CustomGenericException( identifier + " Doesn't Exist")
            );

            message="Edit " + identifier + " Successfully";
        }

        BeanUtils.copyProperties(request,dataUsed);
        repository.save(dataUsed);

        //return
        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> deleteData(String identifier,String id) {
        log.info("Delete " + identifier + " = "+ id.toString());
        log.warn("Initialization Process Delete");

        LoFeedbackFleet dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
