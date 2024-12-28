package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
 
import javax.imageio.ImageIO;
import com.github.sarxos.webcam.Webcam;
 


public class MainActivity extends AppCompatActivity
{
   double size[][] = new double[][]
   { 
       {0.1, 0.48, 0.1, 0.48},
       {25,25,25,25,25,25}
   };
   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
      //JPanel sp0 = new JPanel(new GridBagLayout());
      //components.put(R.id.prog,sp0);
      //contentpane.add(sp0);
        
      //sp0.setAlignmentX(Component.LEFT_ALIGNMENT);
      //sp0.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      JPanel ll1 = new JPanel(new GridBagLayout());
      c = new GridBagConstraints();
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      components.put(R.id.prog,ll1); 
      contentpane.add(ll1,c);
      //ll1.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      ll1.setAlignmentX(Component.LEFT_ALIGNMENT);
      
      JPanel tl20 = new JPanel(new GridBagLayout());
      tl20.setBackground(Color.LIGHT_GRAY);
       c = new GridBagConstraints();
        
       c.anchor = GridBagConstraints.NORTHWEST;
       c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(1,1,1,1);
      
      
      ll1.add(tl20,c);
      
      
      JPanel tl2 = new JPanel(new GridBagLayout());
      tl2.setBackground(Color.LIGHT_GRAY);
       c = new GridBagConstraints();
       //c.insets = new Insets(1, 1, 1, 1);
       c.anchor = GridBagConstraints.NORTHWEST;
       c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.5;
      c.insets = new Insets(1,1,1,1);
      
      
      tl20.add(tl2,c);
      
      
      JLabel lbl3 = new JLabel(  "<html><nobr>" +  R.string.uid.replaceAll(" ","&nbsp;") + "</nobr></html>" );
      components.put(R.id.lbluid,lbl3);
      lbl3.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl3.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl3.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.0;
      c.gridx = 0;
      c.ipadx = 2;
      c.ipady = 2;
      c.gridy = 0;
      c.weighty = 0.5;
     // c.insets = new Insets(1,1,1,1);
      lbl3.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      tl2.add(lbl3,c);
      JTextField txt4 = new JTextField("D        ");
      components.put(R.id.txtuid,txt4);
      txt4.setBorder(javax.swing.BorderFactory.createEmptyBorder());
      txt4.setForeground(ParseLayout.hex2Rgb("#000000"));
      txt4.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      txt4.setOpaque(true);
      txt4.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
       c = new GridBagConstraints();
      txt4.setPreferredSize(new Dimension(70,20));
      c.weightx = 0.5;
      c.ipadx = 2;
      c.ipady = 2;
      c.gridx = 1;
      c.gridy = 0;
      c.fill = GridBagConstraints.HORIZONTAL;
      txt4.setPreferredSize(new Dimension(70,20));
      c.weighty = 0.9;
      //c.insets = new Insets(1,1,1,0);
      tl2.add(txt4,c);
      JLabel lbl5 = new JLabel("<html><nobr>&nbsp;&nbsp;" +  R.string.rip.replaceAll(" ","&nbsp;") + "</nobr></html>");
      components.put(R.id.lblrip,lbl5);
      lbl5.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl5.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl5.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.EAST;
      c.weightx = 0.0;
      c.gridx = 2;
      c.gridy = 0;
      c.ipadx = 2;
      c.ipady = 2;
      c.weighty = 0.5;
      //c.insets = new Insets(1,1,1,0);
      lbl5.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      tl2.add(lbl5,c);
      JTextField txt6 = new JTextField(" 0.8 ");
      txt6.setBorder(javax.swing.BorderFactory.createEmptyBorder());
      components.put(R.id.txtrip,txt6);
      txt6.setForeground(ParseLayout.hex2Rgb("#000000"));
      txt6.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      txt6.setOpaque(true);
      txt6.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
       c = new GridBagConstraints();
      txt6.setPreferredSize(new Dimension(70,20));
      c.weightx = 0.3;
      c.anchor = GridBagConstraints.WEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridx = 3;
      c.gridy = 0;
      c.ipadx = 2;
      c.ipady = 2;
      txt6.setPreferredSize(new Dimension(70,20));
      c.weighty = 0.9;
      //c.insets = new Insets(1,1,1,0);
      tl2.add(txt6,c);
      JLabel lbl7 = new JLabel( "<html><nobr>&nbsp;&nbsp;" +R.string.Session.replaceAll(" ","&nbsp;") + "</nobr></html>");
      components.put(R.id.lblses0,lbl7);
      lbl7.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl7.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl7.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.EAST;
      c.weightx = 0.0;
      c.gridx = 4;
      c.gridy = 0;
      c.weighty = 0.5;
      c.ipadx = 2;
      c.ipady = 2;
      //c.insets = new Insets(1,1,1,0);
      lbl7.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      tl2.add(lbl7,c);
      JLabel lbl8 = new JLabel("_____");
      components.put(R.id.lblses,lbl8);
      lbl8.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl8.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl8.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.EAST;
      c.weightx = 0.0;
      c.gridx = 5;
      c.gridy = 0;
      c.ipadx = 2;
      c.ipady = 2;
      c.weighty = 0.5;
      //c.insets = new Insets(1,1,1,0);
      lbl8.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
      tl2.add(lbl8,c);
      JLabel lbl9 = new JLabel( "<html><nobr>&nbsp;&nbsp;" +R.string.timestamp.replaceAll(" ","&nbsp;") + "</nobr></html>");
      components.put(R.id.lbltimes0,lbl9);
      lbl9.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl9.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl9.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.0;
      c.gridx = 6;
      c.gridy = 0;
      c.weighty = 0.5;
      c.ipadx = 2;
      c.ipady = 2;
      //c.insets = new Insets(1,1,1,0);
      lbl9.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      tl2.add(lbl9,c);
      JLabel lbl10 = new JLabel( "______");
      components.put(R.id.lbltimes,lbl10);
      lbl10.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl10.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl10.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.2;
      c.gridx = 7;
      c.gridy = 0;
      c.weighty = 0.5;
      c.ipadx = 2;
      c.ipady = 2;
      //c.insets = new Insets(1,1,1,1);
      lbl10.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
      tl2.add(lbl10,c);
      JPanel ll11 = new JPanel(new GridBagLayout());
      //ll11.setPreferredSize(new Dimension(windwidth-10, 130));
      components.put(R.id.main_table,ll11);
      c = new GridBagConstraints();
      c.weightx = 0.9;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1;
      c.ipadx = 0;
      c.ipady = 0;
      c.insets = new Insets(3,2,3,2);
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.BOTH;
      ll1.add(ll11,c);
      ll11.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      JLabel lbl12 = new JLabel( "<html>" +R.string.instrr.replaceAll("\n","<br>") + "</html>");
      components.put(R.id.instr,lbl12);
      lbl12.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl12.setBackground(ParseLayout.hex2Rgb("#ffffff"));
       c = new GridBagConstraints();
       c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.BOTH;
      c.insets = new Insets(2,2,2,2);
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 2;
      c.weighty = 0.5;
      lbl12.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl12,c);
      
   }
   
   void refresh()
   {
       
       removeeverything();
       NR = 0;
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
        try{buildToolbar("main.xml");}catch(Exception e){}
        contentpane.add(toolBar,c);
        makeElements();
        lblrip = (JLabel)findViewById(R.id.lblrip);
        lbluid = (JLabel)findViewById(R.id.lbluid);
        txtuid = (JTextField) findViewById(R.id.txtuid);
        txtrip = (JTextField) findViewById(R.id.txtrip);
        lblses = (JLabel) findViewById(R.id.lblses);
        lbltimes = (JLabel) findViewById(R.id.lbltimes);
        tl = (JPanel) findViewById(R.id.main_table);
        instr = (JLabel) findViewById(R.id.instr);
        instr.setText(instr.getText().replaceAll("([0-9])", "\n$1"));
        maxid =  R.id.txtuid + 10;
        onStart1();
        savep("temp","" );
        String oldunid = txtuid.getText();
         
        oldrip = get("rip", this);
        if (oldrip != null)
            password  = get("password" + oldrip, this);
        else
        {
            oldrip = "";
            password = null;
        }
        txtrip.setText(oldrip);
        txtuid.setText(oldunid);
        lbltimes.setText(Responsetbl.dstr(new Date(r.timemoment)));
        lblses.setText(r.session);
        setTitle(getString(R.string.response));
        lblrip.addMouseListener(new GetInfo());
        pack();
        setPreferredSize(new Dimension(windwidth, windheight));
   }
   String oldrip = "";
   public MainActivity()
   {
      super();
      try{buildToolbar("main.xml");}catch(Exception e){}
      makeElements();
      pack();
      setPreferredSize(new Dimension(windwidth, windheight));
     
   }
   
   private final int CAMERA_REQUEST_CODE = 100;
    private final int READ_REQUEST_CODE = 101;
    private final int NET_REQUEST_CODE = 102;
    private final int SELECT_PHOTO = 103;
    private final int TAKE_PHOTO_CODE = 104;
    private final int EDIT_QUESTION_CODE = 105;
    private final int WRITE_REQUEST_CODE = 106;
    private  final int OPEN_DOWNLOAD_CODE = 107;
    private final int EDIT_SETTING_CODE = 108;
    private JTextField txtrip, txtuid;
    private JPanel tl;
    protected static int NQ = 5;
    private int NR = 0;
   // protected static int PORT = 50937;
    private String selecteduri = null;
    private String selectedImagePath;
    private int maxid = 0;
    private final String GOOD = "" + ((char) 10003);
    private ArrayList<String> questions = new ArrayList<String>(),downloaded = new ArrayList<String>();
    private Vector<String> standardAnswers = new Vector();
    private Vector<String> myAnswers = new Vector();
    private Vector<String> orders = new Vector();
    private JLabel lblrip = null;
    private JLabel lbluid= null;
    private int num = 0;
    static String missed = null;
    String uid = "";
    private JTextField leftedit = null;
    private Queue<String> uriq = new LinkedList<>();
    Response r = new Response(0, "", 0, 5, ",,,\n,,,\n,,,\n,,,\n,,,");
    Responsetbl resdb = null;
    private JLabel lblses, lbltimes;
    private JLabel instr;
    int MAXN = 0;

    private boolean[] graded = null;
    private boolean[] click2sent = null;
    private HashMap<String,String> patharr = new HashMap<>();
    private Semaphore mutex = new Semaphore(1);
    AES enc = null;
    File downloadfolder = new File(System.getProperty("user.home") + File.separator + "Downloads");
    private JProgressBar progress = null;
    String  password;

    int permit;
    Menu menu0;
    String wholeip() {
        String rip = txtrip.getText().toString().trim().replaceAll(" ", "");
        
        savep("rip", rip );
        int j = rip.indexOf(".");
        if (j < 0) 
           rip = "0." + rip;
        else if (j == 0)
           rip = "0" + rip;
        
        //if (rip.replaceAll("[0-9]", "").equals("."))  //short version, add the first two bytes
        {
            //rip = getIpAddress().replaceFirst("[0-9]+\\.[0-9]+$", "") + rip;
            Date d = new Date();
            enc = new AES("" + d.getYear() + d.getMonth() + d.getDate() + rip);
        } 
        //else return "";
        return rip;
    }


   
    protected void onCreate() 
    {
        num = getIntent().getIntExtra("num", 0);
        
        
        savep("role", "Response" );

         
        lblrip = (JLabel)findViewById(R.id.lblrip);
        lbluid = (JLabel)findViewById(R.id.lbluid);
        txtuid = (JTextField) findViewById(R.id.txtuid);
        txtrip = (JTextField) findViewById(R.id.txtrip);
        lblses = (JLabel) findViewById(R.id.lblses);
        lbltimes = (JLabel) findViewById(R.id.lbltimes);
        tl = (JPanel) findViewById(R.id.main_table);
        instr = (JLabel) findViewById(R.id.instr);
        instr.setText(instr.getText().replaceAll("([0-9])", "\n$1"));
        maxid =  R.id.txtuid + 10;
        addhead();
        if (num != 0) 
        {
            resdb = new Responsetbl(null);
            r = resdb.getrecord("Response", num);
            downloaded = str2list(resdb.getOthers("Response", r.session, -1));
            missed = null;
        }
        else
        {
            missed = get("temp", this);
            if (missed!=null && missed.equals("")) missed = null;
        }

        password  = get("password",this);
        if (password == null || password.equals(""))
        {
            password =  "" +  ((int) (1000000000 * Math.random()) + 1);
            savep("password", password );
        }

        onStart1();
        savep("temp","" );
        oldunid = get("unid", this);
        oldrip = get("rip", this);
        txtrip.setText(oldrip);
        txtuid.setText(oldunid);
        setTitle(getString(R.string.response));
        lblrip.addMouseListener(new GetInfo());
        usewifi();
    }
    String oldunid = "";
    static public long encryt(String pass)
    {
        return (pass + (new Date()).getTime()%100000).hashCode();
    }
    String  newapassword()
    {
        return  "" +  ((int) (1000000000 * Math.random()) + 1);

    }
    String [] userinfoready()
    {
        String unid = txtuid.getText().toString().trim();
        
        if (!unid.equals(oldunid))
        {
            savep("unid", unid);
            oldunid = unid;
        }
        
        String rip0 = txtrip.getText().trim().replaceAll(" ", "");
        
        String rip = wholeip();
        if(unid.equals("") || !rip.matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+"))
        {
            mytoast(getBaseContext(), getString(R.string.wait));
            return null;
        } 
        String passwordnow;
        if (!rip0.equals(oldrip))
        {
            passwordnow =  get("password" + rip0, this);
            if (passwordnow == null)
            {
                passwordnow = newapassword();
                password = passwordnow;
                savep("password" + rip0, password );
            }
            else
            {
                password = passwordnow;
                passwordnow = "" + encryt(passwordnow);
            }

        }
        else
            passwordnow = "" + encryt(password);
        return new String[]{unid, rip, passwordnow};
    }
    void SendMsg(int r,  String myAnswer,int d)
    {
        String [] x = userinfoready();
        if (x == null) return;
        String unid = x[0];
        String rip = x[1];
        String pass =  x[2];
        
        new TCPSendMsg(new String[]{rip, unid, ""+(r+1), myAnswer, "" + (d + 1),pass}).run();

    } 


    protected void onStop() {
        
        if (missed !=null && !missed.equals(""))
        savep("temp", missed );
    }

    protected void onStart1() {
        if (missed != null && missed.indexOf("\n") > 0) {
            resdb = new Responsetbl(null);
            r = new Response(missed);
        } else if (resdb == null) {
            resdb = new Responsetbl(null);
        }

        lblses.setText(r.session.equals("")?"______":r.session);
        if (r.timemoment != 0)
            lbltimes.setText(Responsetbl.dstr(new Date(r.timemoment)));
        if (missed == null || r.content == null || missed.indexOf("\n") < 0) 
        {
            r.numberq = 5;
            NR = 0;
            for (int i = 0; i < r.numberq; i++) {
              //  addrow("", "", "");
            }
            if (r.content == null || r.content.equals(""))   // return;
            r.content = "1,\"\",\"\",\"\",0,1,0\n" +
"2,\"\",\"\",\"\",0,0,0\n" +
"3,\"\",\"\",\"\",0,0,0\n" +
"4,\"\",\"\",\"\",0,0,0\n" +
"5,\"\",\"\",\"\",0,0,0"; 
        }
        CSVParse p = new CSVParse(r.content, '"', new String[]{",", "\n"});
        String[][] ptr = p.nextMatrix();
        NR = 0;
        NQ = r.numberq; 
        int i=0;
        MAXN = r.numberq;
        graded = new boolean[MAXN+50];
        click2sent = new boolean[MAXN+50];
        standardAnswers.clear();
        myAnswers.clear();
        orders.clear();
        questions.clear();
       
        for (; i < r.numberq; i++) {

             if (ptr[i].length > 1) 
                questions.add(ptr[i][1]);
            else
                questions.add(""); 
            if (ptr[i].length > 2) 
                standardAnswers.addElement(ptr[i][2]);
            else
                standardAnswers.addElement("");
            if (ptr[i].length > 3) 
                myAnswers.addElement(ptr[i][3]);
            else
                myAnswers.addElement("");
            if (ptr[i].length > 4)
                orders.addElement(ptr[i][4]);
            else
                orders.addElement("");
            click2sent[i] = ptr[i].length > 5 && ptr[i][5].equals("1");
            graded[i] = ptr[i].length > 6 && ptr[i][6].equals("1");
           
            int l = 0;
            if (ptr[i].length > 3)
            {
                String q = "";
                if (ptr[i].length > 4)  q = ptr[i][4];
                if (ptr[i][3] != null && ptr[i][3].length() > 1 && ptr[i][3].charAt(0) == (char) (254))
                {
                    ptr[i][3] = ptr[i][3].substring(1);
                    String fn = putpath(ptr[i][3],i);
                    addrow(ptr[i][2], fn, q);
                    if (fn.indexOf(".jpg") > 0 || fn.indexOf(".png") > 0 || fn.indexOf(".gif") > 0)
                    {
                        JLabel feedback = (JLabel) (compbyid(maxid + i * 5 + 3));
                        feedback.addMouseListener(new Imgopen(ptr[i][3]));
                        feedback.setForeground(Color.BLUE);
                    }
                } 
                else
                    addrow(ptr[i][2], ptr[i][3], q);
            }
        }

        downloaded = str2list(resdb.getOthers("Response", r.session, -1)); 

    }

    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_QUESTION_CODE)
        {
            if (resultCode == RESULT_OK) {
                ArrayList<String> x = data.getStringArrayListExtra("question");
                if (x!=null)
                questions = x;
            }
        }
        else if (requestCode == TAKE_PHOTO_CODE) {

            if (resultCode == RESULT_OK) {
                (new Updateedit()).run();
                resizesend(selectedImagePath);
            }
        } else if (requestCode == SELECT_PHOTO) {

            if (resultCode == RESULT_OK && data != null) {
               
            }
        } else if (requestCode == OPEN_DOWNLOAD_CODE)
        {
            if (1+1==2) //resultCode == RESULT_OK)
            {
                ArrayList<String> x = data.getStringArrayListExtra("downloaded");
                if (x!=downloaded)
                {
                   downloaded.clear();
                   for (int i=0; i < x.size(); i++)
                       downloaded.add(x.get(i));
                }
                resdb.saveOthers("Response", r.session, -1, list2String(downloaded));
            }
            
        }
        else if (requestCode == EDIT_SETTING_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                this.setVisible(true);
                if (data.getIntExtra("refresh",0) == 1)
                {  
                   r.content = content();
                   refresh();
                }
            }
        }
        

    }
    void opendownload()
    {
         download();
        
    }

    void download()
    {
        String [] x = userinfoready();
        if (x == null) return;
         Intent intent = new Intent(this, DownloadActivity.class); 
         intent.putStringArrayListExtra("downloaded", downloaded);
         
        intent.putStringExtra("myid", x[0]);
        intent.putStringExtra("rip",  x[1]);
        intent.putStringExtra("password", x[2]);
        startActivityForResult(intent, OPEN_DOWNLOAD_CODE);
    }

    public static boolean isExternalStorageDocument(String uri) {
        return true;//"com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    private void pickfile() {
        JFileChooser choice = new JFileChooser();
        choice.setCurrentDirectory(new File(AppCompatActivity.uploadfolder));
        int option = choice.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try{
                String p = choice.getSelectedFile().getPath();
                int l = (compid.get(leftedit).intValue() - 1 - maxid) / 5;
                p = putpath(p, l);
                leftedit.setText(p);
            }catch(Exception e){}
        }  
    }



    private void pickfile1() {
        save(null);

        if (leftedit != null) {
            leftedit.setText("");
            int r = (compid.get(leftedit).intValue()- 1 - maxid) / 5;
 
             
        }
    }

    class Updateedit1 implements Runnable {
        String selecteduri;
        Context c;

        public Updateedit1(String selecteduri, Context c) {
            this.selecteduri = selecteduri;
            this.c = c;
        }

        public void run() {
            //selectedImagePath = FileUtils.getPath(selecteduri, c);
            if (leftedit != null) {
                int l = (compid.get(leftedit).intValue() - 1 - maxid) / 5;
                String p = putpath(selecteduri, l);
                leftedit.setText(p);

            }
        }
    }

    class Updateedit implements Runnable {

        public Updateedit() {
        }

        public void run()
        {
            if (leftedit != null)
            {
                int r = (compid.get(leftedit).intValue() - 1 - maxid) / 5;
                String p = putpath(selectedImagePath,r);
                leftedit.setText(p);
            }
        }
    }

    String putpath(String p, int r)
    {
        int l = p.lastIndexOf(File.separator);
        if (p.length() - l > 15)
            l = p.length() - 15;
        patharr.put(r + p.substring(l + 1),p);
        return p.substring(l + 1);
    }

    String hold = null;
    HashMap<Component, Integer> compid = new HashMap();
    Component compbyid(int d)
    {
        for (Component x:compid.keySet())
            if (compid.get(x).intValue() == d)
                return x;
        return null;
    }
    private int rr = 0;
    private void sendall()
    {
        JLabel tv = (JLabel) (compbyid(maxid - 2));
        if (rr==-1 || tv.getText().equals(">>"))
        {
            tv.setText(">>");
            rr = -1;
            return;
        }
        int d = maxid  + 5 * rr + 2;
        JLabel v = (JLabel) (compbyid(d));
        if (v == null )
        {
            rr = -1;
            tv.setText(">>");
            return;
        }
        if ( v.getBackground().equals(ParseLayout.hex2Rgb(AppCompatActivity.background)))
        {
            rr++;
            sendall();
            return;
        }
        JTextField edit = (JTextField) compbyid(d - 1);
        JLabel feedback = (JLabel) compbyid(d + 1);
        String myAnswer = edit.getText().trim();
        String filepath = patharr.get(rr + myAnswer);
        if (graded[rr] && filepath == null) 
        {
            rr++;
            sendall();
            return;
        }
        if (myAnswer.equals("")) 
        {
            rr++;
            sendall();
            return;
        } 
        else 
        {

            if (permit ==1 && myAnswer.length()>20)
            {
                myAnswer = myAnswer.substring(0,20);
                edit.setText(myAnswer);
            }

            if (filepath==null) 
            {
                myAnswers.set(rr, myAnswer);

            }
            else
            {
                filepath = ((char) (254)) + filepath;
                 myAnswers.set(rr, filepath);
            }

            v.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
            if (filepath != null)
            {
                progress = new JProgressBar();

            }
            else
                progress = null;
            SendMsg(rr, myAnswer, d);
        }
    }
    private void addhead()
    {
        for (int j = 0; j < 4; j++) 
        {
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = j;
            c.gridy = 0;
            c.insets = new Insets(1, 1, 1, 1);
            
            c.ipadx = 5;
            c.ipady = 5;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weighty = 1/(10.0);
            c.fill = GridBagConstraints.BOTH;
             
            JLabel textviewj = new JLabel();
            textviewj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));

            compid.put(textviewj,   maxid + j);
            //textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
            textviewj.setOpaque(true);
            if (j == 0) 
            {
                c.weightx = 0;
                c.anchor = GridBagConstraints.EAST;
                textviewj.setPreferredSize(new Dimension(30, 25));
                textviewj.setText("#");
                textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                textviewj.setForeground(ParseLayout.hex2Rgb("#0000cc"));
                //textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                textviewj.setHorizontalAlignment(SwingConstants.RIGHT);
                textviewj.setAlignmentX(RIGHT_ALIGNMENT);
                textviewj.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent e){}
                    public void mouseReleased(MouseEvent e){}
                    public void mouseEntered(MouseEvent e){}
                    public void mouseExited(MouseEvent e){}
                    public void mouseClicked(MouseEvent e) { 
                     Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                     intent.putStringArrayListExtra("question",questions);
                     MainActivity.this.startActivityForResult(intent, EDIT_QUESTION_CODE);
                    }
                });
            } 
            else if (j == 2)
            {
                c.weightx = 0;

                textviewj.setPreferredSize(new Dimension(40, 25));
                textviewj.setHorizontalAlignment(SwingConstants.CENTER);
                textviewj.setAlignmentX(CENTER_ALIGNMENT);
                String txt = ">>";
                textviewj.setText(txt); 
                textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                textviewj.setForeground(ParseLayout.hex2Rgb("#0000cc"));

                textviewj.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent e){}
                    public void mouseReleased(MouseEvent e){}
                    public void mouseEntered(MouseEvent e){}
                    public void mouseExited(MouseEvent e){}
                    public void mouseClicked(MouseEvent e)  {
                         JLabel lbl = (JLabel) e.getSource();
                         if (lbl.getText().equals(">>"))
                            {
                                if (rr == -1) rr = 0;
                                lbl.setText("x");
                                sendall();
                            }
                            else
                            {
                                lbl.setText(">>");
                            }
                    }
                });

            } 
            else // j==3 j == 1
            {

                c.weightx = 0.5;
                textviewj.setHorizontalAlignment(SwingConstants.LEFT); 
                int width =  (getWidth()-90) / 2;  if (width < 200) width =200;
                textviewj.setPreferredSize(new Dimension(width,25));
                if (j == 3)
                textviewj.setText(getString(R.string.feed));
                else if (j == 1)
                    textviewj.setText(getString(R.string.yours));
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));


            }

            tl.add(textviewj,c);
           
        }
        //tl.setVisible(true);
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
        maxid += 4;
    }

       
    private void addrow(String stardardAnswer, String myAnswer, String order)
    {
        if (NR  >= orders.size())
            orders.addElement("");
        if (NR  >= myAnswers.size())
            myAnswers.addElement("");
        if (NR  >= standardAnswers.size())
            standardAnswers.addElement("");
        if (NR  >= questions.size())
            questions.add("");

        if (NR>=MAXN)
        {

            boolean graded1[] = new boolean[MAXN+30];

            boolean click2sent1[] = new boolean[MAXN+30];

            for (int i=0; i < MAXN; i++)
            {

                graded1[i] = graded[i];

                click2sent1[i] = click2sent[i];
            }
            for (int i=MAXN; i < MAXN+30; i++)
            {

                graded1[i] = false;

                click2sent1[i] = false;
            }
            graded= graded1;

            click2sent = click2sent1;
            MAXN += 30;
        }
        tl.setBackground(Color.LIGHT_GRAY);
       /* double newsize[] = new double[NR+1];
        for (int j=0; j < NR; j++)
        {
           newsize[j] = size[1][j]; 
        }
        newsize[NR] = 25.0;
        size[1] = newsize;
        tl.setLayout(new TableLayout(size));*/
        JLabel btn = null;
        for (int j = 0; j < 4; j++) 
        {
            GridBagConstraints c = new GridBagConstraints();
            c.gridx = j;
            c.gridy = NR + 1;
            c.insets = new Insets(0, 1, 1, 1);
            c.ipadx = 5;
            c.ipady = 5;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weighty = 1/(10.0);
            c.fill = GridBagConstraints.BOTH;
            if (j != 1) 
            {
                JLabel textviewj = new JLabel();
                
                textviewj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                compid.put(textviewj, 5 * (NR) + maxid + j);
                //textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                textviewj.setOpaque(true);
                if (j == 0) 
                {
                    c.weightx = 0;
                    c.anchor = GridBagConstraints.EAST;
                    textviewj.setPreferredSize(new Dimension(30, 25));
                    textviewj.setText("" + (NR + 1));
                    textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                    textviewj.setForeground(ParseLayout.hex2Rgb("#0000cc"));
                    textviewj.setHorizontalAlignment(SwingConstants.RIGHT);
                    textviewj.setAlignmentX(RIGHT_ALIGNMENT);
                    textviewj.addMouseListener(new MouseListener() {
                        public void mousePressed(MouseEvent e){}
                        public void mouseReleased(MouseEvent e){}
                        public void mouseEntered(MouseEvent e){}
                        public void mouseExited(MouseEvent e){}
                        public void mouseClicked(MouseEvent e) { 
                            JLabel tv = (JLabel) e.getSource();
                            int i = Integer.parseInt(tv.getText().trim());
                            String str = getString(R.string.question) + " " + i;
                            if (questions.size() < i || questions.get(i - 1).equals(""))
                            str += " " + getString(R.string.oral);
                            pop(getString(R.string.QuestionText), str, i);

                        }
                    });
                } 
                else if (j == 2)
                {
                    c.weightx = 0;
                    String filepath = patharr.get(NR + myAnswer);
                    textviewj.setPreferredSize(new Dimension(40, 25));
                    textviewj.setHorizontalAlignment(SwingConstants.CENTER);
                    textviewj.setAlignmentX(CENTER_ALIGNMENT);
                    textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                    textviewj.setForeground(ParseLayout.hex2Rgb("#0000cc"));
                    String txt = "<html>&rarr;</html>";
                    if (!order.matches("[0-9]+")) order = "";
                    if (click2sent[NR] && myAnswer!=null && !myAnswer.equals("")) 
                    {

                        if (filepath != null || stardardAnswer == null    || stardardAnswer.equals("")) 
                        {
                            if (order=="") 
                                txt = "<html>&rarr;</html>";// order = "-";
                            else
                                txt = order + ">";
                        } else {
                             txt = order;
                        }
                    }
                    textviewj.setText(txt); 
                     
                    if (!order.equals("") && order.matches("[0-9]+") && !stardardAnswer.equals("") && !myAnswer.equals("")) 
                    {
                        
                    } 
                    else 
                    {
                        textviewj.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                        //textviewj.setForeground(ParseLayout.hex2Rgb("#7c1843)"));
                    }
                    
                    
                    textviewj.addMouseListener(new MouseListener() {
                        public void mousePressed(MouseEvent e){}
                        public void mouseReleased(MouseEvent e){}
                        public void mouseEntered(MouseEvent e){}
                        public void mouseExited(MouseEvent e){}
                        public void mouseClicked(MouseEvent e)  {
                            JLabel v = ((JLabel)e.getSource());
                             
                             
                            if ( v.getBackground().equals(ParseLayout.hex2Rgb(AppCompatActivity.background)))
                                return;

                            int d = compid.get(v);
                            int r = (d - maxid - 2) / 5;
                            JTextField edit = (JTextField) compbyid(d - 1);
                            JLabel feedback = (JLabel) compbyid(d + 1);
                            String myAnswer = edit.getText().trim();
                            String filepath = patharr.get(r + myAnswer);
                            if (graded[r] && filepath == null) return;

                            if (myAnswer.equals("")) {
                                mytoast(getBaseContext(), getString(R.string.enter));
                            } else {

                                if (permit ==1 && myAnswer.length()>20)
                                {
                                    myAnswer = myAnswer.substring(0,20);
                                    edit.setText(myAnswer);
                                }

                                if (filepath==null) {
                                    myAnswers.set(r, myAnswer);

                                }
                                else
                                {
                                    filepath = ((char) (254)) + filepath;
                                     myAnswers.set(r, filepath);
                                }

                                v.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                                if (filepath != null)
                                {
                                    progress = new JProgressBar();

                                }
                                else
                                    progress = null;
                                SendMsg(r, myAnswer, d);
                       
                            }
                        }
                    });
                    btn = textviewj;

                } else // j==3
                {
                     
                    c.weightx = 0.5;
                    textviewj.setHorizontalAlignment(SwingConstants.LEFT); 
                    int width =  (getWidth()-90) / 2;  if (width < 200) width =200;
                   
                    textviewj.setPreferredSize(new Dimension(width,25));
                    for (int k=0; k < NR; k++)
                    {
                        compbyid(5 * k + maxid + j).setPreferredSize(new  Dimension(width, 25));
                    }
                    String filepath = patharr.get(NR+myAnswer);
                    graded[NR] = false;
                    if (filepath==null && click2sent[NR] && myAnswer!=null && stardardAnswer!=null && !myAnswer.equals("") && !stardardAnswer.equals("")) {
                        graded[NR] = true;
                        if (same(myAnswer, stardardAnswer))
                        {
                            textviewj.setText(GOOD);
                            textviewj.setForeground(ParseLayout.hex2Rgb("#00ee00"));
                        }
                        else
                        {
                            textviewj.setText(stardardAnswer);
                            textviewj.setForeground(ParseLayout.hex2Rgb("#ee0000"));
                        }
                        if (btn.getText().toString().indexOf("html")>0)
                            btn.setText("");
                    } else if (click2sent[NR] && !myAnswer.equals("")) 
                    {
                        if (filepath!=null)
                        {
                            textviewj.setText(getString(R.string.sent));
                            textviewj.setForeground(ParseLayout.hex2Rgb("#0000ff"));
                            textviewj.addMouseListener(new Imgopen(filepath));
                        }
                        else
                        {
                            textviewj.setText(getString(R.string.sent));
                            textviewj.setForeground(ParseLayout.hex2Rgb("#000000"));
                        }
                    }
                    ;

                }

                tl.add(textviewj,c);
                
            } 
            else if (j == 1) 
            {
                int width =  (getWidth()-90) / 2;  if (width < 200) width =200;
                 
                JTextField edittextj = new JTextField();
                edittextj.setPreferredSize(new Dimension(width, 25));
                edittextj.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                for (int k=0; k < NR; k++)
                {
                    compbyid(5 * k + maxid + j).setPreferredSize(new  Dimension(width, 25));
                }
                c.weightx = 0.5;
                edittextj.setForeground(Color.BLACK);
                compid.put(edittextj, (5 * (NR) + maxid + j));
                edittextj.setBackground(ParseLayout.hex2Rgb("#ffffff"));
                if (myAnswer != null) edittextj.setText(myAnswer);
                if (questions.size()>NR && questions.get(NR)!=null)
                    edittextj.setToolTipText(questions.get(NR));
                edittextj.addFocusListener(  new FocusListener() {
 
                    public void focusGained(FocusEvent e) {
                       leftedit = (JTextField) (e.getSource());
                       int nr = (compid.get(leftedit).intValue() - maxid - 1)/5;
                       if (questions.get(nr)!=null)
                           leftedit.setToolTipText(questions.get(nr));
                    }
                    public void focusLost(FocusEvent e) { }
     
                     
                });
                tl.add(edittextj,c);
                //tl.add(edittextj,  j +  "," + NR);
            }

        }
        //tl.setVisible(true);
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
        NR++;
    }

     
    /*
    boolean allowINTERNET = ContextCompat.checkSelfPermission(this,
            Manifest.permission.INTERNET) ==PackageManager.PERMISSION_GRANTED;
    boolean allowACCESS_NETWORK_STATE = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_NETWORK_STATE) ==PackageManager.PERMISSION_GRANTED;
    boolean allowREAD_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;
    boolean allowWRITE_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE) ==PackageManager.PERMISSION_GRANTED;
            */
    public boolean onCreateOptionsMenu(Menu menu) {
       /*
        setIconInMenu(menu, R.id.newm, getString(R.string.New), R.mipmap.newm);
        setIconInMenu(menu, R.id.browse, getString(R.string.Browse), R.mipmap.browse);
        setIconInMenu(menu, R.id.delete, getString(R.string.Delete), R.mipmap.delete);
        setIconInMenu(menu, R.id.email, getString(R.string.Report), R.mipmap.report);
        setIconInMenu(menu, R.id.receive, getString(R.string.asenquirer), R.mipmap.ear);
        setIconInMenu(menu, R.id.question, getString(R.string.recordq), R.mipmap.question);
        setIconInMenu(menu, R.id.setting, getString(R.string.setting), R.mipmap.setting);
        setIconInMenu(menu, R.id.exit, getString(R.string.Exit), R.mipmap.exit);
        setIconInMenu(menu, R.id.download, getString(R.string.download), R.mipmap.download);
       */
       return true;
    }

    void usewifi() {
        
    }

    private boolean deleter() 
    {
        int x0  = JOptionPane.showConfirmDialog
        (
            this,
            "Delete?",
            "Confirm",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
                (Icon)(new ImageIcon(iconpath("delete")))
              ) ;
       int b = -1;
       if (x0 == 0 && r.num > 0) 
        {
           b = resdb.delete("Response", r.num) ;
        }
       return x0 == 0 && b == 1;
    }
    
    private void newr() {
        missed = "";
        savep("temp", "" );
         r.num = num = 0;
        r.timemoment = System.currentTimeMillis();
        r.session = "_________";
        r.questions.clear();
        myAnswers.clear();
        downloaded.clear();
        standardAnswers.clear();
        orders.clear();
        r.content = "1,\"\",\"\",\"\",0,1,0\n" +
"2,\"\",\"\",\"\",0,0,0\n" +
"3,\"\",\"\",\"\",0,0,0\n" +
"4,\"\",\"\",\"\",0,0,0\n" +
"5,\"\",\"\",\"\",0,0,0";
        refresh();
    }

    void emailme() 
    {
      String addr =  JOptionPane.showInputDialog(this,"Email address?");
    }

    String pad(int x) {
        if (x < 10) return "0" + x;
        return "" + x;
    }

    void tryemail(String addr) {

        
    }

    private void gotoenq() {
        savep("role", "Enquirer" );
        new  EnquireActivity().onCreate();
        dispose();
         
    }

    private void gotobrowse() {
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putStringExtra("which", "Response");
        this.startActivity(intent);
    }

    public void onOptionsItemSelected(int j) 
    {
        Intent intent;
        switch (j) 
        {
            case R.id.newm:
                newr();
                break;
            case R.id.setting:
                intent = new Intent(this, SettingActivity.class);
                intent.putStringExtra("which","res");
                this.startActivityForResult(intent, EDIT_SETTING_CODE);
                break;
                 
            case R.id.delete:
                deleter();
                newr();
                break;
            case R.id.receive:
                save("gotoenq");
                break;
            case R.id.email:
                emailme();
                break;
            case R.id.exit:
                //finish();
                this.dispose();
                break;
            case R.id.browse:
                save("gotobrowse");
                break;
            case R.id.camera: {
                if (leftedit != null) {
                    takephoto();
                } else {
                    mytoast(null, "Click an answer cell first");
                }
                break;
            }
            case R.id.file:

                if (leftedit != null) {
                    pickfile();
                } else {
                    mytoast(null, "Click an answer cell first");
                }
                break;
            case R.id.save:
                save(null);
                break;
            case R.id.question:
                intent = new Intent(this, QuestionActivity.class);
                intent.putStringArrayListExtra("question",questions);
                this.startActivityForResult(intent, EDIT_QUESTION_CODE);
                break;
            case R.id.download:
                opendownload();
                break;
            case R.id.lang:
                switchlang(MainActivity.class, r.num);
                
                break;
            default:
                
        }
 
    }
    
    static int idnum = 0;

    public String getLocalIpAddress() {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return null;
    }

    public static String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    String str = inetAddress.getHostAddress();
                    if (inetAddress.isSiteLocalAddress() && str.replace('.', '0').replaceAll("[0-9]", "").equals("")) {
                        ip = str;
                    }

                }

            }

        } catch (SocketException e) {

            ip += "Something Wrong! " + e.toString();
        }

        return ip;
    }

    protected void save(String callback) {
        r.content = content();
        r.numberq = NQ;
        if (r.session != null && !r.session.equals("")) {
            int n = resdb.save("Response", r);
            resdb.saveOthers("Response", r.session, -1, list2String(downloaded));
            if (n > 1) r.num = n;
            else if (r.num == 0 && n == 1) r.num = 1;
            else if (n == 0) {
                callback = null;
                mytoast(getBaseContext(), DBHelper.err.toString() );
            }
        } else r.session = "unname";
        missed = r.toString();
        savep("temp", missed );
        if (callback != null) {
            if (callback.equals("gotoenq"))
                gotoenq();
            else
                gotobrowse();
        }
    }

    private void pop(String title, String msg, final int i) {
       Object x0  = JOptionPane.showInputDialog(
                                     this,
                                     msg,
                                     title,
                                     JOptionPane.QUESTION_MESSAGE,
                                     (Icon)(new ImageIcon(iconpath("user"))),
                                     null,
                                     questions.get(i-1)) ;
       if (x0==null) return;
       String x = x0.toString();
        if (x!=null && x.length() > 0 && !x.equals(getString(R.string.NewSession))) 
        {
            questions.set(i - 1, x);
            ((JTextField)compbyid(5 * (i-1) + maxid + 1)).setToolTipText(x);
        }
    }


    private String content() {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < NQ; i++) {
            str.append((i + 1) + ",\"");
            if (i < questions.size() && questions.get(i) != null)
                str.append(questions.get(i).replaceAll("\"", ""));
            else str.append("");

            str.append("\",\"");

            if (i < standardAnswers.size() && standardAnswers.elementAt(i) != null)
                str.append(standardAnswers.elementAt(i).replaceAll("\"", ""));
            else str.append("");

            str.append("\",\"");

            if (i < myAnswers.size() && myAnswers.elementAt(i) != null) {

                str.append( myAnswers.elementAt(i).replaceAll("\"", ""));
            } else
            {
                  JTextField ll = (JTextField)compbyid(maxid + 5*i + 1);
                if (ll!=null)str.append(ll.getText());
                else
                    str.append("");
            }
            str.append("\",");
            if (i < orders.size() && orders.elementAt(i) != null)
                str.append(orders.elementAt(i));
            else
                str.append("0");

            str.append(",");
            str.append(click2sent[i]?"1":"0");
            str.append(",");
            str.append(graded[i]?"1":"0");
            if (i < NQ - 1) str.append("\n");
        }

        
        return str.toString();
    }
     
    static public int toInt(byte[] bytes) {

        int l = ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF) << 0);

        return l;
    }

    public static byte[] getIntByte(int value) {

        byte[] x = new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
        return x;

    }

    void endis(int ids[],boolean b)
    {
        // int ids[] = new int[]{R.id.newm,R.id.browse, R.id.delete, R.id.email,  R.id.response,R.id.exit, R.id.question,R.id.detail,R.id.setting,R.id.download};

        for (int j=0; j < ids.length; j++)
        {
       //     MenuItem item = menu0.findItem(ids[j]);
       //     item.setVisible(b);
        }
    }
    private class TakePhoto extends SwingWorker<String, Integer> {
        int questionN;
        int tid;

        protected String doInBackground() {
            return "1";
        }

        protected void onPreExecute(String [] params) {
            
        }

        protected void onPostExecute(String datagram)  // onPostExecute can access UI!!!!
        {
        }
    }

    private class PickFile extends SwingWorker<String, String > {
        int questionN;
        int tid;

        protected String doInBackground( ) {
            return null;
        }

        protected void onPreExecute(String [] params) {
            
        }

        protected void onPostExecute(String datagram)  // onPostExecute can access UI!!!!
        {
        }
    }


    long filelength = 0;

    private class TCPSendMsg extends SwingWorker<String, Integer> {
        int questionN;
        int tid;
        int step;
        boolean keep = true;
        char   codes = ' ';
        String errs[] = getString(R.string.neterror).split("\\|");
        String myAnswer = null;
        String ip = null;
        String myid = null;
        String password = "";
        public TCPSendMsg(String [] params) 
        {
            super();
            ip = params[0];
            myid = params[1];
            questionN = Integer.parseInt(params[2]);
            myAnswer = params[3];
            tid = Integer.parseInt(params[4]);
            password =  (params[5]);
        }
        
        protected void onProgressUpdate(Integer [] prog){
           //if (progress!=null) progress.setProgress(prog[0]);
            {
                JLabel feedback = (JLabel) compbyid(tid);
                if (feedback != null) feedback.setText(  prog[0].intValue() + "%");
            }
        }
        protected void progress(Integer[] prog){
           //if (progress!=null) progress.setProgress(prog[0]);
            {
                JLabel feedback = (JLabel) compbyid(tid);
                if (feedback != null) feedback.setText(  prog[0].intValue() + "%");
            }
        }
        protected String doInBackground() {

            
            File file = null;
            String path = null;
            if (questionN>0 && progress!=null)
            {
                path = patharr.get((questionN-1) + myAnswer);

                file = new File(path);
                if (!file.exists())
                    file = null;
            }
            Socket socket = null;
            String datagram = "";
            int state = 0;
            InputStream sis = null;
            OutputStream sos = null;

            step = 0;
            try {
                SocketAddress sockaddr = new InetSocketAddress(ip, AppCompatActivity.clientport);
                socket = new Socket();
                step = 1;
                String str = " ";
                FileInputStream fis = null;
                byte buf[] = null;
                if (file == null) {
                    socket.setSoTimeout(100000);
                    step = 2;
                    socket.connect(sockaddr);
                    step = 3;
                    if (myid.equals(""))
                        myid = socket.getLocalAddress().toString().replaceFirst(":.*", "").replaceFirst("^[^0-9]?[0-9]+\\.[0-9]+\\.", "");

                    str = "0" + myAnswer.getBytes().length + ":" + myid + ":" + password + ":" + questionN + ":" + myAnswer;
                    step = 4;
                } else {

                    buf = new byte[2048];
                    filelength = file.length();
                    socket.setSoTimeout(50000 + (int) (filelength / 100));
                    step = 2;
                    socket.connect(sockaddr);
                    step = 3;
                    if (myid.equals(""))
                        myid = socket.getLocalAddress().toString().replaceFirst(":.*", "").replaceFirst("[0-9]+\\.[0-9]+\\.", "");
                    String fn = file.getName();

                    str = file.length() + ":" + myid + ":"  + password + ":" + questionN + ":" + fn;
                    step = 4;

                }
                str += "\n";
                byte[] buff = str.getBytes("utf-8");
                sos = socket.getOutputStream();
                step = 5;

                sos.write(buff);
                sos.flush();
                state = 1;
                step = 6;
                datagram = "";
                sis = socket.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(sis));
                String aline = r.readLine();
                if (file != null && aline!=null &&  aline.length()>0 )
                {
                    codes = aline.charAt(0);
                    try
                    {
                        fis = new FileInputStream(file);
                        if (codes == '0')
                        {
                            int k;
                            int T = 0;
                            int old = 0;
                            step = 61;
                            while (keep && (k = fis.read(buf)) > 0)
                            {
                                try
                                {
                                    sos.write(buf, 0, k);

                                    if (progress != null)
                                    {
                                        T += k;
                                        int j = T * 100 / ((int) filelength);
                                        if (j > old) {

                                            setProgress(j);
                                        }
                                        old = j;
                                    }
                                }catch(Exception e){
                                   // System.out.println(old + " " + T + " " + e.toString());
                                    fis.close();
                                     aline = e.toString();
                                }
                            }
                            codes = T == file.length()?'3':'4';
                        }
                       // System.out.println("codes=" + codes + ", aline=" + aline);

                        fis.close();
                        aline =r.readLine();
                   }
                    catch (Exception e4) {
                    }
                    finally
                    {
                            if (fis != null) try {
                                fis.close();
                            } catch (Exception e1) {
                            }
                     }



                    step = 62;
                }
                step = 7;
                if (aline !=null && aline.equals("nopermit"))
                {
                    step = 7;
                    datagram = aline;
                }
                else
                {
                    while (aline != null)
                    {
                        if (datagram.length()>0)
                            datagram += "\n";
                        datagram += aline;
                        aline = r.readLine();
                    }
                step = 8;
                }
                // datagram = enc.decrypt(datagram);
                state = 2;
                

            } catch (java.net.ConnectException w) {
                datagram =  errs[1] + ":" + step;;
            } catch (SocketTimeoutException e) {
                datagram =  errs[2] + ":" + step;;
            } catch (SocketException e) {
                if (step < 7)
                    datagram =  errs[3] + ":" + step;;
            } catch (IOException e) {
                datagram =  errs[1] + ":" + step;;
            }

            if (sos != null) try {
                sos.close();
            } catch (Exception e) {
            }
            if (sis != null) try {
                sis.close();
            } catch (Exception e) {
            }
            if (socket != null) try {
                socket.close();
            } catch (Exception e) {
            }
            //if (progress!=null) 
                setProgress(100);
           // if (tid - maxid - 3>=0) cansend[(tid - maxid - 3) / 5] = true;

            return datagram;

        }

 
        protected void done()  // onPostExecute can access UI!!!!
        {
            super.done();
            String datagram = "";
            try{ datagram = get();}catch(Exception e){}
          
            JPanel tl = (JPanel) findViewById(R.id.main_table);

            CSVParse p = new CSVParse(datagram, '"', new String[]{",", "\n"});
 
            String[][] s = p.nextMatrix();
            if (questionN>0)
            click2sent[questionN-1] = true;
            if (s.length == 1  )
            {
                if (questionN > 0)
                {
                    JLabel sendbtn = (JLabel) compbyid(tid-1);
                    JLabel feedback = (JLabel) compbyid(tid);
                    if (feedback!=null)
                    {
                        if (datagram.equals(""))
                            datagram = errs[0];
                        feedback.setText(datagram);
                        feedback.setForeground(ParseLayout.hex2Rgb("#ff0000"));
                    }
                    sendbtn.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                }
                if(step < 5)
                {
                    if (rr == -1)
                      mytoast(null,MainActivity.this.getString(R.string.wrongip) + ": " + step);
                    else
                    {
                        rr++;sendall(); 
                    }
                    return;
                }
            }
            NQ = s.length - 1;
            if (!r.session.equals(s[NQ][0]))
            downloaded = str2list(resdb.getOthers("Response", s[NQ][0],-1));
            r.session = s[NQ][0];
            
            lblses.setText(r.session);
            if (s[NQ].length < 4)
            {
                if (rr == -1)
                mytoast(null,"Invalid datagram:" + datagram);
                else if (rr>=0){ rr++;sendall();}
                return;
            }
            lbltimes.setText(s[NQ][1]);
            try{
                r.timemoment = Long.parseLong(s[NQ][1]);
                permit = Integer.parseInt(s[NQ][3]);
            }catch (Exception e1)
            {
                if (rr == -1)
                mytoast(null,"Invalid datagram:" + datagram);
                else if (rr>=0){ rr++;sendall();}
                return;
            }
            if (r.timemoment != 0) lbltimes.setText(Responsetbl.dstr1(new Date(r.timemoment)));
            r.numberq = NQ;
            String thisorder =  s[NQ][2];

            if (permit == 0)
            {
                if (rr == -1)
                mytoast(null, "You are disconnected with this Enquirer. Change port number or short IP to communicate with another Enquirer");
                else if (rr>=0){ rr++;sendall();}
                return;
            }
            else if (permit < 3)
            {
                endis(new int []{R.id.download, R.id.camera, R.id.file},false);
            }
            else if (permit == 3)
            {
                endis(new int []{R.id.download},true);
                endis(new int []{R.id.camera, R.id.file},false);
            }
            else if (permit == 4)
            {
                endis(new int []{R.id.download,R.id.camera, R.id.file},true);
            }
            if (questionN > 0)
            {
                orders.set(questionN - 1, thisorder);
            }
            for (int i = NR; i < NQ; i++)
            {
                questions.add("");myAnswers.add("");standardAnswers.add("");orders.add("");
                click2sent[i] = false; graded[i] = false;
            }
            for (int i = 0; i < NQ; i++)
            {
                if (s[i].length <2) continue;
                String[] z = s[i];  //z[0] is question  text and z[1] is the standard answer text
                boolean questionchanged = (i < questions.size() && !questions.get(i).equals("") && !z[0].equals("") && !z[0].equals(questions.get(i)));
                graded[i] = false;
                questions.set(i, z[0]);
                if (z[0]!=null&& z[0].length()>0)
                    ((JTextField)compbyid(5 * (i) + maxid + 1)).setToolTipText(z[0]);
                int N = NR;
                if (i >= N)
                {
                    if (N == 7)
                    {
                        if (!instr.getText().toString().equals("")) instr.setText("");
                    }
                    addrow("", "", "");
                }
                String stardardAnswer = z[1].trim();
                standardAnswers.set(i, stardardAnswer);

               // LinearLayout row = (LinearLayout) tl.getChildAt(i);

                if (i == questionN - 1)
                {   
                    JLabel order = (JLabel) compbyid(5 * i + maxid + 2);
                    order.setText( thisorder);
                    order.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                }
                JLabel feedback = (JLabel) compbyid(5 * i + maxid + 3);
                JTextField edit = (JTextField) compbyid(5 * i + maxid + 1); 
                String myAnswer = edit.getText().toString().trim();

                int l = i;//(edit.getId() - maxid - 1) / 5;
                String filepath = patharr.get(l + myAnswer);

                graded[l] = false;
                if (!click2sent[l]) myAnswer = "";

                boolean b =  stardardAnswer.equals("")|| filepath!=null || myAnswer.equals("") || questionchanged;
                JLabel btn = (JLabel) (compbyid(5 * i + maxid + 2)); 
                edit.setEnabled(b);
                //edit.setFocusable(b);
                if (!b)
                {
                    if (btn.getText().indexOf("html")>=0)
                        btn.setText("");
                    else if (btn.getText().indexOf(">")>=0)
                        btn.setText(btn.getText().replaceFirst(">",""));
                    btn.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                }
                else
                {
                    if (btn.getText().toString().equals(""))
                        btn.setText("<html>&rarr;</html>");
                    else if (btn.getText().matches("[0-9]+") && btn.getText().indexOf(">")<0)
                        btn.setText(btn.getText()+ ">");
                    btn.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
                }

                if (click2sent[l] && !myAnswer.equals(""))
                {
                    if (filepath==null && !stardardAnswer.equals(""))
                    {
                        graded[l] = true;
                        if (same(myAnswer, stardardAnswer)) {
                            feedback.setText(GOOD);
                            feedback.setForeground(ParseLayout.hex2Rgb("#00ee00"));
                        } else {
                            feedback.setText(stardardAnswer);
                            feedback.setForeground(ParseLayout.hex2Rgb("#ee0000"));
                        }
                    }
                    else
                    {
                        if (i == questionN - 1)
                        {
                            if (filepath != null)
                            {
                                if (codes=='3')
                                {
                                    feedback.setText(getString(R.string.sent));
                                    feedback.setForeground(Color.BLUE);
                                    feedback.addMouseListener(new Imgopen(filepath));
                                }
                                else if (codes >= '1')
                                {
                                    String txt[] = getString(R.string.errorcode).split("\\|");
                                    feedback.setText(txt[(int)(codes-'1')]);
                                    feedback.setForeground(Color.RED);
                                    feedback.addMouseListener(null);
                                }
                            } else {
                                feedback.setText(getString(R.string.sent));
                                feedback.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                                feedback.addMouseListener(null);
                            }
                        }
                        else
                        {
                            if (filepath == null && feedback.getText().toString().equals(""))
                            {
                                feedback.setText(getString(R.string.sent));
                                feedback.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                                feedback.addMouseListener(null);
                            }
                        }
                    }
                    ;
                }

            }
            int width =  (getWidth()-90) / 2;  if (width < 200) width =200;
            for (int i = 0; i < NQ; i++)
            {
                for (int j = 1; j <= 3; j = j+2)
               {
                   Component tf = compbyid(5 * i + maxid + j);
                   tf.setPreferredSize(new Dimension(width,25));
                   tf.setMinimumSize(new Dimension(tf.getPreferredSize().width - 1, tf
            .getPreferredSize().height));
               }
            }
            if (rr>=0){ rr++;sendall();}
        }
    }

    public static boolean same(String x, String y) {
        x = x.trim().toLowerCase();
        y = y.trim().toLowerCase();
        try {
            double dx = Double.parseDouble(x);
            double dy = Double.parseDouble(y);
            return dy == dx;
        } catch (Exception e) {
            String[] xs = x.split("[\\s]+");
            String[] ys = y.split("[\\s]+");
            if (xs.length == 1 && ys.length == 1) {
                int lx = x.length();
                int ly = y.length();
                if (lx == ly) {
                    return x.equals(y);
                }
                if (lx < ly) {
                    return lx > 1 && y.indexOf(x.substring(0, lx - 1)) == 0;
                }
                return ly > 1 && x.indexOf(y.substring(0, ly - 1)) == 0;
            }
            int count = 0;
            for (String p : xs)
                if (y.contains(p)) count++;
            for (String p : ys)
                if (x.contains(p)) count++;
            return count * 10 > 6 * (xs.length + ys.length);
        }
    }

     

     
 
 
    boolean usedcamera = false;
