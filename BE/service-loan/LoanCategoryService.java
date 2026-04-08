package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.controllers.api.loan.LoanCategoryController;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.LoanCategoryDetailDTO;
import com.phincon.talents.app.dto.loan.LoanCategoryListDTO;
import com.phincon.talents.app.dto.loan.LoanCategoryPostDTO;
import com.phincon.talents.app.dto.loan.ResponseDataDTO;
import com.phincon.talents.app.model.loan.LoLoanCategory;
import com.phincon.talents.app.repository.LoLoanCategoryRepository;
import com.phincon.talents.app.repository.LoLoanRequestRepository;
import com.phincon.talents.app.utils.CustomMessageWithId;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanCategoryService {
    private static final Logger log = LogManager.getLogger(LoanCategoryService.class);

    @Autowired
    LoLoanCategoryRepository repository;

    @Autowired
    LoLoanRequestRepository loLoanRequestRepository;

    @Transactional
    public Page<LoanCategoryListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed,
            String requestCategoryName
    ) {
        Page<LoanCategoryListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(name,startDateUsed,endDateUsed,requestCategoryName,pageable);
        response.stream()
                .forEach(
                        singleResponse -> {
                            Boolean isVehicle = repository.checkIsVehicles(singleResponse.getId().toString(),LocalDate.now());
                            singleResponse.setVehicle(isVehicle == null ? false : isVehicle);
                        }
                );

        return response;
    }

    @Transactional
    public LoanCategoryDetailDTO getDetail(String identifier, String id) {
        LoanCategoryDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier,LoanCategoryPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoLoanCategory dataUsed = new LoLoanCategory();
        String message=null;

        //logic
        if(request.getId()==null){
            message="Submit New " + identifier + " Successfully";
        }else {
            dataUsed = repository.findById(request.getId()).orElseThrow(
                    ()-> new CustomGenericException( identifier + " Doesn't Exist")
            );

            if(dataUsed.getSimulation() != request.getSimulation()){
                //if user change simulation, check if there are still any in progress request
                Integer countLoanRequests = loLoanRequestRepository.countLoanCategoryNameRequestByCategoryId(dataUsed.getUuid());
                if(countLoanRequests == null){
                    countLoanRequests = 0;
                }
                if(countLoanRequests > 0){
                    //if there are any request (in progress) still use this loan category, cannot change it
                    throw new CustomGenericException( "Cannot change simulation. It's still In Progress (" + countLoanRequests.toString() + ") Requests use it ! ");
                }
            }

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

        LoLoanCategory dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
