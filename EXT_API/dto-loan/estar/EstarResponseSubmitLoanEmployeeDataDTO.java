package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarResponseSubmitLoanEmployeeDataDTO {

    public static final String RESPONSE_SUCCESS = "00";
    public static final String RESPONSE_ERROR = "02";

    @JsonProperty("SubmitLoanEmployeeResult")
    EstarResponseSubmitLoanEmployeeDataDetailDTO submitLoanEmployeeResult;

    @Override
    public String toString() {
        return "EstarResponseSubmitLoanEmployeeDataDTO{" +
                "submitLoanEmployeeResult=" + submitLoanEmployeeResult +
                '}';
    }

    public EstarResponseSubmitLoanEmployeeDataDTO(EstarResponseSubmitLoanEmployeeDataDetailDTO submitLoanEmployeeResult) {
        this.submitLoanEmployeeResult = submitLoanEmployeeResult;
    }

    public EstarResponseSubmitLoanEmployeeDataDTO() {
    }

    public EstarResponseSubmitLoanEmployeeDataDetailDTO getSubmitLoanEmployeeResult() {
        return submitLoanEmployeeResult;
    }

    public void setSubmitLoanEmployeeResult(EstarResponseSubmitLoanEmployeeDataDetailDTO submitLoanEmployeeResult) {
        this.submitLoanEmployeeResult = submitLoanEmployeeResult;
    }
}
