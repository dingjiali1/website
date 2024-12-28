package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class UserActivity extends AppCompatActivity
{
   static String [] split(String x)
    {
        if (x.equals(""))
            return new String[]{"","", "1"};
        int j = x.indexOf(" "); String position="0",pin="",name;
        int k = x.indexOf(" ", j+1);
        if (j >= 0)
        {
            pin = x.substring(0,j);
         }
        if (k > j+1)
        {
            position = x.substring(j+1,k);
        }
        name = x.substring(k+1);
        return new String[]{name, pin, position};

    }
   JPanel ll0;
   void makeElements()
   {
      Border border1,margin1;
       GridBagConstraints c;
      JPanel ll1 = new JPanel(new GridBagLayout());
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTH;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 1.0;
      contentpane.add(ll1,c);
      ll0 = new JPanel(new GridBagLayout());
       
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTH;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0;
      c.insets = new Insets(2,2,2,2);
      ll1.add(ll0,c);
      
      JPanel ll2 = new JPanel(new GridBagLayout());
      components.put(R.id.maintbl,ll2);
      c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTH;
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 0;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0;
      c.insets = new Insets(2,2,2,2);
      ll1.add(ll2,c);
      
      
      
      
      ll2.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      JLabel lbl3 = new JLabel( "<html>" +R.string.userinstr.replaceAll("\n","<br>") + "</html>");
      components.put(R.id.userins,lbl3);
      lbl3.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      //lbl3.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      lbl3.setPreferredSize(new Dimension(500,120));
      lbl3.setOpaque(true);
       c = new GridBagConstraints();
      c.anchor = GridBagConstraints.NORTH;
      c.fill = GridBagConstraints.BOTH;
      c.weightx = 0.9;
      c.gridx = 0;
      c.gridy = 2;
      c.ipadx =c.ipady = 2;
      c.weighty = 0.1;
      c.insets = new Insets(2,3,2,3);
      //lbl3.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      lbl3.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl3,c);
      
   }

   public UserActivity()
   {
      super();
      try{buildToolbar("user.xml");}catch(Exception e){}
      makeElements();
       pack();
   }
    static public void main(String [] args)
   {
      new UserActivity();
    }
    

    final int PASTE_QUESTION_CODE = 101;
    private final String CHECK = "" + ((char) 10003);
    private final String UNCHECK = "" + ((char) 10007);
    //ArrayList<String> users = new ArrayList<>();
    JPanel  tl;
    int COL = 4;
    int NR = 0;
    int maxid = R.id.maintbl + 1;
    JTextField u = null;
    JLabel instr;
    @Override
    protected void onCreate( ) {
         
        String users = getIntent().getStringExtra("user");
        tl = (JPanel)  findViewById(R.id.maintbl);

        String aline[] = users.split("\n");
        maxid = R.id.maintbl + 1;
        makedefault();
        makehead(getString(R.string.namepin));

        for (int i=0; i < aline.length; i++)
        {
           addrow(aline[i]);
        }
        instr = (JLabel)findViewById(R.id.userins);
        instr.setText(instr.getText().toString().replaceAll("([0-9])", "\n$1"));
        setTitle(getString(R.string.allresponses));
    }

    ArrayList<JComponent []> ele = new ArrayList<>();
    
     
    public void onOptionsItemSelected(int item)
    {
        super.onOptionsItemSelected(item);
        int j = item;
        if (j == R.id.cancel)
        {
            finish();
        }
        else
        if (j == R.id.save)
        {
            JComponent[] row =  ele.get(0);
            JComboBox m = (JComboBox) row[1];
            EnquireActivity.defaultpermit = m.getSelectedIndex();
            
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < NR; i++)
            {
                row = ele.get(i+2);
                JTextField n = (JTextField) row[0];
                JTextField p = (JTextField) row[1];
                 m = (JComboBox) row[2];
                JLabel k = (JLabel) row[3];
                if (!k.getText().toString().equals(UNCHECK) && !n.getText().toString().equals(""))
                {
                    if (s.length() >0) s.append("\n");
                    s.append( '"'+n.getText() +"\",\"" + p.getText() + "\"," + m.getSelectedIndex());
                }
            }
            Intent intent = new Intent();
            intent.putStringExtra("user",s.toString());
            setResult(RESULT_OK, intent);

            finish();
        }
        else  if (j == R.id.addq)
        {
            addrow(",12345678," + EnquireActivity.defaultpermit + "");
        }
        else if (j ==  R.id.paste) {
            Intent intent = new Intent(this, ParseActivity.class);
            intent.putExtra("rawtxtr", rawtext);

            this.startActivityForResult(intent, PASTE_QUESTION_CODE);
        }
        
    }
    String rawtext = "";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PASTE_QUESTION_CODE)
        {
            if (data!=null) {
                rawtext = data.getStringExtra("question");
                if (rawtext!=null) rawtext.trim();
            }
            if (rawtext == null) rawtext = "";
            if (rawtext.equals("")) return;
            if (resultCode == RESULT_OK)
            {
                String [] x =  rawtext.split("\n");

                for (int i=0; i < x.length ; i++)
                {
                    if (x[i] == null) continue;
                    x[i] = x[i].trim();
                    if (x[i].equals("")) continue;
                    x[i] = x[i].replaceFirst("[ ]+", ",");
                    if(x[i].indexOf(",") < 0) return;
                     
                    addrow(x[i]);
                }

            }
        }
    }

    private void makedefault() {

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,0,0,0);
        c.ipadx = 1;
        c.ipady = 2;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
       // tr.setBackground(Color.WHITE);
         
        int width = getWidth()   ;
        JLabel btn = null;
        JComponent row[] = new JComponent[2];
        for (int j = 0; j < 2; j++) 
        {
            c.gridx =  j;
            c.ipady = 2;
            if ( j==0  )
            {
                
                JLabel textviewj = new JLabel();
                row[0] = textviewj;
                textviewj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                textviewj.setOpaque(true); 
                 
                 
                c.insets = new Insets(1, 1, 1, 1);
                if (j==2) {
                    textviewj.setText(getString(R.string.allresponses)  );
                    textviewj.setAlignmentX(LEFT_ALIGNMENT);;
                }
                else
                {
                    textviewj.setText(getString(R.string.defaultpermit) + ":");
                    textviewj.setAlignmentX(LEFT_ALIGNMENT);;
                }
                ll0.add(textviewj,c);
            }else if (j==1)
            {
                final JComboBox spinnerj = new JComboBox ();
                row[1] = spinnerj;
                spinnerj.setBackground(Color.WHITE);
                spinnerj.setOpaque(true);
                String [] xs = getString(R.string.option).split("/");
                c.insets = new Insets(0,0,0,0); 
                for (int i=0; i < xs.length; i++)
                    spinnerj.addItem(xs[i]);
                spinnerj.setAlignmentX(LEFT_ALIGNMENT);;
                
                spinnerj.setSelectedIndex(EnquireActivity.defaultpermit);
                ll0.add(spinnerj,c);

            }

        }
        ele.add(row);
    }



    private void makehead(final String userline) {
        String [] xs = split(userline);
        String name = xs[0];
        String pin= xs[1];
        String position = xs[2];
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,0,0,0);
        c.ipadx = 1;
        c.ipady = 0;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL;
         
        int width =  getWidth() - 60 ;
        JLabel btn = null;
        JComponent [] row = new JComponent[4];
        for (int j = 0; j < COL; j++) 
        {
            c.insets = new Insets(1, 1, 1, (j==0?1:0));
            c.gridx = j;
            c.gridy = 0;
            if ( j==3)
            {
                c.weightx = 0.10;
                c.ipady = 4;        
                final JLabel textviewj = new JLabel(CHECK,SwingConstants.CENTER);
                row[j] = textviewj;
                textviewj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize)); 
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                 
                textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                textviewj.setOpaque(true);
                textviewj.setText(CHECK);
                textviewj.setAlignmentX(CENTER_ALIGNMENT);
                tl.add(textviewj,c);

                textviewj.addMouseListener(new MouseListener() {
                   
                    public void mousePressed(MouseEvent e){}                                    
                    public void mouseReleased(MouseEvent e){}                                    
                    public void mouseEntered(MouseEvent e){}                                    
                    public void mouseExited(MouseEvent e){}                                    
                    public void mouseClicked(MouseEvent e) {
                        JLabel  u = (JLabel)(e.getSource());
                        if (u.getText().toString().equals(CHECK))
                        {
                            u.setText(UNCHECK);
                        }
                        else
                        {
                            u.setText(CHECK);
                        }
                        String m = u.getText().toString();
                         
                        for (int i=0; i < NR; i++)
                        {
                            JLabel t = (JLabel) (ele.get(i+2))[3];
                            t.setText(m);
                        }

                    }
                });

            } else if (j==1 || j==0)
            {
                c.weightx = 0.30;
                c.ipady = 4;
                JLabel textviewj =  new JLabel("",SwingConstants.LEFT);
                row[j] = textviewj;
                textviewj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize)); 
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                
                textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                textviewj.setOpaque(true); 
                if (j==0)
                      textviewj.setText(name);
                else if (j==1)
                    textviewj.setText(pin);
                else
                    textviewj.setText(position);
                textviewj.setAlignmentX(LEFT_ALIGNMENT);;

                 
                tl.add(textviewj,c);
            }else if (j==2)
            {
                c.weightx = 0.30;
                c.ipady = 0;
                c.insets = new Insets(0,0,0,0);
                JComboBox spinnerj = new JComboBox ( );
                row[j] = spinnerj;
                spinnerj.setBackground(Color.WHITE);
                spinnerj.setOpaque(true);
                String [] xss = getString(R.string.option).split("/") ;
                //spinnerj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize-2));  
                for (int i=0; i < xss.length; i++)
                    spinnerj.addItem(xss[i]);
                spinnerj.setSelectedIndex(EnquireActivity.defaultpermit);
                tl.add(spinnerj,c);
              //  View v = spinnerj.getSelectedView();((JLabel)v).setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));(16);
                spinnerj.addActionListener (new ActionListener () 
        {
           public void actionPerformed(ActionEvent e) 
           {    
                        JComboBox u = (JComboBox)e.getSource();
                        int position = u.getSelectedIndex();
                        EnquireActivity.defaultpermit = u.getSelectedIndex();
                        for (int i=0; i < NR; i++)
                        {
                            JComboBox s = (JComboBox)(ele.get(i+2))[2];
                            s.setSelectedIndex(position);
                        }
                        //spinnerj.setSelectedIndex(5);

                    }
 

                });
            }

        }
        ele.add(row);
    }

   

    private void addrow(final String userline) {
        String [] xs = (new CSVParse(userline,'"',new String[]{","})).nextRow();
        String name = xs[0];
        String pin= xs[1];
        String position = xs[2];
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(0,0,0,0);
        c.ipadx = 3;
        c.ipady = 3;
        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.HORIZONTAL; 
        
        int width =  getWidth() - 60;
        JLabel btn = null;
        JComponent row [] = new JComponent[COL];
        for (int j = 0; j < COL; j++) 
        {
            c.insets = new Insets(0,1,0,(j==0)?1:0);
            c.gridx = j;
            c.gridy = NR + 1;
            if ( j==3)
            {
                c.ipady =4;
                JLabel textviewj = new JLabel("",SwingConstants.CENTER);
                row[j] = textviewj;
                textviewj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize)); 
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                textviewj.setBackground(Color.WHITE);
                textviewj.setOpaque(true); 
                    textviewj.setText(CHECK);
                    textviewj.setAlignmentX(CENTER_ALIGNMENT);
 
                tl.add(textviewj,c);

                    textviewj.addMouseListener(new MouseListener() {
                   
                    public void mousePressed(MouseEvent e){}                                    
                    public void mouseReleased(MouseEvent e){}                                    
                    public void mouseEntered(MouseEvent e){}                                    
                    public void mouseExited(MouseEvent e){}                                    
                    public void mouseClicked(MouseEvent e) {
                            JLabel  u = (JLabel)(e.getSource());

                            if (u.getText().toString().equals(CHECK))
                            {
                                u.setText(UNCHECK);

                            }
                            else
                            {
                                u.setText(CHECK);

                            }
                        }
                    });

            } else if (j==0 || j==1)
            {
                //c.insets = new Insets(0,0,0,0);
                c.ipady = c.ipadx = 4;
                JTextField edittextj = new JTextField();
                row[j] = edittextj;
                edittextj.setForeground(Color.BLACK);
               
                edittextj.setFont(new Font("Times",Font.BOLD,AppCompatActivity.fontsize));;
                edittextj.setBorder(javax.swing.BorderFactory.createEmptyBorder()); 
                edittextj.setBackground(Color.WHITE);
                edittextj.setOpaque(true);
                if (j==0) edittextj.setText(name);
                else edittextj.setText(pin);
                tl.add(edittextj,c);
             } else
            {
                JComboBox spinnerj = new JComboBox ( );
                row[j] = spinnerj; 
                //c.insets = new Insets(0,0,0,0); 
                c.ipady = c.ipadx = 0;
                spinnerj.setBackground(Color.WHITE);
                spinnerj.setOpaque(true);
                xs = getString(R.string.option).split("/");
                spinnerj.setBorder(javax.swing.BorderFactory.createEmptyBorder());
               for (int i=0; i < xs.length; i++)
                spinnerj.addItem(xs[i]);
                spinnerj.setSelectedIndex(Integer.parseInt(position));
                tl.add(spinnerj,c);
            }

        }
        NR++;
        ele.add(row);
        tl.revalidate();//forces panel to lay out components again
        tl.repaint();
    }
}

