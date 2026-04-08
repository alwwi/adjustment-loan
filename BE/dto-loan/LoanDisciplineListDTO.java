package com.phincon.talents.app.dto.loan;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.phincon.talents.app.utils.LocalDateTimeToMillisSerializer;

import java.time.LocalDateTime;

public class LoanDisciplineListDTO {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeToMillisSerializer.class)
    private LocalDateTime endDate;

    private String disciplineId;
    private String loanTypeId;

    private String disciplineName;

    public LoanDisciplineListDTO(String id, LocalDateTime startDate, LocalDateTime endDate, String disciplineId, String loanTypeId, String disciplineName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.disciplineId = disciplineId;
        this.loanTypeId = loanTypeId;
        this.disciplineName = disciplineName;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
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
