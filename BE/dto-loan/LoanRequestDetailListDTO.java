package com.phincon.talents.app.dto.loan;

public class LoanRequestDetailListDTO {

    private String id;
    private String feedbackFleetId;
    private String loanRequestId;
    private Double value;

    public LoanRequestDetailListDTO() {
    }

    public LoanRequestDetailListDTO(String id, String feedbackFleetId, String loanRequestId, Double value) {
        this.id = id;
        this.feedbackFleetId = feedbackFleetId;
        this.loanRequestId = loanRequestId;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedbackFleetId() {
        return feedbackFleetId;
    }

    public void setFeedbackFleetId(String feedbackFleetId) {
        this.feedbackFleetId = feedbackFleetId;
    }

    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
