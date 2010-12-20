package com.tonyxzt.language;

import com.google.api.translate.Translate;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/12/10
 * Time: 19.51
 * To change this template use File | Settings | File Templates.
 */
public class Translator {
    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};

    private String _inLang ="";
    private String _outLang ="";
    public Translator(String inLang, String outLang) {
        _inLang=inLang;
        _outLang = outLang;
    }

    public Translator(String inLang, String outLang,boolean mode) {
        _inLang=inLang;
        _outLang = outLang;
    }

    public String translate(String ciao) throws Exception {
         return Translate.translate(ciao,_inLang,_outLang);
    }
}



