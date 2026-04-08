package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class VehicleBrandPostDTO {
    private String id;
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String loanCategoryId;

    private String loanVehicleTypeId;

    private Double employeePercentage;

    private Double companyPercentage;

    private String engineType;

    @Override
    public String toString() {
        return "VehicleBrandPostDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", loanVehicleTypeId='" + loanVehicleTypeId + '\'' +
                ", employeePercentage=" + employeePercentage +
                ", companyPercentage=" + companyPercentage +
                ", engineType='" + engineType + '\'' +
                '}';
    }

    public VehicleBrandPostDTO() {
    }

    public VehicleBrandPostDTO(String id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, String loanCategoryId, String loanVehicleTypeId,
                               Double employeePercentage, Double companyPercentage,String engineType
                               ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loanCategoryId = loanCategoryId;
        this.loanVehicleTypeId= loanVehicleTypeId;
        this.employeePercentage = employeePercentage;
        this.companyPercentage = companyPercentage;
        this.engineType = engineType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public Double getEmployeePercentage() {
        return employeePercentage;
    }

    public void setEmployeePercentage(Double employeePercentage) {
        this.employeePercentage = employeePercentage;
    }

    public Double getCompanyPercentage() {
        return companyPercentage;
    }

    public void setCompanyPercentage(Double companyPercentage) {
        this.companyPercentage = companyPercentage;
    }

    public String getLoanVehicleTypeId() {
        return loanVehicleTypeId;
    }

    public void setLoanVehicleTypeId(String loanVehicleTypeId) {
        this.loanVehicleTypeId = loanVehicleTypeId;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
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
