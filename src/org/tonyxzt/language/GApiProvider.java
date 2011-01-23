package org.tonyxzt.language;

import com.google.api.translate.Translate;
import org.tonyxzt.language.ContentProvider;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 20/01/11
 * Time: 15.32
 * To change this template use File | Settings | File Templates.
 */
public class GApiProvider implements ContentProvider {

    public String retrieve(String word, String langIn, String langOut) throws Exception{
        return Translate.translate(word,langIn,langOut);
    }
}
