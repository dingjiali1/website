package response;

 

import java.sql.*;
import java.io.*;
import java.util.*;
/*
import java.util.regex.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import oracle.sql.*;
import oracle.jdbc.*; 
import oracle.sql.*;
import oracle.jdbc.driver.*; */
 

public class SQLiteDatabase  
{
    public   String sysserver = "jdbc:h2:file:" + System.getProperty("user.home") 
            + File.separator + "Response";
    public   String sysdriver = "org.h2.Driver";
    public   String sysuser = "sa";
    public   String syspass = "";
    public void setdbinfo(String server, String driver, String user, String pass)
    {
       sysserver = server;
       sysdriver = driver;
       sysuser = user;
       syspass = pass;
    }
    Connection          connection = null;
    Statement           statement = null;
    public ResultSet           resultSet = null;
    public ResultSetMetaData   metaData = null;
    DatabaseMetaData dmd = null;
    StringBuffer errormsg = new StringBuffer(150);
    boolean debug = true;
    String dburl = null;
    public String[]    columnNames = {};
    public Vector<String []>  rows = null;
    public boolean[]   colIsNum = {};
    public int [] colSizes = {};
    public int [] colNullable = {};
    private int numberOfColumns = 0;
    int numberOfRows = 0;
    java.io.PrintStream ps = null;
    java.io.PrintWriter pw = null;
    char output;
    public String dbms = "access";
    public String status ="broken";
    public int cursor=0;
    public boolean needMetaInfo = false;

    public static void main(String [] args)
    {
      String server, driver, user="", pass="", sql;
      
    /*  server = "jdbc:odbc:telamanzlin";
      driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      sql = "SELECT ProductID,  ProductName, UnitPrice  from products";
      testit(server, driver, user, pass, sql);
       
/*
     server = "jdbc:microsoft:sqlserver://localhost:5560;DatabaseName=master";
      user = "sa";
      pass = "sqlserver";
      driver = "com.microsoft.jdbc.sqlsrever.SQLServerDriver";
      sql = "SELECT objective FROM Course";
      testit(server, driver, user, pass, sql);
     *
     * 
      server = "jdbc:oracle:thin:@167.21.180.26:1521:oracledb";
      user = "scott";
      pass = "tiger";
      driver = "oracle";
      sql = "SELECT EMPNO, ENAME,   JOB as JOB_t_8,  HIREDATE FROM EMP";
      testit(server, driver, user, pass, sql);
    */  
      server = "jdbc:mysql://localhost:3306/jdao";   //jdbc:mysql://localhost:3306/test
      user = "root";
      pass = "tomcat";
      driver = "com.mysql.jdbc.Driver";
      sql = "SELECT VERSION()";
      testit(server, driver, user, pass, sql);
    /*
      driver = "COM.cloudscape.core.RmiJdbcDriver";
      server = "jdbc:cloudscape.rmi://localhost:1099/estore";   
      testit(server, driver, user, pass, sql);
*/
     }

     static void testit(String server, String driver, String user, String pass, String sql)
     {  
      SQLiteDatabase adapter = new SQLiteDatabase(server, driver, user, pass, System.out);
      adapter.executeQuery(sql);
      adapter.println(adapter.error());
      adapter.print();
      String [ ] a = adapter.fieldList("Test"); 
     
      adapter.close();
     }
     
     public String error()
     {
        return errormsg.toString();
     }
      
     public String dbInfo()
     {
         try{
         DatabaseMetaData dm = connection.getMetaData();
         dbms = dm.getDatabaseProductName();
         return dm.getDatabaseProductName() + "\n"+
         dm.getDriverVersion() + "\n"+
         dm.getURL()+"\n" +
         dm.getUserName(); 
         }catch(Exception e){return e.toString();} 
      }

