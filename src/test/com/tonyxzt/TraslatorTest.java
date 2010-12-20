package test.com.tonyxzt;

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
        Translator translator = new Translator("en","fr");
        Assert.assertEquals("salut",translator.translate("hi"));
    }
    @Test
    public void withDictionaryScrapApiEnglishFrench() throws Exception {
        Translator translator = new Translator("en","fr",Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertTrue(translator.translate("hi").contains("bonjour"));
    }
    @Test
    public void withDictionaryScrapApiEnglishItalian() throws Exception {
        Translator translator = new Translator("en","it",Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertTrue(translator.translate("hi").contains("ciao"));
        Assert.assertTrue(translator.translate("hi").contains("salve"));
    }
    @Test
    public void removeTheHtmlTags() throws Exception {
        Translator translator = new Translator("en","fr",Translator.TranslationMode.USES_DICTIONARY_BY_SCRAPING);
        Assert.assertFalse(translator.translate("hello").contains("<"));
    }
}
