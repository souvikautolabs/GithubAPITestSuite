package com.git.qa.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RestRequestBuilder {
    public RestRequestBuilder() {
    }

    public static RequestSpecBuilder requestSpecBuilderWithParameters(HashMap params, ParametersType parametersType) throws Exception {
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        if (params == null) {
            return reqBuilder;
        } else {
            Iterator it = params.entrySet().iterator();

            while(it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                if (pair.getValue() != null) {
                    switch(parametersType) {
                        case FORM:
                            reqBuilder.removeFormParam(pair.getKey().toString());
                            reqBuilder.addFormParam(pair.getKey().toString(), new Object[]{pair.getValue()});
                            break;
                        case QUERY:
                            reqBuilder.removeQueryParam(pair.getKey().toString());
                            reqBuilder.addQueryParam(pair.getKey().toString(), new Object[]{pair.getValue()});
                            break;
                        case REQUEST:
                            reqBuilder.removeParam(pair.getKey().toString());
                            reqBuilder.addParam(pair.getKey().toString(), new Object[]{pair.getValue()});
                            break;
                        case PATH:
                            reqBuilder.removePathParam(pair.getKey().toString());
                            reqBuilder.addPathParam(pair.getKey().toString(), pair.getValue());
                    }
                }
            }

            if (parametersType == ParametersType.JSON && params.size() > 0) {
                reqBuilder.setContentType(ContentType.JSON);
                String mapAsJson = (new ObjectMapper()).writeValueAsString(params);
                reqBuilder.setContent(mapAsJson);
            }

            return reqBuilder;
        }
    }
}
