package response;



import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
//import static response.EnquireActivity.missed;
public class BrowseActivity extends AppCompatActivity
{
    Responsetbl resdb = new Responsetbl(null);
    ArrayList<String> sessions = new ArrayList();
    HashMap<String, ArrayList<Response>> s2t = new HashMap<>();
    private String which = "Enquirer";
    JComboBox<String> spinner  ;
    JPanel times;
    @Override
    protected void onCreate( ) 
    {
        super.onCreate( );
        times = (JPanel)findViewById(R.id.times);
        String which0 = getIntent().getStringExtra("which");
        if (which0 != null) which = which0;
        resdb.resindex(which,sessions, s2t);
        setTitle(getString(R.string.record));
        //spinner = (JComboBox<String>) findViewById( R.id.session );
        String session0 = getIntent().getStringExtra("session"); 
        int J = -1;
        for (int j=0; j < sessions.size(); j++)
        {
            spinner.addItem(sessions.get(j));
            if (session0!=null && session0.equals(sessions.get(j)))
                J = j;
        }
        if (J >=0)
        {
            spinner.setSelectedIndex(J);
            popultelist( session0);
        }
        else
        {
            spinner.addItem("SELECT....");
            spinner.setSelectedIndex(sessions.size());
        }
        spinner.addActionListener (new ActionListener () 
        {
           public void actionPerformed(ActionEvent e) 
           {
             int position = ((JComboBox)(e.getSource())).getSelectedIndex();
             String x = null;
             if (position < sessions.size()) 
                 x = sessions.get(position);
             if (x!=null) popultelist( x);
           }
       });     
         
    }

     

    public void onOptionsItemSelected(int item) 
    {
        Intent intent;
        switch(item ) {
            case R.id.prev:
            finish();
            break;
            default:
                super.onOptionsItemSelected(item);
        }
 
    }


    HashMap<String,Integer> str2m = new HashMap<>();
    public void  popultelist(final String x)
    {
        str2m.clear();
        JPanel pan = (JPanel) findViewById(R.id.times);
        pan.removeAll();
        pan.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
        ArrayList<Response> y = s2t.get(x);
        int m = 0;
        int COL = 3;
        for (Response z:y)
        {
            str2m.put(Responsetbl.dstr(new Date(z.timemoment)),z.num);
        }
        double N = Math.ceil(y.size()/((double)COL))*COL; 
        for (; m < N; m++)
        {
            Response z = null; 
            if (m < y.size())  z = y.get(m);
            JLabel lbl = new JLabel( z!=null?Responsetbl.dstr(new Date(z.timemoment)):"        ");
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.NORTH;
            c.fill = GridBagConstraints.BOTH;
            c.gridx = m%COL;
            c.gridy = m/COL;
            c.weightx =1.0/COL;
            c.insets = new Insets(3,3,3,3);// m<COL?1:0, 1,1,m%COL==0?1:0);
            lbl.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
            lbl.setForeground(ParseLayout.hex2Rgb("#0000ff"));
            lbl.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
           // lbl.setOpaque(true);
            if (z!=null)
            lbl.addMouseListener( new MouseListener(){
            @Override
            public void mousePressed(MouseEvent e){}                        
            public void mouseReleased(MouseEvent e){}                        
            public void mouseEntered(MouseEvent e){}                        
            public void mouseExited(MouseEvent e){}                        
            public void mouseClicked(MouseEvent e) {
                    JLabel  tv = (JLabel)e.getSource();
                    int mm = str2m.get(tv.getText()).intValue();
                    Intent i = null;
                    if (which.equals("Enquirer"))
                        i = new Intent(BrowseActivity.this, EnquireActivity.class);
                    else
                        i = new Intent(BrowseActivity.this, MainActivity.class);
                    i.putIntExtra("num", mm);
                    startActivity(i);
            
            }});
            times.add(lbl,c);
          
        }
        times.revalidate();//forces panel to lay out components again
        times.repaint();
    }

    

   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
       
      JPanel ll2 = new JPanel(new GridBagLayout());
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.insets = new Insets(2,2,2,2);
      contentpane.add(ll2,c);
      
      
      JLabel lbl3 = new JLabel( "<html>" +R.string.Session.replaceAll("\n","<br>") + "</html>");
      lbl3.setForeground(ParseLayout.hex2Rgb("#FF7722"));
      lbl3.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl3.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
       c = new GridBagConstraints();
       c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0;
      ll2.add(lbl3,c);
      
      spinner = new JComboBox<>();
      spinner.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      spinner.setForeground(ParseLayout.hex2Rgb("#000000"));
      spinner.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.5;
      c.gridx = 1;
      c.gridy = 0;
      c.weighty = 0;
      ll2.add(spinner,c);
      
      JPanel ll4 = new JPanel(new GridBagLayout());
      ll4.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      components.put(R.id.times, ll4);
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTHWEST;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0.9;
      c.gridx = 0;
      c.gridy = 1;
      c.gridwidth = 2;
      c.weighty = 0.5;
      ll2.add(ll4,c);
      
   }

   public BrowseActivity()
   {
      super();
      try{buildToolbar("browse.xml");}catch(Exception e){}
      makeElements();
      pack();
   }
    static public void main(String [] args)
   {
      new BrowseActivity();
    }
   }
