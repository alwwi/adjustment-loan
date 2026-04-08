package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phincon.talents.app.dto.personal.RequestParentDTO;
import com.phincon.talents.app.model.AttachmentDataApproval;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.hr.RequestCategoryType;
import com.phincon.talents.app.model.hr.VwEmpAssignment;
import com.phincon.talents.app.model.hr.WorkExperienceRequest;
import com.phincon.talents.app.model.loan.LoLoanRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class LoanRequestDTO  extends RequestParentDTO {

    private String actionRequest;
    private String employmentId;
    private String loanCategoryId;
    private String loanTypeId;
    private String brandId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loanDate;
    private Double amount;
    private Double amountOtr;
    private Integer tenor;

    private Double downPayment;
    private Double monthlyInstallment;

    private Boolean isBypassApproval;

    private List<LoanRequestAttachmentDTO> attachmentDetails;

    @Override
    public String toString() {
        return "LoanRequestDTO{" +
                "actionRequest='" + actionRequest + '\'' +
                ", employmentId=" + employmentId +
                ", loanCategoryId='" + loanCategoryId + '\'' +
                ", loanTypeId='" + loanTypeId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", loanDate=" + loanDate +
                ", amount=" + amount +
                ", amountOtr=" + amountOtr +
                ", tenor=" + tenor +
                ", downPayment=" + downPayment +
                ", monthlyInstallment=" + monthlyInstallment +
                ", isBypassApproval=" + isBypassApproval +
                ", attachmentDetails=" + attachmentDetails +
                ", requestId=" + requestId +
                ", remark='" + remark + '\'' +
                ", requestNo='" + requestNo + '\'' +
                ", requestDate=" + requestDate +
                ", categoryName='" + categoryName + '\'' +
                ", dataApprovalId=" + dataApprovalId +
                ", status='" + status + '\'' +
                ", progressOverall='" + progressOverall + '\'' +
                ", approverName='" + approverName + '\'' +
                ", attachments='" + attachments + '\'' +
                ", vwEmpAssignment=" + vwEmpAssignment +
                ", employeeProfile='" + employeeProfile + '\'' +
                ", reasonReject='" + reasonReject + '\'' +
                ", expiredDateDetail=" + expiredDateDetail +
                '}';
    }

    public LoanRequestDTO() {
        super();
    }

    public LoanRequestDTO(LoLoanRequest request, RequestCategoryType requestCategoryType,
                          DataApproval dataApproval,
                          VwEmpAssignment vwEmpAssignment, AttachmentDataApproval attachmentDataApproval,
                          String approverName, String serverNamePath, String secret) {

        super(request.getId(), request.getRemark(), request.getRequestNo(), Date.from(request.getRequestDate().atZone( ZoneId.systemDefault()).toInstant()),
                requestCategoryType,
                dataApproval, vwEmpAssignment, attachmentDataApproval, approverName, serverNamePath, secret);
        this.remark = request.getRemark();
        this.employmentId = request.getEmploymentId();
        this.loanCategoryId = request.getLoanCategoryId();
        this.loanTypeId = request.getLoanTypeId();
        this.brandId = request.getBrandId();
        this.loanDate = request.getLoanDate();
        this.amount = request.getAmount();
        this.amountOtr = request.getAmountOtr();
        this.tenor = request.getTenor();
        this.downPayment = request.getDownPayment();
        this.monthlyInstallment = request.getMonthlyInstallment();


    }

    public String getActionRequest() {
        return actionRequest;
    }

    public void setActionRequest(String actionRequest) {
        this.actionRequest = actionRequest;
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

    public List<LoanRequestAttachmentDTO> getAttachmentDetails() {
        return attachmentDetails;
    }

    public void setAttachmentDetails(List<LoanRequestAttachmentDTO> attachmentDetails) {
        this.attachmentDetails = attachmentDetails;
    }

    public Boolean getBypassApproval() {
        return isBypassApproval;
    }

    public void setBypassApproval(Boolean bypassApproval) {
        isBypassApproval = bypassApproval;
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

    public Double getAmountOtr() {
        return this.amountOtr;
    }

    public void setAmountOtr(Double amountOtr) {
        this.amountOtr = amountOtr;
    }

}
