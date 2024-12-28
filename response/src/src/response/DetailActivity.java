package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;import javax.swing.border.*;
import java.awt.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
public class DetailActivity extends AppCompatActivity
{
   
   void makeElements()
   {
      Border border1,margin1;
      GridBagConstraints c;
      c = new GridBagConstraints();
      c.fill = GridBagConstraints.BOTH;
      c.anchor = GridBagConstraints.NORTHEAST;
      c.weightx = 0.99;
      c.gridx = 0;
      c.gridy = 1;
      c.weighty = 0.99;
      JEditorPane web1 = new JEditorPane("text/html","R.string.Report");
      components.put(R.id.detail,web1);
      contentpane.add(web1,c);
      
   }
    JEditorPane  webView;
    ArrayList<String> sorts;
    String shortf(long f)
    {
        if (f < 10) return ".00" + f;
        else if (f < 100) return ".0" + f;
        else if (f < 1000) return  "." + f;
        return "" + (f/1000);
    }
     
    protected void onCreate() {
        super.onCreate();
        ArrayList<String> sorts = getIntent().getStringArrayListExtra("sorts");

        StringBuffer sb = new StringBuffer();
        sb.append("<!DOCTYPE html><html><head><title>Detail</title><style>h2,table tr td{color:#7c1843}</style></head><body><table border=1 style=\"border-color:green;border-width:1px;border-collapse:collapse\" ><tr><td align=left>" + getString(R.string.sender)
                + "</td><td  align=left>" + getString(R.string.theip)
                + "</td><td  align=left>" + getString(R.string.isfile)
                + "</td><td  align=right>" + getString(R.string.number)
                + "</td><td  align=left>" + getString(R.string.content)
                + "</td><td  align=right>" + getString(R.string.status)
                + "</td><td  align=right>" + getString(R.string.KB) + "</td></tr>");
        long sum = 0;
        ArrayList<String> csid = new ArrayList<>();
        ArrayList<String> cip = new ArrayList<>();
        ArrayList<Integer> qnum = new ArrayList<>();

        int count = 0;
        for (EnquireActivity.ReceiveData r : EnquireActivity.clients)
        {
            if (r.msg == null) continue;
            String x = r.msg.isfilename?getString(R.string.upload):(r.msg.isdownload?getString(R.string.download):getString(R.string.Response));
            sb.append("<tr><td>");
            sb.append(r.msg.sid);sb.append("</td><td>");
            sb.append(r.ip);sb.append("</td><td>");
            sb.append(x);sb.append("</td><td  align=right>");
            sb.append(r.msg.questionNumber);sb.append("</td><td>");
            sb.append(r.msg.answer);sb.append("</td><td  align=right>");
            sb.append(r.status);sb.append("</td><td  align=right>");
            sb.append(shortf(r.replylength + r.msg.length));sb.append("</td></tr>");
            sum += r.replylength + r.msg.length;
            if (!csid.contains(r.msg.sid))csid.add(r.msg.sid);
            if (!cip.contains(r.ip))cip.add(r.ip);
            if (!qnum.contains(r.msg.questionNumber))qnum.add(r.msg.questionNumber);
            count++;
        }
        sb.append("<tr bgcolor=#eeeeee><td>");
        sb.append(csid.size());sb.append("</td><td>");
        sb.append(cip.size());sb.append("</td><td>");
        sb.append("");sb.append("</td><td  align=right>");
        sb.append(qnum.size());sb.append("</td><td>");
        sb.append(count);sb.append("</td><td  align=right>");
        sb.append("");sb.append("</td><td  align=right>");
        sb.append(shortf(sum));sb.append("</td></tr>");
        sb.append("</table>");// + getString(R.string.detail2));
     //   sb.append("<script>var type='" + getString(R.string.Response)  + "';</script>");
     //   sb.append("<script>" + LoadData("details.js") + " </script>");
        sb.append("</body></html>");
         
        webView = (JEditorPane)findViewById(R.id.detail) ;
        HTMLEditorKit kit = new HTMLEditorKit();
        webView.setEditorKit(kit);
        
        // add some styles to the html
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
        styleSheet.addRule("table {border-collapse: collpase;}");
        styleSheet.addRule("h2 {color: #ff0000;}");
        styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");
        Document doc = kit.createDefaultDocument();
        webView.setDocument(doc);
        webView.setText(sb.toString());
         
        setTitle(getString(R.string.allmsg) );
    }
    
    public void onOptionsItemSelected(int item)
    {
        super.onOptionsItemSelected(item);
        int j = item ;
        if (j == R.id.prev)
        {
            finish();
        }

         
    }
    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = null;//getAssets().open(inFile);
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }


   public DetailActivity()
   {
      super();
      try{buildToolbar("detail.xml");}catch(Exception e){}
      makeElements();
       pack();
   }
    static public void main(String [] args)
   {
      new DetailActivity();
    }
   }
