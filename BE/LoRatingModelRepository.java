package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanRatingModelDetailDTO;
import com.phincon.talents.app.dto.loan.LoanRatingModelListDTO;
import com.phincon.talents.app.dto.loan.VehicleTypeDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleTypeListDTO;
import com.phincon.talents.app.model.loan.LoRatingModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoRatingModelRepository extends CrudRepository<LoRatingModel,String>, PagingAndSortingRepository<LoRatingModel,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.paRatingId" +
                ",u.yearBefore" +
                ",u.loanCategoryId" +
                ",pr.name" +
            ") " +
            "FROM " +
                "LoRatingModel u " +
                "LEFT JOIN PARating pr ON u.paRatingId = pr.id " +
            "WHERE 1=1 " +
            "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
            //check overlap date
            "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
            "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
            "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) "
    )
    public Page<LoanRatingModelListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanCategoryId") String loanCategoryId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.paRatingId" +
                ",u.yearBefore" +
                ",u.loanCategoryId" +
                ",pr.name" +
            ") " +
            "FROM " +
                "LoRatingModel u " +
                "LEFT JOIN PARating pr ON u.paRatingId = pr.id " +
            "WHERE 1=1 " +
            "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanRatingModelDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.paRatingId" +
                ",u.yearBefore" +
                ",u.loanCategoryId" +
                ",pr.name" +
                ",pr.rangeFrom" +
                ",pr.rangeTo" +
            ") " +
            "FROM " +
                "LoRatingModel u " +
                "LEFT JOIN PARating pr ON u.paRatingId = pr.id " +
            "WHERE 1=1 " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) " +
                "AND cast(:currentDate as date) BETWEEN u.startDate AND u.endDate "
    )
    public List<LoanRatingModelListDTO> findByLoanCategoryId(@Param("loanCategoryId") String loanCategoryId,@Param("currentDate") LocalDate currentDate);

}
