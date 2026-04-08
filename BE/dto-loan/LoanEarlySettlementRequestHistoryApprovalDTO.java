package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.DataApprovalDetail;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoanEarlySettlementRequestHistoryApprovalDTO {
    //request category type
    private String categoryName;

    //Loan Early Settlement
    private String loanRequestId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime requestDate;
    private String requestNo;

    private String requestId;

    //data approval
    private Integer currentApprovalLevel;
    private Integer approvalLevel;
    private String dataApprovalDetailId;
    private String dataApprovalId;
    private String status;
    private String progressOverall;
    private String approverName;

    private String attachments;
    private VwEmpAssignment vwEmpAssignment;
    private VwEmpAssignment approverDetail;
    private String employeeProfile;
    private String approverProfile;
    private String reasonReject;
    private Date actionDate;
    private String actionNote;

    public LoanEarlySettlementRequestHistoryApprovalDTO() {
        super();
    }

    public LoanEarlySettlementRequestHistoryApprovalDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath, DataApprovalDetail dad, VwEmpAssignment approverDetail, String secret ) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath, dad, approverDetail,secret);
    }

    public LoanEarlySettlementRequestHistoryApprovalDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath, DataApprovalDetail dad, VwEmpAssignment approverDetail, String secret ) {
        //ini buat history approval,dimana statusnya adalah action di approvaldetail
        super();

        //Laon Early Settlement
//        this.loanRequestId = requestData.getLoanRequestId();
        this.requestDate = requestData.getRequestDate();
        this.requestNo = requestData.getRequestNo();

        this.categoryName = requestCategoryType.getName();
        this.requestNo = requestData.getRequestNo();
        this.requestDate = requestData.getRequestDate();
        this.requestId = requestData.getId();

        //category
        this.categoryName = requestCategoryType.getName();


        //dataapproval
        this.dataApprovalId = dataApproval.getId();
        this.status = dad.getAction();
        this.progressOverall = dataApproval.getCurrentApprovalLevel()+" of "+dataApproval.getApprovalLevel();
        this.approverName = approverName;
        this.reasonReject = dataApproval.getReasonReject();
        this.currentApprovalLevel = dataApproval.getCurrentApprovalLevel();
        this.approvalLevel = dataApproval.getApprovalLevel();
        this.actionDate=dad.getActionDate();

        if(attachmentDataApproval!= null) {
            this.attachments = serverNamePath+attachmentDataApproval.getPath()+"&token=" + JwtWrapperService.createPathJwt(attachmentDataApproval.getPath(),secret);
        }
        this.vwEmpAssignment = vwEmpAssignment;
        this.approverDetail = approverDetail;
        this.dataApprovalDetailId=dad.getId();
        this.employeeProfile = vwEmpAssignment.getPhotoProfile();

        if(this.vwEmpAssignment!= null)
        {
            this.employeeProfile = serverNamePath+vwEmpAssignment.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(vwEmpAssignment.getPhotoProfile(),secret);
        }

        if(this.approverDetail!=null)
        {
            this.approverProfile = serverNamePath+approverDetail.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(approverDetail.getPhotoProfile(),secret);
        }
        this.actionNote =dad.getActionNote();
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public String getDataApprovalDetailId() {
        return dataApprovalDetailId;
    }

    public void setDataApprovalDetailId(String dataApprovalDetailId) {
        this.dataApprovalDetailId = dataApprovalDetailId;
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

    public VwEmpAssignment getApproverDetail() {
        return approverDetail;
    }

    public void setApproverDetail(VwEmpAssignment approverDetail) {
        this.approverDetail = approverDetail;
    }

    public String getEmployeeProfile() {
        return employeeProfile;
    }

    public void setEmployeeProfile(String employeeProfile) {
        this.employeeProfile = employeeProfile;
    }

    public String getApproverProfile() {
        return approverProfile;
    }

    public void setApproverProfile(String approverProfile) {
        this.approverProfile = approverProfile;
    }

    public String getReasonReject() {
        return reasonReject;
    }

    public void setReasonReject(String reasonReject) {
        this.reasonReject = reasonReject;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionNote() {
        return actionNote;
    }

    public void setActionNote(String actionNote) {
        this.actionNote = actionNote;
    }
}
