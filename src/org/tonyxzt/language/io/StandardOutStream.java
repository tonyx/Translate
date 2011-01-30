package org.tonyxzt.language.io;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 29/01/11
 * Time: 21.27
 * To change this template use File | Settings | File Templates.
 */
public class StandardOutStream implements OutStream{
     public void output(String out) {
        System.out.print(out);
     }

     public void close() {
     }
}
