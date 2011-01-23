package org.tonyxzt.language;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 13.34
 * To change this template use File | Settings | File Templates.
 */
public class GDicContentFilter implements ContentFilter {

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
            if (!"\n".equals(toAdd.replaceAll(" ",""))) {
                toReturn+=toAdd;
                toReturn+=", ";
            }
        }
        return toReturn.replaceAll("<[^>]*>","");
    }
}
