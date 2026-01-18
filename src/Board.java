public class Board {
    private int rows, cols;
    private char[][] grid;

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

    public boolean place(Pentomino p, int startRow, int startCol){
        if (!canPlace(p,startRow,startCol)){
            return false;
        }
        for (int[] cell : p.getFilledCells()){
            int r = startRow + cell[0];
            int c = startCol + cell[1];
            grid[r][c] = p.getColor().name().charAt(0);
        }
        return true;
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
