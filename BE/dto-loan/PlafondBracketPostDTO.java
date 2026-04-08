package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PlafondBracketPostDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    private Double minValue;
    private Double maxValue;
    private Integer multiplier;
    private String elementName;

    @Override
    public String toString() {
        return "PlafondBracketPostDTO{" +
                "id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", organizationId=" + organizationId +
                ", organizationGroupId=" + organizationGroupId +
                ", positionId=" + positionId +
                ", positionLevelId=" + positionLevelId +
                ", jobTitleId=" + jobTitleId +
                ", jobFamilyId=" + jobFamilyId +
                ", workLocationType='" + workLocationType + '\'' +
                ", companyOfficeId=" + companyOfficeId +
                ", gradeId=" + gradeId +
                ", employmentStatus='" + employmentStatus + '\'' +
                ", ratingId=" + ratingId +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", multiplier=" + multiplier +
                ", elementName='" + elementName + '\'' +
                '}';
    }

    public PlafondBracketPostDTO() {
    }

    public PlafondBracketPostDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String loanCategoryId, String organizationId, String organizationGroupId, String positionId, String positionLevelId, String jobTitleId, String jobFamilyId, String workLocationType, String companyOfficeId, String gradeId, String employmentStatus, String ratingId, Double minValue, Double maxValue, Integer multiplier, String elementName) {
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
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.multiplier = multiplier;
        this.elementName = elementName;
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

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
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
