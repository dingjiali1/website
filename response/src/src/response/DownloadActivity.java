package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.awt.*;
 
public class DownloadActivity extends AppCompatActivity
{
   
   void makeElements()
   {
      Border border1,margin1;
       GridBagConstraints c;
     
      JPanel ll2 = new JPanel(new GridBagLayout());
      ll2.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      ll2.setOpaque(true);
      components.put(R.id.maintbl0,ll2);
      
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(1,1,1,1);
      c.weightx = 0.9;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.9;
      contentpane.add(ll2,c);
        
      JLabel lbl4 = new JLabel( "<html>" +R.string.download2.replaceAll("\n","<br>").replaceAll("\\.",".<br>") + "</html>");
      components.put(R.id.instrd,lbl4);
      lbl4.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl4.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl4.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 0;
      c.ipadx = c.ipady = 5;
      c.insets = new Insets(1,1,3,1);
      c.weighty = 0;
      ll2.add(lbl4,c);
      
      
      GridBagLayout contentPaneLayout = new GridBagLayout();
     // contentPaneLayout.rowHeights = new int[] {25,25,25,25,25,25,25,25,25,25,25};
      JPanel ll3 = new JPanel(contentPaneLayout);
      ll3.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      ll3.setOpaque(true);  
      components.put(R.id.maintbl,ll3);
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0;
      c.insets = new Insets(1,0,1,0);
      ll2.add(ll3,c);
      
      lbl5 = new JLabel();
      lbl5.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl5.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl5.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      lbl5.setOpaque(true);  
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 2;
      c.weighty = 0;
      c.insets = new Insets(1,0,1,0);
      ll2.add(lbl5,c);
      
       
      
      
   }
   JLabel lbl5 = null;
   public DownloadActivity()
   {
      super();
      try{buildToolbar("download.xml");}catch(Exception e){}
      makeElements();
       pack();
   }
    static public void main(String [] args)
   {
      new DownloadActivity();
    }
  
 
    final int SELECT_FILE_CODE = 101;
    final int READ_REQUEST_CODE = 102;
    private final String CHECK = "" + ((char) 10003);
    private final String UNCHECK = "" + ((char) 10007);
    ArrayList<String> files, oldfiles = new ArrayList<>();
    ArrayList<String> downloaded = null;
    String rip = null, myid="";
    String password;
    String afilepath = "";
    int NR = 0;
    int COL = 0;
   // int maxid = R.id.maintbl + 1;
    File downloadfolder = new File(AppCompatActivity.downloadfolder);//System.getProperty("user.home") + File.separator + "Downloads");
    JPanel tl;
    JLabel txt = null;
    boolean matches[];
    long [] flength = null;
    long []  dlength = null;
    long []  olddlength = null;
    private JProgressBar progress;

    String slash(String fd)
    {
        if (File.separatorChar == '/')
                fd = fd.replace('\\', File.separatorChar);
            else 
                fd = fd.replace('/', File.separatorChar);
        return fd;
    }

    File tempfile(String fn, String fd)
    {
        if (!downloadfolder.exists()) downloadfolder.mkdir();
        File f = null;
        if (fd == null)
        {
            f = new File(downloadfolder, fn);
        }
        else
        {
            fd = slash(fd);
            fn = slash(fn);
            int jj = fn.lastIndexOf(File.separator);
            fn = fn.substring(jj+1);
            String s0 = AppCompatActivity.downloadfolder  + File.separator + fd;
            File f1 = new File(s0);
            
            if (!f1.exists())
            {
                
                boolean b = f1.mkdir();
                 
            }
            f = new File(f1,fn);
        }
         
        //if (f.isFile() && f.exists()) f.delete();
        
        return f;
    }
    @Override
    protected void onCreate( ) {
        super.onCreate( );
        Intent  intent  = getIntent();
        files = intent.getStringArrayListExtra("download");
        downloaded = intent.getStringArrayListExtra("downloaded");
        myid = intent.getStringExtra("myid");
        rip = intent.getStringExtra("rip");
        password = intent.getStringExtra("password");
        tl = (JPanel) findViewById(R.id.maintbl);
        txt = (JLabel) findViewById(R.id.instrd) ;
        setTitle(getString(R.string.downlist));
      //  maxid = R.id.maintbl + 1;
        if(files == null)
        {
            COL = 5;
            makeheading();
            files = new ArrayList<>();
            txt.setText("");
            new TCPDownload(rip, myid, "0", getString(R.string.downlist), "0").execute();
        }
        else
        {
            COL = 4;
            makeheading();
            
            for (int i = 0; i < files.size(); i++)
            {

                addrow(files.get(i),"");
                oldfiles.add(files.get(i));
            }
            activemenu(R.id.downall,false);
        }
        
        //onCreateOptionsMenu();
    }

     


