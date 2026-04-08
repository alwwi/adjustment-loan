package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanMyLoanDetailDTO;
import com.phincon.talents.app.dto.loan.LoanMyLoanListDTO;
import com.phincon.talents.app.model.loan.LoCriteria;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoEmployeeLoanRepository  extends CrudRepository<LoEmployeeLoan,String>, PagingAndSortingRepository<LoEmployeeLoan,String> {
    Optional<LoEmployeeLoan> findByRequestNo(String requestNo);
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanMyLoanListDTO(" +
                "u.id" +
                ",u.requestNo" +
                ",u.requestDate" +
                ",u.employmentId" +
                ",u.loanCategoryId" +
                ",u.loanTypeId" +
                ",u.brandId" +
                ",u.loanDate" +
                ",u.amount" +
                ",u.tenor" +
                ",u.status" +
                ",u.remaining" +
                ",llc.name" +
                ",llt.name" +
                ",u.goLiveDate" +
                ",u.downPayment" +
                ",u.monthlyInstallment" +
                ",u.paidAmount" +
                ",u.leftInssurance" +
                ",u.lastPaidDate" +
                ",u.transferDate" +
                ",u.maturityDate" +
            ") " +
            "FROM " +
                "LoEmployeeLoan u " +
                "LEFT JOIN LoLoanType llt ON llt.id = u.loanTypeId " +
                "LEFT JOIN LoLoanCategory llc ON llc.id = u.loanCategoryId " +
            "WHERE 1=1 " +
                "AND (CAST(:employmentId as string) is null or u.employmentId = :employmentId ) " +
                "AND (CAST(:status as string) is null or u.status = :status ) " +
                "AND (CAST(:requestNo as string) is null or u.requestNo = cast(:requestNo as string) ) " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or CAST(:startDate as string) is null  or u.requestDate BETWEEN cast(:startDate as date) AND cast(:endDate as date) ) " +
                //detail by id
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) " +
                "AND (CAST(:loanCategoryId as string) is null or u.loanCategoryId = cast(:loanCategoryId as string) ) "
    )
