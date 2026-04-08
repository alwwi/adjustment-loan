package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class AllowanceBracketDetailDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private String name;
    private String description;
    private String loanCategoryId;

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
    private String ratingId;

    private Double value;
    private Integer multiplier;
    private String elementName;

    private OrganizationDTO organization;
    private OrganizationGroupDTO organizationGroup;
    private PositionDTO position;
    private PositionLevelDTO positionLevel;
    private JobTitleDTO jobTitle;
    private JobFamilyDTO jobFamily;
    private WorkLocationDTO companyOffice;
    private GradeDTO grade;
    private PARatingDTO rating;

    private String syncElementName;

    public AllowanceBracketDetailDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String loanCategoryId, String organizationId, String organizationGroupId, String positionId, String positionLevelId, String jobTitleId, String jobFamilyId, String workLocationType, String companyOfficeId, String gradeId, String employmentStatus, String ratingId, Double value,
                                   String elementName, Integer multiplier, String syncElementName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.loanCategoryId = loanCategoryId;
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
        this.ratingId = ratingId;
        this.value = value;
        this.elementName = elementName;
        this.multiplier = multiplier;
        this.syncElementName = syncElementName;
    }

    public String getSyncElementName() {
        return syncElementName;
    }

    public void setSyncElementName(String syncElementName) {
        this.syncElementName = syncElementName;
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

    public PARatingDTO getRating() {
        return rating;
    }

    public void setRating(PARatingDTO rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
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

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Integer multiplier) {
        this.multiplier = multiplier;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

}
