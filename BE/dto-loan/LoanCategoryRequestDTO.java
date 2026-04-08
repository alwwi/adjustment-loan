package com.phincon.talents.app.dto.loan;

public class LoanCategoryRequestDTO {

    private String id;
    private String name;
    private long countRequest;

    public LoanCategoryRequestDTO(String id, String name, long countRequest) {
        this.id = id;
        this.name = name;
        this.countRequest = countRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCountRequest() {
        return countRequest;
    }

    public void setCountRequest(long countRequest) {
        this.countRequest = countRequest;
    }
}
