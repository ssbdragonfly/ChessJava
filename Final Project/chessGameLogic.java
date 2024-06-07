import java.io.*;
import java.util.*;

public class chessGameLogic implements chessGameInterface {
   private boolean whiteAtBottom = true;
   private chessPiece[][] array = new chessPiece[8][8];
   private char[][] charArray = new char[8][8];
   private boolean ai;
   private chessMenuGUI menuGUI;
   private boolean whiteplays = true;
   
   public chessGameLogic(chessMenuGUI menu){
      this.menuGUI = menu;
      new Thread(
         () -> {
            ai = menuGUI.report();
         }).start();
      for(int x=0;x<8;x++)
      {
         for(int y=0;y<8;y++)
         {
            array[x][y]=null;
         }
      }
      addPieces("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
      fenToCharArray("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
   }
   
   //intialize the classes of the pieces: blackPawn, whiteKing, etc. (Store them in a 2d array)
   private void fenToCharArray(String fen) {
      String[] rows = fen.split(" ")[0].split("/");
      for (int i = 0; i < rows.length; i++) {
         String row = rows[i];
         int col = 0;
         for (int j = 0; j < row.length(); j++) {
            if (Character.isDigit(row.charAt(j))) {
               int emptySpaces = Character.getNumericValue(row.charAt(j));
               for (int k = 0; k < emptySpaces; k++) {
                  charArray[i][col] = '\u0000';
                  col++;
               }
            } else {
               charArray[i][col] = row.charAt(j);
               col++;
            }
         }
      }
   }
   
   private void addPieces(String fen) {
      String[] rows = fen.split(" ")[0].split("/");
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
               
               
               try{
                  Class<?> clazz = Class.forName(color + piece);
                  Object obj = clazz.getDeclaredConstructor().newInstance();
                  array[i][col]=(chessPiece)obj;
               }
               catch(Exception e){
                  System.out.println("Error:" + color + piece);
               }
               col++;
            }
         }
      }
   
   }
   public String doMove(chessMove move) { 
      
      whiteplays=(!whiteplays);
      
      if(whiteAtBottom != true){
         move.setStartY(7-move.getStartY());
         move.setStartX(7-move.getStartX());
         move.setEndY(7-move.getEndY());
         move.setEndX(7-move.getEndX());
      }
      
      System.out.println(move.getStartX()+","+move.getStartY()+","+move.getEndX()+","+move.getEndY());
      System.out.println(array[move.getStartY()][move.getStartX()]+","+array[move.getEndY()][move.getEndX()]);      
            
      if(array[move.getStartY()][move.getStartX()]!=null){
         if(array[move.getStartY()][move.getStartX()].isValidMove(move,charArray) &&
         ((Character.toLowerCase(charArray[move.getStartY()][move.getStartX()])==(charArray[move.getStartY()][move.getStartX()]))!=
         (Character.toLowerCase(charArray[move.getEndY()][move.getEndX()])==(charArray[move.getEndY()][move.getEndX()])))
         ){
            charArray[move.getEndY()][move.getEndX()]=charArray[move.getStartY()][move.getStartX()];
            charArray[move.getStartY()][move.getStartX()]='\u0000';
            array[move.getEndY()][move.getEndX()]=array[move.getStartY()][move.getStartX()];
            array[move.getStartY()][move.getStartX()]=null;
         }
      }
      return returnFEN();
   }
   public String reset(){
      whiteplays = false;
      array = new chessPiece[8][8];
      charArray = new char[8][8];
      addPieces("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
      fenToCharArray("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
      return returnFEN();
   }
   public String undo(){
   //add code here to undo move and return current gamestate
      return reset();
   }
   public String flip() {
      toggleFlip();
      return returnFEN();
   }
   private String getFEN() {
      StringBuilder fen = new StringBuilder();
      for (int i = 0; i < charArray.length; i++) {
         int emptyCount = 0;
         for (int j = 0; j < charArray[i].length; j++) {
            if (charArray[i][j] == '\u0000') {
               emptyCount++;
            } else {
               if (emptyCount > 0) {
                  fen.append(emptyCount);
                  emptyCount = 0;
               }
               fen.append(charArray[i][j]);
            }
         }
         if (emptyCount > 0) {
            fen.append(emptyCount);
         }
         if (i < charArray.length - 1) {
            fen.append('/');
         }
      }
      if(whiteplays){
         fen.append(" b");
      }
      else{
         fen.append(" w");
      }
      fen.append(" - - 0 1");
      System.out.println(fen.toString());
      return fen.toString();
   }
   private String returnFEN(){
      if(whiteAtBottom){
         return getFEN();
      }
      else{
         return flipFEN();
      }
   }
   private void toggleFlip(){
      if(whiteAtBottom == true){
         whiteAtBottom = false;
      }
      else{
         whiteAtBottom = true;
      }
   }
   private String flipFEN(){
      String[] parts = getFEN().split(" ");
      String[] rows = parts[0].split("/");
      StringBuilder flippedFen = new StringBuilder();
      
      for (int i = rows.length - 1; i >= 0; i--) {
         flippedFen.append(new StringBuilder(rows[i]).reverse().toString());
         if (i != 0) {
            flippedFen.append("/");
         }
      }
      
      for (int i = 1; i < parts.length; i++) {
         flippedFen.append(" ");
         flippedFen.append(parts[i]);
      }
      
      return flippedFen.toString();
   }
}