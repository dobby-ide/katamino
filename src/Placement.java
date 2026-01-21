import java.util.ArrayList;
import java.util.List;

// represents one specific way to place a Pentomino on the board, The Solver class is using it to generate as many Pentamino anchored (anchor) to a place on the board
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

        for (int[] cell : shape) {

            int boardRow = anchor.row + cell[0];
            int boardCol = anchor.col + cell[1];
            cells.add(new Cell(boardRow, boardCol));
        }

        return cells;
    }

    public boolean covers(Cell c) {
        for (Cell cell : coveredCells) {
            if (cell.row == c.row && cell.col == c.col) return true;
        }
        return false;
    }

}
