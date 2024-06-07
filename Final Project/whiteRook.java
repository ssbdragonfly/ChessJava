public class whiteRook extends chessPiece {
   public whiteRook() {
      super(true);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      return(moveIsStraight(move,charArray));
   }
}
