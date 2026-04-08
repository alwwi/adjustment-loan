package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "lo_loan_request_dtl")
public class LoLoanRequestDtl extends AbstractEntityUUID {

    private String feedbackFleetId;
    private String loanRequestId;
    private Double value;

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
