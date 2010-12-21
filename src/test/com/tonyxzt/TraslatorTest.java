package test.com.tonyxzt;

import com.google.api.translate.Language;
import com.tonyxzt.language.Translator;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 20/12/10
 * Time: 22.33
 * To change this template use File | Settings | File Templates.
 */
public class TraslatorTest {
    @Test
    public void withPlainApi() throws Exception {
        Translator translator = new Translator();
        Assert.assertEquals("salut",translator.translate("hi",Language.ENGLISH,Language.FRENCH));
    }
    @Test
    public void chinese() throws Exception {
        Translator translator = new Translator();
        Assert.assertEquals("你好",translator.translate("hi",Language.ENGLISH,Language.CHINESE));
    }
    @Test
    public void withDictionaryScrapApiEnglishFrench() throws Exception {
        Translator translator = new Translator(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertTrue(translator.translate("hi",Language.ENGLISH,Language.FRENCH).contains("bonjour"));
    }
    @Test
    public void withDictionaryScrapApiEnglishItalian() throws Exception {
        Translator translator = new Translator(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertTrue(translator.translate("hi",Language.ENGLISH,Language.ITALIAN).contains("ciao"));
        Assert.assertTrue(translator.translate("hi",Language.ENGLISH,Language.ITALIAN).contains("salve"));
    }
    @Test
    public void removeTheHtmlTags() throws Exception {
        Translator translator = new Translator(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertFalse(translator.translate("hello",Language.ENGLISH,Language.FRENCH).contains("<"));
    }

    @Test
    public void canHandleChinese() throws Exception {
        Translator translator = new Translator(Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertFalse(translator.translate("hi",Language.ENGLISH,Language.FRENCH).contains("<"));
    }

    @Test
    public void canGetHelpMessage() throws Exception {
        String params[] = {"executable","--help"};
        String returned = Translator.parseCommandLine(params);
        Assert.assertTrue(returned.contains("help"));
    }
}
