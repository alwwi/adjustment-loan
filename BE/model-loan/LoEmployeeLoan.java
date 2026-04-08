package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_employee_loan")
public class LoEmployeeLoan extends AbstractEntityUUID {
    public static final String STATUS_NOT_STARTED = "Not Started";

    public static final String STATUS_CANCEL = "Canceled";
    public static final String STATUS_PAID_OFF = "Paid Off";
    public static final String STATUS_IN_PROGRESS = "In Progress";

    private String requestNo;
    private LocalDateTime requestDate;

    private LocalDateTime goLiveDate;
    private String employmentId;
    private String loanCategoryId;
    private String loanTypeId;
    private String brandId;
    private LocalDateTime loanDate;
    private Double amount;
    private Integer tenor;

    private Double downPayment;
    private Double monthlyInstallment;

    private Double paidAmount;

    private Double leftInssurance;

    private LocalDateTime lastPaidDate;

    private String status;

    private Double remaining;

    private String estarAggrementNo;
    private String estarContractStatus;
    private Double estarDownPayment;
    private String estarInsNo;
    private Double estarInstallmentAmount;
    private LocalDateTime estarLastPaidDate;
    private String estarName;
    private Double estarOtr;
    private Double estarOutstandingAr;
    private Double estarOutstandingInsurance;
    private Double estarPaidAmount;
    private Double estarPrepaidAmount;
    private Integer estarTenor;

    private LocalDateTime transferDate;

    private LocalDateTime maturityDate;

    private Boolean isConfirmed;

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getEstarAggrementNo() {
        return estarAggrementNo;
    }

    public void setEstarAggrementNo(String estarAggrementNo) {
        this.estarAggrementNo = estarAggrementNo;
    }

    public String getEstarContractStatus() {
        return estarContractStatus;
    }

    public void setEstarContractStatus(String estarContractStatus) {
        this.estarContractStatus = estarContractStatus;
    }

    public Double getEstarDownPayment() {
        return estarDownPayment;
    }

    public void setEstarDownPayment(Double estarDownPayment) {
        this.estarDownPayment = estarDownPayment;
    }

    public String getEstarInsNo() {
        return estarInsNo;
    }

    public void setEstarInsNo(String estarInsNo) {
        this.estarInsNo = estarInsNo;
    }

    public Double getEstarInstallmentAmount() {
        return estarInstallmentAmount;
    }

    public void setEstarInstallmentAmount(Double estarInstallmentAmount) {
        this.estarInstallmentAmount = estarInstallmentAmount;
    }

    public LocalDateTime getEstarLastPaidDate() {
        return estarLastPaidDate;
    }

    public void setEstarLastPaidDate(LocalDateTime estarLastPaidDate) {
        this.estarLastPaidDate = estarLastPaidDate;
    }

    public String getEstarName() {
        return estarName;
    }

    public void setEstarName(String estarName) {
        this.estarName = estarName;
    }

    public Double getEstarOtr() {
        return estarOtr;
    }

    public void setEstarOtr(Double estarOtr) {
        this.estarOtr = estarOtr;
    }

    public Double getEstarOutstandingAr() {
        return estarOutstandingAr;
    }

    public void setEstarOutstandingAr(Double estarOutstandingAr) {
        this.estarOutstandingAr = estarOutstandingAr;
    }

    public Double getEstarOutstandingInsurance() {
        return estarOutstandingInsurance;
    }

    public void setEstarOutstandingInsurance(Double estarOutstandingInsurance) {
        this.estarOutstandingInsurance = estarOutstandingInsurance;
    }

    public Double getEstarPaidAmount() {
        return estarPaidAmount;
    }

    public void setEstarPaidAmount(Double estarPaidAmount) {
        this.estarPaidAmount = estarPaidAmount;
    }

    public Double getEstarPrepaidAmount() {
        return estarPrepaidAmount;
    }

    public void setEstarPrepaidAmount(Double estarPrepaidAmount) {
        this.estarPrepaidAmount = estarPrepaidAmount;
    }

    public Integer getEstarTenor() {
        return estarTenor;
    }

    public void setEstarTenor(Integer estarTenor) {
        this.estarTenor = estarTenor;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getLeftInssurance() {
        return leftInssurance;
    }

    public void setLeftInssurance(Double leftInssurance) {
        this.leftInssurance = leftInssurance;
    }

    public LocalDateTime getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(LocalDateTime lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public LocalDateTime getGoLiveDate() {
        return goLiveDate;
    }

    public void setGoLiveDate(LocalDateTime goLiveDate) {
        this.goLiveDate = goLiveDate;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public Double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(Double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
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

    public String getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(String employmentId) {
        this.employmentId = employmentId;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }
}
