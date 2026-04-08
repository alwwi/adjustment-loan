package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.phincon.talents.app.model.hr.Regional;
import com.phincon.talents.app.model.hr.WorkLocation;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;

public class WorkLocationDTO {
    private String id;

    private String name;

    private String regionalId;


    private BigDecimal lat;

    private BigDecimal lng;


    private String type;

    private Integer tolleranceInMeter;

    private Integer timezone;

    private String companyOfficeId;

    private String attendanceGroupId;

    private Boolean companyOfficeFlag;

    private Boolean isActive;

    public WorkLocationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(String regionalId) {
        this.regionalId = regionalId;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTolleranceInMeter() {
        return tolleranceInMeter;
    }

    public void setTolleranceInMeter(Integer tolleranceInMeter) {
        this.tolleranceInMeter = tolleranceInMeter;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public String getCompanyOfficeId() {
        return companyOfficeId;
    }

    public void setCompanyOfficeId(String companyOfficeId) {
        this.companyOfficeId = companyOfficeId;
    }

    public String getAttendanceGroupId() {
        return attendanceGroupId;
    }

    public void setAttendanceGroupId(String attendanceGroupId) {
        this.attendanceGroupId = attendanceGroupId;
    }

    public Boolean getCompanyOfficeFlag() {
        return companyOfficeFlag;
    }

    public void setCompanyOfficeFlag(Boolean companyOfficeFlag) {
        this.companyOfficeFlag = companyOfficeFlag;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
