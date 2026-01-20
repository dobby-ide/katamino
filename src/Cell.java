import java.util.Objects;

// a simple class that understands the coords for each cell in the board
//useful to interact with Placement
public class Cell {
    public final int row;
    public final int col;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return row == cell.row && col == cell.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ')';
    }
}
