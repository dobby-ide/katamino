import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(4,5);

        List<PentominoColor> pieces = new ArrayList<>();
        pieces.add(PentominoColor.BLUE);
        pieces.add(PentominoColor.CYAN);
        pieces.add(PentominoColor.ORANGE);
        pieces.add(PentominoColor.VIOLET);

        Solver solver = new Solver(board, pieces);

        if (solver.solve()){
            board.print();
            System.out.println("solved");
        } else {
            System.out.println("failed");
        }
        //board.place(PentominoColor.LGREEN.getPentomino(), 0,0);
        //board.print();

        //PentominoColor.LGREEN.getPentomino().print();
        //board.remove(PentominoColor.LGREEN.getPentomino(), 0, 0);
        //System.out.println(board.place(PentominoColor.RED.getPentomino(), 0,0));
        //board.print();
        //PentominoColor.LGREEN.getPentomino().rotate90();
        //PentominoColor.LGREEN.getPentomino().rotate90();
        //PentominoColor.LGREEN.getPentomino().rotate90();

        // after changes in Pentomino rotate90() method the filledCells are not changing but a new int[][] of cells is returned

        //board.place(PentominoColor.LGREEN.getPentomino(), 0,0);
//        Pentomino piece = PentominoColor.LGREEN.getPentomino();
//
//        board.place(piece,0,0);
//        board.print();
    }
}
