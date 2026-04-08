package com.phincon.talents.app.dto.loan;

public class LoanEarlySettlementPostDTO {

    private String employeeLoanId;

    @Override
    public String toString() {
        return "LoanEarlySettlementPostDTO{" +
                "employeeLoanId=" + employeeLoanId +
                '}';
    }

    public String getEmployeeLoanId() {
        return employeeLoanId;
    }

    public void setEmployeeLoanId(String employeeLoanId) {
        this.employeeLoanId = employeeLoanId;
    }
}
