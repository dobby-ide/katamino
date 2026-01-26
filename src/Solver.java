
import java.util.*;
import java.util.stream.Collectors;

//the List of pieces will shrink or grow depending on which depth level the traversal is.

public class Solver {
    private final Board board;
    private final List<PentominoColor> remainingPieces;

    private final List<Placement> allPlacements;

    private long recursiveCalls = 0;

   public Solver(Board board, List<PentominoColor> remainingPieces) {
       this.board = board;
       this.remainingPieces = remainingPieces;
       this.allPlacements = generateAllPlacements(remainingPieces, board);
   }

   public boolean solve() {
       recursiveCalls++;
       //base case
       if (remainingPieces.isEmpty()) return true;

       for (int i = 0; i < remainingPieces.size(); i++) {


           PentominoColor pieceColor = remainingPieces.get(i);
           Pentomino piece = pieceColor.getPentomino();
           for (int[][] shape : piece.getAllRotations()) {

               for (int row = 0; row < board.getRows(); row++) {
                   for (int col = 0; col < board.getCols(); col++) {

                       if (board.canPlace(piece, shape, row, col)) {

                           board.place(piece, shape, row, col);

                           remainingPieces.remove(i);

                           if (solve()) {
                               System.out.println(recursiveCalls);
                               return true;
                           }

                           board.remove(piece, shape, row, col);
                           remainingPieces.add(i, pieceColor);
                           }
                   }
               }
           }
       }

       return false;
   }

    public boolean solveWithMRV() {
        System.out.println("Remaining pieces: " + remainingPieces);
        System.out.println("Empty cells: " + board.emptyCells().size());

        recursiveCalls++;

        if (remainingPieces.isEmpty()) return true;

        Cell nextCell = chooseMRVCell(board, allPlacements);
        if (nextCell == null) return false;

        List<Placement> validPlacements = placementsForMRVCell(nextCell, remainingPieces, board);

        for (Placement p : validPlacements) {

            if (!board.canPlace(p)) continue;

            board.place(p);

            remainingPieces.remove(p.piece.getColor());

            if (solveWithMRV()) {
                System.out.println("recursive call: " + recursiveCalls);
                return true;
            }

            board.remove(p);
            remainingPieces.add(p.piece.getColor());
        }

        return false;
    }

/*
chooses the Cell with the least possible laying candidates.
* **/

    public Cell chooseMRVCell(Board board, List<Placement> placements){

        int min = Integer.MAX_VALUE;
        List<Cell> candidates = new ArrayList<>();

        for (Cell c : board.emptyCells()){
            int count = 0;

            for (Placement p : placements){

                if (p.covers(c) && remainingPieces.contains(p.piece.getColor()) && board.canPlace(p)){
                    count++;
                }
            }

            if(count == 0) continue;

            if(count < min){
                min = count;
                candidates.clear();
                candidates.add(c);
            } else if(count == min){
                candidates.add(c);
            }
        }

        if(candidates.isEmpty()) return null;

        return candidates.get(new Random().nextInt(candidates.size()));
    }


    /*
    generates all possible placements for each Pentomino on an empty board.
    dramatically reducing the number of pieces that would be tried otherwise, like in a naive solver.

    * **/
    private List<Placement> generateAllPlacements(List<PentominoColor> pieces, Board board){
        List<Placement> placements = new ArrayList<>();
        for (PentominoColor color : pieces){
            Pentomino piece = color.getPentomino();

            List<int[][]> rotations = piece.getAllRotations();
            for (int rotationIndex = 0; rotationIndex < rotations.size(); rotationIndex++) {
                for (int row = 0; row < board.getRows(); row++){
                    for (int col = 0; col < board.getCols(); col++){
                        Placement p = new Placement(color, piece, rotationIndex, new Cell(row, col));

                        if(board.inBounds(p.coveredCells)){
                            placements.add(p);

                        }
                    }
                }
            }

        }
        return placements;
    }
/*
Pentominos shapes in all possible rotations/flips that can fit to lay on a specific chosen "best" cell.
* **/
    private List<Placement> placementsForMRVCell(
            Cell mrvCell,
            List<PentominoColor> remainingPieces,
            Board board) {

        List<Placement> candidates = new ArrayList<>();

        for (PentominoColor color : remainingPieces) {
            Pentomino piece = color.getPentomino();
            List<int[][]> rotations = piece.getAllRotations();

            for (int rotationIndex = 0; rotationIndex < rotations.size(); rotationIndex++) {
                int[][] shape = rotations.get(rotationIndex);


                for (int[] cell : shape) {
                    int r = cell[0];
                    int c = cell[1];

                    int anchorRow = mrvCell.row - r;
                    int anchorCol = mrvCell.col - c;

                    Placement p = new Placement(color, piece, rotationIndex, new Cell(anchorRow, anchorCol));

                    if (p.covers(mrvCell) && board.canPlace(p)) {
                        candidates.add(p);
                    }
                }
            }
        }
        return candidates;
    }



//DLX methods

