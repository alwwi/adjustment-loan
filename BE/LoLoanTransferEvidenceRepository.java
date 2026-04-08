package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanTransferEvidenceListDTO;
import com.phincon.talents.app.model.loan.LoLoanTransferEvidence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoLoanTransferEvidenceRepository  extends CrudRepository<LoLoanTransferEvidence,String>, PagingAndSortingRepository<LoLoanTransferEvidence,String> {

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanTransferEvidenceListDTO(" +
                "u.id" +
                ",u.requestNo" +
                ",u.transferDate" +
                ",u.remark" +
                ",u.attachment" +
                ",:serverName" +
                ",:secret" +
            ") " +
            "FROM " +
                "LoLoanTransferEvidence u " +
            "WHERE 1=1 " +
                "AND (CAST(:requestNo as string) is null or (lower(u.requestNo) LIKE lower(:requestNo)) ) " +
                "AND (" +
                        "(cast(:startDate as string)    is     null and cast(:endDate as string) is not null) and cast(u.transferDate as date) <= cast(:endDate as date) " +
                        "or (cast(:startDate as string) is not null and cast(:endDate as string) is null) and cast(u.transferDate as date) >= cast(:startDate as date) " +
                        "or (cast(:startDate as string) is not null and cast(:endDate as string) is not null) and cast(u.transferDate as date) BETWEEN cast(:startDate as date) AND cast(:endDate as date) " +
                        "or (cast(:startDate as string) is null and cast(:endDate as string) is null) " +
                ")"
    )
    public Page<LoanTransferEvidenceListDTO> listData(
            @Param("requestNo") String requestNo
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , Pageable pageable,
            @Param("serverName") String serverName,
            @Param("secret") String secret
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanTransferEvidenceListDTO(" +
                "u.id" +
                ",u.requestNo" +
                ",u.transferDate" +
                ",u.remark" +
                ",u.attachment" +
                ",:serverName" +
                ",:secret" +
            ") " +
            "FROM " +
                "LoLoanTransferEvidence u " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = (:id) ) "
    )
    public Optional<LoanTransferEvidenceListDTO> getData(@Param("id") String id,
                                                         @Param("serverName") String serverName,
                                                         @Param("secret") String secret);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanTransferEvidenceListDTO(" +
            "u.id" +
            ",u.requestNo" +
            ",u.transferDate" +
            ",u.remark" +
            ",u.attachment" +
            ",:serverName" +
            ",:secret" +
            ") " +
            "FROM " +
            "LoLoanTransferEvidence u " +
            "WHERE 1=1 " +
            "AND (CAST(:requestNo as string) is null or u.requestNo = (:requestNo) ) "
    )
    public Optional<LoanTransferEvidenceListDTO> findByRequestNo(@Param("requestNo") String requestNo,
                                                                 @Param("serverName") String serverName,
                                                                 @Param("secret") String secret
                                                                 );

    @Query("select " +
            "true " +
            "FROM " +
            "LoLoanTransferEvidence u " +
            "WHERE 1=1 " +
            "AND (u.requestNo = (:requestNo) ) "
    )
    public Optional<List<Boolean>> isAnyTransferEvidence(@Param("requestNo") String requestNo, Pageable pageable);

}
