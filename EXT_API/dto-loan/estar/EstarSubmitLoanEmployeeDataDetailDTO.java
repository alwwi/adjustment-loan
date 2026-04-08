package com.phincon.external.app.dto.loan.estar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EstarSubmitLoanEmployeeDataDetailDTO {

    @JsonProperty("NIP")
    private String nip;

    @JsonProperty("NIK")
    private String nik;

    @JsonProperty("NoRequest")
    private String noRequest;

    public EstarSubmitLoanEmployeeDataDetailDTO(String nip, String nik, String noRequest) {
        this.nip = nip;
        this.nik = nik;
        this.noRequest = noRequest;
    }

    public EstarSubmitLoanEmployeeDataDetailDTO() {
    }

    @Override
    public String toString() {
        return "EstarSubmitLoanEmployeeDataDetailDTO{" +
                "nip='" + nip + '\'' +
                ", nik='" + nik + '\'' +
                ", noRequest='" + noRequest + '\'' +
                '}';
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNoRequest() {
        return noRequest;
    }

    public void setNoRequest(String noRequest) {
        this.noRequest = noRequest;
    }
}
