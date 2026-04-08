package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanAttachmentDetailDTO;
import com.phincon.talents.app.dto.loan.LoanAttachmentListDTO;
import com.phincon.talents.app.dto.loan.LoanRequestAttachmentDTO;
import com.phincon.talents.app.model.loan.LoLoanAttachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoLoanAttachmentRepository  extends CrudRepository<LoLoanAttachment,String>, PagingAndSortingRepository<LoLoanAttachment,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanAttachmentListDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.name" +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.mandatory" +
            ") " +
            "FROM " +
              "LoLoanAttachment u " +
            "WHERE 1=1 " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) " +
            "ORDER BY u.createdDate"
    )
    public Page<LoanAttachmentListDTO> listData(
            @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanTypeId") String loanTypeId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanAttachmentDetailDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.name" +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.mandatory" +
            ") " +
            "FROM " +
                 "LoLoanAttachment u " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanAttachmentDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanAttachmentDetailDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.name" +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.mandatory" +
            ") " +
            "FROM " +
                "LoLoanAttachment u " +
            "WHERE 1=1 " +
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) "
    )
    public List<LoanAttachmentDetailDTO> getAllRequiredAttachmentByLoanType(@Param("loanTypeId") String loanTypeId);
}
