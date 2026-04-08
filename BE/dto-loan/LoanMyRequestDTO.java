package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.dto.recruitment.FKPDetailDTO;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.DataApprovalDetail;
import com.phincon.talents.app.model.hr.FKPRequest;
import com.phincon.talents.app.model.hr.MPP;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanCategory;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.utils.JwtWrapperService;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoanMyRequestDTO {
    //requestCategory
    private String categoryName;

    private String requestId;

    //Loan Request
    private String employmentId;
    private String loanCategoryId;
    private String loanTypeId;

    private String loanTypeName;
    private String brandId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime loanDate;
    private Double amount;
    private Double amountOtr;
    private Integer tenor;

    private Double downPayment;
    private Double monthlyInstallment;

    private List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails;

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

    private Boolean isSimulation;

    private List<LoanFeedbackFleetListDTO> loanFeedbackFleetListDTOS;
    private List<LoanRequestDetailListDTO> loanRequestDetail;

    private Integer approverLevel;

    private Boolean isNeedAdditionalInformation;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime goLiveDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime transferDate;

    private Date createdDate;

    private Boolean isAnyUpload;

    private Integer approvalLevelFleet;

    public LoanMyRequestDTO()
    {
        super();
    }

    public LoanMyRequestDTO(LoLoanRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, String approverName, String serverNamePath ,String loanTypeName, LoLoanCategory llc, LocalDateTime goLiveDate, LocalDateTime transferDate, String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath,loanTypeName,llc, goLiveDate, transferDate, secret);
    }

    public LoanMyRequestDTO(LoLoanRequest requestData, RequestCategoryType requestCategoryType, DataApproval dataApproval, VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval, String approverName, String serverNamePath
    , String loanTypeName, LoLoanCategory llc, LocalDateTime goLiveDate, LocalDateTime transferDate, String secret ) {
        super();

        //Laon Request
        this.employmentId = requestData.getEmploymentId();
        this.loanCategoryId = requestData.getLoanCategoryId();
        this.loanTypeId = requestData.getLoanTypeId();
        this.loanTypeName = loanTypeName;
        this.brandId = requestData.getBrandId();
        this.loanDate = requestData.getLoanDate();
        this.amount = requestData.getAmount();
        this.amountOtr = requestData.getAmountOtr();
        this.tenor = requestData.getTenor();
        this.requestNo = requestData.getRequestNo();
        this.downPayment = requestData.getDownPayment();
        this.monthlyInstallment = requestData.getMonthlyInstallment();
        this.goLiveDate = goLiveDate;
        this.transferDate = transferDate;
        this.createdDate = requestData.getCreatedDate();
        this.isAnyUpload = requestData.getEvidence();

        this.remark = requestData.getRemark();
        this.categoryName = requestCategoryType.getName();
        this.requestNo = requestData.getRequestNo();
        this.requestDate = requestData.getRequestDate();
        this.requestId = requestData.getId();

        this.isSimulation = llc.getSimulation();

        //data approval
        this.approvalLevel = dataApproval.getApprovalLevel();
        this.approvalLevelFleet = llc.getApprovalLevelFleet();

        if(llc.getApprovalLevelFleet() != null &&
            llc.getApprovalLevelFleet().equals(dataApproval.getCurrentApprovalLevel())
        ){
            this.isNeedAdditionalInformation = true;
        }else {
            this.isNeedAdditionalInformation = false;
        }
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

    public LoanMyRequestDTO(LoLoanRequest requestData,RequestCategoryType requestCategoryType,DataApproval dataApproval, VwEmpAssignment vwEmpAssignment,AttachmentDataApproval attachmentDataApproval,String approverName,String serverNamePath, VwEmpAssignment empRqst ,String loanTypeName, LoLoanCategory llc, LocalDateTime goLiveDate, LocalDateTime transferDate, String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, attachmentDataApproval, approverName, serverNamePath,loanTypeName,llc, goLiveDate, transferDate, secret);
        this.requestorAsgn = empRqst;
    }

    public LoanMyRequestDTO(LoLoanRequest requestData,RequestCategoryType requestCategoryType,DataApproval dataApproval, VwEmpAssignment vwEmpAssignment,String approverName,String serverNamePath, VwEmpAssignment empRqst ,String loanTypeName, LoLoanCategory llc, LocalDateTime goLiveDate, LocalDateTime transferDate, String secret) {
        this(requestData, requestCategoryType, dataApproval, vwEmpAssignment, null, approverName, serverNamePath,loanTypeName,llc,goLiveDate,transferDate,secret);
        this.requestorAsgn = empRqst;
    }

    public Integer getApprovalLevelFleet() {
        return approvalLevelFleet;
    }

    public void setApprovalLevelFleet(Integer approvalLevelFleet) {
        this.approvalLevelFleet = approvalLevelFleet;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getAnyUpload() {
        return isAnyUpload;
    }

    public void setAnyUpload(Boolean anyUpload) {
        isAnyUpload = anyUpload;
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

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    public LocalDateTime getGoLiveDate() {
        return goLiveDate;
    }

    public void setGoLiveDate(LocalDateTime goLiveDate) {
        this.goLiveDate = goLiveDate;
    }

    public Integer getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(Integer approverLevel) {
        this.approverLevel = approverLevel;
    }

    public Boolean getNeedAdditionalInformation() {
        return isNeedAdditionalInformation;
    }

    public void setNeedAdditionalInformation(Boolean needAdditionalInformation) {
        isNeedAdditionalInformation = needAdditionalInformation;
    }

    public Boolean getSimulation() {
        return isSimulation;
    }

    public void setSimulation(Boolean simulation) {
        isSimulation = simulation;
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

    public List<LoanRequestAttachmentNeedApprovalDTO> getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setAttachmentDetails(List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
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

    public LocalDateTime getRequestDate() {
        return requestDate;
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

    public Double getAmountOtr() {
        return this.amountOtr;
    }

    public void setAmountOtr(Double amountOtr) {
        this.amountOtr = amountOtr;
    }
}
