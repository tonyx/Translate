package refactoring.com.tonyxzt.language;

import com.tonyxzt.language.OnLineDictionary;
import com.tonyxzt.language.Translator;

import java.io.FileOutputStream;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
public class RefactoredCommandLineToStatusClassWrapper {

    public void setStatusReadyForTheAction(RefactoredTranslator translator, String[] strIn,Map<String,GenericDictionary> dics)  {
        boolean inlineread=true;
        boolean stdOut = true;
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
                translator.currentDictionary.setOutStream(new FileOutStream(aStrIn.substring(aStrIn.indexOf("=")+1)));
            }
            if (aStrIn.startsWith("--inFile=")) {
                inlineread=false;
                //translator.setReadFromFile(true);
                translator.currentDictionary.setInputStream(new SimpleInputStream(translator.readContentFromFile(aStrIn.substring(aStrIn.indexOf("=")+1)).split("\n")));
                //translator.setInFileName(aStrIn.substring(aStrIn.indexOf("=") + 1));
            }
        }
        if (inlineread&&translator.currentDictionary!=null) {
            translator.currentDictionary.setInputStream(new SimpleInputStream(new String[] {strIn[strIn.length-1]}));
        }

    }
}
