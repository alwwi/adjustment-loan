//package com.phincon.talents.app.repository;
//
//import com.phincon.talents.app.dto.loan.LoanCategoryDetailDTO;
//import com.phincon.talents.app.dto.loan.LoanCategoryListDTO;
//import com.phincon.talents.app.dto.loan.LoanDsrComponentDetailDTO;
//import com.phincon.talents.app.dto.loan.LoanDsrComponentListDTO;
//import com.phincon.talents.app.model.loan.LoLoanDsrComponent;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//public interface LoLoanDsrComponentRepository  extends PagingAndSortingRepository<LoLoanDsrComponent,Long> {
//    @Query("select " +
//            "new com.phincon.talents.app.dto.loan.LoanDsrComponentListDTO(" +
//                "u.id" +
//                ",u.startDate" +
//                ",u.endDate" +
//                ",u.loanTypeId" +
//                ",u.elementName" +
//            ") " +
//            "FROM " +
//                "LoLoanDsrComponent u " +
//            "WHERE 1=1 " +
//                //check overlap date
//                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
//                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
//                //detail by id
//                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as long) ) "
//    )
//    public Page<LoanDsrComponentListDTO> listData(
//            @Param("startDate") LocalDate startDate
//            , @Param("endDate") LocalDate endDate
//            , @Param("loanTypeId") Long loanTypeId
//            , Pageable pageable
//    );
//
//    @Query("select " +
//            "new com.phincon.talents.app.dto.loan.LoanDsrComponentDetailDTO(" +
//                "u.id" +
//                ",u.startDate" +
//                ",u.endDate" +
//                ",u.loanTypeId" +
//                ",u.elementName" +
//            ") " +
//            "FROM " +
//                "LoLoanDsrComponent u " +
//            "WHERE 1=1 " +
//                "AND (CAST(:id as string) is null or u.id = cast(:id as long) ) "
//    )
//    public Optional<LoanDsrComponentDetailDTO> getData(@Param("id") String id);
//
//    @Query("select " +
//                "SUM(md.totalElementValue * (llt.dsrPercentage / 100)) " +
//            "FROM " +
//                "LoLoanType llt " +
//                "JOIN LoLoanDsrComponent lldc ON (CAST(:loanTypeId as string) is null or llt.id = cast(:loanTypeId as long) ) AND llt.id = lldc.loanTypeId AND cast(:effectiveDate as date) BETWEEN lldc.startDate AND lldc.endDate " +
//
//                //get only the plafond bracket that have elementName
//                "JOIN PayrollMonthlyDetail md ON md.elementName = lldc.elementName AND md.totalElementValue IS NOT NULL " +
//                "JOIN PayrollMonthlyHeader mh ON mh.id = md.monthlyHeaderId " +
//                                "AND mh.employmentId = :employmentId " +
//                                "AND mh.positionId = :positionId " +
//                                "AND mh.organizationId = :organizationId " +
//                                "AND mh.companyOfficeId = :companyOfficeId " +
//                                "AND YEAR (mh.paymentStartDate) = cast(:year as integer) " +
//                                "AND MONTH (mh.paymentStartDate) = cast(:month as integer) " +
//            "WHERE 1=1 " +
//                "AND (CAST(:loanTypeId as string) is null or llt.id = cast(:loanTypeId as long) ) " +
//            "GROUP BY llt.id "
//    )
//    public Double getTotalDsrForLoanRequest(@Param("loanTypeId") String loanTypeId
//            ,@Param("effectiveDate") LocalDate effectiveDate
//
//            ,@Param("employmentId") long employmentId
//            ,@Param("positionId") long positionId
//            ,@Param("organizationId") long organizationId
//            ,@Param("companyOfficeId") long companyOfficeId
//
//            ,@Param("year") Integer year
//            ,@Param("month") Integer month
//    );
//
//
//}
