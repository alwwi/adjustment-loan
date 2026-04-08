package com.phincon.talents.app.dto.loan;

public class LoanEmploymentPerformanceRating {
    private String employeePerformanceId;
    private String employmentId;
    private Double finalScore;
    private String finalRating;
    private String finalRemark;
    private String paRatingId;

    private Double totalScore;
    private String year;

    public LoanEmploymentPerformanceRating() {
    }

    public LoanEmploymentPerformanceRating(String employeePerformanceId, String employmentId, Double finalScore, String finalRating, String finalRemark, String paRatingId, String year, Double totalScore) {
        this.employeePerformanceId = employeePerformanceId;
        this.employmentId = employmentId;
        this.finalScore = finalScore;
        this.finalRating = finalRating;
        this.finalRemark = finalRemark;
        this.paRatingId = paRatingId;
        this.year = year;
        this.totalScore = totalScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmployeePerformanceId() {
        return employeePerformanceId;
    }

    public void setEmployeePerformanceId(String employeePerformanceId) {
        this.employeePerformanceId = employeePerformanceId;
    }

    public String getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(String employmentId) {
        this.employmentId = employmentId;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public String getFinalRating() {
        return finalRating;
    }

    public void setFinalRating(String finalRating) {
        this.finalRating = finalRating;
    }

    public String getFinalRemark() {
        return finalRemark;
    }

    public void setFinalRemark(String finalRemark) {
        this.finalRemark = finalRemark;
    }

    public String getPaRatingId() {
        return paRatingId;
    }

    public void setPaRatingId(String paRatingId) {
        this.paRatingId = paRatingId;
    }
}
