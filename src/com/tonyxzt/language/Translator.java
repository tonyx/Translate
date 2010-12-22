package com.tonyxzt.language;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.xml.utils.StringToStringTableVector;

import javax.swing.text.html.parser.Parser;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/12/10
 * Time: 19.51
 * To change this template use File | Settings | File Templates.
 */
public class Translator {
    protected String _oriLang;
    protected String _targetLang;

    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;

    public Translator()  {
    }

    public Translator(TranslationMode mode) {
        _mode=mode;
    }

    public static void main(String[] inLine) {
        Translator translate = new Translator();
        System.out.println(translate.wrapCommandLineParameters(inLine));
    }

    public String wrapCommandLineParameters(String[] strIn)  {
        if (strIn.length==0|| "--help".equals(strIn[0])) {
            return helpCommand();
        }
        if ("--languages".equals(strIn[0])) {
            return validLanguages();
        }
        for (int i=0;i<strIn.length-1;i++) {
            if ("--gApi".equals(strIn[i])) {
                this._mode=Translator.TranslationMode.USES_ONLY_API;
            }
            if ("--gDic".equals(strIn[i])) {
                this._mode=Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING;
            }
            if (strIn[i].startsWith("--oriLang=")) {
                this._oriLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--targetLang=")) {
                this._targetLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
        }
        try {
            return translate(strIn[strIn.length-1]);
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public String helpCommand() {
        return "usage: gtranslate [--languages] [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";
    }

    public String validLanguages() {
        String toReturn = "";
        Field[] langField = Language.class.getDeclaredFields();
        for (Field f:langField) {
            if ("String".equals(f.getType().getSimpleName())) {
                toReturn+=f.getName().toLowerCase();
                String shortDesc="";
                try {
                     shortDesc = (String)f.get(f);
                } catch (IllegalAccessException e) {
                }
                toReturn=toReturn+"\t";
                toReturn=toReturn+shortDesc+"\n";
            }
        }
        return toReturn;
    }

    @Deprecated
    public String translate(String word,String langIn,String langOut) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(word, langIn, langOut );
            case USES_DICTIONARY_BY_SCRAPING:
                return GoogleDictionary.lookUp(word, langIn, langOut);
            default:
                return Translate.translate(word, langIn, langOut);
        }
    }

    public String translate(String word) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(word,this._oriLang, this._targetLang );
            case USES_DICTIONARY_BY_SCRAPING:
                return GoogleDictionary.lookUp(word, this._oriLang, this._targetLang);
            default:
                return Translate.translate(word, this._oriLang, this._targetLang);
        }
    }

}



