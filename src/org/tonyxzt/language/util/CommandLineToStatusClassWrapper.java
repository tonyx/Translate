package org.tonyxzt.language.util;

import org.tonyxzt.language.core.GenericDictionary;
import org.tonyxzt.language.io.*;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 12.42
 * To change this template use File | Settings | File Templates.
 */
public class CommandLineToStatusClassWrapper {
    private GenericDictionary _genericDictionary;
    private String _oriLang;
    private String _targetLang;
    private OutStream _outStream;
    private InputStream _inputStream;
    private int _delay;

    public String[] getStrIn() {
        return strIn;
    }

    private String[] strIn;
    private Map<String, GenericDictionary> dics;

    public CommandLineToStatusClassWrapper(String[] strIn, Map<String, GenericDictionary> dics, OutStream outStream) {
        this.strIn = strIn;
        this.dics = dics;
        this._outStream=outStream;
        setStatusReadyForTheAction(strIn);
    }


    public void setStatusReadyForTheAction(String[] strIn)  {
        if (strIn!=null&&strIn.length>0) {
            this.setInputStream(new SimpleInputStream(new String[]{strIn[strIn.length - 1]}));
            for (String aStrIn : strIn) {
                if (aStrIn.startsWith("--dic=")) {
                    this.setCurrentDictionary(dics.get(aStrIn.substring(aStrIn.indexOf("=") + 1)));
                }
                if (aStrIn.startsWith("--oriLang=")) {
                    this.setOriLang(aStrIn.substring(aStrIn.indexOf("=") + 1));
                }
                if (aStrIn.startsWith("--targetLang=")) {
                    this.setTargetLang(aStrIn.substring(aStrIn.indexOf("=") + 1));
                }
                if (aStrIn.startsWith("--outFile=")) {
                    System.out.println("setting out stream "+aStrIn.substring(aStrIn.indexOf("=")+1));
                    this.setOutStream(new FileOutStream(aStrIn.substring(aStrIn.indexOf("=") + 1)));
                }
                if (aStrIn.startsWith("--inFile=")) {
                    this.setInputStream(new SimpleInputStream(new FileIoManager().readContentFromFile(aStrIn.substring(aStrIn.indexOf("=") + 1)).split("\n")));
                }
                if (aStrIn.startsWith("--delay=")) {
                    this.setDelay(aStrIn.substring(aStrIn.indexOf("=") + 1));
                }
            }
        }
    }

    private void setInputStream(InputStream simpleInputStream) {
        this._inputStream = simpleInputStream;
    }

    private void setOutStream(OutStream outStream) {
        this._outStream = outStream;
    }

    private void setTargetLang(String targetLang) {
        this._targetLang= targetLang;
    }

    private void setOriLang(String oriLang) {
        this._oriLang=oriLang;
    }

    private void setDelay(String _delay) {
        try {
            this._delay = Integer.parseInt(_delay);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error number format for _delay parameter "+ _delay);
        }
    }

    public int getDelay() {
        return this._delay;
    }

    private void setCurrentDictionary(GenericDictionary genericDictionary) {
        this._genericDictionary=genericDictionary;
    }

    public InputStream getInputStream() {
        return _inputStream;
    }

    public OutStream getOutStream() {
        return _outStream;
    }

    public String getTargetLang() {
        return _targetLang;
    }

    public String getOriLang() {
        return _oriLang;
    }

    public GenericDictionary getGenericDictionary() {
        return _genericDictionary;
    }
}
