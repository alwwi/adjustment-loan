package com.phincon.talents.app.repository;

import com.phincon.talents.app.dto.FKPRequestDTO;
import com.phincon.talents.app.dto.FKPRequestHistoryApprovalDTO;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.DataApproval;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LoLoanRequestRepository extends CrudRepository<LoLoanRequest,String>, PagingAndSortingRepository<LoLoanRequest,String> {
    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestNeedApprovalDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath)" +
                ",llc" +
                ", :secret" +
            ") from " +
                "LoLoanRequest ar " +
                "join LoLoanCategory llc ON ar.loanCategoryId = llc.id " +
                "join DataApproval da on ar.id = da.objectRef and da.module='Loan'  " +
                "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where " +
                "current_date() <= coalesce(da.expiredDate,current_date()) " +
                "and :serverPath = :serverPath " +
                "and da.status = :status " +
                "and da.currentAssignApprover like :approverEmployeeId " +
                "and (Date(ar.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (vea.name like :ppl or vea.employeeNo like :ppl or ar.requestNo like :ppl) " +
                "and ( cast(:loanTypeId as string) is null or ar.loanTypeId = :loanTypeId )"
    )
    Page<LoanRequestNeedApprovalDTO> findNeedApprovalBy(@Param("approverEmployeeId") String approverEmployeeId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, @Param("ppl") String ppl, @Param("loanTypeId") String loanTypeId, Pageable pageable, @Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath)" +
                ",llt.name,llc,lel.goLiveDate,lel.transferDate, :secret) " +
            "from " +
                "LoLoanRequest ar " +
                "left join LoEmployeeLoan lel ON lel.requestNo = ar.requestNo " +
                "join LoLoanCategory llc ON llc.id = ar.loanCategoryId " +
                "join LoLoanType llt ON llt.id = ar.loanTypeId " +
                "join DataApproval da on ar.id = da.objectRef and da.module='Loan' and da.isByAdmin=false " +
                "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  " +
                "(cast(:employmentId as string) is null or ar.employmentId = :employmentId) " +
                "and :serverPath = :serverPath " +
                "and (da.status = :status or :status is null ) " +
                "and (ar.loanTypeId = :loanTypeId or :loanTypeId is null ) " +
                "and (Date(ar.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (:requestNo is null or ar.requestNo like :requestNo) " +
                "and (coalesce(ar.evidence,false) = :evidence or :evidence is null ) "
    )
    Page<LoanMyRequestDTO> findByEmployeeAndStatus(@Param("employmentId") String employmentId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, Pageable pageable, @Param("requestNo") String requestNo,@Param("loanTypeId") String loanTypeId,@Param("evidence") Boolean evidence,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath) " +
                ",llt.name,llc,lel.goLiveDate,lel.transferDate, :secret ) " +
            "from " +
                "LoLoanRequest ar " +
                "left join LoEmployeeLoan lel ON lel.requestNo = ar.requestNo " +
                "join LoLoanCategory llc ON llc.id = ar.loanCategoryId " +
                "join LoLoanType llt ON llt.id = ar.loanTypeId " +
                "join DataApproval da on ar.id = da.objectRef and da.module='Loan' and da.isByAdmin=false " +
                "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  " +
                "(:employmentId is null or ar.employmentId = :employmentId) " +
                "and :serverPath = :serverPath " +
                "and da.id = :dataApprovalId"
    )
    Optional<LoanMyRequestDTO> findByEmployeeAndId(@Param("employmentId") String employmentId,@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanMyRequestDTO(" +
            "ar" +
            ",rct" +
            ",da" +
            ",vea" +
//                ",ada" +
            ",f_get_empname (da.currentAssignApprover)" +
            ",concat('',:serverPath) " +
            ",llt.name,llc,lel.goLiveDate,lel.transferDate, :secret ) " +
            "from " +
            "LoLoanRequest ar " +
            "left join LoEmployeeLoan lel ON lel.requestNo = ar.requestNo " +
            "join LoLoanCategory llc ON llc.id = ar.loanCategoryId " +
            "join LoLoanType llt ON llt.id = ar.loanTypeId " +
            "join DataApproval da on ar.id = da.objectRef and da.module='Loan' and da.isByAdmin=false " +
            "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
            "join RequestCategoryType rct on rct.id = ar.categoryId  " +
            "where  1=1 " +
            "and :serverPath = :serverPath " +
            "and da.id = :dataApprovalId"
    )
    Optional<LoanMyRequestDTO> findByEmployeeAndIdAdmin(@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,@Param("secret") String secret);


    @Query("select new com.phincon.talents.app.dto.loan.LoanMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath)" +
                ",llt.name" +
                ",llc,lel.goLiveDate,lel.transferDate, :secret" +
            ") " +
            "from " +
                "LoLoanRequest ar " +
                "left join LoEmployeeLoan lel ON lel.requestNo = ar.requestNo " +
                "join LoLoanCategory llc ON llc.id = ar.loanCategoryId " +
                "join LoLoanType llt ON llt.id = ar.loanTypeId " +
                "join DataApproval da on ar.id = da.objectRef and da.module='Loan'  " +
                "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId " +
            " where  " +
                ":serverPath = :serverPath " +
                "and da.id = :dataApprovalId " +
                "and da.currentAssignApprover like :approverEmployeeId"
    )
    Optional<LoanMyRequestDTO> findNeedApprovalById(@Param("approverEmployeeId") String approverEmployeeId,@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,@Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp,llt.name, :secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoLoanRequest br  on br.id = da.objectRef and da.module='Loan'" +
                "join LoLoanType llt ON br.loanTypeId = llt.id " +
                "join VwEmpAssignment vea on br.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and (dad.action = :status or (:status is null and dad.action in ('Approved','Rejected') ) ) " +
                "and dad.actionByEmployeeId = :approverEmployeeId " +
                "and (Date(br.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (vea.name like :ppl or vea.employeeNo like :ppl or br.requestNo like :ppl)"
    )
    Page<LoanRequestHistoryApprovalDTO> findHistoryApproval(@Param("approverEmployeeId") String approverEmployeeId, @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, @Param("ppl") String ppl, Pageable pageable, @Param("secret") String secret);

    @Query("select new com.phincon.talents.app.dto.loan.LoanHistoryDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",veaApp" +
                ",lel" +
                ",llc" +
                ",llt" +
                ",lvb" +
                ",lvt" +
//                ",dad" +
                ", :secret" +
                ",:serverPath " +
            ") " +
            "from " +
                "LoLoanRequest br    " +
                "join LoLoanCategory llc ON br.loanCategoryId= llc.id " +
                "join LoLoanType llt ON br.loanTypeId= llt.id " +
                "left join LoVehicleBrand lvb ON br.brandId = lvb.id " +
                "left join LoVehicleType lvt ON lvb.loanVehicleTypeId= lvt.id " +
                "left join LoEmployeeLoan lel ON lel.requestNo = br.requestNo  " +
                "join DataApproval da on br.id = da.objectRef and da.module='Loan' and da.status = 'Approved' and (:requestNo is null or br.requestNo = :requestNo) " +
//                "left join DataApprovalDetail dad ON da.id = dad.dataApprovalId " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employmentId = br.employmentId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and da.status = 'Approved' " +
                "and veaApp.employeeId = :employeeId " +
                "and (:requestNo is null or br.requestNo = :requestNo) " +
                "and (:nikName is null or veaApp.name LIKE :nikName) " +
                "and (:loanStatus is null or lel.status = :loanStatus) " +
                "and (:loanTypeId is null or br.loanTypeId = :loanTypeId) " +
                "and (Date(br.loanDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) "
    )
    Page<LoanHistoryDTO> findHistoryLoan(
            @Param("employeeId") String employeeId
            , @Param("requestNo") String requestNo
            , @Param("nikName") String nikName
            , @Param("loanStatus") String loanStatus
            , @Param("loanTypeId") String loanTypeId
            , @Param("serverPath") String serverPath
            , @Param("requestDateStart") Date requestDateStart
            , @Param("requestDateEnd") Date requestDateEnd
            , Pageable pageable
            , @Param("secret") String secret
    );

    @Query("select new com.phincon.talents.app.dto.loan.LoanHistoryDetailDTO(" +
            "br" +
            ",veaApp" +
            ",lel" +
            ",llc" +
            ",llt" +
            ",lvb" +
            ",lvt" +
            ",da" +
            ",:serverNamePath " +
            ",:secret" +
            ") " +
            "from " +
            "LoLoanRequest br    " +
            "join LoLoanCategory llc ON br.loanCategoryId= llc.id " +
            "join LoLoanType llt ON br.loanTypeId= llt.id " +
            "join DataApproval da on br.id = da.objectRef and da.module='Loan' " +

            "left join LoVehicleBrand lvb ON br.brandId = lvb.id " +
            "left join LoVehicleType lvt ON lvb.loanVehicleTypeId= lvt.id " +
            "left join LoEmployeeLoan lel ON lel.requestNo = br.requestNo  " +
            "join VwEmpAssignment veaApp on veaApp.employmentId = br.employmentId  " +
            "where  " +
            "br.id = :loanRequestId "
    )
    LoanHistoryDetailDTO findHistoryLoanDetail(
            @Param("loanRequestId") String loanRequestId
            ,@Param("serverNamePath") String serverNamePath
            ,@Param("secret") String secret
    );

    @Query("select new com.phincon.talents.app.dto.loan.LoanHistoryDetailApproverDTO(" +
            "br.requestNo" +
            ",dad.actionByEmployeeId" +
            ",veaApp.name" +
            ",dad.action" +
            ",dad.level" +
            ") " +
            "from " +
                "LoLoanRequest br    " +
                "join DataApproval da on br.id = da.objectRef and da.module='Loan' " +
                "left join DataApprovalDetail dad ON da.id = dad.dataApprovalId " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                "br.requestNo = :requestNo " +
            "order by dad.level asc "
    )
    List<LoanHistoryDetailApproverDTO> findApproverByRequestId(
            @Param("requestNo") String requestNo
    );

    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp,llt.name,:secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoLoanRequest br  on br.id = da.objectRef and da.module='Loan'  " +
                "join LoLoanType llt ON br.loanTypeId = llt.id " +
                "join VwEmpAssignment vea on br.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and dad.actionByEmployeeId = :approverEmployeeId  " +
                "and dad.id = :dataApprovalDetailId"
    )
    Optional<LoanRequestHistoryApprovalDTO> findHistoryApprovalByIdAndEmployee(@Param("approverEmployeeId") String approverEmployeeId,@Param("dataApprovalDetailId") String dataApprovalDetailId,@Param("serverPath") String serverPath, @Param("secret") String secret);


    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestHistoryApprovalDTO(" +
                "br" +
                ",rct" +
                ",da" +
                ",vea" +
//                ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath),dad,veaApp,llt.name, :secret) " +
            "from " +
                "DataApprovalDetail dad " +
                "left join DataApproval da  on da.id = dad.dataApprovalId " +
                "join  LoLoanRequest br  on br.id = da.objectRef and da.module='Loan'  " +
                "join LoLoanType llt ON br.loanTypeId = llt.id " +
                "join VwEmpAssignment vea on br.employmentId = vea.employmentId " +
//                "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = br.categoryId  " +
                "join VwEmpAssignment veaApp on veaApp.employeeId = dad.actionByEmployeeId  " +
            "where  " +
                ":serverPath = :serverPath " +
                "and dad.action is not null " +
                "and da.id = :dataApprovalId   "
    )
    List<LoanRequestHistoryApprovalDTO> findHistoryApprovalByDataApprovalId(@Param("dataApprovalId") String dataApprovalId,@Param("serverPath") String serverPath,Sort sort,@Param("secret") String secret);

