package response;

 
import java.lang.*;
import java.util.*;

public class Intent 
{
    public Class<?> cl;
    private HashMap<String,Object> a = new HashMap<>();
    int code;
    public void setCode(int x)
    {
        code = x;
    }
    AppCompatActivity caller;
    
    public Intent()
    {
    }
    
    public Intent(AppCompatActivity app, Class<?> cl)
    {
        this.cl = cl;
        caller = app;
    }
    public Intent(Context app, Class<?> cl)
    {
        
    }
    public Intent(int code)
    {
        
    }
    void setType(String pattern)
    {
        
    }
    public int getIntExtra(String nm, int def)
    {
        try{
        Integer x = (Integer) a.get(nm);
        if (x == null) return def;
        return x.intValue();
        }catch(Exception e){return def;}
    }
    public String getStringExtra(String nm)
    {
        try{
        String x =  (String)a.get(nm);
        return x;
        }catch(Exception e)
        {
           
            return null;
        }
    }
    public void  putIntegerArrayListExtra(String x, ArrayList<Integer> y)
    {
        a.put(x, y);
    }
    public ArrayList<Integer> getIntegerArrayListExtra(String x)
    {
        try{
        ArrayList<Integer> y = (ArrayList<Integer>) a.get(x);
        return y;
        }catch(Exception e)
        {
            
            return null;
        }
    }
    
    public void  putStringArrayExtra(String x, String [] y)
    {
        a.put(x, y);
    }
    public String[] getStringArrayExtra(String x)
    {
        try{
        String[] y = (String []) a.get(x);
        return y;
         }catch(Exception e)
        {
          
            return null;
        }
    }
    
    public ArrayList<String> getStringArrayListExtra(String x)
    {
        
        try{
        ArrayList<String> y = (ArrayList<String>) a.get(x);
        return y;
        }catch(Exception e)
        {
           
            return null;
        }
    }
    public void putStringArrayListExtra(String x, ArrayList<String> y)
    {
        a.put(x, y);
    }
    public void  putStringExtra(String x, String y)
    {
        a.put(x, y);
    }
    public void  putExtra(String x, String y)
    {
        a.put(x, y);
    }
    public void putIntExtra(String x, int y)
    {
        a.put(x, new Integer(y));
    }
}
