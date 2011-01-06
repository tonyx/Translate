package com.tonyxzt.language;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
public class CommandLineToStatusClassWrapper {
    public void setStatusReadyForTheAction(Translator translator, String[] strIn,Map<String,OnLineDictionary> dics)  {
        for (String aStrIn : strIn) {
            if (aStrIn.startsWith("--dic")) {
                translator.setCurrentDictionary(dics.get(aStrIn.substring(aStrIn.indexOf("=") + 1)));
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
