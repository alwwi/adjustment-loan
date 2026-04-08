package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.LoanDisciplineDetailDTO;
import com.phincon.talents.app.dto.loan.LoanDisciplineListDTO;
import com.phincon.talents.app.model.loan.LoCriteria;
import com.phincon.talents.app.model.loan.LoDiscipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoDisciplineRepository  extends CrudRepository<LoDiscipline,String>, PagingAndSortingRepository<LoDiscipline,String> {

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanDisciplineListDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.disciplineId" +
                ",u.loanTypeId" +
                ",d.name" +
            ") " +
            "FROM " +
                "LoDiscipline u " +
                "JOIN Discipline d ON u.disciplineId = d.id " +
            "WHERE 1=1 " +
                //check overlap date
                "AND (CAST(:endDate as string) is null or u.startDate <= cast(:endDate as date) ) " +
                "AND (CAST(:startDate as string) is null or u.endDate >= cast(:startDate as date) ) " +
                //detail by id
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) " +
            "ORDER BY d.name "
    )
    public Page<LoanDisciplineListDTO> listData(
            @Param("startDate") LocalDate startDate
            , @Param("endDate") LocalDate endDate
            , @Param("loanTypeId") String loanTypeId
            , Pageable pageable
    );

    @Query("select " +
                "new com.phincon.talents.app.dto.loan.LoanDisciplineListDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.disciplineId" +
                ",u.loanTypeId" +
                ",d.name" +
                ") " +
            "FROM " +
                "LoDiscipline u " +
                "JOIN Discipline d ON u.disciplineId = d.id " +
                "JOIN EmployeeDiscipline ed ON ed.disciplineId = d.id AND ed.employmentId = :employmentId AND cast(:requestDate as date) BETWEEN ed.startDate AND ed.endDate " +
            "WHERE 1=1 " +
                "AND cast(:requestDate as date) BETWEEN u.startDate AND u.endDate " +
                //detail by id
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) " +
            "ORDER BY d.name "
    )
    public List<LoanDisciplineListDTO> checkLoanDiscipline(
            @Param("requestDate") LocalDate requestDate
            , @Param("loanTypeId") String loanTypeId
            , @Param("employmentId") String employmentId
    );

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanDisciplineDetailDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.disciplineId" +
                ",u.loanTypeId" +
                ",d.name" +
            ") " +
            "FROM " +
                "LoDiscipline u " +
                "JOIN Discipline d ON u.disciplineId = d.id " +
            "WHERE 1=1 " +
                "AND (CAST(:id as string) is null or u.id = cast(:id as string) ) "
    )
    public Optional<LoanDisciplineDetailDTO> getData(@Param("id") String id);

    @Query("select " +
            "new com.phincon.talents.app.dto.loan.LoanDisciplineDetailDTO(" +
                "u.id" +
                ",u.startDate" +
                ",u.endDate" +
                ",u.disciplineId" +
                ",u.loanTypeId" +
                ",d.name" +
            ") " +
            "FROM " +
                "LoDiscipline u " +
                "JOIN Discipline d ON u.disciplineId = d.id " +
            "WHERE 1=1 " +
                "AND (:exceptionId is null or u.id != :exceptionId) " +
                "AND (CAST(:loanTypeId as string) is null or u.loanTypeId = cast(:loanTypeId as string) ) " +
                "AND (CAST(:disciplineId as string) is null or u.disciplineId = cast(:disciplineId as string) ) " +
                "AND (" +
                        "cast(:startDate as date) <  cast(u.endDate as date) " +
                        "and cast(:endDate as date) >  cast(u.startDate as date) " +
                    ") "
    )
    public List<LoanDisciplineDetailDTO> checkSameDataAlreadyAdded(@Param("loanTypeId") String loanTypeId,
                                                                   @Param("startDate") LocalDateTime startDate,
                                                                   @Param("endDate") LocalDateTime endDate,
                                                                   @Param("disciplineId") String disciplineId,
                                                                   @Param("exceptionId") String exceptionId
                                                                       );


}
