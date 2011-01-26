package acceptance.com.tonyxzt;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.*;

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
    InMemoryOutStream ios;
    Translator translator;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
                    put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilterIdentity()));
               }
        };
        translator = new Translator(mapDictionaries);
    }

    @Test
    public void DictionaryTest() throws Exception{
        GenericDictionary dictionary = new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter());
        Assert.assertTrue(dictionary.lookUp("hi", "en","it").contains("piacere"));
    }
    @Test
    public void TranslatorTest() throws Exception {
        GenericDictionary dictionary = new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}});
        Assert.assertEquals("ciao",dictionary.lookUp("hi", "en", "it"));
    }

//    @Test
//    public void refactoringTranslator() throws Exception {
//        Translator translator = new Translator(mapDictionaries);
//        org.junit.Assert.assertEquals("salut", translator.wrapCommandLineParameters(new String[]{"--dic=gApi", "--oriLang=en", "--targetLang=fr", "salut"}));
//    }
//
//    @Test
//    public void withDictionaryScrapApiEnglishItalian() throws Exception {
//        Translator translator = new Translator(mapDictionaries);
//        String result = translator.wrapCommandLineParameters(new String[]{"--dic=gDic", "--oriLang=en", "--targetLang=it", "hi"});
//        org.junit.Assert.assertTrue(result.contains("ciao"));
//        org.junit.Assert.assertTrue(result.contains("salve"));
//    }
//
//    @Test
//    public void aslkfaslkfas() throws Exception {
//        Translator translator = new Translator(mapDictionaries);
//        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=it","hi"});
//        Assert.assertTrue(returned.contains("ciao"));
//        Assert.assertTrue(returned.contains("salve"));
//    }
//
//    @Test
//    public void removeTheHtmlTags() throws Exception {
//        Translator translator = new Translator(mapDictionaries);
//        String returned = translator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hello"});
//        org.junit.Assert.assertFalse(returned.contains("<"));
//    }


    //   return "usage: gtranslate [--gApi|--gDic] [--oriLang=orilang] [--targetLang=targetlang] word";

    @Test
    public void canSetLanguage() throws Exception {
        translator.wrapCommandLineParameters(new String[]{"--oriLang=it","ciao"});
        org.junit.Assert.assertEquals("it", translator.getOriLang());
    }


    @Test
    public void orilanItalianTargetEnSayHi() throws Exception {
         translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--oriLang=it","--targetLang=en","ciao"});
         org.junit.Assert.assertEquals("it", translator.getOriLang());
         org.junit.Assert.assertEquals("hi", translator.translate("hi"));
     }

}
