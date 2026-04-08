package com.phincon.external.app.model.loan;

import com.phincon.external.app.model.general.AbstractEntityUUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_loan_category")
public class LoLoanCategory extends AbstractEntityUUID {

    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer occurenceLimit;

    private Boolean isSimulation;

    public Boolean getSimulation() {
        return isSimulation;
    }

    public void setSimulation(Boolean simulation) {
        isSimulation = simulation;
    }

    public Integer getOccurenceLimit() {
        return occurenceLimit;
    }

    public void setOccurenceLimit(Integer occurenceLimit) {
        this.occurenceLimit = occurenceLimit;
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
