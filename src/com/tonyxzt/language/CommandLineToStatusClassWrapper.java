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
                 translator.setMode(Translator.TranslationMode.USES_ONLY_API);
             }
             if ("--gDic".equals(aStrIn)) {
                 translator.setMode(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
             }
             if (aStrIn.startsWith("--oriLang=")) {
                 translator.setOriLang( aStrIn.substring(aStrIn.indexOf("=") + 1));
             }
             if (aStrIn.startsWith("--targetLang=")) {
                 translator.setTargetLang(aStrIn.substring(aStrIn.indexOf("=") + 1));
             }
             if (aStrIn.startsWith("--outFile=")) {
                 translator.setSaveToFile(true);
                 translator.setFileName(aStrIn.substring(aStrIn.indexOf("=") + 1));
             }
             if (aStrIn.startsWith("--inFile=")) {
                 translator.setReadFromFile(true);
                 translator.setInFileName(aStrIn.substring(aStrIn.indexOf("=") + 1));
             }
         }
    }
}