//    String id, String loanAttachmentId, String loanAttachmentName, String loanAttachmentDesc, String path, LocalDateTime loanAttachmentStartDate, java.time.LocalDateTime loanAttachmentEndDate
    @Query("select new com.phincon.talents.app.dto.loan.LoanRequestAttachmentNeedApprovalDTO(" +
                "ada.id" +
                ",ada.objectRefId" +
                ",lla.name" +
                ",lla.description" +
                ",ada.path" +
                ",lla.startDate" +
                ",lla.endDate " +
            ") " +
            "from " +
                "DataApproval da " +
                "join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "left join LoLoanAttachment lla on ada.objectRefId = lla.id " +
            "where 1=1 " +
                "and da.id = :dataApprovalId"
    )
    List<LoanRequestAttachmentNeedApprovalDTO> getDetailAttachments(@Param("dataApprovalId") String dataApprovalId);

    @Query("select count(1) " +
            "from " +
                "LoLoanRequest pr " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Loan'  " +
            "where  " +
                "pr.employmentId = :employmentId  " +
                "AND (da.status='In Progress' or (da.status='Approved' and pr.needSyncEstar = True)) ")
    Integer countPendingAndNeedSync(@Param("employmentId") String employmentId);

    @Query("select new com.phincon.talents.app.dto.loan.LoanMyRequestDTO(" +
                "ar" +
                ",rct" +
                ",da" +
                ",vea" +
    //            ",ada" +
                ",f_get_empname (da.currentAssignApprover)" +
                ",concat('',:serverPath)" +
                ",llt.name,llc,lel.goLiveDate,lel.transferDate, :secret) " +
            "from " +
                "LoLoanRequest ar " +
                "left join LoEmployeeLoan lel ON lel.requestNo = ar.requestNo " +
                "join LoLoanCategory llc ON llc.id = ar.loanCategoryId " +
                "join LoLoanType llt ON llt.id = ar.loanTypeId " +
                "join DataApproval da on ar.id = da.objectRef and da.module='Loan' and da.isByAdmin=true  " +
                "join VwEmpAssignment vea on ar.employmentId = vea.employmentId " +
    //            "left join AttachmentDataApproval ada on ada.dataApproval = da.id " +
                "join RequestCategoryType rct on rct.id = ar.categoryId " +
                "LEFT JOIN VwEmpAssignment empRqstee on empRqstee.employeeId=da.employeeId " +
            "where " +
                ":serverPath = :serverPath " +
                "and (da.status = :status or :status is null ) " +
                "and (ar.requestNo = :requestNo or :requestNo is null ) " +
                "and (ar.loanTypeId = :loanTypeId or :loanTypeId is null ) " +
                "and (llt.loanCategoryId = :loanCategoryId or :loanCategoryId is null ) " +
                "and (Date(ar.requestDate) between :requestDateStart and :requestDateEnd or :requestDateStart is null or :requestDateEnd is null) " +
                "and (empRqstee.name like :ppl or empRqstee.employeeNo like :ppl or ar.requestNo like :ppl)")
    Page<LoanMyRequestDTO> findByEmployeeAndStatusAdmin(@Param("requestNo") String requestNo,@Param("loanTypeId") String loanTypeId,@Param("loanCategoryId") String loanCategoryId,
                                                        @Param("status") String status, @Param("serverPath") String serverPath, @Param("requestDateStart") Date requestDateStart, @Param("requestDateEnd") Date requestDateEnd, @Param("ppl") String ppl, Pageable pageable, @Param("secret") String secret);

    @Query("select da " +
            "from " +
                "LoLoanRequest pr " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Loan'  " +
            "where  " +
                "da.objectRef = :objectRefId  "
    )
    Optional<DataApproval> findByObjectRef(@Param("objectRefId") String objectRefId);

    @Query("select pr " +
            "from " +
                "LoLoanRequest pr " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Loan'  " +
            "where 1=1 " +
                "AND pr.requestNo = cast(:requestNo as string)  " +
                "AND da.status = 'Approved'  "
    )
    Optional<LoLoanRequest> findByRequestNoApproved(@Param("requestNo") String requestNo);

    @Query("select pr " +
            "from " +
            "LoLoanRequest pr " +
            "join DataApproval da on pr.id = da.objectRef and da.module='Loan'  " +
            "where 1=1 " +
            "AND pr.requestNo = cast(:requestNo as string)  "
    )
    Optional<LoLoanRequest> findByRequestNo(@Param("requestNo") String requestNo);

    // @Query("select new com.phincon.talents.app.dto.loan.LoanCategoryRequestDTO(" +
    //             "MAX(llc.id)" +
    //             ",llc.name" +
    //             ",COUNT(pr.id)" +
    //         ") " +
    //         "FROM " +
    //             "LoLoanRequest pr " +
    //             "join LoLoanCategory llc ON pr.loanCategoryId = llc.id AND llc.name IN (:loanCategoryNameList) " +
    //             "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('In Progress','Approved') " +
    //             "join LoEmployeeLoan lel on pr.requestNo = lel.requestNo and lel.status != '" + LoEmployeeLoan.STATUS_PAID_OFF + "' " +
    //         "WHERE 1=1 " +
    //             "AND (pr.employmentId = cast(:employmentId as string) ) " +
    //             "AND (llc.name IN (:loanCategoryNameList) ) " +
    //         "GROUP BY llc.name " +
    //         "HAVING COUNT(pr.id) > 0 "
    // )
    // public List<LoanCategoryRequestDTO> countLoanCategoryNameRequest(
    //         @Param("employmentId") String employmentId
    //         ,@Param("loanCategoryNameList") List<String> loanCategoryNameList
    // );

    @Query("select new com.phincon.talents.app.dto.loan.LoanCategoryRequestDTO(" +
        "MAX(llc.id)" +
        ",llc.name" +
        ",COUNT(pr.id)" +
        ") " +
        "FROM " +
            "LoLoanRequest pr " +
            "join LoLoanCategory llc ON pr.loanCategoryId = llc.id AND llc.name IN (:loanCategoryNameList) " +
            "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('" + DataApproval.IN_PROGRESS + "','" + DataApproval.APPROVED + "') " + 
            "join LoEmployeeLoan lel on pr.requestNo = lel.requestNo and lel.status NOT IN ('"+ LoEmployeeLoan.STATUS_PAID_OFF + "', '"+ LoEmployeeLoan.STATUS_CANCEL + "') " + 
        "WHERE 1=1 " +
            "AND (pr.employmentId = cast(:employmentId as string) ) " + 
            "AND (llc.name IN (:loanCategoryNameList) ) " +
        "GROUP BY llc.name " +
        "HAVING COUNT(pr.id) > 0 "
    )

    public List<LoanCategoryRequestDTO> countLoanCategoryNameRequest(
        @Param("employmentId") String employmentId
        ,@Param("loanCategoryNameList") List<String> loanCategoryNameList
    );

    @Query("select " +
                "COUNT(pr.id)" +
            "FROM " +
                "LoLoanRequest pr " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('In Progress') " +
            "WHERE 1=1 " +
                "AND (pr.loanCategoryId = :loanCategoryId ) " +
            "GROUP BY pr.loanCategoryId "
    )
    public Integer countLoanCategoryNameRequestByCategoryId(
            @Param("loanCategoryId") String loanCategoryId
    );

    @Query("select " +
                "pr " +
            "FROM " +
                "LoLoanRequest pr " +
                "join DataApproval da on pr.id = da.objectRef and da.module='Loan' and da.status IN ('Approved') " +
            "WHERE 1=1 " +
                "AND pr.needSyncEstar = true " +
                "AND da.status = 'Approved' " +
                "AND (:employmentId is null or pr.employmentId = :employmentId) "
    )
    public List<LoLoanRequest> findNeedSync(
            @Param("employmentId") String employmentId
    );

    @Query("select " +
            "SUM(u.monthlyInstallment) " +
            "FROM " +
            "LoLoanRequest u " +
            "join DataApproval da on u.id = da.objectRef and da.module='Loan' and da.status = cast(:status as string) " +
            "WHERE 1=1 " +
            "AND (da.status = cast(:status as string) ) " +
            "AND u.employmentId = :employmentId "
    )
    public Optional<Double> findByStatus(
            @Param("status") String status,
            @Param("employmentId") String employmentId
    );
}
