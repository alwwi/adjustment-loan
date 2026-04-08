package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarLoanEmployeeDataResultDTO {

    @JsonProperty("LoanEmployeeDataResult")
    private EstarLoanEmployeeDataResultDetailDTO loanEmployeeDataResult;

    public EstarLoanEmployeeDataResultDTO() {
    }

    @Override
    public String toString() {
        return "EstarLoanEmployeeDataResultDTO{" +
                "loanEmployeeDataResult=" + loanEmployeeDataResult +
                '}';
    }

    public EstarLoanEmployeeDataResultDetailDTO getLoanEmployeeDataResult() {
        return loanEmployeeDataResult;
    }

    public void setLoanEmployeeDataResult(EstarLoanEmployeeDataResultDetailDTO loanEmployeeDataResult) {
        this.loanEmployeeDataResult = loanEmployeeDataResult;
    }
}
