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
 

public class SettingActivity extends AppCompatActivity{
    final int WRITE_REQUEST_CODE = 200;
    final int EDIT_QUESTION_CODE = 105;
    final int READ_REQUEST_CODE = 201;
    final int DOWNLOAD_CODE = 202;
    final int EDIT_USER_CODE = 203;
    final int EDIT_SELF_CODE = 204;
    final int ANSWER_QUESTION_CODE = 205;
    private JPanel tl;
    private String[] lblstr = R.string.setttinglabels.split(",");
    private JLabel lbls[] = new JLabel[lblstr.length];
     
    private JTextField txts[];
    //static int PORT = MainActivity.PORT;
    
    private JComboBox spinner;
     
   File directory = new File(System.getProperty("user.home") + File.separator + "Downloads");
     

     
     int NR = 0;
     String which = null;
     void makeElements()
   {
      lblstr = R.string.setttinglabels.split(",");
      NR = lblstr.length;
      lbls  = new JLabel[NR];
      txts  = new JTextField[NR];
       
      
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
      c.insets = new Insets(3,3,3,3);
      c.weighty = 0.0;
      int n = 0;
      for (int i=0; i < NR; i++)
      {
         lbls[i] = new JLabel(lblstr[i]);
         lbls[i].setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
         lbls[i].setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
         c.weightx = 0;
         c.gridx = 0;
         c.gridy = n;
       
         n++;
        // if (which.equals("enq") || which.equals("res") && i!=7)
             ll2.add(lbls[i],c);
         
         c.weightx = 1;
         if (i!=0)
         {
         txts[i] = new JTextField();
         txts[i].setBackground(ParseLayout.hex2Rgb("#ffffff"));
         txts[i].setForeground(ParseLayout.hex2Rgb("#000000"));
         components.put(i +100, txts[i]);
         c.gridx = 1;
        
         //if (which.equals("enq")   || which.equals("res") && i!=7)
              ll2.add(txts[i],c);
        
         }
         
       }
       spinner = new JComboBox();
       spinner.setBackground(ParseLayout.hex2Rgb("#ffffff"));
       spinner.setForeground(ParseLayout.hex2Rgb("#000000"));
       components.put( 100, spinner);
       c.gridy = 0;
       ll2.add(spinner,c); 
      
      
   }

   public SettingActivity()
   {
      super();
      try{buildToolbar("self.xml");}catch(Exception e){}
      
   }
    static public void main(String [] args)
   {
       (new SettingActivity()).onCreate();
   }
   boolean refresh = false;
   void save()
   {
       refresh = false;
       String x = (String)spinner.getSelectedItem();
       if (!x.equals(AppCompatActivity.language))
       {
            ParseLayout.sourcefolder = "res" + File.separator + "values-" + x;
            ParseLayout.destinyfolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\";
            ParseLayout p = new ParseLayout("strings.xml");
            p.proc();
            AppCompatActivity.language = x;
            savep("language", language);
            refresh = true;
       }
       if (!AppCompatActivity.background.equals(txts[1].getText()))
       {
           AppCompatActivity.background = txts[1].getText();
           refresh = true;
       }
       if (!AppCompatActivity.foreground.equals(txts[2].getText()))
       {
           AppCompatActivity.foreground = txts[2].getText();
           refresh = true;
       }
       try{
       if (AppCompatActivity.fontsize != Integer.parseInt( txts[3].getText())) 
       {
           AppCompatActivity.fontsize = Integer.parseInt( txts[3].getText());
           refresh = true;
       }}catch(Exception e){}
       
       if (AppCompatActivity.datafolder.equals(txts[4].getText()) )
       {
            DBHelper.movefile(txts[4].getText());
       }
      
       AppCompatActivity.uploadfolder = txts[5].getText();
       AppCompatActivity.downloadfolder = txts[6].getText();
       StringBuffer ss = new StringBuffer();
       AppCompatActivity.servefolder = txts[7].getText();
       try{
          if (which.equals("enq"))
             AppCompatActivity.serverport = Integer.parseInt(txts[8].getText());
          else
             AppCompatActivity.clientport = Integer.parseInt (txts[8].getText());
       }catch(Exception e){}
       ss.append(AppCompatActivity.background + "\n");
       ss.append(AppCompatActivity.foreground + "\n");
       ss.append(AppCompatActivity.fontsize + "\n");
       ss.append(AppCompatActivity.datafolder + "\n");
       ss.append(AppCompatActivity.uploadfolder + "\n");
       ss.append(AppCompatActivity.downloadfolder + "\n");
       ss.append(AppCompatActivity.servefolder + "\n");
       ss.append(AppCompatActivity.serverport + "\n");
       ss.append(AppCompatActivity.clientport );
       savep("setting", ss.toString());
       AppCompatActivity.folderok();
       intent.putIntExtra("refresh", refresh?1:0);
       
       setResult(RESULT_OK, intent);
      
   }
     
 

    
    protected void onCreate() 
    {
        which = getIntent().getStringExtra("which");
        if (which == null) which = "enq";
        makeElements();
        pack();
        setPreferredSize(new Dimension(windwidth, windheight));
        File fs = new File("res");
        String s = "";
        File [] fds = fs.listFiles();
        int k = 0, j=0;
        for (int i=0; i < fds.length; i++)
        {
            if (fds[i].getName().indexOf("values-")==0)
            {
                String x = fds[i].getName().replaceFirst("values\\-", "");
                spinner.addItem(x);
                if (x.equals(AppCompatActivity.language))
                    k = j;
                j++;
            }
               
        }
        spinner.setSelectedIndex(k);
         
       txts[1].setText(AppCompatActivity.background );
       txts[2].setText(AppCompatActivity.foreground);
       txts[3].setText(""+AppCompatActivity.fontsize);
       txts[4].setText(AppCompatActivity.datafolder);
       txts[5].setText(AppCompatActivity.uploadfolder);
       txts[6].setText(AppCompatActivity.downloadfolder);
       txts[7].setText(AppCompatActivity.servefolder);
       if (which.equals("enq"))
         txts[8].setText(""+AppCompatActivity.serverport);
       else
         txts[8].setText(""+AppCompatActivity.clientport);
       setTitle(R.string.setting);
    }

    
    public void onOptionsItemSelected(int item)
    {
        super.onOptionsItemSelected(item);
        int j = item ;
        if (j == R.id.cancel)
        {
            setResult(RESULT_OK+1, intent);
        
            finish();
        }
        else if (j == R.id.save)
        {
            save();
            finish();
        }
         
    }
     
     
}

   
 
