package com.tonyxzt.language;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 13.10
 * To change this template use File | Settings | File Templates.
 */
public class FileIoManager {
    protected String readContentFromFile(String fileName) {
        String toReturn="";
        File file = new File(fileName);
        try {
            FileInputStream fileIn = new FileInputStream(file);
            byte[] inBytes = new byte[1024];

            while (fileIn.read(inBytes)!=-1) {
                fileIn.read(inBytes);
                String strReaden = new String(inBytes,"UTF-8");
                toReturn+=strReaden;
            }
            fileIn.close();
            toReturn = toReturn.trim();
            return toReturn;
        }
        catch (FileNotFoundException fe) {
        }
        catch (IOException fe) {
        }
        return "";
    }

    protected void saveToFile(String result,String fileName) {
      Exception ex=null;
      File file = new File(fileName);
      try {
          FileOutputStream out = new FileOutputStream(file,true);
          out.write(result.getBytes("UTF-8"));
          out.write("\n".getBytes());
          out.close();
      }
      catch (FileNotFoundException fo) {
          ex=fo;
      }
      catch (UnsupportedEncodingException uo) {
          ex=uo;
      }
      catch (IOException io) {
          ex=io;
      }
      if (ex!=null)
          System.out.println(ex.getMessage());
     }

}

