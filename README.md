# Katamino Solver

Classic Katamino game, each Pentomino is represented through a 2D boolean array, converted into an list of true coords.
Enabling explorations of possible solutions with backtracking and implicit graph + DFS-like traversal.

## Features
- Board representation and piece placement using a 2D grid
- Predefined Pentomino pieces with colors
- Solver using recursive backtracking
- Print the board in console


<img src="assets/pentomino1.png" alt="Pentomino example" width="300"/>
<img src="assets/pentomino2.png" alt="Board example" width="300"/>

------------------
20.1
The solver attempts to place one piece at a time on the board.
For each piece, all possible orientations are stored and normalized.
The algorithm backtracks and tries the next possibility if a placement leads to a dead end.

<img src="assets/pentomino3.png" alt="Pentomino example" width="300"/>
<img src="assets/pentomino4.png" alt="Board example" width="300"/>


