public class whiteQueen extends chessPiece {
   public whiteQueen() {
      super(true);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      // Implement the logic specific to how this piece moves
      return (moveIsDiagonal(move,charArray)||moveIsStraight(move,charArray));
   }
}