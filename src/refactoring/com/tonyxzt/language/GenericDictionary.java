package refactoring.com.tonyxzt.language;

import refactoring.com.tonyxzt.language.ContentFilter;
import refactoring.com.tonyxzt.language.ContentProvider;

import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 12.16
 * To change this template use File | Settings | File Templates.
 */
public class GenericDictionary {
    ContentProvider provider;
    ContentFilter filter;
    String name;
    InputStream inputStream;
    OutStream outStream;
    public GenericDictionary(String name, ContentProvider provider, ContentFilter filter) {
        this.name = name;
        this.provider=provider;
        this.filter=filter;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream=inputStream;
    }

    public void setOutStream(OutStream outStream) {
        this.outStream=outStream;
    }

    public String lookUp(String word,String langIn, String langOut) throws Exception {
        return filter.filter(provider.retrieve(word,langIn,langOut));
    }
}
