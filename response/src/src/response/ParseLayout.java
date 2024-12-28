package response;

import java.awt.Color;
import java.awt.Component;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import javax.swing.*;
import java.util.*;

class MenuInfo
{
    
    public int id=-1;
    public String str="";
    public String icon;
    public String showAsAction;
    public String title;
    public String toString()
    {
        return  "["+ id + ",\"" + str +  "\",\"" + icon +  "\",\"" + showAsAction +
        "\",\"" + title + "\"]";
    }
    public MenuInfo(String is, String ic, String a, String t)
    {
        
        if (is!=null)
        id = ParseLayout.getid(is);
        str = is;
        icon = ic;
        showAsAction = a;
        title = t;
    }
    public MenuInfo(int is, String ic, String a, String t)
    {
        id = is;
        icon = ic;
        showAsAction = a;
        title = t;
    }
}

public class ParseLayout {
   static public String sourcefolder = "";
   static public String destinyfolder = "";
   String sourcefile;
   Document doc;
   Element root = null;
   StringBuffer s = new StringBuffer("");
   StringBuffer dec = new StringBuffer();
   int ii = 0;
   int [] row= new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
   int [] col= new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
   String [] rc = new String[16];
   String mode = ""; //linevertial, line
   StringBuffer Rs = new StringBuffer();
   static HashMap<String,String>  ids = new HashMap();
   ArrayList<MenuInfo> ms = new ArrayList();
   int level = 0;
   R.string forlang = new R.string();
   public ParseLayout(String f)
   { 
       sourcefile = f;
       
       try
       {
           DocumentBuilderFactory factory =
           DocumentBuilderFactory.newInstance();
           DocumentBuilder builder = factory.newDocumentBuilder();
           StringBuilder xmlStringBuilder = new StringBuilder();
           xmlStringBuilder.append( (new String ( Files.readAllBytes( Paths.get(sourcefolder + File.separator + sourcefile) ) )).replaceAll("fill_parent","match_parent")); 
           ByteArrayInputStream input =  new ByteArrayInputStream(
           xmlStringBuilder.toString().getBytes("UTF-8"));
           doc = builder.parse(input);
           root = doc.getDocumentElement();
       }
       catch(Exception e){
       err += e.toString() + "check " + sourcefolder + File.separator + sourcefile;System.out.println(e.toString() + "check " + sourcefolder + File.separator + sourcefile);
       
       }
 
//getAttribute("attributeName"); 
//getAttributes(); 
//getElementsByTagName("subelementName"); 
//getChildNodes(); 
}
   
          
   void parse(String parentname, Element e)
   {
       if (e == null) return;
       level++;
       if (e.getNodeName().equals("LinearLayout"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
           
          int or = 0;
          if (orientation!=null && orientation.equals("vertical")) 
              or = 1;
          String name = "ll" + (ii++);
          String id = e.getAttribute("android:id");id = getit(id);
          s.append("JPanel " + name + " = new JPanel(new GridBagLayout());\n"); 
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n");
          
          if (parentname!=null)
          {
              s.append("c = new GridBagConstraints();\n");
              if (or==0)
                 s.append("c.fill = GridBagConstraints.HORIZONTAL;\n"); 
              //else   s.append("c.fill = GridBagConstraints.VERTICAL;\n");
              if (width!=null&& width.equals("match_parent"))
              {
                  s.append("c.weightx = 1.0;\n");
                  s.append("c.gridwidth = GridBagConstraints.REMAINDER;\n");
              }
              else
                 s.append("c.weightx = 0.5;\n");
              s.append("c.gridx = " + col[level-1] + ";\n");
              s.append("c.gridy = " + row[level-1] + ";\n");
              if (height!=null&& height.equals("match_parent"))
              {
                 s.append("c.weighty = 1.0;\n");
                 s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
                 s.append("c.gridheight = GridBagConstraints.REMAINDER;");
              }
              else
                 s.append("c.weighty = 0.5;\n");
              s.append(parentname + ".add(" + name + ",c);\n");
          }
          else
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
          if (background!=null && !background.equals(""))
            s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          NodeList roots = e.getChildNodes();
          row[level] = col[level] = 0;
          for (int i=0; i < roots.getLength();i++)
          {
              if (roots.item(i) instanceof Element)
              {
                  parse(name, (Element)roots.item(i) );
                  if (or == 0)col[level]++;
                  else row[level]++;
              }
          }
          row[level] = col[level] = 0;
       }
       else if (e.getNodeName().equals("TableLayout"))
       {
         // String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          int or = 0;
          if (orientation!=null && orientation.equals("vertical")) 
              or = 1;
          String name = "tl" + (ii++);
          String id = e.getAttribute("android:id");id = getit(id);;
          s.append("JPanel " + name + " = new JPanel(new GridBagLayout());\n");
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n");
          if (parentname!=null)
          {
              s.append(" c = new GridBagConstraints();\n");
              s.append(" c.anchor = GridBagConstraints.NORTHWEST;\n");
              if (or==0)
                 s.append("c.fill = GridBagConstraints.HORIZONTAL;\n"); 
              //else   s.append("c.fill = GridBagConstraints.VERTICAL;\n");
              if (width!=null&& width.equals("match_parent"))
              {
                  s.append("c.gridwidth = GridBagConstraints.REMAINDER;\n");
                  s.append("c.weightx = 1.0;\n");
              }
              else
                 s.append("c.weightx = 0.5;\n");
              s.append("c.gridx = " + col[level-1] + ";\n");
              s.append("c.gridy = " + row[level-1] + ";\n");
              if (height!=null&& height.equals("match_parent"))
              {
                  s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
                  s.append("c.gridheight = GridBagConstraints.REMAINDER;");
                  s.append("c.weighty = 1.0;\n");
              }
                 
              else
                 s.append("c.weighty = 0.5;\n");
           
             s.append(parentname + ".add(" + name + ",c);\n");
          }
          else
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
          //if (background!=null && !background.equals(""))  s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          NodeList roots = e.getChildNodes();
          col[level] = row[level] = 0; 
          for (int i=0; i < roots.getLength();i++)
          {
              if (roots.item(i) instanceof Element)
              {
                 parse(name, (Element)roots.item(i) );
                 row[level]++;
              }
          }
          row[level] = 0;
       }
       
       else if (e.getNodeName().equals("TableRow"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          int or = 0;
          if (orientation!=null && orientation.equals("vertical")) 
              or = 1;
          
          String id = e.getAttribute("android:id");
          id = getit(id); 
           
          NodeList roots = e.getChildNodes();
          col[level] = 0;
          row[level] = row[level-1]; 
          for (int i=0; i < roots.getLength();i++)
          {
              if (roots.item(i) instanceof Element)
              {
                 parse(parentname, (Element)roots.item(i) );
                 col[level]++;
              }
          }
          col[level] = 0;
       }
       else if (e.getNodeName().equals("ScrollView") || e.getNodeName().equals("HorizontalScrollView") || e.getNodeName().equals("android.support.constraint.ConstraintLayout"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          int or = 0;
          if (orientation!=null && orientation.equals("vertical")) 
              or = 1;
          String name = "sp" + (ii++);
          String id = e.getAttribute("android:id");id = getit(id);;
          s.append("JPanel " +   name + " = new JPanel(new GridBagLayout());\n");
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n");  
          if (parentname!=null)
          {
              s.append(" c = new GridBagConstraints();\n");
              if (or==0)
                 s.append("c.fill = GridBagConstraints.HORIZONTAL;\n"); 
              //else   s.append("c.fill = GridBagConstraints.VERTICAL;\n");
              if (width!=null&& width.equals("match_parent"))
              {
                  s.append("c.gridwidth = GridBagConstraints.REMAINDER;\n");
                  s.append("c.weightx = 1.0;\n");
              }
              else
                 s.append("c.weightx = 0.5;\n");
              s.append("c.gridx = " + col[level-1] + ";\n");
              s.append("c.gridy = " + row[level-1] + ";\n");
              if (height!=null&& height.equals("match_parent"))
              {
                  s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
                  s.append("c.gridheight = GridBagConstraints.REMAINDER;");
                  s.append("c.weighty = 1.0;\n");
              }
              else
                 s.append("c.weighty = 0.5;\n");
           
             s.append(parentname + ".add(" + name + ",c);\n");
          }
          else 
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
          if (background!=null && !background.equals(""))
            s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          NodeList roots = e.getChildNodes();
          col[level] = row[level] = 0; 
          for (int i=0; i < roots.getLength();i++)
          {
              if (roots.item(i) instanceof Element)
              {
                 parse(name, (Element)roots.item(i) );
                 if (or==1)row[level]++;
                 else col[level]++;
              }
          }
          col[level] = row[level] = 0; 
       }
       else if (e.getNodeName().equals("EditText"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          String name = "txt" + (ii++);
          String id = e.getAttribute("android:id");id = getit(id);; 
          String margin=e.getAttribute("android:layout_margin");
          String padding=e.getAttribute("android:padding");
          String text=e.getAttribute("android:text");
          if (text!=null) text = text.replaceFirst("@string/", "R.string.");
          String textColor=e.getAttribute("android:textColor");
          String textSize=e.getAttribute("android:textSize");
          String tsize = textSize;
          if (height!=null && (height.equals("match_parent") || height.equals("fill_parent")))
          {
              if (tsize != null) 
                  tsize = "" + (4+Integer.parseInt(tsize.replaceAll("[^0-9]", "")));
              else
                  tsize = "22";
              height = tsize;
          }
           
          if (text.indexOf("R.string") <0 ) text = "\"" + text + "\"";
         
          s.append("JTextField " + name   + " = new JTextField("+text +");\n");
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n");
          if (textColor!=null && !textColor.equals(""))
          s.append(name + ".setForeground(ParseLayout.hex2Rgb(\"" + textColor + "\"));\n");
          if (background!=null && !background.equals(""))
          s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          
          if (textSize!=null && !textSize.equals(""))
          {
              s.append(name + ".setFont(new Font(\"Times\",Font.PLAIN," + textSize.replaceFirst("[^0-9]+","") + "));\n");   
          }  
          if (margin!=null && !margin.equals(""))
          {
              margin = margin.replaceFirst("[^0-9]+","");
              //s.append(name + ".setMargin(new Insets(" + margin + "," + margin  + "," + margin + "," + margin + "));\n");
          }
          
          if (parentname!=null)
          {
              s.append(" c = new GridBagConstraints();\n");
              

              if (width!=null&& width.indexOf("_parent")>0)
              {   
                 s.append("c.weightx = 1.0;\n");
                 s.append("c.gridwidth = GridBagConstraints.REMAINDER;");
                 s.append("c.fill = GridBagConstraints.HORIZONTAL;\n");
              }
              else
              {
                  String ans = dim(width, height, textSize);
                  if (ans!=null)
                  {
                     s.append(name + ".setPreferredSize(new Dimension(" + ans + "));\n");
                  }
                  s.append("c.weightx = 0.5;\n");
                  if (width!=null) width = width.replaceAll("[^0-9]","");
              }
              s.append("c.gridx = " + col[level-1] + ";\n");
              s.append("c.gridy = " + row[level-1] + ";\n");
              if (height!=null&& height.indexOf("_parent")>0)
              {
                  s.append("c.weighty = 1.0;\n");
                  s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
                  s.append("c.gridheight = GridBagConstraints.REMAINDER;");
              }
              else
              {
                  String ans = dim(width, height, textSize);
                  if (ans!=null)
                  {
                     s.append(name + ".setPreferredSize(new Dimension(" + ans + "));\n");
                  }
                  s.append("c.weighty = 0.5;\n");
                  if (height!=null) height = height.replaceAll("[^0-9]","");
              }
              if (padding!=null && !padding.equals(""))
              {
                  margin = padding.replaceFirst("[^0-9]+","");
                  s.append("c.insets = new Insets("+ margin + "," + margin  + "," + margin + "," + margin + ");\n");
              }
              s.append(parentname + ".add(" + name + ",c);\n");
          }
          else
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
          
       }
       else if (e.getNodeName().equals("TextView"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          String margin=e.getAttribute("android:layout_margin");
          String padding=e.getAttribute("android:padding");
          String text=e.getAttribute("android:text");
          if (text!=null) text = text.replaceFirst("@string/", "R.string.");
          String textColor=e.getAttribute("android:textColor");
          String textSize=e.getAttribute("android:textSize");
          String tsize = textSize;
          
          String name = "lbl" + (ii++);
          String id = e.getAttribute("android:id");id = getit(id);;
          
          if (text.indexOf("R.string") <0 ) text = "\"" + text + "\"";
          s.append("JLabel " +name + " = new JLabel( \"<html>\" +"+ text +".replaceAll(\"\\n\",\"<br>\") + \"</html>\");\n");
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n"); 
          
          if (textColor!=null)
          s.append(name + ".setForeground(ParseLayout.hex2Rgb(\"" + textColor + "\"));\n");
          if (background!=null && !background.equals(""))
          s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          
          
           
          if (margin!=null && !margin.equals(""))
          {
              margin = margin.replaceFirst("[^0-9]+","");
             // s.append(name + ".setMargin(new Insets(" + margin + "," + margin  + "," + margin + "," + margin + "));\n");
          }
           
          if (parentname!=null)
          {
              s.append(" c = new GridBagConstraints();\n");
              s.append("c.fill = GridBagConstraints.HORIZONTAL;\n"); 

              if (width!=null&& width.indexOf("_parent")>0 )
              {   
                 s.append("c.weightx = 1.0;\n");
                 s.append("c.gridwidth = GridBagConstraints.REMAINDER;");
                 s.append("c.fill = GridBagConstraints.HORIZONTAL;\n");
              }
              else
              {
                  String ans = dim(width, height, textSize);
                  if (ans!=null && !width.replaceFirst("[0-9]","").equals(width))
                  {
                     s.append(name + ".setPreferredSize(new Dimension(" + ans + "));\n");
                  }
                  s.append("c.weightx = 0.5;\n");
                   
              }
              s.append("c.gridx = " + col[level-1] + ";\n");
              s.append("c.gridy = " + row[level-1] + ";\n");
              if (height!=null&& height.indexOf("_parent")>0)
              {
                 s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
                 s.append("c.gridheight = GridBagConstraints.REMAINDER;");
                 s.append("c.weighty = 1.0;\n");
              }
              else
              {
                  String ans = dim(width, height, textSize);
                  if (ans!=null&& !width.replaceFirst("[0-9]","").equals(width))
                  {
                     s.append(name + ".setPreferredSize(new Dimension(" + ans + "));\n");
                  }
                  s.append("c.weighty = 0.5;\n");
                  
              }

              if (padding!=null && !padding.equals(""))
              {
                  margin = padding.replaceFirst("[^0-9]+","");
                  s.append("c.insets = new Insets("+ margin + "," + margin  + "," + margin + "," + margin + ");\n");
              }
              if (textSize!=null && !textSize.equals(""))
              {
                  s.append(name + ".setFont(new Font(\"Times\",Font.PLAIN," + textSize.replaceFirst("[^0-9]+","") + "));\n");   
              }

             s.append(parentname + ".add(" + name + ",c);\n");
          }
          else
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
           
          
           
       }
       else if (e.getNodeName().equals("WebView"))
       {
          String background=e.getAttribute("android:background");
          String orientation=e.getAttribute("android:orientation");
          String width=e.getAttribute("android:width");
          String height=e.getAttribute("android:height");
          
          
          
           
          s.append(" c = new GridBagConstraints();\n");
          s.append("c.fill = GridBagConstraints.HORIZONTAL;\n"); 
           
          if (width!=null&& width.equals("match_parent"))
          {
              s.append("c.gridwidth = GridBagConstraints.REMAINDER;\n");
              s.append("c.weightx = 1.0;\n");
          }
          else
          {
              s.append("c.weightx = 0.5;\n");
              if (width!=null) width = width.replaceAll("[^0-9]","");
          }
          s.append("c.gridx = " + col[level-1] + ";\n");
          s.append("c.gridy = " + row[level-1] + ";\n");
          if (height!=null&& height.equals("match_parent"))
          {
             s.append("c.weighty = 1.0;\n");
             s.append("c.fill = GridBagConstraints.VERTICAL;\n"); 
             s.append("c.gridheight = GridBagConstraints.REMAINDER;");
          }
          else
          {
              s.append("c.weighty = 0.5;\n");
             // if (height!=null) height = height.replaceAll("[^0-9]","");
          }
           
          String margin=e.getAttribute("android:layout_margin");
          String padding=e.getAttribute("android:padding");
          String text=e.getAttribute("android:text");
          if (text!=null) text = text.replaceFirst("@string/", "R.string.");
          String textColor=e.getAttribute("android:textColor");
          String textSize=e.getAttribute("android:textSize");
          String tsize = textSize;
          if (height!=null && (height.equals("match_parent") || height.equals("fill_parent")))
          {
              if (tsize != null) 
                  tsize = "" + (4+Integer.parseInt(tsize.replaceAll("[^0-9]", "")));
              else
                  tsize = "22";
             
          } 
          String id = e.getAttribute("android:id");id = getit(id); 
          String name = "web" + (ii++);
           
          s.append("JEditorPane " + name + " = new JEditorPane(\"text/html\",\""+text +"\");\n");
          if (id != null && !id.equals("")) 
             s.append("components.put(R.id." + id + "," + name + ");\n"); 
           
          if (textColor!=null && !textColor.equals(""))
          s.append(name + ".setForeground(ParseLayout.hex2Rgb(\"" + textColor + "\"));\n");
          if (background!=null && !background.equals(""))
          s.append(name + ".setBackground(ParseLayout.hex2Rgb(\"" + background + "\"));\n");
          
          if (width!=null && height != null && !width.equals("") && !height.equals("") )
          {
              if (!width.replaceAll("[0-9]","").equals(width) && !height.replaceAll("[0-9]","").equals(height))
               s.append(name + ".setPreferredSize(new Dimension(" + width.replaceAll("[^0-9","")  + "," + height.replaceAll("[^0-9","")  + "));\n");
          }
          
           
          if (margin!=null && !margin.equals(""))
          {
              margin = margin.replaceFirst("[^0-9]+","");
           //   s.append(name + ".setMargin(new Insets(" + margin + "," + margin  + "," + margin + "," + margin + "));\n");
          }
           
          if (padding!=null && !padding.equals(""))
          {
              margin = padding.replaceFirst("[^0-9]+","");
              s.append("c.insets = new Insets("+ margin + "," + margin  + "," + margin + "," + margin + ");\n");
          }
          if (textSize!=null && !textSize.equals(""))
          {
              s.append(name + ".setFont(new Font(\"Times\",Font.PLAIN," + textSize.replaceFirst("[^0-9]+","") + "));\n");   
          }
           
          if (parentname!=null)
             s.append(parentname + ".add(" + name + ",c);\n");
          else
          {
               s.append( "contentpane.add(" + name + ");\n");
               s.append(name + ".setAlignmentX(Component.LEFT_ALIGNMENT);\n");
          }
           
          
            
           
       }
       else if(e.getNodeName().equals("resources") || e.getNodeName().equals("menu") )
       {
          NodeList roots = e.getChildNodes();
          for (int i=0; i < roots.getLength();i++)
          {
              if (roots.item(i) instanceof Element)
              parse(parentname, (Element)roots.item(i));
          }         
       }
       else if(e.getNodeName().equals("color"))
       {
           if (!sourcefolder.equals("res"))
           {
              String id = e.getAttribute("name");
              Rs.append("static public String " + id + "=" +  "\"" + e.getTextContent() + "\";\n");
           }
       }
       else if(e.getNodeName().equals("string"))
       {
           if ( sourcefolder.indexOf("res" + File.separator + "values") < 0 )
           {
              String id = e.getAttribute("name");
              Rs.append("static public String " + id + "=" +  "\"" + e.getTextContent().trim().replaceAll("\"","'").replaceAll("\n","\\n") + "\";\n");
           }
           else
           {
               String id = e.getAttribute("name");
               try
               {
               Field field = R.string.class.getField(id);
               String x = e.getTextContent().trim().toString().replace('"','\'');
               field.set(forlang, x);
               }
               catch(Exception e1){}
           }
       }
       else if(e.getNodeName().equals("item"))
       {
            
               String id = e.getAttribute("android:id");
               id = getit(id);
               
               String icon = e.getAttribute("android:icon");
               if (icon!=null)icon = icon.replaceFirst("@drawable/","");
               
               String showAsAction = e.getAttribute("app:showAsAction");
               if (showAsAction!=null) 
               {
                   if (!showAsAction.replaceFirst("@string/","").equals(showAsAction))
                      showAsAction = getstring(showAsAction.replaceFirst("@string/",""));
               }
               String title = e.getAttribute("android:title");
               if (title!=null) 
               {
                   if (!title.replaceFirst("@string/","").equals(title))
                       title = getstring(title.replaceFirst("@string/",""));
               }
                
               MenuInfo m = new MenuInfo(id, icon, showAsAction,title);
               ms.add(m );
       }
       
       level--;
   }
   String getit(String id)
   {
       String nid = id.replaceFirst("@\\+id/","");
       if (!id.equals(nid))
       {
           ids.put(nid, "1");
       }
       return nid;
   }
   static Integer getid(String x)
   {
       R.id q = new R.id();
       Integer ans = -1;
       try{
        Field field = R.id.class.getField(x);
        ans = (Integer)field.get(q);
       }catch(Exception e){}
       return ans;
   }
    
   static String getstring(String id)
   {
       R.string q = new R.string();
       String ans = "";
       try{
        Field field = R.string.class.getField(id);
        ans = (String)field.get(q);
       }catch(Exception e){}
       return ans;
   }
   static public String err = "";
   public String proc()
   {
       if (root==null ) return null;
       if ( sourcefile.indexOf("activity") >= 0)
       {
       parse(null,root);
       String classname = sourcefile.replaceFirst("activity_([^\\.]+).*$", "$1Activity");
       classname = classname.substring(0,1).toUpperCase() + classname.substring(1);
       String df = destinyfolder + File.separator + classname + ".java";
       try{
           FileWriter f = new FileWriter(df, false);
           f.append("import layout.*;\nimport java.io.*;\nimport java.util.*;\nimport java.net.*;\nimport javax.swing.*;import javax.swing.border.*;\nimport java.awt.*;\npublic class " + classname + " extends AppCompatActivity\n{\n   ");
           f.append(dec.toString().replaceAll("\n", "\n   "));
           f.append("\n   void makeElements()\n   {\n      Border border1,margin1;\n       GridBagConstraints c;\n     ");
           f.append(s.toString().replaceAll("\n", "\n      ")); 
           f.append("\n   }\n");
           f.append("\n   public " + classname +"()\n   {\n      super();\n      ");
           f.append("try{buildToolbar(\"" + classname.replaceFirst("Activity","").toLowerCase() + ".xml\");}catch(Exception e){}\n      makeElements();\n       pack();\n   }\n    static public void main(String [] args)\n   {\n      new " + classname + "();\n    }\n   }");
           f.close();
       }catch(Exception e){err += "No path: " + df;System.out.println("No path: " + df);}
       }
       else if ( sourcefile.indexOf("strings") >= 0 &&  !sourcefolder.equals("res"))
       {
           parse(null,root);
           String classname = "R";
           String df = destinyfolder + File.separator +  "R.java";
           try{
           FileWriter f = new FileWriter(df, false);
           f.append("public class " + classname + "\n{\n   public static class string\n   {");
           f.append(Rs.toString().replaceAll("\n", "\n      "));
           f.append("\n   }\n  \n}");
           f.close();
           }catch(Exception e){err += "\nNo path: " + df ;System.out.println("No path: " + df);}
       }
       else if ( sourcefile.indexOf("colors") >= 0 )
       {
           parse(null,root);
           String classname = "R";
           String df = destinyfolder + File.separator +  "R.java";
           try{
           FileWriter f = new FileWriter(df, true);
           f.append("   public static class color\n   {");
           f.append(Rs.toString().replaceAll("\n", "\n      "));
           f.append("\n   }\n   }");
           f.close();
           }catch(Exception e){err += "\nNo path: " + df;System.out.println("No path: " + df);}
       }
       else if ( sourcefolder.indexOf("menu") >= 0 )
       {
           parse(null,root);
           //return ms.toString();
       }
       return null; 
   }
   String dim(String width, String height, String textSize)
   {
       int ts = 20;
       if (textSize!=null && !textSize.replaceFirst("[0-9]","").equals(textSize))
       {
           ts = Integer.parseInt(textSize.replaceAll("[^0-9]","")) + 4;
       }
       String ans = null; 
       if (width == null)
       {
           if (height == null)
           {
               ans = "70," + ts; 
           }
           else if (height.indexOf("_parent")>0)
           {
               ans = "70,200";
           }
           else if (height.indexOf("_content")>0)
           {
               ans = "70," + ts;
           }
           else if (!height.replaceFirst("[0-9]", "").equals(height))
           {
               ans = "70," +height.replaceAll("[^0-9]", "");
           }
           else
           {
               ans = "70," + ts;
           }
       }
       else if (width.indexOf("_parent")>0)
       {
           if (height == null)
           {
               ans = "200," + ts; 
           }
           else if (height.indexOf("_parent")>0)
           {
               ans = "200,200";
           }
           else if (height.indexOf("_content")>0)
           {
               ans = "200," + ts;
           }
           else if (!height.replaceFirst("[0-9]", "").equals(height))
           {
               ans = "200," +height.replaceAll("[^0-9]", "");
           }
           else
           {
               ans = "200," + ts;
           }
       }
       else if (width.indexOf("_content")>0)
       {
           if (height == null)
           {

           }
           else if (height.indexOf("_parent")>0)
           {

           }
           else if (height.indexOf("_content")>0)
           {

           }
           else if (!height.replaceFirst("[0-9]", "").equals(height))
           {

           }
           else
           {

           }
       }
       else if (!width.replaceFirst("[0-9]", "").equals(width))
       {
           if (height == null)
           {
               ans = width.replaceAll("[^0-9]", "") + "," + ts;
           }
           else if (height.indexOf("_parent")>0)
           {
               ans = width.replaceAll("[^0-9]", "") + ",200" ;
           }
           else if (height.indexOf("_content")>0)
           {
                ans = width.replaceAll("[^0-9]", "") + "," + ts;
           }
           else if (!height.replaceFirst("[0-9]", "").equals(height))
           {
                ans = width.replaceAll("[^0-9]", "") + "," + height.replaceFirst("[^0-9]", "");
           }
           else
           {
                ans = width.replaceAll("[^0-9]", "") + "," + ts;
           }
       }
       else
       {
           if (height == null)
           {
               ans = "70," + ts; 
           }
           else if (height.indexOf("_parent")>0)
           {
               ans = "70,200";
           }
           else if (height.indexOf("_content")>0)
           {
               ans = "70," + ts;
           }
           else if (!height.replaceFirst("[0-9]", "").equals(height))
           {
               ans = "70," +height.replaceAll("[^0-9]", "");
           }
           else
           {
               ans = "70," + ts;
           }
       }
       return ans;
   }
   public static void changelang(String lang)
   {
       ParseLayout.sourcefolder = "res";
       String lan = ""; 
       if (!lang.equals("")) lan += "-" + lang;
       ParseLayout p = new ParseLayout("strings" + lan + ".xml");
       p.parse(null,p.root);
   }
   public static Color hex2Rgb(String colorStr) {
    return new Color(
            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
}
   
   public static void main(String [] args)
   {
      /*ParseLayout.sourcefolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\Response\\src\\app\\src\\main\\res\\layout";
      ParseLayout.sourcefolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\Response\\src\\app\\src\\main\\res\\values";
      ParseLayout.destinyfolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\layout\\src";
       
      ParseLayout p = new ParseLayout("colors.xml");
      p.proc();*/
      if (args.length ==0)
      {
          ParseLayout.sourcefolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\Response\\src\\app\\src\\main\\res\\layout";
          ParseLayout.destinyfolder = "C:\\Users\\zhong\\Documents\\NetBeansProjects\\layout\\src";
      }
      else
      {
          ParseLayout.sourcefolder = args[0];
          ParseLayout.destinyfolder = args[1];
      }
      
      File f = new File(ParseLayout.sourcefolder);
      String fn[] = f.list();
      for (String x: fn)
      {
         if (x.indexOf("activity") < 0) continue;
         ParseLayout p = new ParseLayout(x);
          p.proc();
      }
      ParseLayout.sourcefolder = ParseLayout.sourcefolder.replaceFirst("layout$", "menu");
      f = new File(ParseLayout.sourcefolder);
      fn = f.list();
      for (String x: fn)
      {
         ParseLayout p = new ParseLayout(x);
         p.proc();
      }
      String classname = "R";
      String df = destinyfolder + File.separator +  "R.java";
      
      try{
           FileWriter ff = new FileWriter(df, true);
           ff.append("   public static class id\n   {");
           int jj = 0;
           for (String x : ids.keySet())
           {
                ff.append("\n        static public int " + x + " = " + (jj++) + ";");
           }
           ff.append("\n   }\n   }");
           ff.close();
           }catch(Exception e){err += "\nNo path: " + df; System.out.println("No path: " + df);} 
   }
}
