package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phincon.talents.app.model.hr.Employment;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

public class OrganizationGroupDTO {
    private String id;

    private String name;

    private String picRecruitmentEmploymentId;

    private String picRecruitmentName;

    private String picRecruitmentNIK;

    public OrganizationGroupDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicRecruitmentEmploymentId() {
        return picRecruitmentEmploymentId;
    }

    public void setPicRecruitmentEmploymentId(String picRecruitmentEmploymentId) {
        this.picRecruitmentEmploymentId = picRecruitmentEmploymentId;
    }

    public String getPicRecruitmentName() {
        return picRecruitmentName;
    }

    public void setPicRecruitmentName(String picRecruitmentName) {
        this.picRecruitmentName = picRecruitmentName;
    }

    public String getPicRecruitmentNIK() {
        return picRecruitmentNIK;
    }

    public void setPicRecruitmentNIK(String picRecruitmentNIK) {
        this.picRecruitmentNIK = picRecruitmentNIK;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
