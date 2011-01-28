package test.com.tonyxzt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.core.*;
import org.tonyxzt.language.io.InMemoryOutStream;
import org.tonyxzt.language.io.InputStream;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 23/01/11
 * Time: 1.02
 * To change this template use File | Settings | File Templates.
 */
public class TranslatorTest {

    Map<String,GenericDictionary> mapMockedDictionaries;
    private Translator translator;

    @Before
    public void SetUp() {
        mapMockedDictionaries = new HashMap<String,GenericDictionary>(){
            {
                put("gDic",new GenericDictionary("gDic",new ContentProvider(){
                    public String supportedLanguges() {
                        return "italian\t it";
                    }

                    public String retrieve(String word, String langIn, String langOut) throws Exception {
                        return StubbedGHtmlContent.content;
                    }
                }, new GDicContentFilter()));

                put("gApi",new GenericDictionary("gApi",new ContentProvider() {
                    public String retrieve(String word, String langIn, String langOut) throws Exception {
                        return "hi";}

                    public String supportedLanguges() {
                        return "italian\t it";
                    }
                 },new ContentFilterIdentity()));
            }
        };
        translator = new Translator(mapMockedDictionaries);
    }

    @Test
    public void canGetThePlainLanguageName() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        translator.setCommand(new String[]{"--dic=gApi", "--languages"});
        translator.setOutStream(outStream);
        translator.doAction();
        Assert.assertTrue("extend languages description is not contained",outStream.getContent().toLowerCase().contains("italian"));
    }

    @Test
    public void canGetLanguagesFromSpecifigDictionary() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        translator.setCommand(new String[]{"--dic=gApi", "--languages"});
        translator.setOutStream(outStream);
        translator.doAction();
        Assert.assertTrue("extend languages description is not contained",outStream.getContent().toLowerCase().contains("italian"));
    }


    @Test
    public void forUnsupportedLanguageShouldGetAWarningMessage() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        translator.setOutStream(outStream);

        translator.setCommand(new String[]{"--dic=gUnsupported"});
        translator.doAction();
        Assert.assertTrue(outStream.getContent().contains("unresolved dictionary"));
    }



    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        InputStream inputStream = new InputStream() {
            int count = 0;
            public String next() {
                if (count++<3) return "hi";
                else return null;
            }
        };
        InMemoryOutStream outStream  = new InMemoryOutStream();
        translator.setInputStream(inputStream);

        translator.setCommand(new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"});
        translator.setOutStream(outStream);
        translator.doAction();

        Assert.assertTrue(outStream.getContent().contains("salut!"));
        Assert.assertTrue(outStream.getContent().contains("\n"));

    }
//
//
    @Test
    public void canReadFromInputFile() throws Exception {
        InputStream inputStream = new InputStream() {
            int count = 0;
            public String next() {
                if (count++<2) return "hi";
                else return null;
            }
        };
        InMemoryOutStream outStream  = new InMemoryOutStream();
        translator.setInputStream(inputStream);
        translator.setOutStream(outStream);

        translator.setCommand(new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"});
        translator.doAction();
        Assert.assertTrue(outStream.getContent().contains("salut"));

    }

    @Test
    public void helpCommandShouldReturnAvailablesDictionaries() throws Exception {
        InMemoryOutStream outStream  = new InMemoryOutStream();
        mapMockedDictionaries.put("myDic",new GenericDictionary("myDic",  (new ContentProvider(){
            public String retrieve(String word, String langIn, String langOut) throws Exception {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String supportedLanguges() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }),new ContentFilter(){
            public String filter(String content) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));
        translator = new Translator(mapMockedDictionaries);
        translator.setCommand(new String[]{"--help"});
        translator.setOutStream(outStream);
        translator.doAction();

        Assert.assertTrue(outStream.getContent().contains("gApi"));
        Assert.assertTrue(outStream.getContent().contains("gDic"));
        Assert.assertTrue(outStream.getContent().contains("myDic"));
    }




//
//
//
//
//    @Test
//    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
//        RefactoredTranslatorMock translatorWithMockedSources = new RefactoredTranslatorMock(mapMockedDictionaries);
//        String returned = translatorWithMockedSources.setCommand(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
//
//    @Test
//    public void shouldNotContainSingleEmptyEol() throws Exception {
//        RefactoredTranslatorMock targetRefactoredTranslator = new RefactoredTranslatorMock(mapMockedDictionaries);
//
//        String returned = targetRefactoredTranslator.setCommand(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
}




