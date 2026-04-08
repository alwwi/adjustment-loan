package com.phincon.talents.app.dto.loan;

public class LoanEarlySettlementPostAttachmentDTO {
    private String earlySettlementId;
    private String attachment;

    private String remark;

    @Override
    public String toString() {
        return "LoanEarlySettlementPostAttachmentDTO{" +
                "earlySettlementId='" + earlySettlementId + '\'' +
                ", attachment='" + attachment + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEarlySettlementId() {
        return earlySettlementId;
    }

    public void setEarlySettlementId(String earlySettlementId) {
        this.earlySettlementId = earlySettlementId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
