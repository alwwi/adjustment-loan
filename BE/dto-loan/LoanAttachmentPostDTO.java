package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanAttachmentPostDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String name;
    private String description;
    private String loanTypeId;

    private Boolean mandatory;

    @Override
    public String toString() {
        return "LoanAttachmentPostDTO{" +
                "id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", loanTypeId='" + loanTypeId + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }

    public LoanAttachmentPostDTO() {
    }

    public LoanAttachmentPostDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String name, String description, String loanTypeId, Boolean mandatory) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
        this.loanTypeId = loanTypeId;
        this.mandatory = mandatory;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }
}
