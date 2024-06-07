public class whiteKing extends chessPiece {
   public whiteKing() {
      super(true);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      return ((moveIsDiagonal(move,charArray)||moveIsStraight(move,charArray))&&(Math.abs(move.getEndY()-move.getStartY())<2)&&(Math.abs(move.getEndX()-move.getStartX())<2));
   }
}