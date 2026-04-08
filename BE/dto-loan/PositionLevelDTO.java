package com.phincon.talents.app.dto.loan;

import jakarta.persistence.Column;

public class PositionLevelDTO {
    private String id;
    private String name;

    private Integer sequence;

    private String description;

    private String manpowerLevel;

    public PositionLevelDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManpowerLevel() {
        return manpowerLevel;
    }

    public void setManpowerLevel(String manpowerLevel) {
        this.manpowerLevel = manpowerLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
