package accepttestrefactoring;

import com.google.api.translate.Language;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import refactoring.com.tonyxzt.language.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 14.13
 * To change this template use File | Settings | File Templates.
 */
public class RefactoringTest {
    Map<String,GenericDictionary> mapDictionaries;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
                    put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}}));
               }
        };
    }

    @Test
    public void DictionaryTest() throws Exception{
        GenericDictionary dictionary = new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter());
        Assert.assertTrue(dictionary.lookUp("hi", Language.ENGLISH,Language.ITALIAN).contains("piacere"));
    }
    @Test
    public void TranslatorTest() throws Exception {
        GenericDictionary dictionary = new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}});
        Assert.assertEquals("ciao",dictionary.lookUp("hi", Language.ENGLISH, Language.ITALIAN));
    }
    @Test
    public void refactoringTranslator() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        org.junit.Assert.assertEquals("salut", translator.wrapCommandLineParameters(new String[]{"--dic=gApi", "--oriLang=en", "--targetLang=fr", "salut"}));
    }

    @Test
    public void withDictionaryScrapApiEnglishItalian() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        String result = translator.wrapCommandLineParameters(new String[]{"--dic=gDic", "--oriLang=en", "--targetLang=it", "hi"});
        org.junit.Assert.assertTrue(result.contains("ciao"));
        org.junit.Assert.assertTrue(result.contains("salve"));
    }

    @Test
    public void aslkfaslkfas() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=it","hi"});
        Assert.assertTrue(returned.contains("ciao"));
        Assert.assertTrue(returned.contains("salve"));
    }

    @Test
    public void removeTheHtmlTags() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hello"});
        org.junit.Assert.assertFalse(returned.contains("<"));
    }

    @Test
    public void languageList() throws Exception {
        Assert.assertNotNull(Language.validLanguages);
        Assert.assertTrue("should countain en", Language.validLanguages.contains("en"));
        Assert.assertFalse("should not countain xx", Language.validLanguages.contains("xx"));
    }

     @Test
    public void ValidLanguageCRFormat() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        org.junit.Assert.assertTrue(translator.validLanguages().contains("\n"));
    }

    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        org.junit.Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    //   return "usage: gtranslate [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";

    @Test
    public void canSetLanguage() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        translator.wrapCommandLineParameters(new String[]{"--oriLang=it","ciao"});
        org.junit.Assert.assertEquals("it", translator.getOriLang());
    }


    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
         RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
         translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--oriLang=it","--targetLang=en","ciao"});
         org.junit.Assert.assertEquals("it", translator.getOriLang());
         org.junit.Assert.assertEquals("hi", translator.translate("hi"));
     }


}
