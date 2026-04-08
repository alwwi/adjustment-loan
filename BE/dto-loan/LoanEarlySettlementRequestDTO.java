package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phincon.talents.app.dto.personal.RequestParentDTO;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import com.phincon.talents.app.model.loan.LoLoanRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LoanEarlySettlementRequestDTO extends RequestParentDTO {
    private String actionRequest;

    private String loanRequestNo;
    private Boolean isBypassApproval;

    private Double remainingPayment;


    public LoanEarlySettlementRequestDTO() {
        super();
    }

    public LoanEarlySettlementRequestDTO(LoEarlySettlementRequest request, RequestCategoryType requestCategoryType,
                                         DataApproval dataApproval,
                                         VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval,
                                         String approverName, String serverNamePath, String secret) {

        super(request.getId(), request.getRemark(), request.getRequestNo(), Date.from(request.getRequestDate().atZone( ZoneId.systemDefault()).toInstant()),
                requestCategoryType,
                dataApproval, vwEmpAssignment, attachmentDataApproval, approverName, serverNamePath,secret);
        this.remark = request.getRemark();


    }

    public String getActionRequest() {
        return actionRequest;
    }

    public void setActionRequest(String actionRequest) {
        this.actionRequest = actionRequest;
    }

    public Double getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(Double remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public String getLoanRequestNo() {
        return loanRequestNo;
    }

    public void setLoanRequestNo(String loanRequestNo) {
        this.loanRequestNo = loanRequestNo;
    }

    public Boolean getBypassApproval() {
        return isBypassApproval;
    }

    public void setBypassApproval(Boolean bypassApproval) {
        isBypassApproval = bypassApproval;
    }

}
