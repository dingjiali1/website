package response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhong
 */
public class ContentValues extends java.util.HashMap<String,String>
{
     void put(String x, int y)
     {
         put(x, "!$#@" + y);
     }
     void put(String x, long y)
     {
         put(x, "!$#@" + y);
     }
     void put(String x, float y)
     {
         put(x, "!$#@" + y);
     }
     void put(String x, double y)
     {
         put(x, "!$#@" + y);
     }
     void put(String x, char y)
     {
         put(x, "!$#@" + y);
     }
     public String toString()
     {
         StringBuffer s = new StringBuffer();
         for (String y: keySet())
         {
             s.append(y + "=" + get(y) + "\n");
         }
         return s.toString();    
     }
}
