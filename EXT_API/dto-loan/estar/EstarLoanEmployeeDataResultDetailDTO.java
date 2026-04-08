package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EstarLoanEmployeeDataResultDetailDTO {
    @JsonProperty("ErrorCode")
    private String errorCode;

    @JsonProperty("MessageResponse")
    private String messageResponse;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("LoanEmployeeData")
    private List<EstarLoanEmployeeDataDTO> loanEmployeeData;

    @Override
    public String toString() {
        return "EstarLoanEmployeeDataResultDTO{" +
                "errorCode='" + errorCode + '\'' +
                ", messageResponse='" + messageResponse + '\'' +
                ", response='" + response + '\'' +
                ", loanEmployeeData=" + loanEmployeeData +
                '}';
    }

    public EstarLoanEmployeeDataResultDetailDTO() {
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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<EstarLoanEmployeeDataDTO> getLoanEmployeeData() {
        return loanEmployeeData;
    }

    public void setLoanEmployeeData(List<EstarLoanEmployeeDataDTO> loanEmployeeData) {
        this.loanEmployeeData = loanEmployeeData;
    }
}
