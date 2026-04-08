package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanCriteriaPostDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private String ratingId;
    private Integer minLos;
    private Integer maxLos;

    public LoanCriteriaPostDTO() {
    }

    public LoanCriteriaPostDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String loanTypeId, String organizationId, String organizationGroupId, String positionId, String positionLevelId, String jobTitleId, String jobFamilyId, String workLocationType, String companyOfficeId, String gradeId, String employmentStatus, String ratingId, Integer minLos, Integer maxLos) {
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
        this.ratingId = ratingId;
        this.minLos = minLos;
        this.maxLos = maxLos;
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

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
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
