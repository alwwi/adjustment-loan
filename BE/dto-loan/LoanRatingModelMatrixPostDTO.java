package com.phincon.talents.app.dto.loan;

public class LoanRatingModelMatrixPostDTO {
    private String id;
    private String name;
    private String description;

    private String loanRatingModelId;

    private String paRatingId1;
    private String paRatingId2;
    private String result;

    public LoanRatingModelMatrixPostDTO(String id, String name, String description, String loanRatingModelId, String paRatingId1, String paRatingId2, String result) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.loanRatingModelId = loanRatingModelId;
        this.paRatingId1 = paRatingId1;
        this.paRatingId2 = paRatingId2;
        this.result = result;
    }

    public LoanRatingModelMatrixPostDTO() {
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
