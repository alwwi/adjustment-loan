package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_discipline")
public class LoDiscipline extends AbstractEntityUUID {

    private String disciplineId;
    private String loanTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

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
}
