public class blackBishop extends chessPiece {
   public blackBishop() {
      super(false);
   }

   public boolean isValidMove(chessMove move,char[][] charArray) {
      return (moveIsDiagonal(move,charArray));
   }
}