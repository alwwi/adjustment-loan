package com.phincon.talents.app.dto.loan;

public class LoanSimulationResultsDTO {
    private Double loanAmount;
    private Integer tenorInMonth;
    private Double interestRate;
    private Double monthlyInstallment;

    @Override
    public String toString() {
        return "LoanSimulationResultsDTO{" +
                "loanAmount=" + loanAmount +
                ", tenorInMonth=" + tenorInMonth +
                ", interestRate=" + interestRate +
                ", monthlyInstallment=" + monthlyInstallment +
                '}';
    }

    public LoanSimulationResultsDTO(Double loanAmount, Integer tenorInMonth, Double interestRate, Double monthlyInstallment) {
        this.loanAmount = loanAmount;
        this.tenorInMonth = tenorInMonth;
        this.interestRate = interestRate;
        this.monthlyInstallment = monthlyInstallment;
    }

    public LoanSimulationResultsDTO() {
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTenorInMonth() {
        return tenorInMonth;
    }

    public void setTenorInMonth(Integer tenorInMonth) {
        this.tenorInMonth = tenorInMonth;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(Double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }
}
