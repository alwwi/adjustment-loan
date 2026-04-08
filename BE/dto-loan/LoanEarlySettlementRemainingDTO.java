package com.phincon.talents.app.dto.loan;

public class LoanEarlySettlementRemainingDTO {

    private String requestNo;
    private Double remaining;

    public LoanEarlySettlementRemainingDTO(String requestNo, Double remaining) {
        this.requestNo = requestNo;
        this.remaining = remaining;
    }

    public LoanEarlySettlementRemainingDTO() {
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
}
