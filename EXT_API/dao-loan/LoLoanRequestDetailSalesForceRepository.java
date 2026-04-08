package com.phincon.external.app.dao.loan;

import com.phincon.external.app.dao.SalesforceRepository;
import com.phincon.external.app.model.loan.LoEmployeeLoan;
import com.phincon.external.app.model.loan.LoLoanRequestDtl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface LoLoanRequestDetailSalesForceRepository extends SalesforceRepository<LoLoanRequestDtl, String> {

    //-----------------------------------------------------------
    // Additional need sync
    //-----------------------------------------------------------
    @Query(value =
            "SELECT " +
                    "group_concat(cast(llrd.ext_id as char(100))) as extIdLists" +
                    ", group_concat(llrd.id_backup) as loanRequestIdLists" +
                    ", group_concat(llrd.id) as loanRequestNoLists" +
                    ", vea.employee_no as  employeeNo" +
                    ", cast(SUM(llrd.value) as char(100)) as totalMonthlyInstallment" +
                    ", cast(:paymentDate as date) as paymentDate" +
                    ", cast(:requestDate as date) as requestDate" +
                    ", group_concat(llc.name) as loanCategoryLists" +
                    ", lff.sync_element_name as elementName " +
                    ", 'lo_loan_request_dtl' as tableName " +
            "FROM " +
                    "lo_loan_request a " +
                    "JOIN lo_employee_loan lel ON lel.request_no = a.request_no and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' and is_confirmed = true  " + //and a.need_sync=TRUE
                    "JOIN lo_loan_category llc on llc.id = a.loan_category_id " +
                    "LEFT JOIN wf_data_approval d on d.module='Loan' and d.object_ref_id=a.id " +
                    "JOIN vw_employee_assignment vea ON vea.employment_id = lel.employment_id " +

                    "JOIN lo_loan_request_dtl llrd ON llrd.loan_request_id = a.id AND (llrd.need_sync is null or llrd.need_sync = TRUE) " +
                    "JOIN lo_feedback_fleet lff ON llrd.feedback_fleet_id = lff.id AND (lff.value_source is null or lff.value_source Not In ('Monthly Installment','Monthly Installment MOP')) AND current_date BETWEEN lff.start_date and lff.end_date and lff.loan_category_id = llc.id  " +

            "WHERE 1=1 " +
                    "and (llrd.need_sync is null or llrd.need_sync = TRUE) AND d.status='Approved' " + //a.need_sync=TRUE AND
                    "and lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "' " +
                    "and llrd.value is not null " +
                    "and llrd.value > 0 " +
                    "and lff.sync_element_name is not null " +
                    "and lff.sync_element_name != ' ' " +
                    "and lff.sync_element_name != 'notused' " +
            "GROUP BY " +
                    "vea.employee_no,lff.sync_element_name " +
            "HAVING " +
                    "employeeNo is not null " +
            "ORDER BY " +
                    "employee_no,lel.go_live_date,lff.sync_element_name "

            ,nativeQuery = true)
    List<Map<String,Object>> findAdditionalNeedSyncLoanByDate(@Param("paymentDate") LocalDate paymentDate,
                                                              @Param("requestDate") LocalDate requestDate
    );

}
