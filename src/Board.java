public class Board {
    private int rows, cols;
    private char[][] grid;

    public Board(int rows, int cols){

        this.rows = rows;
        this.cols = cols;
        grid = new char[rows][cols];


    }

    public boolean canPlace(Pentomino p, int startRow, int startCol){

        return true;
    }

    public void place(Pentomino p, int startRow, int startCol){
        for (int[] cell : p.getFilledCells()){
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = p.getColor().name().charAt(0);
        }
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