     public SQLiteDatabase(java.io.PrintWriter out)
     {
         
        String server, driver,user,pass;
        server = "jdbc:oracle:thin:@167.21.180.26:1521:oracledb";
        user = "scott";
        pass = "tiger";
        driver = "oracle";

        pw = out; 
        output = 'w';
       // out.println(server + driver);
        init(server, driver,user, pass);
        
         //sql = "SELECT ProductID,  ProductName as ProductName_t_32, UnitPrice as UnitPrice_t_6 FROM products";
         //testit(server, driver, user, pass, sql);
     }
      void print(String str)
      {
          if (debug ) 
          {
            if (output == 'w') pw.print(str);
            else if (output =='s') ps.print(str);
          }
          else
          {
             errormsg.append(str); 
          }
      }
      void println(String str)
      {
          if (debug)
          { 
            if (output == 'w') pw.println(str);
            else if (output =='s') ps.println(str);
          }
          else
          {
              errormsg.append(str+"\n"); 
          }
      }
      public SQLiteDatabase()
      {
          debug = true;
          init(sysserver, sysdriver, sysuser, syspass);
        //  System.out.println(sysserver + sysdriver +  sysuser +  syspass + status);
      }
      public SQLiteDatabase(String url, String driverName, String user, String passwd) 
      {
          debug = true;
          init(url, driverName, user,passwd);
         // System.out.println(sysserver + sysdriver +  sysuser +  syspass + status);
      }
      public SQLiteDatabase(String url, String driverName,
                       String user, String passwd, PrintWriter out) 
      {   
          pw = out;  output = 'w';
          init(url, driverName, user,passwd);
      }
      public SQLiteDatabase(String url, String driverName,
                       String user, String passwd, PrintStream out) 
      {   
          ps = out;  output = 's';
          init(url, driverName, user,passwd);
      }


     
   public void init(String url, String driverName, String user, String passwd)
    {
        this.sysserver = url;
        this.sysdriver = driverName;
        this.sysuser = user;
        this.syspass = passwd;
        if (debug) println("start");
        if (driverName == null) {println("driver is null");return;}
        if (url == null) {println("db server is null");return;}
        if (user == null) {println("user is null"); user = "";}
        dburl = url.replaceFirst(".*/([^/]*)$","$1");
        //Toolbox.println(1,dburl);
        try
        {
           if (driverName.indexOf("oracle")>=0)
           {
               //DriverManager.registerDriver(new  jdbc.driver.OracleDriver());
           }
           else if (driverName.indexOf("microsoft")>=0)
           {
               //DriverManager.registerDriver(new  microsoft.jdbc.sqlserver.SQLServerDriver());
           }
           else
           {
               //Class.forName("org.h2.Driver");
               Class.forName(driverName);//.newInstance();
                
           }

           if (debug) println("Opening db connection");

           if (driverName.indexOf("microsoft")>=0)
              connection = DriverManager.getConnection(url + ";User=" + user +";Password=" + passwd);
           else if (driverName.indexOf("derby")>=0)
           {
              Properties properties = new java.util.Properties();

              connection = DriverManager.getConnection(url + ";create=true",properties);
           }
           else
              connection = DriverManager.getConnection(url, user, passwd);
            //System.out.println(url + user + passwd);
            if (connection!=null)
            {
               statement = connection.createStatement();
               DatabaseMetaData dm = connection.getMetaData();
               dbms = dm.getDatabaseProductName().toLowerCase();
               if (dbms.equals("microsoft sql server"))
                   dbms = "sqlserver";
               else if (dbms.equals("apache derby"))
                   dbms = "derby";
               status ="open";
            }
            else
              if (debug) println("invalid");
       }
       catch( Exception e)
       {
         // System.out.println( e.toString());
          
       }


     }

    public void profile()
    {
       println(error());
       println ("user:" + userName());
       println ("url: " + url());
       println ("driver: " + driverName());
       String [] tn = tableList();
       if (tn != null)
       for (int i = tn.length - 1; i >= 0; i--) println(tn[i]);
       println("");
    }


    public void print()
    {

       int nr = getRowCount();
       int nc = getColumnCount();
       int [] width = new int[nc];
       String v;
       int w;
       for (int i = 0; i < nc; i++)
           try{
               width[i] = metaData.getColumnDisplaySize(i + 1);
           }catch( Exception e){}


       for (int i = 0; i < nr; i++)
       {
          for (int j = 0; j < nc; j++)
          {
             v = getValueAt(i, j);
             if (v == null) v="";
             w = v.length();
             if (colIsNum[j])
             {
                for (int k = 0; k < width[j] - w; k++)
                    print(" ");
                print(v+" ");
             }
             else
             {
                 print(v);
                 for (int k = 0; k < width[j] - w+1; k++)
                    print(" ");
             }
          }
          println("");
       }
    }

