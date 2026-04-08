package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dao.*;
import com.phincon.talents.app.dao.pa.PARatingRepository;
import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.hr.*;
import com.phincon.talents.app.model.loan.LoAllowanceBracket;
import com.phincon.talents.app.model.pa.PARating;
import com.phincon.talents.app.repository.LoAllowanceBracketRepository;
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
import java.util.Optional;

@Service
public class LoanAllowanceBracketService {

    private static final Logger log = LogManager.getLogger(LoanAllowanceBracketService.class);

    @Autowired
    LoAllowanceBracketRepository repository;

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
    PARatingRepository paRatingRepository;

    @Transactional
    public Page<AllowanceBracketListDTO> listData(
            String page, String size,
            String name, LocalDate startDateUsed, LocalDate endDateUsed
            , String loanCategoryId
    ) {
        Page<AllowanceBracketListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String categoryIdUsed = null;
        if(loanCategoryId != null){
            categoryIdUsed = (loanCategoryId);
        }

        response = repository.listData(name,startDateUsed,endDateUsed,categoryIdUsed,pageable);

        return response;
    }

    @Transactional
    public AllowanceBracketDetailDTO getDetail(String identifier, String id) {
        AllowanceBracketDetailDTO response;

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

        if(response.getRatingId() != null){
            Optional<PARating> paRating = paRatingRepository.findById(response.getRatingId());

            if(paRating.isPresent()) {
                PARatingDTO dataUsed = new PARatingDTO();
                BeanUtils.copyProperties(paRating.get(),dataUsed);
                response.setRating(dataUsed);
            }
        }

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, AllowanceBracketPostDTO request) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoAllowanceBracket dataUsed = new LoAllowanceBracket();
        String message=null;

        //check mandatory field
        repositoryUtils.checkMandatoryField("Loan Category", LoLoanCategoryRepository.class,request.getLoanCategoryId());

        //check validation data

        //user must fill one of these -> min and max value or multiplier element name
        if(request.getValue() == null  && request.getElementName() == null && request.getMultiplier() == null){
            log.info("user must fill one of these ->  value or multiplier element name");
            throw new CustomGenericException("You must fill either (value) or (element name and multiplier)");
        }

        //if user choose min max value, it must fill both min and max value
        if(request.getMultiplier() == null && request.getElementName() == null) {
            if (request.getValue() == null ) {
                log.info("You must fill Value ");
                throw new CustomGenericException("You must fill Value ");
            }
        }

        //If user already fill min and max value, it cannot fill multiplier and element name
        if(request.getValue() != null ){
            if(request.getElementName() != null || request.getMultiplier() != null){
                log.info("Element Name and Multiplier must be blank because you already fill value !");
                throw new CustomGenericException("Element Name and Multiplier must be blank because you already fill value !");
            }
        }

        //if user choose element name, it must input both element name and multiplier
        if(request.getValue() == null){
            if(request.getElementName() == null || request.getMultiplier() == null){
                log.info("You must fill both (element name) and (multiplier) ");
                throw new CustomGenericException("You must fill both (element name) and (multiplier) ");
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

        LoAllowanceBracket dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        repository.delete(dataUsed);
        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }

}
