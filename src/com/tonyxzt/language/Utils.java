package com.tonyxzt.language;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 20/12/10
 * Time: 22.51
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static String stripBlock(String toStrip,String badTag) {
        int indStart = toStrip.indexOf("<"+badTag);
        if (indStart==-1) return toStrip;
        int indEnd = toStrip.indexOf("</"+badTag+">");
        String firstPiece = toStrip.substring(0,indStart);
        String secondPiece = toStrip.substring(indEnd+("</"+badTag+">").length(),toStrip.length());
        String toReturn=firstPiece+secondPiece;
        return stripBlock(toReturn,badTag);
    }

}
