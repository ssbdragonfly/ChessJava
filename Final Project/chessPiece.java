public abstract class chessPiece {
   protected boolean isWhite;

   public chessPiece(boolean isWhite) {
      this.isWhite = isWhite;
   }

   public abstract boolean isValidMove(chessMove move,char[][] charArray);
    
   public boolean moveIsDiagonal(chessMove move, char[][] charArray) {
      int dx = move.getEndX() - move.getStartX();
      int dy = move.getEndY() - move.getStartY();
      System.out.println("" + dx);
      System.out.println("" + dy);
      System.out.println(move.getStartY() + "," + move.getStartX());

      if (Math.abs(dx) != Math.abs(dy)) {
         return false;
      }

      int stepX = dx > 0 ? 1 : -1;
      int stepY = dy > 0 ? 1 : -1;
      int steps = Math.abs(dx); 

      for (int i = 1; i < steps; i++) {
         int currentX = move.getStartX() + i * stepX;
         int currentY = move.getStartY() + i * stepY;
         if (charArray[currentY][currentX] != '\u0000') {
               System.out.println("" + charArray[currentY][currentX]);
               return false;
         }
      }

      return true;
   }

   public boolean moveIsStraight(chessMove move,char[][] charArray){
      if (((move.getStartX()==move.getEndX())||(move.getStartY()==move.getEndY())) == false){
         return false;
      }
      else if (move.getEndX() - move.getStartX() > 0){
         for(int x = move.getStartX()+1; x<move.getEndX(); x++){
            if(charArray[move.getEndY()][x]!='\u0000'){
               return false;
            }
         }
         return true;
      }
      else if (move.getEndX() - move.getStartX() < 0){
         for(int x = move.getStartX()-1; x>move.getEndX(); x--){
            if(charArray[move.getEndY()][x]!='\u0000'){
               return false;
            }
         }
         return true;
      }
      else if (move.getEndY() - move.getStartY() > 0){
         for(int y = move.getStartY()+1; y<move.getEndY(); y++){
            if(charArray[y][move.getEndX()]!='\u0000'){
               return false;
            }
         }
         return true;
      }
      else if (move.getEndY() - move.getStartY() < 0){
         for(int y = move.getStartY()-1; y>move.getEndY(); y--){
            if(charArray[y][move.getEndX()]!='\u0000'){
               return false;
            }
         }
         return true;
      }
      else {
         return false;
      }
   }
}