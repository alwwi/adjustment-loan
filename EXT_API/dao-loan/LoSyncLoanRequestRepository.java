package com.phincon.external.app.dao.loan;


import com.phincon.external.app.model.loan.LoSyncLoanRequest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoSyncLoanRequestRepository extends PagingAndSortingRepository<LoSyncLoanRequest,Long> {

}
