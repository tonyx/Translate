package org.tonyxzt.language.core;

import org.tonyxzt.language.util.BrowserActivator;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 29/01/11
 * Time: 18.56
 * To change this template use File | Settings | File Templates.
 */
public class DefaultBrowserActivator implements BrowserActivator {

    public void activateBrowser(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
