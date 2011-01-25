package testrefactoring;

import com.google.api.translate.Language;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tonyxzt.language.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 11.50
 * To change this template use File | Settings | File Templates.
 */
public class Spikes {
    Map<String,GenericDictionary> mapDictionaries;
    String associateDictionaries = "gDic,org.tonyxzt.language.GDicProvider,refactoring.com.tonyxzt.language.refactoring.GDicContentFilter";
    String associategApi = "gApi,org.tonyxzt.language.GApiProvider,refactoring.com.tonyxzt.language.refactoring.ContentFilterIdentity";
    // "gDic",new GDicProvider(),new GDicContentFilter()))
    @Before
    public void SetUp() {
        mapDictionaries = new HashMap<String,GenericDictionary>(){
               {
                    put("gDic",new GenericDictionary("gDic",new GDicProvider(),new GDicContentFilter()));
                    put("gApi",new GenericDictionary("gApi",new GApiProvider(),new ContentFilter(){public String filter(String aString) {return aString;}}));
               }
        };
    }

    @Test
    public void shouldBeAbleToInstantiateDicClassesByReflection() throws Exception {
        ContentProvider provider;
        ContentFilter filter;
        provider = (ContentProvider)Class.forName("org.tonyxzt.language.GDicProvider").newInstance();
        filter = (ContentFilter)Class.forName("org.tonyxzt.language.GDicContentFilter").newInstance();
        GenericDictionary gDictionary = new GenericDictionary("gDic",provider,filter);
        Assert.assertTrue(gDictionary.lookUp("hi", Language.ENGLISH, Language.ITALIAN).contains("ciao"));
    }


}








