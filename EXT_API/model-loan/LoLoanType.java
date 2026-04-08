package com.phincon.external.app.model.loan;

import com.phincon.external.app.model.general.AbstractEntityUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_loan_type")
public class LoLoanType extends AbstractEntityUUID {
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    private String loanCategoryId;
    private BigDecimal dsrPercentage;
    private Integer minTenor;
    private Integer maxTenor;
    private Double interestRate;
    private Boolean isEarlySettlement;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Boolean isDownPayment;

    public Boolean getDownPayment() {
        return isDownPayment;
    }

    public void setDownPayment(Boolean downPayment) {
        isDownPayment = downPayment;
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

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public BigDecimal getDsrPercentage() {
        return dsrPercentage;
    }

    public void setDsrPercentage(BigDecimal dsrPercentage) {
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
