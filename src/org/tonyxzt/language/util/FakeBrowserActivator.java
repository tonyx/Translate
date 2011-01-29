package org.tonyxzt.language.util;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 29/01/11
 * Time: 16.49
 * To change this template use File | Settings | File Templates.
 */
public class FakeBrowserActivator implements BrowserActivator {
    private String outUrl;
    public void activateBrowser(String url) {
        this.outUrl=url;
    }
    public String getOutUrl() {
        return outUrl;
    }
}
