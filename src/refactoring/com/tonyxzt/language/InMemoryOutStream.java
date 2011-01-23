package refactoring.com.tonyxzt.language;

/**
 * Created by IntelliJ IDEA.
 * User: tonyx
 * Date: 22/01/11
 * Time: 22.16
 * To change this template use File | Settings | File Templates.
 */
public class InMemoryOutStream implements OutStream {
    String buffer = "";
    public void output(String out) {
        buffer+=out;
        buffer+="\n";
    }

    public void close() {
    }

    public String getContent() {
        return buffer;
    }
}

