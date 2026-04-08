package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanFeedbackFleetDetailDTO;
import com.phincon.talents.app.dto.loan.LoanFeedbackFleetListDTO;
import com.phincon.talents.app.model.loan.LoFeedbackFleet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoFeedbackFleetRepository extends CrudRepository<LoFeedbackFleet,String>, PagingAndSortingRepository<LoFeedbackFleet,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanFeedbackFleetListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.syncElementName" +
                ",u.valueSource" +
                ",u.isActive" +
                ",u.startDate" +
                ",u.endDate" +
                ",llc.name" +
                ",u.sequenceNo" +
            ") " +
            "FROM " +
                "LoFeedbackFleet u " +
                "JOIN LoLoanCategory llc ON u.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND u.isActive = true " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
            //check overlap date
                "AND (current_date BETWEEN cast(u.startDate as date) AND cast(u.endDate as date)  ) "
    )
    public List<LoanFeedbackFleetListDTO> findByLoanCategoryId(
            @Param("loanCategoryId") String loanCategoryId
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanFeedbackFleetListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.syncElementName" +
                ",u.valueSource" +
                ",u.isActive" +
                ",u.startDate" +
                ",u.endDate" +
                ",llc.name" +
                ",u.sequenceNo" +
            ") " +
            "FROM " +
                "LoFeedbackFleet u " +
                "JOIN LoLoanCategory llc ON u.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND u.isActive = true " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
                //check overlap date
                "AND (current_date BETWEEN cast(u.startDate as date) AND cast(u.endDate as date)  ) " +
//                "AND u.valueSource NOT IN ('" + LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT + "','" + LoFeedbackFleet.VALUE_SOURCE_MONTHLY_INSTALLMENT_MOP + "') " +
            "ORDER BY u.sequenceNo ASC "
    )
    public List<LoanFeedbackFleetListDTO> findByLoanCategoryIdExceptMonthlyInstallment(
            @Param("loanCategoryId") String loanCategoryId
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanFeedbackFleetListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.syncElementName" +
                ",u.valueSource" +
                ",u.isActive" +
                ",u.startDate" +
                ",u.endDate" +
                ",llc.name" +
                ",u.sequenceNo" +
            ") " +
            "FROM " +
                "LoFeedbackFleet u " +
                "JOIN LoLoanCategory llc ON u.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
            "ORDER BY u.createdDate asc "
    )
    public Page<LoanFeedbackFleetListDTO> listData(
            @Param("loanCategoryId") String loanCategoryId
            ,@Param("name") String name
            ,@Param("startDate") LocalDate startDate
            ,@Param("endDate") LocalDate endDate
            ,Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanFeedbackFleetDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.syncElementName" +
                ",u.valueSource" +
                ",u.isActive" +
                ",u.startDate" +
                ",u.endDate" +
                ",llc.name" +
                ",u.sequenceNo" +
            ") " +
            "FROM " +
                "LoFeedbackFleet u " +
                "JOIN LoLoanCategory llc ON u.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanFeedbackFleetDetailDTO> getData(@Param("id") String id);

    @Query("select " +
                "u " +
            "FROM " +
                "LoFeedbackFleet u " +
            "WHERE 1=1 " +
                "AND (:excludeId is null or u.id != :excludeId) " +
                "AND u.isActive = true " +
                "AND (CAST(:syncElementName as string) is null or u.syncElementName = cast(:syncElementName as string) ) " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) "
    )
    public List<LoFeedbackFleet> getDataBySyncElementName(
            @Param("syncElementName") String syncElementName,
            @Param("loanCategoryId") String loanCategoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeId") String excludeId
    );

    @Query("select " +
            "u " +
            "FROM " +
            "LoFeedbackFleet u " +
            "WHERE 1=1 " +
            "AND u.isActive = true " +
            "AND (:excludeId is null or u.id != :excludeId) " +
            "AND (u.valueSource IN :valueSourceName ) " +
            "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
            //check overlap date
            "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
            "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) "
    )
    public List<LoFeedbackFleet> getDataByValueSourceList(
            @Param("valueSourceName") List<String> valueSourceName,
            @Param("loanCategoryId") String loanCategoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeId") String excludeId
    );

    @Query("select " +
            "u " +
            "FROM " +
            "LoFeedbackFleet u " +
            "WHERE 1=1 " +
            "AND u.isActive = true " +
            "AND (:excludeId is null or u.id != :excludeId) " +
            "AND u.valueSource is not null " +
            "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
            //check overlap date
            "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
            "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) "
    )
    public List<LoFeedbackFleet> getDataByValueSource(
            @Param("loanCategoryId") String loanCategoryId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeId") String excludeId
    );

}
