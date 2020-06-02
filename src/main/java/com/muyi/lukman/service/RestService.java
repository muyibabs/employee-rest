package com.muyi.lukman.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muyi.lukman.config.RestTemplateConfig;
import com.muyi.lukman.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResourceAccessException;


import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class RestService {
    private static final Logger LOG = LoggerFactory.getLogger(RestService.class);
    protected HttpHeaders httpHeaders;
    @Autowired
    private RestTemplateConfig restTemplateFactory; //Why not autowire to constructor

    @PostConstruct
    public void init() {
        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        this.httpHeaders.set("Accept", "application/json");
        this.setErrorHandler();
    }

    private void setErrorHandler() {
        this.restTemplateFactory.restTemplate().setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                LOG.error("Response Status Code: " + response.getRawStatusCode());
                LOG.error("Response Status Text: " + response.getStatusText());
                if (response.getRawStatusCode() == 404) {//usually there is no response to parse in this case
                    LOG.error("Response Body: " + new String(getResponseBody(response)));
                    return true;
                }
                return false; //we want to be able to parse the error response
            }
        });
    }

    public <T> ResponseEntity<T> invokeClient(String url, HttpMethod method,
                                              HttpEntity<?> httpRequest,Class<T> object) {
        return this.restTemplateFactory.restTemplate().exchange(url, method, httpRequest, object);
    }
    //TODO: implementation REST call

    //Sample: Consume REST API
    public Employee employeeById(String employeeId) {
        HttpEntity<?> httpRequest = new HttpEntity<>(null, this.httpHeaders);
        ResponseEntity<?> responseTemp = null;
        String url = "http://localhost:8022/v1/employees/" + employeeId; //TODO thirty party URL from properties
        Employee employee = null;

        try {
            LOG.info("Server Result calling  URL: {}", url);
            responseTemp = invokeClient(url, HttpMethod.GET, httpRequest, String.class);

            if (responseTemp.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                LOG.info("Response Body:{}", responseTemp.getBody().toString());
                employee = mapper.readValue(responseTemp.getBody().toString(), Employee.class);
                LOG.info(employee.toString());
            } else {
                //Handle other Response Codes
                // handleOtherResponseCodes(responseTemp);
            }
        } catch (ResourceAccessException e) {
            String msg = e.getMessage();
            LOG.error("Exception calling  URL: {}", url, e);
            //throw new RestException(HBEConstants.HPF_MS_BS_003, e.getMessage());
        } catch (Exception e) {
            LOG.error("General Exception calling  URL: {}", url, e);
        }
        return employee;
    }

    public Object getRandomFact(){
        HttpEntity<?> httpRequest = new HttpEntity<>(null, this.httpHeaders);
        ResponseEntity<?> responseTemp = null;
        String url = "https://gturnquist-quoters.cfapps.io/api/random"; //TODO thirty party URL from properties
        Object object = null;

        try {
            LOG.info("Server Result calling  URL: {}", url);
            responseTemp = invokeClient(url, HttpMethod.GET, httpRequest, String.class);

            if (responseTemp.getStatusCode().is2xxSuccessful()) {
                ObjectMapper mapper = new ObjectMapper();
                LOG.info("Response Body:{}", responseTemp.getBody().toString());
                object = mapper.readValue(responseTemp.getBody().toString(), Object.class);
                LOG.info(object.toString());
            } else {
                //Handle other Response Codes
                // handleOtherResponseCodes(responseTemp);
            }
        } catch (ResourceAccessException e) {
            String msg = e.getMessage();
            LOG.error("Exception calling  URL: {}", url, e);
            //throw new RestException(HBEConstants.HPF_MS_BS_003, e.getMessage());
        } catch (Exception e) {
            LOG.error("General Exception calling  URL: {}", url, e);
        }
        return object;
    }
}

