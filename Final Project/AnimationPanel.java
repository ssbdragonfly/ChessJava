import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

class AnimationPanel extends JPanel{
   public static final int FRAME = 500;
   private static final Color BACKGROUND = new Color(0, 204, 0);

   private BufferedImage myImage;  
   private Graphics myBuffer;
   private Timer t;
   private ArrayList<Animatable> animationObjects;

   public AnimationPanel(){
      myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_RGB); 
      myBuffer = myImage.getGraphics(); 
      myBuffer.setColor(BACKGROUND);    
   
      animationObjects = new ArrayList<Animatable>();  
 
      Animatable cr = new TJ(); 
      animationObjects.add(cr); 
            
      t = new Timer(5, new AnimationListener());  
      t.start();  
   }

   public void paintComponent(Graphics g){
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);  
   }

   public void animate(){
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0,0,FRAME,FRAME);
   
      for(Animatable animationObject : animationObjects){
         animationObject.step();  
         animationObject.drawMe(myBuffer);  
      }         
      
      repaint();
   }

   private class AnimationListener implements ActionListener{
      public void actionPerformed(ActionEvent e){
         animate();  
      }
   }
}