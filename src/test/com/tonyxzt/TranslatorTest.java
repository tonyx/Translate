package test.com.tonyxzt;

import com.tonyxzt.language.ExternalSourceManager;
import com.tonyxzt.language.ExternalSourceManagerMock;
import com.tonyxzt.language.Translator;
import com.tonyxzt.language.TranslatorMock;
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
        TranslatorMock translator = new TranslatorMock();
        translator.wrapCommandLineParameters(new String[]{"--gDic","ciao"});
        Assert.assertEquals(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING, translator.getMode());
    }

    @Test
    public void canGetThePlainLanguageName() throws Exception {
        TranslatorMock translator = new TranslatorMock();
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
        TranslatorMock translator = new TranslatorMock();
        translator.wrapCommandLineParameters(new String[]{"--gApi","ciao"});
        Assert.assertEquals(Translator.TranslationMode.USES_ONLY_API,translator.getMode());
    }

    @Test
    public void canUseMockedGoogleHtmlProvider() throws Exception {
        ExternalSourceManager externalSource = new ExternalSourceManagerMock(StubbedGHtmlContent.content);
        Translator translatorWithMockedSources = new TranslatorMock(externalSource);
        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=fr","hi"});
        Assert.assertTrue(returned.contains("salut"));
        Assert.assertTrue(returned.contains("bonjour"));
    }

    @Test
    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
        ExternalSourceManager externalSource = new ExternalSourceManagerMock(ArabicGHtmlContent.content);
        Translator translatorWithMockedSources = new TranslatorMock(externalSource);
        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=ar","moon"});
        Assert.assertFalse(returned.contains("<"));
    }

    @Test
    public void shouldNotContainSingleEmptyEol() throws Exception {
        ExternalSourceManager externalSource = new ExternalSourceManagerMock(ArabicGHtmlContent.content);
        Translator translatorWithMockedSources = new TranslatorMock(externalSource);
        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--gDic","--oriLang=en","--targetLang=ar","moon"});
        Assert.assertFalse(returned.contains("<"));
    }

}


