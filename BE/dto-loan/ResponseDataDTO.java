package com.phincon.talents.app.dto.loan;

import java.util.List;

public class ResponseDataDTO<T> {
    private Long totalRecord;
    private Long totalShowRecord;
    private List<T> data;

    @Override
    public String toString() {
        return "ResponseDataDTO{" +
                "totalRecord=" + totalRecord +
                ", totalShowRecord=" + totalShowRecord +
                ", data=" + data +
                '}';
    }

    public Long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Long getTotalShowRecord() {
        return totalShowRecord;
    }

    public void setTotalShowRecord(Long totalShowRecord) {
        this.totalShowRecord = totalShowRecord;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
