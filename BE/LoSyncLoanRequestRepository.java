package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanSyncListDTO;
import com.phincon.talents.app.dto.loan.LoanTypeListDTO;
import com.phincon.talents.app.model.loan.LoPlafondBracket;
import com.phincon.talents.app.model.loan.LoSyncLoanRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface LoSyncLoanRequestRepository extends CrudRepository<LoSyncLoanRequest,String>, PagingAndSortingRepository<LoSyncLoanRequest,String> {

    @Query("select new com.phincon.talents.app.dto.loan.LoanSyncListDTO(" +
            "u.id, u.requestNo, u.requestDate, u.requestorEmploymentId, u.status, " +
            "u.startDate, u.endDate, u.paymentDate, u.remark, u.logFile, " +
            "concat('', :serverPath),:secret) " +
            "FROM LoSyncLoanRequest u " +
            "WHERE (:startDate IS NULL OR :endDate IS NULL OR " +
            "(coalesce(:startDate, :endDate) <= u.endDate AND " +
            "coalesce(:endDate, :startDate) >= u.startDate)) " +
            "AND (:status IS NULL OR u.status = :status) " +
            "ORDER BY u.createdDate DESC")
    public Page<LoanSyncListDTO> listData(
            @Param("status") String status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("serverPath") String serverPath,
            Pageable pageable,
            @Param("secret") String secret
    );


    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanSyncListDTO(" +
            "u.id" +
            ",u.requestNo" +
            ",u.requestDate" +
            ",u.requestorEmploymentId" +
            ",u.status" +
            ",u.startDate" +
            ",u.endDate" +
            ",u.paymentDate" +
            ",u.remark" +
            ",u.logFile" +
            ",concat('',:serverPath)" +
            ",:secret" +
            ") " +
            "FROM " +
            "LoSyncLoanRequest u " +
            "WHERE 1=1 " +
            "AND (cast(:id as string) is null or u.id = cast(:id as string)) "
    )
    public LoanSyncListDTO getDetail(
            @Param("id") String id
            , @Param("serverPath") String serverPath
            ,@Param("secret") String secret
    );

}
