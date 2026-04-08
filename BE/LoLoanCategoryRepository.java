package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanCategoryDetailDTO;
import com.phincon.talents.app.dto.loan.LoanCategoryListDTO;
import com.phincon.talents.app.model.loan.LoLoanAttachment;
import com.phincon.talents.app.model.loan.LoLoanCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoLoanCategoryRepository   extends CrudRepository<LoLoanCategory,String>, PagingAndSortingRepository<LoLoanCategory,String> {

    @Query("select " +
                "new com.phincon.talents.app.dto.loan.LoanCategoryListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.occurenceLimit" +
                ",u.isSimulation" +
                ",u.description" +
                ",u.approvalLevelFleet" +
                ",u.requestCategoryTypeId" +
                ",rct.name" +
            ") " +
            "FROM " +
                "LoLoanCategory u " +
                "left join RequestCategoryType rct ON rct.id = u.requestCategoryTypeId " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                "AND (CAST(:requestCategoryName as string) is null or rct.name = cast(:requestCategoryName as string) ) "
    )
    public Page<LoanCategoryListDTO> listData(
            @Param("name") String name
            ,@Param("startDate") LocalDate startDate
            ,@Param("endDate") LocalDate endDate
            ,@Param("requestCategoryName") String requestCategoryName
            ,Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanCategoryDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.occurenceLimit" +
                ",u.isSimulation" +
                ",u.description" +
                ",u.approvalLevelFleet" +
                ",u.requestCategoryTypeId" +
                ",rct.name" +
            ") " +
            "FROM " +
              "LoLoanCategory u " +
                "left join RequestCategoryType rct ON rct.id = u.requestCategoryTypeId " +
            "WHERE 1=1 " +
               "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanCategoryDetailDTO> getData(@Param("id") String id);

    @Query("select " +
                "CASE WHEN COUNT(u.id) > 0 THEN True ELSE False END " +
            "FROM " +
                "LoVehicleBrand u " +
            "WHERE 1=1 " +
                "AND (u.loanCategoryId = cast(:loanCategoryId as string)) " +
                "AND (cast(:effectiveDate as date) BETWEEN u.startDate and u.endDate ) " +
            "GROUP BY u.loanCategoryId "
    )
    public Boolean checkIsVehicles(@Param("loanCategoryId") String loanCategoryId,@Param("effectiveDate") LocalDate effectiveDate);
}
