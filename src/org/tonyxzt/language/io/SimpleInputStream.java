package org.tonyxzt.language.io;

import org.tonyxzt.language.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 21/01/11
 * Time: 14.39
 * To change this template use File | Settings | File Templates.
 */
public class SimpleInputStream implements InputStream {
    String[] values;
    int counter = 0;
    public SimpleInputStream(String[] values) {
        this.values=values;
    }

    public String next() {
        return (counter==values.length?null:values[counter++]);
    }
}
