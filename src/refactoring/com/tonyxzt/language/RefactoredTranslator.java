package refactoring.com.tonyxzt.language;
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
public class RefactoredTranslator {
    GenericDictionary currentDictionary;
    Map<String,GenericDictionary> commandToTranslator;
    RefactoredCommandLineToStatusClassWrapper commandlineToStatusWrapper = new RefactoredCommandLineToStatusClassWrapper();
    protected RefactoredFileIoManager fileIoManager = new RefactoredFileIoManager();
    protected String _oriLang;
    protected String _targetLang;
    protected boolean _saveToFile=false;
    protected boolean _readFromFile=false;
    protected String _inFileName;
    protected String _fileName;

    public static void main(String[] inLine) {
        Map<String,GenericDictionary> mapDictionaries = new HashMap<String,GenericDictionary>(){
            {put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
             put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}}));
        }
        };
        RefactoredTranslator translate = new RefactoredTranslator(mapDictionaries);
        translate.wrapCommandLineParameters(inLine);
        System.out.println(translate.doAction(inLine));
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

    public RefactoredTranslator(Map<String, GenericDictionary> mapTranslator) {
        this.commandToTranslator = mapTranslator;
    }

    public RefactoredTranslator() {
        //new RefactoredTranslator(new GoogleDictionary());
    }

    public void setSaveToFile(boolean _saveToFile) {
        this._saveToFile = _saveToFile;
    }

    public void setReadFromFile(boolean _readFromFile) {
        this._readFromFile = _readFromFile;
    }

    public void setFileName(String _fileName) {
        this._fileName = _fileName;
    }

    public void setInFileName(String _inFileName) {
        this._inFileName = _inFileName;
    }

    public String getOriLang() {
        return _oriLang;
    }

    public void wrapCommandLineParameters(String[] strIn)  {
        commandlineToStatusWrapper.setStatusReadyForTheAction(this,strIn,this.commandToTranslator);
        //return doAction(strIn);
    }


    public String doAction(String[] strIn) {
        if (strIn.length==0|| "--help".equals(strIn[0])) {
            return helpCommand();
        }
        if ("--languages".equals(strIn[0])) {
            return validLanguages();
        }
        try {
            String toReturn="";

//            if (!_readFromFile)
//            {
//                currentDictionary.setInputStream(new SimpleInputStream(new String[]{strIn[strIn.length-1]}));
//            } else {
//                currentDictionary.setInputStream(new SimpleInputStream(readContentFromFile(_inFileName).split("\n")));
//            }
            String content;
            while ((content = currentDictionary.inputStream.next())!=null) {
                String translated = translate(content);
                toReturn+=translated;
                toReturn +="\n";
                if (_saveToFile) {
                    saveToFile(content.trim()+ " = "+translated,_fileName);
                }
            }
            return toReturn.trim();
        } catch (Exception e) {
            return e.getMessage();
        }
    }


//    private String doAction(String[] strIn) {
//        try {
//            String toReturn="";
//            String toBeTranslated= (_readFromFile? readContentFromFile(_inFileName):strIn[strIn.length-1]);
//            String splitted[] = toBeTranslated.split("\n");
//            for (String content : splitted) {
//                String translated = translate(content);
//                toReturn+=translated;
//                toReturn+="\n";
//                if (_saveToFile) {
//                    saveToFile(content.trim()+ " = "+translated,_fileName);
//                }
//            }
//            return toReturn.trim();
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }

    protected String readContentFromFile(String fileName) {
         return fileIoManager.readContentFromFile(fileName);
    }

    protected void saveToFile(String result,String fileName) {
         fileIoManager.saveToFile(result, fileName);
    }

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


