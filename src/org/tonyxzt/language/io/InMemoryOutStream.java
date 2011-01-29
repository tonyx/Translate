package org.tonyxzt.language.io;

import org.tonyxzt.language.io.OutStream;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 22/01/11
 * Time: 22.16
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryOutStream implements OutStream {
    String buffer = "";
    String url = "";
    public void output(String out) {
        buffer+=out;
        buffer+="\n";
    }

    public void openUrl(String url) {
        this.url=url;
    }

    public void close() {
    }

    public String getUrl() {
        return url;
    }

    public String getContent() {
        return buffer;
    }
}

