package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanTypePostDTO {
    private String id;
    private String name;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String loanCategoryId;
    private Double dsrPercentage;
    private Integer minTenor;
    private Integer maxTenor;
    private Double interestRate;
    private Boolean isEarlySettlement;

    private Boolean isDownPayment;

    @Override
    public String toString() {
        return "LoanTypePostDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", dsrPercentage=" + dsrPercentage +
                ", minTenor=" + minTenor +
                ", maxTenor=" + maxTenor +
                ", interestRate=" + interestRate +
                ", isEarlySettlement=" + isEarlySettlement +
                ", isDownPayment=" + isDownPayment +
                '}';
    }

    public LoanTypePostDTO() {
    }

    public LoanTypePostDTO(String id, String name, String description, LocalDateTime startDate, LocalDateTime endDate, String loanCategoryId, Double dsrPercentage, Integer minTenor, Integer maxTenor, Double interestRate, Boolean isEarlySettlement, Boolean isDownPayment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loanCategoryId = loanCategoryId;
        this.dsrPercentage = dsrPercentage;
        this.minTenor = minTenor;
        this.maxTenor = maxTenor;
        this.interestRate = interestRate;
        this.isEarlySettlement = isEarlySettlement;
        this.isDownPayment = isDownPayment;
    }

    public Boolean getDownPayment() {
        return isDownPayment;
    }

    public void setDownPayment(Boolean downPayment) {
        isDownPayment = downPayment;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public Double getDsrPercentage() {
        return dsrPercentage;
    }

    public void setDsrPercentage(Double dsrPercentage) {
        this.dsrPercentage = dsrPercentage;
    }

    public Integer getMinTenor() {
        return minTenor;
    }

    public void setMinTenor(Integer minTenor) {
        this.minTenor = minTenor;
    }

    public Integer getMaxTenor() {
        return maxTenor;
    }

    public void setMaxTenor(Integer maxTenor) {
        this.maxTenor = maxTenor;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getEarlySettlement() {
        return isEarlySettlement;
    }

    public void setEarlySettlement(Boolean earlySettlement) {
        isEarlySettlement = earlySettlement;
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
