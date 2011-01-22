package com.tonyxzt.language;

import org.apache.commons.httpclient.NameValuePair;

import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 21/12/10
 * Time: 1.21
 * To change this template use File | Settings | File Templates.
 */
public class GoogleDictionary implements OnLineDictionary{

    protected FilterAndCleanContent filter = new GoogleDictionaryFilterAndCleanContent();
    protected ExternalSourceManager sourceManager;

    public GoogleDictionary(ExternalSourceManager sourceManager,FilterAndCleanContent filter) {
        this.sourceManager = sourceManager;
        this.filter = filter;
    }
    public GoogleDictionary(ExternalSourceManager sourceManager) {
        this.sourceManager = sourceManager;
    }

    public GoogleDictionary() {
        this(new ExternalSourceManager(),new GoogleDictionaryFilterAndCleanContent());
    }

    public String lookUp(String word, String langIn, String langOut) throws Exception {
        NameValuePair[] params = new NameValuePair[]{new NameValuePair("aq", "f"), new NameValuePair("langpair", langIn+"|"+langOut),
                new NameValuePair("q", word), new NameValuePair("hl", "en")};
        String theResult = sourceManager.lookupTranslationByProviderByGet("http://www.google.com/translate_dict", params);
        return filter.filter(theResult);

    }
}