    public void onCreateOptionsMenu()
    {
       
        if (downloaded==null)
        {
            findItem(R.id.downall).setVisible(false);
        }
        else
        {
            JButton menuitem = (JButton)findViewById(R.id.file);
            if (menuitem!=null)
                menuitem.setIcon( (Icon)(new ImageIcon(iconpath("refresh"))));
        }
        
        JButton menuitem = (JButton)findViewById(R.id.downall);
            if (menuitem!=null)
                menuitem.setIcon( (Icon)(new ImageIcon(iconpath("downall"))));
        

    }
    public void onOptionsItemSelected(int j)
    {
         
        if (j == R.id.downall)
        {

            if (downloaded!=null)
            {
                downall();
            }

        }
        else if (j == R.id.cancel)
        {
            Intent intent = new Intent();
            if (downloaded==null)
            {
                intent.putStringArrayListExtra("download", files);
            }
            else
            {
                downloaded.clear();
                
                for (int i=0; i < NR; i++)
                {
                    JLabel t = ((JLabel)ele.get(i+1).get(2));
                    downloaded.add( (dlength[i]>olddlength[i]?dlength[i]:olddlength[i]) + " " + t.getText());
                }
                intent.putStringArrayListExtra("downloaded", downloaded);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        if (j == R.id.save)
        {
            Intent intent = new Intent();
            if (downloaded==null)
            {
                for (int i = NR - 1; i >= 0; i--)
                {
                    JLabel t = ((JLabel)ele.get(i+1).get(3));
                    if (t.getText().equals(UNCHECK))
                        files.remove(i);
                }
                intent.putStringArrayListExtra("download", files);
            }
            else
            {
                downloaded.clear();
               
                for (int i = 0; i < NR; i++)
                {
                    JLabel t1 = ((JLabel)ele.get(i+1).get(1));
                    JLabel t4 = ((JLabel)ele.get(i+1).get(4));
                    JLabel t2 = ((JLabel)ele.get(i+1).get(2));
                    if (t4.getText().equals(CHECK) && !t1.getText().equals(""))  
                    {
                        downloaded.add((dlength[i]>olddlength[i]?dlength[i]:olddlength[i]) + " " + t2.getText());
                    }
                    else
                    {
                        new File(t2.getText()).delete();
                    }
                }
                
                intent.putStringArrayListExtra("downloaded", downloaded);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
        else  if (j == R.id.file)
        {
            if (downloaded==null)                
            {
                pickfile();
            } 
            else
                refresh();
        }

         
    }

    private void downall()
    {
        StringBuffer filenums = new StringBuffer();
        StringBuffer filenames = new StringBuffer();
        StringBuffer filelengths = new StringBuffer();
        for (int i=0; i < files.size(); i++)
        {
            JLabel check = ((JLabel)ele.get(i+1).get(4));
            JLabel fn = ((JLabel)ele.get(i+1).get(1));

            if (check.getText().equals(CHECK) )
            {
                JLabel pathc = ((JLabel)ele.get(i+1).get(2));
                String path = pathc.getText();
                if (!path.equals("") && (new File(path)).exists()) continue;
                if (filenums.length()>0)
                {
                    filenums.append(",");
                    filenames.append(",");
                    filelengths.append(",");
                }
                filenums.append("" + (i+1));
                filenames.append(fn.getText());
                filelengths.append(flength[i]);
                JLabel target = ((JLabel)ele.get(i+1).get(2));
                target.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        new  TCPDownload(rip, myid, filenums.toString(), filenames.toString(), filelengths.toString()).execute();

    }

    private void refresh()
    {
        for (; NR>=1; NR--)
        {
            ArrayList<Component> tr = ele.get(NR);
            int N = tr.size();
            for (int j=N-1; j >=0; j--)
            {
                tl.remove(tr.get(j));
                tr.remove(j);
            }
            ele.remove(NR);
        }
        files = new ArrayList<>();
        txt.setText("");
        new TCPDownload(rip, myid, "0", getString(R.string.downlist), "0").execute();
    }

    void process1(File f)
    {
        if (f.isFile()){process(f.getAbsolutePath());return;}
        File [] s = f.listFiles();
        for (File d:s)
            process1(d);
    }
    File lastfold = null;
    
    private void pickfile()
    {
        if (lastfold == null)
        lastfold = new File(AppCompatActivity.servefolder);
        JFileChooser choice = new JFileChooser();
        choice.setCurrentDirectory(lastfold);
        choice.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int option = choice.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try{
                String p = choice.getSelectedFile().getPath();
               // int j = p.lastIndexOf(File.separator);
               // lastfold = new File(p.substring(0,j));
                process(p);
            }catch(Exception e){}
        }  
    }

    void process(String selectedImagePath)
    {
        File ff = new File(selectedImagePath);
        long l = 0;
        if (ff.isFile()) l = ff.length();
        
        for (String fn: files)
        if (fn.contains(" " + selectedImagePath))
        {
            txt.setText("duplicate");
            return;
        }

        files.add(l + " " + selectedImagePath);
        String afilepath = l + " " + selectedImagePath;
        addrow(afilepath,"");
        tl.revalidate();//forces panel to lay out components again
            tl.repaint();
        
    }
     
    

    String shortnum(long fle)
    {
       String flen = "" + fle;
       int l = flen.length();
        if (l>3) return flen.substring(0,flen.length()-3);
        else if (l==3) return ("." + flen).replaceFirst("[0]+$","");
        else if (l==2) return (".0" + flen).replaceFirst("[0]+$","");
        else if (l==1 && !flen.equals("0")) return ".00" + flen;
        return "0";

    }
    ArrayList<ArrayList<Component>> ele = new ArrayList<>();
    int [] getR(JLabel u)
    {
        int rr = 0,  cc=0;
        for (; rr < ele.size(); rr++)
            for (cc=0; cc < ele.get(rr).size(); cc++)
                if (ele.get(rr).get(cc).equals(u))
                    return new int[]{rr-1,cc};
        return new int[]{-1,-1};
    }
    public void mytoast(Context c, String msg)
    {
        JOptionPane.showMessageDialog(this, msg);
       
    }

    String findsize(JLabel u)
  {
      int r = 0; 
      while ((JLabel)ele.get(r+1).get(2) != u)
          r++;
      String s = ((JLabel)ele.get(r+1).get(1)).getText();
      long ll = countfilelength(new File(s));
      files.set(r,files.get(r).replaceFirst("0 ", ll + " "));
      return shortnum(ll);
  }
  
  long countfilelength(File f)
  {
      if (f.isFile()) return f.length();
      long s = 0;
      File [] fs = f.listFiles();
      for (File g:fs)
      {
          s += countfilelength(g);
      }
      return s;
  }
    private void addrow(  String filepath,   String path)
    {
        String [] s1 = split(filepath);
        String [] s2 = split(path);
        long flen = 0; try{ flen = Long.parseLong(s1[0]);}catch(Exception e){}
         
        long dlen = 0; try{ dlen = Long.parseLong(s2[0]);}catch(Exception e){}
        
        if (COL == 5){
           olddlength[NR] = dlen;
            flength[NR] = flen;
        }
        

        String word[];
        int [] width, align;
        boolean downloadable = true;


        if (COL == 4)
        {
            word = new String[]{"" + (NR + 1), s1[1], flen ==0 ?"?":shortnum(flen), CHECK};
            width = new int[]{50,getWidth() - 150,50, 50 };
            align = new int[]{SwingConstants.RIGHT, SwingConstants.LEFT,SwingConstants.RIGHT,SwingConstants.CENTER};
        }
        else
        {
            downloadable = (flen != dlen || path.equals(""));// && !s1[0].equals("0");
            String str = "";
            if(dlen !=0 &&  flen > dlen)
                str  = shortnum(  dlen)+"<"+shortnum( flen);
            else   
                str  =  shortnum( flen  > dlen? flen: dlen);
             
            
            word = new String[]{"" + (NR + 1), s1[1], s2[1], str, CHECK};
            width = new int[]{50,200, getWidth() - 350, 50, 50 };
            align = new int[]{SwingConstants.RIGHT, SwingConstants.LEFT, (path.equals("")?SwingConstants.CENTER:SwingConstants.LEFT), SwingConstants.RIGHT,SwingConstants.CENTER};
        }

        ArrayList<Component> tr = new ArrayList<>();
        GridBagConstraints c =  new GridBagConstraints();
        c.ipadx = 1;
        c.ipady = 0;
        c.gridy = NR+1;
        c.weighty = 0.01;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.BOTH;
         
        for (int j = 0; j < COL; j++) 
        {
            if (j == 0)
               c.insets = new Insets(0, 1, 1, 1);
            else
               c.insets = new Insets(0, 1, 1, 0);
            c.ipadx = 1;
            c.ipady = 0;
            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.BOTH;
            c.gridx = j;
            c.gridy = NR + 1;
            c.weighty =0;
            c.weightx = 0.1;
            
               
            if (j==0 || j == COL-1 || j==COL-2)
                c.weightx = 0.1;
            else
                c.weightx = 0.9/(COL-3);
            c.gridx = j; 
            JLabel textviewj = new JLabel(word[j],align[j]);
            textviewj.setFont(new Font("Time",Font.PLAIN,AppCompatActivity.fontsize));
            textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
            // textviewj.setId(COL * (NR) + maxid + j);
            textviewj.setBackground(ParseLayout.hex2Rgb("#fdfdfd"));
            textviewj.setOpaque(true);
            if (j==0 || j == COL-1 || j==COL-2)
                textviewj.setPreferredSize(new Dimension(50,25));
            else
                textviewj.setPreferredSize(new Dimension(getWidth()-150,25));
            if ( 1==2 )//j==2 && COL==4 ||   COL==5 && j==3)
            {
                if (word[j].indexOf("<")>0) 
                    textviewj.setForeground(ParseLayout.hex2Rgb("#ff0000"));
                textviewj.addMouseListener(new MouseListener() {
                   
                    public void mousePressed(MouseEvent e){}                                    
                    public void mouseReleased(MouseEvent e){}                                    
                    public void mouseEntered(MouseEvent e){}                                    
                    public void mouseExited(MouseEvent e){}                                    
                    public void mouseClicked(MouseEvent e) {
                        JLabel  u = (JLabel)(e.getSource());
                        rr = getR(u)[0];//(u.getId() - maxid -1)/COL;
                        Long flen = flength[rr];
                        mytoast(null,flen + " bytes");
                    }
                });
            }
                else if (j==1 &&  COL==5 && downloadable)
                {
                    textviewj.setForeground(ParseLayout.hex2Rgb("#0000ff"));
                    textviewj.addMouseListener(new MouseListener() {
                         @Override
                        public void mousePressed(MouseEvent e){}                                    
                        public void mouseReleased(MouseEvent e){}                                    
                        public void mouseEntered(MouseEvent e){}                                    
                        public void mouseExited(MouseEvent e){}                                    
                        public void mouseClicked(MouseEvent e) {
                            JLabel  u = (JLabel)(e.getSource());
                            rr = getR(u)[0];//.getId() - maxid -1)/COL;
                            String filename= u.getText();
                            new  TCPDownload(rip, myid, "" + (rr+1), filename, flength[rr]+"").execute();
                        }
                    });
                }
                else if (j==1 && COL==4|| j==2 && COL==5)
                {
                    if (path.equals(""))
                    {
                        textviewj.setHorizontalAlignment(SwingConstants.CENTER);
                    }
                    if (filepath.indexOf(".jpeg")>0 ||filepath.indexOf(".jpg")>0 || filepath.indexOf(".png")>0 || filepath.indexOf(".gif")>0)
                    {
                        textviewj.setHorizontalAlignment(SwingConstants.LEFT);
                        textviewj.setForeground(ParseLayout.hex2Rgb("#0000ff"));
                        textviewj.addMouseListener(new MouseListener() {

                            @Override
                            public void mousePressed(MouseEvent e){}                                    
                            public void mouseReleased(MouseEvent e){}                                    
                            public void mouseEntered(MouseEvent e){}                                    
                            public void mouseExited(MouseEvent e){}                                    
                            public void mouseClicked(MouseEvent e) {
                                JLabel  u = (JLabel)(e.getSource());
                                String filepath = u.getText();
                                if (filepath.equals("")) return;
                                new GalleryActivity(filepath);
                                
                            }
                        });
                    }
                    else {
                        textviewj.setHorizontalAlignment(SwingConstants.LEFT);
                        textviewj.setForeground(ParseLayout.hex2Rgb("#0000ff"));
                        textviewj.addMouseListener(new MouseListener() {
                            @Override
                            public void mousePressed(MouseEvent e){}                                    
                            public void mouseReleased(MouseEvent e){}                                    
                            public void mouseEntered(MouseEvent e){}                                    
                            public void mouseExited(MouseEvent e){}                                    
                            public void mouseClicked(MouseEvent e) {
                                 //Intent myIntent = new Intent(Intent.ACTION_VIEW);
                                JLabel u = (JLabel)(e.getSource());
                                String filepath = u.getText();
                                if (filepath.equals("")) return;
                               // File f  = new File(filepath);
                               try{Runtime.getRuntime().exec("explorer.exe " + filepath);}
                               catch(Exception e2){
                               try{ new ColinMcTaggart().path(filepath);}catch(Exception e1){}
                                //MainActivity.otheropen(f,DownloadActivity.this);
                               }
                            }
                        });
                    }
                }
                else if (j==3 && COL==4 || j==4 && COL==5)
                {
                    textviewj.addMouseListener(new MouseListener() {
                        public void mousePressed(MouseEvent e){}                                    
                        public void mouseReleased(MouseEvent e){}                                    
                        public void mouseEntered(MouseEvent e){}                                    
                        public void mouseExited(MouseEvent e){}                                    
                        public void mouseClicked(MouseEvent e) {
                            JLabel  u = (JLabel)(e.getSource());
                            if (u.getText().equals(CHECK))
                            {
                                u.setText(UNCHECK);
                            }
                            else
                            {
                                u.setText(CHECK);
                            }
                        }
                    });
                }
                else if (j==2 && COL == 4 && word[j].equals("?"))
                {
                     textviewj.addMouseListener(new MouseListener() {
                        public void mousePressed(MouseEvent e){}                                    
                        public void mouseReleased(MouseEvent e){}                                    
                        public void mouseEntered(MouseEvent e){}                                    
                        public void mouseExited(MouseEvent e){}                                    
                        public void mouseClicked(MouseEvent e) {
                            JLabel  u = (JLabel)(e.getSource());
                            u.setText(findsize(u));
                        }
                    });
                }
            
                tl.add(textviewj,c);
                tr.add(textviewj);
        }
        ele.add(tr);
        NR++;
    }
   class ColinMcTaggart {
   final String[] prefix = {
      "osascript",
      "-e", "tell application \"Finder\"",
      "-e", "activate",
      "-e", "<openCmdGoesHere>",
      "-e", "end tell"
  };
   
  
  
  void path(String path) throws Exception {
    prefix[6] = buildFolderCommand(path);
    Runtime.getRuntime().exec(prefix).waitFor();
  }
 
 String buildFolderCommand(String folderPath) {
    StringBuilder openCommand = new StringBuilder("open ");
    String[] pathParts = folderPath.split("/");
    for (int i = pathParts.length - 1; i > 0; i--) {
      openCommand.append("folder \"").append(pathParts[i]).append("\" of ");
    }
    return openCommand.append("startup disk").toString();
  }
}
    private void makeheading() 
    {
        
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 1;
        c.ipady = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
       
        //tr.setBackground(ParseLayout.hex2Rgb("#666666"));
       
        ArrayList<Component> tr = new ArrayList();
        JLabel btn = null;
        String head[] = null; int width[];
        int [] align;
        if (COL==5)
        {
            head = new String[]{"#", getString(R.string.downloadname), getString(R.string.download1),getString(R.string.KB), CHECK};
            width = new int[]{50, 200, getWidth() - 350,50, 50};
            align = new int[]{SwingConstants.RIGHT, SwingConstants.LEFT, SwingConstants.LEFT, SwingConstants.RIGHT,SwingConstants.CENTER};
        }
        else
        {
            head = new String[]{"#", getString(R.string.downloadpath),getString(R.string.KB), CHECK};
            width = new int[]{50, getWidth() - 150,50, 50};
            align = new int[]{SwingConstants.RIGHT, SwingConstants.LEFT,SwingConstants.RIGHT,SwingConstants.CENTER};
        }
        for (int j = 0; j < COL; j++) 
        {
            c.gridx = j;
            if (j == 0)
               c.insets = new Insets(1, 1, 1, 1);
            else
               c.insets = new Insets(1, 1, 1, 0);
            if (j==0 || j == COL-1 || j==COL-2)
                c.weightx = 0.1;
            else
                c.weightx = 0.9/(COL-3);
            c.weighty = 0.01;
            
            JLabel textviewj = new JLabel(head[j],align[j]);
            textviewj.setFont(new Font("Time",Font.PLAIN,AppCompatActivity.fontsize));
            textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
            textviewj.setBackground(ParseLayout.hex2Rgb("#dddddd"));
            textviewj.setOpaque(true);
            if (j==0 || j == COL-1 || j==COL-2)
                    textviewj.setPreferredSize(new Dimension(50,25));
            
             if (j==4 && COL==5 || j==3 && COL==4)
                textviewj.addMouseListener(new MouseListener() {

                    @Override
                    public void mousePressed(MouseEvent e){}                                    
                    public void mouseReleased(MouseEvent e){}                                    
                    public void mouseEntered(MouseEvent e){}                                    
                    public void mouseExited(MouseEvent e){}                                    
                    public void mouseClicked(MouseEvent e) {
                        JLabel  u = (JLabel)(e.getSource());
                        if (u.getText().equals(CHECK))
                        {
                            u.setText(UNCHECK);
                         }
                        else
                        {
                            u.setText(CHECK);
                        }
                        String m = u.getText();

                        for (int i=0; i < NR; i++)
                        {
                            JLabel  w = ((JLabel)ele.get(i+1).get(4));
                            w.setText(m);
                        }

                    }
                });
            tl.add(textviewj,c);
            tr.add(textviewj);
            
        }
        ele.add(tr); 
    }
    int rr;
    class FileInfo
    {
        int filenum;
        String filename;
        long filelength;
        int afilenum;
        int step;
        public FileInfo(int filenum,
        String filename,
        long filelength,
        int afilenum,
        int step)
        {
            this.filenum = filenum;
        this.filename = filename;
        this.filelength = filelength;
        this.afilenum = afilenum;
        this.step = step;
        }
        public String toString()
        {
            return filenum + "|" + filename + "|" + filelength + "|" + step;
        }
    }
    int filenumlength[];
    synchronized void  setlength(long [] len, int filenum, long g)
    {
        len[filenum] = g;
    }
    
    private class TCPDownload extends SwingWorker<String, Integer> 
    {
        int   filenum;
        String  filename;
        long filelength   ;
        int afilenum;
       // int [] filenums;
       // String[] filenames;
       // long [] filelengths ;
       // int [] afilenums;
        int  step;
        
       // int [] steps;
        String err = "";
        boolean keep = true;
        ArrayList<FileInfo> fileinfos = new ArrayList<>();
        protected void process(java.util.List<Integer>  prog)
        { 
            
           int percent = prog.get(prog.size()-1);
           if (fd!=null)
               txt.setText(percent + "%");
           else
           {
               ((JLabel)ele.get(filenum).get(2)).setText(percent + "%");
           } 
        }
        protected void onProgressUpdate(Integer... prog)
        {
            
        }
        long t0 = 0;
        String [] params;
        public TCPDownload(String ip,String  myid,String  filenum,String  filename, String filelength, String fd)
        {
            super();
            this.params = new String[]{ip, myid, filenum, filename, filelength};
            this.fd = fd;
        }
        public TCPDownload(String ip,String  myid,String  filenum,String  filename, String filelength)
        {
            super();
            this.params = new String[]{ip, myid, filenum, filename, filelength};
        }
        String fd = null;
        protected String doInBackground() {
            String ip = params[0];
            String myid = params[1];
            String filenumstr []=null;
            String filelengthstr[]=null;
            
            if (params[2].matches("[0-9]+")  )
            {
                filenum =  Integer.parseInt(params[2]);
                //filenums = new int[]{filenum};
                filename = params[3];
                //filenames = new String[]{filename};
                filelength = Long.parseLong(params[4]);
                //filelengths = new long[]{filelength};
                afilenum = filenum;
                //afilenums = new int[]{filenum};
                //steps = new int[]{0};
                step = 0;
                fileinfos.add(new FileInfo(filenum, filename,filelength, afilenum, step));
            }
            else 
            {
                fd = null;
                filenumstr   = params[2].split(",");
               // steps = new int[filenumstr.length];
                String [] filenames   = params[3].split(",");
                filelengthstr   =  params[4].split(",");
                //filenums = new int[filenumstr.length];
                //filelengths = new long[filenumstr.length];
               // afilenums = new int[filenumstr.length];
                for (int i = 0; i < filenumstr.length; i++)
                {
                    String a = filenumstr[i];
                    String b = filelengthstr[i];
                    try{
                       // filenums[i] = Integer.parseInt(a);
                        filenum = Integer.parseInt(a);
                        //filelengths[i] = Long.parseLong(b);
                        filelength =  Long.parseLong(b);
                        //afilenums[i] = filenums[i];
                        afilenum = filenum;
                        step = 0;
                    }catch (Exception e)
                    { //steps[i] = 1;
                       step = 1;filenum = -1; filelength = -1;afilenum=-1;
                    }
                    fileinfos.add(new FileInfo(filenum,filenames[i], filelength, afilenum,step));
                }

            }
            StringBuffer datagrams = new StringBuffer();
            if (fileinfos.size()>1|| filenum > 0 )
                t0 = System.currentTimeMillis();
            for (int K = 0; keep && K < fileinfos.size(); K++)
            {
                FileInfo fileinfo = fileinfos.get(K);
                filenum = fileinfo.filenum;//filenums[K];
                filename = fileinfo.filename;//filenames[K];
                filelength = fileinfo.filelength;//filelengths[K];
                afilenum = fileinfo.afilenum;//afilenums[K];
                if (fileinfo.step == 1)
                {
                    if (K>0) datagrams.append("\n");
                    datagrams.append("Invalid" + filenumstr[K] + filelengthstr[K]);
                    continue;
                }
                if (fd == null && filenum >0  )
                    dlength[filenum-1] = 0;
               /* else if (fileinfo.filelength == 0 && fileinfo.filenum >0 )//(filelengths[K] == 0 && filenums[K] > 0)
                {
                    if (K>0) datagrams.append("\n");
                    datagrams.append("");
                    continue;
                }*/
                Socket socket = null;
                String datagram = "";
                
                InputStream sis = null;
                OutputStream sos = null;
                step = 0;
                FileOutputStream fout = null;
                String errs[] = getString(R.string.neterror).split("\\|");

                try {

                    step = 1;
                    String str = " ";
                    SocketAddress sockaddr = new InetSocketAddress(ip, (AppCompatActivity.clientport));
                    socket = new Socket();
                    socket.setSoTimeout(60000);
                    byte[] sign = new byte[1];
                    if (myid.equals(""))
                    {
                        if (socket != null)
                            myid = socket.getLocalAddress().toString().replaceFirst(":.*", "").replaceFirst("^[^0-9]?[0-9]+\\.[0-9]+\\.", "");
                        else
                        {
                            myid = "" + System.currentTimeMillis()%1000;
                            MainActivity.savep("unip", myid);
                        }
                    }
                     
                    File save2File =  null;
                    long existlength = 0;
                    if (filenum >0)
                    {
                        save2File = tempfile(filename, fd); 
                        if (save2File.exists())
                        existlength = save2File.length();
                       
                    }
                    str = "-" + existlength + ":" + myid + ":" + password + ":" + filenum + ":" + filename + "\n";
                   
                    step = 2;
                    socket.connect(sockaddr);

                    step = 3;
                    byte[] buff = str.getBytes("utf-8");
                    sos = socket.getOutputStream();
                    step = 4;
                    sos.write(buff);
                    step = 5;
                    sos.flush();

                    sis = socket.getInputStream();
                    int k = sis.read(sign);

                    if (k == 1) 
                    {
                        byte filemark = sign[0];
                        if (filemark == EnquireActivity.NOSUCHFILE) 
                        {
                            datagram =  filename + " not exists " + ":" + step;
                        }
                        else if (filemark == EnquireActivity.NOPERMISSION ) 
                        {
                            datagram = " no permission";
                        }
                        else if (filemark == EnquireActivity.ISFILELIST) 
                        {
                            files.clear();
                            BufferedReader r = new BufferedReader(new InputStreamReader(sis));
                            StringBuffer sb = new StringBuffer();
                            String aline = null;
                            
                            datagram = "";
                            while ( (aline = r.readLine())!= null){
                                files.add(aline);
                            }
                            afilenum = 0;
                            flength = new long[files.size()];
                            dlength = new long[files.size()];
                            olddlength = new long[files.size()];
                            step = 7;
                            
                        } 
                        else if (filemark == EnquireActivity.ISDIRECTORY) 
                        {
                            BufferedReader r = new BufferedReader(new InputStreamReader(sis));
                            StringBuffer sb = new StringBuffer();
                            String aline = r.readLine(); 
                            String  tt[] =  split(aline);
                            tt[1] = slash(tt[1]);
                           File ffd = new File(AppCompatActivity.downloadfolder + File.separator + tt[1]);
                           if (ffd.exists() == false)
                               ffd.mkdir();
                            ss = false;
                            long totallength = 0;
                            if (fd == null) setlength(flength,filenum-1,0);
                            while ((aline = r.readLine()) != null) 
                            {
                                String ss[] = split(aline);
                                try{ totallength += Long.parseLong(ss[0]);}catch(Exception e1){}
                                new TCPDownload(this.params[0],this.params[1], tt[0], aline, ss[0],tt[1] ).execute();
                            }
                            datagram = totallength + " " + ffd.getAbsolutePath();
                            setlength(flength,filenum-1,totallength + flength[filenum-1]);
                            step = 9;
                            ss = true;
                        } 
                        else if (filemark == EnquireActivity.ISEMPTYFILE) 
                        {
                            //File f = tempfile(filename,fd);
                            save2File.createNewFile();
                            datagram = "0 " + filelength + " " +  save2File.getAbsolutePath();
                           
                            step = 8;
                        }
                        else if (filemark == EnquireActivity.ISSAMEFILE) 
                        {
                            datagram = filelength + " " + filelength + " " +  save2File.getAbsolutePath();
                            
                            setlength(dlength,filenum-1,dlength[filenum-1] + filelength);
                            step = 8;
                        }
                        else  
                        {
                           // File f = tempfile(filename,fd);
                            long L = 0;
                            //if (f != null)
                            {
                                fout = new FileOutputStream(save2File);
                                int old = 0;
                                byte [] buf = new byte[2048];
                                while (keep && (k = sis.read(buf)) > 0) {
                                    L += k;
                                    fout.write(buf, 0, k);
                                    int percent = (int) (100 * L / filelength);
                                    if (percent > old  ) {
                                        publish(percent);
                                    }
                                    old = percent;
                                }
                            }
                             
                            setlength(dlength,filenum-1,dlength[filenum-1] + L);
                            datagram = L + " " +  filelength + "  " + save2File.getAbsolutePath();
                            step = 8;
                        }
                    }
                    else if (filelength > 0)
                    {
                        datagram =  errs[0] + ":" + step;
                        
                    }


                } catch (java.net.ConnectException w) {
                    datagram = errs[1] + ":" + step;

                } catch (SocketTimeoutException e) {
                    datagram = errs[2] + ":" + step;

                } catch (SocketException e) {
                    //String rip = socket.getRemoteSocketAddress().toString().replaceFirst(":.*", "").replaceFirst("^[^0-9]","");
                    datagram = errs[3] + ":" + step;

                } catch (IOException e) {
                    datagram = errs[1] + ":" + step;
                }
                finally
                {
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
                    if (fout != null) try {
                        fout.close();
                    } catch (Exception e3) {
                    }

                }

                if (K>0) datagrams.append("\n");
                datagrams.append(datagram);
                fileinfos.get(K).step = step;
                fileinfos.get(K).afilenum  = afilenum;
                if (afilenum == 0) break;

            }
            if (t0 > 0)
                t0 = System.currentTimeMillis() - t0;
            return datagrams.toString();

        }
        boolean ss = false;
        String pad(String x)
        {
            for (int i=x.length(); i < 20; i++)
                x += " ";
            return x;
        }
        protected void done( )
        {
            super.done();
            String datagram = "";
            try{ datagram = get();}catch(Exception e){}
           // progress.cancel();

            String [] datagrams = datagram.split("\n");
            long sum = 0;
            for (int K=0; K < datagrams.length; K++)
            {
                datagram = datagrams[K];
                String [] gg = split(datagram);
                FileInfo fileinfo = fileinfos.get(K);
                step = fileinfo.step;// steps[K];
                filenum = fileinfo.filenum;//filenums[K];
                afilenum = fileinfo.afilenum;// afilenums[K];
                filename = fileinfo.filename ;//filenames[K];
                filelength = fileinfo.filelength ;// filelengths[K];

                if (step == 8 || step == 9) 
                {
                    //int k = maxid + COL * (filenum-1) + 2;
                    JLabel t =  (JLabel)ele.get(filenum).get(2);
                    if (step == 8)
                    {
                        String [] gg1 = split(gg[1]);
                        
                        gg[1] = gg1[1];  
                         
                    }
                    t.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    JLabel w =  (JLabel)ele.get(filenum).get(3);
                    if (dlength[filenum-1] < flength[filenum-1]) 
                    {
                       
                        w.setText(shortnum(dlength[filenum-1] ) + " < " + shortnum(flength[filenum-1] ));
                        w.setForeground(ParseLayout.hex2Rgb("#ff0000"));
                    } 
                    else 
                    {
                        w.setText(shortnum(dlength[filenum-1] ));
                        JLabel f = (JLabel)ele.get(filenum).get(1);
                        f.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                        f.addMouseListener(null);
                        w.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                    }
                    if (fd==null) 
                    {
                        downloaded.add(datagram);
                    }
                     
                    long len =  dlength[filenum-1] ;
                    sum += len;
                    if (K == datagrams.length-1 && t0 > 10)
                    {
                        txt.setText( Math.round(sum / t0) + " " + getString(R.string.KB) + "/" + getString(R.string.second));
                    }
                    if (fd == null ) 
                    {
                        if (gg[1] == null || gg[1].equals(""))
                        {
                             
                        }
                        else 
                        {
                            t.setText( gg[1]);
                             
                        }
                    }
                    else
                    {
                       lbl5.setText(gg[1]);
                    }
                } else if (step == 5 && afilenum == 0) {
                    txt.setText(getString(R.string.download0));
                    mytoast(getApplicationContext(), getString(R.string.download0));
                } else if (step == 7) {
                    if (filenum > 0 && afilenum == 0) {
                        mytoast(getApplicationContext(), getString(R.string.nolonger));
                        txt.setText(getString(R.string.nolonger));
                    }
                } else if (filenum > 0) {
                    JLabel t =  (JLabel)ele.get(filenum).get(2);
                   
                }
                if (afilenum == 0)
                {
                    if (step <  7)
                    {
                        txt.setText(datagram);
                    }

                    redo();
                }
            }
        }
    }
    static public String [] split(String filepath)
    {
        String flen="", actualpath = null;
        if (filepath !=null) filepath = filepath.trim();
        int p = -1; 
        if (filepath!=null) 
        {
            actualpath = filepath.replaceFirst("^[0-9]+ ","");
            p = actualpath.length();
            if (p < filepath.length())
                flen =  filepath.substring(0,filepath.length() - p).trim();
            actualpath = actualpath.trim();
        }
         
        return new String[] {flen, actualpath};
    }
    void redo()
    {
        for (int r = 0; r < NR; r++)
        {
            ArrayList<Component> tr = ele.get(NR-r); 
            int K = tr.size();
            for (int c=K-1; c>=0; c--)
            {
                tl.remove(tr.get(c));
                tr.remove(c);
            }
            ele.remove(NR-r);
        }
        NR = 0;
         
        for (int l= 0; l < downloaded.size(); l++)
        {
               if (downloaded.get(l) == null || downloaded.get(l).replaceFirst("^[0-9]+[ ]+", "").equals(""))  
               {
                   downloaded.remove(l);
                   l--;
               }
        }
        if (downloaded!=null && downloaded.size()>0)
            matches = new boolean[ downloaded.size() ];
        for (int i=0; i < files.size(); i++)
        {
            String [] fs = split(files.get(i));
            String filepath = fs[1];
            int l = downloaded.size()-1;
            String path = "";
            for (; l >=0; l--) 
            {
                String [] ds = split(downloaded.get(l));
                if (slash(ds[1]).indexOf(slash(File.separator + filepath)) > 0)
                {
                    matches[l] = true;
                    path = downloaded.get(l);
                    break;
                }
            }
             
            addrow(files.get(i),path);
        }
        if (downloaded!=null && downloaded.size()>0)
        for (int i=0; i < downloaded.size(); i++)
        {
            if (matches[i]) continue;
            String f = downloaded.get(i);
            if (f == null) continue;
            int k = f.lastIndexOf(File.separator);
            String g = f.substring(k+1);
            addrow("0 ", f);
        }
        for (int i=0; i < NR; i++)
        {
            ArrayList<Component> tr =  ele.get(i+1);
            JLabel  y = (JLabel) (tr.get(COL-2));
            if (y.getText().equals(""))
                y.setText(dlength[NR] + "");
        }
         tl.revalidate();//forces panel to lay out components again
         tl.repaint();
    }

}
