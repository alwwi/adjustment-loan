package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanTypeDetailDTO;
import com.phincon.talents.app.dto.loan.LoanTypeListDTO;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.model.loan.LoLoanType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface LoLoanTypeRepository extends CrudRepository<LoLoanType,String>, PagingAndSortingRepository<LoLoanType,String> {

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanTypeListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.dsrPercentage" +
                ",u.minTenor" +
                ",u.maxTenor" +
                ",u.interestRate" +
                ",u.isEarlySettlement" +
                ",u.isDownPayment" +
            ") " +
            "FROM " +
                "LoLoanType u " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:categoryId as string) is null or u.loanCategoryId = (:categoryId) ) "
    )
    public Page<LoanTypeListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("categoryId") String categoryId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanTypeDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.loanCategoryId" +
                ",u.dsrPercentage" +
                ",u.minTenor" +
                ",u.maxTenor" +
                ",u.interestRate" +
                ",u.isEarlySettlement" +
                ",u.isDownPayment" +
            ") " +
            "FROM " +
                "LoLoanType u " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = (:id) ) "
    )
    public Optional<LoanTypeDetailDTO> getData(@Param("id") String id);

}
