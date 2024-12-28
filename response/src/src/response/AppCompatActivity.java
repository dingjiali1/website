package response;

 
import javax.swing.*;
 
import javax.swing.event.*;
import java.net.URL;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.*;
public class AppCompatActivity extends JFrame
       implements ActionListener 
{
    static public int RESULT_OK = 0;
    static public String language = "en";
    public JToolBar toolBar = new JToolBar("Still draggable");
    public JMenuBar menuBar = new JMenuBar();
    public Container contentpane;
    protected HashMap<Integer,Component> components = new HashMap(); 
    int windwidth = 560;
    int windheight = 600;
    protected Intent intent = new Intent();
    public Intent getIntent()
    {
        return intent;
    }
    void onCreate()
    {
        
    }
    AppCompatActivity parent = null;
    
    int requestcode = -1;
    
    public void startActivity(Intent intent)
    {
        startActivityForResult(intent, -1);
    }
    public void startActivityForResult(Intent intent, int requestcode)
    {
        try{  
        Object object = intent.cl.newInstance();
        intent.setCode(requestcode);
        this.requestcode = requestcode;
        ((AppCompatActivity)object).intent = intent;
        ((AppCompatActivity)object).parent = intent.caller;
         setVisible(false);
        ((AppCompatActivity)object).onCreate();
        
        }catch(Exception e){}
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){}
    void finish()
    {
        setVisible(false);
        if (parent!=null )
        {
            parent.setVisible(true);
            if (parent.requestcode>=0)
            parent.onActivityResult(parent.requestcode, parent.resultcode, parent.intent);
        }
        dispose();
    }
    int resultcode;
    
    static String background = "#eeeeef";
    static String foreground = "#7c1843";
    static int fontsize = 14;
    static String datafolder = System.getProperty("user.home") + File.separator + "enqresp";
    static String uploadfolder = System.getProperty("user.home");
    static String downloadfolder = System.getProperty("user.home") + File.separator + "downloads";
    static String servefolder = System.getProperty("user.home") + File.separator + "Downloads";
    static int serverport=50937, clientport=50937;
    static void loadparams()
    {
        String setting = get("setting", null);
        if (setting!=null)
        {
            String s[] = setting.split("\n");
            background = s[0];
            foreground = s[1];
            fontsize = Integer.parseInt(s[2]);
            datafolder = s[3];
            uploadfolder = s[4];
            downloadfolder = s[5]; 
            servefolder = s[6];
            serverport = Integer.parseInt(s[7]);
            clientport = Integer.parseInt(s[8]);
            DBHelper.updateserver(); 
        }
    }
    static void folderok()
    {
        
        if (!(new File(datafolder)).exists())
        {
            (new File(datafolder)).mkdir();
        }
        else if ((new File(datafolder)).isFile())
        {
            datafolder += "1";
            (new File(datafolder)).mkdir();
        }
        if (!(new File(uploadfolder)).exists())
        {
            (new File(uploadfolder)).mkdir();
        }
        if (!(new File(downloadfolder)).exists())
        {
            (new File(downloadfolder)).mkdir();
        }
        if (!(new File(servefolder)).exists())
        {
            (new File(servefolder)).mkdir();
        }
        
    }
    public void setResult(int resultcode, Intent intent)
    {
        this.resultcode = resultcode;
        if (parent!=null && resultcode == RESULT_OK)
        {
           parent.intent = intent;
        }
    }
    Component findViewById(int j)
    {
        Component tf = components.get(j);
        if (tf == null)
        {
           
            return null;
        }
        int width = tf.getPreferredSize().width;
        if (width > 1) tf.setMinimumSize(new Dimension(width - 1, tf.getPreferredSize().height));
        return tf;
    }
    void removeeverything()
    {
        contentpane.removeAll();
        menuBar.removeAll();
        id2it.clear();
        command2id.clear();
        components.clear();
    }
    public AppCompatActivity() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        contentpane = getContentPane();
        //JScrollPane scrollPane = new JScrollPane(textArea);
        contentpane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,0,2,0); 
       // contentpane.setLayout(new BoxLayout(contentpane, BoxLayout.Y_AXIS));  
        contentpane.add(toolBar,c);
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        //add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(menuBar);  
        setPreferredSize(new Dimension(windwidth, windheight));
    }
    static HashMap<String,String> tempstore = new HashMap(); 
    static String[] get(String n[])
    {
           try{ 
            File file = new File(datafolder + File.separator + "temp");
            FileInputStream sin = new FileInputStream(file);
            GZIPInputStream gzis = new GZIPInputStream(sin);
            ObjectInputStream in = new ObjectInputStream(gzis);
            tempstore = (HashMap<String,String>)in.readObject();
            in.close();
            gzis.close();
            sin.close();
            String [] ans = new String[n.length];
            for (int i=0; i < n.length; i++)
                ans[i] = tempstore.get(n[i]);
            return ans;
           }catch(Exception e){
            
           }
           return null;
    }
    static String get(String n, AppCompatActivity s)
    {
           try{ 
            File file = new File(datafolder + File.separator + "temp");
            FileInputStream sin = new FileInputStream(file);
            GZIPInputStream gzis = new GZIPInputStream(sin);
            ObjectInputStream in = new ObjectInputStream(gzis);
            tempstore = (HashMap<String,String>)in.readObject();
            in.close();
            gzis.close();
            sin.close();
            return (String) tempstore.get(n);
           }catch(Exception e){
            
           }
           return null;
    }
    public static void savep(String n, String v )
    {
           tempstore.put(n, v);
           try{
            File file = new File(datafolder + File.separator + "temp");
            FileOutputStream fos = new FileOutputStream(file);
            GZIPOutputStream gzos = new GZIPOutputStream(fos);
            ObjectOutputStream out = new ObjectOutputStream(gzos);
            out.writeObject(tempstore);
            out.close();
            gzos.close();
            fos.close();
           }catch(Exception e){}
    }
    public Context  getBaseContext()
    {
        return null;
    }
    public Context  getApplicationContext()
    {
        return null;
    }
    HashMap<String,Integer> command2id = new HashMap();
    HashMap<Integer,JMenuItem> id2it = new HashMap();
    public void buildToolbar(String filename) throws FileNotFoundException
    {
        ParseLayout.sourcefolder = "res" + File.separator + "menu";
        ParseLayout p = new ParseLayout(filename); 
        p.parse(null,p.root);
        
        ArrayList<MenuInfo> menus = p.ms;
         
        File file = new File("res" + File.separator + "mipmap-mdpi");
        String path = file.getAbsolutePath();
        int w = 0;
        HashMap<String, JMenu> maps = new HashMap<>();
        for (MenuInfo menu : menus)
        {
           
           command2id.put(menu.title, menu.id);
           JButton button = makeNavigationButton(path  + File.separator
                  + menu.icon + ".png", 
                   menu.title,  
                   menu.title, 
                   menu.id);
           toolBar.add(button);
           components.put(menu.id, button);
           JMenu mx = maps.get(menu.showAsAction);
           if (mx == null)
           {
               mx =  new JMenu(menu.showAsAction.replaceAll(" ",""));
               menuBar.add(mx);
               maps.put(menu.showAsAction, mx);
           }
           JMenuItem it = new JMenuItem(menu.title);
           id2it.put(menu.id,it);
           w += button.getWidth();
           it.addActionListener(new AMouseListener(this,menu.title,menu.id));
           mx.add(it);
        }
        if (windwidth < w)
            windwidth = w;
        
         
    }
    String iconpath(String filename)
    {
       File f = new File("res" + File.separator + "mipmap-mdpi");
        String path = f.getAbsolutePath();
        return path + File.separator + filename + ".png";
     } 
    JMenuItem  findItem(int j)
    {
       JMenuItem x =  id2it.get(new Integer(j));
       return x;
    }
    public void activemenu(int j, boolean b)
    {
       JMenuItem it = id2it.get(j);
       if (it!=null) it.setEnabled(b);
       JButton z = actionButtons.get(j);
       if (z!=null) z.setEnabled(b);
    }
    
    HashMap<Integer,JButton> actionButtons = new HashMap<>();
    protected JButton makeNavigationButton(String imgLocation,
                                           String actionCommand,
                                           String toolTipText,
                                           int altText) 
    {
        JButton button = new JButton();
        //button.setPreferredSize(new Dimension(25,25));
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        try{
          ImageIcon icon =  new ImageIcon(imgLocation);
          button.setIcon(icon );
        }
        catch(Exception e)
        {                                     //no image found
            button.setText("" + altText);
            System.err.println(e.toString());
        }
        actionButtons.put(altText, button);
        return button;
    }
    public String getString(String x){return x;}
    public void onOptionsItemSelected(int menustr) 
    {
         
    }
    class AMouseListener  implements ActionListener
    {
       AppCompatActivity x;
       String y;
       int z;
       public AMouseListener(AppCompatActivity x,String y,int id) 
       {
          this.x = x; 
          this.y = y;
          z = id;
       }
        
        public void actionPerformed(ActionEvent e) {
            x.onOptionsItemSelected(z);
        }
      
    }
    protected void save(String x){}
    protected void switchlang(Class<?> cls, int num)
    {
        save(null);
        File fs = new File("res");
        String s = "";
        File [] fds = fs.listFiles();
        for (int i=0; i < fds.length; i++)
        {
            if (fds[i].getName().indexOf("values-")==0)
                s += "," + fds[i].getName().replaceFirst("values\\-", "");
        }
        String [] langs = s.substring(1).split(",");
        Object x0    = JOptionPane.showInputDialog(
                             this,
                             R.string.language, 
                             R.string.language,
                             JOptionPane.QUESTION_MESSAGE,
                             (Icon)(new ImageIcon(iconpath("setting"))),
                             langs,
                             language);
        if (x0 != null && !x0.toString().equals(language))
        {
            ParseLayout.sourcefolder = "res" + File.separator + "values-" + x0.toString();
            ParseLayout.destinyfolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\";
            ParseLayout p = new ParseLayout("strings.xml");
            p.proc();
            language = x0.toString();
            savep("language", language);
            intent = new Intent(this, cls);
            intent.putIntExtra("num", num); 
            this.startActivity(intent);
            this.dispose();
        }
    }
    public static String list2String(ArrayList<String> x)
    {
       StringBuffer s = new StringBuffer();
       for (String y:x)
       {
           if (s.length()>0)
               s.append("\n");
           s.append(y.replaceAll("\n","").trim());
       }
       return s.toString();
    }
    ArrayList<String> str2list(String x)
    {
        if (x==null || x.equals(""))
            return new ArrayList<String>();
        String [] y = x.split("\n");
        ArrayList<String> z = new ArrayList<>();
        for (String a:y)
        if (a!=null && a.length()>0)z.add(a);
        return z;
    }
    public void actionPerformed(ActionEvent e) {
         
        onOptionsItemSelected( command2id.get(e.getActionCommand()));
    }
}
   
