import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int rows, cols;
    private final char[][] grid;

    public static final char EMPTY = '\u0000';


    public Board(int rows, int cols){

        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];


    }

    public boolean canPlace(Pentomino p, int startRow, int startCol){
        for (int[] cell : p.getFilledCells()){
            int r = startRow + cell[0];
            int c = startCol + cell[1];

            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                return false;
            }
            if (grid[r][c] != EMPTY) {
                return false;
            }
        }
        return true;
    }

    //overloaded canPlace to use in the solver
    public boolean canPlace(Pentomino p, int[][] shape, int startRow, int startCol) {
        for (int[] cell : shape) {
            int r = startRow + cell[0];
            int c = startCol + cell[1];

            if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
            if (grid[r][c] != EMPTY) return false;
        }
        return true;
    }

    // overloaded canPlace to use with RMV, this time there is no need to check a starting position
    // on grid.row and grid.col because later we will check all possible positions for each pentomino and store them.

    public boolean canPlace(Placement p){
        for (Cell c : p.coveredCells){
            if (!inBounds(c.row,c.col)) {
                System.out.println("Cannot place " + p.piece.getColor() + " at " + c.row + "," + c.col + " -> out of bounds");
                return false;
            }
            if(grid[c.row][c.col] != EMPTY){
                System.out.println("Cannot place " + p.piece.getColor() + " at " + c.row + "," + c.col + " -> occupied by " + grid[c.row][c.col]);

                return false;
            }
        }

        return true;
    }
    //helper for canPlace(Placement p)
    public boolean inBounds(int r, int c){
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public boolean inBounds(List<Cell> cells) {
        for (Cell c : cells) {
            if (!inBounds(c.row, c.col)) return false;
        }
        return true;
    }


    public boolean place(Pentomino p, int startRow, int startCol){
        if (!canPlace(p,startRow,startCol)){
            return false;
        }
        for (int[] cell : p.getFilledCells()){
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = p.getColor().getSymbol();
        }
        return true;
    }

    //overloaded place() to use in Solver
    public boolean place(Pentomino p, int[][] shape, int startRow, int startCol) {
        if (!canPlace(p, shape, startRow, startCol)) return false;

        for (int[] cell : shape) {
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = p.getColor().getSymbol(); // or any representation
        }
        return true;
    }

    //overlad place to be used with solveMRV

    public boolean place(Placement p) {

        if (!canPlace(p)) return false;


        for (Cell c : p.coveredCells) {
            grid[c.row][c.col] = p.piece.getColor().getSymbol();
        }

        return true;
    }


    public void remove(Pentomino p, int startRow, int startCol) {

        char symbol = p.getColor().getSymbol();

        for (int[] cell : p.getFilledCells()) {

            int r = startRow + cell[0];
            int c = startCol + cell[1];


            if (grid[r][c] == symbol) {
                grid[r][c] = EMPTY;
            }
        }
    }

    //overloaded remove

    public void remove(Pentomino p, int[][] shape, int startRow, int startCol) {
        for (int[] cell : shape) {
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = EMPTY;
        }
    }

    //overload remove to be used with solveMRV

    public void remove(Placement p) {
        for (Cell c : p.coveredCells) {
            grid[c.row][c.col] = EMPTY;
        }
    }

    // this method simply returns the list of empty cells in the board, which later will be used against all possible pentomino positions and rotations.
    public List<Cell> emptyCells (){
        List<Cell> list = new ArrayList<>();
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                if (grid[r][c] == EMPTY){
                    list.add(new Cell(r, c));
                }
            }
        }
        return list;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void print() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                char toPrint = (grid[r][c] == EMPTY) ? '.' : grid[r][c];
                System.out.print("[ "+toPrint + " ]");
            }
            System.out.println();
        }
        System.out.println();
    }



}
