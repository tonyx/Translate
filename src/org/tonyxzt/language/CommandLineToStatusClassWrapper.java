package org.tonyxzt.language;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
public class CommandLineToStatusClassWrapper {
    public void setStatusReadyForTheAction(Translator translator, String[] strIn,Map<String,GenericDictionary> dics)  {

        translator.setOutStream(new OutStream(){
            public void output(String out) {
                System.out.print(out);
            }
            public void close() {
            }
        });

        if (strIn!=null&&strIn.length>0) {
            translator.setInputStream(new SimpleInputStream(new String[] {strIn[strIn.length-1]}));
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
                    translator.setOutStream(new FileOutStream(aStrIn.substring(aStrIn.indexOf("=")+1)));
                }
                if (aStrIn.startsWith("--inFile=")) {
                    //translator.setInputStream(new SimpleInputStream(translator.readContentFromFile(aStrIn.substring(aStrIn.indexOf("=")+1)).split("\n")));
                    translator.setInputStream(new SimpleInputStream(new FileIoManager().readContentFromFile(aStrIn.substring(aStrIn.indexOf("=")+1)).split("\n")));
                }
            }
        }
    }
}
