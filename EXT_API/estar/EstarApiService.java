package com.phincon.external.app.services.estar;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phincon.external.app.config.ExternalAPIConfig;
import com.phincon.external.app.dto.loan.estar.EstarLoanEmployeeDataResultDTO;
import com.phincon.external.app.dto.loan.estar.EstarRequestLoanEmployeeDataDTO;
import com.phincon.external.app.dto.loan.estar.EstarResponseSubmitLoanEmployeeDataDTO;
import com.phincon.external.app.dto.loan.estar.EstarSubmitLoanEmployeeDataDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class EstarApiService {
    private static final Logger log = LogManager.getLogger(EstarApiService.class);

    @Value("${estar.base.url}")
    String BASE_URL_ESTAR;

    @Value("${estar.basic.token}")
    String BASIC_TOKEN_ESTAR;

    @Value("${estar.requestLoanEmployeeData.url}")
    String REQUEST_LOAN_EMPLOYEE_ESTAR;

    @Value("${estar.submitLoanEmployee.url}")
    String SUBMIT_LOAN_EMPLOYEE_ESTAR;

    String urlRequestLoanEmployeeData;

    String urlSubmitLoanEmployeeData;


    public EstarApiService() {
        // The constructor is called before property values are injected by Spring
//        this.BASE_URL_ESTAR = "http://localhost:3000";
//        this.BASIC_TOKEN_ESTAR = "WzbY/XLcHFYB6bsFT1jYQZNt9MeygomF";
//        this.urlRequestLoanEmployeeData = this.BASE_URL_ESTAR + "/HCEazyServices.svc/rest/RequestLoanEmployeeData";
//        this.urlSubmitLoanEmployeeData = this.BASE_URL_ESTAR + "";
    }

    @PostConstruct
    private void init() {
        // This method will be called after the bean has been constructed and properties have been set
//        this.urlRequestLoanEmployeeData = this.BASE_URL_ESTAR + "/HCEazyServices.svc/rest/RequestLoanEmployeeData";
//        this.urlRequestLoanEmployeeData = this.BASE_URL_ESTAR + "/HCEazyServices.svc/rest/RequestLoanEmployeeData";
//        this.urlSubmitLoanEmployeeData = this.BASE_URL_ESTAR + "/HCEazyServices.svc/rest/SubmitLoanEmployee";
        this.urlRequestLoanEmployeeData = this.BASE_URL_ESTAR + "/" + REQUEST_LOAN_EMPLOYEE_ESTAR;
        this.urlSubmitLoanEmployeeData = this.BASE_URL_ESTAR + "/" + SUBMIT_LOAN_EMPLOYEE_ESTAR;

    }

    //-----------------------------------------
    // HcEazy To Estar API
    //-----------------------------------------
    @Transactional
    public Map<String,Object> getToken(String logIdentifier){
        log.info(logIdentifier + "getToken triggered ");
        Map<String, Object> responseMaps = new HashMap<>();

        //for now estar only use static token
        responseMaps.put("token",BASIC_TOKEN_ESTAR);
        System.out.println(responseMaps.toString());
        return responseMaps;
    }

    @Transactional
    private MultiValueMap<String, String> getHeaderParams(String logIdentifier,String module, Map<String,Object> token){
        log.info(logIdentifier + "getHeaderParams triggered ");

        MultiValueMap<String, String> headerParams = new LinkedMultiValueMap<>();

        //for now, no different between any module
        //for now no token used too
        // just hardcode  header param
        headerParams.add("content-type", "application/json");

        return headerParams;
    }

    @Transactional
    public EstarLoanEmployeeDataResultDTO requestLoanEmployeeData(String logIdentifier, EstarRequestLoanEmployeeDataDTO requestBody) throws JsonProcessingException {
        log.info(logIdentifier + "requestLoanEmployeeData triggered ");
        log.info(logIdentifier + "requestBody : " + (requestBody == null ? "null" : requestBody.toString()));
        Map<String,Object> response = new HashMap<>();
        EstarLoanEmployeeDataResultDTO responseDTO = new EstarLoanEmployeeDataResultDTO();

        String url = urlRequestLoanEmployeeData;
        log.info(logIdentifier + "api used : " + url);
        //get token
        Map<String,Object> tokenResponse = getToken(logIdentifier);
        String authToken = tokenResponse.get("token") == null ? null : tokenResponse.get("token").toString();

        //get Header Param
        MultiValueMap<String, String> headerParams = getHeaderParams(logIdentifier,"",null);
        log.info(headerParams);

        //convert response to responseDTO
        try {
            log.info(logIdentifier + "try to call external api");
            response = ExternalAPIConfig.callExternalAPI(logIdentifier,HttpMethod.POST,false,requestBody, url, authToken,ExternalAPIConfig.AUTH_BASIC, headerParams,null);
            log.info(logIdentifier + "success to call external api");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(response);
            log.info(logIdentifier + "response from api : " + jsonString);

            log.info(logIdentifier + "try to convert to dto ");
            responseDTO = objectMapper.readValue(jsonString, EstarLoanEmployeeDataResultDTO.class);
            log.info(logIdentifier + "success convert to dto ");

        }catch (Exception ex){
            ex.printStackTrace();
        }


        log.info(logIdentifier + "response " + (response == null ? "null" : responseDTO.toString()));
        return responseDTO;
    }

    private Map<String,Object> callSubmitLoanAPI(String NIP, String NIK, String NoRequest){

        // Replace with your actual JSON payload
        String jsonPayload = "{\"SubmitLoanEmployeeData\":[{\"NIP\":\"" + NIP + "\",\"NIK\":\"" + NIK + "\",\"NoRequest\":\"" + NoRequest + "\"}]}";

        // Set the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + BASIC_TOKEN_ESTAR);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set the request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

        // Set the URL
        String url = urlSubmitLoanEmployeeData;

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Make the POST request
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // Print the response
        Map<String, Object> responseBody = responseEntity.getBody();
        log.info("Response: " + responseBody);
        return responseBody;
    }

    @Transactional
    public EstarResponseSubmitLoanEmployeeDataDTO submitLoanEmployeeData(String logIdentifier, EstarSubmitLoanEmployeeDataDTO requestBody) throws JsonProcessingException {
        log.info(logIdentifier + "submitLoanEmployeeData triggered ");
        log.info(logIdentifier + "requestBody : " + (requestBody == null ? "null" : requestBody.toString()));
        Map<String,Object> response = new HashMap<>();
        EstarResponseSubmitLoanEmployeeDataDTO responseDTO = new EstarResponseSubmitLoanEmployeeDataDTO();

        String url = urlSubmitLoanEmployeeData;
        log.info(logIdentifier + "api used : " + url);

        //get token
        Map<String,Object> tokenResponse = getToken(logIdentifier);
        String authToken = tokenResponse.get("token") == null ? null : tokenResponse.get("token").toString();

        //get Header Param
        MultiValueMap<String, String> headerParams = getHeaderParams(logIdentifier,"",null);

        //convert response to responseDTO
        try {
            log.info(logIdentifier + "try to call external api");
//            response = ExternalAPIConfig.callExternalAPI(logIdentifier,HttpMethod.POST,false,requestBody, url, authToken,ExternalAPIConfig.AUTH_BASIC, headerParams,null);
            response = callSubmitLoanAPI(requestBody.getSubmitLoanEmployeeData().get(0).getNip(),
                    requestBody.getSubmitLoanEmployeeData().get(0).getNik(),
                    requestBody.getSubmitLoanEmployeeData().get(0).getNoRequest()
                    );
            log.info(logIdentifier + "success to call external api");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(response);
            log.info(logIdentifier + "response from api : " + jsonString);

            log.info(logIdentifier + "try to convert to dto ");
            responseDTO = objectMapper.readValue(jsonString, EstarResponseSubmitLoanEmployeeDataDTO.class);
            log.info(logIdentifier + "success convert to dto ");

        }catch (Exception ex){
            ex.printStackTrace();
        }


        log.info(logIdentifier + "response " + (response == null ? "null" : responseDTO.toString()));
        return responseDTO;
    }
    //-----------------------------------------

}
