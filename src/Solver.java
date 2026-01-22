
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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






}
