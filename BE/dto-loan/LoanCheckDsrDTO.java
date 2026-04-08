package com.phincon.talents.app.dto.loan;

public class LoanCheckDsrDTO {
    private String loanTypeId;
    private Double monthlyInstallment;
    private String employeeId;

    private Double dsrLimit;
    private Boolean isEligible;

    private String message;

    private Double monthlyInstallmentBefore;

    private Double monthlyInstallmentInProgressRequest;

    public LoanCheckDsrDTO() {
    }

    public LoanCheckDsrDTO(String loanTypeId, Double monthlyInstallment, String employeeId, Double dsrLimit, Boolean isEligible) {
        this.loanTypeId = loanTypeId;
        this.monthlyInstallment = monthlyInstallment;
        this.employeeId = employeeId;
        this.dsrLimit = dsrLimit;
        this.isEligible = isEligible;
    }

    public Double getMonthlyInstallmentBefore() {
        return monthlyInstallmentBefore;
    }

    public void setMonthlyInstallmentBefore(Double monthlyInstallmentBefore) {
        this.monthlyInstallmentBefore = monthlyInstallmentBefore;
    }

    public Double getMonthlyInstallmentInProgressRequest() {
        return monthlyInstallmentInProgressRequest;
    }

    public void setMonthlyInstallmentInProgressRequest(Double monthlyInstallmentInProgressRequest) {
        this.monthlyInstallmentInProgressRequest = monthlyInstallmentInProgressRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public Double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(Double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getDsrLimit() {
        return dsrLimit;
    }

    public void setDsrLimit(Double dsrLimit) {
        this.dsrLimit = dsrLimit;
    }

    public Boolean getEligible() {
        return isEligible;
    }

    public void setEligible(Boolean eligible) {
        isEligible = eligible;
    }
}
