package com.tonyxzt.language;

import org.apache.commons.httpclient.NameValuePair;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 15.35
 * To change this template use File | Settings | File Templates.
 */
public class GoogleDictionaryFilterAndCleanContent implements FilterAndCleanContent {
    private static final int INDEX_START_FILTERING = 2;
    public String filter(String theResult) {

        Pattern p = Pattern.compile("<span class=\"dct-tt\">|<div class=\"wbtr_cnt\">");
        String splitted[] = p.split(theResult, Pattern.MULTILINE | Pattern.DOTALL);
        String toReturn="";
        toReturn = flattenAndClean(splitted, toReturn);
        return toReturn;
    }

    public String flattenAndClean(String[] splitted, String toReturn) {
        String toAdd;
        for (int i=INDEX_START_FILTERING ;i<splitted.length;i++)  {
            toAdd= splitted[i].substring(0,splitted[i].indexOf("</span>"));
            toAdd = Utils.stripBlock(toAdd, "a");
            toReturn+=toAdd;
            toReturn+=", ";
        }
        return toReturn;
    }
}
