//package com.phincon.talents.app.dto.loan;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import java.time.LocalDateTime;
//
//public class LoanDsrComponentDetailDTO {
//    private Long id;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime startDate;
//
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime endDate;
//
//    private Long loanTypeId;
//    private String elementName;
//
//    public LoanDsrComponentDetailDTO(Long id, LocalDateTime startDate, LocalDateTime endDate, Long loanTypeId, String elementName) {
//        this.id = id;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.loanTypeId = loanTypeId;
//        this.elementName = elementName;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public LocalDateTime getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDateTime startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDateTime getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDateTime endDate) {
//        this.endDate = endDate;
//    }
//
//    public Long getLoanTypeId() {
//        return loanTypeId;
//    }
//
//    public void setLoanTypeId(Long loanTypeId) {
//        this.loanTypeId = loanTypeId;
//    }
//
//    public String getElementName() {
//        return elementName;
//    }
//
//    public void setElementName(String elementName) {
//        this.elementName = elementName;
//    }
//}
