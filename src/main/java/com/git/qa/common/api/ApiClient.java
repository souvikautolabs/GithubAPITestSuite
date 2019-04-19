package com.git.qa.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ApiClient {
    private ApiConfiguration apiConfiguration;
    private ArrayList<HashMap> requestParams = new ArrayList();
    private CommonResponse response;
    private EndPoint endpoint;
    private RestSession restSession;
    private Response restResponse;
    private ArrayList<ArrayList<HashMap>> sessionHistory = new ArrayList();
    private HashMap<String, String> headerList = new HashMap<>();

    public ApiClient(ApiConfiguration apiConfiguration) {
        this.setAPIConfiguration(apiConfiguration);
    }

    public CommonResponse sendRequest() throws Exception {
        if (this.endpoint == null) {
            throw new Exception("API Endpoint is not configured.");
        } else {
            try {
                this.sendRESTRequest();
            } catch (Exception var2) {
                throw new Exception("Problem while sending the request:", var2);
            }
            ArrayList<HashMap> params = this.requestParams;
            this.sessionHistory.add(params);
            this.requestParams.clear();
            return this.response;
        }
    }

    private void sendRESTRequest() throws Exception {
        this.setRestSession(new RestSession(this.getAPIConfiguration()));
        if (this.getHeaders().isEmpty()) {
            restResponse = getRestSession().sendRequest(requestParams.get(0), getEndpoint());
        } else {
            restResponse = getRestSession().sendRequest(requestParams.get(0), getEndpoint(), getHeaders());
        }
        this.response = new CommonResponse(restResponse);
    }

    public HashMap<String, String> getHeaders() {
        return headerList;
    }

    public void setHeaders(HashMap<String, String> headerMap) {
        headerList.putAll(headerMap);
    }

    public RestSession getRestSession() {
        return this.restSession;
    }

    public void setAPIConfiguration(ApiConfiguration APIConfiguration) {
        this.apiConfiguration = APIConfiguration;
    }

    public void setRestSession(RestSession restSession) {
        this.restSession = restSession;
    }

    public ApiConfiguration getAPIConfiguration() {
        return this.apiConfiguration;
    }

    public EndPoint getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(EndPoint endpoint) {
        this.endpoint = endpoint;
    }

    public void setRequest(Object object) throws IOException {
        if (HashMap.class.isInstance(object)) {
            HashMap map = (HashMap)object;
            this.requestParams.add(map);
        } else {
            ObjectMapper m = new ObjectMapper();
            this.requestParams.add(m.convertValue(object, HashMap.class));
        }
    }

    public CommonResponse getResponse() {
        return this.response;
    }

}
