package com.phincon.external.app.dao.loan;


import com.phincon.external.app.dto.loan.estar.EstarSubmitLoanEmployeeDataDetailDTO;
import com.phincon.external.app.model.loan.LoEmployeeLoan;
import com.phincon.external.app.model.loan.LoLoanRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoLoanRequestRepository extends PagingAndSortingRepository<LoLoanRequest,String> {

    @Query("SELECT new com.phincon.external.app.dto.loan.estar.EstarSubmitLoanEmployeeDataDetailDTO(empmnt.name,emp.nircNo,llr.requestNo) " +
                "FROM LoLoanRequest llr " +
                "JOIN Employment empmnt ON llr.employmentId = empmnt.id " +
                "JOIN Employee emp ON emp.id = empmnt.employeeId " +
            "WHERE 1=1 " +
                "and llr.needSyncEstar = TRUE " +
                "and ((:dateFrom is not null and :dateTo is not null) or cast(llr.requestDate as date) BETWEEN cast(:dateFrom as date) AND cast(:dateTo as date)) " +
            "order by llr.requestDate asc ")
    public List<EstarSubmitLoanEmployeeDataDetailDTO> findNeedSync(@RequestParam("dateFrom") LocalDate dateFrom, @RequestParam("dateTo") LocalDate dateTo);

    @Query("select " +
                "pr " +
            "FROM " +
                "LoLoanRequest pr " +
                "left join LoEmployeeLoan lel ON pr.requestNo = lel.requestNo " +
                //must only get data for non simulation, because non simulation will be auto update by estar, not hceazy
                "join LoLoanCategory llc ON pr.loanCategoryId = llc.id " +
                "join WFDataApproval da on pr.id = da.objectRefId and da.module='Loan' and da.status IN ('Approved') " +
            "WHERE 1=1 " +
                "AND llc.isSimulation = false " +
                "AND da.status = 'Approved' " +
                "AND (lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "') " +
                "AND (:employmentId is null or pr.employmentId = :employmentId) "
    )
    public List<LoLoanRequest> findNeedSyncForGetData(@RequestParam("employmentId") String employmentId);

    @Query("select " +
                "pr " +
            "FROM " +
                "LoLoanRequest pr " +

            "WHERE 1=1 " +
                "AND (pr.requestNo = :requestNo) "
    )
    public Optional<LoLoanRequest> findByRequestNo(@RequestParam("requestNo") String requestNo);
}
