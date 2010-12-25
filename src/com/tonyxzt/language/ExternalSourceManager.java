package com.tonyxzt.language;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 14.35
 * To change this template use File | Settings | File Templates.
 */
public class ExternalSourceManager {
     public String lookupTranslationByProviderByGet(String url, NameValuePair[] queryString) throws Exception {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        method.setQueryString(queryString);
        client.executeMethod(method) ;
        return method.getResponseBodyAsString();
    }
}
