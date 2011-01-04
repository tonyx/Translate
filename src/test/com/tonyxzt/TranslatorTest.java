package test.com.tonyxzt;

import com.tonyxzt.language.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 18.40
 * To change this template use File | Settings | File Templates.
 */
public class TranslatorTest {
    @Test
    public void canHandleDictionaryParameter() throws Exception {
        Translator translator = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
        translator.wrapCommandLineParameters(new String[]{"--gDic","ciao"});
        Assert.assertEquals(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING, translator.getMode());
    }

    @Test
    public void canGetThePlainLanguageName() throws Exception {
        Translator translator = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
        String returned = translator.wrapCommandLineParameters(new String[]{"--languages"});
        Assert.assertTrue("extend languages description is not contained",returned.toLowerCase().contains("italian"));
    }

    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        Translator translator = new Translator();
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    @Test
    public void passgPlainTranlator() throws Exception {
        Translator translator = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));
        translator.wrapCommandLineParameters(new String[]{"--gApi","ciao"});
        Assert.assertEquals(Translator.TranslationMode.USES_ONLY_API,translator.getMode());
    }

    @Test
    public void canUseMockedGoogleHtmlProvider() throws Exception {

        Translator translatorWithMockedSources = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));

        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=fr","hi"});
        Assert.assertTrue(returned.contains("salut"));
        Assert.assertTrue(returned.contains("bonjour"));
    }

    @Test
    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
        Translator translatorWithMockedSources = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));

        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=ar","moon"});
        Assert.assertFalse(returned.contains("<"));
    }

    @Test
    public void shouldNotContainSingleEmptyEol() throws Exception {
        Translator targetTranslator = new Translator(new GoogleDictionary(new ExternalSourceManagerMock(StubbedGHtmlContent.content)));

        String returned = targetTranslator.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=ar","moon"});
        
        Assert.assertFalse(returned.contains("<"));
    }

}


