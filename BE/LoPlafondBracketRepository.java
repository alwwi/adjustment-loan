package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanCriteriaDetailDTO;
import com.phincon.talents.app.dto.loan.LoanCriteriaListDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketDetailDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketListDTO;
import com.phincon.talents.app.model.loan.LoLoanType;
import com.phincon.talents.app.model.loan.LoPlafondBracket;
import com.phincon.talents.app.model.loan.LoRatingModelMatrix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoPlafondBracketRepository extends CrudRepository<LoPlafondBracket,String>, PagingAndSortingRepository<LoPlafondBracket,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.PlafondBracketListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanCategoryId" +
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
                ",u.minValue" +
                ",u.maxValue" +
                ",u.elementName" +
                ",u.multiplier" +
            ") " +
            "FROM " +
                "LoPlafondBracket u " +
            "WHERE 1=1 " +
                "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) "
    )
    public Page<PlafondBracketListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanCategoryId") String loanCategoryId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.PlafondBracketDetailDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanCategoryId" +
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
                ",u.minValue" +
                ",u.maxValue" +
                ",u.elementName" +
                ",u.multiplier" +
            ") " +
            "FROM " +
                "LoPlafondBracket u " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<PlafondBracketDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.PlafondBracketListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanCategoryId" +
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
                ",u.minValue" +
                ",u.maxValue" +
                ",u.elementName" +
                ",u.multiplier" +
            ") " +
            "FROM " +
                "LoPlafondBracket u " +
                "LEFT JOIN LoRatingModel par ON u.ratingId = par.id " +
            "WHERE 1=1 " +
                "AND  u.minValue IS NOT NULL " +
                "AND  u.maxValue IS NOT NULL " +

                "AND cast(:dateUsed as date) BETWEEN cast(u.startDate as date) AND cast(u.endDate as date) " +
                "AND ( u.loanCategoryId = (:loanCategoryId) ) " +

                "AND (u.organizationId is null or u.organizationId = cast(:organizationId as string) ) " +
                "AND (u.organizationGroupId is null or u.organizationGroupId = cast(:organizationGroupId as string) )" +
                "AND (u.positionId is null or u.positionId = cast(:positionId as string) )" +
                "AND (u.positionLevelId is null or u.positionLevelId = cast(:positionLevelId as string) )" +
                "AND (u.jobTitleId is null or u.jobTitleId = cast(:jobTitleId as string) )" +
                "AND (u.jobFamilyId is null or u.jobFamilyId = cast(:jobFamilyId as string) )" +
                "AND (u.workLocationType is null or u.workLocationType = cast(:workLocationType as string) )" +
                "AND (u.companyOfficeId is null or u.companyOfficeId = cast(:companyOfficeId as string) )" +
                "AND (u.gradeId is null or u.gradeId = cast(:gradeId as string) )" +
                "AND (u.employmentStatus is null or u.employmentStatus = cast(:employmentStatus as string) )" +
