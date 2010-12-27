package com.tonyxzt.language;

import org.apache.commons.httpclient.NameValuePair;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 21/12/10
 * Time: 1.21
 * To change this template use File | Settings | File Templates.
 */
public class GoogleDictionary {
    private static final int INDEX_START_FILTERING = 2;
    public static String flattenAndClean(String[] splitted, String toReturn) {
        String toAdd;
        for (int i=INDEX_START_FILTERING ;i<splitted.length;i++)  {
            toAdd= splitted[i].substring(0,splitted[i].indexOf("</span>"));
            toAdd = Utils.stripBlock(toAdd, "a");
            if (!"".equals(toAdd.trim())) {
                toReturn+=toAdd;
                toReturn+=", ";
            }
        }
        return toReturn;
    }

    public static String lookUp(String ciao, String langIn, String langOut) throws Exception {
        NameValuePair[] params = new NameValuePair[]{new NameValuePair("aq", "f"), new NameValuePair("langpair", langIn+"|"+langOut),
                new NameValuePair("q", ciao), new NameValuePair("hl", "en")};
        String theResult = Utils.lookupTranslationByProviderByGet("http://www.google.com/translate_dict", params);
        Pattern p = Pattern.compile("<span class=\"dct-tt\">|<div class=\"wbtr_cnt\">");
        String splitted[] = p.split(theResult, Pattern.MULTILINE | Pattern.DOTALL);
        String toReturn="";
        toReturn = flattenAndClean(splitted, toReturn);
        return toReturn;
    }
}
