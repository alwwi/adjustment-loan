package com.phincon.external.app.dao.loan;


import com.phincon.external.app.dao.SalesforceRepository;
import com.phincon.external.app.dto.LoanRequestSyncDTO;
import com.phincon.external.app.model.loan.LoEmployeeLoan;
import com.phincon.external.app.model.loan.LoLoanRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LoLoanRequestSalesForceRepository extends SalesforceRepository<LoLoanRequest, String> {
    //-----------------------------------------------------------
    // dummy
    //-----------------------------------------------------------
    @Query(value =
            " SELECT \n" +
                    "                    group_concat(cast(a.ext_id as char(100))) as extIdLists\n" +
                    "                    , group_concat(a.id) as loanRequestIdLists\n" +
                    "                    , group_concat(a.request_no) as loanRequestNoLists\n" +
                    "                    , vea.employee_no as  employeeNo\n" +
                    "                    , cast(SUM(100) as char(100)) as totalMonthlyInstallment\n" +
                    "                    , cast('2024-02-02' as date) as paymentDate\n" +
                    "                    , cast('2024-02-01' as date) as requestDate\n" +
                    "                    , group_concat(llc.name) as loanCategoryLists\n" +
                    "                    ,  'Potongan / Loan' as elementName \n" +
                    "                    , 'lo_loan_request' as tableName \n" +
                    "           FROM \n" +
                    "                    lo_loan_request a \n" +
                    "                    JOIN lo_loan_category llc on llc.id = a.loan_category_id and llc.`name`  = 'MOP 2024'\n" +
                    "                    \n" +
                    "                    LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id \n" +
                    "                    JOIN vw_employee_assignment vea ON vea.employment_id = a.employment_id \n" +
                    "           WHERE 1=1 \n" +
                    "           GROUP BY vea.employee_no  having employeeNo is not null  "
            ,nativeQuery = true)
    List<Map<String,Object>> dummydata(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("paymentDate") LocalDate paymentDate,
                                                    @Param("requestDate") LocalDate requestDate
    );



    //-----------------------------------------------------------
    // Tenor = 1
    //-----------------------------------------------------------
    @Query(value =
            "SELECT * FROM (" +
            "SELECT " +
                    "group_concat(cast(a.ext_id as char(100))) as extIdLists" +
                    ", group_concat(cast(a.id_backup as char)) as loanRequestIdLists" +
                    ", group_concat(a.request_no) as loanRequestNoLists" +
                    ", vea.employee_no as  employeeNo" +
                    ", cast(SUM(lel.monthly_installment) as char(100)) as totalMonthlyInstallment" +
                //     ", cast(:paymentDate as date) as paymentDate" +
                    ", DATE_ADD(cast(lel.go_live_date as date), INTERVAL lel.tenor MONTH) as paymentDate" +
                    ", cast(:requestDate as date) as requestDate" +
                    ", group_concat(llc.name) as loanCategoryLists" +
                    ", lff.sync_element_name as elementName " +
                    ", 'lo_loan_request' as tableName " +
           "FROM " +
                    "lo_loan_request a " +
                    "JOIN lo_employee_loan lel ON lel.request_no = a.request_no and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' and is_confirmed = true and a.need_sync = true  " +
                    "JOIN lo_loan_category llc on llc.id = a.loan_category_id " +
                    "" +
                    "JOIN lo_feedback_fleet lff ON lff.value_source = 'Monthly Installment' AND current_date BETWEEN lff.start_date and lff.end_date and lff.loan_category_id = llc.id " +
                    "LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id " +
                    "JOIN vw_employee_assignment vea ON vea.employment_id = lel.employment_id " +
           "WHERE 1=1 " +
                    "and a.need_sync=TRUE AND d.status='Approved' " +
                    "and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' " +
                    "AND lel.tenor = 1 " +
                    "and lff.sync_element_name is not null " +
                    "and lff.sync_element_name != ' ' " +
                    "and lff.sync_element_name != 'notused' " +
                    "and (:dateFrom is null or :dateTo is null) or cast(lel.go_live_date as date) BETWEEN cast(:dateFrom as date)  AND cast(:dateTo as date) " +
                    "group by vea.employee_no,lff.sync_element_name " +
            ") as raw_data " +
           "GROUP BY raw_data.employeeNo  having employeeNo is not null "
            ,nativeQuery = true)
    List<Map<String,Object>> findNeedSyncLoanTenor1(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo,
                                                          @Param("requestDate") LocalDate requestDate
    );



//     -----------------------------------------------------------
//      Tenor > 1
//     -----------------------------------------------------------
//     @Query(value =
//             "SELECT * FROM (" +
//             "SELECT " +
//                     "group_concat(cast(a.ext_id as char(100))) as extIdLists" +
//                     ", group_concat(cast(a.id_backup as char)) as loanRequestIdLists" +
//                     ", group_concat(a.request_no) as loanRequestNoLists" +
//                     ", vea.employee_no as  employeeNo" +
//                     ", cast(SUM(lel.monthly_installment) as char(100)) as totalMonthlyInstallment" +
//                     ", cast(:paymentDate as date) as paymentDate" +
//                     ", cast(:requestDate as date) as requestDate" +
//                     ", group_concat(llc.name) as loanCategoryLists" +
//                     ", lff.sync_element_name as elementName " +
//                     ", 'lo_loan_request' as tableName " +
//             "FROM " +
//                     "lo_loan_request a " +
//                     "JOIN lo_employee_loan lel ON lel.request_no = a.request_no and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' and is_confirmed = true and a.need_sync= true  " +
//                     "JOIN lo_loan_category llc on llc.id = a.loan_category_id " +

//                     "JOIN lo_feedback_fleet lff ON lff.value_source = 'Monthly Installment' AND current_date BETWEEN lff.start_date and lff.end_date and lff.loan_category_id = llc.id " +
//                     "LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id " +
//                     "JOIN vw_employee_assignment vea ON vea.employment_id = lel.employment_id " +
//             "WHERE 1=1 " +
//                     "and a.need_sync=TRUE AND d.status='Approved' " +
//                     "and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' " +
//                     "AND lel.tenor > 1 " +
//                     "and lff.sync_element_name is not null " +
//                     "and lff.sync_element_name != ' ' " +
//                     "and lff.sync_element_name != 'notused' " +
//             "group by vea.employee_no,lff.sync_element_name " +
//             ") as raw_data " +
//             "GROUP BY raw_data.employeeNo  having employeeNo is not null "
//             ,nativeQuery = true)
//     List<Map<String,Object>> findNeedSyncLoanTenorMoreThan1(@Param("paymentDate") LocalDate paymentDate,
//                                                     @Param("requestDate") LocalDate requestDate
//     );

    //-----------------------------------------------------------
    // MOP Tenor = 1
    //-----------------------------------------------------------
    @Query(value =
            "SELECT * FROM (" +
            "SELECT " +
                    "group_concat(cast(a.ext_id as char(100))) as extIdLists" +
                    ", group_concat(cast(a.id_backup as char)) as loanRequestIdLists" +
                    ", group_concat(a.request_no) as loanRequestNoLists" +
                    ", vea.employee_no as  employeeNo" +
                    ", cast(SUM(lel.monthly_installment) as char(100)) as totalMonthlyInstallment" +
                //     ", cast(:paymentDate as date) as paymentDate" +
                    ", DATE_ADD(cast(lel.go_live_date as date), INTERVAL lel.tenor MONTH) as paymentDate" +
                    ", cast(:requestDate as date) as requestDate" +
                    ", group_concat(llc.name) as loanCategoryLists" +
                    ",CONCAT_WS(' ke ',lff.sync_element_name,(ROW_NUMBER() OVER (PARTITION BY employee_no,lff.sync_element_name ORDER BY employee_no,lff.sync_element_name))) as elementName  " +
                    ", 'lo_loan_request' as tableName " +
            "FROM " +
                    "lo_loan_request a " +
                    "JOIN lo_employee_loan lel ON lel.request_no = a.request_no and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' and is_confirmed = true and a.need_sync= true " +
                    "JOIN lo_loan_category llc on llc.id = a.loan_category_id " +

                    "JOIN lo_feedback_fleet lff ON lff.value_source = 'Monthly Installment MOP' AND current_date BETWEEN lff.start_date and lff.end_date and lff.loan_category_id = llc.id " +
                    "LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id " +
                    "JOIN vw_employee_assignment vea ON vea.employment_id = lel.employment_id " +
            "WHERE 1=1 " +
                    "and a.need_sync=TRUE AND d.status='Approved' " +
                    "and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' " +
                    "AND lel.tenor = 1 " +
                    "and lff.sync_element_name is not null " +
                    "and lff.sync_element_name != ' ' " +
                    "and lff.sync_element_name != 'notused' " +
                    "and (:dateFrom is null or :dateTo is null) or cast(lel.go_live_date as date) BETWEEN cast(:dateFrom as date)  AND cast(:dateTo as date) " +
            "group by vea.employee_no,lff.sync_element_name " +
            ") as raw_data " +
            "GROUP BY raw_data.employeeNo  having employeeNo is not null "
            ,nativeQuery = true)
    List<Map<String,Object>> findNeedSyncLoanTenor1MOP(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo,
                                                    @Param("requestDate") LocalDate requestDate
    );

    //-----------------------------------------------------------
    // MOP Tenor > 1
    //-----------------------------------------------------------
//     @Query(value =
//             "SELECT * FROM (" +
//             "SELECT " +
//                     "group_concat(cast(a.ext_id as char(100))) as extIdLists" +
//                     ", group_concat(cast(a.id_backup as char)) as loanRequestIdLists" +
//                     ", group_concat(a.request_no) as loanRequestNoLists" +
//                     ", vea.employee_no as  employeeNo" +
//                     ", cast(SUM(lel.monthly_installment) as char(100)) as totalMonthlyInstallment" +
//                     ", cast(:paymentDate as date) as paymentDate" +
//                     ", cast(:requestDate as date) as requestDate" +
//                     ", group_concat(llc.name) as loanCategoryLists" +
//                     ", CONCAT_WS(' ke ',lff.sync_element_name,(ROW_NUMBER() OVER (PARTITION BY employee_no,lff.sync_element_name ORDER BY employee_no,lff.sync_element_name))) as elementName  " +
//                     ", 'lo_loan_request' as tableName " +
//             "FROM " +
//                     "lo_loan_request a " +
//                     "JOIN lo_employee_loan lel ON lel.request_no = a.request_no and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' and is_confirmed = true and a.need_sync= true  " +
//                     "JOIN lo_loan_category llc on llc.id = a.loan_category_id " +

//                     "JOIN lo_feedback_fleet lff ON lff.value_source = 'Monthly Installment MOP' AND current_date BETWEEN lff.start_date and lff.end_date and lff.loan_category_id = llc.id " +
//                     "LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id " +
//                     "JOIN vw_employee_assignment vea ON vea.employment_id = lel.employment_id " +
//             "WHERE 1=1 " +
//                     "and a.need_sync=TRUE AND d.status='Approved' " +
//                     "and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' " +
//                     "AND lel.tenor > 1 " +
//                     "and lff.sync_element_name is not null " +
//                     "and lff.sync_element_name != ' ' " +
//                     "and lff.sync_element_name != 'notused' " +
//             "group by vea.employee_no,lff.sync_element_name " +
//             ") as raw_data " +
//              "GROUP BY raw_data.employeeNo  having employeeNo is not null "
//             ,nativeQuery = true)
//     List<Map<String,Object>> findNeedSyncLoanTenorMoreThan1MOP(@Param("paymentDate") LocalDate paymentDate,
//                                                             @Param("requestDate") LocalDate requestDate
//     );

//    //-----------------------------------------------------------
//    // Allowance Bracket need sync
//    //-----------------------------------------------------------
//    @Query(value =
//            "with employee_loan as ( " +
//                    "select lel.employment_id,lel.loan_category_id,MIN(pep.total_score) as score " +
//                    "from lo_employee_loan lel " +
//                    "left join pa_employee_performance pep ON pep.employment_id = lel.employment_id AND CURRENT_DATE BETWEEN pep.start_date and pep.end_date " +
//                    "WHERE lel.status = 'In Progress' " +
//                    "GROUP BY employment_id,loan_category_id  " +
//                    ") " +
//                    "select  ''" +
////                    "--               group_concat(cast(a.ext_id as char(100))) as extIdLists " +
////                    "--                     , group_concat(a.id) as loanRequestIdLists " +
////                    "--                     , group_concat(a.request_no) as loanRequestNoLists " +
//                    "                    , vea.employee_no as  employeeNo " +
//                    "                    , cast(SUM(u.value) as char(100)) as totalMonthlyInstallment " +
//                    "                     , cast(:paymentDate as date) as paymentDate " +
//                    "                     , cast(:requestDate as date) as requestDate " +
//                    "                    , group_concat(llc.name) as loanCategoryLists " +
//                    "                    , u.sync_element_name as elementName  " +
//                    ", 'lo_sync_loan_request_dtl' as tableName " +
//                    "            FROM  " +
//                    "                lo_allowance_bracket u  " +
//                    "                LEFT JOIN pa_rating par ON u.rating_id = par.id  " +
//                    "               " +
//                    "        JOIN lo_loan_category llc on llc.id = u.loan_category_id AND  u.element_name IS NOT NULL AND  u.value IS NULL " +
//                    "         " +
//                    "        join employee_loan el ON el.loan_category_id = u.loan_category_id " +
//                    "        join vw_employee_assignment vea ON el.employment_id = vea.employment_id " +
//                    "        join hr_job_title hjt ON hjt.id = vea.job_title_id " +
//                    "        join hr_job_family hjf ON hjt.job_family_id = hjf.id " +
//                    "         " +
//                    "        JOIN hr_payroll_monthly_detail md ON md.element_name = u.element_name AND md.total_element_value IS NOT NULL  " +
//                    "                JOIN hr_payroll_monthly_header mh ON mh.id = md.monthly_header_id  " +
//                    "                            AND mh.employment_id = el.employment_id  " +
//                    "                            AND (u.position_id is null or mh.position_id = u.position_id) " +
//                    "                            AND (u.organization_id is null or mh.organization_id = u.organization_id) " +
//                    "                            AND (u.company_office_id is null or mh.company_office_id = u.company_office_id) " +
//                    "                            AND YEAR (mh.payment_start_date) = YEAR(current_date - interval 1 month) " +
//                    "                            AND MONTH (mh.payment_start_date) = MONTH(current_date - interval 1 month) " +
//                    "            WHERE 1=1  " +
//                    "                AND  u.element_name IS NOT NULL  " +
//                    "        AND  u.value IS NULL " +
//                    "        AND CURRENT_DATE BETWEEN CAST(u.start_date AS DATE) AND CAST(u.end_date AS DATE) " +
//                    " " +
//                    "                AND (u.organization_id is null or u.organization_id = vea.organization_id )  " +
//                    "        AND (u.organization_group_id is null or u.organization_group_id = vea.organization_group_id )  " +
//                    "        AND (u.position_id is null or u.position_id = vea.position_id )  " +
//                    "        AND (u.position_level_id is null or u.position_level_id = vea.position_level_id )  " +
//                    "        AND (u.job_title_id is null or u.job_title_id =vea.job_title_id ) " +
//                    "        AND (u.job_family_id is null or u.job_family_id = hjf.id ) " +
//                    "        AND (u.work_location_type is null or u.work_location_type =vea.work_location_type ) " +
//                    "        AND (u.company_office_id is null or u.company_office_id = vea.company_office_id ) " +
//                    "        AND (u.grade_id is null or u.grade_id = vea.grade_id ) " +
//                    "        AND (u.employment_status is null or u.employment_status = vea.employee_status ) " +
//                    "        AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) " +
//                    "     GROUP BY vea.employee_no,u.sync_element_name  having employeeNo is not null "
//            ,nativeQuery = true)
//    List<Map<String,Object>> findAllowanceBracketElementNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
//                                                              @Param("requestDate") LocalDate requestDate
//    );
//
//    @Query(value =
//            "with employee_loan as ( " +
//                    "select lel.employment_id,lel.loan_category_id,MIN(pep.total_score) as score " +
//                    "from lo_employee_loan lel " +
//                    "left join pa_employee_performance pep ON pep.employment_id = lel.employment_id AND CURRENT_DATE BETWEEN pep.start_date and pep.end_date " +
//                    "WHERE lel.status = 'In Progress' " +
//                    "GROUP BY employment_id,loan_category_id  " +
//                    ") " +
//                    "select  ''" +
////                    "--               group_concat(cast(a.ext_id as char(100))) as extIdLists " +
////                    "--                     , group_concat(a.id) as loanRequestIdLists " +
////                    "--                     , group_concat(a.request_no) as loanRequestNoLists " +
//                    "                    , vea.employee_no as  employeeNo " +
//                    "                    , cast(SUM(u.value) as char(100)) as totalMonthlyInstallment " +
//                    "                     , cast(:paymentDate as date) as paymentDate " +
//                    "                     , cast(:requestDate as date) as requestDate " +
//                    "                    , group_concat(llc.name) as loanCategoryLists " +
//                    "                    , u.sync_element_name as elementName  " +
//                    ", 'lo_sync_loan_request_dtl' as tableName " +
//                    "            FROM  " +
//                    "                lo_allowance_bracket u  " +
//                    "                LEFT JOIN pa_rating par ON u.rating_id = par.id  " +
//                    "               " +
//                    "        JOIN lo_loan_category llc on llc.id = u.loan_category_id AND  u.element_name IS NOT NULL AND  u.value IS NULL " +
//                    "         " +
//                    "        join employee_loan el ON el.loan_category_id = u.loan_category_id " +
//                    "        join vw_employee_assignment vea ON el.employment_id = vea.employment_id " +
//                    "        join hr_job_title hjt ON hjt.id = vea.job_title_id " +
//                    "        join hr_job_family hjf ON hjt.job_family_id = hjf.id " +
//                    "         " +
//                    "            WHERE 1=1  " +
//                    "                AND  u.element_name IS NULL  " +
//                    "        AND  u.value IS NOT NULL " +
//                    "        AND CURRENT_DATE BETWEEN CAST(u.start_date AS DATE) AND CAST(u.end_date AS DATE) " +
//                    " " +
//                    "                AND (u.organization_id is null or u.organization_id = vea.organization_id )  " +
//                    "        AND (u.organization_group_id is null or u.organization_group_id = vea.organization_group_id )  " +
//                    "        AND (u.position_id is null or u.position_id = vea.position_id )  " +
//                    "        AND (u.position_level_id is null or u.position_level_id = vea.position_level_id )  " +
//                    "        AND (u.job_title_id is null or u.job_title_id =vea.job_title_id ) " +
//                    "        AND (u.job_family_id is null or u.job_family_id = hjf.id ) " +
//                    "        AND (u.work_location_type is null or u.work_location_type =vea.work_location_type ) " +
//                    "        AND (u.company_office_id is null or u.company_office_id = vea.company_office_id ) " +
//                    "        AND (u.grade_id is null or u.grade_id = vea.grade_id ) " +
//                    "        AND (u.employment_status is null or u.employment_status = vea.employee_status ) " +
//                    "        AND (par.id is null or el.score BETWEEN par.range_from AND par.range_to ) " +
//                    "     GROUP BY vea.employee_no,u.sync_element_name  having employeeNo is not null "
//            ,nativeQuery = true)
//    List<Map<String,Object>> findAllowanceBracketValueNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
//                                                                           @Param("requestDate") LocalDate requestDate
//    );


}
