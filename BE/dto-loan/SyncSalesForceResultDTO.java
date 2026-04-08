package com.phincon.talents.app.dto.loan;

public class SyncSalesForceResultDTO {
    private String status;
    private Boolean isSuccess;
    private String message;

    @Override
    public String toString() {
        return "SyncSalesForceResultDTO{" +
                "status='" + status + '\'' +
                ", isSuccess=" + isSuccess +
                ", message='" + message + '\'' +
                '}';
    }

    public SyncSalesForceResultDTO(String status, Boolean isSuccess, String message) {
        this.status = status;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public SyncSalesForceResultDTO() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
