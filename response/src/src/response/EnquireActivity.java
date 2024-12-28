package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.ZoneId;
 

public class EnquireActivity extends AppCompatActivity{
    final int WRITE_REQUEST_CODE = 200;
    final int EDIT_QUESTION_CODE = 105;
    final int READ_REQUEST_CODE = 201;
    final int DOWNLOAD_CODE = 202;
    final int EDIT_USER_CODE = 203;
    final int EDIT_SELF_CODE = 204;
    final int ANSWER_QUESTION_CODE = 205;
    final int EDIT_SETTING_CODE = 206;
    public static final byte ISFILELIST = 0, ISEMPTYFILE=1, ISDIRECTORY = 2,
            ISFILECONTENT=3, ISSAMEFILE = 4, NOPERMISSION = 5, NOSUCHFILE = 6;
    private JPanel tl;
   
    private JLabel tmyip;
    private JLabel lbltimes;
    private int maxid;
    private final ArrayList<String> answers = new ArrayList<>();
    Responsetbl resdb;
    static String missed = null;
    AES enc;
    Response r = null;
    //static int PORT = MainActivity.PORT;
   // private  AtomicInteger[] orders;
    private JMenuItem  newsm;
    private JButton recev ;
    public static int NR = 0;
    private int NQ = 0;
    private JComboBox spinner;
    private final String GOOD = "" + ((char) 10003);
    //private ArrayList<String> arrayAdapter;
    
    ArrayList<String> sessions, downloads = new ArrayList<>();
    int num;
    private int[] orders = new int[10];
    private String todo = null;
    private JLabel instr = null;
    File directory = null;//new File(System.getProperty("user.home") + File.separator + "Downloads");
    //File directory = Environment.getExternalStoragePublicDirectory("download");
    public static ArrayList<ReceiveData> clients = new ArrayList<>();
    ExecutorService pool = Executors.newFixedThreadPool(10);
    static int defaultpermit = 4;
    //ArrayList<Integer> permissions = new ArrayList<>();
    //ArrayList<String> pins = new ArrayList<>();

    Semaphore filenamemutex = new Semaphore(1);
    ArrayList<ArrayList<Component>> ele = new ArrayList();
    
    class Buddy
    {
        String user,pin; 
        int permission;
        public Buddy(String x, String y, int z)
        {
            user=x;
            pin=y;permission=z;
        }
        public String toString()
        {
            return "\"" + user.replaceAll("\"", "\\\"") + "\","
                  +"\"" + pin.replaceAll("\"", "\\\"") + "\","
                  +  permission;
        }
        public Buddy(String aline)
        {
            String x[] = (new CSVParse(aline,'"',new String[]{","})).nextRow();
            user = x[0];
            if (x.length>1) pin = x[1];
            if (x.length>2) 
                try{permission = Integer.parseInt(x[2]);}catch(Exception e)
            {permission=defaultpermit;}
        }
    }
    private ArrayList<Buddy> buddys = new ArrayList<>();
    class Pair {
        String ip, filename;

        public Pair(String ip, String fn) {
            this.ip = ip;
            this.filename = fn;
        }
    }

     void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
      JPanel ll2 = new JPanel(new GridBagLayout());
      ll2.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      c = new GridBagConstraints();
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      contentpane.add(ll2,c);
      components.put(R.id.LinearLayout01,ll2);
      
      JPanel ll30 = new JPanel(new GridBagLayout());
      ll30.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.99;
      c.ipadx = c.ipady = 1;
      c.insets = new Insets(1,1,1,1);
      ll2.add(ll30,c);
      
      JPanel ll3 = new JPanel(new GridBagLayout());
      ll3.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 0;
      
