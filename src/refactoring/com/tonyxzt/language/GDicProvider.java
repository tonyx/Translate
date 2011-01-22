package refactoring.com.tonyxzt.language;

import com.sun.jndi.toolkit.url.UrlUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import refactoring.com.tonyxzt.language.ContentProvider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 13.01
 * To change this template use File | Settings | File Templates.
 */
public class GDicProvider implements ContentProvider {
    public String retrieve(String word, String langIn, String langOut) throws Exception {

        NameValuePair[] params = new NameValuePair[]{new NameValuePair("aq", "f"), new NameValuePair("langpair", langIn+"|"+langOut),
        new NameValuePair("q", word), new NameValuePair("hl", "en")};

        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://www.google.com/translate_dict");
        method.setQueryString(params);

        client.executeMethod(method) ;
        return method.getResponseBodyAsString();
    }
}
