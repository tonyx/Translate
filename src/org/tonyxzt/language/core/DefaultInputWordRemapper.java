package org.tonyxzt.language.core;

/**
 * Created with IntelliJ IDEA.
 * User: tonyx
 * Date: 06/08/12
 * Time: 18.05
 * To change this template use File | Settings | File Templates.
 */
public class DefaultInputWordRemapper implements InputWordRemapper {
    @Override
    public String remappedInputWord(String inWord, String rawRetrievedContent) {
        return inWord;
    }
}
