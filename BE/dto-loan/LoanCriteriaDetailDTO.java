package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.model.hr.*;
import com.phincon.talents.app.model.hrnew.HROrganization;
import com.phincon.talents.app.model.hrnew.HROrganizationGroup;
import com.phincon.talents.app.model.hrnew.HRPosition;
import com.phincon.talents.app.model.hrnew.HRPositionLevel;
import com.phincon.talents.app.model.loan.LoRatingModel;
import com.phincon.talents.app.model.pa.PARating;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanCriteriaDetailDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private String name;
    private String description;
    private String loanTypeId;

    private String organizationId;
    private String organizationGroupId;
    private String positionId;
    private String positionLevelId;
    private String jobTitleId;
    private String jobFamilyId;

    private String workLocationType;
    private String companyOfficeId;
    private String gradeId;
    private String employmentStatus;
    private String ratingModelId;
    private Integer minLos;
    private Integer maxLos;

    private OrganizationDTO organization;
    private OrganizationGroupDTO organizationGroup;
    private PositionDTO position;
    private PositionLevelDTO positionLevel;
    private JobTitleDTO jobTitle;
    private JobFamilyDTO jobFamily;
    private WorkLocationDTO companyOffice;
    private GradeDTO grade;
    private LoRatingModel ratingModel;


    public LoanCriteriaDetailDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String loanTypeId, String organizationId, String organizationGroupId, String positionId, String positionLevelId, String jobTitleId, String jobFamilyId, String workLocationType, String companyOfficeId, String gradeId, String employmentStatus, String ratingModelId, Integer minLos, Integer maxLos) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.loanTypeId = loanTypeId;
        this.organizationId = organizationId;
        this.organizationGroupId = organizationGroupId;
        this.positionId = positionId;
        this.positionLevelId = positionLevelId;
        this.jobTitleId = jobTitleId;
        this.jobFamilyId = jobFamilyId;
        this.workLocationType = workLocationType;
        this.companyOfficeId = companyOfficeId;
        this.gradeId = gradeId;
        this.employmentStatus = employmentStatus;
        this.ratingModelId = ratingModelId;
        this.minLos = minLos;
        this.maxLos = maxLos;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public OrganizationGroupDTO getOrganizationGroup() {
        return organizationGroup;
    }

    public void setOrganizationGroup(OrganizationGroupDTO organizationGroup) {
        this.organizationGroup = organizationGroup;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public PositionLevelDTO getPositionLevel() {
        return positionLevel;
    }

    public void setPositionLevel(PositionLevelDTO positionLevel) {
        this.positionLevel = positionLevel;
    }

    public JobTitleDTO getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitleDTO jobTitle) {
        this.jobTitle = jobTitle;
    }

    public JobFamilyDTO getJobFamily() {
        return jobFamily;
    }

    public void setJobFamily(JobFamilyDTO jobFamily) {
        this.jobFamily = jobFamily;
    }

    public WorkLocationDTO getCompanyOffice() {
        return companyOffice;
    }

    public void setCompanyOffice(WorkLocationDTO companyOffice) {
        this.companyOffice = companyOffice;
    }

    public GradeDTO getGrade() {
        return grade;
    }

    public void setGrade(GradeDTO grade) {
        this.grade = grade;
    }

    public LoRatingModel getRatingModel() {
        return ratingModel;
    }

    public void setRatingModel(LoRatingModel ratingModel) {
        this.ratingModel = ratingModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationGroupId() {
        return organizationGroupId;
    }

    public void setOrganizationGroupId(String organizationGroupId) {
        this.organizationGroupId = organizationGroupId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionLevelId() {
        return positionLevelId;
    }

    public void setPositionLevelId(String positionLevelId) {
        this.positionLevelId = positionLevelId;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getJobFamilyId() {
        return jobFamilyId;
    }

    public void setJobFamilyId(String jobFamilyId) {
        this.jobFamilyId = jobFamilyId;
    }

    public String getWorkLocationType() {
        return workLocationType;
    }

    public void setWorkLocationType(String workLocationType) {
        this.workLocationType = workLocationType;
    }

    public String getCompanyOfficeId() {
        return companyOfficeId;
    }

    public void setCompanyOfficeId(String companyOfficeId) {
        this.companyOfficeId = companyOfficeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public String getRatingModelId() {
        return ratingModelId;
    }

    public void setRatingModelId(String ratingModelId) {
        this.ratingModelId = ratingModelId;
    }

    public Integer getMinLos() {
        return minLos;
    }

    public void setMinLos(Integer minLos) {
        this.minLos = minLos;
    }

    public Integer getMaxLos() {
        return maxLos;
    }

    public void setMaxLos(Integer maxLos) {
        this.maxLos = maxLos;
    }
}
