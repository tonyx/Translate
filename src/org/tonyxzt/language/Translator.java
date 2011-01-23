package org.tonyxzt.language;
import com.google.api.translate.Language;
import com.tonyxzt.language.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/12/10
 * Time: 19.51
 * To change this template use File | Settings | File Templates.
 */
public class Translator {
    GenericDictionary currentDictionary;
    Map<String,GenericDictionary> commandToTranslator;
    RefactoredCommandLineToStatusClassWrapper commandlineToStatusWrapper = new RefactoredCommandLineToStatusClassWrapper();
    protected RefactoredFileIoManager fileIoManager = new RefactoredFileIoManager();
    protected String _oriLang;
    protected String _targetLang;
    InputStream inputStream;
    OutStream outStream;

    public static void main(String[] inLine) {
        Map<String,GenericDictionary> mapDictionaries = new HashMap<String,GenericDictionary>(){
//            {put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
//             put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilterIdentity()));
//            }
        };

        try {
            mapDictionaries.putAll(new ImplementationInjectorFromFile("conf/providers.txt").getMap());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }

        Translator translate = new Translator(mapDictionaries);
        translate.wrapCommandLineParameters(inLine);
        translate.doAction(inLine);
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream=inputStream;
    }

    public void setOutStream(OutStream outStream) {
        this.outStream=outStream;
    }
    public void setCurrentDictionary(GenericDictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    public void setOriLang(String _oriLang) {
        this._oriLang = _oriLang;
    }

    public void setTargetLang(String _targetLang) {
        this._targetLang = _targetLang;
    }

    public Translator(Map<String, GenericDictionary> mapTranslator) {
        this.commandToTranslator = mapTranslator;
    }

    public Translator() {
        //new Translator(new GoogleDictionary());
    }


    public String getOriLang() {
        return _oriLang;
    }

    public void wrapCommandLineParameters(String[] strIn)  {
        commandlineToStatusWrapper.setStatusReadyForTheAction(this,strIn,this.commandToTranslator);
        //return doAction(strIn);
    }


    public void doAction(String[] strIn) {
        if (strIn.length==0|| "--help".equals(strIn[0])) {
            outStream.output(helpCommand());
            return;
        }
        if ("--languages".equals(strIn[0])) {
            outStream.output(validLanguages());
        }
        try {
            String toReturn="";
            String content;
            while ((content = inputStream.next())!=null) {
                String translated = translate(content);
                toReturn+=translated;
                toReturn +="\n";
                outStream.output(content+" = "+translated);
            }
        } catch (Exception e) {
            outStream.output(e.getMessage());
        }
    }

//    protected String readContentFromFile(String fileName) {
//         return fileIoManager.readContentFromFile(fileName);
//    }

    public String helpCommand() {
        return "usage: gtranslate [--languages] [--dic=gApi|--dic=gDic] [--oriLang=orilang] [--targetLang=targetlang] [--inFile=infile] [--outFile=outfile] [word|\"any words\"]";
    }

    public String validLanguages() {
        return new FieldsInspector().fieldsAnValues(Language.class);
    }

    public String translate(String word) throws Exception {
        return this.currentDictionary.lookUp(word,this._oriLang,this._targetLang);
    }
}


