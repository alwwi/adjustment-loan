package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.*;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanHistoryDTO {

    private String loanRequestId;
    private String dataApprovalId;

    private String employeeName;

    private String positionLevelName;

    private String positionName;

    private String employeeNo;

    private String requestNo;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime requestDate;
    private String loanCategoryId;
    private String loanCategoryName;
    private String loanTypeId;
    private String loanTypeName;
    private String vehicleTypeId;
    private String vehicleTypeName;
    private String vehicleBrandId;
    private String vehicleBrandName;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDate;
    private Double loanAmount;
    private Integer tenor;
    private String loanStatus;

    private String approvalStatus;

    private String photoProfile;

    @Override
    public String toString() {
        return "LoanHistoryDTO{" +
                "loanRequestId=" + loanRequestId +
                ", dataApprovalId=" + dataApprovalId +
                ", employeeName='" + employeeName + '\'' +
                ", positionLevelName='" + positionLevelName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", employeeNo='" + employeeNo + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", requestDate=" + requestDate +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", loanCategoryName='" + loanCategoryName + '\'' +
                ", loanTypeId='" + loanTypeId + '\'' +
                ", loanTypeName='" + loanTypeName + '\'' +
                ", vehicleTypeId='" + vehicleTypeId + '\'' +
                ", vehicleTypeName='" + vehicleTypeName + '\'' +
                ", vehicleBrandId='" + vehicleBrandId + '\'' +
                ", vehicleBrandName='" + vehicleBrandName + '\'' +
                ", loanDate=" + loanDate +
                ", loanAmount=" + loanAmount +
                ", tenor=" + tenor +
                ", loanStatus='" + loanStatus + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                '}';
    }

    public LoanHistoryDTO(LoLoanRequest llr, RequestCategoryType rct, DataApproval da, VwEmpAssignment vea, LoEmployeeLoan lel, LoLoanCategory llc, LoLoanType llt,
                          LoVehicleBrand lvb, LoVehicleType lvt, String secret, String serverPath
                          ) {
        this.loanRequestId = llr.getId();
        this.dataApprovalId = da.getId();
        this.employeeName = vea.getName();
        this.requestNo = llr.getRequestNo();
        this.requestDate = llr.getRequestDate();
        this.loanCategoryId = llr.getLoanCategoryId();
        this.loanCategoryName = llc.getName();
        this.loanTypeId = llr.getLoanTypeId();
        this.loanTypeName = llt.getName();
        this.vehicleBrandId = llr.getBrandId();

        this.photoProfile = serverPath + vea.getPhotoProfile() + "&token=" + JwtWrapperService.createPathJwt(vea.getPhotoProfile(), secret);


        if(lvb != null) {
            this.vehicleTypeId = lvb.getLoanVehicleTypeId();
            this.vehicleBrandName = lvb.getName();
        }

        if(lvt != null) {
            this.vehicleTypeName = lvt.getName();
        }

        this.loanDate = llr.getLoanDate();
        this.loanAmount = llr.getAmount();
        this.tenor = llr.getTenor();

        if(lel != null) {
            this.loanStatus = lel.getStatus();
        }

        this.approvalStatus = da.getStatus();
        this.employeeNo = vea.getEmployeeNo();
        this.positionLevelName = vea.getPositionLevelName();
        this.positionName = vea.getPositionName();
    }

    public LoanHistoryDTO() {
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public void setPhotoProfile(String photoProfile) {
        this.photoProfile = photoProfile;
    }

    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionLevelName() {
        return positionLevelName;
    }

    public void setPositionLevelName(String positionLevelName) {
        this.positionLevelName = positionLevelName;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getDataApprovalId() {
        return dataApprovalId;
    }

    public void setDataApprovalId(String dataApprovalId) {
        this.dataApprovalId = dataApprovalId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanCategoryName() {
        return loanCategoryName;
    }

    public void setLoanCategoryName(String loanCategoryName) {
        this.loanCategoryName = loanCategoryName;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }

    public void setVehicleTypeName(String vehicleTypeName) {
        this.vehicleTypeName = vehicleTypeName;
    }

    public String getVehicleBrandId() {
        return vehicleBrandId;
    }

    public void setVehicleBrandId(String vehicleBrandId) {
        this.vehicleBrandId = vehicleBrandId;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
