package com.tonyxzt.language;
import com.google.api.translate.Translate;
import org.apache.commons.httpclient.NameValuePair;

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
    private static Map commandSwitchToTranslatorMode = new HashMap<String,TranslationMode>();

    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;
    static {
        commandSwitchToTranslatorMode.put("--gApi",TranslationMode.USES_ONLY_API);
        commandSwitchToTranslatorMode.put("--gDic",TranslationMode.USES_DICTIONARY_BY_SCRAPING );
    }
    public Translator()  {
    }
    public Translator(TranslationMode mode) {
        _mode=mode;
    }
    public static void main(String[] inLine) {
        if ("--help".equals(inLine[2]))
            System.out.println("help");
    }

    public String translate(String ciao,String langIn,String langOut) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(ciao, langIn, langOut );
            case USES_DICTIONARY_BY_SCRAPING:
                return GoogleDictionary.lookUp(ciao, langIn, langOut);
            default:
                return Translate.translate(ciao, langIn, langOut);
        }
    }
    public static String parseCommandLine(String[] params) {
        return "help";
    }
}
