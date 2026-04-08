package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanRatingModelMatrixDetailDTO;
import com.phincon.talents.app.dto.loan.LoanRatingModelMatrixListDTO;
import com.phincon.talents.app.model.loan.LoRatingModelMatrix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LoRatingModelMatrixRepository  extends CrudRepository<LoRatingModelMatrix,String>, PagingAndSortingRepository<LoRatingModelMatrix,String> {
    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelMatrixListDTO(" +
                "u.id" +
                ",u.loanRatingModelId" +
                ",u.paRatingId1" +
                ",u.paRatingId2" +
                ",u.result" +
                ",lrm.name" +
                ",pr1.name" +
                ",pr2.name" +
            ") " +
            "FROM " +
                "LoRatingModelMatrix u " +
                "JOIN LoRatingModel lrm ON u.loanRatingModelId = lrm.id " +
                "JOIN PARating pr1 ON u.paRatingId1 = pr1.id " +
                "JOIN PARating pr2 ON u.paRatingId2 = pr2.id " +
            "WHERE 1=1 " +
                "AND (CAST(:loanRatingModelId as string) is null or u.loanRatingModelId = cast(:loanRatingModelId as string) ) " +
                "AND (CAST(:paRatingId1 as string) is null or u.paRatingId1 = cast(:paRatingId1 as string) ) " +
                "AND (CAST(:paRatingId2 as string) is null or u.paRatingId2 = cast(:paRatingId2 as string) ) " +
                "AND (CAST(:result as string) is null or u.result = cast(:result as string) ) "
    )
    public Page<LoanRatingModelMatrixListDTO> listData(
            @Param("loanRatingModelId") String loanRatingModelId
            , @Param("paRatingId1") String paRatingId1
            , @Param("paRatingId2") String paRatingId2
            , @Param("result") String result
            , Pageable pageable
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelMatrixDetailDTO(" +
                "u.id" +
                ",u.loanRatingModelId" +
                ",u.paRatingId1" +
                ",u.paRatingId2" +
                ",u.result" +
                ",lrm.name" +
                ",pr1.name" +
                ",pr2.name" +
            ") " +
            "FROM " +
                "LoRatingModelMatrix u " +
                "JOIN LoRatingModel lrm ON u.loanRatingModelId = lrm.id " +
                "JOIN PARating pr1 ON u.paRatingId1 = pr1.id " +
                "JOIN PARating pr2 ON u.paRatingId2 = pr2.id " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanRatingModelMatrixDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanRatingModelMatrixDetailDTO(" +
                "u.id" +
                ",u.loanRatingModelId" +
                ",u.paRatingId1" +
                ",u.paRatingId2" +
                ",u.result" +
                ",lrm.name" +
                ",pr1.name" +
                ",pr2.name" +
            ") " +
            "FROM " +
                "LoRatingModelMatrix u " +
                "JOIN LoRatingModel lrm ON u.loanRatingModelId = lrm.id " +
                "JOIN PARating pr1 ON u.paRatingId1 = pr1.id " +
                "JOIN PARating pr2 ON u.paRatingId2 = pr2.id " +
            "WHERE 1=1 " +
                "AND u.loanRatingModelId = cast(:loanRatingModelId as string) "
    )
    public List<LoanRatingModelMatrixDetailDTO> findByLoanRatingModel(@Param("loanRatingModelId") String loanRatingModelId
    );


}
