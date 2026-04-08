package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanTransferEvidencePostDTO {
    private  String id;
    private String requestNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transferDate;
    private String remark;
    private String attachment;

    @Override
    public String toString() {
        return "LoanTransferEvidencePostDTO{" +
                "id='" + id + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", transferDate=" + transferDate +
                ", remark='" + remark + '\'' +
                ", attachment='" + attachment + '\'' +
                '}';
    }

    public LoanTransferEvidencePostDTO(String id, String requestNo, LocalDateTime transferDate, String remark, String attachment) {
        this.id = id;
        this.requestNo = requestNo;
        this.transferDate = transferDate;
        this.remark = remark;
        this.attachment = attachment;
    }

    public LoanTransferEvidencePostDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
