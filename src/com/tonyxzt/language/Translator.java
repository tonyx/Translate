package com.tonyxzt.language;
import com.google.api.translate.Translate;
import org.apache.commons.httpclient.NameValuePair;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/12/10
 * Time: 19.51
 * To change this template use File | Settings | File Templates.
 */
public class Translator {
    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;

    private String _inLang ="";
    private String _outLang ="";
    public Translator(String inLang, String outLang) {
        _inLang=inLang;
        _outLang = outLang;
    }

    public Translator(String inLang, String outLang,TranslationMode mode) {
        _mode=mode;
        _inLang=inLang;
        _outLang = outLang;
    }

    public String translate(String ciao) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(ciao, _inLang, _outLang);

            case USES_DICTIONARY_BY_SCRAPING:
                NameValuePair[] params = new NameValuePair[]{new NameValuePair("aq", "f"), new NameValuePair("langpair", _inLang+"|"+_outLang),
                            new NameValuePair("q", ciao), new NameValuePair("hl", "en")};
                String theResult = Utils.lookupTranslationByProviderByGet("http://www.google.com/translate_dict", params);
                Pattern p = Pattern.compile("<span class=\"dct-tt\">|<div class=\"wbtr_cnt\">");
                String splitted[] = p.split(theResult, Pattern.MULTILINE | Pattern.DOTALL);
                String toReturn="";
                String toAdd="";
                for (int i=2;i<splitted.length;i++)  {
                    toAdd= splitted[i].substring(0,splitted[i].indexOf("</span>"));
                    toAdd = Utils.stripBlock(toAdd,"a");
                    toReturn+=toAdd;
                    toReturn+=", ";
                }
                return toReturn;
            default:
                return Translate.translate(ciao, _inLang, _outLang);
        }
    }
}
