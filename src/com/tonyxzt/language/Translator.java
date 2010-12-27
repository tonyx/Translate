package com.tonyxzt.language;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.xml.utils.StringToStringTableVector;

import javax.swing.text.html.parser.Parser;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

    public enum TranslationMode { USES_ONLY_API,USES_DICTIONARY_BY_SCRAPING};
    TranslationMode _mode = Translator.TranslationMode.USES_ONLY_API;

    public Translator()  {
    }

    public Translator(TranslationMode mode) {
        _mode=mode;
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
        for (int i=0;i<strIn.length;i++) {
            if ("--gApi".equals(strIn[i])) {
                this._mode=Translator.TranslationMode.USES_ONLY_API;
            }
            if ("--gDic".equals(strIn[i])) {
                this._mode=Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING;
            }
            if (strIn[i].startsWith("--oriLang=")) {
                this._oriLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--targetLang=")) {
                this._targetLang=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--outFile=")) {
                this._saveToFile=true;
                this._fileName=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
            if (strIn[i].startsWith("--inFile=")) {
                this._readFromFile=true;
                this._inFileName=strIn[i].substring(strIn[i].indexOf("=")+1);
            }
        }

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
            return toReturn;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    protected String readContentFromFile(String fileName) {
        String toReturn="";
        File file = new File(fileName);
        try {
            FileInputStream fileIn = new FileInputStream(file);
            byte[] inBytes = new byte[1024];

            while (fileIn.read(inBytes)!=-1) {
                fileIn.read(inBytes);
                String strReaden = new String(inBytes,"UTF-8");
                toReturn+=strReaden;
            }
            fileIn.close();
            toReturn = toReturn.trim();
            return toReturn;
        }
        catch (FileNotFoundException fe) {
        }
        catch (IOException fe) {
        }
        return "";
    }


    protected void saveToFile(String result,String fileName) {
        Exception ex=null;
        File file = new File(fileName);
        try {
            FileOutputStream out = new FileOutputStream(file,true);
            out.write(result.getBytes("UTF-8"));
            out.write("\n".getBytes());
            out.close();
        }
        catch (FileNotFoundException fo) {
            ex=fo;
        }
        catch (UnsupportedEncodingException uo) {
            ex=uo;
        }
        catch (IOException io) {
            ex=io;
        }
        if (ex!=null)
            System.out.println(ex.getMessage());
    }


    public String helpCommand() {
        return "usage: gtranslate [--languages] [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";
    }

    public String validLanguages() {
        String toReturn = "";
        Field[] langField = Language.class.getDeclaredFields();
        for (Field f:langField) {
            if ("String".equals(f.getType().getSimpleName())) {
                toReturn+=f.getName().toLowerCase();
                String shortDesc="";
                try {
                     shortDesc = (String)f.get(f);
                } catch (IllegalAccessException e) {
                }
                toReturn=toReturn+"\t";
                toReturn=toReturn+shortDesc+"\n";
            }
        }
        return toReturn;
    }

    @Deprecated
    public String translate(String word,String langIn,String langOut) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(word, langIn, langOut );
            case USES_DICTIONARY_BY_SCRAPING:
                return GoogleDictionary.lookUp(word, langIn, langOut);
            default:
                return Translate.translate(word, langIn, langOut);
        }
    }

    public String translate(String word) throws Exception {
        switch (_mode) {
            case USES_ONLY_API:
                return Translate.translate(word,this._oriLang, this._targetLang );
            case USES_DICTIONARY_BY_SCRAPING:
                return GoogleDictionary.lookUp(word, this._oriLang, this._targetLang);
            default:
                return Translate.translate(word, this._oriLang, this._targetLang);
        }
    }
}



