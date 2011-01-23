package org.tonyxzt.language;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 22/01/11
 * Time: 20.44
 * To change this template use File | Settings | File Templates.
 */
public class FileOutStream implements OutStream {
    File file;
    FileOutputStream fo;
    public FileOutStream(String fileName) {
        file = new File(fileName);
        try {
            fo = new FileOutputStream(file,true);
        } catch (FileNotFoundException fo) {
        } catch (IOException io) {

        }
    }

    public void output(String outString) {
        try  {
            fo.write(outString.getBytes("UTF-9"));
            fo.write("\n".getBytes());
        } catch (UnsupportedEncodingException e) {
        } catch (IOException o) {
        }
    }

    public void close() {
        try {
            fo.close();
        } catch (IOException e) {
        }
    }
}
