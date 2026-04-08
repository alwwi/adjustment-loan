package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanRequestDetailListDTO;
import com.phincon.talents.app.model.loan.LoLoanRequestDtl;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoLoanRequestDtlRepository  extends CrudRepository<LoLoanRequestDtl,String>, PagingAndSortingRepository<LoLoanRequestDtl,String> {

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRequestDetailListDTO(" +
                "u.id" +
                ",u.feedbackFleetId" +
                ",u.loanRequestId" +
                ",u.value" +
            ") " +
            "FROM " +
              "LoLoanRequestDtl u " +
            "WHERE 1=1 " +
                "AND (CAST(:loanRequestId as string) is null or u.loanRequestId = :loanRequestId ) "
    )
    public List<LoanRequestDetailListDTO> findByLoanRequestId(
            @Param("loanRequestId") String loanRequestId
    );

}
