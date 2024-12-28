package response;

import layout.*;
import java.io.*;
import java.util.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import javax.swing.text.html.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
public class GalleryActivity extends AppCompatActivity
{
    String path;  
   public GalleryActivity(String path)
   {
      this.path  = path;
      onCreate();
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
   }
   
   protected void onCreate()
   {
       try{
       //String path =   getIntent().getStringExtra("path");
       if (path == null)
           path = "C:\\Users\\zhong\\Pictures\\warplane.jpg";
   
       BufferedImage img=ImageIO.read(new File(path));
       
       ImageIcon icon=new ImageIcon(img);
       this.setLayout(new GridBagLayout());
       setSize(img.getWidth(),img.getHeight());
       JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.gridx = c.gridy = 0;
        c.weightx = c.weighty = 1;
        add(lbl,c);
         setVisible(true);
       setTitle(path); 
       }catch(Exception e){}
   }
  
    static public void main(String [] args)
   {
      new GalleryActivity(null);
    }
   }
