package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanCriteriaDetailDTO;
import com.phincon.talents.app.dto.loan.LoanCriteriaListDTO;
import com.phincon.talents.app.dto.loan.LoanRatingEmploymentDTO;
import com.phincon.talents.app.model.loan.LoCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface LoCriteriaRepository extends CrudRepository<LoCriteria,String>, PagingAndSortingRepository<LoCriteria,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanCriteriaListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.organizationId" +
                ",u.organizationGroupId" +
                ",u.positionId" +
                ",u.positionLevelId" +
                ",u.jobTitleId" +
                ",u.jobFamilyId" +
                ",u.workLocationType" +
                ",u.companyOfficeId" +
                ",u.gradeId" +
                ",u.employmentStatus" +
                ",u.ratingId" +
                ",u.minLos" +
                ",u.maxLos" +
            ") " +
            "FROM " +
                "LoCriteria u " +
            "WHERE 1=1 " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) "
    )
    public Page<LoanCriteriaListDTO> listData(
            @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanTypeId") String loanTypeId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanCriteriaDetailDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.organizationId" +
                ",u.organizationGroupId" +
                ",u.positionId" +
                ",u.positionLevelId" +
                ",u.jobTitleId" +
                ",u.jobFamilyId" +
                ",u.workLocationType" +
                ",u.companyOfficeId" +
                ",u.gradeId" +
                ",u.employmentStatus" +
                ",u.ratingId" +
                ",u.minLos" +
                ",u.maxLos" +
            ") " +
            "FROM " +
                  "LoCriteria u " +
            "WHERE 1=1 " +
                 "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanCriteriaDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanCriteriaListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanTypeId" +
                ",u.organizationId" +
                ",u.organizationGroupId" +
                ",u.positionId" +
                ",u.positionLevelId" +
                ",u.jobTitleId" +
                ",u.jobFamilyId" +
                ",u.workLocationType" +
                ",u.companyOfficeId" +
                ",u.gradeId" +
                ",u.employmentStatus" +
                ",u.ratingId" +
                ",u.minLos" +
                ",u.maxLos" +
            ") " +
            "FROM " +
                "LoCriteria u " +
//                "LEFT JOIN PARating par ON u.ratingId = par.id " +
            "WHERE 1=1 " +
                "AND cast(:dateUsed as date) BETWEEN cast(u.startDate as date) AND cast(u.endDate as date)" +
                "AND u.loanTypeId = cast(:loanTypeId as string)  " +

                "AND (CAST(u.organizationId as string) is null or u.organizationId = cast(:organizationId as string) ) " +
                "AND (cast(u.organizationGroupId as string) is null or u.organizationGroupId = cast(:organizationGroupId as string) )" +
                "AND (cast(u.positionId as string) is null or u.positionId = cast(:positionId as string) )" +
                "AND (cast(u.positionLevelId as string) is null or u.positionLevelId = cast(:positionLevelId as string) )" +
                "AND (cast(u.jobTitleId as string) is null or u.jobTitleId = cast(:jobTitleId as string) )" +
                "AND (cast(u.jobFamilyId as string) is null or u.jobFamilyId = cast(:jobFamilyId as string) )" +
                "AND (cast(u.workLocationType as string) is null or u.workLocationType = cast(:workLocationType as string) )" +
                "AND (cast(u.companyOfficeId as string) is null or u.companyOfficeId = cast(:companyOfficeId as string) )" +
                "AND (cast(u.gradeId as string) is null or u.gradeId = cast(:gradeId as string) )" +
                "AND (cast(u.employmentStatus as string) is null or u.employmentStatus = cast(:employmentStatus as string) )" +
//                "AND (par.id is null or (:score BETWEEN par.rangeFrom AND par.rangeTo) ) " +

                "AND (" +
                    "(cast(u.minLos as string) is not null and cast(u.maxLos as string) is not null and (cast(:lengthOfService as integer) BETWEEN u.minLos and u.maxLos) )  " +
                    "or (cast(u.minLos as string) is not null and cast(u.maxLos as string) is null and (cast(:lengthOfService as integer)>= u.minLos))  " +
                    "or (cast(u.maxLos as string) is not null and cast(u.minLos as string) is null and (cast(:lengthOfService as integer)<= u.maxLos))  " +

                ") " +

                "AND (" +
                    "u.ratingId is null or " +
                    "(" +
                        "(cast(:sizeMatrixResultNull as integer) > 0 AND u.ratingId IN :matrixResultNull  )" +
                        "or (cast(:sizeMatrixResultYear1 as integer) > 0 AND u.ratingId IN :matrixResultYear1  )" +
                        "or (cast(:sizeMatrixResultYear2Atas as integer) > 0 AND u.ratingId IN :matrixResultYear2Atas)" +
                        "or (cast(:sizeMatrixResultYear2Bawah as integer) > 0 AND u.ratingId IN :matrixResultYear2Bawah )" +
                    ")" +
                ") "

    )
    public List<LoanCriteriaListDTO> findAllByLoanTypeId(
            @Param("dateUsed") LocalDate dateUsed
            , @Param("loanTypeId") String loanTypeId

            ,@Param("organizationId") String organizationId
            ,@Param("organizationGroupId") String organizationGroupId
            ,@Param("positionId") String positionId
            ,@Param("positionLevelId") String positionLevelId
            ,@Param("jobTitleId") String jobTitleId
            ,@Param("jobFamilyId") String jobFamilyId
            ,@Param("workLocationType") String workLocationType
            ,@Param("companyOfficeId") String companyOfficeId
            ,@Param("gradeId") String gradeId
            ,@Param("employmentStatus") String employmentStatus
//            ,@Param("score") Double score

            ,@Param("lengthOfService") Integer lengthOfService
            ,@Param("sizeMatrixResultNull") Integer sizeMatrixResultNull
            ,@Param("matrixResultNull") List<String> matrixResultNull
            ,@Param("sizeMatrixResultYear1") Integer sizeMatrixResultYear1
            ,@Param("matrixResultYear1") List<String> matrixResultYear1
            ,@Param("sizeMatrixResultYear2Atas") Integer sizeMatrixResultYear2Atas
            ,@Param("matrixResultYear2Atas") List<String> matrixResultYear2Atas
            ,@Param("sizeMatrixResultYear2Bawah") Integer sizeMatrixResultYear2Bawah
            ,@Param("matrixResultYear2Bawah") List<String> matrixResultYear2Bawah
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingEmploymentDTO(" +
                "pep.id" +
                ",pep.employmentId" +
                ",pep.asOfDate" +
                ",pep.startDate" +
                ",pep.endDate" +
                ",pep.totalScore" +
                ",pep.totalRemark" +
            ") " +
            "FROM " +
                "PAEmployeePerformance pep " +
            "WHERE 1=1 " +
                "AND ( pep.status = 'Final Assessment' ) " +
//                "AND (cast(:effectiveDate as date) BETWEEN pep.startDate AND pep.endDate ) " +
                "AND (MONTH(pep.asOfDate) BETWEEN 7 AND 12 ) " +
                "AND ( pep.employmentId = cast(:employmentId as string) ) " +
                "AND ( pep.finalRating is not null ) " +
            "ORDER BY pep.asOfDate DESC"
    )
    public List<LoanRatingEmploymentDTO> findRatingByEmploymentId(
//            @Param("effectiveDate") LocalDate effectiveDate
            @Param("employmentId") String employmentId
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanCriteriaListDTO(" +
            "u.id" +
            ",u.startDate " +
            ",u.endDate " +
            ",u.name " +
            ",u.description" +
            ",u.loanTypeId" +
            ",u.organizationId" +
            ",u.organizationGroupId" +
            ",u.positionId" +
            ",u.positionLevelId" +
            ",u.jobTitleId" +
            ",u.jobFamilyId" +
            ",u.workLocationType" +
            ",u.companyOfficeId" +
            ",u.gradeId" +
            ",u.employmentStatus" +
            ",u.ratingId" +
            ",u.minLos" +
            ",u.maxLos" +
            ") " +
            "FROM " +
            "LoCriteria u " +
            "WHERE 1=1 " +
                "AND u.loanTypeId = cast(:loanTypeId as string) " +
                "AND current_date BETWEEN u.startDate and u.endDate "
    )
    public List<LoanCriteriaListDTO> findByLoanTypeId(
            @Param("loanTypeId") String loanTypeId
    );
}
