public class whiteKnight extends chessPiece {
   public whiteKnight() {
      super(true);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      return ((Math.abs(move.getEndX()-move.getStartX())==2)&&(Math.abs(move.getEndY()-move.getStartY())==1)||(Math.abs(move.getEndY()-move.getStartY())==2)&&(Math.abs(move.getEndX()-move.getStartX())==1));
   }
}