    public boolean transacte1(String querys[], int n, int m)
    {
       StringBuffer er = new StringBuffer();
       boolean j = true;
       for (int i = n; i < m; i++)
       {
           try
           {
             if (executeUpdate(querys[i]) < 0)
             {
                j = false;
                er.append(error() +"\n");
             }
           }
           catch(Exception e)
           {j = false;
            er.append(e.toString());
           }
       }
       errormsg.append(er);
       return j;

    }

    public boolean transacte(String querys[], int n, int m) throws  Exception
    {
       boolean tt =  connection.getAutoCommit();
       boolean b = false;
       boolean hasstate = true;
       try{
           connection.setAutoCommit(false);
           if (statement==null)
           {
               statement = connection.createStatement();
               hasstate = false;
           }
           for (int i = n; i < m; i++)
           {
               statement.execute(querys[i]);
           }
           connection.commit();
           b = true;
       }
       catch(SQLException e)
       {
           connection.rollback();
           throw (Exception)e;
       } finally{
           statement.close();
           connection.setAutoCommit(tt);
           if (hasstate)
               statement = connection.createStatement();
       }
       return b;
    }
    public int executeUpdate(String query)
    {
       if (dbms.equals("mysql"))
           query = query.replaceAll("\\\\", "\\\\\\\\");
       else if (dbms.equals("h2"))
           query = query.replaceAll(" mod ", " % ");

       int i = 0, j=0, N = query.length();
       if (N==0) return 0;
       while (i < N && query.charAt(i)!=' ')i++;
       String act = query.substring(0,i).toLowerCase();
       boolean mody = true;
       String tablename = null;
       if (act.indexOf("update") == 0)
       {
          while (i < N && query.charAt(i)==' ')i++; j=i;
          while (i < N && query.charAt(i)!=' ')i++;
          tablename = query.substring(j,i).toLowerCase();

       }
       else //if (act.indexOf("delete") == 0)
       {
           while (i < N && query.charAt(i)==' ')i++; j=i;
           while (i < N && query.charAt(i)!=' ')i++;
           tablename = query.substring(j,i).toLowerCase();
           if (!tablename.equals("table")&&!tablename.equals("into")&&!tablename.equals("from")&&!tablename.equals("database"))
           {
              println("Error: incorrect query:" + act + " " + tablename);
              return -1;
           }
           while (i < N && query.charAt(i)==' ')i++; j=i;
           while (i < N && query.charAt(i)!=' ')i++;
           tablename = query.substring(j,i).toLowerCase();
       }
       if (tablename.equals("") && query.indexOf("SET")!=0)
       {
              println("Error: incorrect query");
              return -1;
       }

       errormsg.setLength(0);
       if (connection == null || statement == null)
       {
            println("no connection");
            return -1;
       }
       numberOfColumns = 0;
       int nrows = -1;
       String tb = dburl + "," + tablename;
       while ( dburls.contains(tb) )
       {
           Thread.yield();
       }

       grabHandle(tb,true);
       try
        {
            nrows = statement.executeUpdate(query);
            println(query + nrows);
        }
        catch (SQLException ex)
        {
            println("Error: " + ex.toString());
            println(" " +query);
        }
        grabHandle(tb,false);
        return nrows;
    }
    static public java.util.TreeSet<String> dburls = new java.util.TreeSet<String>();

    synchronized static void grabHandle(String url, boolean get)
    {
       if (get)
            dburls.add(url);
       else
            dburls.remove(url);
    }

