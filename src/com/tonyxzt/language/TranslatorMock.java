package com.tonyxzt.language;

import test.com.tonyxzt.StubbedGHtmlContent;

import java.security.PublicKey;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 22/12/10
 * Time: 1.59
 * To change this template use File | Settings | File Templates.
 */
public class TranslatorMock extends Translator{
    protected String _mockedResultFile;
    protected String _mockedInputFileContent;

//    public TranslatorMock(GoogleDictionary googleDctionary) {
//        this.currentDictionary = googleDctionary;
//    }

    public TranslatorMock(Map<String,OnLineDictionary> mapTranslator) {
        this.commandToTranslator = mapTranslator;
    }


    @Override
    protected void saveToFile(String result,String fileName) {
        _mockedResultFile=result;
    }

    @Override
    protected String readContentFromFile(String fileName) {
        return _mockedInputFileContent;
    }

    public String readMockFile() {
        return _mockedResultFile;
    }

    public void setMockedInputFileContent(String content)  {
        _mockedInputFileContent=content;
    }
}
