package acceptance.com.tonyxzt;

import com.google.api.translate.Language;
import com.tonyxzt.language.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import test.com.tonyxzt.StubbedGHtmlContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 20/12/10
 * Time: 22.33
 * To change this template use File | Settings | File Templates.
 */
public class TraslatorTest {
    Map<String,OnLineDictionary> mapDictionaries;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,OnLineDictionary>(){
               {
                    put("gDic",new GoogleDictionary());
                    put("gApi",new GoogleTranslator());
               }
        };
    }

    @Test
    public void withPlainApi() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        Assert.assertEquals("salut",translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--oriLang=en","--targetLang=fr","salut"}));
    }
    @Test
    @Ignore
    // give false error on the acceptance: the correct par is -Dfile.encoding=UTF-8
    public void chinese() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        //Assert.assertEquals("你好",translator.translate("hi",Language.ENGLISH,Language.CHINESE));
    }
    @Test
    public void withDictionaryScrapApiEnglishFrench() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        Assert.assertTrue(translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"}).contains("salut"));
    }

    @Test
    public void withDictionaryScrapApiEnglishItalian() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        String result = translator.wrapCommandLineParameters(new String[]{"--dic=gDic", "--oriLang=en", "--targetLang=it", "hi"});
        Assert.assertTrue(result.contains("ciao"));
        Assert.assertTrue(result.contains("salve"));
    }

    @Test
    public void aslkfaslkfas() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=it","hi"});
        Assert.assertTrue(returned.contains("ciao"));
        Assert.assertTrue(returned.contains("salve"));
    }

    @Test
    public void removeTheHtmlTags() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hello"});
        Assert.assertFalse(returned.contains("<"));
    }

    @Test
    public void languageList() throws Exception {
        Assert.assertNotNull(Language.validLanguages);
        Assert.assertTrue("should countain en",Language.validLanguages.contains("en"));
        Assert.assertFalse("should not countain xx", Language.validLanguages.contains("xx"));
    }

    @Test
    public void ValidLanguageCRFormat() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("\n"));
    }

    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    //   return "usage: gtranslate [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";

    @Test
    public void canSetLanguage() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        translator.wrapCommandLineParameters(new String[]{"--oriLang=it","ciao"});
        Assert.assertEquals("it",translator.getOriLang());
    }

    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
        Translator translator = new Translator(mapDictionaries);
        translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--oriLang=it","--targetLang=en","ciao"});
        Assert.assertEquals("it",translator.getOriLang());
        Assert.assertEquals("hi",translator.translate("hi"));
    }

}


