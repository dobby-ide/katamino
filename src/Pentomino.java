import java.util.ArrayList;
import java.util.List;

public class Pentomino {

    private final PentominoColor color;
    private final int[][] filledCells;
    public Pentomino(PentominoColor color, boolean[][] shape){
        this.color = color;
        List<int[]> coords = new ArrayList<>();
        for (int r = 0; r < shape.length; r++){
            for (int c = 0; c < shape[r].length; c++){
                if (shape[r][c]) coords.add(new int[]{r, c});
            }
        }
        this.filledCells = coords.toArray(new int[0][0]);
    }


    //rotate90() needs to "understand" the height of a piece before it is rotated.
    // The formula for rotating of 90deg in a matrix is as follows: (r, c) --> (c, height - 1 - r)
    // a minRow and a maxRow will keep the height value normalized as in the method
    public void rotate90(){
        // not an in-place routine so a new 2D array must be created to save the rotated Pentomino
        int[][] rotated = new int[filledCells.length][2];

        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;

        for (int[] cell : filledCells){
            minRow = Math.min(minRow, cell[0]);
            maxRow = Math.max(maxRow, cell[0]);
        }
        int height = maxRow - minRow + 1;

        for (int i = 0; i < filledCells.length; i++){
            int r = filledCells[i][0];
            int c = filledCells[i][1];

            //new column and new row given by the formula
            rotated[i][0] = c;
            rotated[i][1] = height - 1 - r;
        }

        //normalizing so top-left is in its correct position
//        int minRotRow = Integer.MAX_VALUE;
//        int minRotCol = Integer.MAX_VALUE;
//        for (int[] cell : rotated) {
//            minRotRow = Math.min(minRotRow, cell[0]);
//            minRotCol = Math.min(minRotCol, cell[1]);
//        }
//        for (int[] cell : rotated) {
//            cell[0] -= minRotRow;
//            cell[1] -= minRotCol;
//        }

        for (int i = 0; i < filledCells.length; i++) {
            filledCells[i][0] = rotated[i][0];
            filledCells[i][1] = rotated[i][1];
        }
    }

    public int[][] getFilledCells(){
        return filledCells;
    }

    public PentominoColor getColor(){
        return color;
    }

    public void print() {
        for (int[] cell : filledCells) {
            System.out.println("(" + cell[0] + ", " + cell[1] + ")");
        }
    }
}
