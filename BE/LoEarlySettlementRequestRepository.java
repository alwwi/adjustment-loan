package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoCriteria;
import com.phincon.talents.app.model.loan.LoEarlySettlementRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LoEarlySettlementRequestRepository   extends CrudRepository<LoEarlySettlementRequest,String>, PagingAndSortingRepository<LoEarlySettlementRequest,String> {

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementRequestNeedApprovalDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath) " +
            ") from " +
                "LoEarlySettlementRequest ar " +
                "join LoLoanRequest llr ON ar.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +

                "join DataApproval da on ar.id = da.objectRef and da.module='Early Settlement'  " +
                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where " +
                "current_date() <= coalesce(da.expiredDate,current_date()) " +
                "and :serverPath = :serverPath " +
                "and da.status = :status " +
                "and da.currentAssignApprover like :approverEmployeeId " +
                "and (Date(ar.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (vea.name like :ppl or vea.employeeNo like :ppl or ar.requestNo like :ppl)" +
                "and (:loanTypeId is null or llr.loanTypeId = :loanTypeId )"
    )
    Page<LoanEarlySettlementRequestNeedApprovalDTO> findNeedApprovalBy(@Param("approverEmployeeId") String approverEmployeeId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, @Param("ppl") String ppl,@Param("loanTypeId") String loanTypeId, Pageable pageable);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),llc,llt,lel, :secret) " +
            "from " +
                "LoEarlySettlementRequest ar " +
                "join LoLoanRequest llr ON ar.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +
                "join LoLoanCategory llc ON llc.id = llr.loanCategoryId " +
                "join LoLoanType llt ON llt.id = llr.loanTypeId " +

                "join DataApproval da on ar.id = da.objectRef and da.module='Early Settlement' and da.isByAdmin=false " +
                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  " +
                "llr.employmentId = :employmentId " +
                "and :serverPath = :serverPath " +
                "and (da.status = :status or :status is null ) " +
                "and (Date(ar.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (:requestNo is null or ar.requestNo like :requestNo)" +
                "and (:loanTypeId is null or llr.loanTypeId like :loanTypeId)" +
                "and (:loanCategoryId is null or llr.loanCategoryId like :loanCategoryId)"
    )
    Page<LoanEarlySettlementMyRequestDTO> findByEmployeeAndStatus(@Param("employmentId") String employmentId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, Pageable pageable, @Param("requestNo") String requestNo, @Param("loanTypeId") String loanTypeId, @Param("loanCategoryId") String loanCategoryId, @Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),llc,llt,lel, :secret) " +
            "from " +
                "LoEarlySettlementRequest ar " +
                "join LoLoanRequest llr ON ar.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +
                "join LoLoanCategory llc ON llc.id = llr.loanCategoryId " +
                "join LoLoanType llt ON llt.id = llr.loanTypeId " +

                "join DataApproval da on ar.id = da.objectRef and da.module='Early Settlement' and da.isByAdmin=false " +
                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  " +
                "llr.employmentId = :employmentId " +
                "and :serverPath = :serverPath " +
                "and da.id = :dataApprovalId"
    )
    Optional<LoanEarlySettlementMyRequestDTO> findByEmployeeAndId(@Param("employmentId") String employmentId, @Param("dataApprovalId") String dataApprovalId, @Param("serverPath") String serverPath, @Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),llc,llt,lel, :secret) " +
            "from " +
                "LoEarlySettlementRequest ar " +
                "join LoLoanRequest llr ON ar.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +
                "join LoLoanCategory llc ON llc.id = llr.loanCategoryId " +
                "join LoLoanType llt ON llt.id = llr.loanTypeId " +

            "join DataApproval da on ar.id = da.objectRef and da.module='Early Settlement' and da.isByAdmin=false " +
                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  1=1 " +
                "and :serverPath = :serverPath " +
                "and da.id = :dataApprovalId"
    )
    Optional<LoanEarlySettlementMyRequestDTO> findByEmployeeAndIdAdmin(@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),llc,llt,lel, :secret) " +
            "from " +
                "LoEarlySettlementRequest ar " +
                "join LoLoanRequest llr ON ar.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +
                "join LoLoanCategory llc ON llc.id = llr.loanCategoryId " +
                "join LoLoanType llt ON llt.id = llr.loanTypeId " +

                "join DataApproval da on ar.id = da.objectRef and da.module='Early Settlement'  " +
                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId " +
            " where  " +
                ":serverPath = :serverPath " +
                "and da.id = :dataApprovalId " +
                "and da.currentAssignApprover like :approverEmployeeId"
    )
    Optional<LoanEarlySettlementMyRequestDTO> findNeedApprovalById(@Param("approverEmployeeId") String approverEmployeeId,@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,@Param("secret") String secret);


    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp, :secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoEarlySettlementRequest br  on br.id = da.objectRef and da.module='Early Settlement'  " +
                "join LoLoanRequest llr ON br.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +

                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and (dad.action = :status or (:status is null and dad.action in ('Approved','Rejected') ) ) " +
                "and dad.actionByEmployeeId = :approverEmployeeId " +
                "and (Date(br.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (vea.name like :ppl or vea.employeeNo like :ppl or br.requestNo like :ppl)"
    )
    Page<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApproval(@Param("approverEmployeeId") String approverEmployeeId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, @Param("ppl") String ppl, Pageable pageable,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp, :secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoEarlySettlementRequest br  on br.id = da.objectRef and da.module='Early Settlement'  " +
                "join LoLoanRequest llr ON br.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +

                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                 "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and dad.actionByEmployeeId = :approverEmployeeId  " +
                "and dad.id = :dataApprovalDetailId"
    )
    Optional<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApprovalByIdAndEmployee(@Param("approverEmployeeId") String approverEmployeeId,@Param("dataApprovalDetailId") String dataApprovalDetailId,@Param("serverPath") String serverPath,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp, :secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoEarlySettlementRequest br  on br.id = da.objectRef and da.module='Early Settlement'  " +
                "join LoLoanRequest llr ON br.loanRequestNo = llr.requestNo " +

                //make sure its already in lo_employee_loan (lo_employee_loan filled from estar api)
                "join LoEmployeeLoan lel ON lel.requestNo = llr.requestNo " +

                "join VwEmpAssignment vea on llr.employmentId = vea.employmentId " +
                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and dad.action is not null " +
                "and da.id = :dataApprovalId   "
    )
    List<LoanEarlySettlementRequestHistoryApprovalDTO> findHistoryApprovalByDataApprovalId(@Param("dataApprovalId") String dataApprovalId, @Param("serverPath") String serverPath, Sort sort,@Param("secret") String secret);


    @Query("select count(1) " +
            "from " +
                "LoEarlySettlementRequest pr " +
                "join LoLoanRequest llr ON pr.loanRequestNo = llr.requestNo " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Early Settlement'  " +
            "where  " +
                "llr.employmentId = :employmentId  " +
                "AND (da.status='In Progress' ) "
    )
    Integer countPendingAndNeedSync(@Param("employmentId") String employmentId);

    @Query("select pr " +
            "from " +
                "LoEarlySettlementRequest pr " +
                "join LoLoanRequest llr ON pr.loanRequestNo = llr.requestNo " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Early Settlement'  " +
            "where  " +
                "llr.id = :loanRequestId  " +
                "AND (da.status='Approved' ) "
    )
    Optional<LoEarlySettlementRequest> findApprovedEarlySettlementByLoanRequestId(@Param("loanRequestId") String loanRequestId);

    @Query("select new com.phincon.talents.app.dto.loan.LoanEarlySettlementRemainingDTO(" +
                "u.requestNo" +
            "   ,u.remaining" +
            ")" +
            "from " +
                "LoEmployeeLoan u " +
            "where  " +
                "u.requestNo = :requestNo " +
                "and u.employmentId = :employmentId "
    )
    Optional<LoanEarlySettlementRemainingDTO> checkRemainingAmount(
            @Param("employmentId") String employmentId
            ,@Param("requestNo") String requestNo

    );
}
