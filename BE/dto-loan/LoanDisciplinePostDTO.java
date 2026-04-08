package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class LoanDisciplinePostDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private String disciplineId;
    private String loanTypeId;

    @Override
    public String toString() {
        return "LoanDisciplinePostDTO{" +
                "id='" + id + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", disciplineId=" + disciplineId +
                ", loanTypeId='" + loanTypeId + '\'' +
                '}';
    }

    public LoanDisciplinePostDTO() {
    }

    public LoanDisciplinePostDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String disciplineId, String loanTypeId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.disciplineId = disciplineId;
        this.loanTypeId = loanTypeId;
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

    public String getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId;
    }

    public String getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(String loanTypeId) {
        this.loanTypeId = loanTypeId;
    }
}
