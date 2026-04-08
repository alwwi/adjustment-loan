package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanRatingModelListDTO {
    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private String description;

    private String paRatingId;

    private String paRatingName;

    private Integer yearBefore;

    private String loanCategoryId;

    private Double paRatingRangeFrom;
    private Double paRatingRangeTo;

    public LoanRatingModelListDTO() {
    }

    public LoanRatingModelListDTO(String id, String name, LocalDateTime startDate, LocalDateTime endDate, String description, String paRatingId, Integer yearBefore, String loanCategoryId, String paRatingName) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.paRatingId = paRatingId;
        this.yearBefore = yearBefore;
        this.loanCategoryId = loanCategoryId;
        this.paRatingName = paRatingName;
    }

    public LoanRatingModelListDTO(String id, String name, LocalDateTime startDate, LocalDateTime endDate, String description, String paRatingId, Integer yearBefore, String loanCategoryId, String paRatingName,
                                  Double paRatingRangeFrom,Double paRatingRangeTo) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.paRatingId = paRatingId;
        this.yearBefore = yearBefore;
        this.loanCategoryId = loanCategoryId;
        this.paRatingName = paRatingName;
        this.paRatingRangeFrom = paRatingRangeFrom;
        this.paRatingRangeTo = paRatingRangeTo;
    }

    public Double getPaRatingRangeFrom() {
        return paRatingRangeFrom;
    }

    public void setPaRatingRangeFrom(Double paRatingRangeFrom) {
        this.paRatingRangeFrom = paRatingRangeFrom;
    }

    public Double getPaRatingRangeTo() {
        return paRatingRangeTo;
    }

    public void setPaRatingRangeTo(Double paRatingRangeTo) {
        this.paRatingRangeTo = paRatingRangeTo;
    }

    public String getPaRatingName() {
        return paRatingName;
    }

    public void setPaRatingName(String paRatingName) {
        this.paRatingName = paRatingName;
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
}
