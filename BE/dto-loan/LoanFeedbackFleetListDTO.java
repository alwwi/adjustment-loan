package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanFeedbackFleetListDTO {
    private String id;
    private String name;
    private String description;

    private String loanCategoryId;

    private String loanCategoryName;
    private String syncElementName;
    private String valueSource;
    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private Integer sequenceNo;

    private Double value;

    public LoanFeedbackFleetListDTO() {
    }

    public LoanFeedbackFleetListDTO(String id, String name, String description, String loanCategoryId, String syncElementName, String valueSource, Boolean isActive, LocalDateTime startDate, LocalDateTime endDate, String loanCategoryName, Integer sequenceNo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.loanCategoryId = loanCategoryId;
        this.syncElementName = syncElementName;
        this.valueSource = valueSource;
        this.isActive = isActive;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loanCategoryName = loanCategoryName;
        this.sequenceNo = sequenceNo;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getLoanCategoryName() {
        return loanCategoryName;
    }

    public void setLoanCategoryName(String loanCategoryName) {
        this.loanCategoryName = loanCategoryName;
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

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getSyncElementName() {
        return syncElementName;
    }

    public void setSyncElementName(String syncElementName) {
        this.syncElementName = syncElementName;
    }

    public String getValueSource() {
        return valueSource;
    }

    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
