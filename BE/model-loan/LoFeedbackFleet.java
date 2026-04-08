package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_feedback_fleet")
public class LoFeedbackFleet extends AbstractEntityUUID {
    public final static String VALUE_SOURCE_MONTHLY_INSTALLMENT = "Monthly Installment";

    public final static String VALUE_SOURCE_MONTHLY_INSTALLMENT_MOP = "Monthly Installment MOP";
    public final static String VALUE_SOURCE_COMPANY_PERCENTAGE = "Company Percentage";
    public final static String VALUE_SOURCE_EMPLOYEE_PERCENTAGE = "Employee Percentage";
    private String name;
    private String description;

    private String loanCategoryId;
    private String syncElementName;
    private String valueSource;
    private Boolean isActive;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer sequenceNo;

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
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
