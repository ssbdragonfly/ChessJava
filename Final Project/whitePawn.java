import java.io.*;
import java.util.*;
public class whitePawn extends chessPiece {
   public whitePawn() {
      super(true);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      if ((move.getEndY()- move.getStartY() == -1)&&(move.getEndX()-move.getStartX()==0)){
         if(charArray[move.getEndY()][move.getEndX()]=='\u0000'){
            return true;
         }
         else{
            return false;
         }
      }
      else if ((move.getEndY()- move.getStartY() == -1)&&(Math.abs(move.getEndX()-move.getStartX())==1)){
         if(charArray[move.getEndY()][move.getEndX()]!='\u0000'){
            return true;
         }
         else{
            return false;
         }
      }
      else if ((move.getStartY() == 6) && (move.getEndY()- move.getStartY() == -2)&&(move.getEndX()-move.getStartX()==0)){

         return true;
      }
      else {
         return false;
      }
   }
}