//            repository.listData(requestNo,startDateUsed,endDateUsed,loanTypeIdUsed,loanCategoryIdUsed,pageable);
    public Page<LoanMyLoanListDTO> listData(
            @Param("employmentId") String employmentId
            ,@Param("requestNo") String requestNo
            ,@Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanTypeId") String loanTypeId
            , @Param("loanCategoryId") String loanCategoryId
            , @Param("status") String status
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanMyLoanDetailDTO(" +
                "u.id" +
                ",u.requestNo" +
                ",u.requestDate" +
                ",u.employmentId" +
                ",u.loanCategoryId" +
                ",u.loanTypeId" +
                ",u.brandId" +
                ",u.loanDate" +
                ",u.amount" +
                ",u.tenor" +
                ",u.status" +
                ",u.remaining" +
                ",llc.name" +
                ",llt.name" +
                ",u.goLiveDate" +
                ",u.downPayment" +
                ",u.monthlyInstallment" +
                ",u.paidAmount" +
                ",u.leftInssurance" +
                ",u.lastPaidDate" +
                ",llr.tenor" +
                ",u.estarAggrementNo" +
                ",u.estarContractStatus" +
                ",u.estarDownPayment" +
                ",u.estarInsNo" +
                ",u.estarInstallmentAmount" +
                ",u.estarLastPaidDate" +
                ",u.estarName" +
                ",u.estarOtr" +
                ",u.estarOutstandingAr" +
                ",u.estarOutstandingInsurance" +
                ",u.estarPaidAmount" +
                ",u.estarPrepaidAmount" +
                ",u.estarTenor" +
                ",llt.isEarlySettlement" +
                ",u.transferDate" +
                ",u.maturityDate" +
                ",u.isConfirmed" +
            ") " +
            "FROM " +
                "LoEmployeeLoan u " +
                "LEFT JOIN LoLoanType llt ON llt.id = u.loanTypeId " +
                "LEFT JOIN LoLoanCategory llc ON llc.id = u.loanCategoryId " +
                "LEFT JOIN LoLoanRequest llr ON llr.requestNo = u.requestNo " +
            "WHERE 1=1 " +
                "AND (CAST(:employmentId as string) is null or u.employmentId = :employmentId ) " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanMyLoanDetailDTO> getData(
            @Param("id") String id,
            @Param("employmentId") String employmentId
    );


    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanMyLoanDetailDTO(" +
                "u.id" +
                ",u.requestNo" +
                ",u.requestDate" +
                ",u.employmentId" +
                ",u.loanCategoryId" +
                ",u.loanTypeId" +
                ",u.brandId" +
                ",u.loanDate" +
                ",u.amount" +
                ",u.tenor" +
                ",u.status" +
                ",u.remaining" +
                ",llc.name" +
                ",llt.name" +
                ",u.goLiveDate" +
                ",u.downPayment" +
                ",u.monthlyInstallment" +
                ",u.paidAmount" +
                ",u.leftInssurance" +
                ",u.lastPaidDate" +
                ",llr.tenor" +
                ",u.estarAggrementNo" +
                ",u.estarContractStatus" +
                ",u.estarDownPayment" +
                ",u.estarInsNo" +
                ",u.estarInstallmentAmount" +
                ",u.estarLastPaidDate" +
                ",u.estarName" +
                ",u.estarOtr" +
                ",u.estarOutstandingAr" +
                ",u.estarOutstandingInsurance" +
                ",u.estarPaidAmount" +
                ",u.estarPrepaidAmount" +
                ",u.estarTenor" +
                ",llt.isEarlySettlement" +
                ",u.transferDate" +
                ",u.maturityDate" +
                ",u.isConfirmed" +
            ") " +
            "FROM " +
                "LoEmployeeLoan u " +
                "LEFT JOIN LoLoanType llt ON llt.id = u.loanTypeId " +
                "LEFT JOIN LoLoanCategory llc ON llc.id = u.loanCategoryId " +
                "LEFT JOIN LoLoanRequest llr ON llr.requestNo = u.requestNo " +
            "WHERE 1=1 " +
                "AND (u.employmentId = cast(:employmentId as string) ) " +
                "AND (u.status IN ('" + LoEmployeeLoan.STATUS_IN_PROGRESS + "') )"
    )
    public List<LoanMyLoanDetailDTO> getInProgressLoan(@Param("employmentId") String employmentId);

    @Query("select " +
            "SUM(u.amount / u.tenor) " +
            "FROM " +
            "LoEmployeeLoan u " +
            "WHERE 1=1 " +
            "AND u.status = 'In Progress' " +
            "AND (CAST(:employmentId as string) is null or u.employmentId = cast(:employmentId as string) ) " +
            "GROUP BY u.employmentId "
    )
    public Double getTotalNotPaidLoanPerTenor(@Param("employmentId") String employmentId);

    @Query("select " +
            "SUM(u.amount) " +
            "FROM " +
            "LoEmployeeLoan u " +
            "WHERE 1=1 " +
            "AND u.status = 'In Progress' " +
            "AND (CAST(:employmentId as string) is null or u.employmentId = cast(:employmentId as string) ) " +
            "GROUP BY u.employmentId "
    )
    public Double getTotalNotPaidLoan(@Param("employmentId") String employmentId);

    @Query("select " +
            "pr " +
            "FROM " +
            "LoLoanRequest pr " +
            "left join LoEmployeeLoan lel ON pr.requestNo = lel.requestNo " +
            //must only get data for non simulation, because non simulation will be auto update by estar, not hceazy
            "join LoLoanCategory llc ON pr.loanCategoryId = llc.id " +
            "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('Approved') " +
            "WHERE 1=1 " +
            "AND llc.isSimulation = false " +
            "AND da.status = 'Approved' " +
            "AND (lel.status = '" + LoEmployeeLoan.STATUS_IN_PROGRESS + "') " +
            "AND (:employmentId is null or pr.employmentId = :employmentId) "
    )
    public List<LoLoanRequest> findNeedUpdateInsertDataEstar(
            @Param("employmentId") String employmentId
    );

    @Query("select " +
            "pr " +
            "FROM " +
            "LoLoanRequest pr " +
            //must only get data for simulation, because non simulation will be auto update by estar, not hceazy
            "join LoLoanCategory llc ON pr.loanCategoryId = llc.id " +
            "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('Approved') " +
            "WHERE 1=1 " +
            "AND llc.isSimulation = true " +
            "AND da.status = 'Approved' " +
            "AND (:employmentId is null or pr.employmentId = :employmentId) "
    )
    public List<LoLoanRequest> findNeedUpdateInsertDataHceazy(
            @Param("employmentId") Long employmentId
    );

    @Query("select " +
                "SUM(u.monthlyInstallment) " +
            "FROM " +
                "LoEmployeeLoan u " +
            "WHERE 1=1 " +
            "AND (CAST(:status as string) is null or u.status = cast(:status as string) ) " +
            "AND u.employmentId = :employmentId "
    )
    public Optional<Double> findByStatus(
            @Param("status") String status,
            @Param("employmentId") String employmentId
    );
}
