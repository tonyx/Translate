package org.tonyxzt.language.io;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Tonino
 * Date: 25/12/10
 * Time: 13.10
 * To change this template use File | Settings | File Templates.
 */
public class FileIoManager {
    public String readContentFromFile(String fileName) {
        String toReturn="";
        File file = new File(fileName);
        try {
//            String tempRet="";
            FileInputStream fileIn = new FileInputStream(file);

            //FileInputStream fstream = new FileInputStream("textfile.txt");
            // Get the object of DataInputStream

            DataInputStream in = new DataInputStream(fileIn);
            //BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF8"));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                toReturn+=strLine;
                toReturn+="\n";
            }

//            byte[] inBytes = new byte[10];
//            while (fileIn.read(inBytes)!=-1) {
//                fileIn.read(inBytes);
//                String strReaden = new String(inBytes,"UTF-8");
//                //System.out.println(strReaden);
//                toReturn+=strReaden;
//            }
//            fileIn.close();
//            toReturn = toReturn.trim();
//
////
////            //OutputStream debugFile=new FileOutputStream("DEBUG.txt");
////            //debugFile.write(toReturn.getBytes("UTF-8"));
////
            return toReturn;
        }

        catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        catch (IOException fe) {
            fe.printStackTrace();
        }

        catch (Exception ex)  {
            ex.printStackTrace();
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