    public Column buildDLXStructure() {
        int rows = board.getRows();
        int cols = board.getCols();

        Column header = new Column("H");
        Column[] cellColumns = new Column[rows * cols];

        // --- create board cell columns ---
        for (int i = 0; i < cellColumns.length; i++) {
            cellColumns[i] = new Column("C" + i);
            header.linkRight(cellColumns[i]);
        }

        // --- create piece columns to prevent reuse ---
        Map<PentominoColor, Column> pieceColumns = new HashMap<>();
        for (PentominoColor color : remainingPieces) {
            Column c = new Column(color.name());
            header.linkRight(c);
            pieceColumns.put(color, c);
        }

        // --- create nodes for each placement (rows) ---
        for (Placement p : allPlacements) {
            List<Node> rowNodes = new ArrayList<>();

            // --- cell constraints ---
            for (Cell c : p.coveredCells) {
                int colIndex = c.row * board.getCols() + c.col;
                Column col = cellColumns[colIndex];

                Node node = new Node();
                node.column = col;
                node.placement = p;

                col.linkDown(node);
                rowNodes.add(node);

                // optional debug
                System.out.println("Linked node for column " + col.name + " (placement " + p.color.name() + ")");
            }

            // --- piece constraint (prevents reuse) ---
            Column pieceCol = pieceColumns.get(p.color);
            Node pieceNode = new Node();
            pieceNode.column = pieceCol;
            pieceNode.placement = p;
            pieceCol.linkDown(pieceNode);
            rowNodes.add(pieceNode);

            System.out.println("Linked piece node for column " + pieceCol.name + " (placement " + p.color.name() + ")");

            // --- link the row circularly ---
            for (int i = 0; i < rowNodes.size(); i++) {
                Node current = rowNodes.get(i);
                Node next = rowNodes.get((i + 1) % rowNodes.size());
                current.right = next;
                next.left = current;
            }
        }

        return header;
    }






    // algorithm X

    public boolean search(Column header, List<Node> solution){
        recursiveCalls++;
        if(header.right == header){
            printSolution(solution);
            System.out.println("recursive calls with DLX: " + recursiveCalls);
            return true;
        }

        System.out.println("Entering search, solution size: " + solution.size());
        Column c = chooseColumn(header);
        System.out.println("Chosen column: " + c.name);
        c.cover();

        for (Node r = c.down; r != c; r = r.down){
            System.out.println("Trying row for placement: " + r.placement.color.name());
            solution.add(r);

            for (Node j = r.right; j != r; j = j.right){
                j.column.cover();
            }

            if(search(header, solution)){
                return true;
            };
            System.out.println("Backtracking from placement: " + r.placement.color.name());
            solution.remove(solution.size() - 1);

            for (Node j = r.left; j != r; j = j.left){
                j.column.uncover();
            }
        }

        c.uncover();
        return false;
    }

    public Column chooseColumn(Column header){
        int min = Integer.MAX_VALUE;
        Column smallerColumn = null;

        for (Column c = (Column) header.right; c != header; c = (Column) c.right){
            if(c.size < min){
                min = c.size;
                smallerColumn = c;
            }
        }

        return smallerColumn;
    }

    public void printSolution(List<Node> solution) {
        // create empty board display
        char[][] displayBoard = new char[board.getRows()][board.getCols()];
        for (int r = 0; r < board.getRows(); r++) {
            Arrays.fill(displayBoard[r], '.'); // empty cell
        }

        // fill board with piece symbols
        for (Node n : solution) {
            Placement p = n.placement;
            char symbol = p.color.getSymbol(); 
            for (Cell c : p.coveredCells) {
                displayBoard[c.row][c.col] = symbol;
            }
        }

        // print the board
        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getCols(); c++) {
                System.out.print(displayBoard[r][c] + " ");
            }
            System.out.println();
        }
    }

    public static void linkRight(Node a, Node b) {
        b.right = a.right;
        b.left = a;

        a.right.left = b;
        a.right = b;
    }


}
