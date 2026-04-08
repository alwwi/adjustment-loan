package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_early_settlement_request")
public class LoEarlySettlementRequest extends AbstractEntityUUID {

    private String employeeLoanId;
    private String categoryId;

    private String loanRequestNo;

    private String requestNo;
    private LocalDateTime requestDate;

    private Double remainingPayment;
    private String remark;


    public String getEmployeeLoanId() {
        return employeeLoanId;
    }

    public void setEmployeeLoanId(String employeeLoanId) {
        this.employeeLoanId = employeeLoanId;
    }

    public String getLoanRequestNo() {
        return loanRequestNo;
    }

    public void setLoanRequestNo(String loanRequestNo) {
        this.loanRequestNo = loanRequestNo;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(Double remainingPayment) {
        this.remainingPayment = remainingPayment;
    }
}
