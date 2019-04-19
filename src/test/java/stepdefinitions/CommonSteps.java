package stepdefinitions;


import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.testng.Assert;
import com.git.qa.common.api.ApiClient;
import com.git.qa.zomatoapis.GITContext;

import java.util.HashMap;

public class CommonSteps {
    private GITContext context;
    private ApiClient apiClient;

    public CommonSteps(GITContext context) throws Throwable{
        this.context = context;
        this.apiClient = GITContext.getApiClient();
    }

    @And("^I send the request$")
    public void iSendTheRequest() throws Throwable {
       HashMap<String,String> apiAuth = new HashMap<String,String>();
       apiAuth.put(apiClient.getAPIConfiguration().getProperties().getStringValue("GIT_APIKEY_PARAMETER"),
               apiClient.getAPIConfiguration().getProperties().getStringValue("GIT_APIKEY_VALUE"));
       apiClient.setHeaders(apiAuth);
       apiClient.sendRequest();
    }

    @Then("^I should receive response code (\\d+)$")
    public void iShouldReceiveResponseCode(int respCode) throws Throwable {
        int statusCode = apiClient.getResponse().getStatusCode();
        Assert.assertEquals(statusCode,respCode,"Expected : "+respCode+" -  Actual : "+statusCode);
    }


}
