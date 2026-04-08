package com.phincon.talents.app.controllers.api.loan;

import com.phincon.talents.app.dto.loan.VehicleTypeDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleTypeListDTO;
import com.phincon.talents.app.dto.loan.VehicleTypePostDTO;
import com.phincon.talents.app.services.loan.VehicleTypeService;
import com.phincon.talents.app.utils.CustomMessageWithId;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/loan/vehicle-type")
public class VehicleTypeController {
    private static final Logger log = LogManager.getLogger(VehicleTypeController.class);

    @Autowired
    VehicleTypeService service;

    String identifier = "Vehicle Type";

    @GetMapping("by-loan-category")
    public List<VehicleTypeListDTO> listDataByLoanCategory(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "loanCategoryId", required = true) String loanCategoryId
            ) {
        LocalDate startDateUsed=null;
        if(startDate == null || startDate.isEmpty()){
//            startDateUsed=LocalDate.now();
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        LocalDate endDateUsed=null;
        if(endDate == null || endDate.isEmpty()){
//            endDateUsed=LocalDate.now();
        }else{
            endDateUsed=LocalDate.parse(startDate);
        }

        return service.listDataByLoanCategory(name, startDateUsed,endDateUsed,loanCategoryId);
    }

    @GetMapping("by-loan-type")
    public List<VehicleTypeListDTO> listDataByLoanType(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "loanTypeId", required = true) String loanTypeId
    ) {
        LocalDate startDateUsed=null;
        if(startDate == null || startDate.isEmpty()){
//            startDateUsed=LocalDate.now();
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        LocalDate endDateUsed=null;
        if(endDate == null || endDate.isEmpty()){
//            endDateUsed=LocalDate.now();
        }else{
            endDateUsed=LocalDate.parse(startDate);
        }

        return service.listDataByLoanType(name, startDateUsed,endDateUsed,loanTypeId);
    }

    @GetMapping()
    public Page<VehicleTypeListDTO> listData(
           @RequestParam(value = "name", required = false) String name,
           @RequestParam(value = "startDate", required = false) String startDate,
           @RequestParam(value = "endDate", required = false) String endDate,

           @RequestParam(value = "page",required = false,defaultValue = "0") String page,
           @RequestParam(value = "size",required = false,defaultValue = "1000") String size
    ) {

        LocalDate startDateUsed=null;
        if(startDate == null || startDate.isEmpty()){
//            startDateUsed=LocalDate.now();
        }else{
            startDateUsed=LocalDate.parse(startDate);
        }

        LocalDate endDateUsed=null;
        if(endDate == null || endDate.isEmpty()){
//            endDateUsed=LocalDate.now();
        }else{
            endDateUsed=LocalDate.parse(startDate);
        }

        return service.listData(page,size,name, startDateUsed,endDateUsed);
    }

    @GetMapping("detail")
    public VehicleTypeDetailDTO getDetailEmployeeCertification(@RequestParam(value = "id") String id){
        return service.getDetail(identifier,id);
    }

    @PostMapping
    public ResponseEntity<CustomMessageWithId> submitData(@RequestBody VehicleTypePostDTO request){
        log.info("============ submit " + identifier + " ============");
        ResponseEntity<CustomMessageWithId> response = service.submitData(identifier,request);
        log.info("============ submit " + identifier + " ============");
        return response;
    }

    @PostMapping("delete")
    public ResponseEntity<CustomMessageWithId> deleteCertification(@RequestBody VehicleTypePostDTO request){
        log.info("============ delete " + identifier + " ============");
        ResponseEntity<CustomMessageWithId>  response= service.deleteData(identifier,request.getId());
        log.info("============ delete " + identifier + " ============");
        return response;
    }

}
