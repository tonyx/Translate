package acceptance.com.tonyxzt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tonyxzt.language.*;
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
public class TranslatorTest {
    Map<String,GenericDictionary> mapDictionaries;
    Map<String,GenericDictionary> mapMockedDictionaries;
    private Translator translator;
    private Translator translatorWithMockedSources;
    InMemoryOutStream ios;

    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
                    put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilterIdentity()));
               }
        };
        mapMockedDictionaries = new HashMap<String,GenericDictionary>(){
            {
                put("gDic",new GenericDictionary("gDic",new ContentProvider(){
                    public String retrieve(String word, String langIn, String langOut) throws Exception {
                        return StubbedGHtmlContent.content;
                    }

                    public String supportedLanguges() {
                        return "italian\t it";
                    }
                },new GDicContentFilter()));
                put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilterIdentity()));
            }
        };
        ios = new InMemoryOutStream();
        translator = new Translator(mapDictionaries);
        translatorWithMockedSources = new Translator(mapMockedDictionaries);
    }

//    @Test
//    public void canGetThePlainLanguageName() throws Exception {
//        Translator translator = new Translator(mapDictionaries);
//        translator.wrapCommandLineParameters(new String[]{"--languages", "aaaa"});
//        String returned = translator.doAction(new String[]{"--languages"});
//        Assert.assertTrue("extend languages description is not contained",returned.toLowerCase().contains("italian"));
//    }


    @Test
    @Ignore
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }

    @Test
    public void shouldBeAbleToManageAnyInputStreamer() throws Exception {
        translator.wrapCommandLineParameters(new String[]{"--dic=gDic", "--oriLang=en", "--targetLang=fr", "hi"});
        translator.setOutStream(ios);

        translator.doAction(new String[]{"--dic=fDic", "--oriLang=en", "--targetLang=fr", "hi"});

        Assert.assertTrue(ios.getContent().contains("salut"));
        Assert.assertTrue(ios.getContent().contains("bonjour"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "ciao";} else return null;}};
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setInputStream(inputStream);
        translator.setOutStream(ios);

        translator.doAction(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});

        Assert.assertTrue(ios.getContent().contains("bye"));
    }


    @Test
    public void canReadFromInputFile() throws Exception {
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});
        InputStream inputStream = new InputStream(){ boolean start = true; public String next() {if (start) { start=false; return "hi";} else return null;}};
        translator.setInputStream(inputStream);
        translator.setOutStream(ios);
        translator.doAction(new String[] {"--dic=gDic","--oriLang=en","--targetLang=it","--inFile=infile"});

        Assert.assertTrue(ios.getContent().contains("ciao"));
        Assert.assertTrue(ios.getContent().contains("salve"));
    }


    @Test
    public void manageOutputFile() throws Exception {
        translator.wrapCommandLineParameters(new String[]  {"--dic=gApi","--oriLang=it","--targetLang=en","ciao"});
        translator.setOutStream(ios);
        translator.doAction(new String[]{"--dic=gApi", "--oriLang=it", "--targetLang=en", "ciao"});

        Assert.assertTrue(ios.getContent().contains("hello"));
    }



    @Test
    public void canUseMockedGoogleHtmlProvider() throws Exception {
        translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"});
        translatorWithMockedSources.setOutStream(ios);
        translatorWithMockedSources.doAction((new String[]{"--dic=gDic","--oriLang=en","--targetLang=fr","hi"}));

        Assert.assertTrue(ios.getContent().contains("salut"));
        Assert.assertTrue(ios.getContent().contains("bonjour"));
    }


    @Test
    public void testCyrillic() throws Exception {
        translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--oriLang=en","--targetLang=ru","hi"});
        translator.setOutStream(ios);
        translator.doAction((new String[]{"--dic=gApi","--oriLang=en","--targetLang=ru","hi"}));

        Assert.assertTrue(ios.getContent().contains("привет"));
    }


}



