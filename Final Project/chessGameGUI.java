import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class chessGameGUI extends JPanel {
   private chessGameInterface listener;
   private JLayeredPane layeredPane;
   private JPanel chessBoard;
   private JLabel pieceImage;
   private int xAdjustment;
   private int yAdjustment;
   private int col1;
   private int row1;
   String abcget = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1";

   public chessGameGUI(chessGameInterface listener) {
      this.listener = listener;
      
      Dimension boardSize = new Dimension(730, 768);
      layeredPane = new JLayeredPane();
      this.add(layeredPane);
      layeredPane.setPreferredSize(boardSize);
      
      chessBoard = new JPanel();
      layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
      chessBoard.setLayout(new GridLayout(8, 8));
      chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
      
      JButton resetButton = new JButton("Reset");
      resetButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               resetBoard();
            }
         });
      this.add(resetButton, BorderLayout.SOUTH);
      
      JButton undoButton = new JButton("Undo");
      undoButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               undoMove();
            }
         });
      this.add(undoButton, BorderLayout.SOUTH);
      
      JButton flipButton = new JButton("Flip");
      flipButton.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               flipBoard();
            }
         });
      this.add(flipButton, BorderLayout.SOUTH);
      
      JButton saveButton = new JButton("Save");
      saveButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            saveToFile(abcget);
         }
      });
      this.add(saveButton, BorderLayout.SOUTH);
      
      initializeBoard();
      addPieces(listener.reset());
      addMouseListeners(); 
   }
    
   private void initializeBoard() {
      for (int i = 0; i < 64; i++) {
         JPanel square = new JPanel(new BorderLayout());
         chessBoard.add(square);
      
         int row = (i / 8) % 2;
         if (row == 0) {
            if (i % 2 == 0){
               square.setBackground(new Color(232, 237, 249));
            }
            else{
               square.setBackground(new Color(183, 192, 216));
            }
         } 
         else {
            if (i % 2 == 0){
               square.setBackground(new Color(183, 192, 216));
            }
            else{
               square.setBackground(new Color(232, 237, 249));
            }
         }
      }
   
   }

   private void addPieces(String fen) {
      
      Component[] components = chessBoard.getComponents();
      for (int i = 0; i < components.length; i++) {
         if (components[i] instanceof JPanel) {
            JPanel panel = (JPanel) components[i];
            panel.removeAll();
         }
      }
      
      String[] fenParts = fen.split(" ");
      String[] rows = fenParts[0].split("/");
    
      for (int i = 0; i < rows.length; i++) {
         int col = 0;
         char[] rowArray = rows[i].toCharArray();
         for (int j = 0; j < rowArray.length; j++) {
            char c = rowArray[j];
            if (Character.isDigit(c)) {
               col += Character.getNumericValue(c);
            } 
            else {
               String color = "";
               if(Character.isUpperCase(c)){
                  color = "white";
               }
               else{
                  color = "black";
               }
               c = Character.toLowerCase(c); 
               String piece = "";
               if (c == 'r') {
                  piece = "Rook";
               } else if (c == 'n') {
                  piece = "Knight";
               } else if (c == 'b') {
                  piece = "Bishop";
               } else if (c == 'q') {
                  piece = "Queen";
               } else if (c == 'k') {
                  piece = "King";
               } else if (c == 'p') {
                  piece = "Pawn";
               }
                
               JLabel chessPiece = new JLabel(new ImageIcon("assets/" + color + piece + ".png"));
               JPanel panel = (JPanel) chessBoard.getComponent(i * 8 + col);
               panel.add(chessPiece);
               col++;
            }
         }
      }
   }
      
   private void resetBoard() {
      addPieces(listener.reset());
   
      chessBoard.revalidate();
      chessBoard.repaint();
   }

   private void undoMove() {  
      addPieces(listener.undo());
   
      chessBoard.revalidate();
      chessBoard.repaint();
   }
   private void flipBoard() {
      addPieces(listener.flip());
   
      chessBoard.revalidate();
      chessBoard.repaint();
   }
   
   private void saveToFile(String data) {
      System.out.println(abcget);
      JFileChooser fileChooser = new JFileChooser();
      if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
         File file = fileChooser.getSelectedFile();
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(data);
         } catch (IOException ex) {
            ex.printStackTrace();
         }
      }
   }

      
   private void addMouseListeners(){
      MouseAdapter mouseAdapter = 
         new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
               pieceImage = null;
               Component c = layeredPane.findComponentAt(e.getX(), e.getY());
            
               if (c instanceof JPanel) 
                  return;
            
               Point parentLocation = c.getParent().getLocation();
               col1 = (parentLocation.x / (chessBoard.getWidth() / 8));
               row1 = (parentLocation.y / (chessBoard.getHeight() / 8));
            
               xAdjustment = parentLocation.x - e.getX();
               yAdjustment = parentLocation.y - e.getY();
               pieceImage = (JLabel) c;
               pieceImage.setLocation(parentLocation.x, parentLocation.y);
               pieceImage.setSize(pieceImage.getWidth(), pieceImage.getHeight());
               layeredPane.add(pieceImage, JLayeredPane.DRAG_LAYER);
            }
         
            public void mouseDragged(MouseEvent me) {
               if (pieceImage == null) 
                  return;
               pieceImage.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
            }
         
            public void mouseReleased(MouseEvent e) {
               if (pieceImage == null) 
                  return;
            
               pieceImage.setVisible(false);
               
               Component c = layeredPane.findComponentAt(e.getX() + xAdjustment + (int)(chessBoard.getWidth()/16.0), e.getY() + yAdjustment + (int)(chessBoard.getHeight()/16.0));
                              
               int col = ((e.getX() + xAdjustment + (int)(chessBoard.getWidth()/16.0)) / (chessBoard.getWidth() / 8));
               int row = ((e.getY() + yAdjustment + (int)(chessBoard.getHeight()/16.0)) / (chessBoard.getHeight() / 8));
                              
               if (c instanceof JLabel) {
                  Container parent = c.getParent();
                  parent.remove(0);
                  parent.add(pieceImage);
               } else {
                  Container parent = (Container) c;
                  parent.add(pieceImage);
               }
               pieceImage.setVisible(true);
               
               if(!(col1==col && row1==row)){
                  chessMove move = new chessMove(col1, row1, col, row);
                  abcget = listener.doMove(move);
                  addPieces(abcget);
               
                  chessBoard.revalidate();
                  chessBoard.repaint();
               }
            
            }
         };
      layeredPane.addMouseListener(mouseAdapter);
      layeredPane.addMouseMotionListener(mouseAdapter);
   }
}