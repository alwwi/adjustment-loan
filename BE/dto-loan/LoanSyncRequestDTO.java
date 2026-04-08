package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanSyncRequestDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String paymentStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String paymentEndDate;

    @JsonFormat(pattern = "yyyy-MM")
    private String paymentDate;

    @Override
    public String toString() {
        return "LoanSyncRequestDTO{" +
                "paymentStartDate='" + paymentStartDate + '\'' +
                ", paymentEndDate='" + paymentEndDate + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }

    public LoanSyncRequestDTO() {
    }

    public LoanSyncRequestDTO(String paymentStartDate, String paymentEndDate, String paymentDate) {
        this.paymentStartDate = paymentStartDate;
        this.paymentEndDate = paymentEndDate;
        this.paymentDate = paymentDate;
    }

    public String getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(String paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public String getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(String paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}
