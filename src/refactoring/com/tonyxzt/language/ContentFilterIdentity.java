package refactoring.com.tonyxzt.language;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 12.27
 * To change this template use File | Settings | File Templates.
 */
public class ContentFilterIdentity implements ContentFilter {
    public String filter(String content) {
        return content;
    }
}
