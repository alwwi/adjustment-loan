package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanCategoryDetailDTO {
    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private Integer occurenceLimit;

    private Boolean isSimulation;

    private String description;

    private Integer approvalLevelFleet;

    private String requestCategoryTypeId;
    private String requestCategoryTypeName;

    public LoanCategoryDetailDTO(String id, String name, LocalDateTime startDate, LocalDateTime endDate, Integer occurenceLimit, Boolean isSimulation, String description,
                                 Integer approvalLevelFleet,String requestCategoryTypeId, String requestCategoryTypeName
                                 ) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurenceLimit = occurenceLimit;
        this.isSimulation = isSimulation;
        this.description = description;
        this.approvalLevelFleet = approvalLevelFleet;
        this.requestCategoryTypeId = requestCategoryTypeId;
        this.requestCategoryTypeName = requestCategoryTypeName;
    }

    public String getRequestCategoryTypeId() {
        return requestCategoryTypeId;
    }

    public void setRequestCategoryTypeId(String requestCategoryTypeId) {
        this.requestCategoryTypeId = requestCategoryTypeId;
    }

    public String getRequestCategoryTypeName() {
        return requestCategoryTypeName;
    }

    public void setRequestCategoryTypeName(String requestCategoryTypeName) {
        this.requestCategoryTypeName = requestCategoryTypeName;
    }

    public Integer getApprovalLevelFleet() {
        return approvalLevelFleet;
    }

    public void setApprovalLevelFleet(Integer approvalLevelFleet) {
        this.approvalLevelFleet = approvalLevelFleet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
