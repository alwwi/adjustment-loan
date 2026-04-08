package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import com.phincon.talents.app.model.loan.LoLoanRequest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoanEarlySettlementRequestNeedApprovalDTO {
    //requestCategory
    private String categoryName;
    private String loanRequestId;

    private List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestDate;
    private String requestNo;

    private String requestId;

    //dataApproval
    private Integer currentApprovalLevel;
    private Integer approvalLevel;
    private String dataApprovalId;
    private String status;
    private String progressOverall;
    private String approverName;
    private String remark;
    private String claimFor;
    private String attachments;
    private VwEmpAssignment vwEmpAssignment;
    private String employeeProfile;
    private String reasonReject;
    private Date expiredDateDetail;
    private VwEmpAssignment requestorAsgn;

    public LoanEarlySettlementRequestNeedApprovalDTO()
    {
        super();
    }

    public LoanEarlySettlementRequestNeedApprovalDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath ) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath );
    }

    public LoanEarlySettlementRequestNeedApprovalDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath ) {
        super();

        //Early Settlement
//        this.loanRequestId = requestData.getLoanRequestId();

        this.requestDate = requestData.getRequestDate();
        this.requestNo = requestData.getRequestNo();

        this.remark = requestData.getRemark();
        this.categoryName = requestCategoryType.getName();
        this.requestNo = requestData.getRequestNo();
        this.requestDate = requestData.getRequestDate();
        this.requestId = requestData.getId();

        //data approval
        this.status = dataApproval.getStatus();
        this.progressOverall = dataApproval.getCurrentApprovalLevel()+" of "+dataApproval.getApprovalLevel();
        this.approverName = approverName;
        this.currentApprovalLevel = dataApproval.getCurrentApprovalLevel();
        this.approvalLevel = dataApproval.getApprovalLevel();
        this.reasonReject = dataApproval.getReasonReject();
        this.expiredDateDetail = dataApproval.getExpiredDateDetail();
        if(attachmentDataApproval!= null) {
            this.attachments = serverNamePath+attachmentDataApproval.getPath();
        }
        this.vwEmpAssignment = vwEmpAssignment;
        this.dataApprovalId=dataApproval.getId();
        this.employeeProfile = vwEmpAssignment.getPhotoProfile();

        if(this.vwEmpAssignment!= null)
        {
            this.employeeProfile = serverNamePath+vwEmpAssignment.getPhotoProfile();
        }

    }

    public LoanEarlySettlementRequestNeedApprovalDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath, VwEmpAssignment empRqst ) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, attachmentDataApproval, approverName, serverNamePath);
        this.requestorAsgn = empRqst;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLoanRequestId() {
        return loanRequestId;
    }

    public void setLoanRequestId(String loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public List<LoanRequestAttachmentNeedApprovalDTO> getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setAttachmentDetails(List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public Integer getCurrentApprovalLevel() {
        return currentApprovalLevel;
    }

    public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
        this.currentApprovalLevel = currentApprovalLevel;
    }

    public Integer getApprovalLevel() {
        return approvalLevel;
    }

    public void setApprovalLevel(Integer approvalLevel) {
        this.approvalLevel = approvalLevel;
    }

    public String getDataApprovalId() {
        return dataApprovalId;
    }

    public void setDataApprovalId(String dataApprovalId) {
        this.dataApprovalId = dataApprovalId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgressOverall() {
        return progressOverall;
    }

    public void setProgressOverall(String progressOverall) {
        this.progressOverall = progressOverall;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClaimFor() {
        return claimFor;
    }

    public void setClaimFor(String claimFor) {
        this.claimFor = claimFor;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public VwEmpAssignment getVwEmpAssignment() {
        return vwEmpAssignment;
    }

    public void setVwEmpAssignment(VwEmpAssignment vwEmpAssignment) {
        this.vwEmpAssignment = vwEmpAssignment;
    }

    public String getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(String employeeProfile) {
        this.employeeProfile = employeeProfile;
    }

    public String getReasonReject() {
        return reasonReject;
    }

    public void setReasonReject(String reasonReject) {
        this.reasonReject = reasonReject;
    }

    public Date getExpiredDateDetail() {
        return expiredDateDetail;
    }

    public void setExpiredDateDetail(Date expiredDateDetail) {
        this.expiredDateDetail = expiredDateDetail;
    }

    public VwEmpAssignment getRequestorAsgn() {
        return requestorAsgn;
    }

    public void setRequestorAsgn(VwEmpAssignment requestorAsgn) {
        this.requestorAsgn = requestorAsgn;
    }
}
