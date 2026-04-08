package com.phincon.external.app.dao.loan;

import com.phincon.external.app.model.loan.LoLoanCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LoLoanCategoryRepository   extends PagingAndSortingRepository<LoLoanCategory,String> {

}
