package com.tonyxzt.language;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 16.16
 * To change this template use File | Settings | File Templates.
 */
public interface OnLineDictionary {
    public  String lookUp(String word, String langIn, String langOut) throws Exception;
}
