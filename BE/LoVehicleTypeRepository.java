package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.VehicleTypeDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleTypeListDTO;
import com.phincon.talents.app.model.loan.LoVehicleBrand;
import com.phincon.talents.app.model.loan.LoVehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoVehicleTypeRepository extends CrudRepository<LoVehicleType,String>, PagingAndSortingRepository<LoVehicleType,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleTypeListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
            ") " +
            "FROM " +
                "LoVehicleType u " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) "
    )
    public Page<VehicleTypeListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleTypeListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
            ") " +
            "FROM " +
                "LoVehicleType u " +
                "JOIN LoVehicleBrand lvb ON u.id = lvb.loanVehicleTypeId " +
                "JOIN LoLoanCategory llc ON lvb.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                "AND (CAST(:loanCategoryId as string) is null or llc.id = cast(:loanCategoryId as string) ) " +
            "GROUP BY u.id "
    )
    public List<VehicleTypeListDTO> listDataByLoanCategory(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanCategoryId") String loanCategoryId
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleTypeListDTO(" +
            "u.id" +
            ",u.name" +
            ",u.startDate" +
            ",u.endDate" +
            ") " +
            "FROM " +
                "LoVehicleType u " +
                "JOIN LoVehicleBrand lvb ON u.id = lvb.loanVehicleTypeId " +
                "JOIN LoLoanCategory llc ON lvb.loanCategoryId = llc.id " +
                "JOIN LoLoanType llt ON llt.loanCategoryId = llc.id " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                "AND (CAST(:loanTypeId as string) is null or llt.id = cast(:loanTypeId as string) ) " +
            "GROUP BY u.id "
    )
    public List<VehicleTypeListDTO> listDataByLoanType(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanTypeId") String loanTypeId
    );


    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleTypeDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
            ") " +
            "FROM " +
                "LoVehicleType u " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<VehicleTypeDetailDTO> getData(@Param("id") String id);
}
