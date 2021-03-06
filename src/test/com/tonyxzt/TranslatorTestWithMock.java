package test.com.tonyxzt;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tonyxzt.language.core.*;
import org.tonyxzt.language.io.InputStream;
import org.tonyxzt.language.io.OutStream;
import org.tonyxzt.language.util.BrowserActivator;
import org.tonyxzt.language.util.CommandLineToStatusClassWrapper;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 09/02/11
 * Time: 17.51
 * To change this template use File | Settings | File Templates.
 */
public class TranslatorTestWithMock {
    Map<String,GenericDictionary> mapMockedDictionaries;
    private Translator translator;
    BrowserActivator browserActivator;

    OutStream outStream;
    ContentProvider gDicContentProviderMock;
    ContentProvider gApiContentProviderMock;

    @Before
    public void SetUp() throws Exception {

        outStream = mock(OutStream.class);
        browserActivator = mock(BrowserActivator.class);

        gDicContentProviderMock =  mock(ContentProvider.class);
        when(gDicContentProviderMock.supportedLanguges()).thenReturn("italian");
        when(gDicContentProviderMock.getInfoUrl()).thenReturn("http://www.google.com/dictionary");
        when(gDicContentProviderMock.retrieve(anyString(), anyString(), anyString())).thenReturn(StubbedGHtmlContent.content);

        gApiContentProviderMock =  mock(ContentProvider.class);
        when(gApiContentProviderMock.supportedLanguges()).thenReturn("italian");
        when(gApiContentProviderMock.getInfoUrl()).thenReturn(null);
        when(gApiContentProviderMock.retrieve(anyString(),anyString(),anyString())).thenReturn("hi");

        mapMockedDictionaries = new HashMap<String,GenericDictionary>();

        mapMockedDictionaries.put("gDic",new GenericDictionary("gDic",gDicContentProviderMock,new GDicContentFilter()));
        mapMockedDictionaries.put("gApi",new GenericDictionary("gApi",gApiContentProviderMock,new ContentFilterIdentity()));

        //translator = new Translator(mapMockedDictionaries,browserActivator,outStream);
    }



    @Test
    public void canGetThePlainLanguageName() throws Exception {
        // given
        String[] command = new String[] {"--dic=gApi","--languages"};
        CommandLineToStatusClassWrapper commandLineToStatusClassWrapper  = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,commandLineToStatusClassWrapper);

        // when
        translator.doAction();

        // then
        verify(gApiContentProviderMock).supportedLanguges();
        verify(outStream).output("italian");
    }

//    @Test
//    @Ignore
//    public void forUnsupportedLanguageShouldGetAWarningMessage() throws Exception {
//        translator.setCommand(new String[]{"--dic=gUnsupported"});
//        translator.doAction();
//        verify(outStream).output("unresolved dictionary");
//    }
//
//
    @Test
    public void canReadFromInputStreamAllTheLinesUntilNull() throws Exception {
        // given
        //String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=fr", "--inFile=infile"};
        String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=fr", "ciao" };
        CommandLineToStatusClassWrapper mapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        InputStream inputStreamm = mock(InputStream.class);
        when(inputStreamm.next()).thenReturn("hi");
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,mapper);

        // when
        translator.doAction();

        // then
        verify(gDicContentProviderMock,times(1)).retrieve(anyString(),anyString(),anyString());
        verify(outStream,times(1)).output("ciao = salut!, bonjour!, hé!, ");
    }

    @Test
    public void canReadFromInputStreamTwoTimesTheContent() throws Exception {
        // given
        //String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=fr", "--inFile=infile"};
        String[] command = new String[]{"--dic=gDic", "--oriLang=it", "--targetLang=fr", "ciao" };
        CommandLineToStatusClassWrapper mapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        InputStream inputStreamm = mock(InputStream.class);
        when(inputStreamm.next()).thenReturn("hi").thenReturn("hi");
        //Translator translator = new Translator(mapMockedDictionaries,browserActivator,mapper,inputStreamm);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,mapper);

        // when
        translator.doAction();
        translator.doAction();

        // then
        verify(gDicContentProviderMock,times(1)).retrieve(anyString(),anyString(),anyString());
        verify(outStream,times(1)).output("ciao = salut!, bonjour!, hé!, ");
    }


    @Test
    public void canGetTheUrlService() {
        // given
        String[] command = new String[] {"--dic=gDic", "--info"};

        //CommandLineToStatusClassWrapper mapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries);
        CommandLineToStatusClassWrapper mapper = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);

        Translator translator = new Translator(mapMockedDictionaries,browserActivator,mapper);

        // when
        translator.doAction();

        // then
        verify(browserActivator).activateBrowser("http://www.google.com/dictionary");
    }


    @Test
    @Ignore
    public void helpCommandShouldReturnAvailablesDictionaries() throws Exception {
        // given
        String[] command = new String[] {"--help"};
        ContentProvider mockContentProvider = mock(ContentProvider.class);
        ContentFilter contentFilter = mock(ContentFilter.class);
        mapMockedDictionaries.put("myDic",new GenericDictionary("myDic",mockContentProvider,contentFilter));
        //CommandLineToStatusClassWrapper commandLine = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries);
        CommandLineToStatusClassWrapper commandLine = new CommandLineToStatusClassWrapper(command,mapMockedDictionaries,outStream);
        Translator translator = new Translator(mapMockedDictionaries,browserActivator,commandLine);

        // when
        translator.doAction();

        // then
        verify(outStream).output("usage: gtranslate [--dic=myDic|--dic=gDic|--dic=gApi][--languages|--info] [--oriLang=oriLang] [--targetLang=targetLang] [--inFile=infile] [--outFile=outfile] [word|\"any words\"]");
    }

}



