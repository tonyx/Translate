package testrefactoring;

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
 * User: tonyx
 * Date: 23/01/11
 * Time: 1.02
 * To change this template use File | Settings | File Templates.
 */
public class RefactoredTranslatorTest {

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
                },new ContentFilter(){public String filter(String aString) {return aString;}}));
            }
        };
        translator = new Translator(mapMockedDictionaries);
    }

    @Test
    public void canGetThePlainLanguageName() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        translator.wrapCommandLineParameters(new String[]{"--dic=gApi","--languages"});
        translator.setOutStream(outStream);
        translator.doAction(new String[]{"--dic=gApi","--languages"});
        Assert.assertTrue("extend languages description is not contained",outStream.getContent().toLowerCase().contains("italian"));
    }

    @Test
    public void canGetLanguagesFromSpecifigDictionary() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        translator.wrapCommandLineParameters(new String[]{"--dic=gApi", "--languages"});
        translator.setOutStream(outStream);
        translator.doAction(new String[]{"--dic=gApi","--languages"});
        //Assert.assertTrue(true);
        Assert.assertTrue("extend languages description is not contained",outStream.getContent().toLowerCase().contains("italian"));
    }





//
    @Test
    @Ignore
    public void ValidLanguageCRFormatContainsItalian() throws Exception {
        Translator translator = new Translator(mapMockedDictionaries);
        Assert.assertTrue(translator.validLanguages().contains("it"));
    }
//
//


    @Test
    @Ignore
    public void forNoArgumentsShoudBehaveReturningHelp() throws Exception {
        InMemoryOutStream outStream = new InMemoryOutStream();
        Translator translator = new Translator(mapMockedDictionaries);
        translator.wrapCommandLineParameters(new String[]{});

        translator.setInputStream(new InputStream() {
            public String next() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        translator.setOutStream(outStream);
        translator.doAction(new String[]{});

        Assert.assertTrue(translator.validLanguages().contains("it"));
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
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setInputStream(inputStream);
        translator.setOutStream(outStream);
        translator.doAction(new String[] {"--dic=gDic", "--oriLang=it","--targetLang=en","--inFile=infile"});

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
        translator.wrapCommandLineParameters(new String[] {"--dic=gDic","--oriLang=it","--targetLang=en","--inFile=infile"});
        translator.setOutStream(outStream);
        translator.doAction(new String[] {"--dic=gDic","--oriLang=it","--targetLang=en","--inFile=infile"});
        Assert.assertTrue(outStream.getContent().contains("salut"));
    }

//
//
//
//
//    @Test
//    public void shouldRemoveHtmlStuffsFromContent() throws Exception {
//        RefactoredTranslatorMock translatorWithMockedSources = new RefactoredTranslatorMock(mapMockedDictionaries);
//        String returned = translatorWithMockedSources.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
//
//    @Test
//    public void shouldNotContainSingleEmptyEol() throws Exception {
//        RefactoredTranslatorMock targetRefactoredTranslator = new RefactoredTranslatorMock(mapMockedDictionaries);
//
//        String returned = targetRefactoredTranslator.wrapCommandLineParameters(new String[]{"--dic=gDic","--oriLang=en","--targetLang=ar","moon"});
//        Assert.assertFalse(returned.contains("<"));
//    }
}




