package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
public class SelfActivity extends AppCompatActivity
{
   
   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
       
      JPanel ll1 = new JPanel(new GridBagLayout());
      ll1.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.insets = new Insets(1,1,1,1);
      contentpane.add(ll1,c);
      
      
      JPanel ll2 = new JPanel(new GridBagLayout());
      ll2.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      components.put(R.id.maintbl,ll2);
      c = new GridBagConstraints();
      c.insets = new Insets(1,1,1,2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.gridwidth = 2;
      c.weighty = 0.99;
      ll1.add(ll2,c);
      
      
      JLabel lbl5 = new JLabel( "  #");
      lbl5.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl5.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl5.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.insets = new Insets( 1, 1, 1, 1);
      c.weightx = 0.0;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0;
      lbl5.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll2.add(lbl5,c);
      
      JLabel lbl6 = new JLabel(R.string.question);
      lbl6.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl6.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl6.setOpaque(true);
       c = new GridBagConstraints();
       c.insets = new Insets( 1, 1, 1, 0);
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 1;
      c.gridy = 0;
      c.weighty = 0.4;
      lbl6.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll2.add(lbl6,c);
      
      JLabel lbl7 = new JLabel(R.string.answer);
      lbl7.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl7.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl7.setOpaque(true);
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.insets = new Insets( 1, 1, 1, 0);
      c.weightx = 0.0;
      c.gridx = 2;
      c.gridy = 0;
      c.weighty = 0.4;
      lbl7.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll2.add(lbl7,c);
      
      
      
      
      
      
      JLabel lbl3 = new JLabel( "<html>" +getString(R.string.uid).replaceAll("\n","<br>") + "</html>");
      components.put(R.id.txtuid,lbl3);
      lbl3.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl3.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0;
      lbl3.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl3,c);
      
      JTextField lbl4 = new JTextField(" ");
      components.put(R.id.user,lbl4);
      lbl4.setForeground(ParseLayout.hex2Rgb("#000000"));
      lbl4.setBackground(ParseLayout.hex2Rgb("#ffffff"));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.0;
      c.gridx = 1;
      c.gridy = 0;
      c.weighty = 0;
      lbl4.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl4,c);
      
   }
   
   final int PASTE_QUESTION_CODE = 101;
    private final String CHECK = "" + ((char) 10003);
    private final String UNCHECK = "" + ((char) 10007);
    ArrayList<String> questions, answers = new ArrayList<>();
    JPanel  tl;
    JTextField user;
    JLabel  txtuid;
    int COL = 3;
    boolean forvote = true;
    @Override
    protected void onCreate() {
        super.onCreate();
      
        questions = getIntent().getStringArrayListExtra("question");
        answers = getIntent().getStringArrayListExtra("myvotes");
        tl = (JPanel) findViewById(R.id.maintbl); 
         user = (JTextField) findViewById(R.id.user);
        txtuid = (JLabel) findViewById(R.id.txtuid);
        user = (JTextField) findViewById(R.id.user);
        if (answers == null)
        {
            forvote = false;
            answers = getIntent().getStringArrayListExtra("answer");
            user.setVisible(false);
            txtuid.setVisible(false);
        }
        
        user.setText(getIntent().getStringExtra("myid"));
        if(questions == null){ questions = new ArrayList<>();
        questions.add("A");questions.add("B");questions.add("C");questions.add("D");questions.add("E");}
         
        for (int i=0; i < questions.size(); i++)
        {
            addrow(questions.get(i), (i < answers.size()?answers.get(i):"") );
            answers.add("");
        }
        setTitle(getString(R.string.question));
        setPreferredSize(new Dimension(500, questions.size()*26 + 90));
    }

     
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
          //  updatetxt();
            answers.clear();
            for (int i = 0; i < NR; i++)
            {
                //int k = maxid + 1 + COL * i;
                JTextField t = (JTextField) ele.get(i).get(2);
                if (i < answers.size())
                    answers.set(i, t.getText().trim());
                else
                    answers.add(t.getText().trim());
            }
             
            
            Intent intent = new Intent();
            intent.putStringExtra("user",user.getText().trim());
            intent.putStringArrayListExtra("answers",answers);
            setResult(RESULT_OK, intent);
            finish();
        }
         
        
    }
    
    int NR = 0;
   // int maxid = R.id.maintbl + 1;
    JTextField u = null;
    ArrayList<ArrayList<Component>> ele = new ArrayList();
    int getR(Component x)
    {
       int r = 0;
       int R = ele.size();
       for (int j=0; j < R; j++)
       {
           ArrayList<Component> tr = ele.get(j);
           int N = tr.size();
           for (int i=0; i < N; i++)
              if (x == tr.get(i))
                  return j;
       }
       return -1;
    }
     
    private void addrow(final String question, String ans) {
        ArrayList<Component> tr = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5; 
        c.ipady = 5;
        c.weighty = 0.0;
        c.gridy = NR+1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        JLabel btn = null;
        for (int j = 0; j < COL; j++) 
        {
            c.gridx = j;
            c.insets = new Insets( 0, 1, 1, j==0?1:0);
            c.weightx = j;
            if (j == 0 || j==1)
            {
                JLabel textviewj;
                if (j==0)
                    textviewj = new JLabel("" + (NR + 1),SwingConstants.RIGHT);
                else
                    textviewj = new JLabel(question,SwingConstants.LEFT);
                textviewj.setPreferredSize(new Dimension(30 + j*(getWidth() - 50 )/2,25));
                textviewj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                 textviewj.setOpaque(true);
                 tr.add(textviewj);
                tl.add(textviewj,c);
                
            } else
                {
                
                c.fill =  GridBagConstraints.BOTH;
                int width = (getWidth() - 50 )/2;
                JTextField edittextj = new JTextField(ans);
                edittextj.setForeground(Color.BLACK);
                edittextj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                edittextj.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                edittextj.setBackground(ParseLayout.hex2Rgb("#ffffff"));
                 
                edittextj.setOpaque(true);
                
                tr.add(edittextj);
                tl.add(edittextj,c);
            }
            
        }
        ele.add(tr);
        NR++;
         tl.revalidate();//forces panel to lay out components again
            tl.repaint();
    }
 


   public SelfActivity()
   {
      super();
      try{buildToolbar("self.xml");}catch(Exception e){}
      makeElements();
      pack();
   }
    static public void main(String [] args)
   {
      SelfActivity q = new SelfActivity();
      q.onCreate();
    }
   }
