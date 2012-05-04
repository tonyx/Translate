package org.tonyxzt.language.core;

import org.tonyxzt.language.io.StandardOutStream;
import org.tonyxzt.language.util.BrowserActivator;
import org.tonyxzt.language.util.CommandLineToStatusClassWrapper;
import org.tonyxzt.language.io.InputStream;
import org.tonyxzt.language.io.OutStream;
import org.tonyxzt.language.util.ImplementationInjectorFromFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/12/10
 * Time: 19.51
 * To change this template use File | Settings | File Templates.
 *
 * (c) Antonio Lucca 2011
 * License gpl3: http://www.gnu.org/licenses/gpl-3.0.txt
 *
 */
public class Translator {
    GenericDictionary currentDictionary;
    Map<String,GenericDictionary> dictionaries;
    CommandLineToStatusClassWrapper commandlineToStatusWrapper;
    protected String _oriLang;
    protected String _targetLang;
    InputStream inputStream;
    OutStream outStream;

    String command[];
    BrowserActivator browserActivator;

    public static void main(String[] inLine) {
        Map<String,GenericDictionary> mapDictionaries = new HashMap<String,GenericDictionary>();
        try {
            String path =  Translator.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("\\/[^\\/]*.jar","");
            mapDictionaries.putAll(new ImplementationInjectorFromFile(path+"/conf/providers.txt").getMap());
        } catch (Exception e) {
            System.out.println("error in the implementation injection. Check your conf directory and the providers.txt file");
            e.printStackTrace();
            return;
        }

        Translator translate = new Translator(mapDictionaries,new DefaultBrowserActivator(),new StandardOutStream(), new CommandLineToStatusClassWrapper(inLine,mapDictionaries,new StandardOutStream()));
        translate.doAction();
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream=inputStream;
    }

    public void setOutStream(OutStream outStream) {
        this.outStream=outStream;
    }

    public void setCurrentDictionary(GenericDictionary currentDictionary) {
        if (currentDictionary!=null) {
        //    this.currentDictionary = currentDictionary;
        }
        else
            //this.commandlineToStatusWrapper.getOutStream().output("unresolved dictionary");
            this.outStream.output("unresolved dictionary");
    }

    public void setOriLang(String _oriLang) {
        this._oriLang = _oriLang;
    }

    public void setTargetLang(String _targetLang) {
        this._targetLang = _targetLang;
    }

    public Translator(Map<String, GenericDictionary> mapTranslator,BrowserActivator browserActivator,OutStream outStream, CommandLineToStatusClassWrapper mapper) {
        this.outStream=outStream;
        this.dictionaries =mapTranslator;
        this.browserActivator=browserActivator;
        this.commandlineToStatusWrapper = mapper;
    }

    public Translator(Map<String, GenericDictionary> mapTranslator,BrowserActivator browserActivator,OutStream outStream, CommandLineToStatusClassWrapper mapper,InputStream inStream) {
        this.outStream=outStream;
        this.inputStream=inStream;
        this.dictionaries =mapTranslator;
        this.browserActivator=browserActivator;
        this.commandlineToStatusWrapper = mapper;
    }

    @Deprecated
    public Translator(Map<String, GenericDictionary> mapMockedDictionaries, BrowserActivator browserActivator, OutStream outStream) {
        this(mapMockedDictionaries,browserActivator,outStream,new CommandLineToStatusClassWrapper());
    }


    public void readyToAct() {
        commandlineToStatusWrapper.setStatusReadyForTheAction(this);
        //command=commandlineToStatusWrapper.getStrIn();
    }

    @Deprecated
    public void setCommand(String[] strIn)  {
        command=strIn;
        //commandlineToStatusWrapper.setStatusReadyForTheAction(this,strIn,this.dictionaries);
    }

    public void doAction() {
        readyToAct();
        String[] theCommand = this.commandlineToStatusWrapper.getStrIn();
        if (theCommand.length==0|| "--help".equals(theCommand[0])) {
            this.commandlineToStatusWrapper.getOutStream().output(helpCommand());
            //outStream.output(helpCommand());
            return;
        }

        if ("--languages".equals(theCommand[0])) {
            //outStream.output(validLanguages());
            this.commandlineToStatusWrapper.getOutStream().output(validLanguages());
            return;
        }

        if (theCommand.length>1&&"--info".equals(theCommand[1])) {
            this.browserActivator.activateBrowser(this.commandlineToStatusWrapper.getGenericDictionary().getInfoUrl());
            return;
        }

        if (theCommand.length>1&&"--languages".equals(theCommand[1])) {
            this.commandlineToStatusWrapper.getOutStream().output(validLanguages());
            //outStream.output(validLanguages());
            return;
        }

        try {
            //String toReturn="";
            String content;
            InputStream altInputStream = this.commandlineToStatusWrapper.getInputStream();
            while ((content = altInputStream.next())!=null) {
                String translated = translate(content);
                this.commandlineToStatusWrapper.getOutStream().output(content.trim() + " = " + translated);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String helpCommand() {
        return "usage: gtranslate "+injectedDictionariesList()+"[--languages|--info] [--oriLang=oriLang] [--targetLang=targetLang] [--inFile=infile] [--outFile=outfile] [word|\"any words\"]";
    }

    private String injectedDictionariesList() {
        String toReturn = "[";
        for (String key : dictionaries.keySet()) {
            toReturn +="--dic="+key+"|";
        }
        return toReturn.substring(0,toReturn.lastIndexOf("|"))+"]";
    }

    public String validLanguages() {
        return this.commandlineToStatusWrapper.getGenericDictionary().supportedLanguages();
        //return this.currentDictionary.supportedLanguages();
    }

    public String translate(String word) throws Exception {
        if (this.commandlineToStatusWrapper.getGenericDictionary()!=null)
        //    return this.currentDictionary.lookUp(word,this._oriLang,this._targetLang);
              return this.commandlineToStatusWrapper.getGenericDictionary().lookUp(word,this.commandlineToStatusWrapper.getOriLang(),this.commandlineToStatusWrapper.getTargetLang());
        return "";
    }
}


