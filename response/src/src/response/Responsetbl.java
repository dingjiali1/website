package response;

 

import java.util.ArrayList;
import java.util.*;
import java.util.HashMap;
import java.sql.*;
 

public class Responsetbl {
    private DBHelper dbHelper;
    static int gap = 1800000;
    public Responsetbl(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int  save(String tbl, Response a )
    {
        int x = update1(tbl,a);
        if (x <= 0)
            x = insert(tbl,a);
        return x;
    }
    public int  insert(String tbl, Response a )
    {
         int ret = -1;
         dbHelper = new DBHelper(null); 
         SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("session", a.session);
            values.put("timemoment", a.timemoment );
            values.put("numberq", a.numberq);
            values.put("content", a.content);
            ret = (int) db.insert(tbl, null, values);
            if (ret!=1)
            {
            
            }
        }catch (Exception e)
        {
            DBHelper.err.append(e.toString());
        }
        db.close(); // Closing database connection
        return ret;
    }

    public int delete(String tbl, int num) {
        dbHelper = new DBHelper(null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int n = -1;
        try{
           n = db.delete(tbl, "num=" + num, null);
        }catch ( Exception e)
        {
            DBHelper.err.append(e.toString());
        }
        db.close(); // Closing database connection
        return n;
    }


    public int  update1(String tbl, Response a )
    {
        dbHelper.updateserver();
        dbHelper = new DBHelper(null);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numberq", a.numberq);
        values.put("content", a.content);
        values.put("session", a.session);
        int k =0;
        try {
             k = db.update(tbl, values, "num=" + a.num, null);
             
            if (k!=1)
            {
              
            }

       }catch ( Exception e)
       {
           DBHelper.err.append(e.toString() + "\n");
       }
        db.close();
        return k;

    }


    public Response getrecord(String tbl,  int num )
    {
        //session varchar(40), timemoment INTEGER, numberq INTEGER, content
        String sql = "SELECT content,numberq,timemoment,session FROM " + tbl +" WHERE num="+ num ;
        SQLiteDatabase db = dbHelper.getReadableDatabase(); 
        Response r = new Response(num,"New Session", System.currentTimeMillis(),5, "");
        try {
            int n = db.executeQuery(sql.replaceFirst("= ","="));

            if (n == 1)
            {
                r.content = db.getValueAt(0,0);
                r.numberq = Integer.parseInt(db.getValueAt(0,1));
                r.timemoment = Long.parseLong(db.getValueAt(0,2));
                r.session = db.getValueAt(0,3);
            }
            else
            {
               
            }
           
        }catch ( Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();
        return r;
    }
            
    public  String  getOthers(String tbl, String session,  int numberq)
    {
        String sql = "SELECT content FROM " + tbl + " WHERE session='" + session + "' AND timemoment=0 AND numberq=" + numberq;
        SQLiteDatabase db = dbHelper.getReadableDatabase(); 
        int n = 0; 
        try 
        {
            n = db.executeQuery(sql);
            
        }catch ( Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();
        if (n >= 1) 
        return  db.getValueAt(n-1,0); 
        return "";
    }
     public int saveOthers(String tbl, String session,  int numberq, String content )
    {
        
        if (session.equals(R.string.NewSession)) return 0;
        
        //session varchar(40), timemoment INTEGER, numberq INTEGER, content
        String sql = "INSERT INTO " + tbl + " (timemoment,session,numberq,content) VALUES (0,'" + session + "'," + numberq + ",'" + content.replaceAll("'","''") + "')";
        String sql1 = "DELETE FROM " + tbl + "   WHERE session='" + session + "' AND timemoment=0 AND numberq=" + numberq ;
        SQLiteDatabase db = dbHelper.getReadableDatabase(); 
        int n = 0; 
        try {
            n = db.executeUpdate(sql1);
            n = db.executeUpdate(sql);
           
        }catch ( Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();
        
        return n;
    }
    

    public Response getrecord(String tbl,  String ses )
    {
        //session varchar(40), timemoment INTEGER, numberq INTEGER, content
        String sql = "SELECT content,numberq,timemoment,session FROM " + tbl +" WHERE session='"+ ses + "' AND numberq>=0" ;
        SQLiteDatabase db = dbHelper.getReadableDatabase(); 
        Response r = new Response(0,"New Session", System.currentTimeMillis(),5, "");
        try {
            int n = db.executeQuery(sql.replaceFirst("= ","="));

            if (n == 1)
            {
                r.content = db.getValueAt(0,0);
                r.numberq = Integer.parseInt(db.getValueAt(0,1));
                r.timemoment = Long.parseLong(db.getValueAt(0,3));
                r.session = db.getValueAt(0,3);
            }
           
        }catch ( Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();
        return r;
    }
    
    public String weeksession()
    {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

            long x = System.currentTimeMillis() - 7 * 24 * 3600000;
            String sql = "SELECT  min(abs(timemoment -" + x + ")) as cl FROM Enquirer";
            int c = db.executeQuery(sql);
            long lg = 0;
            if (c==1) 
                 lg = Long.parseLong(db.getValueAt(0,0));
           
            if (lg == 0)
            {
                db.close();
                return null;
            }
            long x1 = x - lg;
            long x2 = x + lg;
            sql = "SELECT session FROM Enquirer WHERE timemoment = " + x1 + " OR timemoment = " + x2;
            c = db.executeQuery(sql );
            String session = null;
            if (c==1) session = db.getValueAt(0,0);
             
            db.close();
        return session;
    }
    public void resindex(String tbl, ArrayList<String> sessions, HashMap<String,ArrayList<Response>>times)
    {
        String sql = "SELECT session,timemoment,num FROM " + tbl + " ORDER BY session,timemoment AND numberq>=0";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {

            int c = db.executeQuery(sql );
            String oldsession = "";
            ArrayList<Response> y = null;
            int k=0;
            if (c < 0)
            {
                 
              
            }
            for (; k < c; k++)
           
                {
                    String s = db.getValueAt(k,0);
                    if (!s.equals(oldsession))
                    {
                        sessions.add(s);
                        if (times!=null){
                        y = new ArrayList<>();
                        times.put(s, y);}
                    }
                    if (times!=null && y != null)
                    {
                        long timemoment = Long.parseLong(db.getValueAt(k,1));
                        int n = Integer.parseInt(db.getValueAt(k,2));
                        times.get(s).add(new Response(n,s, timemoment, 0,""));
                    }
                    oldsession = s;
                 }  

            db.close();
        }catch (Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();

    }

    public String all(String tbl)
    {
        String sql = "SELECT num,session,timemoment,content FROM " + tbl + " ORDER BY session,timemoment";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        StringBuilder s = new StringBuilder();
        try
        {
            int c = db.executeQuery(sql );

            ArrayList<Response> y = null;
            for (int k=0;k < c; k++)
                {
                    s.append(db.getValueAt(k,0) + "," +
                            db.getValueAt(k,1) + ", \"" + 
                            dstr(new java.util.Date(Long.parseLong(db.getValueAt(k,2)))) 
                            + "\"\n"  + db.getValueAt(k,3) + "\n");
                }  
 
        }catch (Exception e)
        {
            DBHelper.err.append(e.toString() + "\n");
        }
        db.close();
        return s.toString();
    }
    static String pad(int x)
    {
        if (x < 10) return "0" + x;
        return "" + x;
    }
    public static String dstr1(java.util.Date dk)
    {
         
        return  pad(dk.getYear()+1900) +"-"+ pad(dk.getMonth()+1) + "-" + pad(dk.getDate())+ "_" + pad(dk.getHours()) + ":" + pad(dk.getMinutes());
    }



    public static String dstr(java.util.Date dk)
    {
        return pad(dk.getYear()+1900) + "-" + pad(dk.getMonth()+1) + "-" + pad(dk.getDate())+ "_" + pad(dk.getHours()) + ":" + pad(dk.getMinutes());
    }


}
