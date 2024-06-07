public interface chessGameInterface {
    String doMove(chessMove move);
    String reset();
    String undo();
    String flip();
    // Other methods can be added as needed
}