
import java.util.List;

//the List of pieces will shrink or grow depending on which depth level the traversal is.

public class Solver {
    private final Board board;
    private final List<PentominoColor> remainingPieces;

   public Solver(Board board, List<PentominoColor> remainingPieces) {
       this.board = board;
       this.remainingPieces = remainingPieces;
   }

   /*
   example structure for a backtracking recursion:
   for piece in remainingPieces
        for rotation in pieceRotations
            for row in boardRows
                for col in boardCols
                    if canPlace(piece, row, col):
                        place(piece, row, col)
                        remove piece from remainingPieces
                        solve()
                    else:
                        undo placement
                        add piece back
   **/
   public boolean solve(){
       //base case
       if(remainingPieces.isEmpty()) return true;

       for(int i = 0; i < remainingPieces.size(); i++){
           PentominoColor pieceColor = remainingPieces.get(i);
           Pentomino piece = pieceColor.getPentomino();

           // iterating over board positions next is more natural even though I want to try caching rotations
           // that avoids duplicate checks.
           for(int row = 0; row < board.getRows(); row++){
               for(int col = 0; col < board.getCols(); col++){
                   int[][] shapeToTry = piece.getFilledCells();
                    for (int rot = 0; rot < 4; rot++){
                        if(board.canPlace(piece,shapeToTry,row,col)){
                            board.place(piece, shapeToTry, row, col);

                            remainingPieces.remove(i);

                            if(solve()) return true;

                            board.remove(piece, shapeToTry, row, col);

                            remainingPieces.add(i, pieceColor);
                        }

                        shapeToTry = piece.rotate90(shapeToTry);
                    }
               }
           }
       }

       return false;
   }


}
