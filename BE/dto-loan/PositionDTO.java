package com.phincon.talents.app.dto.loan;

import com.phincon.talents.app.model.hr.JobTitle;
import com.phincon.talents.app.model.hr.MPP;
import com.phincon.talents.app.model.hr.Organization;
import com.phincon.talents.app.model.hr.PositionLevel;

import jakarta.persistence.*;
import java.util.Date;

public class PositionDTO {
    private String id;
    private Date startDate;

    private Date endDate;

    private String name;

    private String organizationId;

    private Organization organization;


    private String jobTitleId;


    private String positionLevelId;

    private String mppId;

    public PositionDTO() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public String getPositionLevelId() {
        return positionLevelId;
    }

    public void setPositionLevelId(String positionLevelId) {
        this.positionLevelId = positionLevelId;
    }

    public String getMppId() {
        return mppId;
    }

    public void setMppId(String mppId) {
        this.mppId = mppId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
