package com.phincon.external.app.dao.loan;

import com.phincon.external.app.model.loan.LoEmployeeLoan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoEmployeeLoanRepository  extends PagingAndSortingRepository<LoEmployeeLoan,String> {
    Optional<LoEmployeeLoan> findByRequestNo(String requestNo);

    @Query(value = "select llr.id as loanRequestId,lel.id as loanEmployeeId " +
            "from lo_employee_loan lel " +
            "join lo_loan_request llr ON llr.request_no = lel.request_no AND llr.need_sync = false " +
            "join lo_loan_category llc ON llr.loan_category_id = llc.id AND llc.is_simulation = true " +
            "where lel.employment_id = :employmentId", nativeQuery = true)
    List<Map<String,Object>> findByEmploymentId(String employmentId);

}
