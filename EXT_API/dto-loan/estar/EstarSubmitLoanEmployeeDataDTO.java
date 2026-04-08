package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EstarSubmitLoanEmployeeDataDTO {

    @JsonProperty("SubmitLoanEmployeeData")
    private List<EstarSubmitLoanEmployeeDataDetailDTO> submitLoanEmployeeData;

    @Override
    public String toString() {
        return "EstarSubmitLoanEmployeeDataDTO{" +
                "submitLoanEmployeeData=" + submitLoanEmployeeData +
                '}';
    }

    public EstarSubmitLoanEmployeeDataDTO() {
    }

    public List<EstarSubmitLoanEmployeeDataDetailDTO> getSubmitLoanEmployeeData() {
        return submitLoanEmployeeData;
    }

    public void setSubmitLoanEmployeeData(List<EstarSubmitLoanEmployeeDataDetailDTO> submitLoanEmployeeData) {
        this.submitLoanEmployeeData = submitLoanEmployeeData;
    }
}
