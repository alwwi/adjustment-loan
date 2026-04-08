package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.VehicleBrandDetailDTO;
import com.phincon.talents.app.dto.loan.VehicleBrandListDTO;
import com.phincon.talents.app.model.loan.LoVehicleBrand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface LoVehicleBrandRepository extends CrudRepository<LoVehicleBrand,String>, PagingAndSortingRepository<LoVehicleBrand,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleBrandListDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.loanVehicleTypeId" +
                ",lvt.name" +
                ",u.employeePercentage" +
                ",u.companyPercentage" +
                ",u.engineType" +
            ") " +
            "FROM " +
                "LoVehicleBrand u " +
                "LEFT JOIN LoVehicleType lvt ON u.loanVehicleTypeId = lvt.id " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name LIKE cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:categoryId as string) is null or u.loanCategoryId = cast(:categoryId as string) ) " +
                "AND (CAST(:vehicleTypeId as string) is null or u.loanVehicleTypeId = cast(:vehicleTypeId as string) ) "
    )
    public Page<VehicleBrandListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("categoryId") String categoryId
            , @Param("vehicleTypeId") String vehicleTypeId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.VehicleBrandDetailDTO(" +
                "u.id" +
                ",u.name" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.description" +
                ",u.loanVehicleTypeId" +
                ",lvt.name" +
                ",u.employeePercentage" +
                ",u.companyPercentage" +
                ",u.engineType" +
            ") " +
            "FROM " +
                "LoVehicleBrand u " +
                "LEFT JOIN LoVehicleType lvt ON u.loanVehicleTypeId = lvt.id " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<VehicleBrandDetailDTO> getData(@Param("id") String id);
}
