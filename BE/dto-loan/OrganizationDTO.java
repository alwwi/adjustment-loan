package com.phincon.talents.app.dto.loan;

import com.phincon.talents.app.model.hr.OrganizationGroup;

import jakarta.persistence.*;
import java.util.Date;

public class OrganizationDTO {
    private String id;
    private String name;

    private String organizationLevelCode;

    private Date endDate;

    private Date startDate;

    private Integer organizationLevel;

    private Integer organizationSubLevel;

    private String organizationGroupId;

    private String organizationTracking;

    private String organizationStructureName;

    private String organizationType;

    private String remark;

    public OrganizationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganizationLevelCode() {
        return organizationLevelCode;
    }

    public void setOrganizationLevelCode(String organizationLevelCode) {
        this.organizationLevelCode = organizationLevelCode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(Integer organizationLevel) {
        this.organizationLevel = organizationLevel;
    }

    public Integer getOrganizationSubLevel() {
        return organizationSubLevel;
    }

    public void setOrganizationSubLevel(Integer organizationSubLevel) {
        this.organizationSubLevel = organizationSubLevel;
    }

    public String getOrganizationGroupId() {
        return organizationGroupId;
    }

    public void setOrganizationGroupId(String organizationGroupId) {
        this.organizationGroupId = organizationGroupId;
    }

    public String getOrganizationTracking() {
        return organizationTracking;
    }

    public void setOrganizationTracking(String organizationTracking) {
        this.organizationTracking = organizationTracking;
    }

    public String getOrganizationStructureName() {
        return organizationStructureName;
    }

    public void setOrganizationStructureName(String organizationStructureName) {
        this.organizationStructureName = organizationStructureName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
