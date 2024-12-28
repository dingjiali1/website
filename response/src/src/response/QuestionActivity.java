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
public class QuestionActivity extends AppCompatActivity
{
   
   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
       
      JPanel ll1 = new JPanel(new GridBagLayout());
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.5;
      c.insets = new Insets(1,1,1,1);
      contentpane.add(ll1,c);
      ll1.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      JPanel ll2 = new JPanel(new GridBagLayout());
      ll2.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      components.put(R.id.maintbl,ll2);
      c = new GridBagConstraints();
      c.insets = new Insets(1,1,1,2);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
       
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.99;
      ll1.add(ll2,c);
      
      JLabel lbl3 = new JLabel( "<html><table><tr><td>" +R.string.question2.replaceAll("\n","<br>") + "</td></tr></table></html>");
      components.put(R.id.instr,lbl3);
      lbl3.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl3.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
       c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.9;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0;
      lbl3.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl3,c);
      
   }
   
   final int PASTE_QUESTION_CODE = 101;
    private final String CHECK = "" + ((char) 10003);
    private final String UNCHECK = "" + ((char) 10007);
    ArrayList<String> questions, oldquestions = new ArrayList<>();
    JPanel  tl;
    JLabel instr;
    int COL = 2;
    JButton answer,paste;
    @Override
    protected void onCreate() {
        super.onCreate();
      
        questions = getIntent().getStringArrayListExtra("question");
        tl = (JPanel) findViewById(R.id.maintbl); 
        if(questions == null){ questions = new ArrayList<>();
        questions.add("A");questions.add("B");questions.add("C");questions.add("D");questions.add("E");}
        String x = getIntent().getStringExtra("enquirer");
        if (x!=null)
        {
            COL = 3;
            //activemenu(R.id.answer, false);
            activemenu(R.id.paste, false);
        }
        else
        {
            instr = (JLabel) findViewById(R.id.instr);
            instr.setVisible(false);
            //activemenu(R.id.answer, false);
            activemenu(R.id.paste, false);
        }
        for (int i=0; i < questions.size(); i++)
        {
            addrow(questions.get(i));
            oldquestions.add(questions.get(i));
        }
        setTitle(getString(R.string.question));
        
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
            questions.clear();
            for (int i = 0; i < NR; i++)
            {
                //int k = maxid + 1 + COL * i;
                JTextField t = (JTextField) ele.get(i).get(1);
                if (i < questions.size())
                    questions.set(i, t.getText());
                else
                    questions.add(t.getText());
            }
            while (questions.size()>0)
            if (questions.get(questions.size()-1).equals(""))
                questions.remove(questions.size()-1);
            else 
                break;

            ArrayList<Integer> dels = new ArrayList<>();
            if (COL==3)
            {
                boolean a = true;
                for (int i = NR - 1; i >= 0 && NR-dels.size()>5; i--)
                {
                    //int k = maxid + 2 + COL * i;
                    JTextField w = (JTextField) ele.get(i).get(1);
                    JLabel t = (JLabel) ele.get(i).get(2);
                    boolean b = w.getText().equals("");
                    if (t.getText().equals(UNCHECK)|| a && b)
                        dels.add(i);
                    if (!b) a = false;
                }

            }
            Intent intent = new Intent();
            if (COL==3 && dels.size()>0)
            {
                intent.putIntegerArrayListExtra("dels",dels);

            }
            intent.putStringArrayListExtra("question",questions);
            setResult(RESULT_OK, intent);
            finish();
        }
        else  if (j == R.id.addq)
        {
            addrow("");
        }
        else if (j ==  R.id.paste) {
            Intent intent = new Intent(this, ParseActivity.class);
            intent.putStringExtra("question", rawtext);
            intent.putStringArrayListExtra("questions",questions);
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
                String [] x =  rawtext.split("\n[ ]*\n");
                if (x.length == 1)
                {
                    x = rawtext.split("\n");
                }

                    for (int i=0; i < questions.size() && i < x.length; i++)
                    {
                        if (x[i] == null) continue;
                        if (!(questions.get(i)==null || questions.get(i).equals(""))) continue;
                        x[i] = x[i].trim();
                        String y[] = x[i].split("[\\.| ]+");
                        int n;
                        try {
                            n = Integer.parseInt(y[0]);
                            int k = 1 + y[0].length();
                            if (k < x[i].length()) {
                                x[i] = x[i].substring(k);
                                if (n > 0 ) {
                                    questions.set(n - 1, x[i]);
                                    JTextField e = (JTextField) ele.get(i).get(1);
                                    e.setText(x[i]);
                                }
                            }
                        } catch (Exception e1) {

                            questions.set(i, x[i]);
                            JTextField e = (JTextField)ele.get(i).get(1);
                            e.setText(x[i]);
                        }
                    }

                    for (int i=questions.size(); i < x.length; i++)
                    {
                        questions.add(x[i]);
                        addrow(x[i]);
                    }

            }
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
    void updatetxt()
    {
        if (u == null) return;
        int r = getR(u);
        if (r < questions.size())
            questions.set(r, u.getText());
        else
            questions.add( u.getText());
    }
    private void addrow(final String question) {
        ArrayList<Component> tr = new ArrayList<>();
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5; 
        c.ipady = 5;
        c.weighty = 0.0;
        c.gridy = NR;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        JLabel btn = null;
        for (int j = 0; j < COL; j++) 
        {
            c.gridx = j;
            c.insets = new Insets( NR==0?1:0, 1, 1, j==0?1:0);
            c.weightx = 0;
            if (j == 0 || j==2)
            {
                JLabel textviewj;
                if (j==0)
                    textviewj = new JLabel("" + (NR + 1),SwingConstants.RIGHT);
                else
                    textviewj = new JLabel(CHECK,SwingConstants.CENTER);
                textviewj.setPreferredSize(new Dimension(30,25));
                textviewj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                textviewj.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
                textviewj.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
                 textviewj.setOpaque(true);
                 tr.add(textviewj);
                tl.add(textviewj,c);
                if (j==2)
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
                        }
                    });

            } else
                {
                c.weightx = 1;
                c.fill =  GridBagConstraints.BOTH;
                int width = getWidth() - 50*(COL-1);
                JTextField edittextj = new JTextField();
                edittextj.setForeground(Color.BLACK);
                edittextj.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
                edittextj.setBorder(javax.swing.BorderFactory.createEmptyBorder());
                edittextj.setBackground(ParseLayout.hex2Rgb("#ffffff"));
                if (question != null) 
                    edittextj.setText(question);
                edittextj.setOpaque(true);
                edittextj.addFocusListener(  new FocusListener() {
 
                    public void focusGained(FocusEvent e) {
                        u = (JTextField)(e.getSource());
                        updatetxt();
                    }
                    public void focusLost(FocusEvent e) { 
                         u = (JTextField)(e.getSource());
                        updatetxt();
                    }
                });
                    
                tr.add(edittextj);
                tl.add(edittextj,c);
            }
            
        }
        ele.add(tr);
        NR++;
         tl.revalidate();//forces panel to lay out components again
            tl.repaint();
    }
 


   public QuestionActivity()
   {
      super();
      try{buildToolbar("question.xml");}catch(Exception e){}
      makeElements();
      pack();
   }
    static public void main(String [] args)
   {
      QuestionActivity q = new QuestionActivity();
      q.onCreate();
    }
   }
