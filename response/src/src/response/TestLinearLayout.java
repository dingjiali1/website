package response;



import java.awt.Button;
import java.awt.Frame;
import javax.swing.BoxLayout;

public class TestLinearLayout extends Frame 
{  
 Button buttons[][];  
 LinearLayout tl = new LinearLayout(LinearLayout.VERTICAL); 
 public TestLinearLayout() 
 {  
   buttons = new Button [5][];  
    
   for (int i = 0;i<5;i++) 
   {  
      buttons[i] = new Button[2];
      LinearLayout r = new LinearLayout(LinearLayout.HORIZONTAL);
      tl.add(r);
      for (int j=0; j < 2; j++)
      {
          buttons[i][j] = new Button("Button " + (i + 1));  
          r.add(buttons[i][j]);
      }
    }  
    add(tl);
    setSize(400,400);  
    setVisible(true);  
}  
  
public static void main(String args[]){  
TestLinearLayout b =new TestLinearLayout();  
}  
}  
 
