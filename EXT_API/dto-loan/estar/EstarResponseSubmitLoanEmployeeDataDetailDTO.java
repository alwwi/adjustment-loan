package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarResponseSubmitLoanEmployeeDataDetailDTO {

    @JsonProperty("ErrorCode")
    private String errorCode;

    @JsonProperty("MessageResponse")
    private String messageResponse;

    @Override
    public String toString() {
        return "EstarResponseSubmitLoanEmployeeDataDetailDTO{" +
                "errorCode='" + errorCode + '\'' +
                ", messageResponse='" + messageResponse + '\'' +
                '}';
    }

    public EstarResponseSubmitLoanEmployeeDataDetailDTO() {
    }

    public EstarResponseSubmitLoanEmployeeDataDetailDTO(String errorCode, String messageResponse) {
        this.errorCode = errorCode;
        this.messageResponse = messageResponse;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }
}
