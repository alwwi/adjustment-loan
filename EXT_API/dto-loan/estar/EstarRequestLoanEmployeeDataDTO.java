package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarRequestLoanEmployeeDataDTO {

    @JsonProperty("NIK_Employee")
    private String nikEmployee;

    @JsonProperty("RequestNo")
    private String requestNo;

    @Override
    public String toString() {
        return "EstarRequestLoanEmployeeDataDTO{" +
                "nikEmployee='" + nikEmployee + '\'' +
                ", requestNo='" + requestNo + '\'' +
                '}';
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public EstarRequestLoanEmployeeDataDTO(String nikEmployee) {
        this.nikEmployee = nikEmployee;
    }

    public EstarRequestLoanEmployeeDataDTO() {
    }

    public String getNikEmployee() {
        return nikEmployee;
    }

    public void setNikEmployee(String nikEmployee) {
        this.nikEmployee = nikEmployee;
    }
}
