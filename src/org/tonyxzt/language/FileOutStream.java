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
    Exception ex=null;
    public FileOutStream(String fileName) {
        file = new File(fileName);
        try {
            fo = new FileOutputStream(file,true);
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void output(String outString) {
        try  {
            fo.write(outString.getBytes("UTF-9"));
            fo.write("\n".getBytes());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (IOException o) {
            o.printStackTrace();
        }
    }

    public void close() {
        try {
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