    public int executeQuery(String query)
    {
        if (dbms.equals("mysql"))
           query = query.replaceAll("\\\\", "\\\\\\\\");
        else if (dbms.equals("h2"))
           query = query.replaceAll(" mod ", " % ");

        errormsg.setLength(0);
        cursor = 0;
        numberOfColumns = 0;
        numberOfRows = 0;

        if (connection == null || statement == null)
        {
            println("no connection");
            return -1;
        }

        if (query == null || query.length() < 6)
        {
            println("wrong query:" + query);
            return -1;
        }
        String ss = query.substring(0,6).toLowerCase();

        if (ss.equals("select") == false)
        try
        {
            int i = statement.executeUpdate(query);
            if (i == -1) println("error");
            else println(query + i);
            return i;
        }
        catch (SQLException ex)
        {
            println("query=" + query);
            println(ex.toString());
            return -1;
        }

        try
        {
            numberOfColumns=0;
            numberOfRows = 0;
            resultSet = statement.executeQuery(query);
            metaData = resultSet.getMetaData();
            if ( metaData == null)
            {
               return -1;
            }
            numberOfColumns = metaData.getColumnCount();

        }
        catch (SQLException ex)
        {
            println("Error:");
            println(ex.toString());
            return -1;
        }
        boolean hasnext = false;

        if (rows==null) rows = new Vector<String[]>(10);

        while(true)
        {
           hasnext = false;
           try{ hasnext = resultSet.next(); }catch(Exception ex){break;}
           if (!hasnext) break;

           String arow[] = new String[numberOfColumns];
           for(int i = 1; i <= numberOfColumns; i++)
           {
               try{ 
                  arow[i-1] = resultSet.getString(i);
               }
               catch(Exception ex){}
           }
           rows.addElement(arow);
           numberOfRows++;
         }
         if (needMetaInfo)
         {
                metainfo();
         }
 
         return numberOfRows;
    }

    public void metainfo()
    {
        try
        {
            //metaData = resultSet.getMetaData();
            //numberOfColumns =  metaData.getColumnCount();

            columnNames = new String[numberOfColumns];
            colIsNum = new boolean[numberOfColumns];
            colSizes = new int[numberOfColumns];
            colNullable = new int[numberOfColumns];

            for(int column = 0; column < numberOfColumns; column++)
            {
                columnNames[column] = metaData.getColumnLabel(column+1);

                colIsNum[column] = isnum(column);
                colSizes[column] = metaData.getColumnDisplaySize(column+1);
                colNullable[column] = metaData.isNullable(column+1);
            }
        }
        catch (Exception e){println("" + e);}
    }

    public boolean executeQuery2(String query,boolean mt)
    {
        if (dbms.equals("mysql"))
           query = query.replaceAll("\\\\", "\\\\\\\\");
        else if (dbms.equals("h2"))
           query = query.replaceAll(" mod ", " % ");
        errormsg.setLength(0);
        try
        {
            resultSet = statement.executeQuery(query);
            if (mt) metaData = resultSet.getMetaData();
            cursor=0;
            return true;
        }
        catch (SQLException ex)
        {
            println("Error:");
            println(ex.toString());
        }
        return false;
    }
    public boolean nextRow()
    {
        if (cursor==numberOfRows-1)
            return false;
            cursor++;
        return true;
    }
    public String getParameter(int i)
    {
        return getValueAt(cursor,i);
    }

    public String getValueAt(int aRow, int aColumn)
    {
        if(aRow >= numberOfRows || aColumn >= numberOfColumns)
        {
            return  null;
        }
        else
        {
            return  ((String[])(rows.elementAt(aRow)))[aColumn];
        }
    }

    public void close()
    {
        errormsg.setLength(0);
        String m = "resultset";
        try
        {
            if (resultSet != null){ resultSet.close(); resultSet=null;}
            m = "statement";
            if (statement != null){ statement.close(); statement = null;}
            m = "connection";
            if (connection != null) connection.close();
            if (debug) println("Closed db connection");
            connection = null;
            statement = null;
            dmd = null;
        }
        catch(SQLException e)
        {
            println("Error as Closing db "+m);
        }
    }
    @Override
    protected void finalize() throws Throwable
    {
        close();
        super.finalize();
    }


    boolean isnum(int column)
    {
        int type;
        try
        {
            type = metaData.getColumnType(column+1);
        }
        catch (SQLException e)
        {
            return false;
        }

        switch (type)
        {
            case Types.BIT:
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
            case Types.FLOAT:
            case Types.DOUBLE:
            case Types.CHAR:
            case Types.DECIMAL:
            case Types.NUMERIC:
            case Types.REAL:
            return true;
         }
        return false;
    }

