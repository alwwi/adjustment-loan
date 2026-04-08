package com.phincon.external.app.dao.loan;

import com.phincon.external.app.dao.SalesforceRepository;
import com.phincon.external.app.model.loan.LoSyncLoanRequestDtl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LoSyncLoanRequestDtlSalesForceRepository extends SalesforceRepository<LoSyncLoanRequestDtl, String> {
    String raw_data_cte =
            "with cte_employee_performance as (" +
                "select " +
                    "pep.employment_id" +
                    ",pep.final_rating" +
                    ",pep.end_date" +
                    ",pep.final_remark" +
                    ",pep.total_score" +
                    ",ROW_NUMBER() OVER (PARTITION BY employment_id ORDER BY end_date asc) as years " +
                "from " +
                    "pa_employee_performance pep " +
                    "left join pa_rating pa ON pep.final_rating = pa.name " +
                "where 1=1 " +
//                    "AND cast(:dateUsed as date) <= pep.end_date " +
//                    "and pep.final_rating is not null " +
//                    "and MONTH(pep.end_date) = 12 " +
//                    "and pep.final_remark LIKE '%Final Balancing Semester%' " +
                    "AND extract(year from pep.as_of_date) >= extract(year from cast(:dateUsed as date)) - 2 " +
                    "and pep.final_rating is not null " +
                    "and pep.final_remark LIKE '%Final Balancing Semester%' " +
                ")," +
            "cte_employee_employee_performance_2_years as (" +
                    "select " +
                        "employment_id" +
                        ",MAX(CASE WHEN years =1 THEN final_remark ELSE '' END) AS final_remark_year_1" +
                        ",MAX(CASE WHEN years =1 THEN final_rating ELSE '' END) AS final_rating_year_1" +
                        ",MAX(CASE WHEN years =1 THEN end_date ELSE '' END) AS end_date_year_1" +
                        ",MAX(CASE WHEN years =1 THEN total_score ELSE '' END) AS total_score_year_1" +
                        ",MAX(CASE WHEN years =2 THEN final_remark ELSE '' END) AS final_remark_year_2" +
                        ",MAX(CASE WHEN years =2 THEN final_rating ELSE '' END) AS final_rating_year_2" +
                        ",MAX(CASE WHEN years =2 THEN end_date ELSE '' END) AS end_date_year_2" +
                        ",MAX(CASE WHEN years =2 THEN total_score ELSE '' END) AS total_score_year_2 " +
                "FROM " +
                    "cte_employee_performance " +
                "WHERE years <=2 " +
                "GROUP BY employment_id " +
            ") ";

    String raw_data_element =
            "select  " +
                    "el.employment_id " +
                    ",el.loan_category_id " +
                    ",lab.id as allowance_bracket_id " +
                    ",lab.sync_element_name " +
                    ",cast(f_aes_double_decrypt(cast(md.total_element_value as varchar(255)),:secret) as double) * (CASE WHEN lab.multiplier is null then 0 ELSE lab.multiplier END) as value " +
                    ",lab.start_date " +
                    ",lab.end_date " +
                    ",MONTH(cast(:dateUsed as date)) as period_month  " +
                    ",YEAR(cast(:dateUsed as date)) as period_year  " +
                    ",(SELECT id_backup FROM lo_sync_loan_request_dtl lslrd WHERE lslrd.employment_id =el.employment_id AND lslrd.allowance_bracket_id = lab.id and  lslrd.period_month = MONTH(cast(:dateUsed as date)) AND lslrd.period_year = YEAR(cast(:dateUsed as date))) as update_id " +
            "from " +
                    "(  " +
                        "select  " +
                            "lel.employment_id,lel.loan_category_id,MIN(pep.total_score_year_1) as total_score_year_1,MIN(pep.total_score_year_2) as total_score_year_2  " +
                        "from  " +
                            "lo_employee_loan lel  " +
                            "left join cte_employee_employee_performance_2_years pep ON pep.employment_id = lel.employment_id  " +
                        "WHERE  " +
                            "lel.status = 'In Progress'  " +
                        "GROUP BY  " +
                            "employment_id,loan_category_id   " +
                    ") as el  " +
                    "JOIN lo_allowance_bracket lab ON el.loan_category_id = lab.loan_category_id and cast(:dateUsed as date) BETWEEN CAST(lab.start_date AS DATE) AND CAST(lab.end_date AS DATE)  " +
                        "AND  lab.element_name IS NOT NULL AND  lab.value IS NULL  " +
                    "join vw_employee_assignment vea ON el.employment_id = vea.employment_id  " +
                    "join hr_job_title hjt ON hjt.id = vea.job_title_id  " +
                    "join hr_job_family hjf ON hjt.job_family_id = hjf.id  " +

                    //same as value, only add this join
                    "JOIN hr_payroll_monthly_detail md ON md.element_name = lab.element_name AND md.total_element_value IS NOT NULL " +
                    "JOIN hr_payroll_monthly_header mh ON mh.id = md.monthly_header_id  " +
                        "AND mh.employment_id = el.employment_id  " +
                        "AND (lab.position_id is null or mh.position_id = lab.position_id) " +
                        "AND (lab.organization_id is null or mh.organization_id = lab.organization_id) " +
                        "AND (lab.company_office_id is null or mh.company_office_id = lab.company_office_id) " +
                        "AND YEAR (mh.payment_start_date) = YEAR(cast(:dateUsed as date) - interval 1 month) " +
                        "AND MONTH (mh.payment_start_date) = MONTH(cast(:dateUsed as date) - interval 1 month) " +

                    "LEFT JOIN lo_rating_model lrm ON lab.rating_id = lrm.id " +
                    "LEFT JOIN pa_rating par ON lrm.pa_rating_id = par.id " +
                    "LEFT JOIN lo_rating_model_matrix lrmMetrix ON lrmMetrix.loan_rating_model_id = lrm.id " +
                    "LEFT JOIN pa_rating parMetrixY1 ON lrmMetrix.pa_rating_id_1 = parMetrixY1.id " +
                    "LEFT JOIN pa_rating parMetrixY2 ON lrmMetrix.pa_rating_id_2 = parMetrixY2.id " +
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
//                    "AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) "
                    "AND (" +
                        "lrm.id is null or (" +
                            "(lrm.year_before = 1 AND coalesce(el.total_score_year_1,0) BETWEEN par.range_from AND par.range_to)" +
                            "or (lrm.year_before = 2" +
                                " AND coalesce(el.total_score_year_1,0) BETWEEN parMetrixY1.range_from AND parMetrixY1.range_to" +
                                " AND coalesce(el.total_score_year_2,0) BETWEEN parMetrixY2.range_from AND parMetrixY2.range_to" +
                                ")" +
                        ")" +
                    ")"
            ;

    public String raw_data_value =
            "select  " +
                    "el.employment_id " +
                    ",el.loan_category_id " +
                    ",lab.id as allowance_bracket_id " +
                    ",lab.sync_element_name " +
                    ",lab.value as value " +
                    ",lab.start_date " +
                    ",lab.end_date " +
                    ",MONTH(cast(:dateUsed as date)) as period_month  " +
                    ",YEAR(cast(:dateUsed as date)) as period_year  " +
                    ",(SELECT id_backup FROM lo_sync_loan_request_dtl lslrd WHERE lslrd.employment_id =el.employment_id AND lslrd.allowance_bracket_id = lab.id and  lslrd.period_month = MONTH(cast(:dateUsed as date)) AND lslrd.period_year = YEAR(cast(:dateUsed as date))) as update_id " +
            "from " +
                    "(  " +
                        "select  " +
                            "lel.employment_id,lel.loan_category_id,MIN(pep.total_score_year_1) as total_score_year_1,MIN(pep.total_score_year_2) as total_score_year_2  " +
                        "from  " +
                            "lo_employee_loan lel  " +
                            "left join cte_employee_employee_performance_2_years pep ON pep.employment_id = lel.employment_id  " +
                        "WHERE  " +
                            "lel.status = 'In Progress'  " +
                        "GROUP BY  " +
                            "employment_id,loan_category_id   " +
                    ") as el  " +
                    "JOIN lo_allowance_bracket lab ON el.loan_category_id = lab.loan_category_id and cast(:dateUsed as date) BETWEEN CAST(lab.start_date AS DATE) AND CAST(lab.end_date AS DATE)  " +
                        "AND  lab.element_name IS NULL AND  lab.value IS NOT NULL  " +
                    "join vw_employee_assignment vea ON el.employment_id = vea.employment_id  " +
                    "join hr_job_title hjt ON hjt.id = vea.job_title_id  " +
                    "join hr_job_family hjf ON hjt.job_family_id = hjf.id  " +

                    "LEFT JOIN lo_rating_model lrm ON lab.rating_id = lrm.id " +
                    "LEFT JOIN pa_rating par ON lrm.pa_rating_id = par.id " +
                    "LEFT JOIN lo_rating_model_matrix lrmMetrix ON lrmMetrix.loan_rating_model_id = lrm.id " +
                    "LEFT JOIN pa_rating parMetrixY1 ON lrmMetrix.pa_rating_id_1 = parMetrixY1.id " +
                    "LEFT JOIN pa_rating parMetrixY2 ON lrmMetrix.pa_rating_id_2 = parMetrixY2.id " +
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
//                    "AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) ";
                    "AND (" +
                        "lrm.id is null or (" +
                            "(lrm.year_before = 1 AND coalesce(el.total_score_year_1,0) BETWEEN par.range_from AND par.range_to)" +
                                "or (lrm.year_before = 2" +
                                " AND coalesce(el.total_score_year_1,0) BETWEEN parMetrixY1.range_from AND parMetrixY1.range_to" +
                                " AND coalesce(el.total_score_year_2,0) BETWEEN parMetrixY2.range_from AND parMetrixY2.range_to" +
                            ")" +
                        ")" +
                    ")";

    public String insertDataToSyncValue = "INSERT INTO lo_sync_loan_request_dtl(employment_id,allowance_bracket_id,value,period_month,period_year,need_sync) " +
            "SELECT employment_id,allowance_bracket_id,value,period_month,period_year,true FROM (" + raw_data_cte +  raw_data_value + ") as  raw_data WHERE update_id is null";

    public String updateDataToSyncValue =
            "UPDATE lo_sync_loan_request_dtl tableUpdate  " +
            "JOIN " +
                    "(" + raw_data_cte + "SELECT update_id,employment_id,allowance_bracket_id,value,period_month,period_year FROM (" +
                    raw_data_value +
                    ") as  raw_data WHERE update_id is not null " +
            ")  as tableData ON tableData.update_id = tableUpdate.id " +
            "SET " +
                    "tableUpdate.value = tableData.value," +
                    "tableUpdate.need_sync = true";

    public String insertDataToSyncElement = "INSERT INTO lo_sync_loan_request_dtl(employment_id,allowance_bracket_id,`value`,period_month,period_year,need_sync) " +
            "SELECT employment_id,allowance_bracket_id,`value`,period_month,period_year,true FROM (" +raw_data_cte +  raw_data_element + ") as  raw_data WHERE update_id is null";

    public String updateDataToSyncElement =
            "UPDATE lo_sync_loan_request_dtl tableUpdate  " +
            "JOIN " +
                    "(" + raw_data_cte + "SELECT update_id,employment_id,allowance_bracket_id,value,period_month,period_year FROM (" +
                    raw_data_element +
                    ") as  raw_data WHERE update_id is not null " +
           ")  as tableData ON tableData.update_id = tableUpdate.id " +
           "SET " +
                    "tableUpdate.value = tableData.value," +
                    "tableUpdate.need_sync = true";

    //-----------------------------------------------------------
    // Allowance Bracket need sync
    //-----------------------------------------------------------
    @Query(value = "SELECT " +
                        "group_concat(cast(u.ext_id as char(100))) as extIdLists " +
                        ", group_concat(u.id_backup) as loanRequestIdLists " +
                        ", group_concat(u.id) as loanRequestNoLists " +
                        ", vea.employee_no as  employeeNo " +
                        ", cast(SUM(u.value) as char(100)) as totalMonthlyInstallment " +
                        ", cast(:paymentDate as date) as paymentDate " +
                        ", cast(:requestDate as date) as requestDate " +
                        ", group_concat(llc.name) as loanCategoryLists " +
                        ", lab.sync_element_name as elementName  " +
                        ", 'lo_sync_loan_request_dtl' as tableName " +
                    "FROM lo_sync_loan_request_dtl u " +
                        "join lo_allowance_bracket lab ON u.allowance_bracket_id = lab.id and cast(:paymentDate as date) BETWEEN CAST(lab.start_date AS DATE) AND CAST(lab.end_date AS DATE) " +
                        "join lo_loan_category llc ON llc.id = lab.loan_category_id " +
                        "join vw_employee_assignment vea ON u.employment_id = vea.employment_id " +
                    "WHERE 1=1 " +
                    "GROUP BY " +
                            "vea.employee_no,lab.sync_element_name " +
                    "HAVING employeeNo is not null "
            ,nativeQuery = true)
    List<Map<String,Object>> findAllowanceBracketNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
                                                                           @Param("requestDate") LocalDate requestDate
    );
