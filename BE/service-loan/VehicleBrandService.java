package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.VehicleBrandDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleBrandListDTO;
import com.phincon.talents.app.dto.loan.VehicleBrandPostDTO;
import com.phincon.talents.app.model.loan.LoVehicleBrand;
import com.phincon.talents.app.repository.LoLoanCategoryRepository;
import com.phincon.talents.app.repository.LoVehicleBrandRepository;
import com.phincon.talents.app.repository.LoVehicleTypeRepository;
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
public class VehicleBrandService {
    private static final Logger log = LogManager.getLogger(VehicleBrandService.class);

    @Autowired
    LoVehicleBrandRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Transactional
    public Page<VehicleBrandListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed
            ,String loanCategoryId, String vehicleTypeId
    ) {
        Page<VehicleBrandListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String categoryIdUsed = null;
        if(loanCategoryId != null){
            categoryIdUsed = (loanCategoryId);
        }

        String vehicleTypeIdUsed = null;
        if(vehicleTypeId != null){
            vehicleTypeIdUsed = (vehicleTypeId);
        }

        if(name != null){
            name = "%" + name + "%";
        }

        response = repository.listData(name,startDateUsed,endDateUsed,categoryIdUsed,vehicleTypeIdUsed,pageable);

        return response;
    }

    @Transactional
    public VehicleBrandDetailDTO getDetail(String identifier, String id) {
        VehicleBrandDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, VehicleBrandPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoVehicleBrand dataUsed = new LoVehicleBrand();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Category", LoLoanCategoryRepository.class,request.getLoanCategoryId());
        repositoryUtils.checkMandatoryField("Vehicle Type", LoVehicleTypeRepository.class,request.getLoanVehicleTypeId());

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

        LoVehicleBrand dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
