package org.tonyxzt.language.core;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 12.37
 * To change this template use File | Settings | File Templates.
 */
public interface ContentFilter {
    String filter(String content,String langIn, String langOut);
}
