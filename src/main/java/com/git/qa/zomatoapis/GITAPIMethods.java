package com.git.qa.zomatoapis;

import com.git.qa.common.api.HttpMethod;
import com.git.qa.common.api.ParametersType;

public class GITAPIMethods {

    // List of GIT APIs to test
    public GITAPIMethod GET_SEARCH_REPO = new GITAPIMethod("/repositories", HttpMethod.GET, ParametersType.QUERY);



}
