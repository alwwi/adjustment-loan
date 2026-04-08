package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanMyLoanDetailDTO {
    private String id;

    private String requestNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime requestDate;
    private String employmentId;
    private String loanCategoryId;

    private String loanCategoryName;
    private String loanTypeId;

    private String loanTypeName;
    private String brandId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDateTo;
    private Double amount;
    private Integer tenor;

    private Integer maxTenor;

    private String status;

    private Double remaining;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime goLiveDate;
    private Double downPayment;
    private Double monthlyInstallment;

    private Double paidAmount;

    private Double leftInssurance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime lastPaidDate;

    private String estarAggrementNo;
    private String estarContractStatus;
    private Double estarDownPayment;
    private String estarInsNo;
    private Double estarInstallmentAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime estarLastPaidDate;
    private String estarName;
    private Double estarOtr;
    private Double estarOutstandingAr;
    private Double estarOutstandingInsurance;
    private Double estarPaidAmount;
    private Double estarPrepaidAmount;
    private Integer estarTenor;

    private Boolean isEarlySettlement;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime transferDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime maturityDate;

    private Boolean isConfirmed;

    public LoanMyLoanDetailDTO(String id, String requestNo, LocalDateTime requestDate, String employmentId, String loanCategoryId, String loanTypeId, String brandId, LocalDateTime loanDate, Double amount, Integer tenor, String status, Double remaining
            ,String loanCategoryName,String loanTypeName,
                               LocalDateTime goLiveDate,Double downPayment,Double monthlyInstallment,Double paidAmount,Double leftInssurance,LocalDateTime lastPaidDate,
                               Integer maxTenor,
                               String estarAggrementNo,
                                String estarContractStatus,
                                Double estarDownPayment,
                                String estarInsNo,
                                Double estarInstallmentAmount,
                                LocalDateTime estarLastPaidDate,
                                String estarName,
                                Double estarOtr,
                                Double estarOutstandingAr,
                                Double estarOutstandingInsurance,
                                Double estarPaidAmount,
                                Double estarPrepaidAmount,
                                Integer estarTenor,
                               Boolean isEarlySettlement,
                               LocalDateTime transferDate,
                               LocalDateTime maturityDate,
                               Boolean isConfirmed
                               ) {
        this.id = id;
        this.requestNo = requestNo;
        this.requestDate = requestDate;
        this.employmentId = employmentId;
        this.loanCategoryId = loanCategoryId;
        this.loanTypeId = loanTypeId;
        this.brandId = brandId;

        this.loanDate = loanDate;
        this.loanDateFrom = loanDate;
        this.loanDateTo = loanDate.plusMonths(tenor).toLocalDate().atStartOfDay();

        this.amount = amount;
        this.tenor = tenor;
        this.maxTenor = maxTenor;
        this.status = status;
        this.remaining = remaining;

        this.loanCategoryName = loanCategoryName;
        this.loanTypeName = loanTypeName;
        this.goLiveDate = goLiveDate;
        this.downPayment = downPayment;
        this.monthlyInstallment = monthlyInstallment;

        this.paidAmount = paidAmount;

        this.leftInssurance = leftInssurance;

        this.lastPaidDate = lastPaidDate;

        this.estarAggrementNo = estarAggrementNo;
        this.estarContractStatus = estarContractStatus;
        this.estarDownPayment = estarDownPayment;
        this.estarInsNo = estarInsNo;
        this.estarInstallmentAmount = estarInstallmentAmount;
        this.estarLastPaidDate = estarLastPaidDate;
        this.estarName = estarName;
        this.estarOtr = estarOtr;
        this.estarOutstandingAr = estarOutstandingAr;
        this.estarOutstandingInsurance = estarOutstandingInsurance;
        this.estarPaidAmount = estarPaidAmount;
        this.estarPrepaidAmount = estarPrepaidAmount;
        this.estarTenor = estarTenor;
        this.isEarlySettlement = isEarlySettlement;

        this.transferDate = transferDate;
        this.maturityDate = maturityDate;

        this.isConfirmed = isConfirmed;
    }

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

    public Boolean getEarlySettlement() {
        return isEarlySettlement;
    }

    public void setEarlySettlement(Boolean earlySettlement) {
        isEarlySettlement = earlySettlement;
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

    public LocalDateTime getLoanDateFrom() {
        return loanDateFrom;
    }

    public void setLoanDateFrom(LocalDateTime loanDateFrom) {
        this.loanDateFrom = loanDateFrom;
    }

    public LocalDateTime getLoanDateTo() {
        return loanDateTo;
    }

    public void setLoanDateTo(LocalDateTime loanDateTo) {
        this.loanDateTo = loanDateTo;
    }

    public Integer getMaxTenor() {
        return maxTenor;
    }

    public void setMaxTenor(Integer maxTenor) {
        this.maxTenor = maxTenor;
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

    public String getLoanCategoryName() {
        return loanCategoryName;
    }

    public void setLoanCategoryName(String loanCategoryName) {
        this.loanCategoryName = loanCategoryName;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
