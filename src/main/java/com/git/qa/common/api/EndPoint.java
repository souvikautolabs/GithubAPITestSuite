package com.git.qa.common.api;

public interface EndPoint {
    String getRestMethodPath();
    HttpMethod getRestHttpMethodType();
    ParametersType getRestParametersType();

}
