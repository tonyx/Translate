package org.tonyxzt.language.core;

import org.tonyxzt.language.core.ContentFilter;
import org.tonyxzt.language.core.ContentProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 12.16
 * To change this template use File | Settings | File Templates.
 * (c) Antonio Lucca 2011
 * License gpl3: http://www.gnu.org/licenses/gpl-3.0.txt
 */
public class GenericDictionary {
    ContentProvider provider;
    ContentFilter filter;
    InputWordRemapper inputWordRemapper;
    String name;
    public GenericDictionary(String name, ContentProvider provider, ContentFilter filter) {
        this.name = name;
        this.provider=provider;
        this.filter=filter;
        this.inputWordRemapper=new DefaultInputWordRemapper();
    }

    public GenericDictionary(String name, ContentProvider provider, ContentFilter filter,InputWordRemapper inputWordRemapper) {
        this.name = name;
        this.provider=provider;
        this.filter=filter;
        this.inputWordRemapper = inputWordRemapper;
    }

    public String lookUp(String word,String langIn, String langOut) throws Exception {
        String retrievedFromUrl = provider.retrieve(word,langIn,langOut);
        String theWord = inputWordRemapper.remappedInputWord(word,retrievedFromUrl);
        return theWord +" = "+ filter.filter(retrievedFromUrl,langIn, langOut);
    }

    public String supportedLanguages() {
        return provider.supportedLanguges();
    }

    public String getInfoUrl() {
        return provider.getInfoUrl();
    }

}
