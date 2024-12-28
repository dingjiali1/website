package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
public class ParseActivity extends AppCompatActivity
{
   
   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
      
      JPanel ll1 = new JPanel(new GridBagLayout());
      ll1.setBackground(ParseLayout.hex2Rgb("#aaaaaa"));
      
      c = new GridBagConstraints();
      c.insets = new Insets(2,2,2,2);
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 0.5;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.50;
      contentpane.add(ll1,c);
      ll1.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      
      JLabel lbl2 = new JLabel( "<html><table><tr><td>" +R.string.parseinstr.replaceAll("\n","<br>") + "</td></tr></table></html>");
      components.put(R.id.instr,lbl2);
      lbl2.setPreferredSize(new Dimension(400,60));
      lbl2.setMinimumSize(new Dimension(400,60));
      lbl2.setForeground(ParseLayout.hex2Rgb(AppCompatActivity.foreground));
      lbl2.setBackground(ParseLayout.hex2Rgb(AppCompatActivity.background));
      lbl2.setOpaque(true);
      c = new GridBagConstraints();
      //c.insets = new Insets(2,2,2,2);
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 0;
      c.weighty = 0.8;
      lbl2.setFont(new Font("Times",Font.PLAIN,AppCompatActivity.fontsize));
      ll1.add(lbl2,c);
      
       JTextArea txt3 = new  JTextArea("",5,5);
      components.put(R.id.editText,txt3);
      txt3.setForeground(ParseLayout.hex2Rgb("#000000"));
      txt3.setBackground(ParseLayout.hex2Rgb("#ffffff"));
      txt3.setOpaque(true);
      c = new GridBagConstraints();
      txt3.setPreferredSize(new Dimension(500,600));
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(2,2,2,2);
      c.anchor = GridBagConstraints.NORTHWEST;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.1;
      ll1.add(txt3,c);
      
   }

    String question;
    String rawtxt;
    JTextArea editText;
    JLabel lbl;
    ArrayList<String> questions = new ArrayList<>();
  
    protected void onCreate( ) {
        super.onCreate( );
        
        question = getIntent().getStringExtra("question");
        rawtxt =  getIntent().getStringExtra("rawtxt");
        editText = (JTextArea) findViewById(R.id.editText);
        lbl = (TextView)findViewById(R.id.instr);
        if (question!=null)
            editText.setText(question);
        else
        {
            editText.setText(rawtxt);

        }
        editText.setFont(new Font("Time", Font.PLAIN,AppCompatActivity.fontsize));
        editText.addFocusListener(  new FocusListener() {
 
                    public void focusGained(FocusEvent e) {
                        lbl.setText("");
                        lbl.setPreferredSize(new Dimension(300,1));
                    }
                    public void focusLost(FocusEvent e) { 
                           lbl.setText(getString(R.string.parseinstr));
                           lbl.setPreferredSize(new Dimension(300,50));
                    }
                });
             
        questions = getIntent().getStringArrayListExtra("questions");
        setTitle(getString(R.string.parsetxt));
    }


     
    public void onOptionsItemSelected(int item)
    {
        super.onOptionsItemSelected(item);
        int j = item;
        if (j == R.id.cancel)
        {
            Intent intent = new Intent();
            intent.putStringExtra("question",editText.getText());
            setResult(RESULT_OK-1, intent);
            finish();
        }
        else
        if (j == R.id.parse)
        {
            Intent intent = new Intent();
            intent.putStringExtra("question", editText.getText());
            setResult(RESULT_OK, intent);
            finish();
        }
        else  if (j == R.id.merge)
        {
            StringBuffer sb = new StringBuffer();
            for (String x:questions)
            {
                if (sb.length() > 0)
                    sb.append("\n\n");
                sb.append(x);
            }
            editText.setText(sb.toString());
        }
 
    }
   public ParseActivity()
   {
      super();
      try{buildToolbar("parse.xml");}catch(Exception e){}
      makeElements();
       pack();
   }
    static public void main(String [] args)
   {
      new ParseActivity();
    }
   }
