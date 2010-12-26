package com.tonyxzt.language;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
public class CommandLineToStatusClassWrapper {
     public void setStatusReadyForTheAction(Translator translator, String[] strIn)  {
         for (String aStrIn : strIn) {
             if ("--gApi".equals(aStrIn)) {
                 translator._mode = Translator.TranslationMode.USES_ONLY_API;
             }
             if ("--gDic".equals(aStrIn)) {
                 translator._mode = Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING;
             }
             if (aStrIn.startsWith("--oriLang=")) {
                 translator._oriLang = aStrIn.substring(aStrIn.indexOf("=") + 1);
             }
             if (aStrIn.startsWith("--targetLang=")) {
                 translator._targetLang = aStrIn.substring(aStrIn.indexOf("=") + 1);
             }
             if (aStrIn.startsWith("--outFile=")) {
                 translator._saveToFile = true;
                 translator._fileName = aStrIn.substring(aStrIn.indexOf("=") + 1);
             }
             if (aStrIn.startsWith("--inFile=")) {
                 translator._readFromFile = true;
                 translator._inFileName = aStrIn.substring(aStrIn.indexOf("=") + 1);
             }
         }
    }
}
