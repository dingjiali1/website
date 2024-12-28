package response;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import javax.swing.*;

 
public class LinearLayout extends JPanel
{
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    BoxLayout bl;
    int orientation = 0;
    //setBackground(Color.DARK_GRAY);
    //setPreferredSize(new Dimension(100, 400)); 
    public  LinearLayout(int o)
    {
       orientation = o;
       if (o == 0)
          bl = new BoxLayout(this, BoxLayout.X_AXIS);
       else
          bl = new BoxLayout(this, BoxLayout.Y_AXIS);
       setLayout(bl);
    }
    
    class LayoutParams
    {
        static final int MATCH_PARENT = -1, WRAP_CONTENT = -2;
    }
    void setOrientation(int o)
    {
       orientation = o;
       if (o == 0)
          bl = new BoxLayout(this, BoxLayout.X_AXIS);
       else
          bl = new BoxLayout(this, BoxLayout.Y_AXIS);
       setLayout(bl); 
    }
    void setLayoutParams(  LayoutParams l)
    {
       
    }
}    
    
