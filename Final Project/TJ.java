import javax.swing.*;
import java.awt.*;

public class TJ implements Animatable{
   private int x;
   private int y;
   private int s;
   
   public TJ()
   {
      x=50;
      y=50;
      s=100;
   }
   public void drawMe(Graphics g)
   {
      ImageIcon tommy = new ImageIcon("Test.jpg");
      g.drawImage(tommy.getImage(), x, y, s, s, null);
   }
   public void step(){
   x++;
   y++;
      
   }
}