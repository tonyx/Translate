package org.tonyxzt.language;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 13.01
 * To change this template use File | Settings | File Templates.
 */
public class GDicProvider implements ContentProvider {

    public String retrieve(String word, String langIn, String langOut) throws Exception {

        NameValuePair[] params = new NameValuePair[]{new NameValuePair("aq", "f"), new NameValuePair("langpair", langIn+"|"+langOut),
        new NameValuePair("q", word), new NameValuePair("hl", "en")};

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://www.google.com/translate_dict");
        method.setQueryString(params);

        client.executeMethod(method) ;
        return method.getResponseBodyAsString();
    }

    public String supportedLanguges() {
        return "";
    }


//      public String retrieve(String word, String langIn, String langOut) throws Exception {
//
//        URL url = new URL("http://www.google.com/translate_dict?aq=f&langpair="+langIn+"|"+langOut+"&"+"q="+word+"&"+"hl=en");
//
//
//        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//        String inputLine;
//        String toReturn = "";
//        while ((inputLine = in.readLine())!=null) {
//            toReturn+=inputLine;
//        }
//        in.close();
//        return toReturn.trim();
//    }
}

