package com.tonyxzt.language;

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
    public TranslationMode getMode() {
        return _mode;
    }

    public String getOriLang() {
        return _oriLang;
    }

    @Override
    protected void saveToFile(String result,String fileName) {
        _mockedResultFile=result;
    }

    public String readMockFile() {
        return _mockedResultFile;
    }

    public void setMockedInputFileContent(String content)  {
        _mockedInputFileContent=content;
    }
}
