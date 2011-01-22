package refactoring.com.tonyxzt.language;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 12.22
 * To change this template use File | Settings | File Templates.
 */
public interface ContentProvider {
    String retrieve(String word, String langIn, String langOut) throws Exception;
}
