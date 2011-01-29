package org.tonyxzt.language.core;

import org.tonyxzt.language.core.ContentFilter;
import org.tonyxzt.language.core.ContentProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 12.16
 * To change this template use File | Settings | File Templates.
 */
public class GenericDictionary {
    ContentProvider provider;
    ContentFilter filter;
    String name;
    public GenericDictionary(String name, ContentProvider provider, ContentFilter filter) {
        this.name = name;
        this.provider=provider;
        this.filter=filter;
    }

    public String lookUp(String word,String langIn, String langOut) throws Exception {
        return filter.filter(provider.retrieve(word,langIn,langOut));
    }

    public String supportedLanguages() {
        return provider.supportedLanguges();
    }

    public String getInfoUrl() {
        return provider.getInfoUrl();
    }

}
