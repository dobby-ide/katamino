public class Main {
    public static void main(String[] args) {
        Board board = new Board(4,5);
        //board.place(PentominoColor.LGREEN.getPentomino(), 0,0);
        //board.print();

        //PentominoColor.LGREEN.getPentomino().print();
        //board.remove(PentominoColor.LGREEN.getPentomino(), 0, 0);
        //System.out.println(board.place(PentominoColor.RED.getPentomino(), 0,0));
        //board.print();
        PentominoColor.LGREEN.getPentomino().rotate90();
        PentominoColor.LGREEN.getPentomino().rotate90();
        PentominoColor.LGREEN.getPentomino().rotate90();
        PentominoColor.LGREEN.getPentomino().rotate90();  //4 different positions
        //PentominoColor.NAVYBLUE.getPentomino().rotate90();
        board.place(PentominoColor.LGREEN.getPentomino(), 0,0);
        board.print();
    }
}
