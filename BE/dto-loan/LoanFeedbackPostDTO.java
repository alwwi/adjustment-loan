package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanFeedbackPostDTO {
    private String id;
    private String name;
    private String description;

    private String loanCategoryId;
    private String syncElementName;
    private String valueSource;
    private Boolean isActive;

    private Integer sequenceNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @Override
    public String toString() {
        return "LoanFeedbackPostDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", syncElementName='" + syncElementName + '\'' +
                ", valueSource='" + valueSource + '\'' +
                ", isActive=" + isActive +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public LoanFeedbackPostDTO() {
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
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
