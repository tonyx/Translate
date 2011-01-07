package com.tonyxzt.language;

import com.google.api.translate.Translate;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 16.23
 * To change this template use File | Settings | File Templates.
 */
public class GoogleTranslator implements OnLineDictionary {
    public String lookUp(String word, String langIn, String langOut) throws Exception {
        return Translate.translate(word, langIn, langOut);
    }
}
