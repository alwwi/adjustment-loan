package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.VehicleTypeDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleTypeListDTO;
import com.phincon.talents.app.dto.loan.VehicleTypePostDTO;
import com.phincon.talents.app.model.loan.LoVehicleType;
import com.phincon.talents.app.repository.LoVehicleTypeRepository;
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
import java.util.List;

@Service
public class VehicleTypeService {
    private static final Logger log = LogManager.getLogger(VehicleTypeService.class);

    @Autowired
    LoVehicleTypeRepository repository;

    @Transactional
    public Page<VehicleTypeListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed
    ) {
        Page<VehicleTypeListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        response = repository.listData(name,startDateUsed,endDateUsed,pageable);

        return response;
    }

    @Transactional
    public List<VehicleTypeListDTO> listDataByLoanCategory(
            String name, LocalDate startDateUsed, LocalDate endDateUsed,
            String loanCategoryId
    ) {
        List<VehicleTypeListDTO> response;

        response = repository.listDataByLoanCategory(name,startDateUsed,endDateUsed,loanCategoryId);

        return response;
    }

    @Transactional
    public List<VehicleTypeListDTO> listDataByLoanType(
            String name, LocalDate startDateUsed, LocalDate endDateUsed,
            String loanTypeId
    ) {
        List<VehicleTypeListDTO> response;

        response = repository.listDataByLoanType(name,startDateUsed,endDateUsed,loanTypeId);

        return response;
    }

    @Transactional
    public VehicleTypeDetailDTO getDetail(String identifier, String id) {
        VehicleTypeDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, VehicleTypePostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoVehicleType dataUsed = new LoVehicleType();
        String message=null;

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

        LoVehicleType dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
