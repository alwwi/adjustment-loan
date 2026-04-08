package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_loan_request")
public class LoLoanRequest extends AbstractEntityUUID {

    private String requestNo;
    private LocalDateTime requestDate;
    private String remark;
    private String employmentId;
    private String loanCategoryId;
    private String loanTypeId;
    private String brandId;
    private LocalDateTime loanDate;
    private Double amount;
    private Double amountOtr;
    private Integer tenor;

    private Double downPayment;
    private Double monthlyInstallment;

    private String categoryId;

    private Boolean needSync;

    private Boolean needSyncEstar;

    private String resultSync;

    private String resultSyncEstar;

    private LocalDateTime syncDate;

    private LocalDateTime syncDateEstar;

//    private Double angsuranPerBulan;
    private Double premiAsuransi;
    private Double angsuranUnitPerBulan;
    private Double acpCash;
    private Double porsiAngsuranKaryawan;
    private Double porsiAngsuranPerusahaan;

    private Boolean evidence;

    @Override
    public String toString() {
        return "LoLoanRequest{" +
                "requestNo='" + requestNo + '\'' +
                ", requestDate=" + requestDate +
                ", remark='" + remark + '\'' +
                ", employmentId=" + employmentId +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", loanTypeId='" + loanTypeId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", loanDate=" + loanDate +
                ", amount=" + amount +
                ", amountOtr=" + amountOtr +
                ", tenor=" + tenor +
                ", downPayment=" + downPayment +
                ", monthlyInstallment=" + monthlyInstallment +
                ", categoryId=" + categoryId +
                ", needSync=" + needSync +
                ", needSyncEstar=" + needSyncEstar +
                ", resultSync='" + resultSync + '\'' +
                ", resultSyncEstar='" + resultSyncEstar + '\'' +
                ", syncDate=" + syncDate +
                ", syncDateEstar=" + syncDateEstar +
                ", premiAsuransi=" + premiAsuransi +
                ", angsuranUnitPerBulan=" + angsuranUnitPerBulan +
                ", acpCash=" + acpCash +
                ", porsiAngsuranKaryawan=" + porsiAngsuranKaryawan +
                ", porsiAngsuranPerusahaan=" + porsiAngsuranPerusahaan +
                '}';
    }

    //    public Double getAngsuranPerBulan() {
//        return angsuranPerBulan;
//    }
//
//    public void setAngsuranPerBulan(Double angsuranPerBulan) {
//        this.angsuranPerBulan = angsuranPerBulan;
//    }


    public Boolean getEvidence() {
        return evidence;
    }

    public void setEvidence(Boolean evidence) {
        this.evidence = evidence;
    }

    public String getResultSyncEstar() {
        return resultSyncEstar;
    }

    public void setResultSyncEstar(String resultSyncEstar) {
        this.resultSyncEstar = resultSyncEstar;
    }

    public LocalDateTime getSyncDateEstar() {
        return syncDateEstar;
    }

    public void setSyncDateEstar(LocalDateTime syncDateEstar) {
        this.syncDateEstar = syncDateEstar;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }

    public Double getPremiAsuransi() {
        return premiAsuransi;
    }

    public void setPremiAsuransi(Double premiAsuransi) {
        this.premiAsuransi = premiAsuransi;
    }

    public Double getAngsuranUnitPerBulan() {
        return angsuranUnitPerBulan;
    }

    public void setAngsuranUnitPerBulan(Double angsuranUnitPerBulan) {
        this.angsuranUnitPerBulan = angsuranUnitPerBulan;
    }

    public Double getAcpCash() {
        return acpCash;
    }

    public void setAcpCash(Double acpCash) {
        this.acpCash = acpCash;
    }

    public Double getPorsiAngsuranKaryawan() {
        return porsiAngsuranKaryawan;
    }

    public void setPorsiAngsuranKaryawan(Double porsiAngsuranKaryawan) {
        this.porsiAngsuranKaryawan = porsiAngsuranKaryawan;
    }

    public Double getPorsiAngsuranPerusahaan() {
        return porsiAngsuranPerusahaan;
    }

    public void setPorsiAngsuranPerusahaan(Double porsiAngsuranPerusahaan) {
        this.porsiAngsuranPerusahaan = porsiAngsuranPerusahaan;
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

    public LocalDateTime getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(LocalDateTime syncDate) {
        this.syncDate = syncDate;
    }

    public Boolean getNeedSyncEstar() {
        return needSyncEstar;
    }

    public void setNeedSyncEstar(Boolean needSyncEstar) {
        this.needSyncEstar = needSyncEstar;
    }

    public String getResultSync() {
        return resultSync;
    }

    public void setResultSync(String resultSync) {
        this.resultSync = resultSync;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Double getAmountOtr() {
        return this.amountOtr;
    }

    public void setAmountOtr(Double amountOtr) {
        this.amountOtr = amountOtr;
    }
}
