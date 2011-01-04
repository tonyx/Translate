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
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;
    OnLineDictionary googleDictionary = new GoogleDictionary();
    OnLineDictionary googleTranslator =new GoogleTranslator();
    CommandLineToStatusClassWrapper commandlineToStatusWrapper = new CommandLineToStatusClassWrapper();
    protected FileIoManager fileIoManager = new FileIoManager();

    protected String _oriLang;
    protected String _targetLang;

    public void setOriLang(String _oriLang) {
        this._oriLang = _oriLang;
    }

    public void setTargetLang(String _targetLang) {
        this._targetLang = _targetLang;
    }

    public Translator() {
        new Translator(new GoogleDictionary());
    }

    public Translator(GoogleDictionary googleDictionary) {
        this.googleDictionary=googleDictionary;
    }

    public Translator(GoogleDictionary googleDictionary, CommandLineToStatusClassWrapper commandlineToStaturWrapper) {
        this.commandlineToStatusWrapper=commandlineToStatusWrapper;
    }


    public void setSaveToFile(boolean _saveToFile) {
        this._saveToFile = _saveToFile;
    }

    protected boolean _saveToFile=false;


    public void setReadFromFile(boolean _readFromFile) {
        this._readFromFile = _readFromFile;
    }

    protected boolean _readFromFile=false;

    public void setFileName(String _fileName) {
        this._fileName = _fileName;
    }

    protected String _fileName;

    public void setInFileName(String _inFileName) {
        this._inFileName = _inFileName;
    }

    protected String _inFileName;


    public TranslationMode getMode() {
        return _mode;
    }

    public String getOriLang() {
        return _oriLang;
    }

    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};

    public void setMode(TranslationMode _mode) {
        this._mode = _mode;
    }


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
        return "usage: gtranslate [--languages] [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] [--inFile=infile] [--outFile=outfile] [word|\"any words\"]";
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


