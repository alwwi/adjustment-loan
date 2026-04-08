package com.phincon.talents.app.dto.loan;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

public class JobTitleDTO {
    private String id;
    private String name;

    private String description;

    private Date startDate;

    private Date endDate;

    private String jobFamilyId;

    private Integer contractMonth;

    public JobTitleDTO() {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getJobFamilyId() {
        return jobFamilyId;
    }

    public void setJobFamilyId(String jobFamilyId) {
        this.jobFamilyId = jobFamilyId;
    }

    public Integer getContractMonth() {
        return contractMonth;
    }

    public void setContractMonth(Integer contractMonth) {
        this.contractMonth = contractMonth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
