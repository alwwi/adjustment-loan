package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.AllowanceBracketDetailDTO;
import com.phincon.talents.app.dto.loan.AllowanceBracketListDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketDetailDTO;
import com.phincon.talents.app.dto.loan.PlafondBracketListDTO;
import com.phincon.talents.app.model.hr.Organization;
import com.phincon.talents.app.model.loan.LoAllowanceBracket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoAllowanceBracketRepository  extends  CrudRepository<LoAllowanceBracket,String>, PagingAndSortingRepository<LoAllowanceBracket, String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.AllowanceBracketListDTO(" +
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
            ",u.value" +
            ",u.elementName" +
            ",u.multiplier" +
            ",u.syncElementName" +
            ") " +
            "FROM " +
            "LoAllowanceBracket u " +
            "WHERE 1=1 " +
            "AND (CAST(:name as string) is null or u.name = cast(:name as string) ) " +
            //check overlap date
            "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
            "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
            //detail by id
            "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) "
    )
    public Page<AllowanceBracketListDTO> listData(
            @Param("name") String name
            , @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanCategoryId") String loanCategoryId
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.AllowanceBracketDetailDTO(" +
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
            ",u.value" +
            ",u.elementName" +
            ",u.multiplier" +
            ",u.syncElementName" +
            ") " +
            "FROM " +
            "LoAllowanceBracket u " +
            "WHERE 1=1 " +
            "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<AllowanceBracketDetailDTO> getData(@Param("id") String id);

    @Query(value = "select  " +
            "SUM(cast(f_aes_double_decrypt(md.total_element_value,:secret) as double) * (CASE WHEN lab.multiplier is null then 0 ELSE lab.multiplier END)) as value " +
            "from " +
            "lo_allowance_bracket lab " +
            "join vw_employee_assignment vea ON vea.employment_id = :employmentId and lab.loan_category_id = :loanCategoryId and cast(:dateUsed as date) BETWEEN CAST(lab.start_date AS DATE) AND CAST(lab.end_date AS DATE) AND  lab.element_name IS NOT NULL AND  lab.value IS NULL  " +
            "join hr_job_title hjt ON hjt.id = vea.job_title_id  " +
            "join hr_job_family hjf ON hjt.job_family_id = hjf.id  " +

            //same as value, only add this join
            "JOIN hr_payroll_monthly_detail md ON md.element_name = lab.element_name AND md.total_element_value IS NOT NULL " +
            "JOIN hr_payroll_monthly_header mh ON mh.id = md.monthly_header_id  " +
            "AND mh.employment_id = :employmentId  " +
            "AND (lab.position_id is null or mh.position_id = lab.position_id) " +
            "AND (lab.organization_id is null or mh.organization_id = lab.organization_id) " +
            "AND (lab.company_office_id is null or mh.company_office_id = lab.company_office_id) " +
            "AND YEAR (mh.payment_start_date) = YEAR(cast(:dateUsed as date) - interval 1 month) " +
            "AND MONTH (mh.payment_start_date) = MONTH(cast(:dateUsed as date) - interval 1 month) " +

            "LEFT JOIN pa_rating par ON lab.rating_id = par.id " +
            "where 1=1  " +
            "AND (lab.organization_id is null or lab.organization_id = vea.organization_id )   " +
            "AND (lab.organization_group_id is null or lab.organization_group_id = vea.organization_group_id )   " +
            "AND (lab.position_id is null or lab.position_id = vea.position_id )   " +
            "AND (lab.position_level_id is null or lab.position_level_id = vea.position_level_id )   " +
            "AND (lab.job_title_id is null or lab.job_title_id =vea.job_title_id )  " +
            "AND (lab.job_family_id is null or lab.job_family_id = hjf.id )  " +
            "AND (lab.work_location_type is null or lab.work_location_type =vea.work_location_type )  " +
            "AND (lab.company_office_id is null or lab.company_office_id = vea.company_office_id )  " +
            "AND (lab.grade_id is null or lab.grade_id = vea.grade_id )  " +
            "AND (lab.employment_status is null or lab.employment_status = vea.employee_status )  " +
            "AND (par.id is null or :score BETWEEN par.range_from AND par.range_to ) ",nativeQuery = true
    )
    public Double getTotalAllowanceElementByLoanCategoryId(@Param("loanCategoryId") String loanCategoryId,
                                                           @Param("dateUsed") LocalDate dateUsed,
                                                           @Param("employmentId") String employmentId,
                                                           @Param("score") Double score,
                                                           @Param("secret")String secret
                                                           );

    @Query(value = "select  " +
            "SUM(CASE WHEN lab.value IS NULL THEN 0 ELSE lab.value END) as value " +
            "from " +
            "lo_allowance_bracket lab " +
            "join vw_employee_assignment vea ON vea.employment_id = :employmentId and lab.loan_category_id = :loanCategoryId and cast(:dateUsed as date) BETWEEN CAST(lab.start_date AS DATE) AND CAST(lab.end_date AS DATE) AND  lab.element_name IS NULL AND  lab.value IS NOT NULL  " +
            "join hr_job_title hjt ON hjt.id = vea.job_title_id  " +
            "join hr_job_family hjf ON hjt.job_family_id = hjf.id  " +

            "LEFT JOIN pa_rating par ON lab.rating_id = par.id " +
            "where 1=1  " +
            "AND (lab.organization_id is null or lab.organization_id = vea.organization_id )   " +
            "AND (lab.organization_group_id is null or lab.organization_group_id = vea.organization_group_id )   " +
            "AND (lab.position_id is null or lab.position_id = vea.position_id )   " +
            "AND (lab.position_level_id is null or lab.position_level_id = vea.position_level_id )   " +
            "AND (lab.job_title_id is null or lab.job_title_id =vea.job_title_id )  " +
            "AND (lab.job_family_id is null or lab.job_family_id = hjf.id )  " +
            "AND (lab.work_location_type is null or lab.work_location_type =vea.work_location_type )  " +
            "AND (lab.company_office_id is null or lab.company_office_id = vea.company_office_id )  " +
            "AND (lab.grade_id is null or lab.grade_id = vea.grade_id )  " +
            "AND (lab.employment_status is null or lab.employment_status = vea.employee_status )  " +
            "AND (par.id is null or :score BETWEEN par.range_from AND par.range_to ) ",nativeQuery = true
    )
    public Double getTotalAllowanceValueByLoanCategoryId(@Param("loanCategoryId") String loanCategoryId,
                                                           @Param("dateUsed") LocalDate dateUsed,
                                                           @Param("employmentId") String employmentId,
                                                           @Param("score") Double score
    );

    @Query(value = "select MIN(pep.total_score) " +
            "FROM pa_employee_performance pep WHERE pep.employment_id = :employmentId AND cast(:dateUsed as date) BETWEEN pep.start_date and pep.end_date ",nativeQuery = true
    )
    public Double getEmploymentScore(@Param("employmentId") String employmentId,@Param("dateUsed") LocalDate dateUsed);
}
