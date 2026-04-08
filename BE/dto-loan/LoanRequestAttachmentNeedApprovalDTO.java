package com.phincon.talents.app.dto.loan;

import java.time.LocalDateTime;

public class LoanRequestAttachmentNeedApprovalDTO {
    private String id;
    private String loanAttachmentId;
    private String loanAttachmentName;
    private String loanAttachmentDesc;
    private String path;

    private LocalDateTime loanAttachmentStartDate;
    private LocalDateTime loanAttachmentEndDate;

    public LoanRequestAttachmentNeedApprovalDTO() {
    }

    public LoanRequestAttachmentNeedApprovalDTO(String id, String loanAttachmentId, String loanAttachmentName, String loanAttachmentDesc, String path, LocalDateTime loanAttachmentStartDate, LocalDateTime loanAttachmentEndDate) {
        this.id = id;
        this.loanAttachmentId = loanAttachmentId;
        this.loanAttachmentName = loanAttachmentName;
        this.loanAttachmentDesc = loanAttachmentDesc;
        this.path = path;
        this.loanAttachmentStartDate = loanAttachmentStartDate;
        this.loanAttachmentEndDate = loanAttachmentEndDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanAttachmentId() {
        return loanAttachmentId;
    }

    public void setLoanAttachmentId(String loanAttachmentId) {
        this.loanAttachmentId = loanAttachmentId;
    }

    public String getLoanAttachmentName() {
        return loanAttachmentName;
    }

    public void setLoanAttachmentName(String loanAttachmentName) {
        this.loanAttachmentName = loanAttachmentName;
    }

    public String getLoanAttachmentDesc() {
        return loanAttachmentDesc;
    }

    public void setLoanAttachmentDesc(String loanAttachmentDesc) {
        this.loanAttachmentDesc = loanAttachmentDesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