      c.weighty = 0.99;
      c.insets = new Insets(1,1,0,1);
      ll30.add(ll3,c);
      
      
      JLabel lbl400 = new JLabel( "<html><nobr>" +R.string.Session.replaceAll(" ","&nbsp;") + "</nobr></html>");
      lbl400.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl400.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,2,2,2);
      lbl400.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll3.add(lbl400,c);
      
      
      JComboBox<String> lbl40 = new JComboBox<>();
      components.put(R.id.lblses,lbl40);
      lbl40.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl40.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      lbl40.setPreferredSize(new Dimension(100,25));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 1;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,2,2,2);
      lbl40.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
      ll3.add(lbl40,c);
      
      JLabel lbl401 = new JLabel( "<html><nobr>&nbsp;" +R.string.timestamp.replaceAll(" ","&nbsp;") + "</nobr></html>");
      lbl401.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl401.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 2;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,20,2,2);
      lbl401.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll3.add(lbl401,c);
      
      JLabel lbl4 = new JLabel( "___________");
      components.put(R.id.times,lbl4);
      lbl4.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl4.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 3;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,2,2,2);
      lbl4.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
      ll3.add(lbl4,c);
      
      JLabel lbl5 = new JLabel( "<html><nobr>&nbsp;" +R.string.thisip.replaceAll(" ","&nbsp;") + "<nobr></html>");
      components.put(R.id.lblrip,lbl5);
      lbl5.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl5.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 4;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,20,2,2);
      lbl5.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll3.add(lbl5,c);
      
      JLabel lbl6 = new JLabel("__________");
      components.put(R.id.myip,lbl6);
      lbl6.setForeground(ParseLayout.hex2Rgb("#0000ff"));
      lbl6.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.05;
      c.gridx = 5;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(2,2,2,2);
      lbl6.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
      ll3.add(lbl6,c);
      
      JPanel tl7 = new JPanel(new GridBagLayout());
      tl7.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      components.put(R.id.maintable,tl7);
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.insets = new Insets(1,1,1,1);
      ll2.add(tl7,c);
      
      JLabel lbl8 = new JLabel( "<html><nobr>" +R.string.num.replaceAll("\n","<br>") + "</nobr></html>");
      lbl8.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl8.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      //lbl8.setPreferredSize(new Dimension(70, 25));
      lbl8.setHorizontalAlignment(SwingConstants.LEFT);
      lbl8.setAlignmentX(LEFT_ALIGNMENT);
      lbl8.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0 ;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0;
      c.insets = new Insets(1,1,1,1);
      c.anchor = GridBagConstraints.WEST;
      lbl8.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      tl7.add(lbl8,c);
      ele.add(new ArrayList<Component>());
      ele.get(0).add(lbl8);
      
      JLabel lbl9 = new JLabel( "<html><nobr>" +R.string.ResAns.replaceAll("\n","<br>") + "</nobr></html>");
      lbl9.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl9.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      //lbl9.setPreferredSize(new Dimension(70, 25));
      lbl9.setHorizontalAlignment(SwingConstants.LEFT);
      lbl9.setAlignmentX(LEFT_ALIGNMENT);
      lbl9.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(2,2,2,2);
      c.weightx = 0;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0;
      c.anchor = GridBagConstraints.WEST;
      c.insets = new Insets(0,1,1,1);
      lbl9.setFont(new Font("Times",Font.PLAIN,13));
      tl7.add(lbl9,c);
      ele.add(new ArrayList<Component>());
      ele.get(1).add(lbl9);
      
      JLabel lbl10 = new JLabel( "<html>" +R.string.instre.replaceAll("\n","<br>") + "</html>");
      components.put(R.id.instr,lbl10);
      lbl10.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl10.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(2,2,2,2);
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 2;
      c.weighty = 0.5;
      lbl10.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll2.add(lbl10,c);
      
   }

   public EnquireActivity()
   {
      super();
      try{buildToolbar("enquire.xml");}catch(Exception e){}
      makeElements();
      pack();
      setPreferredSize(new Dimension(windwidth, windheight));
   }
   
   static public void main(String [] args)
   {
        loadparams();
        folderok();
        String role = get("role", null);
        if (role != null && role.equals("Response"))
        {
            new  MainActivity().onCreate(); 
        }
        else 
            (new EnquireActivity()).onCreate();
   }
     
 

    int  login(Msg m)
    {
        int i = 0;
        for (; i < buddys.size(); i++)
            if (buddys.get(i).user.equals(m.sid)) break;
        if (i == buddys.size())
        {
            buddys.add(new Buddy(m.sid,m.password,defaultpermit));
            resdb.saveOthers("Enquirer", r.session, -2, user2String());
            return defaultpermit;
        }
        else if (MainActivity.encryt(m.password)!=MainActivity.encryt(buddys.get(i).pin))
        {
            buddys.set(i,new Buddy(m.sid,m.password,Math.min(defaultpermit, buddys.get(i).permission)));
            resdb.saveOthers("Enquirer", r.session, -2, user2String());
            return defaultpermit;
        }
        return buddys.get(i).permission;
     
    }
    boolean  uploadable = true;
    private void educate( String msg)
    {
        int x = JOptionPane.showConfirmDialog(this, msg);
        uploadable = x == 1;
        
    }
 
    void refresh()
    {
        r.content = content();
        removeeverything();
        directory =  new File(AppCompatActivity.uploadfolder);
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0,0,2,0); 
        toolBar = new JToolBar();
        toolBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        setJMenuBar(menuBar);  
        //try{buildToolbar("main.xml");}catch(Exception e){}
        contentpane.add(toolBar,c);
        try{buildToolbar("enquire.xml");}catch(Exception e){}
        makeElements();
        
        spinner = (JComboBox) findViewById(R.id.lblses);
        spinner.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
        changed((JComboBox)(e.getSource()));
    }
});
              
        lbltimes = (JLabel) findViewById(R.id.times);
        tmyip = (JLabel) findViewById(R.id.myip);
        tl = (JPanel) findViewById(R.id.maintable);
        instr = (JLabel) findViewById(R.id.instr);
        instr.setText(instr.getText().replaceAll("([0-9])", "\n$1"));
        NR = 0;
        NQ = 0;
        onResume1();
        setTitle(getString(R.string.enquirer));
        
        recev = (JButton)findViewById(R.id.receive);
        newsm = (JMenuItem)findItem(R.id.newm);
       
        pack();
        setPreferredSize(new Dimension(windwidth, windheight));
         
    }
    
    private void changed(JComboBox b)
    {
        
        int position = b.getSelectedIndex();
        if (position == 0)
        {
           downloads = new ArrayList<>(); 
           String s = sessions.get(position);
           r.session = s;
        }
        else
        {
            String s = sessions.get(position);
            r.session = s;
            downloads = str2list(resdb.getOthers("Enquirer",s, -1));
            parseUserInfo(resdb.getOthers("Enquirer",s, -2));
        }
        missed = null;
    }
    
    protected void onCreate() 
    {
        //uploadable = false;
        directory =  new File(AppCompatActivity.uploadfolder);
        if (!directory.exists())
        {
            directory.mkdirs();
        } 
        num = getIntent().getIntExtra("num", 0);
        
       // educate(getString(R.string.whysave));
        setTitle(getString(R.string.enquirer));
        
        r = new Response(0, getString(R.string.NewSession), System.currentTimeMillis(), 5, null);
        spinner = (JComboBox) findViewById(R.id.lblses);
        lbltimes = (JLabel) findViewById(R.id.times);
        tmyip = (JLabel) findViewById(R.id.myip);
        tl = (JPanel) findViewById(R.id.maintable);
        instr = (JLabel) findViewById(R.id.instr);
        instr.setText(instr.getText().replaceAll("([0-9])", "\n$1"));
         
        DBHelper.updateserver();
        if (num != 0) 
        {
            resdb = new Responsetbl(null);
            r = resdb.getrecord("Enquirer", num);
            downloads = str2list(resdb.getOthers("Enquirer",r.session,-1));
            parseUserInfo(resdb.getOthers("Enquirer",r.session, -2));
            missed = null;
        }
        else if (resdb!=null)
            r.session = resdb.weeksession();
        onResume1();
        spinner.addActionListener (new ActionListener () 
        {
           public void actionPerformed(ActionEvent e) 
           {
               changed((JComboBox)(e.getSource()));
           }
       });  
        if (num == 0)
          spinner.setSelectedItem(r.session);
           
        recev = (JButton)findViewById(R.id.receive);
        newsm = (JMenuItem)findItem(R.id.newm);
        setTitle(getString(R.string.enquirer));
    }

   void endis(boolean b)
    {
        int ids[] = new int[]{R.id.selfans,R.id.newm,R.id.browse, R.id.delete,  R.id.response,R.id.exit, R.id.question,R.id.answer,R.id.detail,R.id.setting,R.id.download};

        for (int j=0; j < ids.length; j++)
        {
            activemenu(ids[j],b);
        }
    }
    protected void onResume1() 
    {
        if (missed != null) {
            resdb = new Responsetbl(null);
            r = new Response(missed);
        } else if (resdb == null) {
            resdb = new Responsetbl(null);
        }
        sessions = new ArrayList<>();
        sessions.add(getString(R.string.NewSession));
        resdb.resindex("Enquirer", sessions, null);
        
        for (int j=0; j < sessions.size(); j++)
            spinner.addItem(sessions.get(j));
                
        lbltimes.setText(Responsetbl.dstr1(new Date(r.timemoment)));
        maxid = R.id.myip + 8;
        filldata();
        int j = 0;
        for (; j < sessions.size(); j++) 
        {
            if (sessions.get(j).equals(r.session))
            {
                spinner.setSelectedIndex(j);
                break;
            }
        }
        

    }

    private void deleter() {
        if (r.num > 0) {
            resdb.delete("Enquirer", r.num);
        }
        newr();
    }
    private void removearrow(int r, boolean all)
    {
        if (r >= ele.size())return;
        ArrayList<Component> tr = ele.get(r);
        int N = tr.size();
        for (int j=N-1; j >=(all?0:1); j--)
        {
            tl.remove(tr.get(j));
            tr.remove(j);
        }
        if (all)ele.remove(r);
    }
    private void newr() {

        missed = null;
        removearrow(NR + 2,true);
        for (; NR > 0; NR--) 
        {
            removearrow(NR + 1,true);
        }
        removearrow(1,false);
        removearrow(0,false);
         
        NQ = 0;
        r.questions.clear();
        answers.clear();
        r = new Response(0, getString(R.string.NewSession), System.currentTimeMillis(), 5, null);
        spinner.setSelectedIndex(0);
        lbltimes.setText(Responsetbl.dstr(new Date(r.timemoment)));
        filldata();
        activemenu(R.id.newm,false);
        
    }
    
    private void loadother(String ses) 
    {
        missed = null;
        if (NR>0) removearrow(NR + 2,true);
        for (; NR > 0; NR--) 
        {
            removearrow(NR + 1,true);
        }
        removearrow(1,false);
        removearrow(0,false);
         
        NQ = 0;
        r.questions.clear();
        answers.clear();
        r = resdb.getrecord("Enquirer", ses);
        
        r.session = ses;
        String [] xx = (new CSVParse(resdb.getOthers("Enquirer",r.session,-1),'"', new String[]{"\n"})).nextRow();
        for (String y:xx)downloads.add(y);
        parseUserInfo(resdb.getOthers("Enquirer",r.session, -2));
        lbltimes.setText(Responsetbl.dstr(new Date(r.timemoment)));
        filldata();
        //activemenu(R.id.newm,false);
        //if (newsm != null) newsm.setVisible(false);
    }

    protected void save(String callback)
    {
        
        missed = r.toString();
        if (r.session.equals(getString(R.string.NewSession))) 
        {
            pop(getString(R.string.sessionname), getString(R.string.EnterName),callback, 0);
        }
        else 
        {
            r.content = content();
            int n = resdb.save("Enquirer", r);
            
            if (n > 1) {
                r.num = n;

            } else if (r.num == 0 && n == 1) {
                r.num = 1;

            } else if (n <= 0) {
                callback = null;
                mytoast( DBHelper.err.toString());
            }
            resdb.saveOthers("Enquirer", r.session, -1, list2String(downloads));
            resdb.saveOthers("Enquirer", r.session, -2, user2String());
            if (callback != null) {
                if (callback.equals("newr")) newr();
                else gotomain();
            }
        }
    }

    void mytoast(String m)
    {
        JOptionPane.showMessageDialog(this, m );
    }
    void filldata()
    {
        for (int i = 0; i < r.numberq; i++) {
            addColumn(false);
            NQ++;
        }
        addColumn(true);
        if(NQ == r.questions.size()-1)
        {
            r.questions.remove(NQ);
            answers.remove(NQ);
        }
        if (r.content == null) {
            r.content = content();
        } else {
            CSVParse p = new CSVParse(r.content, '"', new String[]{",", "\n"});
            String[][] ds = p.nextMatrix();
            int i;
            if (ds.length > 1)
                for (i = 0; i < NQ && i + 1 < ds[1].length; i++) {
                    if (i < r.questions.size())
                        r.questions.set(i, ds[1][i + 1]);
                    else
                        r.questions.add(ds[1][i + 1]);
                }
             ArrayList<Component> tr = ele.get(1);
            if (ds.length > 2)
                for (int j = 1; j <= r.numberq && j <= ds[2].length; j++) {
                    JTextField e = (JTextField) (tr.get(j));
                    e.setText(ds[2][j]);
                    if (j - 1 < answers.size())
                        answers.set(j - 1, ds[2][j]);
                    else
                        answers.add(ds[2][j]);
                }
            NR = 0;
            for (i = 3; i < ds.length; i++) 
            {
                if (ds[i].length == 1 && ds[i][0].matches("^[0-9]+ .*"))
                    break;
                addrow(tl);
                tr = ele.get(NR + 2);
                for (int j = 0; j <= NQ + 1 && j < ds[i].length; j++) {
                    JLabel t = (JLabel) (tr.get(j));
                    t.setText(ds[i][j]);
                    if (ds[i][j].equals(GOOD))
                        t.setForeground(ParseLayout.hex2Rgb("#00ff00"));
                    else if (i < ds.length - 1 
                            && j > 0 
                            && j < answers.size() + 1 
                            && answers.get(j - 1).equals("") == false 
                            && MainActivity.same(answers.get(j - 1), ds[i][j]) == false)
                        t.setForeground(ParseLayout.hex2Rgb("#ff0000"));
                }
                NR++;
            }
            if (NR > 1) NR--;
             
        }
        
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
    }

    private void pop(String title, String msg, final String callback, final int i) {
        Object x0 = JOptionPane.showInputDialog( this,msg, title,
                                     JOptionPane.QUESTION_MESSAGE,
                                     (Icon)(new ImageIcon(iconpath("question"))),
                                     null,
                                     (i>0)?r.questions.get(i - 1):"");
        if (x0 == null) return;
        String x = x0.toString();
        
        if (x != null)
        {
                 x = x.trim();
                int j = 0;
                for (; j < sessions.size() && !sessions.get(j).equals(x); j++) ;
                if (j < sessions.size()) 
                {
                    spinner.setSelectedIndex(j);
                    r.session = x;
                    activemenu(R.id.newm, true);
                    save(callback);

                } else if (x.length() > 0 && !x.equals(getString(R.string.NewSession))) {
                    if (i == 0) 
                    {
                        sessions.add(x);
                        spinner.addItem(x);
                        spinner.setSelectedIndex(sessions.size() - 1);
                        r.session = x;
                        activemenu(R.id.newm, false);
                        save(callback);

                    } else
                        r.questions.set(i - 1, x);
                }
            }
         else
        {
        
                if (callback != null) {
                    if (callback.equals("newr"))
                        newr();
                    else
                        gotomain();
                }
                 
         }
       

    }

   

    void regrade(int j, String oldsanswer, String standardanswer) {
        int noredcount = 0;
        for (int i = 2; i < ele.size()-1; i++) {
            JLabel cell = (JLabel) ((ele.get(i)).get(j + 1));
            
            boolean r = cell.getForeground().equals(ParseLayout.hex2Rgb("#ee0000"));
            String receivedAnswer = cell.getText();
            if (receivedAnswer.equals(GOOD) || cell.getForeground().equals(Color.GREEN)) 
            {
                receivedAnswer = oldsanswer;
            }

            if (standardanswer.equals("")) {
                cell.setText(receivedAnswer);
                cell.setForeground(ParseLayout.hex2Rgb("#000000"));
            } else if (MainActivity.same(standardanswer, receivedAnswer)) {
                cell.setText(GOOD);
                cell.setForeground(ParseLayout.hex2Rgb("#00ee00"));
            } else {
                cell.setText(receivedAnswer);
                if (!receivedAnswer.trim().equals(""))
                    cell.setForeground(ParseLayout.hex2Rgb("#ee0000"));
            }
            boolean r1 = cell.getForeground().equals(ParseLayout.hex2Rgb("#ee0000"));
            if (!r1) noredcount++;
            int change = 0;
            if (r && !r1)
            {
                change = 1;
            } else if (!r && r1)
            {
                change = -1;
            }
            if (change!=0) modifynum(i, j+1,  change);
        }
        if (NR>0 && NR + 2 < ele.size())  
            setv(NR+2,j+1, ""+noredcount);
    }

    

    void setv(int i, int j, String s) {
        if (i == ele.size())
            addrow(tl);
        JLabel x = (JLabel)ele.get(i).get(j);
        if (x != null && s != null) x.setText(s);
    }

    String retrv(int i, int j) {
        JLabel x = (JLabel)ele.get(i).get(j);
        if (x != null) return x.getText();
        return null;
    }


    void swap() {
        for (int i = 0; i <= NR + 2 && i < ele.size(); i++) {
            if (i != 1) {
                String str = retrv(i, NQ);
                setv(i, NQ + 1, str);

                if (i > 0)
                    setv(i, NQ, "");
                else
                    setv(0, NQ, "" + NQ);
            }
           if (i!=1) ((JLabel)ele.get(i).get(NQ)).setHorizontalAlignment(SwingConstants.LEFT);
           if (i!=1)  ((JLabel)ele.get(i).get(NQ+1)).setHorizontalAlignment(SwingConstants.RIGHT);
        }
        setv(0, NQ, "" + NQ);
    }


    
    void retitle() {
        ArrayList<Component> t1 = ele.get(0);
        int N = t1.size();
        for (int i = 1; i < N-1; i++) {
            JLabel t = (JLabel) t1.get(i);
                t.setText("" + i);
        }
    }
    void delColumn(int c) {
        answers.remove(c);
        r.questions.remove(c);
        NQ--;
        int N = ele.size();
        for (int i = 0; i < N; i++) 
        {
            ArrayList<Component> tr = ele.get(i);
            tl.remove(tr.get(c+1));
            if (c < tr.size()-2)
            tr.remove(c+1);
        }
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
    }

    void addColumn(boolean grand) 
    {
        if (r.questions.size() == NQ)
        {
            r.questions.add("");
            answers.add("");
        }
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 1;
        c.ipady = 0;
        c.gridx = ele.get(0).size();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.1;
        c.weighty = 0;
        Component elej;
        
        for (int j=0; j <  NR + (NR>0?3:2); j++)
        {
            if (j ==4 && NQ==6)
            {
               // System.out.println(j);
            }
            c.insets = new Insets(j==0?1:0, 1, 1, 0);
            c.gridy = j;
            ArrayList<Component> tr =   ele.get(j);
            elej = (j==1)? (new JTextField()): (new JLabel("" + (NQ + 1)));
            elej.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
             
            if (j==1)
            {
                ((JTextField)elej).setOpaque(true);
                elej.setForeground(ParseLayout.hex2Rgb("#000000"));
                elej.setBackground(ParseLayout.hex2Rgb("#ffffff"));
                ((JTextField)elej).setBorder(javax.swing.BorderFactory.createEmptyBorder());
            }
            else
            {
                ((JLabel)elej).setOpaque(true);
                elej.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                elej.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                if (j>1) ((JLabel)elej).setText("");
                else if (j==0 && grand) ((JLabel)elej).setText(R.string.Total);
            }
            if (j == 0)
            {
                elej.setForeground(ParseLayout.hex2Rgb("#0000ff"));
                elej.addMouseListener(new MouseListener() 
                {
                    @Override
                    public void mousePressed(MouseEvent e){}                        
                    public void mouseReleased(MouseEvent e){}                        
                    public void mouseEntered(MouseEvent e){}                        
                    public void mouseExited(MouseEvent e){}                        
                    public void mouseClicked(MouseEvent e) {
                        JLabel tv = (JLabel) (e.getSource());
                        try {
                            int i = Integer.parseInt(tv.getText().trim());
                            pop(getString(R.string.QuestionText), getString(R.string.Enter) + i, null, i);
                        } catch (Exception e1) {
                           if (listening == 0) editquestion();
                        }
                    }
                });
            }
        
            elej.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e1) 
            {
                ArrayList<Component>  tr = ele.get(1);
                JTextField e = (JTextField) e1.getSource();
                int i=0;
                for (; i < tr.size(); i++)
                {
                    if ((JTextField)tr.get(i+1) == e)
                        break;
                }

                if ( i < NQ) {
                    String str = e.getText().trim();

                    if (answers.size() <= i)
                        answers.add("");
                    if (!MainActivity.same(answers.get(i), str)) {
                        regrade(i, answers.get(i), str);
                    }

                    answers.set(i, str);
                }
            }
            });
            tr.add(elej);
            tl.add(elej,c);
        }
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
    }


    public void addrow(JPanel tl) 
    {
        ArrayList<Component> tr = new ArrayList();
        int y = ele.size();
        for (int j = 0; j <= NQ + 1; j++) 
        {
            JLabel labelj = new JLabel("", j < NQ+1? SwingConstants.LEFT:SwingConstants.RIGHT);
           
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(0, 1, 1, j==0?1:0);
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.gridx = j;
            c.gridy = y;
            c.weighty =0;
            c.weightx = 1.0/(NQ + 2);
            
            labelj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
            labelj.setOpaque(true);
            labelj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
            if (j > 0)
                labelj.setForeground(Color.BLACK);
            else
                labelj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
            labelj.setText("");
            tr.add(labelj);
            tl.add(labelj,c);
            
        }
        ele.add(tr);
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
    }

  
    protected void onStop()
    {
         if (listening == 1)
         {
             listening = 0;
             new  StopListen().execute();
             endis(true);
         }
      
    }

    class Clickopen implements MouseListener
    {
        public Clickopen( )
        {

        }

        public void mousePressed(MouseEvent e){}                        
        public void mouseReleased(MouseEvent e){}                        
        public void mouseEntered(MouseEvent e){}                        
        public void mouseExited(MouseEvent e){}                        
        public void mouseClicked(MouseEvent e)
        {
            if (listening == 1)
            {
               // mytoast( getString(R.string.stilllisten));
               // return;
            }
            String msg = ((JLabel)e.getSource()).getText();
            File f = new File(directory, msg);
            if (f.exists())
            {
                String ext = msg.toLowerCase().replaceFirst("[^\\.]+\\.","");


                if (ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {
                  //  Intent i = new Intent(getBaseContext(), GalleryActivity.class);
                  //  i.putStringExtra("path", f.getAbsolutePath());
                  //  startActivity(i);
                  new GalleryActivity(f.getAbsolutePath());
                }
                else
                {
                    MainActivity.otheropen(f);
                }


            }
          //  startActivity(myIntent);

        }
    }
    class Clistener implements MouseListener
    {
        public Clistener(int j)
        {
            k = j;
        }
        int k;
        public void mousePressed(MouseEvent e){}                        
        public void mouseReleased(MouseEvent e){}                        
        public void mouseEntered(MouseEvent e){}                        
        public void mouseExited(MouseEvent e){}                        
        public void mouseClicked(MouseEvent e)
        {
            int r = ele.size() - 1;
            HashMap<String, Integer> histo = new HashMap<String, Integer>();
            for (int j = 2; j < r; j++)
            {
                String x = ((JLabel)ele.get(j).get(k)).getText();
                if (histo.get(x) == null) histo.put(x, 1);
                else histo.put(x, 1 + histo.get(x));
            }
            String msg = "";
            for (String x : histo.keySet())
            {
                msg += x + ": " + histo.get(x) + "\n";
            }
            alertmsg(histo);
        }
    }


    protected void deviceip() 
    {
        String str = MainActivity.getIpAddress();
        tmyip.setText(str);
        Date d = new Date();
        enc = new AES("" + d.getYear() + d.getMonth() + d.getDate() + str);
    }


 
    void alertmsg(HashMap<String,Integer> histo)
    {
        StringBuffer s = new StringBuffer("<html><Table>"); 
        for (String x : histo.keySet())
        {
            s.append("<tr><td align=left>" + 
                    x  + "</td><td align=right>" 
                    + histo.get(x).toString() + "</td></tr>");
        }
        JOptionPane.showMessageDialog(this, s.toString() + "</html>");
    }

    Menu menu0;
   
    private String replyDatagram() {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < NQ; i++) {
            str.append("\"" + r.questions.get(i).replaceAll("\"", "\"\"") + "\",\"" + answers.get(i).replaceAll("\"", "\"\"") + "\"\n");
        }
        str.append("\"\",\"\"\n");
        str.append("\"" + r.session + "\"," + r.timemoment);
        return str.toString();
    }



    private void showmsg(final String msg) {
         (new Runnable() 
         {
            @Override
            public void run() 
            {
                instr.setText(instr.getText() + msg + "\n");
            }
        }).run();
    }
    

    String content() {
        int G = 1;
        StringBuffer buf = new StringBuffer();
        
        for (int i = 0; i < NR+2; i++) {
           // TableRow tw = (TableRow) tl.get(i);
            for (int j = 0; j <= NQ + 1; j++) {
                buf.append("\"");
                if (i == 1 && j > 0) {
                    JTextField t = (JTextField) ele.get(i).get(j);//[i][j];
                    buf.append(t.getText().trim().replaceAll("\"", "\"\""));
                } else {
                    JLabel t = (JLabel) ele.get(i).get(j);
                    buf.append(t.getText().trim().replaceAll("\"", "\"\""));
                }
                buf.append("\"");
                if (j < NQ + 1) buf.append(",");
                else buf.append("\n");
            }
            if (i == 0)
                for (int j = 0; j <= NQ + 1; j++) {
                    buf.append("\"");
                    if (j == 0 || j == NQ + 1)
                        buf.append("");
                    else
                        buf.append(r.questions.get(j - 1).replaceAll("\"", "\"\""));
                    buf.append("\"");
                    if (j < NQ + 1) buf.append(",");
                    else buf.append("\n");
                }

        }
        return buf.toString().replaceFirst("\n$", "");
    }

    private int listening = 0;

     


    private void toresponse() {

        //save("gotomain");
        gotomain();
    }

    private void gotomain() {
        savep("role", "Response" );
        new MainActivity().onCreate();
        dispose();
    }

    private void finish1() {
        dispose();
        System.exit(0);
    }

    private void addq() {
        addColumn(false);
        NQ++;
        swap();
    }

     

    private void newse() {


        save("newr");
    }

    private class StopListen extends SwingWorker<String, String> {
        protected String doInBackground()  //doInBackground can not access UI!!!!
        {
            try {
                InetAddress sendAddress = InetAddress.getByName("127.0.0.1");
                Socket s = new Socket("127.0.0.1",  AppCompatActivity.serverport);
                String str = "0-1:0.0.0.0:-1: \n";
                OutputStream os = s.getOutputStream();
                os.write(str.getBytes());
                s.close();
            } catch (Exception e) {
            }
            return "";
        }
    }
    void localsend(String w)
    {
        new Localsend(w).execute();
    }
    private class Localsend extends SwingWorker<String, String> {
        String w = "";
            Localsend(String w){this.w = w;}
        protected String doInBackground()  //doInBackground can not access UI!!!!
        {
            
            try {
                InetAddress sendAddress = InetAddress.getByName("127.0.0.1");
                Socket s = new Socket("127.0.0.1",  AppCompatActivity.serverport);
                OutputStream os = s.getOutputStream();
                os.write((w + "\n").getBytes());
                InputStream is = s.getInputStream();
                byte [] buf = new byte[2048];
                int k = is.read(buf);
                String str = "";
                if (k>0) str = new String(buf, 0, k);

                s.close();
            } catch (Exception e) {
            }
            return "";
        }
    }

    private ArrayList<String> myvotes = new ArrayList<>();
    private void selfanswer()
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new BorderLayout());
        JPanel layout = new JPanel(new GridBagLayout());
        final JTextField[] inputs = new JTextField[NQ+1];
        String uid =  get("unid",this);
        if (uid==null || uid.equals("")) uid = getString(R.string.Self);
         
        inputs[0] = addnewrow(layout, getString(R.string.uid), uid, 0);
        for (int j=0; j < NQ; j++)
        {
            if (j == myvotes.size()) myvotes.add("");
            inputs[j+1] = addnewrow(layout, getString(R.string.question) + (j + 1), myvotes.get(j), j+1);
        }
        f.getContentPane().add(layout,BorderLayout.PAGE_START);
        f.setTitle(getString(R.string.Self));
        JButton ok = new JButton(R.string.OK);
        f.getContentPane().add(ok,BorderLayout.LINE_START);
        ok.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                String mid =  inputs[0].getText();
                if (ele.size() > 3 && ((JLabel)ele.get(2).get(0)).getText().trim().replaceFirst("\\.", "").replaceFirst("[0-9]+", "").equals(""))
                {
                    mid = tmyip.getText();
                    int j = mid.indexOf(".");
                    if (j ==0) mid = "0" + mid;
                    else if (j==-1)
                        mid = "0." + mid;
                }
               for (int i=0; i < NQ; i++)
               {
                   int nn = (i + 1);
                   String ans = inputs[i+1].getText().trim();
                   myvotes.set(i,ans);



                   try {
                       Msg m = new Msg("0" + ans.length() + ":" + mid + ":p:" + nn + ":" + ans);
                       Runupdate x = new Runupdate(m);
                       x.run(); //"0" + myAnswer.getBytes().length + ":" + myid + ":" + questionN + ":" +   myAnswer;
                   } catch (NumberFormatException e1) {
                   }
               }
            }
        });
        
        JButton can = new JButton(R.string.Cancel);
        f.getContentPane().add(can,BorderLayout.LINE_END);
        ok.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
            }
        });
        f.pack();
        f.setVisible(true);
         
    }

    private void detail() {
        Intent intent = new Intent(this, DetailActivity.class);

        this.startActivity(intent);
    }

    private void editquestion()
    {
       Intent intent = new Intent(this, QuestionActivity.class);
                intent.putStringExtra("enquirer","1");
                intent.putStringArrayListExtra("question",r.questions);
                this.startActivityForResult(intent, EDIT_QUESTION_CODE);
    }

    private void browse() {
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putStringExtra("which", "Enquirer");
        if (!spinner.getSelectedItem().toString().equals(R.string.NewSession))
            intent.putStringExtra("session", spinner.getSelectedItem().toString());
        this.startActivity(intent);
    }

     
    public void onOptionsItemSelected(int item) {

        Intent intent;
        switch (item) {

            case R.id.response:
                 toresponse();

                break;
            case R.id.receive:
                if (listening == 0)
                {
                    listening = 1;
                    deviceip();
                    new TcpMsgThread().start();
                    
                    recev.setIcon(new ImageIcon(iconpath("earn")));
                            
                    endis(false);

                } else
                {
                    onStop();
                    listening = 0;
                    recev.setIcon(new ImageIcon(iconpath("ear")));
                    tmyip.setText("");
                }
                break;
            case R.id.email:
              
                break;
            case R.id.exit:
                 this.dispose();

                break;
            case R.id.addq:
               // localsend("0000" +  "012:q:1:XYZ");
                addq();
                break;
            case R.id.newm:

                 newse();
                break;
            case R.id.save:
                save(null);
                break;
            case R.id.delete:
                deleter();

                break;
            case R.id.browse:
                 browse();
                break;
            case R.id.detail:
                detail();
                break;
            case R.id.download:
                opendownload();
                break;
            case R.id.selfans:
                intent = new Intent(this, SelfActivity.class);
                while (r.questions.size() < NQ)
                    r.questions.add("");
                intent.putStringArrayListExtra("question",r.questions);
                intent.putStringArrayListExtra("myvotes",myvotes);
                intent.putStringExtra("myid", myid);
                this.startActivityForResult(intent, EDIT_SELF_CODE); 
                break;
            case R.id.setting:
                intent = new Intent(this, SettingActivity.class);
                intent.putStringExtra("which","enq");
                this.startActivityForResult(intent, EDIT_SETTING_CODE);
                break;
            case R.id.user:
                intent = new Intent(this, UserActivity.class);
                intent.putStringExtra("enquirer","1");
                intent.putStringExtra("user",user2String());
                this.startActivityForResult(intent, EDIT_USER_CODE);
                break;
            case R.id.question:
                editquestion();
                break;
            case R.id.answer:
                intent = new Intent(this, SelfActivity.class);
                intent.putStringExtra("enquirer","1");
                while (r.questions.size() < NQ)
                    r.questions.add("");
                intent.putStringArrayListExtra("question",r.questions);
                intent.putStringArrayListExtra("answer",answers);
                this.startActivityForResult(intent, ANSWER_QUESTION_CODE);
                break;
            case R.id.lang:
                switchlang(EnquireActivity.class, r.num);
                break;
            default:
                 super.onOptionsItemSelected(item);
        }

        
    }
    String myid = "";
    String user2String()
    {
       StringBuffer s = new StringBuffer();
       for (int i=0; i < buddys.size(); i++)
       {
           if (s.length()>0)
               s.append("\n");
           s.append(buddys.get(i).toString());
       }
       return s.toString();
    }
    
    void parseUserInfo(String x)
    {
        if (x == null || x.length() == 0) return;
        buddys.clear();
        String [] ptr = x.split("\n");
        for (int j=0; j < ptr.length; j++)
        {
            buddys.add(new Buddy(ptr[j]));
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        
        if (requestCode == ANSWER_QUESTION_CODE) {
            if (resultCode == RESULT_OK) 
        {
               
               ArrayList<String> inputs = data.getStringArrayListExtra("answers");
               if (inputs == null) return;
               for (int i=0; i < NQ; i++)
               {
                   int nn = (i + 1);
                   String ans = inputs.get(i); 
                   if (ans == null || ans.equals(""))
                       ans = "";
                   ans = ans.trim();
                   
                   ((JTextField)ele.get(1).get(nn)).setText(ans);
                    regrade(i, answers.get(i), ans); 
                    if (answers.size() <= i)
                       answers.add(ans);
                   else            
                       answers.set(i,ans);
               }
        }
        }
        else if (requestCode == EDIT_SELF_CODE) {
            if (resultCode == RESULT_OK) 
        {
               myid = data.getStringExtra("user");
               ArrayList<String> inputs = data.getStringArrayListExtra("answers");
               if (inputs == null) return;
               for (int i=0; i < NQ; i++)
               {
                   int nn = (i + 1);
                   String ans = inputs.get(i); 
                   if (ans == null || ans.equals(""))
                       ans = "";
                   ans = ans.trim();
                   if (myvotes.size() <= i)
                       myvotes.add(ans);
                   else            
                       myvotes.set(i,ans);
                   try {
                       Msg m = new Msg("0" + ans.length() + ":" + myid + ":p:" + nn + ":" + ans);
                       Runupdate x = new Runupdate(m);
                       x.run(); //"0" + myAnswer.getBytes().length + ":" + myid + ":" + questionN + ":" +   myAnswer;
                   } catch (NumberFormatException e1) {
                   }
               }
        }
        }
       
        if (requestCode == EDIT_USER_CODE) {
            if (resultCode == RESULT_OK) 
            {
                String  x = data.getStringExtra("user");
                parseUserInfo(x);
                resdb.saveOthers("Enquirer",r.session,-2, user2String());
            }
        }
        if (requestCode == EDIT_QUESTION_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> x = data.getStringArrayListExtra("question");
                if (x!=null) {

                    for (int i=NQ; i < x.size(); i++)
                    {
                        addq();
                    }
                    r.questions = x;
                }
                ArrayList<Integer> y = null;//data.getIntegerArrayListExtra("dels");
                if (y!=null)
                {
                    for (int j=0; j < y.size(); j++)
                    {
                        delColumn(y.get(j).intValue());
                    }
                    retitle();
                }
            }
        }
        else if (requestCode == DOWNLOAD_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> x = data.getStringArrayListExtra("download");
                if (x!=null && x.size()>0) 
                {
                    downloads = x;
                    
                }
                StringBuffer y = new StringBuffer();
                    for (int i=0; i < downloads.size(); i++)
                    {
                        if (y.length()>0)
                            y.append("\n");
                        y.append("\n" + downloads.get(i));
                    }
                resdb.saveOthers("Enquirer",r.session,-1, y.toString());
            }
        }
        else if (requestCode == EDIT_SETTING_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                this.setVisible(true);
                if (data.getIntExtra("refresh",0) == 1)
                refresh();
            }
        }

    }

    String getTextstr(JTextField v) {
        String ans = v.getText().trim();
        ans = ans.replaceAll("\"", "\"\"");
        if (ans.contains(",") || ans.contains("\n"))
            ans = "\"" + ans + "\"";

        return ans;
    }

    String getTextstr(JLabel v) {
        String ans = v.getText().trim();
        ans = ans.replaceAll("\"", "\"\"");
        if (ans.contains(",") || ans.contains("\n"))
            ans = "\"" + ans + "\"";

        return ans;
    }

    void tryemail(String addr) {

       
    }

    String pad(int x) {
        if (x < 10) return "0" + x;
        return "" + x;
    }

    void getans() {

        for (int j = 0; j < NQ; j++) {
            JTextField e = (JTextField) ele.get(1).get(j+1);
            answers.set(j, e.getText().trim());

        }

    }

    


    private class TcpMsgThread extends Thread {

        Socket socket;

        

        public TcpMsgThread() {
            super();
            
        }



        @Override
        public void run() {

            java.util.concurrent.Semaphore wakeLock = new java.util.concurrent.Semaphore(1);
            try{wakeLock.acquire();}catch(Exception e){}

            int questionN = 0;
            ServerSocket ss = null;
            String err="";
            try
            {
                ss = new ServerSocket(AppCompatActivity.serverport);


                while (true)
                {
                    String er = "";
                    try
                    {
                        socket = ss.accept();
                        String rip = socket.getRemoteSocketAddress().toString().replaceFirst(":.*","").replaceFirst("^[^0-9]","");
                        String lip = socket.getLocalAddress().toString().replaceFirst("^[^0-9]","");
                        if (rip.equals(lip)  ||  rip.equals("127.0.0.1"))
                        {
                            socket.close();
                            listening = 0;
                            break;
                        }
                        ReceiveData rd = new ReceiveData(socket);
                        clients.add(rd);
                        pool.execute(rd);
                    }
                    catch (SocketTimeoutException e) { er = e.toString(); }
                    catch (SocketException e) { er = e.toString(); }
                    catch (IOException e) { er = e.toString(); }
                    if (!er.equals(""))
                    {
                        new Thread(new ShowErr(er)).start();
                    }
                }
            }
            catch (SocketException e)
            {
                e.printStackTrace();
                err = e.toString();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                err = e.toString();
            }
            finally
            {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (Exception e) {
                    }
                }
            }
            if (!err.equals(""))
            {
                mytoast(err);
            }
            wakeLock.release();

        }
    }





    class Runupdate implements Runnable {

        String receivedAnswer;
        String sid;
        int questionNumber;
        Context c;
        boolean isfile = false;
        int EMPTY = 0, WRONG =1, CORRECT = 2;
        
       // private TableLayout tl;

        public Runupdate(Msg msg) {
           //this.tl = tl;
            this.c = c;
            this.receivedAnswer = msg.answer;
            this.sid = msg.sid;
            this.questionNumber = msg.questionNumber;
            isfile = msg.isfilename;
        }


        public void run() {
            try {
              //  showmsg(receivedAnswer + ":" + sid + ":" + questionNumber);
                getans();

                int l = -1;
                int N = ele.size();

                try {
                    for (int j = 2; j < N; j++) 
                    {
                        ArrayList<Component> row =   ele.get(j);
                        if (row == null) break;
                        JLabel cell = (JLabel) row.get(0);
                        String sidj = cell.getText().trim();
                        if (sid.equals(sidj) || sidj.equals("")) 
                        {   //look for SID row or blank row
                            l = j;
                            break;
                        }
                    }
                } catch (Exception e) {
                }
                if (l == -1) //if not found, create a row
                {
                    addrow( tl);
                    NR++;
                    l = NR + 1;
                    if (NR == 2) {
                        instr.setText("");
                    }
                    if (NR == 1)
                    {
                        addrow(tl);
                        for (int j = 0; j <= NQ + 1; j++) 
                        {
                           ele.get(NR + 2).get(j).setForeground(ParseLayout.hex2Rgb("#0000ff"));
                           ele.get(NR + 2).get(j).addMouseListener(new  Clistener(j));
                        }
                    }
                    else
                    {
                        for (int j = 0; j <= NQ + 1; j++) 
                        {
                            ele.get(NR + 1).get(j).addMouseListener(null);
                            ele.get(NR + 1).get(j).setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                            setv(NR + 2, j, retrv(NR + 1, j));
                            ele.get(NR + 2).get(j).addMouseListener(new  Clistener(j));
                            ele.get(NR + 2).get(j).setForeground(ParseLayout.hex2Rgb("#0000ff"));
                            if (j > 0) setv(NR + 1, j, "");

                        }
                    }

                    setv(l, 0, sid);
                    setv(NR + 2, 0, getString(R.string.Total));

                }
                ArrayList<Component> row = ele.get(l);
                JLabel cell = (JLabel) row.get(0);
                cell.setText(sid);
                cell = (JLabel) row.get(questionNumber);
                String standardanswer = answers.get(questionNumber - 1).trim();
                int change = 0;
                 
                int oldstatus = EMPTY;
                if (!cell.getText().trim().equals("") )
                {
                     if (cell.getForeground().equals(ParseLayout.hex2Rgb("#ee0000") ) )
                         oldstatus = WRONG;
                     else
                         oldstatus = CORRECT;
                }
                cell.setText(receivedAnswer);
                if (NR == 1) 
                {
                     setv(3, questionNumber, "0");
                }
                if (standardanswer.equals(""))   // no answer
                {
                    if (oldstatus == WRONG) 
                    {
                        change++;
                        
                    }
                    else if (oldstatus == EMPTY)
                    {
                        if (!receivedAnswer.equals(""))
                        {
                            change++;
                            
                        }
                        
                    }
                    if (isfile)
                    {
                        cell.addMouseListener(new Clickopen());
                        cell.setForeground(Color.BLUE);
                    }
                    else
                        cell.setForeground(ParseLayout.hex2Rgb("#000000"));
                    if (NR == 1 && !receivedAnswer.equals("")) 
                    {
                        setv(3, questionNumber, "1");
                    }
                }
                else  // has answer
                {
                    if (oldstatus == WRONG || oldstatus == EMPTY) 
                    {
                        if (isfile)
                        {
                             cell.addMouseListener(new Clickopen());
                             cell.setForeground(Color.BLUE);
                             change++; 
                                 
                             if (NR == 1) 
                             {
                                 setv(3, questionNumber, "1");
                             }
                        }
                        else  if (!receivedAnswer.equals(""))
                        {
                            if (MainActivity.same(standardanswer, receivedAnswer))
                            {
                                change++; 
                                cell.setText(GOOD);
                                cell.setForeground(ParseLayout.hex2Rgb("#00ee00"));
                                if (NR == 1) 
                                {
                                   setv(3, questionNumber, "1");
                                }
                            }
                            else
                            {
                                 cell.setForeground(ParseLayout.hex2Rgb("#ee0000"));
                                 if (NR == 1) 
                                {
                                   setv(3, questionNumber, "0");
                                }
                            }
                            
                        }
                        
                    }
                    else  
                    {
                        if (!receivedAnswer.equals(""))
                        {
                           
                            if (isfile)
                            {
                               cell.addMouseListener(new Clickopen());
                               cell.setForeground(Color.BLUE);
                               if (NR == 1) 
                                {
                                    setv(3, questionNumber, "1");
                                }
                                
                            }
                            else if (!MainActivity.same(standardanswer, receivedAnswer))
                            {
                               change--;
                               cell.setForeground(ParseLayout.hex2Rgb("#ee0000"));
                               if (NR == 1) 
                                {
                                   setv(3, questionNumber, "0");
                                }
                               
                            }
                            else
                            {
                                cell.setForeground(ParseLayout.hex2Rgb("#00ee00"));
                                if (NR == 1) 
                                {
                                   setv(3, questionNumber, "1");
                                }
                            }
                        }
                        else
                        {
                            change--;
                        }
                    }
                }
                  
                modifynum( l,   questionNumber,  change);
                ((JLabel)ele.get(NR+2).get(questionNumber)).setForeground(ParseLayout.hex2Rgb("#0000ff"));
                
            } catch (Exception e) {
            }
        }

    }
    
    void modifynum(int l, int questionNumber, int change)
    {
        if (NR > 1) 
        {
            modifynum0(NR + 2, questionNumber,  change);
        }
        modifynum0(l, NQ + 1,  change);
        modifynum0(NR + 2, NQ + 1,  change);
        setv(NR + 2, NQ + 1, retrv(NR + 2, NQ + 1) + "/" + (NR*shouldnum()));
    }
    int shouldnum()
    {
        int n = 0;
        for (int j=0; j< NQ; j++)
        {
            for (int i=0; i < NR; i++)
            {
                if (!retrv(i+2,j+1).equals(""))
                {
                    n++; break;
                }
            }
        }
        return n;
    }
    void modifynum0(int i, int j, int change)
    {
        int n = 0; 
        String q = retrv(i,j).trim();
        try{
            n = Integer.parseInt(q.replaceFirst("[^0-9].*$",""));
        }catch(Exception e){
           // ln("n=" + n + "   q=" + q);
        }
        setv(i, j,  ""+(n+change));
    }
    void download()
    {
        Intent intent = new Intent(this, DownloadActivity.class);
        intent.putStringArrayListExtra("download", downloads);
        startActivityForResult(intent, DOWNLOAD_CODE);
    }

    void opendownload()
    {
       download();
    }
    String pad1(int x)
    {
        x %= 100;
        if (x > 10) return ""+x;
        return "0" + x;
    }
    File tempfile(Msg m)
    {
        if (uploadable == false) return null;
        String prefix = m.sid.replaceAll("[^a-z|A-Z|0-9]","");
        if (prefix.length()>5) 
        {
            if (prefix.replaceAll("[^0-9]","").length()<2)
               prefix = prefix.substring(prefix.length()-5); 
            else
               prefix = prefix.substring(0,5);
        }
        //else if (prefix.length() == 1) prefix += "00";
        //else if (prefix.length() == 2) prefix += "0";
        //else if (prefix.length() == 0) prefix += "rcv";
        int l = m.answer.lastIndexOf(".");
        String f1 = m.answer.substring(l);
        Date d = new Date();
        LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        String fns = prefix + pad1(localDate.getYear()) + pad1(localDate.getMonthValue())
                + pad1(localDate.getDayOfMonth()) + pad1(d.getHours())
                + pad1(d.getMinutes()) + pad1(d.getMinutes()) + f1;
        return new File(directory, fns);
    }
 
 
    class ShowErr implements Runnable
    {

        Context c;
        String err = null;

        public ShowErr( String err) {

            this.c = c;
            this.err = err;

        }

        public void run()
        {
            mytoast(  err);
        }
    }

    synchronized int  getorder(int qn)
    {
        if (qn < 0) return -1;
        ++orders[qn];
        return orders[qn];//msg.questionNumber - 1]
    }
    
    void  listFiles(File fd, ArrayList<String> z, String subfolder)
    {
        for (File f : fd.listFiles())
        {
            if (f.isFile())
            {
                z.add(f.length() + " " +  subfolder + f.getName());
            }
            else
            {
                listFiles(f, z,  subfolder + f.getName() + File.separator);
            }
        }
    }
    String lookfor(String file)
    {
        int i = 0;
        for (; i < downloads.size(); i++)
        {
            int j = downloads.get(i).lastIndexOf(File.separator);  //    \fgfg
            String fd = downloads.get(i).substring(j+1);
            int k = file.indexOf(File.separator);        //     dsfdf\
            String y;
            if (k == -1) 
            {
                y = file;
                k = file.length();
                
            }
            else
                y = file.substring(0,k);
            if (fd.equals(y))
                return downloads.get(i).substring(0,j+1) + file;
        }
        return null;
    }
    class ReceiveData implements Runnable {

        Msg msg = null;
        Context c;
        Socket socket = null;
        int status = 0;
        String ip;
        long replylength = 0;
        public ReceiveData(Socket socket) {

            this.c = c;
            this.socket = socket;

        }

        String getfd(int questionNumber, String path)
        {
            int kk =  questionNumber - 1;
            String ss = downloads.get(kk).trim();
            String [] ps = DownloadActivity.split(ss);
            int ll = ps[1].lastIndexOf(File.separator) + 1;
            //kk = path.lastIndexOf(File.separator);
            return path.substring(ll);
             
        }
        
        public void run()
        {
            InputStream is = null;
            BufferedReader r = null;
            OutputStream DOS = null;
            int permit = 4;
            try {
                ip = socket.getRemoteSocketAddress().toString().replaceFirst(":.*", "").replaceFirst("^[^0-9]", "");
                is = socket.getInputStream();;
                status = 1;
                r = new BufferedReader(new InputStreamReader(is));
                String sentence = r.readLine().replaceFirst("\n$", "");
                status = 2;
                msg = new Msg(sentence);
                permit =  login(msg);
                if (permit < 0) 
                {
                    msg.valid = false;
                    msg.answer += " login:" + permit;
                } 
                else if (permit == 1) 
                {
                    if (msg.answer.length() > 20) 
                    {
                        msg.answer = msg.answer.substring(0, 20);
                    }
                }
                long L = 0;
                 
                if (msg.valid && msg.isdownload && msg.questionNumber > 0) 
                {
                    int j = msg.questionNumber - 1;
                    String filepath = null;
                    if (j < downloads.size()) 
                    {
                        filepath = downloads.get(j);
                    }
                    if (filepath == null)
                    {
                        msg.valid = false;
                    }
                    else
                    {
                        //ln("1938 filepath=" + filepath);
                        String [] xs = DownloadActivity.split(filepath);
                        L = 0; try{ L  = Long.parseLong(xs[0]);}catch(Exception e){}
                        String foldrorfilepath = xs[1];
                        
                        String fss[] = DownloadActivity.split(msg.answer);
                        long L1 = 0; try{ L1= Long.parseLong(fss[0]);}catch(Exception e){}
                        String filepath1 = fss[1];
                        
                        if (foldrorfilepath.indexOf(msg.answer) > 0)
                        {
                            msg.answer = foldrorfilepath;
                        }
                        else if (filepath1.contains(foldrorfilepath))
                        {
                            msg.answer =filepath1;
                            L = L1;
                        }
                        else
                        {
                            msg.valid = false;
                        }
                        
                        
                    }
                }

                String reply = "";
                byte [] sign = new byte[1];
                DOS = socket.getOutputStream();
                if (  !msg.valid || msg.questionNumber < 0) 
                {
                    try{
                        DOS =  (socket.getOutputStream());
                        sign[0] = NOPERMISSION;
                        DOS.write(sign, 0, 1);
                        DOS.write("nopermit".getBytes("utf-8"));
                        DOS.flush();
                    }catch(Exception e2){}
                    finally {
                        if (DOS != null) try {
                            DOS.close();
                        } catch (Exception e) {
                        }

                    }
                    reply = "Error";
                } 
                else 
                {
                     
                        
                        if (msg.isdownload) {

                            if (permit < 3) 
                            {
                                msg.answer += " " + permit + "<3";
                                sign[0] = NOPERMISSION;
                                DOS.write(sign);
                                DOS.flush();
                            } else if (msg.questionNumber == 0) 
                            {
                                sign[0] = ISFILELIST;
                                DOS.write(sign);
                                status = 3;
                                for (int i = 0; i < downloads.size(); i++) 
                                {
                                    String y = downloads.get(i);
                                    String[] fs = DownloadActivity.split(y);

                                    int l = fs[1].lastIndexOf(File.separator);
                                     
                                    reply = fs[0] + " " + fs[1].substring(l + 1) + "\n";
                                    replylength += reply.length();
                                    DOS.write(reply.getBytes("utf-8"));
                                }
                                DOS.flush();
                                status = 4;
                            } else //if( msg.questionNumber > 0) 
                            {

                                FileInputStream fis = null;
                                File file = new File(msg.answer);
                                if (!file.exists()) {
                                    msg.answer += "  " + permit + "<3";
                                    sign[0] = NOSUCHFILE;
                                    DOS.write(sign);
                                } else if (file.isFile()) {
                                    long flen = file.length();
                                    if (flen == msg.length) {
                                        sign[0] = ISSAMEFILE;
                                        DOS.write(sign);
                                    } else if (flen > 0) {
                                        sign[0] = ISFILECONTENT;
                                        DOS.write(sign);
                                        try {
                                            fis = new FileInputStream(new File(msg.answer));
                                            status = 3;
                                            byte[] buf = new byte[2048];
                                            int k;
                                            while ((k = fis.read(buf)) > 0) {
                                                DOS.write(buf, 0, k);
                                                replylength += k;
                                            }
                                        } catch (Exception e12) {
                                        }
                                    } else {
                                        sign[0] = ISEMPTYFILE;
                                        DOS.write(sign);

                                    }
                                } else {
                                    File[] fs = file.listFiles();
                                    status = 3;
                                    String fd = getfd(msg.questionNumber, msg.answer);
                                    sign[0] = ISDIRECTORY;
                                    DOS.write(sign);
                                    reply = msg.questionNumber + "       " + fd + "\n";
                                    DOS.write(reply.getBytes("utf-8"));
                                    for (File y : fs) {
                                        if (y.isFile()) {
                                            reply = y.length() + " " + y.getAbsolutePath() + "\n";
                                        } else {
                                            reply = "0 " + y.getAbsolutePath() + "\n";
                                        }
                                        replylength += reply.length();
                                        // ln(reply);
                                        DOS.write(reply.getBytes("utf-8"));
                                    }
                                }
                                DOS.flush();
                                status = 4;
                            }
                        } 
                        else // for main
                        {

                            if (msg.isfilename) 
                            {
                                byte code = 0;
                                L = msg.length;
                                FileOutputStream out = null;
                                File f = null;
                                if (permit < 4) {
                                    msg.answer += "(" + permit + " < 4)";
                                    msg.isfilename = false;
                                    code = 1;
                                } else {
                                    f = tempfile(msg);
                                    if (f == null) {
                                        code = 2;
                                    }
                                    if (code == 0) {
                                        msg.answer = f.getName();
                                    }
                                }

                                try {
                                    String confirm = code + "\n";
                                    DOS.write(confirm.getBytes("utf-8"));
                                    DOS.flush();
                                    status = 3;
                                    if (code == 0) {
                                        out = new FileOutputStream(f);
                                        byte[] buf = new byte[2048];
                                        int k;
                                        while (L > 0 && (k = is.read(buf)) > 0) {
                                            L -= k;
                                            out.write(buf, 0, k);
                                        }
                                        status = 4;
                                    }
                                     

                                } 
                                catch (Exception e) { } 
                                finally 
                                {
                                    if (out != null) 
                                    {
                                        try {
                                            out.close();
                                        } catch (Exception e4) {
                                        }
                                    }
                                }
                            }
                            status = 6;
                            if(permit > 1)
                            {
                                if (msg.questionNumber >= orders.length) 
                                {
                                    int or0[] = new int[orders.length + 10];
                                    for (int j : or0) 
                                    {
                                        if (j < orders.length) 
                                        {
                                            or0[j] = orders[j];
                                        }
                                        or0[j] = 0;
                                    }
                                    orders = or0;
                                }

                                status = 7;
                                reply = replyDatagram() + "," + getorder(msg.questionNumber - 1) + "," + permit;
                                reply += "\n";
                                replylength += reply.length();
                                DOS.write(reply.getBytes("utf-8"));
                                DOS.flush();
                                status = 10;
                            }
                            else
                            {
                                DOS.write("nopermit".getBytes("utf-8"));
                                DOS.flush();
                            }
                        }
                    
                }
                
            }
            catch (IOException e4){ }
            catch (Exception e6){ }
            finally 
            {
                if (DOS!=null)  try{DOS.close();}catch (Exception e){}
                if (r!=null) try{r.close(); is.close();}catch (Exception e){}
                try{ socket.close();}catch (Exception e3){}
            }
            if (msg!=null &&  msg.valid && !msg.isdownload && msg.questionNumber>0)
            (new Runupdate(msg)).run();

        }
    }

    

   static  JTextField addnewrow(JPanel lt, String label, String value, int i )
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = i;
        c.insets = new Insets(1,1,1,1);
        c.weightx = 0.5;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JLabel btn = null;
        JTextField edittextj = null;
        for (int j = 0; j < 2; j++) {
            c.gridx = j;
            if ( j==0)
            {
                JLabel labelj = new JLabel();
                labelj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                labelj.setForeground(ParseLayout.hex2Rgb("#000000"));
                c.ipadx = c.ipady = 5;
                labelj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                labelj.setOpaque(true);

                labelj.setText(label);
                 
                lt.add(labelj,c);


            } else if (j==1)
            {

                edittextj = new JTextField();
                 
                edittextj.setForeground(Color.BLACK);
                 
                edittextj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                edittextj.setBackground(ParseLayout.hex2Rgb("#ffffff"));
                edittextj.setOpaque(true);
                edittextj.setText(value);
                lt.add(edittextj,c);
            }
        }
        return edittextj;
    }
     

}

   
 
