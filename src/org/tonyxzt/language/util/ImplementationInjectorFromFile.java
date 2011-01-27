package org.tonyxzt.language.util;

import org.tonyxzt.language.core.GenericDictionary;
import org.tonyxzt.language.core.ContentFilter;
import org.tonyxzt.language.core.ContentProvider;
import org.tonyxzt.language.io.FileIoManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 23/01/11
 * Time: 15.07
 * To change this template use File | Settings | File Templates.
 */
public class ImplementationInjectorFromFile implements ImplementationInjector {
    String content ="";
    Map<String,GenericDictionary> mapDictionaries = new HashMap<String,GenericDictionary>();
    public ImplementationInjectorFromFile(String fileName) {
        FileIoManager io = new FileIoManager();
        content = io.readContentFromFile(fileName);
    }
    public Map<String, GenericDictionary> getMap() throws Exception{
        String[] rows = content.split("\n");
        for (String current : rows) {
            String[] splittedCurrent = current.split(",");
            GenericDictionary genDic = new GenericDictionary(splittedCurrent[0],(ContentProvider)Class.forName(splittedCurrent[1].trim()).newInstance(),(ContentFilter)Class.forName(splittedCurrent[2].trim()).newInstance());
            mapDictionaries.put(splittedCurrent[0],genDic);
        }
        return mapDictionaries;
    }
}
