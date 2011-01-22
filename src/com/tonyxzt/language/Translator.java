package com.tonyxzt.language;
import com.google.api.translate.Language;

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
    OnLineDictionary currentDictionary;
    Map<String,OnLineDictionary> commandToTranslator;
    CommandLineToStatusClassWrapper commandlineToStatusWrapper = new CommandLineToStatusClassWrapper();
    protected FileIoManager fileIoManager = new FileIoManager();
    protected String _oriLang;
    protected String _targetLang;
    protected boolean _saveToFile=false;
    protected boolean _readFromFile=false;
    protected String _inFileName;
    protected String _fileName;
    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};

    public static void main(String[] inLine) {
        Map<String,OnLineDictionary> mapDictionaries = new HashMap<String,OnLineDictionary>(){
               {put("gDic",new GoogleDictionary());
                put("gApi",new GoogleTranslator());}
        };
        Translator translate = new Translator(mapDictionaries);
        System.out.println(translate.wrapCommandLineParameters(inLine));
    }

    public void setCurrentDictionary(OnLineDictionary currentDictionary) {
        this.currentDictionary = currentDictionary;
    }

    public void setOriLang(String _oriLang) {
        this._oriLang = _oriLang;
    }

    public void setTargetLang(String _targetLang) {
        this._targetLang = _targetLang;
    }

    public Translator(Map<String,OnLineDictionary> mapTranslator) {
        this.commandToTranslator = mapTranslator;
    }

    public Translator() {
        //new Translator(new GoogleDictionary());
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

    public String wrapCommandLineParameters(String[] strIn)  {
        if (strIn.length==0|| "--help".equals(strIn[0])) {
            return helpCommand();
        }
        if ("--languages".equals(strIn[0])) {
            return validLanguages();
        }
        commandlineToStatusWrapper.setStatusReadyForTheAction(this,strIn,this.commandToTranslator);
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
        return "usage: gtranslate [--languages] [--dic=gApi|--dic=gDic] [--oriLang=orilang] [--targetLang=targetlang] [--inFile=infile] [--outFile=outfile] [word|\"any words\"]";
    }

    public String validLanguages() {
        return new FieldsInspector().fieldsAnValues(Language.class);
    }

    public String translate(String word) throws Exception {
        return this.currentDictionary.lookUp(word,this._oriLang,this._targetLang);
    }
}
