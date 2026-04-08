//package com.phincon.talents.app.repository;
//
//import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface LoEarlySettlementRepository  extends CrudRepository<LoEarlySettlementRequest,String>, PagingAndSortingRepository<LoEarlySettlementRequest,String> {
//
//    @Query("SELECT u " +
//            "FROM LoEarlySettlementRequest u " +
//            "JOIN LoEmployeeLoan lel ON u.employeeLoanId = lel.id " +
//            "WHERE 1=1 " +
//            "AND u.employeeLoanId = :employeeLoanId " +
//            "AND u.status IN ('" + LoEarlySettlementRequest.STATUS_COMPLETED + "') ")
//    public List<LoEarlySettlementRequest> findAlreadyRequested(@Param("employeeLoanId") String employeeLoanId);
//
//    @Query("SELECT u " +
//            "FROM LoEarlySettlementRequest u " +
//            "JOIN LoEmployeeLoan lel ON u.employeeLoanId = lel.id " +
//            "WHERE 1=1 " +
//            "AND lel.requestNo = :requestNo " +
//            "AND u.status IN ('" + LoEarlySettlementRequest.STATUS_DATA_SUBMITTED_TO_ESTAR + "') ")
//    public LoEarlySettlementRequest findByRequestNoForSubmit(@Param("requestNo") String requestNo);
//
//}
