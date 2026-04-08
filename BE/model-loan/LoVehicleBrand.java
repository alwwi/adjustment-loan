package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_vehicle_brand")
public class LoVehicleBrand extends AbstractEntityUUID {

    private String name;
    private String description;

    private String loanCategoryId;
    private String loanVehicleTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Double companyPercentage;
    private Double employeePercentage;

    private String engineType;

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Double getCompanyPercentage() {
        return companyPercentage;
    }

    public void setCompanyPercentage(Double companyPercentage) {
        this.companyPercentage = companyPercentage;
    }

    public Double getEmployeePercentage() {
        return employeePercentage;
    }

    public void setEmployeePercentage(Double employeePercentage) {
        this.employeePercentage = employeePercentage;
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

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanVehicleTypeId() {
        return loanVehicleTypeId;
    }

    public void setLoanVehicleTypeId(String loanVehicleTypeId) {
        this.loanVehicleTypeId = loanVehicleTypeId;
    }
}
