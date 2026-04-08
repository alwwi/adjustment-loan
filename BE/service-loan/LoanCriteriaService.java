package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.*;
import com.phincon.talents.app.dao.pa.PARatingRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.hr.*;
import com.phincon.talents.app.model.hrnew.HROrganization;
import com.phincon.talents.app.model.hrnew.HROrganizationGroup;
import com.phincon.talents.app.model.hrnew.HRPosition;
import com.phincon.talents.app.model.hrnew.HRPositionLevel;
import com.phincon.talents.app.model.loan.LoCriteria;
import com.phincon.talents.app.model.loan.LoRatingModel;
import com.phincon.talents.app.model.pa.PARating;
import com.phincon.talents.app.repository.LoCriteriaRepository;
import com.phincon.talents.app.repository.LoLoanTypeRepository;
import com.phincon.talents.app.repository.LoRatingModelRepository;
import com.phincon.talents.app.utils.CustomMessageWithId;
import com.phincon.talents.app.utils.RepositoryUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanCriteriaService {
    private static final Logger log = LogManager.getLogger(LoanCriteriaService.class);

    @Autowired
    LoCriteriaRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Autowired
    OrganizationRepository organizationRepository;
    @Autowired
    OrganizationGroupRepository organizationGroupRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    PositionLevelRepository positionLevelRepository;
    @Autowired
    JobTitleRepository jobTitleRepository;
    @Autowired
    JobFamilyRepository jobFamilyRepository;
    @Autowired
    WorkLocationRepository workLocationRepository;
    @Autowired
    GradeRepository gradeRepository;
    @Autowired
    LoRatingModelRepository loRatingModelRepository;

    @Transactional
    public Page<LoanCriteriaListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed
            ,String loanTypeId
    ) {
        Page<LoanCriteriaListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String loanTypeIdUsed = null;
        if(loanTypeId != null){
            loanTypeIdUsed = (loanTypeId);
        }

        response = repository.listData(startDateUsed,endDateUsed,loanTypeIdUsed,pageable);

        return response;
    }

    @Transactional
    public LoanCriteriaDetailDTO getDetail(String identifier, String id) {
        LoanCriteriaDetailDTO response;

        response = repository.getData(id).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        if(response.getOrganizationId() != null){
            Optional<Organization> organization = organizationRepository.findById(response.getOrganizationId());

            if(organization.isPresent()) {
                OrganizationDTO dataUsed = new OrganizationDTO();
                BeanUtils.copyProperties(organization.get(),dataUsed);
                response.setOrganization(dataUsed);
            }
        }

        if(response.getOrganizationGroupId() != null){
            Optional<OrganizationGroup> organizationGroup = organizationGroupRepository.findById(response.getOrganizationGroupId());

            if(organizationGroup.isPresent()) {
                OrganizationGroupDTO dataUsed = new OrganizationGroupDTO();
                BeanUtils.copyProperties(organizationGroup.get(),dataUsed);
                response.setOrganizationGroup(dataUsed);
            }
        }

        if(response.getPositionId() != null){
            Optional<Position> position = positionRepository.findById(response.getPositionId());

            if(position.isPresent()) {
                PositionDTO dataUsed = new PositionDTO();
                BeanUtils.copyProperties(position.get(),dataUsed);
                response.setPosition(dataUsed);
            }
        }

        if(response.getPositionLevelId() != null){
            Optional<PositionLevel> positionLevel = positionLevelRepository.findById(response.getPositionLevelId());

            if(positionLevel.isPresent()) {
                PositionLevelDTO dataUsed = new PositionLevelDTO();
                BeanUtils.copyProperties(positionLevel.get(),dataUsed);
                response.setPositionLevel(dataUsed);
            }
        }

        if(response.getJobTitleId() != null){
            Optional<JobTitle> jobTitle = jobTitleRepository.findById(response.getJobTitleId());

            if(jobTitle.isPresent()) {
                JobTitleDTO dataUsed = new JobTitleDTO();
                BeanUtils.copyProperties(jobTitle.get(),dataUsed);
                response.setJobTitle(dataUsed);
            }
        }

        if(response.getJobFamilyId() != null){
            Optional<JobFamily> jobFamily = jobFamilyRepository.findById(response.getJobFamilyId());

            if(jobFamily.isPresent()) {
                JobFamilyDTO dataUsed = new JobFamilyDTO();
                BeanUtils.copyProperties(jobFamily.get(),dataUsed);
                response.setJobFamily(dataUsed);
            }
        }

        if(response.getCompanyOfficeId() != null){
            Optional<WorkLocation> workLocation = workLocationRepository.findById(response.getCompanyOfficeId());

            if(workLocation.isPresent()) {
                WorkLocationDTO dataUsed = new WorkLocationDTO();
                BeanUtils.copyProperties(workLocation.get(),dataUsed);
                response.setCompanyOffice(dataUsed);
            }
        }

        if(response.getGradeId() != null){
            Optional<Grade> grade = gradeRepository.findById(response.getGradeId());

            if(grade.isPresent()) {
                GradeDTO dataUsed = new GradeDTO();
                BeanUtils.copyProperties(grade.get(),dataUsed);
                response.setGrade(dataUsed);
            }
        }

        if(response.getRatingModelId() != null){
            Optional<LoRatingModel> loRatingModel = loRatingModelRepository.findById(response.getRatingModelId());

            if(loRatingModel.isPresent()) {
                response.setRatingModel(loRatingModel.get());
            }
        }

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanCriteriaPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoCriteria dataUsed = new LoCriteria();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Type",LoLoanTypeRepository.class,request.getLoanTypeId());
//        repositoryUtils.checkMandatoryField("Loan Rating Model", LoRatingModelRepository.class,request.getRatingId());

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

        LoCriteria dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
}
