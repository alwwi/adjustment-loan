package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.pa.PARatingModelRepository;
import com.phincon.talents.app.dao.pa.PARatingRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoRatingModel;
import com.phincon.talents.app.model.loan.LoRatingModelMatrix;
import com.phincon.talents.app.repository.LoLoanTypeRepository;
import com.phincon.talents.app.repository.LoRatingModelMatrixRepository;
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
public class LoRatingModelMatrixService {
    private static final Logger log = LogManager.getLogger(LoRatingModelMatrixService.class);

    @Autowired
    LoRatingModelMatrixRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Transactional
    public Page<LoanRatingModelMatrixListDTO> listData(
            String page, String size,
            String ratingModelId,
            String paRatingId1,
            String paRatingId2,
            String result
    ) {
        Page<LoanRatingModelMatrixListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(ratingModelId,paRatingId1,paRatingId2,result,pageable);

        return response;
    }

    @Transactional
    public LoanRatingModelMatrixDetailDTO getDetail(String identifier, String id) {
        LoanRatingModelMatrixDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanRatingModelMatrixPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoRatingModelMatrix dataUsed = new LoRatingModelMatrix();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Rating Model", LoRatingModelRepository.class,request.getLoanRatingModelId());
        repositoryUtils.checkMandatoryField("PA Rating Tahun 1", PARatingRepository.class,request.getPaRatingId1());
        repositoryUtils.checkMandatoryField("PA Rating Tahun 2", PARatingRepository.class,request.getPaRatingId2());

        //check validity data
        String[] resultsValid = new String[]{LoRatingModelMatrix.RESULT_BATAS_ATAS,LoRatingModelMatrix.RESULT_BATAS_BAWAH};
        if (request.getResult() != null){
            Boolean isValid = Boolean.FALSE;
            for(String resultValid : resultsValid){
                if (resultValid.equals(request.getResult())) {
                    isValid = Boolean.TRUE;
                    break;
                }
            }

            if (!isValid){
                throw new CustomGenericException("Result Not Valid !");
            }
        }

        //logic
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

        LoRatingModelMatrix dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }

}
