package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;


@Entity
@Table(name = "lo_transfer_evidence")
public class LoLoanTransferEvidence  extends AbstractEntityUUID {

    private String requestNo;
    private LocalDateTime transferDate;
    private String remark;
    private String attachment;

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
