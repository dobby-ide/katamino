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

    public int[][] getFilledCells(){
        return filledCells;
    }

    public PentominoColor getColor(){
        return color;
    }
}
