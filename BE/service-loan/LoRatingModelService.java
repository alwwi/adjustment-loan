package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.pa.PARatingModelRepository;
import com.phincon.talents.app.dao.pa.PARatingRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoLoanCategory;
import com.phincon.talents.app.model.loan.LoRatingModel;
import com.phincon.talents.app.model.loan.LoVehicleType;
import com.phincon.talents.app.repository.LoLoanCategoryRepository;
import com.phincon.talents.app.repository.LoRatingModelRepository;
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

@Service
public class LoRatingModelService {
    private static final Logger log = LogManager.getLogger(LoRatingModelService.class);

    @Autowired
    LoRatingModelRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Transactional
    public Page<LoanRatingModelListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed,
            String loanCategoryId
    ) {
        Page<LoanRatingModelListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(name,startDateUsed,endDateUsed,loanCategoryId,pageable);

        return response;
    }

    @Transactional
    public LoanRatingModelDetailDTO getDetail(String identifier, String id) {
        LoanRatingModelDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanRatingModelPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoRatingModel dataUsed = new LoRatingModel();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Category", LoLoanCategoryRepository.class,request.getLoanCategoryId());

        //check validity data
        Integer[] yearsBeforeValid = new Integer[]{1,2};
        if (request.getYearBefore() != null){
            Boolean isValid = Boolean.FALSE;
            for(Integer yearBeforeValid : yearsBeforeValid){
                if (yearBeforeValid.equals(request.getYearBefore())) {
                    isValid = Boolean.TRUE;
                    break;
                }
            }

            if (!isValid){
                throw new CustomGenericException("Years Before Not Valid !");
            }
        }

        if (request.getYearBefore() != null && request.getYearBefore().equals(1)){
            repositoryUtils.checkMandatoryField("PA Rating", PARatingRepository.class,request.getPaRatingId());
        }

        //logic
        if(request.getId()==null || request.getId().equalsIgnoreCase("")){
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

        LoRatingModel dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }

}
