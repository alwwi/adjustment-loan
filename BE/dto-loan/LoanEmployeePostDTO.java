package com.phincon.talents.app.dto.loan;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanEmployeePostDTO {
    private String requestNo;

    private Double amount;
    private Integer tenor;
//    private String status;
    private Double remaining;

    private LocalDateTime goLiveDate;

    private LocalDateTime lastPaidDate;

    private Double leftInssurance;

    private Double paidAmount;

    public LoanEmployeePostDTO() {
    }

    public LoanEmployeePostDTO(String requestNo, Double amount, Integer tenor, Double remaining) {
        this.requestNo = requestNo;
        this.amount = amount;
        this.tenor = tenor;
        this.remaining = remaining;
    }

    public LoanEmployeePostDTO(String requestNo, Double amount, Integer tenor, Double remaining, LocalDateTime goLiveDate, LocalDateTime lastPaidDate) {
        this.requestNo = requestNo;
        this.amount = amount;
        this.tenor = tenor;
        this.remaining = remaining;
        this.goLiveDate = goLiveDate;
        this.lastPaidDate = lastPaidDate;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getLeftInssurance() {
        return leftInssurance;
    }

    public void setLeftInssurance(Double leftInssurance) {
        this.leftInssurance = leftInssurance;
    }

    public LocalDateTime getLastPaidDate() {
        return lastPaidDate;
    }

    public void setLastPaidDate(LocalDateTime lastPaidDate) {
        this.lastPaidDate = lastPaidDate;
    }

    public LocalDateTime getGoLiveDate() {
        return goLiveDate;
    }

    public void setGoLiveDate(LocalDateTime goLiveDate) {
        this.goLiveDate = goLiveDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
}
