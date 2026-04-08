package com.phincon.talents.app.dto.loan;

public class LoanRequestAttachmentDTO {

    private String id;
    private String loanAttachmentId;

    private String path;

    public LoanRequestAttachmentDTO() {
    }

    public LoanRequestAttachmentDTO(String id,  String loanAttachmentId, String path) {
        this.id = id;
        this.loanAttachmentId = loanAttachmentId;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
}
