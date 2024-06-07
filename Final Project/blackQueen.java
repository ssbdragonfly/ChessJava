public class blackQueen extends chessPiece {
   public blackQueen() {
      super(false);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      return (moveIsDiagonal(move,charArray)||moveIsStraight(move,charArray));
   }
}