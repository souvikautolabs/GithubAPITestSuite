package stepdefinitions;

import com.git.qa.model.Item;
import com.git.qa.model.Repositories;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import com.git.qa.common.api.ApiClient;
import com.git.qa.zomatoapis.GITAPIMethods;
import com.git.qa.zomatoapis.GITContext;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.testng.Assert;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.MatcherAssert.assertThat;

public class SearchRepositorySteps {
    private GITContext context;
    private ApiClient apiClient;
    Response response;

    public SearchRepositorySteps(GITContext context) throws Throwable{
        this.context = context;
        this.apiClient = GITContext.getApiClient();
    }

    @Given("^I am an user with (.*?) willing to search repositories$")
    public void iAmAnUserWithKeywordsWillingToSearch(String Keywords) throws Throwable {
        context.clearParams();
        context.setParams("q",Keywords);
    }

    @When("^I set mandatory fields for repository search$")
    public void iSetMandatoryFieldsForRepositorySearch() throws Throwable {
        this.apiClient.setEndpoint(new GITAPIMethods().GET_SEARCH_REPO);
        this.apiClient.setRequest(context.getParams());
    }

    @And("^I should be able to (.*?) with relevant (.*?)$")
    public void iShouldBeAbleToFindRelevantKeywordsInQualifier(String validationType,String Keywords) throws Throwable {
        this.response = apiClient.getResponse().getRestResponse();
        Repositories repositories = response.as(Repositories.class, ObjectMapperType.GSON);
        String[] keywords = Keywords.toLowerCase().split("\\+");

        List<String> desc = new ArrayList<>();
        LinkedList<Integer> starCnt = new LinkedList<>();
        for(Item items : repositories.getItems()){
            switch(validationType.toUpperCase()){
                case "SEARCHTEXT" :
                      boolean temp = (!items.getDescription().equals(null)
                                   && desc.add(items.getDescription().replaceAll("[^a-zA-Z ]", " "))); break;
                case "SORT" :
                case "SORTASC" :
                    starCnt.add(items.getForksCount());break;
            }
        }
        if(validationType.equalsIgnoreCase("SEARCHTEXT")){
            Assert.assertEquals(context.ifContainsWords(desc,keywords),false,"Validation failed for search text");
        }else if(validationType.equalsIgnoreCase("SORT")){
                context.ifSorted(starCnt);
        }else if(validationType.equalsIgnoreCase("SORTASC")){

        }
    }


    @And("^I should be able to verify that created date is after (.*?)$")
    public void iShouldBeAbleToVerifyCreatedDateIsAfter(String date) throws Throwable {
        this.response = apiClient.getResponse().getRestResponse();
        Repositories repositories = response.as(Repositories.class, ObjectMapperType.GSON);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        Date dateA = formatter.parse(date);
        for(Item items : repositories.getItems()) {
            String createdDate = items.getCreatedAt().split("T")[0];
            Date dateB = formatter.parse(createdDate);
            System.out.println(createdDate + " - Days difference : " + Days.daysBetween(new DateTime(dateA), new DateTime(dateB)).getDays());
            Assert.assertEquals(Days.daysBetween(new DateTime(dateA), new DateTime(dateB)).isGreaterThan(Days.ZERO),
                    true, createdDate + " is less than the expected date");
        }
    }

    @And("^I should be able to verify repository size between (.*?)$")
    public void iShouldBeAbleToVerifyRepositorySizeBetween(String range) throws Throwable {
        this.response = apiClient.getResponse().getRestResponse();
        Repositories repositories = response.as(Repositories.class, ObjectMapperType.GSON);
        int lowerLim = Integer.parseInt(range.split("-")[0]);
        int higherLim = Integer.parseInt(range.split("-")[1]);
        for(Item items : repositories.getItems()) {
            Assert.assertEquals((items.getSize()>=lowerLim)&(items.getSize()<=higherLim),true,"Size validation failed for size : "+items.getSize());
        }
    }

    @And("^I should be able to verify language and star count order$")
    public void iShouldBeAbleToVerifyLanguageAndStarCountOrder() throws Throwable {
        this.response = apiClient.getResponse().getRestResponse();
        Repositories repositories = response.as(Repositories.class, ObjectMapperType.GSON);
        List<String> language = new ArrayList<>();
        List<Integer> stars = new ArrayList<>();
        for(Item items : repositories.getItems()) {
            language.add(items.getLanguage());
            stars.add(items.getStargazersCount());
        }
        assertThat(language,hasItem("COBOL"));
        assertThat(stars, everyItem(greaterThanOrEqualTo(100)));
        List<Integer> temp = new ArrayList<>(stars);
        Collections.sort(stars,Collections.reverseOrder());
        assertThat(temp,is(stars));
    }
}
