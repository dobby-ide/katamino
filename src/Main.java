public class Main {
    public static void main(String[] args) {
        Board board = new Board(4,5);
        board.place(PentominoColor.LGREEN.getPentomino(), 0,0);
        board.print();
    }
}
