package com.phincon.talents.app.model.loan;

import com.phincon.talents.app.model.AbstractEntityUUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "lo_rating_model_matrix")
public class LoRatingModelMatrix extends AbstractEntityUUID {
    public static final String RESULT_BATAS_ATAS = "Batas Atas";
    public static final  String RESULT_BATAS_BAWAH = "Batas Bawah";

    private String loanRatingModelId;

    @Column(name = "pa_rating_id_1")
    private String paRatingId1;

    @Column(name = "pa_rating_id_2")
    private String paRatingId2;
    private String result;

    public String getLoanRatingModelId() {
        return loanRatingModelId;
    }

    public void setLoanRatingModelId(String loanRatingModelId) {
        this.loanRatingModelId = loanRatingModelId;
    }

    public String getPaRatingId1() {
        return paRatingId1;
    }

    public void setPaRatingId1(String paRatingId1) {
        this.paRatingId1 = paRatingId1;
    }

    public String getPaRatingId2() {
        return paRatingId2;
    }

    public void setPaRatingId2(String paRatingId2) {
        this.paRatingId2 = paRatingId2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
