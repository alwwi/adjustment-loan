package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanSyncListDTO {
    private String id;

    private String requestNo;


//    @JsonFormat(pattern = "yyyy-MM-dd 00:00:00")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime requestDate;

    private String requestorEmploymentId;

    private String status;

//    @JsonFormat(pattern = "yyyy-MM")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime paymentStartDate;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime paymentEndDate;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime paymentDate;

    private String remark;

    private String logFile;

    public LoanSyncListDTO() {
    }

    public LoanSyncListDTO(String id, String requestNo, LocalDateTime requestDate, String requestorEmploymentId, String status, LocalDateTime paymentStartDate,LocalDateTime paymentEndDate,LocalDateTime paymentDate, String remark, String logFile, String serverPath, String secret) {
        this.id = id;
        this.requestNo = requestNo;
        this.requestDate = requestDate;
        this.requestorEmploymentId = requestorEmploymentId;
        this.status = status;
        this.paymentStartDate = paymentStartDate;
        this.paymentEndDate = paymentEndDate;
        this.paymentDate= paymentDate;
        this.remark = remark;
        this.logFile = logFile == null ? null : (serverPath + logFile + "&token=" + JwtWrapperService.createPathJwt(logFile,secret));
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(LocalDateTime paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public LocalDateTime getPaymentEndDate() {
        return paymentEndDate;
    }

    public void setPaymentEndDate(LocalDateTime paymentEndDate) {
        this.paymentEndDate = paymentEndDate;
    }
}
