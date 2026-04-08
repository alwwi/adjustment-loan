package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.*;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoanEarlySettlementMyRequestDTO {
    //requestCategory
    private String categoryName;

    private String requestId;

    //Loan Early Settlement
    private String loanRequestId;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime requestDate;
    private String requestNo;

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

    private String loanCategoryName;

    private String loanTypeName;

    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDate;

    private Double loanAmount;

    private Integer loanTenor;

    private Double downPayment;

    private Double remainingPayment;

    public LoanEarlySettlementMyRequestDTO()
    {
        super();
    }

    public LoanEarlySettlementMyRequestDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath,LoLoanCategory llc, LoLoanType llt, LoEmployeeLoan lel , String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath,llc,llt,lel,secret);
    }

    public LoanEarlySettlementMyRequestDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath,
                                           LoLoanCategory llc, LoLoanType llt, LoEmployeeLoan lel, String secret) {
        super();

        //Early Settlement
//        this.loanRequestId = requestData.getLoanRequestId();

        this.remark = requestData.getRemark();
        this.categoryName = requestCategoryType.getName();
        this.requestNo = requestData.getRequestNo();
        this.requestDate = requestData.getRequestDate();
//        this.requestId = requestData.getId();

        this.loanCategoryName = llc.getName();
        this.loanTypeName = llt.getName();
        this.loanDate = lel.getLoanDate();
        this.loanAmount = lel.getAmount();
        this.loanTenor = lel.getTenor();
        this.downPayment = lel.getDownPayment();
        this.remainingPayment = lel.getRemaining();


        //data approval
        this.status = dataApproval.getStatus();
        this.progressOverall = dataApproval.getCurrentApprovalLevel()+" of "+dataApproval.getApprovalLevel();
        this.approverName = approverName;
        this.currentApprovalLevel = dataApproval.getCurrentApprovalLevel();
        this.approvalLevel = dataApproval.getApprovalLevel();
        this.reasonReject = dataApproval.getReasonReject();
        this.expiredDateDetail = dataApproval.getExpiredDateDetail();
        if(attachmentDataApproval!= null) {
            this.attachments = serverNamePath+attachmentDataApproval.getPath()+"&token=" + JwtWrapperService.createPathJwt(attachmentDataApproval.getPath(),secret);
        }
        this.vwEmpAssignment = vwEmpAssignment;
        this.dataApprovalId=dataApproval.getId();
        this.employeeProfile = vwEmpAssignment.getPhotoProfile();

        if(this.vwEmpAssignment!= null)
        {
            this.employeeProfile = serverNamePath+vwEmpAssignment.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(vwEmpAssignment.getPhotoProfile(),secret);
        }

    }

    public LoanEarlySettlementMyRequestDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath, VwEmpAssignment empRqst,LoLoanCategory llc, LoLoanType llt, LoEmployeeLoan lel , String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, attachmentDataApproval, approverName, serverNamePath,llc,llt,lel,secret);
        this.requestorAsgn = empRqst;
    }

    public LoanEarlySettlementMyRequestDTO(LoEarlySettlementRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath, VwEmpAssignment empRqst,LoLoanCategory llc, LoLoanType llt, LoEmployeeLoan lel , String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath,llc,llt,lel,secret);
        this.requestorAsgn = empRqst;
    }

    public Double getRemainingPayment() {
        return remainingPayment;
    }

    public void setRemainingPayment(Double remainingPayment) {
        this.remainingPayment = remainingPayment;
    }

    public String getLoanCategoryName() {
        return loanCategoryName;
    }

    public void setLoanCategoryName(String loanCategoryName) {
        this.loanCategoryName = loanCategoryName;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTenor() {
        return loanTenor;
    }

    public void setLoanTenor(Integer loanTenor) {
        this.loanTenor = loanTenor;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
