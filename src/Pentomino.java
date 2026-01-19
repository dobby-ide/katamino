import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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


    public List<int[][]> getAllRotations(){
        List<int[][]> orientations = new ArrayList<>();

        int base[][] = normalize(copy(filledCells));

        int[][] current = base;
        for(int i = 0; i < 4; i++){
            addIfUnique(orientations, current);
            current = rotate90(current);
        }

        int flipped[][] = flip(base);
        current = flipped;
        for (int i = 0; i < 4; i++){
            addIfUnique(orientations, current);
            current = rotate90(current);
        }

        return orientations;
    }
    private int[][] normalize(int[][] shape) {
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;
        for (int[] c : shape) {
            minRow = Math.min(minRow, c[0]);
            minCol = Math.min(minCol, c[1]);
        }
        for (int[] c : shape) {
            c[0] -= minRow;
            c[1] -= minCol;
        }
        return shape;
    }

    private int[][] copy(int[][] shape) {
        int[][] c = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            c[i][0] = shape[i][0];
            c[i][1] = shape[i][1];
        }
        return c;
    }

    private void addIfUnique(List<int[][]> list, int[][] shape) {
        for (int[][] existing : list) {
            if (sameShape(existing, shape)) {
                return;
            }
        }
        list.add(copy(shape));
    }

    private boolean sameShape(int[][] a, int[][] b) {
        if (a.length != b.length) return false;
        Set<String> sa = new HashSet<>();
        Set<String> sb = new HashSet<>();
        for (int[] c : a) sa.add(c[0] + "," + c[1]);
        for (int[] c : b) sb.add(c[0] + "," + c[1]);
        return sa.equals(sb);
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

    private int[][] flip(int[][] shape) {
        int minCol = Integer.MAX_VALUE;
        int maxCol = Integer.MIN_VALUE;
        for (int[] c : shape) {
            minCol = Math.min(minCol, c[1]);
            maxCol = Math.max(maxCol, c[1]);
        }
        int width = maxCol - minCol + 1;
        int[][] flipped = new int[shape.length][2];
        for (int i = 0; i < shape.length; i++) {
            int r = shape[i][0];
            int c = shape[i][1];
            flipped[i][0] = r;
            flipped[i][1] = width - 1 - c;
        }
        return normalize(flipped);
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
