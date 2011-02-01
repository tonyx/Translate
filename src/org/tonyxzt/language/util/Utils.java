package org.tonyxzt.language.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 20/12/10
 * Time: 22.51
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

    public static String stripBlock(String toStrip,String badTag) {

//        int indStart = toStrip.indexOf("<"+badTag);
//        if (indStart==-1) return toStrip;
//        int indEnd = toStrip.indexOf("</"+badTag+">");
//        String firstPiece = toStrip.substring(0,indStart);
//        String secondPiece = toStrip.substring(indEnd+("</"+badTag+">").length(),toStrip.length());
//        String toReturn=firstPiece+secondPiece;

        String[] res = toStrip.split("<"+badTag+".[^>]*>|</"+badTag+">");
        String strRes="";
        for (int i =0;i<res.length;i+=2) {
            strRes+=res[i];
        }
        return strRes;
    }
}
