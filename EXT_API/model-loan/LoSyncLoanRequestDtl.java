package com.phincon.external.app.model.loan;

import com.phincon.external.app.model.RequestEntity;
import com.phincon.external.app.model.RequestEntityUUID;
import com.phincon.external.app.model.general.AbstractEntityUUID;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "lo_sync_loan_request_dtl")
public class LoSyncLoanRequestDtl extends RequestEntityUUID {
    private String syncLoanRequestId;
    private String employmentId;
    private String allowanceBracketId;
    private Double value;

    private Date syncDate;

    public Date getSyncDate() {
        return syncDate;
    }

    public void setSyncDate(Date syncDate) {
        this.syncDate = syncDate;
    }

    public String getSyncLoanRequestId() {
        return syncLoanRequestId;
    }

    public void setSyncLoanRequestId(String syncLoanRequestId) {
        this.syncLoanRequestId = syncLoanRequestId;
    }

    public String getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(String employmentId) {
        this.employmentId = employmentId;
    }

    public String getAllowanceBracketId() {
        return allowanceBracketId;
    }

    public void setAllowanceBracketId(String allowanceBracketId) {
        this.allowanceBracketId = allowanceBracketId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
