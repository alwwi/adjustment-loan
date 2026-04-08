package com.phincon.talents.app.dto.loan;

public class LoanCheckEligibilityDTO {
    private String message;
    private Boolean isEligible;

    private Double minimalLoanUsed;

    private Double maximunLoanUsed;

    private Double totalInProgressLoan;

    private Double totalInProgressLoanPerTenor;

    private Boolean isHasSimulation;

    private LoanSimulationResultsDTO simulation;

    private String grade;

    private String ratingLastYear;

    private String ratingLast2Year;
    @Override
    public String toString() {
        return "LoanCheckEligibilityDTO{" +
                "message='" + message + '\'' +
                ", isEligible=" + isEligible +
                ", minimalLoanUsed=" + minimalLoanUsed +
                ", maximunLoanUsed=" + maximunLoanUsed +
                ", totalInProgressLoan=" + totalInProgressLoan +
                ", totalInProgressLoanPerTenor=" + totalInProgressLoanPerTenor +
                ", isHasSimulation=" + isHasSimulation +
                ", simulation=" + simulation +
                ", grade=" + grade +
                ", ratingLastYear=" + ratingLastYear +
                ", ratingLast2Year=" + ratingLast2Year +
                '}';
    }

    public Boolean getHasSimulation() {
        return isHasSimulation;
    }

    public void setHasSimulation(Boolean hasSimulation) {
        isHasSimulation = hasSimulation;
    }

    public LoanSimulationResultsDTO getSimulation() {
        return simulation;
    }

    public void setSimulation(LoanSimulationResultsDTO simulation) {
        this.simulation = simulation;
    }

    public LoanCheckEligibilityDTO() {
    }

    public LoanCheckEligibilityDTO(String message, Boolean isEligible) {
        this.message = message;
        this.isEligible = isEligible;
    }

    public Double getMinimalLoanUsed() {
        return minimalLoanUsed;
    }

    public void setMinimalLoanUsed(Double minimalLoanUsed) {
        this.minimalLoanUsed = minimalLoanUsed;
    }

    public Double getMaximunLoanUsed() {
        return maximunLoanUsed;
    }

    public void setMaximunLoanUsed(Double maximunLoanUsed) {
        this.maximunLoanUsed = maximunLoanUsed;
    }

    public Double getTotalInProgressLoan() {
        return totalInProgressLoan;
    }

    public void setTotalInProgressLoan(Double totalInProgressLoan) {
        this.totalInProgressLoan = totalInProgressLoan;
    }

    public Double getTotalInProgressLoanPerTenor() {
        return totalInProgressLoanPerTenor;
    }

    public void setTotalInProgressLoanPerTenor(Double totalInProgressLoanPerTenor) {
        this.totalInProgressLoanPerTenor = totalInProgressLoanPerTenor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getEligible() {
        return isEligible;
    }

    public void setEligible(Boolean eligible) {
        isEligible = eligible;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRatingLastYear() {
        return this.ratingLastYear;
    }

    public void setRatingLastYear(String ratingLastYear) {
        this.ratingLastYear = ratingLastYear;
    }
    
    public String getRatingLast2Year() {
        return this.ratingLast2Year;
    }

    public void setRatingLast2Year(String ratingLast2Year) {
        this.ratingLast2Year = ratingLast2Year;
    }

}
