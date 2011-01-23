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
public class AcceptanceRefactoredTranslatorTest {
    Map<String,GenericDictionary> mapDictionaries;
    Map<String,GenericDictionary> mapMockedDictionaries;
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
                    put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}}));
               }
        };
        mapMockedDictionaries = new HashMap<String,GenericDictionary>(){
            {
                put("gDic",new GenericDictionary("gDic",new ContentProvider(){
                    public String retrieve(String word, String langIn, String langOut) throws Exception {
                        return StubbedGHtmlContent.content;
                    }
                },new GDicContentFilter()));
                put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}}));
            }
        };
    }

//    @Test
//    public void canGetThePlainLanguageName() throws Exception {
//        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
//        translator.wrapCommandLineParameters(new String[]{"--languages", "aaaa"});
//        String returned = translator.doAction(new String[]{"--languages"});
//        Assert.assertTrue("extend languages description is not contained",returned.toLowerCase().contains("italian"));
//    }


    @Test
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    @Test
    public void shouldBeAbleToManageAnyInputStreamer() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapMockedDictionaries);

        translator.wrapCommandLineParameters(new String[]{"--dic=gDic", "--oriLang=en", "--targetLang=fr", "hi"});
        InMemoryOutStream inMemoryOutStream = new InMemoryOutStream();
        translator.setOutStream(inMemoryOutStream);

        translator.doAction(new String[]{"--dic=fDic", "--oriLang=en", "--targetLang=fr", "hi"});

        Assert.assertTrue(inMemoryOutStream.getContent().contains("salut"));
        Assert.assertTrue(inMemoryOutStream.getContent().contains("bonjour"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "ciao";} else return null;}};
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setInputStream(inputStream);
        InMemoryOutStream inMemoryOutStream = new InMemoryOutStream();
        translator.setOutStream(inMemoryOutStream);
        translator.doAction(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});

        Assert.assertTrue(inMemoryOutStream.getContent().contains("bye"));
    }




    @Test
    public void canReadFromInputFile() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "hi";} else return null;}};
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});

        translator.setInputStream(inputStream);

        InMemoryOutStream inMemoryOutStream = new InMemoryOutStream();

        translator.setOutStream(inMemoryOutStream);

        translator.doAction(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        Assert.assertTrue(inMemoryOutStream.getContent().contains("ciao"));
        Assert.assertTrue(inMemoryOutStream.getContent().contains("salve"));
    }


    @Test
    public void manageOutputFile() throws Exception {
        RefactoredTranslator translator = new RefactoredTranslator(mapDictionaries);
        translator.wrapCommandLineParameters(new String[]  {"--dic=gApi","--oriLang=it","--targetLang=en","--outFile=out","ciao"});
        InMemoryOutStream inMemoryOutStream = new InMemoryOutStream();

        //mapDictionaries.get("gApi").setOutStream(inMemoryOutStream);
        translator.setOutStream(inMemoryOutStream);

        translator.doAction(new String[]{"--dic=gApi", "--oriLang=it", "--targetLang=en", "--outFile=out", "ciao"});

        Assert.assertTrue(inMemoryOutStream.getContent().contains("hello"));
    }



    @Test
    public void canUseMockedGoogleHtmlProvider() throws Exception {
        RefactoredTranslator translatorWithMockedSources = new RefactoredTranslator(mapMockedDictionaries);
        translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"});
        InMemoryOutStream inMemoryOutStream = new InMemoryOutStream();
        //mapMockedDictionaries.get("gDic").setOutStream(inMemoryOutStream);
        translatorWithMockedSources.setOutStream(inMemoryOutStream);

        translatorWithMockedSources.doAction((new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"}));
        Assert.assertTrue(inMemoryOutStream.getContent().contains("salut"));
        Assert.assertTrue(inMemoryOutStream.getContent().contains("bonjour"));
    }


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


