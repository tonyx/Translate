package test.com.tonyxzt;

import com.tonyxzt.language.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 18.40
 *  * To change this template use File | Settings | File Templates.
 */
public class TranslatorTest {

    Map<String,OnLineDictionary> mapDictionaries;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,OnLineDictionary>(){
               {
                    put("gDic",new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
                    put("gApi",new GoogleTranslator());
               }
        };
    }

//    @Test
//    public void canHandleDictionaryParameter() throws Exception {
//        Translator translator = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
//        translator.wrapCommandLineParameters(new String[]{"--gDic","ciao"});
//        Assert.assertEquals(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING, translator.getMode());
//    }

    @Test
    public void canGetThePlainLanguageName() throws Exception {
        Translator translator = new TranslatorMock(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
        String returned = translator.wrapCommandLineParameters(new String[]{"--languages"});
        Assert.assertTrue("extend languages description is not contained",returned.toLowerCase().contains("italian"));
    }

    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        TranslatorMock translator = new TranslatorMock(mapDictionaries);

        translator.setMockedInputFileContent("hi\nhi\n");
        String returned = translator.wrapCommandLineParameters(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        Assert.assertTrue(returned.contains("salut!"));
        Assert.assertTrue(returned.contains("\n"));
    }


    @Test
    public void canReadFromInputFile() throws Exception {
        TranslatorMock translator = new TranslatorMock(mapDictionaries);
        translator.setMockedInputFileContent("hi");
        String returned = translator.wrapCommandLineParameters(new String[] {"--dic=gDic","--oriLang=it","--targetLang=en","--inFile=infile"});
        Assert.assertTrue(returned.contains("salut"));
    }


    @Test
    public void manageOutputFile() throws Exception {
        TranslatorMock translator = new TranslatorMock(mapDictionaries);
        translator.wrapCommandLineParameters(new String[] {"--dic=gApi","--oriLang=it","--targetLang=en","--outFile=out","ciao"});
        Assert.assertTrue(translator.readMockFile().contains("hello"));
    }


    @Test
    public void canUseMockedGoogleHtmlProvider() throws Exception {

        TranslatorMock translatorWithMockedSources = new TranslatorMock(mapDictionaries);

        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"});
        Assert.assertTrue(returned.contains("salut"));
        Assert.assertTrue(returned.contains("bonjour"));
    }

    @Test
    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
        TranslatorMock translatorWithMockedSources = new TranslatorMock(mapDictionaries);
        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
        Assert.assertFalse(returned.contains("<"));
    }

    @Test
    public void shouldNotContainSingleEmptyEol() throws Exception {
        TranslatorMock targetTranslator = new TranslatorMock(mapDictionaries);

        String returned = targetTranslator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
        Assert.assertFalse(returned.contains("<"));
    }
}


