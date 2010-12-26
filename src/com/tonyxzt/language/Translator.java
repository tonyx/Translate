package com.tonyxzt.language;
import com.google.api.translate.Language;

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
    protected boolean _saveToFile=false;
    protected boolean _readFromFile=false;
    protected String _fileName;
    protected String _inFileName;

    protected FileIoManager fileIoManager = new FileIoManager();
    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;
    OnLineDictionary googleDictionary= new GoogleDictionary();
    OnLineDictionary googleTranslator=new GoogleTranslator();
    CommandLineToStatusClassWrapper commandlineToStatusWrapper = new CommandLineToStatusClassWrapper();

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
        commandlineToStatusWrapper.setStatusReadyForTheAction(this,strIn);
        return doAction(strIn);
    }

    private String doAction(String[] strIn) {
        try {
            String toReturn="";
            String toBeTranslated= (_readFromFile? readContentFromFile(_inFileName):strIn[strIn.length-1]);
            String splitted[] = toBeTranslated.split("\n");
            for (String content : splitted) {
                String translated = translate(content);
                toReturn+=translated;
                toReturn+="\n";
                if (_saveToFile) {
                    saveToFile(content.trim()+ " = "+translated,_fileName);
                }
            }
            return toReturn.trim();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    protected String readContentFromFile(String fileName) {
         return fileIoManager.readContentFromFile(fileName);
    }

    protected void saveToFile(String result,String fileName) {
         fileIoManager.saveToFile(result, fileName);
    }

    public String helpCommand() {
        return "usage: gtranslate [--languages] [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";
    }

    public String validLanguages() {
        return new FieldsInspector().fieldsAnValues(Language.class);
    }

    public String translate(String word) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return this.googleTranslator.lookUp(word,this._oriLang, this._targetLang );
            case USES_DICTIONARY_BY_SCRAPING:
                return this.googleDictionary.lookUp(word, this._oriLang, this._targetLang);
            default:
                return this.googleTranslator.lookUp(word,this._oriLang, this._targetLang );
        }
    }
}


