//package com.phincon.talents.app.repository;
//
//import com.phincon.talents.app.dto.loan.LoanRequestAttachmentNeedApprovalDTO;
//import com.phincon.talents.app.dto.loan.LoanRequestNeedApprovalDTO;
//import com.phincon.talents.app.model.loan.LoLoanRequestAttachment;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
//public interface LoLoanRequestAttachmentRepository  extends PagingAndSortingRepository<LoLoanRequestAttachment,Long> {
//    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestAttachmentNeedApprovalDTO(" +
//                "u.id" +
//                ",u.loanAttachmentId" +
//                ",loanAttachment.name " +
//                ",loanAttachment.description " +
//                ",u.path" +
//                ",loanAttachment.startDate " +
//                ",loanAttachment.endDate " +
//            ") from " +
//                "LoLoanRequestAttachment u " +
//                "LEFT JOIN LoLoanAttachment loanAttachment ON u.loanAttachmentId = loanAttachment.id " +
//            "where 1=1 " +
//                "AND (CAST(:loanRequestId as string) is null or u.loanRequestId = cast(:loanRequestId as long) ) "
//    )
//    List<LoanRequestAttachmentNeedApprovalDTO> getListByRequestId(@Param("loanRequestId") Long loanRequestId);
//
//}
