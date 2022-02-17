class Main {
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    Board thisBoard = new Board("board.txt");


    thisBoard.printBoard(thisBoard.startState);
    if(thisBoard.solveSudokuBoard(thisBoard.currentState))    {
      //Solution Found! Board will print out/write to 
      //output file
    } else {
      System.out.println("No solution!");
    }
    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println("Total run-time: " + elapsedTime + " milliseconds");
  }
}