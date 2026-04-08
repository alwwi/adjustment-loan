package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanRatingModelPostDTO {
    private String id;
    private String name;
    private String description;

    private String paRatingId;

    private Integer yearBefore;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String loanCategoryId;

    public LoanRatingModelPostDTO(String id, String name, String description, String paRatingId, Integer yearBefore, LocalDateTime startDate, LocalDateTime endDate, String loanCategoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.paRatingId = paRatingId;
        this.yearBefore = yearBefore;
        this.startDate = startDate;
        this.endDate = endDate;
        this.loanCategoryId = loanCategoryId;
    }
    public LoanRatingModelPostDTO() {
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
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

    public String getPaRatingId() {
        return paRatingId;
    }

    public void setPaRatingId(String paRatingId) {
        this.paRatingId = paRatingId;
    }

    public Integer getYearBefore() {
        return yearBefore;
    }

    public void setYearBefore(Integer yearBefore) {
        this.yearBefore = yearBefore;
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
