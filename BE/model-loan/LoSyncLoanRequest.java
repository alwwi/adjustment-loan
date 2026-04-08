package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_sync_loan_request")
public class LoSyncLoanRequest extends AbstractEntityUUID {
    public final static String STATUS_FAILED = "FAILED";
    public final static String STATUS_COMPLETED = "COMPLETED";
    public final static String STATUS_IN_PROGRESS = "IN PROGRESS";

    private String requestNo;
    private LocalDateTime requestDate;

    private String requestorEmploymentId;

    private String status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private LocalDateTime paymentDate;

    private String remark;

    private String logFile;

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestorEmploymentId() {
        return requestorEmploymentId;
    }

    public void setRequestorEmploymentId(String requestorEmploymentId) {
        this.requestorEmploymentId = requestorEmploymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
