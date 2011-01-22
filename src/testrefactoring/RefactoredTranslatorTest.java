package testrefactoring;

import com.tonyxzt.language.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import refactoring.com.tonyxzt.language.*;
import test.com.tonyxzt.StubbedGHtmlContent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 18.40
 *  * To change this template use File | Settings | File Templates.
 */
public class RefactoredTranslatorTest {
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
    public void canGetThePlainLanguageName() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        translator.wrapCommandLineParameters(new String[]{"--languages", "aaaa"});
        String returned = translator.doAction(new String[]{"--languages"});
        Assert.assertTrue("extend languages description is not contained",returned.toLowerCase().contains("italian"));
    }


    @Test
    public void testInputStream() {
        InputStream simpleInput = new SimpleInputStream(new String[] {"a","b"});
        Assert.assertEquals("a",simpleInput.next());
        Assert.assertEquals("b",simpleInput.next());
    }

    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    @Test
    public void shouldBeAbleToManageAnyInputStreamer() throws Exception {
        final ContentProvider fakeProvider = new ContentProvider() {
            public String retrieve(String word, String langIn, String langOut) throws Exception {
                return StubbedGHtmlContent.content;
            }
        };
        RefactoredTranslator translator = new RefactoredTranslator(new HashMap<String,GenericDictionary>()
            {
                {
                    put("fDic",new GenericDictionary("fake",fakeProvider,new GDicContentFilter()));
                }
           });

        translator.wrapCommandLineParameters(new String[]{"--dic=fDic", "--oriLang=en", "--targetLang=fr", "hi"});
        String result = translator.doAction(new String[]{"--dic=fDic", "--oriLang=en", "--targetLang=fr", "hi"});

        Assert.assertTrue(result.contains("salut"));
        Assert.assertTrue(result.contains("bonjour"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "ciao";} else return null;}};

        translator.wrapCommandLineParameters(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        mapDictionaries.get("gDic").setInputStream(inputStream);
        String returned = translator.doAction(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});

        Assert.assertTrue(returned.contains("bye"));
    }


    @Test
    public void canReadFromInputFile() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "hi";} else return null;}};
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        mapDictionaries.get("gDic").setInputStream(inputStream);
        String returned = translator.doAction(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        Assert.assertTrue(returned.contains("ciao"));
        Assert.assertTrue(returned.contains("salve"));
    }




//
//
//    @Test
//    public void manageOutputFile() throws Exception {
//        RefactoredTranslatorMock translator = new RefactoredTranslatorMock(mapDictionaries);
//        translator.wrapCommandLineParameters(new String[] {"--dic=gApi","--oriLang=it","--targetLang=en","--outFile=out","ciao"});
//        Assert.assertTrue(translator.readMockFile().contains("hello"));
//    }


//
//
//    @Test
//    public void canUseMockedGoogleHtmlProvider() throws Exception {
//
//        RefactoredTranslatorMock translatorWithMockedSources = new RefactoredTranslatorMock(mapDictionaries);
//
//        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"});
//        Assert.assertTrue(returned.contains("salut"));
//        Assert.assertTrue(returned.contains("bonjour"));
//    }
//
//    @Test
//    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
//        RefactoredTranslatorMock translatorWithMockedSources = new RefactoredTranslatorMock(mapDictionaries);
//        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
//
//    @Test
//    public void shouldNotContainSingleEmptyEol() throws Exception {
//        RefactoredTranslatorMock targetRefactoredTranslator = new RefactoredTranslatorMock(mapDictionaries);
//
//        String returned = targetRefactoredTranslator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
}


