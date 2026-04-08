package com.phincon.talents.app.dto.loan;

public class PARatingDTO {
    private String id;
    private String name;
    private String description;
    private String ratingModelId;
    private Integer sequenceNo;
    private Double rangeFrom;
    private Double rangeTo;
    private Double value;

    public PARatingDTO() {
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

    public String getRatingModelId() {
        return ratingModelId;
    }

    public void setRatingModelId(String ratingModelId) {
        this.ratingModelId = ratingModelId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Double getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Double rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Double getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Double rangeTo) {
        this.rangeTo = rangeTo;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
