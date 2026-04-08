package com.phincon.talents.app.services.loan;

import com.phincon.talents.app.dto.CustomGenericException;
import com.phincon.talents.app.dto.loan.*;
import com.phincon.talents.app.model.loan.LoEmployeeLoan;
import com.phincon.talents.app.model.loan.LoLoanRequest;
import com.phincon.talents.app.model.loan.LoLoanTransferEvidence;
import com.phincon.talents.app.repository.LoEmployeeLoanRepository;
import com.phincon.talents.app.repository.LoLoanRequestRepository;
import com.phincon.talents.app.repository.LoLoanTransferEvidenceRepository;
import com.phincon.talents.app.utils.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoanTransferEvidenceService {
    private static final Logger log = LogManager.getLogger(LoanTransferEvidenceService.class);

    @Autowired
    LoLoanTransferEvidenceRepository repository;

    @Autowired
    RepositoryUtils repositoryUtils;

    @Autowired
    LoLoanRequestRepository loLoanRequestRepository;

    @Autowired
    LoEmployeeLoanRepository loEmployeeLoanRepository;

    @Autowired
    private Environment env;

    @Autowired
    LoLoanRequestRepository requestRepository;

    @Value("${mtf.sc.jwt.secret}")
    private String secret;

    @Transactional
    public Page<LoanTransferEvidenceListDTO> listData(
            String page, String size,
            String requestNo, LocalDate startDate, LocalDate endDate,
            HttpServletRequest request, Jwt jwt
    ) {
        Page<LoanTransferEvidenceListDTO> response;
        Pageable pageable= PageRequest.of(Integer.parseInt(page == null ? "0" : page),Integer.parseInt(size == null ? "1000" : size));

        String http = env.getProperty("talents.protocol");

        String requestNoUsed = "%%";
        if(requestNo != null){
            requestNoUsed = "%" + requestNo + "%";
        }

        response = repository.listData(requestNoUsed,startDate,endDate,pageable,Utils.getServerName(http, request, jwt, secret),secret);

        response.stream()
                .forEach(singleAttachment -> {
                    if(singleAttachment.getAttachment() != null){
                        singleAttachment.setAttachment( Utils.getServerName(http, request, jwt, secret) + singleAttachment.getAttachment());
                    }
                });

        return response;
    }

    @Transactional
    public LoanTransferEvidenceListDTO getDetail(String identifier, String id, HttpServletRequest request, Jwt jwt) {
        LoanTransferEvidenceListDTO response;

        String http = env.getProperty("talents.protocol");

        response = repository.getData(id,Utils.getServerName(http, request, jwt, secret),secret).orElseThrow(
                ()-> new CustomGenericException(identifier + " Data Doesn't Exist"));

        response.setAttachment(Utils.getServerName(http, request, jwt, secret) + response.getAttachment());

        return response;
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> submitData(String identifier, LoanTransferEvidencePostDTO request, HttpServletRequest requestHTTP, Jwt jwt) {
        log.info("Submit Data = "+request.toString());
        log.warn("Initialization Process Submit");

        //initialize variable
        LoLoanTransferEvidence dataUsed = new LoLoanTransferEvidence();
        String message=null;
        String http = env.getProperty("talents.protocol");

        //check mandatory field
        if(request.getRequestNo().isEmpty() || request.getRequestNo() == null){
            throw new CustomGenericException("Loan Request Can't Be Empty ! ");
        }

        Optional<LoLoanRequest> loanRequestFound = loLoanRequestRepository.findByRequestNo(request.getRequestNo());
        if(!loanRequestFound.isPresent()){
            throw new CustomGenericException("Loan Request Not Found ! ");
        }

        Optional<LoEmployeeLoan> loEmployeeLoanFound = loEmployeeLoanRepository.findByRequestNo(request.getRequestNo());
        if(!loEmployeeLoanFound.isPresent()){
            throw new CustomGenericException("Employee Loan Not Found !");
        }

        //check if already have same loan request


        //logic
        if(request.getId()==null){
            Optional<LoanTransferEvidenceListDTO> loLoanTransferEvidence = repository.findByRequestNo(request.getRequestNo(),Utils.getServerName(http, requestHTTP, jwt, secret),secret);
            if(loLoanTransferEvidence.isPresent()){
                dataUsed = repository.findById(loLoanTransferEvidence.get().getId()).orElseThrow(
                        ()-> new CustomGenericException( identifier + " Doesn't Exist")
                );

                String getFilePath = Utils.getServerName(http, requestHTTP, jwt, secret) + dataUsed.getAttachment();
                deleteFile(getFilePath);
            }

            message="Submit New " + identifier + " Successfully";
        }else {
            dataUsed = repository.findById(request.getId()).orElseThrow(
                    ()-> new CustomGenericException( identifier + " Doesn't Exist")
            );

            String getFilePath = Utils.getServerName(http, requestHTTP, jwt, secret) + dataUsed.getAttachment();
            deleteFile(getFilePath);

            message="Edit " + identifier + " Successfully";
        }
        BeanUtils.copyProperties(request,dataUsed,"id");

        String rootFolderUsed = "transfer-evidence";
        String fileName = createFile(request.getAttachment(), rootFolderUsed);
        dataUsed.setAttachment(fileName);

        repository.save(dataUsed);

        //update employee loan too
        loEmployeeLoanFound.get().setTransferDate(request.getTransferDate());
        loEmployeeLoanRepository.save(loEmployeeLoanFound.get());

        loanRequestFound.get().setEvidence(true);
        loLoanRequestRepository.save(loanRequestFound.get());

        //return
        return new ResponseEntity<>(new CustomMessageWithId(message,false,dataUsed.getId()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CustomMessageWithId> deleteData(String identifier,String id, HttpServletRequest request, Jwt jwt) {
        log.info("Delete " + identifier + " = "+ id.toString());
        log.warn("Initialization Process Delete");

        LoLoanTransferEvidence dataUsed = repository.findById( id).orElseThrow(
                ()-> new CustomGenericException( identifier + " Doesn't Exist")
        );

        //update employee loan too
        Optional<LoEmployeeLoan> loEmployeeLoanFound = loEmployeeLoanRepository.findByRequestNo(dataUsed.getRequestNo());
        if(!loEmployeeLoanFound.isPresent()){
            throw new CustomGenericException("Employee Loan Not Found !");
        }
        loEmployeeLoanFound.get().setTransferDate(null);
        loEmployeeLoanRepository.save(loEmployeeLoanFound.get());

        Optional<LoLoanRequest> loanRequestFound = loLoanRequestRepository.findByRequestNo(dataUsed.getRequestNo());
        if(!loanRequestFound.isPresent()){
            throw new CustomGenericException("Loan Request Not Found ! ");
        }

        loanRequestFound.get().setEvidence(false);
        loLoanRequestRepository.save(loanRequestFound.get());

        String http = env.getProperty("talents.protocol");
        String getFilePath = Utils.getServerName(http, request, jwt, secret) + dataUsed.getAttachment();
        deleteFile(getFilePath);

        //delete data
        repository.delete(dataUsed);

        return new ResponseEntity<>(new CustomMessageWithId("Delete " + identifier + " Successfully",false,dataUsed.getId()),HttpStatus.OK);
    }
    private String createFile(String file, String root) {
        // String fileType = "jpg";
        // String path = UPLOAD_ROOT_PATH;
        String path = GlobalValue.PATH_UPLOAD;
        // System.out.println("Path Upload : " + path);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        // String pathname = "career/" +format+
        // RandomStringUtils.randomAlphanumeric(10);
        String fileName = root + "/" + format + RandomStringUtils.randomAlphanumeric(10);
        ;
        FileOutputStream fos =null;
        ByteArrayInputStream bis = null;
        try {
            // BASE64Decoder decoder = new BASE64Decoder();
            Base64.Decoder decoder = java.util.Base64.getDecoder();
            byte[] fileByte;
            fileByte = decoder.decode(file);
            bis = new ByteArrayInputStream(fileByte);
            // String contentType = URLConnection.guessContentTypeFromStream(new
            // ByteArrayInputStream(fileByte));
            TikaConfig config = TikaConfig.getDefaultConfig();
            MediaType mediaType = config.getMimeRepository().detect(new ByteArrayInputStream(fileByte), new Metadata());
            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
            String extension = mimeType.getExtension();
            fileName = fileName + extension;
            // BufferedImage bufImg = decodeToImage(file);
            File imgOutFile = new File(path + fileName);
            // ImageIO.write(bufImg, fileType, imgOutFile);
            fos = new FileOutputStream(imgOutFile);
            fos.write(fileByte);
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fos!= null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis!= null)
            {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

    private void deleteFile(String filePath) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("File deleted successfully: " + filePath);
                } else {
                    System.out.println("Failed to delete the file: " + filePath);
                }
            } else {
                System.out.println("File not found: " + filePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional
    public Page<LoanMyRequestDTO> findLoanRequests(String employmentRequest, String status,
                                                                   HttpServletRequest request, String requestDateStart, String requestDateEnd, PageRequest pageable,String requestNo,
                                                                   String loanTypeId, Boolean anyTransfer, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date requestDateStartDate = null;
        Date requestDateEndDate = null;
        if (requestDateStart != null) {
            try {
                requestDateStartDate = sdf.parse(requestDateStart);
                requestDateEndDate = sdf.parse(requestDateEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        String employmentRequestUsed = null;
        if(employmentRequest != null){
            employmentRequestUsed = employmentRequest;
        }
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        //Get My Request Data
        //------------------------------------------------------------------------
        Page<LoanMyRequestDTO> findByEmployeeAndStatus = requestRepository.findByEmployeeAndStatus(
                employmentRequestUsed, status, Utils.getServerName(http, request, jwt, secret), requestDateStartDate, requestDateEndDate,
                pageable,'%'+requestNo+'%',loanTypeId,anyTransfer,secret);
        List<LoanMyRequestDTO> content = findByEmployeeAndStatus.getContent();

        //------------------------------------------------------------------------

        return findByEmployeeAndStatus;
    }

    @Transactional
    public Optional<LoanMyRequestDTO> findMyRequestByEmployeeAndId(String dataApprovalId,
                                                                   HttpServletRequest request, Jwt jwt) {
        //------------------------------------------------------------------------
        //Initialize Variable
        //------------------------------------------------------------------------
        String http = env.getProperty("talents.protocol");
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Data
        //------------------------------------------------------------------------
        Optional<LoanMyRequestDTO> resultOpt = requestRepository.findByEmployeeAndId(null, dataApprovalId,
                Utils.getServerName(http, request, jwt, secret),secret);
        //------------------------------------------------------------------------

        //------------------------------------------------------------------------
        //Get Detail Attachments
        //------------------------------------------------------------------------
        if(resultOpt.isPresent()) {
            Optional<LoanTransferEvidenceListDTO> loLoanTransferEvidence = repository.findByRequestNo(resultOpt.get().getRequestNo(),Utils.getServerName(http, request, jwt, secret),secret);
            if(loLoanTransferEvidence.isPresent()) {
                resultOpt.get().setTransferDate(loLoanTransferEvidence.get().getTransferDate());
                resultOpt.get().setRemark(loLoanTransferEvidence.get().getRemark());

                List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails = new ArrayList<>();
                LoanRequestAttachmentNeedApprovalDTO singleAttachment = new LoanRequestAttachmentNeedApprovalDTO();
                String serverNamePath = Utils.getServerName(http, request, jwt, secret);
                singleAttachment.setPath(loLoanTransferEvidence.get().getAttachment());
                attachmentDetails.add(singleAttachment);

                resultOpt.get().setAttachmentDetails(attachmentDetails);
            }else{
                resultOpt.get().setTransferDate(null);
                resultOpt.get().setRemark(null);
                List<LoanRequestAttachmentNeedApprovalDTO> attachmentDetails = new ArrayList<>();
                resultOpt.get().setAttachmentDetails(attachmentDetails);
            }
        }
        //------------------------------------------------------------------------

        return resultOpt;
    }

}
