import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.concurrent.*;

public class chessMenuGUI extends JPanel {
   private  static boolean ai;
   private static boolean changed = false;
   private BufferedImage backgroundImage;
   private Point imageCorner;
   private Point prevPt;
   private JButton switchButton;
   private JButton switchButton2;
   private JButton switchButton3;
   private JButton switchButton4;
   private JLayeredPane layeredPane;
   private Timer timer;

    // Custom JPanel for the background image
   private class BackgroundPanel extends JPanel {
      public BackgroundPanel() {
         setOpaque(false);
      }
   
      @Override
        protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.drawImage(backgroundImage, imageCorner.x, imageCorner.y, this);
      }
   }

   public chessMenuGUI() {
      try {
         backgroundImage = ImageIO.read(new File("Test.jpg"));
      } catch (IOException e) {
         e.printStackTrace();
      }
      imageCorner = new Point(0, 0);
      prevPt = new Point(0, 0);
   
        // Set up the panel
      setPreferredSize(new Dimension(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2));
      setLayout(new BorderLayout());
   
        // Set up the layered pane for the background
      layeredPane = new JLayeredPane();
      layeredPane.setPreferredSize(new Dimension(backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2));
   
        // Create and add the background panel to the layered pane
      BackgroundPanel backgroundPanel = new BackgroundPanel();
      backgroundPanel.setBounds(0, 0, backgroundImage.getWidth() / 2, backgroundImage.getHeight() / 2);
      layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
   
        // Add the buttons to the layered pane on a higher layer
      addButtons();
   
        // Add mouse motion listener to the layered pane for the parallax effect
      addParallaxEffect(backgroundPanel);
   
        // Timer for repainting the background panel
      timer = new Timer(30, e -> backgroundPanel.repaint());
      timer.start();
   
        // Add the layered pane to the panel
      add(layeredPane);
   }
    
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    static boolean report() {
        while (!changed) {
            try {
                Future<?> future = executorService.submit(() -> {});
                future.get(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }
        return ai;
    }


    
   private void addButtons() {
   // Create a panel that will hold the buttons
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
      buttonPanel.setOpaque(false); // Make the panel transparent
      buttonPanel.setBounds(100, 80, 200, 300); // Set bounds to position the panel within the layeredPane
   
   // Title label
      JLabel titleLabel = new JLabel("Chess");
      titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
      titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
   
   // Add the title label to the button panel
      buttonPanel.add(titleLabel);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
   
   // Configure and add switchButton
      switchButton = new JButton("Play Two-Player");
      switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchButton.setFont(new Font("Arial", Font.BOLD, 18));
      switchButton.setMaximumSize(new Dimension(200, switchButton.getMinimumSize().height));
      switchButton.addActionListener(
         new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               ai = false; 
               changed = true;
               JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(chessMenuGUI.this);
               Container contentPane = topFrame.getContentPane();
               if (contentPane instanceof JPanel) {
                  JPanel parentPanel = (JPanel) contentPane;
                  CardLayout layout = (CardLayout) parentPanel.getLayout();
                  layout.next(parentPanel);
               }
            }
         });
      buttonPanel.add(switchButton);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
   
   // Configure and add switchButton2
      switchButton2 = new JButton("Play Against AI");
      switchButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchButton2.setFont(new Font("Arial", Font.BOLD, 18));
      switchButton2.setMaximumSize(new Dimension(200, switchButton2.getMinimumSize().height));
      switchButton2.addActionListener(
         new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               ai = true;
               changed = true;
               JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(chessMenuGUI.this);
               Container contentPane = topFrame.getContentPane();
               if (contentPane instanceof JPanel) {
                  JPanel parentPanel = (JPanel) contentPane;
                  CardLayout layout = (CardLayout) parentPanel.getLayout();
                  layout.next(parentPanel);
               }
            }
         });
      buttonPanel.add(switchButton2);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
   
   // Configure and add switchButton3
      switchButton3 = new JButton("Options");
      switchButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchButton3.setFont(new Font("Arial", Font.BOLD, 18));
      switchButton3.setMaximumSize(new Dimension(200, switchButton3.getMinimumSize().height));
      switchButton3.addActionListener(
         new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
               JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(chessMenuGUI.this);
               Container contentPane = topFrame.getContentPane();
               if (contentPane instanceof JPanel) {
                  JPanel parentPanel = (JPanel) contentPane;
                  CardLayout layout = (CardLayout) parentPanel.getLayout();
                  layout.next(parentPanel);
               }
            }
         });
      buttonPanel.add(switchButton3);
      buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
   
   // Configure and add switchButton4
      switchButton4 = new JButton("Exit");
      switchButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
      switchButton4.setFont(new Font("Arial", Font.BOLD, 18));
      switchButton4.setMaximumSize(new Dimension(200, switchButton4.getMinimumSize().height));
      switchButton4.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
         });
   
   
      buttonPanel.add(switchButton4);
   
   // Add the button panel to the layered pane
      layeredPane.add(buttonPanel, JLayeredPane.PALETTE_LAYER);
   }

   private void addParallaxEffect(BackgroundPanel backgroundPanel) {
      layeredPane.addMouseMotionListener(
         new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
               Point currentPt = SwingUtilities.convertPoint(layeredPane, e.getPoint(), backgroundPanel);
               imageCorner.translate(
                        (int) (prevPt.x - currentPt.x),
                        (int) (prevPt.y - currentPt.y));
               prevPt = currentPt;
               backgroundPanel.repaint(); // Repaint only the background panel
            }
         });
   
      layeredPane.addMouseListener(
         new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
               prevPt = SwingUtilities.convertPoint(layeredPane, e.getPoint(), backgroundPanel);
            }
         });
   }
}



