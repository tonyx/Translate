package test.com.tonyxzt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.core.*;
import org.tonyxzt.language.io.InMemoryOutStream;
import org.tonyxzt.language.io.InputStream;
import org.tonyxzt.language.io.OutStream;
import org.tonyxzt.language.util.CommandLineToStatusClassWrapper;
import org.tonyxzt.language.util.FakeBrowserActivator;
import org.tonyxzt.language.util.Utils;

import java.awt.*;
import java.net.URI;
import java.net.URL;
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
    FakeBrowserActivator browserActivator;
    InMemoryOutStream outStream = new InMemoryOutStream();

    @Before
    public void SetUp() {
        browserActivator = new FakeBrowserActivator();
        mapMockedDictionaries = new HashMap<String,GenericDictionary>(){
            {
                put("gDic",new GenericDictionary("gDic",new ContentProvider(){
                    public String supportedLanguges() {
                        return "italian\t it";
                    }

                    public String getInfoUrl() {
                        return "http://www.google.com/dictionary";
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

                    public String getInfoUrl() {
                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                },new ContentFilterIdentity()));
            }
        };
    }


    @Test
    public void canGetThePlainLanguageNameRefactor() throws Exception {
        // given
        String[] command = new String[] {"--dic=gApi", "--languages"};
        CommandLineToStatusClassWrapper clMapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,clMapper);

        // when
        translator.doAction();

        // then
        Assert.assertTrue("extend languages description is not contained",outStream.getContent().toLowerCase().contains("italian"));
    }


    @Test
    public void forUnsupportedLanguageShouldGetAWarningMessage() throws Exception {
        // given
        String[] command = new String[] {"--dic=gUnsupported"};
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper  = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,commandLineToStatusClassWrapper);

        // when
        translator.doAction();

        // then
        Assert.assertTrue(outStream.getContent().contains("unresolved dictionary"));
    }


    @Test
    public void canReadFromInputFileMultipleLines() throws Exception {
        // given
        String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"};
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,commandLineToStatusClassWrapper);

        // when
        //translator.setCommand(new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"});
        translator.doAction();

        // then
        Assert.assertTrue(outStream.getContent().contains("salut!"));
        Assert.assertTrue(outStream.getContent().contains("\n"));
    }


    @Test
    public void canGetTheUrlService() {
        // given
        String[] command = new String[] {"--dic=gDic", "--info"};
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper  = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,commandLineToStatusClassWrapper);

        // when
        translator.doAction();

        // then
        Assert.assertEquals("http://www.google.com/dictionary", browserActivator.getOutUrl());
    }


    @Test
    public void canReadFromInputFile() throws Exception {
        // given
        String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"};
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);

        InputStream inputStream = new InputStream() {
            int count = 0;
            public String next() {
                if (count++<3) return "hi";
                else return null;
            }
        };

        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,commandLineToStatusClassWrapper,inputStream);

        //translator.setCommand(new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=en", "--inFile=infile"});
        //translator.setInputStream(inputStream);

        // when
        translator.doAction();

        // then
        System.out.println(outStream.getContent());
        Assert.assertTrue(outStream.getContent().contains("salut"));
    }

    @Test
    public void helpCommandShouldReturnAvailablesDictionaries() throws Exception {
        mapMockedDictionaries.put("myDic",new GenericDictionary("myDic",  (new ContentProvider(){
            public String retrieve(String word, String langIn, String langOut) throws Exception {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String supportedLanguges() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public String getInfoUrl() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }),new ContentFilter(){
            public String filter(String content) {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }));

        String[] command = new String[]{"--help"};
        //CommandLineToStatusClassWrapper commandLineToStatusClassWrapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries);
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,outStream,commandLineToStatusClassWrapper);

        //translator.setCommand(new String[]{"--help"});
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




