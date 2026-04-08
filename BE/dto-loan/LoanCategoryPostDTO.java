package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanCategoryPostDTO {
    private String id;
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Integer occurenceLimit;

    private Boolean isSimulation;

    private Integer approvalLevelFleet;

    private String requestCategoryTypeId;

    @Override
    public String toString() {
        return "LoanCategoryPostDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", occurenceLimit=" + occurenceLimit +
                ", isSimulation=" + isSimulation +
                ", approvalLevelFleet=" + approvalLevelFleet +
                ", requestCategoryTypeId=" + requestCategoryTypeId +
                '}';
    }

    public LoanCategoryPostDTO() {
    }

    public LoanCategoryPostDTO(String id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, Integer occurenceLimit, Boolean isSimulation,
                               Integer approvalLevelFleet, String requestCategoryTypeId
                               ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurenceLimit = occurenceLimit;
        this.isSimulation = isSimulation;
        this.approvalLevelFleet = approvalLevelFleet;
        this.requestCategoryTypeId = requestCategoryTypeId;
    }

    public String getRequestCategoryTypeId() {
        return requestCategoryTypeId;
    }

    public void setRequestCategoryTypeId(String requestCategoryTypeId) {
        this.requestCategoryTypeId = requestCategoryTypeId;
    }

    public Integer getApprovalLevelFleet() {
        return approvalLevelFleet;
    }

    public void setApprovalLevelFleet(Integer approvalLevelFleet) {
        this.approvalLevelFleet = approvalLevelFleet;
    }

    public Boolean getSimulation() {
        return isSimulation;
    }

    public void setSimulation(Boolean simulation) {
        isSimulation = simulation;
    }

    public Integer getOccurenceLimit() {
        return occurenceLimit;
    }

    public void setOccurenceLimit(Integer occurenceLimit) {
        this.occurenceLimit = occurenceLimit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
