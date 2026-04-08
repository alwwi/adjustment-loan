package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanRatingModelMatrixListDTO {
    private String id;

    private String loanRatingModelId;

    private String paRatingId1;
    private String paRatingId2;
    private String result;

    private String paRatingName1;
    private String paRatingName2;

    private String loanRatingModelName;
    public LoanRatingModelMatrixListDTO() {
    }

    public LoanRatingModelMatrixListDTO(String id, String loanRatingModelId, String paRatingId1, String paRatingId2, String result, String loanRatingModelName, String paRatingName1, String paRatingName2) {
        this.id = id;
        this.loanRatingModelId = loanRatingModelId;
        this.paRatingId1 = paRatingId1;
        this.paRatingId2 = paRatingId2;
        this.result = result;
        this.loanRatingModelName = loanRatingModelName;
        this.paRatingName1 = paRatingName1;
        this.paRatingName2 = paRatingName2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaRatingName1() {
        return paRatingName1;
    }

    public void setPaRatingName1(String paRatingName1) {
        this.paRatingName1 = paRatingName1;
    }

    public String getPaRatingName2() {
        return paRatingName2;
    }

    public void setPaRatingName2(String paRatingName2) {
        this.paRatingName2 = paRatingName2;
    }

    public String getLoanRatingModelName() {
        return loanRatingModelName;
    }

    public void setLoanRatingModelName(String loanRatingModelName) {
        this.loanRatingModelName = loanRatingModelName;
    }

    public String getLoanRatingModelId() {
        return loanRatingModelId;
    }

    public void setLoanRatingModelId(String loanRatingModelId) {
        this.loanRatingModelId = loanRatingModelId;
    }

    public String getPaRatingId1() {
        return paRatingId1;
    }

    public void setPaRatingId1(String paRatingId1) {
        this.paRatingId1 = paRatingId1;
    }

    public String getPaRatingId2() {
        return paRatingId2;
    }

    public void setPaRatingId2(String paRatingId2) {
        this.paRatingId2 = paRatingId2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
