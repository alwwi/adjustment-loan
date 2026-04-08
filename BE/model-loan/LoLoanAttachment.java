package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntity;
import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_loan_attachment")
public class LoLoanAttachment extends AbstractEntityUUID {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_old")
//    private Long idOld;

    private String name;
    private String description;
    private String loanTypeId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean mandatory;

//    public Long getIdOld() {
//        return idOld;
//    }
//
//    public void setIdOld(Long idOld) {
//        this.idOld = idOld;
//    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
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
