package com.git.qa.zomatoapis;


import com.git.qa.common.api.EndPoint;
import com.git.qa.common.api.ParametersType;
import com.git.qa.common.api.HttpMethod;

import java.util.HashMap;
import java.util.Map;

//Constructing API method path
public class GITAPIMethod implements EndPoint {

    private String methodUrl;
    private HttpMethod httpMethod;
    private HashMap<String, String> params = new HashMap<>();
    private ParametersType reqParamType = ParametersType.JSON;

    public GITAPIMethod(String methodUrl, HttpMethod httpMethod, ParametersType paramType){
          this.methodUrl = methodUrl;
          this.httpMethod = httpMethod;
          this.reqParamType = paramType;
    }

    public String getMethodUrl() {
        return methodUrl;
    }

    public String getRestMethodPath() {
        if (params.isEmpty()) {
            return methodUrl;
        } else {
            for (Map.Entry item : params.entrySet()) {
                this.methodUrl = this.methodUrl.replaceFirst("\\{" + item.getKey() + "\\}", item.getValue().toString());
            }
            return this.methodUrl;
        }
    }

    @Override
    public HttpMethod getRestHttpMethodType() {
        return httpMethod;
    }

    @Override
    public ParametersType getRestParametersType() {
        return reqParamType;
    }

}
