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
        for (int i=0;i<strIn.length;i++) {
            if ("--gApi".equals(strIn[i])) {
                translator._mode= Translator.TranslationMode.USES_ONLY_API;
            }
            if ("--gDic".equals(strIn[i])) {
                translator._mode= Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING;
            }
            if (strIn[i].startsWith("--oriLang=")) {
                translator._oriLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--targetLang=")) {
                translator._targetLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--outFile=")) {
                translator._saveToFile=true;
                translator._fileName=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--inFile=")) {
                translator._readFromFile=true;
                translator._inFileName=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
        }
    }
}
