package com.phincon.external.app.model.loan;

import com.phincon.external.app.model.RequestEntity;
import com.phincon.external.app.model.RequestEntityUUID;
import com.phincon.external.app.model.general.AbstractEntityUUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "lo_loan_request_dtl")
public class LoLoanRequestDtl extends RequestEntityUUID {

    private String feedbackFleetId;
    private String loanRequestId;
    private Double value;

    private Date syncDate;

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
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
