package org.tonyxzt.language.core;

/**
 * Created with IntelliJ IDEA.
 * User: tonyx
 * Date: 06/08/12
 * Time: 18.00
 * To change this template use File | Settings | File Templates.
 */
public interface InputWordRemapper {
    public String remappedInputWord(String inWord, String rawRetrievedContent);
}
