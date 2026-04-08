package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class VehicleBrandListDTO {
    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private String description;

    private String vehicleTypeId;

    private String vehicleTypeName;

    private Double employeePercentage;

    private Double companyPercentage;

    private String engineType;


    public VehicleBrandListDTO(String id, String name, LocalDateTime startDate, LocalDateTime endDate, String description, String vehicleTypeId, String vehicleTypeName,
                               Double employeePercentage, Double companyPercentage, String engineType) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.vehicleTypeId = vehicleTypeId;
        this.vehicleTypeName = vehicleTypeName;
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

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
