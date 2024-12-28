package response;

 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class DBHelper  {
    
    static void movefile(String f)
    {
        File fd = new File( AppCompatActivity.datafolder);
        String [] s = fd.list();
        HashMap<String,String> done = new HashMap<>();
        boolean x = true;
        for (int i=0; i < s.length; i++)
        {
            if (s[i].indexOf(DATABASE_NAME) == 0)
            {
                File file = new File(AppCompatActivity.datafolder + File.separator + s[i]);  
                File newf = new File(f + File.separator + s[i]); 
                if (file.renameTo(newf))
                {
                    done.put(AppCompatActivity.datafolder + File.separator + s[i], f + File.separator + s[i]);
                }
                else
                {
                    x = false;
                }
            }
        }
        if (x==false)
        {
            for (String y: done.keySet())
            {
                String z = done.get(y);
                (new File(z)).renameTo(new File(y));
            }
        }
        if (x) 
        {
            AppCompatActivity.datafolder = f;
            sysserver = "jdbc:h2:file:" + f  + File.separator + DATABASE_NAME;
        }
        
    }
    public static void updateserver()
    {
        sysserver = "jdbc:h2:file:" + AppCompatActivity.datafolder.trim()
            + File.separator + DATABASE_NAME; 
    }
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Response";
    public static String sysserver = "jdbc:h2:file:" + AppCompatActivity.datafolder.trim()
            + File.separator + DATABASE_NAME;
    public static String sysdriver = "org.h2.Driver";
    public static String sysuser = "sa";
    public static String syspass = "";
    // Database Name
    
    public DBHelper(Context context ) {
        SQLiteDatabase db = new SQLiteDatabase(sysserver,sysdriver,sysuser,syspass);
        onCreate(db);
        db.close(); 
    }
    
    SQLiteDatabase getReadableDatabase()
     {
        
        return new SQLiteDatabase(sysserver,sysdriver,sysuser,syspass);
    }
    SQLiteDatabase getWritableDatabase()
    {
        return new SQLiteDatabase(sysserver,sysdriver,sysuser,syspass);
    }
    public DBHelper(Context context,String dbfile ) {

         
    }

    public static StringBuffer err = new StringBuffer();

     
    public void onCreate(SQLiteDatabase db)
    {

        String sql =    "CREATE TABLE Enquirer(num INTEGER AUTO_INCREMENT, session VARCHAR(40), timemoment BIGINT,  numberq INTEGER,  content TEXT,PRIMARY KEY (num) )";
        //String sql1 =    "CREATE TABLE Download(session VARCHAR(40), which VARCHAR(20),  content TEXT,PRIMARY KEY (session,which))";
        try{
            //db.executeUpdate(sql1);
           
            db.executeUpdate(sql);
            db.executeUpdate(sql.replaceFirst("Enquirer", "Response"));
            
        }
        catch(Exception e)
        {
            String str = e.toString();
            err.append(e.toString() + "\n");
        }
    }

    
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.executeUpdate("DROP TABLE IF EXISTS Enquirer" );
        db.executeUpdate("DROP TABLE IF EXISTS Response" );
        // Create tables again
        onCreate(db);

    }

     

}

