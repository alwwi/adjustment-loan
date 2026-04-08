package com.phincon.talents.app.dto.loan;

public class LoanHistoryDetailApproverDTO {

    private String requestNo;

    private String approverEmployeeId;
    private String approverEmployeeName;

    private String approverAction;

    private Integer approverLevel;

    public LoanHistoryDetailApproverDTO(String requestNo, String approverEmployeeId, String approverEmployeeName, String approverAction, Integer approverLevel) {
        this.requestNo = requestNo;
        this.approverEmployeeId = approverEmployeeId;
        this.approverEmployeeName = approverEmployeeName;
        this.approverAction = approverAction;
        this.approverLevel = approverLevel;
    }

    public Integer getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(Integer approverLevel) {
        this.approverLevel = approverLevel;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getApproverEmployeeId() {
        return approverEmployeeId;
    }

    public void setApproverEmployeeId(String approverEmployeeId) {
        this.approverEmployeeId = approverEmployeeId;
    }

    public String getApproverEmployeeName() {
        return approverEmployeeName;
    }

    public void setApproverEmployeeName(String approverEmployeeName) {
        this.approverEmployeeName = approverEmployeeName;
    }

    public String getApproverAction() {
        return approverAction;
    }

    public void setApproverAction(String approverAction) {
        this.approverAction = approverAction;
    }
}
