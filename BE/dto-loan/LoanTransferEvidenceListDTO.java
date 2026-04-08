package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanTransferEvidenceListDTO {

    private String id;
    private String requestNo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime transferDate;

    private String remark;
    private String attachment;

    public LoanTransferEvidenceListDTO(String id, String requestNo, LocalDateTime transferDate, String remark, String attachment, String serverNamePath, String secret) {
        this.id = id;
        this.requestNo = requestNo;
        this.transferDate = transferDate;
        this.remark = remark;
        this.attachment = serverNamePath + attachment  + "&token=" + JwtWrapperService.createPathJwt(attachment,secret);
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
