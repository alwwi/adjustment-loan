package com.phincon.talents.app.dto.loan;

import java.util.Date;

public class LoanRatingEmploymentDTO {
    //pa_employee_performance
    private String performanceId;
    private String employmentId;
    private Date asOfDate;
    private Date startDate;
    private Date endDate;
    private Double score;
    private String remark;

    public LoanRatingEmploymentDTO(String performanceId, String employmentId, Date asOfDate, Date startDate, Date endDate,Double score, String remark) {
        this.performanceId = performanceId;
        this.employmentId = employmentId;
        this.asOfDate = asOfDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.score = score;
        this.remark = remark;
    }

    public String getPerformanceId() {
        return performanceId;
    }

    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(String employmentId) {
        this.employmentId = employmentId;
    }

    public Date getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Date asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
