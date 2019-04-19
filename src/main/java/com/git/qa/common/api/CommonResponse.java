package com.git.qa.common.api;

import io.restassured.response.Response;

public class CommonResponse {
    private Response restResponse;

    public CommonResponse(Response resp) {
        this.restResponse = resp;
    }

    public Response getRestResponse() {
        return this.restResponse;
    }

    public int getStatusCode() {
        return this.restResponse.getStatusCode();
    }

    public String getStatusline() {
        return this.restResponse.getStatusLine();
    }

}
