# Sudoku Solver using Constraint Satisfaction Problem  
**By: William Kopec**  
This program uses Constraint Satisfaction Problem to solve any possible sudoku solution. It uses consistency checking (Function: isPossibleValue) and backtracking (Function: solveSudokuBoard) to efficiently solve the board. A helper method called isNumeric() is used to check whether the value at some position on the board is a valid value (1-9), otherwise set the value as an empty position (0).
  
  
  
HOW TO RUN:
------------  
(in order for this to run locally on your computer you must install Java jdk and set the appropriate paths in your environment variables)
1. Fill in the unsolved sudoku board in the file board.txt - (where " " indicates empty space and 1-9 indicates a valid number)
2. Open command prompt or Windows Powershell (Your choice)
3. Navigate to the proper project folder directory (In this case, ../SudokuSolverCS411)
4. Type the following command: javac Main.java
5. Then type command: java Main
6. Done, solution output into "solution.txt"
  
Cant run it locally?  
Use my Replit link to test it out - https://replit.com/@wily808/SudokuSolver#Main.java
