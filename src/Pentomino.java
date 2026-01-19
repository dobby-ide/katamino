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
    public int[][] rotate90(int[][] cells) {
        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;

        for (int[] c : cells) {
            minRow = Math.min(minRow, c[0]);
            maxRow = Math.max(maxRow, c[0]);
        }
        int height = maxRow - minRow + 1;

        int[][] rotated = new int[cells.length][2];
        for (int i = 0; i < cells.length; i++) {
            int r = cells[i][0];
            int c = cells[i][1];
            rotated[i][0] = c;
            rotated[i][1] = height - 1 - r;
        }

        return rotated;
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
