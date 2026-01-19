public class Board {
    private final int rows, cols;
    private final char[][] grid;

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
            if (grid[r][c] != '\u0000') {
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
            if (grid[r][c] != '\u0000') return false;
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

    public void remove(Pentomino p, int startRow, int startCol) {

        char symbol = p.getColor().getSymbol();

        for (int[] cell : p.getFilledCells()) {

            int r = startRow + cell[0];
            int c = startCol + cell[1];


            if (grid[r][c] == symbol) {
                grid[r][c] = '\u0000';
            }
        }
    }

    //overloaded remove

    public void remove(Pentomino p, int[][] shape, int startRow, int startCol) {
        for (int[] cell : shape) {
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = '\u0000';
        }
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

                char toPrint = (grid[r][c] == '\u0000') ? '.' : grid[r][c];
                System.out.print("[ "+toPrint + " ]");
            }
            System.out.println();
        }
        System.out.println();
    }



}
