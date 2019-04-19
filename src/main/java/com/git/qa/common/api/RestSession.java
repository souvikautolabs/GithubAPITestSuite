package com.git.qa.common.api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import com.git.qa.zomatoapis.GITAPIMethod;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class RestSession {
    public RequestSpecBuilder requestSpecBuilder;
    public Response response;
    public long startTime;
    public ApiConfiguration configuration;
    public URL requestUrl;
    private String num;

    public RestSession(ApiConfiguration configuration) {
        this.configuration = configuration;
    }

    public Response sendRequest(HashMap parameters, EndPoint method) throws Exception {
        RequestSpecBuilder requestSpecBuilderWithParameters = RestRequestBuilder.requestSpecBuilderWithParameters(parameters, method.getRestParametersType());
        return this.sendSpec(requestSpecBuilderWithParameters, method);
    }

    public Response sendRequest(HashMap parameters, EndPoint method, HashMap<String, String> headerList) throws Exception {
        RequestSpecBuilder reqBuilder = null;
        if (parameters == null) {
            reqBuilder = new RequestSpecBuilder();
            reqBuilder.addHeaders(headerList);
            reqBuilder.setContentType(ContentType.JSON);
            return sendSpec(reqBuilder, method);
        } else {
            reqBuilder = RestRequestBuilder.requestSpecBuilderWithParameters(parameters, method.getRestParametersType());
            reqBuilder.addHeaders(headerList);
            return sendSpec(reqBuilder, method);
        }
    }

    private Response sendSpec(RequestSpecBuilder builder, EndPoint method) throws MalformedURLException {
        this.requestUrl = new URL(this.configuration.getHostName().toString()  + method.getRestMethodPath());
        builder.setBaseUri(this.configuration.getHostName());
        builder.setBasePath(((GITAPIMethod)method).getMethodUrl());
        if (this.configuration.getPort() != 0 && this.configuration.getPort() != 80) {
            builder.setPort(this.configuration.getPort());
        }

        RequestSpecification requestSpecification = builder.build();
        requestSpecification.log().all(true);
        requestSpecification.auth().basic(this.configuration.getApiUsername(),this.configuration.getApiPassword());
        ResponseSpecification spec = RestAssured.given().spec(requestSpecification).response();
        this.startTime = System.currentTimeMillis();
        switch(method.getRestHttpMethodType()) {
            case GET:
                this.response = (Response)spec.when().get(this.requestUrl);
                break;
            case POST:
                this.response = (Response)spec.when().post(this.requestUrl);
                break;
            case PUT:
                this.response = (Response)spec.when().put(this.requestUrl);
                break;
            case PATCH:
                this.response = (Response)spec.when().patch(this.requestUrl);
                break;
            case DELETE:
                this.response = (Response)spec.when().delete(this.requestUrl);
                break;
            case HEAD:
                this.response = (Response)spec.when().head(this.requestUrl);
        }

        long elapsedTime = System.currentTimeMillis() - this.startTime;
        ((ValidatableResponse)this.response.then()).log().all(true);
        return this.response;
    }
}
