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
    protected ExternalSourceManager _externalSourceManager;

    public TranslatorMock() {
        super();
    }

    public TranslatorMock(ExternalSourceManager externalSourceManager) {
        googleDictionary = new GoogleDictionaryMock(externalSourceManager);
        //this._externalSourceManager=externalSourceManager;
        //To change body of created methods use File | Settings | File Templates.
    }

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