//
//    @Query(value =
//            "with employee_loan as ( " +
//                    "select " +
//                        "lel.employment_id,lel.loan_category_id,MIN(pep.total_score) as score " +
//                    "from " +
//                        "lo_employee_loan lel " +
//                        "left join pa_employee_performance pep ON pep.employment_id = lel.employment_id AND CURRENT_DATE BETWEEN pep.start_date and pep.end_date " +
//                    "WHERE " +
//                        "lel.status = 'In Progress' " +
//                    "GROUP BY " +
//                        "employment_id,loan_category_id  " +
//            ") " +
//            "select  " +
//                    "group_concat(cast(lslrd.ext_id as char(100))) as extIdLists " +
//                    ", group_concat(lslrd.id) as loanRequestIdLists " +
//                    ", group_concat(lslrd.id) as loanRequestNoLists " +
//                    ", vea.employee_no as  employeeNo " +
//                    ", cast(SUM(u.value) as char(100)) as totalMonthlyInstallment " +
//                    ", cast(:paymentDate as date) as paymentDate " +
//                    ", cast(:requestDate as date) as requestDate " +
//                    ", group_concat(llc.name) as loanCategoryLists " +
//                    ", u.sync_element_name as elementName  " +
//                    ", 'lo_sync_loan_request_dtl' as tableName " +
//            "FROM  " +
//                    "lo_allowance_bracket u  " +
//                    "LEFT JOIN pa_rating par ON u.rating_id = par.id  " +
//                    "JOIN lo_loan_category llc on llc.id = u.loan_category_id AND  u.element_name IS NOT NULL AND  u.value IS NULL " +
//                    "join employee_loan el ON el.loan_category_id = u.loan_category_id " +
//                    "join vw_employee_assignment vea ON el.employment_id = vea.employment_id " +
//                    "join hr_job_title hjt ON hjt.id = vea.job_title_id " +
//                    "join hr_job_family hjf ON hjt.job_family_id = hjf.id " +
//                    "LEFT JOIN lo_sync_loan_request_dtl lslrd ON lslrd.allowance_bracket_id = u.id and el.employment_id = lslrd.employment_id" +
//                    "JOIN hr_payroll_monthly_detail md ON md.element_name = u.element_name AND md.total_element_value IS NOT NULL  " +
//                    "JOIN hr_payroll_monthly_header mh ON mh.id = md.monthly_header_id  " +
//                            "AND mh.employment_id = el.employment_id  " +
//                            "AND (u.position_id is null or mh.position_id = u.position_id) " +
//                            "AND (u.organization_id is null or mh.organization_id = u.organization_id) " +
//                            "AND (u.company_office_id is null or mh.company_office_id = u.company_office_id) " +
//                            "AND YEAR (mh.payment_start_date) = YEAR(current_date - interval 1 month) " +
//                            "AND MONTH (mh.payment_start_date) = MONTH(current_date - interval 1 month) " +
//            "WHERE 1=1  " +
//                    "AND  u.element_name IS NOT NULL  " +
//                    "AND  u.value IS NULL " +
//                    "AND CURRENT_DATE BETWEEN CAST(u.start_date AS DATE) AND CAST(u.end_date AS DATE) " +
//
//                    "AND (u.organization_id is null or u.organization_id = vea.organization_id )  " +
//                    "AND (u.organization_group_id is null or u.organization_group_id = vea.organization_group_id )  " +
//                    "AND (u.position_id is null or u.position_id = vea.position_id )  " +
//                    "AND (u.position_level_id is null or u.position_level_id = vea.position_level_id )  " +
//                    "AND (u.job_title_id is null or u.job_title_id =vea.job_title_id ) " +
//                    "AND (u.job_family_id is null or u.job_family_id = hjf.id ) " +
//                    "AND (u.work_location_type is null or u.work_location_type =vea.work_location_type ) " +
//                    "AND (u.company_office_id is null or u.company_office_id = vea.company_office_id ) " +
//                    "AND (u.grade_id is null or u.grade_id = vea.grade_id ) " +
//                    "AND (u.employment_status is null or u.employment_status = vea.employee_status ) " +
//                    "AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) " +
//            "GROUP BY " +
//                    "vea.employee_no,u.sync_element_name " +
//            "HAVING employeeNo is not null "
//            ,nativeQuery = true)
//    List<Map<String,Object>> findAllowanceBracketElementNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
//                                                                           @Param("requestDate") LocalDate requestDate
//    );
//
//    @Query(value =
//            "with employee_loan as ( " +
//                    "select " +
//                        "lel.employment_id,lel.loan_category_id,MIN(pep.total_score) as score " +
//                    "from " +
//                        "lo_employee_loan lel " +
//                        "left join pa_employee_performance pep ON pep.employment_id = lel.employment_id AND CURRENT_DATE BETWEEN pep.start_date and pep.end_date " +
//                    "WHERE " +
//                        "lel.status = 'In Progress' " +
//                    "GROUP BY " +
//                        "employment_id,loan_category_id  " +
//            ") " +
//            "select  " +
//                    "group_concat(cast(lslrd.ext_id as char(100))) as extIdLists " +
//                    ", group_concat(lslrd.id) as loanRequestIdLists " +
//                    ", group_concat(lslrd.id) as loanRequestNoLists " +
//                    ", vea.employee_no as  employeeNo " +
//                    ", cast(SUM(u.value) as char(100)) as totalMonthlyInstallment " +
//                    ", cast(:paymentDate as date) as paymentDate " +
//                    ", cast(:requestDate as date) as requestDate " +
//                    ", group_concat(llc.name) as loanCategoryLists " +
//                    ", u.sync_element_name as elementName  " +
//                    ", 'lo_sync_loan_request_dtl' as tableName " +
//            "FROM  " +
//                    "lo_allowance_bracket u  " +
//                    "LEFT JOIN pa_rating par ON u.rating_id = par.id  " +
//                    "JOIN lo_loan_category llc on llc.id = u.loan_category_id AND  u.element_name IS NULL AND  u.value IS NOT NULL " +
//
//                    "join employee_loan el ON el.loan_category_id = u.loan_category_id " +
//                    "join vw_employee_assignment vea ON el.employment_id = vea.employment_id " +
//                    "join hr_job_title hjt ON hjt.id = vea.job_title_id " +
//                    "join hr_job_family hjf ON hjt.job_family_id = hjf.id " +
//                    "LEFT JOIN lo_sync_loan_request_dtl lslrd ON lslrd.allowance_bracket_id = u.id and el.employment_id = lslrd.employment_id " +
//
//            "WHERE 1=1  " +
//                    "AND  u.element_name IS NULL  " +
//                    "AND  u.value IS NOT NULL " +
//                    "AND CURRENT_DATE BETWEEN CAST(u.start_date AS DATE) AND CAST(u.end_date AS DATE) " +
//
//                    "AND (u.organization_id is null or u.organization_id = vea.organization_id )  " +
//                    "AND (u.organization_group_id is null or u.organization_group_id = vea.organization_group_id )  " +
//                    "AND (u.position_id is null or u.position_id = vea.position_id )  " +
//                    "AND (u.position_level_id is null or u.position_level_id = vea.position_level_id )  " +
//                    "AND (u.job_title_id is null or u.job_title_id =vea.job_title_id ) " +
//                    "AND (u.job_family_id is null or u.job_family_id = hjf.id ) " +
//                    "AND (u.work_location_type is null or u.work_location_type =vea.work_location_type ) " +
//                    "AND (u.company_office_id is null or u.company_office_id = vea.company_office_id ) " +
//                    "AND (u.grade_id is null or u.grade_id = vea.grade_id ) " +
//                    "AND (u.employment_status is null or u.employment_status = vea.employee_status ) " +
//                    "AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) " +
//            "GROUP BY " +
//                    "vea.employee_no,u.sync_element_name " +
//            "HAVING " +
//                    "employeeNo is not null "
//            ,nativeQuery = true)
//    List<Map<String,Object>> findAllowanceBracketValueNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
//                                                                         @Param("requestDate") LocalDate requestDate
//    );

}