//                "AND (par.id is null or (:score BETWEEN par.rangeFrom AND par.rangeTo) ) " +

                "AND (" +
                    "u.ratingId is null or " +
                    "(" +
                        "(cast(:sizeMatrixResultNull as integer) > 0 AND u.ratingId IN :matrixResultNull AND :totalLoan BETWEEN u.minValue AND u.maxValue )" +
                        "or (cast(:sizeMatrixResultYear1 as integer) > 0 AND u.ratingId IN :matrixResultYear1 AND :totalLoan BETWEEN u.minValue AND u.maxValue )" +
                        "or (cast(:sizeMatrixResultYear2Atas as integer) > 0 AND u.ratingId IN :matrixResultYear2Atas AND :totalLoan BETWEEN 0.0 AND u.maxValue )" +
                        "or (cast(:sizeMatrixResultYear2Bawah as integer) > 0 AND u.ratingId IN :matrixResultYear2Bawah AND :totalLoan BETWEEN 0.0 AND u.minValue )" +
                    ")" +
                ") "

    )
    List<PlafondBracketListDTO> findAllWithMinMaxValueByLoanCategoryId(@Param("dateUsed") LocalDate dateUsed
            ,@Param("loanCategoryId") String  loanCategoryId

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

            ,@Param("totalLoan") Double totalLoan

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
            "new com.phincon.talents.app.dto.loan.PlafondBracketListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanCategoryId" +
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
                ",u.minValue" +
                ",u.maxValue" +
                ",u.elementName" +
                ",u.multiplier" +
                ",md.totalElementValue" +
            ") " +
            "FROM " +
                "LoPlafondBracket u " +
//                "LEFT JOIN PARating par ON u.ratingId = par.id " +

                //get only the plafond bracket that have elementName
                "JOIN PayrollMonthlyDetail md ON md.elementName = u.elementName AND md.totalElementValue IS NOT NULL " +
                "JOIN PayrollMonthlyHeader mh ON mh.id = md.monthlyHeaderId " +
                            "AND mh.employmentId = :employmentId " +
                            "AND (u.positionId is null or mh.positionId = u.positionId) " +
                            "AND (u.organizationId is null or mh.organizationId = u.organizationId) " +
                            "AND (u.companyOfficeId is null or mh.companyOfficeId = u.companyOfficeId) " +
                            "AND YEAR (mh.paymentStartDate) = cast(:year as integer) " +
                            "AND MONTH (mh.paymentStartDate) = cast(:month as integer) " +
            "WHERE 1=1 " +
                "AND  u.elementName IS NOT NULL " +
                "AND  u.multiplier IS NOT NULL " +

                "AND cast(:dateUsed as date) BETWEEN cast(u.startDate as date) AND cast(u.endDate as date) " +
                "AND ( u.loanCategoryId = (:loanCategoryId ) ) " +

                "AND (u.organizationId is null or u.organizationId = cast(:organizationId as string) ) " +
                "AND (u.organizationGroupId is null or u.organizationGroupId = cast(:organizationGroupId as string) )" +
                "AND (u.positionId is null or u.positionId = cast(:positionId as string) )" +
                "AND (u.positionLevelId is null or u.positionLevelId = cast(:positionLevelId as string) )" +
                "AND (u.jobTitleId is null or u.jobTitleId = cast(:jobTitleId as string) )" +
                "AND (u.jobFamilyId is null or u.jobFamilyId = cast(:jobFamilyId as string) )" +
                "AND (u.workLocationType is null or u.workLocationType = cast(:workLocationType as string) )" +
                "AND (u.companyOfficeId is null or u.companyOfficeId = cast(:companyOfficeId as string) )" +
                "AND (u.gradeId is null or u.gradeId = cast(:gradeId as string) )" +
                "AND (u.employmentStatus is null or u.employmentStatus = cast(:employmentStatus as string) )" +
//                "AND (par.id is null or (:score BETWEEN par.rangeFrom AND par.rangeTo) ) "  +
                "AND  (:totalLoan <= cast(u.multiplier as double) * cast(f_aes_double_decrypt(md.totalElementValue,:secret) as double))  " +
                "AND (" +
                    "u.ratingId is null or " +
                    "(" +
                        "(cast(:sizeMatrixResultNull as integer) > 0 AND u.ratingId IN :matrixResultNull AND :totalLoan * 1.0 BETWEEN u.minValue * 1.0 AND u.maxValue * 1.0 )" +
                        "or (cast(:sizeMatrixResultYear1 as integer) > 0 AND u.ratingId IN :matrixResultYear1 AND :totalLoan * 1.0 BETWEEN u.minValue * 1.0 AND u.maxValue * 1.0 )" +
                        "or (cast(:sizeMatrixResultYear2Atas as integer) > 0 AND u.ratingId IN :matrixResultYear2Atas AND :totalLoan * 1.0 BETWEEN 0.0 AND u.maxValue * 1.0 )" +
                        "or (cast(:sizeMatrixResultYear2Bawah as integer) > 0 AND u.ratingId IN :matrixResultYear2Bawah AND :totalLoan * 1.0 BETWEEN 0.0 AND u.minValue * 1.0 )" +
                    ")" +
                ") "

//                "AND  (" +
//                    ":totalLoan BETWEEN " +
//                    "(" +
//                        "CASE " +
//                            "WHEN :matrixResult IS null THEN u.minValue " +
//                            "WHEN :matrixResult = '" + LoRatingModelMatrix.RESULT_BATAS_BAWAH + "' THEN 0 " +
//                            "WHEN :matrixResult = '" + LoRatingModelMatrix.RESULT_BATAS_ATAS + "' THEN 0 " +
//                        "ELSE u.minValue END " +
//                    ") " +
//                    "AND " +
//                    "(" +
//                        "CASE " +
//                            "WHEN :matrixResult IS null THEN u.maxValue " +
//                            "WHEN :matrixResult = '" + LoRatingModelMatrix.RESULT_BATAS_BAWAH + "' THEN u.minValue " +
//                            "WHEN :matrixResult = '" + LoRatingModelMatrix.RESULT_BATAS_ATAS + "' THEN u.maxValue " +
//                        "ELSE u.maxValue END " +
//                        ") " +
//                    ")  "

    )
    List<PlafondBracketListDTO> findAllWithElementNameByLoanCategoryId(@Param("dateUsed") LocalDate dateUsed
            ,@Param("loanCategoryId") String  loanCategoryId

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

            ,@Param("totalLoan") Double totalLoan
            , @Param("year") Integer year
            ,@Param("month") Integer month
            ,@Param("employmentId") String employmentId

            ,@Param("sizeMatrixResultNull") Integer sizeMatrixResultNull
            ,@Param("matrixResultNull") List<String> matrixResultNull
            ,@Param("sizeMatrixResultYear1") Integer sizeMatrixResultYear1
            ,@Param("matrixResultYear1") List<String> matrixResultYear1
            ,@Param("sizeMatrixResultYear2Atas") Integer sizeMatrixResultYear2Atas
            ,@Param("matrixResultYear2Atas") List<String> matrixResultYear2Atas
            ,@Param("sizeMatrixResultYear2Bawah") Integer sizeMatrixResultYear2Bawah
            ,@Param("matrixResultYear2Bawah") List<String> matrixResultYear2Bawah
            ,@Param("secret") String secret
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.PlafondBracketListDTO(" +
                "u.id" +
                ",u.startDate " +
                ",u.endDate " +
                ",u.name " +
                ",u.description" +
                ",u.loanCategoryId" +
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
                ",u.minValue" +
                ",u.maxValue" +
                ",u.elementName" +
                ",u.multiplier" +
            ") " +
            "FROM " +
                "LoPlafondBracket u " +
            "WHERE 1=1 " +
                "AND u.loanCategoryId = cast(:loanCategoryId as string) " +
                "AND current_date BETWEEN u.startDate and u.endDate "
    )
    public List<PlafondBracketListDTO> findByLoanCategoryId(
            @Param("loanCategoryId") String loanCategoryId
    );
    @Query("select " +
    "new com.phincon.talents.app.dto.loan.PlafondBracketListDTO(" +
    "u.id, u.startDate, u.endDate, u.name, u.description, u.loanCategoryId, " +
    "u.organizationId, u.organizationGroupId, u.positionId, u.positionLevelId, " +
    "u.jobTitleId, u.jobFamilyId, u.workLocationType, u.companyOfficeId, " +
    "u.gradeId, u.employmentStatus, u.ratingId, u.minValue, u.maxValue, " +
    "u.elementName, u.multiplier) " +
    "FROM LoPlafondBracket u " +
    "LEFT JOIN LoRatingModel par ON u.ratingId = par.id " +
    "WHERE 1=1 " +
    "AND u.minValue IS NOT NULL " +
    "AND u.maxValue IS NOT NULL " +
    "AND cast(:dateUsed as date) BETWEEN cast(u.startDate as date) AND cast(u.endDate as date) " +
    "AND (u.loanCategoryId = :loanCategoryId) " +
    "AND (u.organizationId IS NULL OR u.organizationId = :organizationId) " +
    "AND (u.organizationGroupId IS NULL OR u.organizationGroupId = :organizationGroupId) " +
    "AND (u.positionId IS NULL OR u.positionId = :positionId) " +
    "AND (u.positionLevelId IS NULL OR u.positionLevelId = :positionLevelId) " +
    "AND (u.jobTitleId IS NULL OR u.jobTitleId = :jobTitleId) " +
    "AND (u.jobFamilyId IS NULL OR u.jobFamilyId = :jobFamilyId) " +
    "AND (u.workLocationType IS NULL OR u.workLocationType = :workLocationType) " +
    "AND (u.companyOfficeId IS NULL OR u.companyOfficeId = :companyOfficeId) " +
    "AND (u.gradeId IS NULL OR u.gradeId = :gradeId) " +
    "AND (u.employmentStatus IS NULL OR u.employmentStatus = :employmentStatus) " +
    "AND (u.ratingId IS NULL " +
    "     OR (:sizeMatrixResultNull > 0 AND u.ratingId IN (:matrixResultNull)) " +
    "     OR (:sizeMatrixResultYear1 > 0 AND u.ratingId IN (:matrixResultYear1)) " +
    "     OR (:sizeMatrixResultYear2Atas > 0 AND u.ratingId IN (:matrixResultYear2Atas)) " +
    "     OR (:sizeMatrixResultYear2Bawah > 0 AND u.ratingId IN (:matrixResultYear2Bawah)) " +
    ")"
)
List<PlafondBracketListDTO> findMinMaxValueByLoanCategoryId(
    @Param("dateUsed") LocalDate dateUsed,
    @Param("loanCategoryId") String loanCategoryId,
    @Param("organizationId") String organizationId,
    @Param("organizationGroupId") String organizationGroupId,
    @Param("positionId") String positionId,
    @Param("positionLevelId") String positionLevelId,
    @Param("jobTitleId") String jobTitleId,
    @Param("jobFamilyId") String jobFamilyId,
    @Param("workLocationType") String workLocationType,
    @Param("companyOfficeId") String companyOfficeId,
    @Param("gradeId") String gradeId,
    @Param("employmentStatus") String employmentStatus,
    @Param("sizeMatrixResultNull") Integer sizeMatrixResultNull,
    @Param("matrixResultNull") List<String> matrixResultNull,
    @Param("sizeMatrixResultYear1") Integer sizeMatrixResultYear1,
    @Param("matrixResultYear1") List<String> matrixResultYear1,
    @Param("sizeMatrixResultYear2Atas") Integer sizeMatrixResultYear2Atas,
    @Param("matrixResultYear2Atas") List<String> matrixResultYear2Atas,
    @Param("sizeMatrixResultYear2Bawah") Integer sizeMatrixResultYear2Bawah,
    @Param("matrixResultYear2Bawah") List<String> matrixResultYear2Bawah
);


}