    public boolean isCellEditable(int row, int column)
    {
        try
        {
            return metaData.isWritable(column+1);
        }
        catch (SQLException e)
        {
            return false;
        }
    }

    public int getColumnCount()
    {
       return  numberOfColumns;
    }

    // Data methods

    public int getRowCount()
    {
        return numberOfRows;
    }

   synchronized public String   keyFields(String tn)
   {
       if (connection == null) return null;
       try{
       if (dmd == null)
       {
          dmd = connection.getMetaData();
       }
       ResultSet rs  = dmd.getPrimaryKeys(null, null, tn);
       String fields  = "";
       String str = "";

       while (rs.next())
       {
          String columnName = rs.getString("COLUMN_NAME");
          if (fields.equals("")==false)
              fields += ",";
          fields += columnName;
       }
       return fields;
       }catch(Exception e){return "";}
   }

   synchronized  public String [] tableList()
    {
       if (connection == null) return null;
       try
       {
          StringBuffer  tblnames = new StringBuffer("");
          if (dmd == null)
          {
              dmd = connection.getMetaData();
          }
          int n = 0;
          String []  ts = {"TABLE"};
          ResultSet rs2  = dmd.getTables(null, null,"%", ts);
          int k =0;
          while(rs2.next())
          {
             String tt = rs2.getString(3);
             if (tt!=null && !tt.equals(""))
             {
               if (k > 0)
                   tblnames.append(",");
               tblnames.append(tt);
               k++;
             }
          }
          return tblnames.toString().split(",");
       }
        catch (Exception e)
       {
           println(e.toString());
           return null;
       }
    }
    public String userName(){ try {return connection.getMetaData().getUserName();}catch(Exception e){return "unknown";}}
    public String url() { try{return connection.getMetaData().getURL();} catch(Exception e){return "unknown";}}
    public String driverName() { try{return connection.getMetaData().getDriverName();} catch(Exception e){return "unknown";}}

    synchronized public ResultSetMetaData tableMeta(String tablename)
    {
        if (connection == null)
        {
            return null;
        }

        try
        {
            resultSet = statement.executeQuery( "SELECT * from " +tablename + " where 0 = 1");
            return resultSet.getMetaData();
        }
        catch (SQLException ex)
        {
            errormsg.append(ex.toString());
        }
        return null;
    }



    synchronized public String [] fieldList(String tablename)
    {
       try
       {
            resultSet = statement.executeQuery( "SELECT * from " +tablename + " where 0 = 1");
            metaData = resultSet.getMetaData();
            if (metaData != null)
            {
               int kk = metaData.getColumnCount();
               String [] a = new String[kk];
               for (int i = 0; i < kk; i++)
               {
                 a[i] = metaData.getColumnName(i+1);
               }
               return a;
            }
        }
        catch(Exception e){}
        return null;
    }

    int insert(String tbl, String where, ContentValues values)
    {
         
        String field="", value="";
        for (String x : values.keySet())
        {
            String y = values.get(x);
            field += "," + x;
            if (y.indexOf("!$#@")==0)
                value +="," + y.substring(4);
            else
                value +=",'" + y.replaceAll("'", "''") + "'";
        }
        String sql = "INSERT INTO " + tbl + field.replaceFirst(",", "(")
                + ") VALUES " + value.replaceFirst(",", "(") + ")";
      
        int k = executeUpdate(sql);
        if (k < 0) DBHelper.err.append( error());
        //System.out.println(k +   error());
        return k;
    }
    int update(String tbl, ContentValues values,String where, String z)
    {
        String field="", value="";
        for (String x : values.keySet())
        {
            String y = values.get(x);
            field += "," + x + "=";
            if (y.indexOf("!$#@")==0)
                field +=  y.substring(4);
            else
                field +="'" + y.replaceAll("'", "''") + "'";
        }
        String sql = "UPDATE   " + tbl + " SET "+ field.replaceFirst(",", "")
                + " WHERE " + where;
        int k = executeUpdate(sql);
        if (k < 0) DBHelper.err.append( error());
        //System.out.println(k + error());
        return k;
        
    }
    
    
    int  delete(String tbl, String where, String x)
    {
        return executeUpdate("DELETE FROM " + tbl + " WHERE " + where );
    }
}



