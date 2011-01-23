package org.tonyxzt.language;

import java.lang.reflect.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 24/01/11
 * Time: 0.18
 * To change this template use File | Settings | File Templates.
 */
    public class FieldsInspector {
     public String fieldsAnValues(Class aClass) {
        String toReturn = "";
        Field[] fields = aClass.getDeclaredFields();
        for (Field f: fields) {
            if ("String".equals(f.getType().getSimpleName())) {
                toReturn+=f.getName().toLowerCase();
                String shortDesc="";
                try {
                     shortDesc = (String)f.get(f);
                } catch (IllegalAccessException e) {
                }
                toReturn=toReturn+"\t";
                toReturn=toReturn+shortDesc+"\n";
            }
        }
        return toReturn;
    }
}
