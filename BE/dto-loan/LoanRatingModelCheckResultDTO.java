package com.phincon.talents.app.dto.loan;

import java.util.List;

public class LoanRatingModelCheckResultDTO {

    private List<String> ratingModelIdYear1;

    private List<String> ratingModelIdYear2Bawah;

    private List<String> ratingModelIdYear2Atas;

    private List<String> ratingModelIdYearNull;
    private String scoreYear1;
    private String scoreYear2;

    private Double scoreFinalYear1;

    @Override
    public String toString() {
        return "LoanRatingModelCheckResultDTO{" +
                "ratingModelIdYear1=" + ratingModelIdYear1 +
                ", ratingModelIdYear2Bawah=" + ratingModelIdYear2Bawah +
                ", ratingModelIdYear2Atas=" + ratingModelIdYear2Atas +
                ", ratingModelIdYearNull=" + ratingModelIdYearNull +
                ", scoreYear1='" + scoreYear1 + '\'' +
                ", scoreYear2='" + scoreYear2 + '\'' +
                ", scoreFinalYear1=" + scoreFinalYear1 +
                '}';
    }

    public List<String> getRatingModelIdYearNull() {
        return ratingModelIdYearNull;
    }

    public void setRatingModelIdYearNull(List<String> ratingModelIdYearNull) {
        this.ratingModelIdYearNull = ratingModelIdYearNull;
    }

    public List<String> getRatingModelIdYear1() {
        return ratingModelIdYear1;
    }

    public void setRatingModelIdYear1(List<String> ratingModelIdYear1) {
        this.ratingModelIdYear1 = ratingModelIdYear1;
    }

    public List<String> getRatingModelIdYear2Bawah() {
        return ratingModelIdYear2Bawah;
    }

    public void setRatingModelIdYear2Bawah(List<String> ratingModelIdYear2Bawah) {
        this.ratingModelIdYear2Bawah = ratingModelIdYear2Bawah;
    }

    public List<String> getRatingModelIdYear2Atas() {
        return ratingModelIdYear2Atas;
    }

    public void setRatingModelIdYear2Atas(List<String> ratingModelIdYear2Atas) {
        this.ratingModelIdYear2Atas = ratingModelIdYear2Atas;
    }

    public String getScoreYear1() {
        return scoreYear1;
    }

    public void setScoreYear1(String scoreYear1) {
        this.scoreYear1 = scoreYear1;
    }

    public String getScoreYear2() {
        return scoreYear2;
    }

    public void setScoreYear2(String scoreYear2) {
        this.scoreYear2 = scoreYear2;
    }

    public Double getScoreFinalYear1() {
        return scoreFinalYear1;
    }

    public void setScoreFinalYear1(Double scoreFinalYear1) {
        this.scoreFinalYear1 = scoreFinalYear1;
    }
}
