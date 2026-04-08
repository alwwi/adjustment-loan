package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.DataApprovalDetail;
import com.phincon.talents.app.model.hr.FKPRequest;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.model.loan.LoLoanType;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoanRequestHistoryApprovalDTO {
    //request category type
    private String categoryName;

    //Loan Request
    private String employmentId;
    private String loanCategoryId;
    private String loanTypeId;
    private String brandId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDate;
    private Double amount;
    private Integer tenor;

    private Double downPayment;
    private Double monthlyInstallment;

    private List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails;

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

    private String remark;

    private String loanTypeName;

    private List<LoanFeedbackFleetListDTO> loanFeedbackFleetListDTOS;
    private List<LoanRequestDetailListDTO> loanRequestDetail;

    public LoanRequestHistoryApprovalDTO() {
        super();
    }

    public LoanRequestHistoryApprovalDTO(LoLoanRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath, DataApprovalDetail dad, VwEmpAssignment approverDetail, String loanTypeName , String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath, dad, approverDetail,loanTypeName,secret);
    }

    public LoanRequestHistoryApprovalDTO(LoLoanRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath, DataApprovalDetail dad, VwEmpAssignment approverDetail, String loanTypeName, String secret ) {
        //ini buat history approval,dimana statusnya adalah action di approvaldetail
        super();

        //Laon Request
        this.employmentId = requestData.getEmploymentId();
        this.loanCategoryId = requestData.getLoanCategoryId();
        this.loanTypeId = requestData.getLoanTypeId();
        this.brandId = requestData.getBrandId();
        this.loanDate = requestData.getLoanDate();
        this.amount = requestData.getAmount();
        this.tenor = requestData.getTenor();
        this.requestDate = requestData.getRequestDate();
        this.requestNo = requestData.getRequestNo();
        this.downPayment = requestData.getDownPayment();
        this.monthlyInstallment = requestData.getMonthlyInstallment();

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
        this.employeeProfile = vwEmpAssignment.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(vwEmpAssignment.getPhotoProfile(),secret);

        if(this.vwEmpAssignment!= null)
        {
            this.employeeProfile = serverNamePath+vwEmpAssignment.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(vwEmpAssignment.getPhotoProfile(),secret);
        }

        if(this.approverDetail!=null)
        {
            this.approverProfile = serverNamePath+approverDetail.getPhotoProfile()+"&token=" + JwtWrapperService.createPathJwt(approverDetail.getPhotoProfile(),secret);
        }
        this.actionNote =dad.getActionNote();
        this.remark = requestData.getRemark();

        this.loanTypeName = loanTypeName;
    }

    public List<LoanRequestDetailListDTO> getLoanRequestDetail() {
        return loanRequestDetail;
    }

    public void setLoanRequestDetail(List<LoanRequestDetailListDTO> loanRequestDetail) {
        this.loanRequestDetail = loanRequestDetail;
    }

    public List<LoanFeedbackFleetListDTO> getLoanFeedbackFleetListDTOS() {
        return loanFeedbackFleetListDTOS;
    }

    public void setLoanFeedbackFleetListDTOS(List<LoanFeedbackFleetListDTO> loanFeedbackFleetListDTOS) {
        this.loanFeedbackFleetListDTOS = loanFeedbackFleetListDTOS;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public Double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public void setMonthlyInstallment(Double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getEmploymentId() {
        return employmentId;
    }

    public void setEmploymentId(String employmentId) {
        this.employmentId = employmentId;
    }

    public String getLoanCategoryId() {
        return loanCategoryId;
    }

    public void setLoanCategoryId(String loanCategoryId) {
        this.loanCategoryId = loanCategoryId;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTenor() {
        return tenor;
    }

    public void setTenor(Integer tenor) {
        this.tenor = tenor;
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
