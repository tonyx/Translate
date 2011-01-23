package org.tonyxzt.language;

import org.tonyxzt.language.ContentFilter;
import org.tonyxzt.language.ContentProvider;
import org.tonyxzt.language.GenericDictionary;
import org.tonyxzt.language.ImplementationInjector;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 16.40
 * To change this template use File | Settings | File Templates.
 */
public class ImplementationInjectorFromPlainText implements ImplementationInjector {
    String content;
    Map<String,GenericDictionary> mapDictionaries = new HashMap<String,GenericDictionary>();
    public ImplementationInjectorFromPlainText(String content) {
        this.content=content;
    }


    public Map<String, GenericDictionary> getMap() throws Exception{
        String[] rows = content.split("\n");
        for (String current : rows) {
            String[] splittedCurrent = current.split(",");
            GenericDictionary genDic = new GenericDictionary(splittedCurrent[0],(ContentProvider)Class.forName(splittedCurrent[1]).newInstance(),(ContentFilter)Class.forName(splittedCurrent[2]).newInstance());
            mapDictionaries.put(splittedCurrent[0],genDic);
        }
        return mapDictionaries;
    }
}
