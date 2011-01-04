package com.tonyxzt.language;

import org.apache.commons.httpclient.NameValuePair;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 17.13
 * To change this template use File | Settings | File Templates.
 */
public class ExternalSourceManagerMock extends ExternalSourceManager {
    private String _mockedContent;
    public ExternalSourceManagerMock(String mockedContent) {
        _mockedContent=mockedContent;
    }

    @Override
    public String lookupTranslationByProviderByGet(String url, NameValuePair[] queryString) throws Exception {
        return _mockedContent;
    }
}
