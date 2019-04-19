package com.git.qa.zomatoapis;



import com.git.qa.common.api.ApiClient;
import com.git.qa.common.api.ApiConfiguration;

import java.util.*;

public class GITContext {
    private static ThreadLocal<ApiClient> apiClients = new ThreadLocal();
    private Object requestObject;

    public GITContext() throws Exception {
        apiClients.set(new ApiClient(new ApiConfiguration()));
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(String key, String value) {
        this.params.put(key,value);
    }

    public void clearParams(){
        if(!this.params.isEmpty())
            this.params.clear();
    }
    private HashMap<String,String> params = new HashMap<>();

    public static ApiClient getApiClient() {
        return (ApiClient)apiClients.get();
    }

    public static void setApiClient(ApiClient aApiClient) {
        apiClients.set(aApiClient);
    }

    public void setRequestObject(Object request) {
        this.requestObject = request;
    }

    public Object getRequestObject() {
        return this.requestObject;
    }

    public boolean ifContainsWords(List<String> inputStringList, String[] words) {
        List<Boolean> results =  new ArrayList<>();
        List<String> wordsList = Arrays.asList(words);
        for(String inputString : inputStringList){
            List<String> inputWordList = Arrays.asList(inputString.split(" "));
            results.add(wordsList.stream().allMatch(inputWordList::contains));
        }
        return results.contains(false);
    }

    public boolean ifSorted(List<Integer> starCnt,String... order) {
        List<Integer> tempDefDesc = starCnt;
        Collections.sort(starCnt);
        return tempDefDesc.equals(starCnt);
    }
}