int webcamattendance(String csname) {
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            String filepath3 = datafolder + File.separator + csname.replaceAll("[^a-z]", "") + System.currentTimeMillis() % 10000 + ".png";
            BufferedImage image = webcam.getImage();
            webcam.close();
            File f = new File(filepath3);
            ImageIO.write(image, "PNG", f);
            if (f.length() < 1000) {
                return 0;
            }
            
        } catch (Exception e) {
        }
        return 0;
    }
    private void takephoto() {
        try
        {
        Webcam webcam = Webcam.getDefault();
	webcam.open();
        BufferedImage image = webcam.getImage();
        selectedImagePath = uploadfolder +File.separator + System.currentTimeMillis() % 100000000 + ".png";
        
        
        int width = image.getWidth();
        Image newImage = null;
        int w = -1;
        Object x0    = JOptionPane.showInputDialog(
                                     this,
                                     R.string.resize,
                                     R.string.resize0,
                                     JOptionPane.QUESTION_MESSAGE,
                                     (Icon)(new ImageIcon(iconpath("setting"))),
                                     null,
                                     "" + width);
        if (x0 == null) return;
        String neww = x0.toString();
                            
        if (neww != null) 
            try{
        {
            w = Integer.parseInt(neww);
            
            if (w > 10)
            {
                newImage = image.getScaledInstance(w, image.getHeight()*w/width, Image.SCALE_DEFAULT);
            }
        }
        }catch(Exception e){}
        
        if (newImage != null) 
        {
           // newImage =  ImageIO.write(newImage, "PNG", new File(selectedImagePath));
            BufferedImage bimage = new BufferedImage(w, image.getHeight()*w/width, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(newImage, 0, 0, null);
            bGr.dispose();
            ImageIO.write(bimage, "PNG", new File(selectedImagePath));
        }
        else
            ImageIO.write(image, "PNG", new File(selectedImagePath));
        webcam.close();
        (new Updateedit()).run();
       
        }catch(Exception e){}
        //Image newImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
    }

     
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case NET_REQUEST_CODE:
                 
                break;
            case READ_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                 
                break;
            case WRITE_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                 
                break;

            case CAMERA_REQUEST_CODE:
             
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }

    public File getPhotoFileString(String fileName) {
         
        File file = new File(  fileName);

        return file;
    }

    File photoFile;

    private void takephoto1() {
        


    }


    static boolean goodport(int port)
    {
        boolean result;

        try {

            Socket s = new Socket("127.0.0.1", port);
            s.close();
            result = true;

        }
        catch(Exception e) {
            result = false;
        }

        return(result);
    }

   

    class ReceiveData implements Runnable {

        String receivedAnswer;
        String sid;
        int questionNumber;
        Context c;
        Socket socket = null;
        String filename;

        public ReceiveData(Context c, Socket socket) {

            this.c = c;
            this.socket = socket;

        }


        public void run() {
            try {
                String rip = socket.getRemoteSocketAddress().toString().replaceFirst(":.*", "").replaceFirst("^[^0-9]", "");
                InputStream is = socket.getInputStream();
                BufferedReader r = new BufferedReader(new InputStreamReader(is));

                String sentence = r.readLine();
                Msg msg = new Msg(sentence);
                String reply = "";
                if (!msg.valid || msg.questionNumber < 0) {
                    reply = "Error";

                } else {

                    socket.close();
                    //  runOnUiThread(new  Runupdata(c, tl, msg));

                }
            } catch (IOException e) {
            } catch (Exception e) {
            }
        }
    }
    class GetInfo implements MouseListener 
    {
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
        public void mouseClicked(MouseEvent e) {
         
            String unid = txtuid.getText().trim();
            if (unid.equals("")) return;
            String rip = wholeip();
            if (rip.equals("")) return;
            savep("unid", unid );
            progress = null;
            SendMsg(-1, "", 0 );
        }
    }



    class Imgopen implements MouseListener {
       
        public void mousePressed(MouseEvent e){}
        public void mouseReleased(MouseEvent e){}
        public void mouseEntered(MouseEvent e){}
        public void mouseExited(MouseEvent e){}
       
        public Imgopen(String msg) {
            this.filepath = msg;
        }

        String filepath;

        public void mouseClicked(MouseEvent e) {
            File f = new File(filepath);
            if (!f.exists()) return;
            if (filepath.indexOf(".jpg") > 0 || filepath.indexOf(".png") > 0 || filepath.indexOf(".gif") > 0){
                ;//new GalleryActivity(filepath);
                 
            }
            else
            {
                otheropen(f );
            }
        }
    }

    static void otheropen(File f )
    {
        //goto file folder
    }
    void resizesend(final String path) {

       String m = JOptionPane.showInputDialog(this,getString(R.string.resize),"200");// + b.getWidth());
         
      
    }
    static int TIME_VISIBLE = 3000;
    
    public void mytoast(Context c, String msg)
    {
        JOptionPane.showMessageDialog(this, msg);
       
    }

   static public void main(String [] args)
   {
        loadparams();
        folderok();
        String role = get("role", null);
        if (role != null && role.equals("Enquirer"))
        {
            new  EnquireActivity().onCreate(); 
        }
        else
            new MainActivity().onCreate();
    }
  }
