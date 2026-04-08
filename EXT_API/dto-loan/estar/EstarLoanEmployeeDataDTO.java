package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarLoanEmployeeDataDTO {
    @JsonProperty("AgreementNo")
    private String agreementNo;

    @JsonProperty("ContractStatus")
    private String contractStatus;

    @JsonProperty("DownPayment")
    private String downPayment;

    @JsonProperty("InsNo")
    private String insNo;

    @JsonProperty("InstallmentAmount")
    private String installmentAmount;

    @JsonProperty("LastPaidDate")
    private String lastPaidDate;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("OTR")
    private String otr;

    @JsonProperty("OutstandingAR")
    private String outstandingAR;

    @JsonProperty("OutstandingInsurance")
    private String outstandingInsurance;

    @JsonProperty("PaidAmount")
    private String paidAmount;

    @JsonProperty("PrepaidAmount")
    private String prepaidAmount;

    @JsonProperty("Tenor")
    private String tenor;

    @JsonProperty("requestNo")
    private String requestNo;

    @Override
    public String toString() {
        return "EstarLoanEmployeeDataDTO{" +
                "agreementNo='" + agreementNo + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", downPayment='" + downPayment + '\'' +
                ", insNo='" + insNo + '\'' +
                ", installmentAmount='" + installmentAmount + '\'' +
                ", lastPaidDate='" + lastPaidDate + '\'' +
                ", name='" + name + '\'' +
                ", otr='" + otr + '\'' +
                ", outstandingAR='" + outstandingAR + '\'' +
                ", outstandingInsurance='" + outstandingInsurance + '\'' +
                ", paidAmount='" + paidAmount + '\'' +
                ", prepaidAmount='" + prepaidAmount + '\'' +
                ", tenor='" + tenor + '\'' +
                ", requestNo='" + requestNo + '\'' +
                '}';
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getInsNo() {
        return insNo;
    }

    public void setInsNo(String insNo) {
        this.insNo = insNo;
    }

    public String getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(String installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public String getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(String lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtr() {
        return otr;
    }

    public void setOtr(String otr) {
        this.otr = otr;
    }

    public String getOutstandingAR() {
        return outstandingAR;
    }

    public void setOutstandingAR(String outstandingAR) {
        this.outstandingAR = outstandingAR;
    }

    public String getOutstandingInsurance() {
        return outstandingInsurance;
    }

    public void setOutstandingInsurance(String outstandingInsurance) {
        this.outstandingInsurance = outstandingInsurance;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPrepaidAmount() {
        return prepaidAmount;
    }

    public void setPrepaidAmount(String prepaidAmount) {
        this.prepaidAmount = prepaidAmount;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }
}
