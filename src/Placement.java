import java.util.ArrayList;
import java.util.List;

// represents one specific way to place a Pentomino
public class Placement {
    public final PentominoColor color;
    public final Pentomino piece;

    public final int rotationIndex;

    public final Cell anchor;
    public final List<Cell> coveredCells;

    public Placement(PentominoColor color, Pentomino piece, int rotationIndex, Cell anchor) {
        this.color = color;
        this.piece = piece;
        this.rotationIndex = rotationIndex;
        this.anchor = anchor;
        this.coveredCells = computeCoveredCells();
    }

    private List<Cell> computeCoveredCells() {


        List<Cell> cells = new ArrayList<>();
        int[][] shape = piece.getAllRotations().get(rotationIndex);
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] != 0) { // occupied
                    cells.add(new Cell(anchor.row + r, anchor.col + c));
                }
            }
        }
        return cells;
    }
}
