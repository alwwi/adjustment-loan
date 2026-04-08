package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.DisciplineRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.LoanDisciplineDetailDTO;
import com.phincon.talents.app.dto.loan.LoanDisciplineListDTO;
import com.phincon.talents.app.dto.loan.LoanDisciplinePostDTO;
import com.phincon.talents.app.model.hr.Discipline;
import com.phincon.talents.app.model.loan.LoDiscipline;
import com.phincon.talents.app.repository.LoDisciplineRepository;
import com.phincon.talents.app.repository.LoLoanTypeRepository;
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
import java.util.List;

@Service
public class LoanDisciplineService {
    private static final Logger log = LogManager.getLogger(LoanDisciplineService.class);

    @Autowired
    LoDisciplineRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Transactional
    public Page<LoanDisciplineListDTO> listData(
            String page, String size,
             LocalDate startDateUsed, LocalDate endDateUsed
            ,String loanTypeId
    ) {
        Page<LoanDisciplineListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String loanTypeIdUsed = null;
        if(loanTypeId != null){
            loanTypeIdUsed = (loanTypeId);
        }

        response = repository.listData(startDateUsed,endDateUsed,loanTypeIdUsed,pageable);

        return response;
    }

    @Transactional
    public LoanDisciplineDetailDTO getDetail(String identifier, String id) {
        LoanDisciplineDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanDisciplinePostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoDiscipline dataUsed = new LoDiscipline();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Type", LoLoanTypeRepository.class,request.getLoanTypeId());
        repositoryUtils.checkMandatoryField("Discipline", DisciplineRepository.class,request.getDisciplineId());

        //check if there are any same startDate, endDate, and loanTypeId
        List<LoanDisciplineDetailDTO> sameDataFounded = repository.checkSameDataAlreadyAdded(request.getLoanTypeId(),request.getStartDate(),request.getEndDate(),request.getDisciplineId(),request.getId());
        if(sameDataFounded.size() > 0){
            throw new CustomGenericException( "Same Discipline Found (" + sameDataFounded.get(0).getDisciplineName() + ") ! ");
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

        LoDiscipline dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
