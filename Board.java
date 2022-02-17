import java.util.stream.*;
import java.util.Arrays;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class Board {
  static int[][] startState = {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
  };
  static int[][] currentState = {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
  };
    

  public Board(String BoardTxtFile){
    //read in the input file "board.txt" and input the start board into
    //startState and set currentState to startState.
    try{
      BufferedReader br = new BufferedReader(new FileReader("board.txt"));
      int rowIndex = 0;
      int colIndex = 0;
        for (String line; (line = br.readLine()) != null;) {
          String[] variables = line.split("");
          for (int i = 1; i < variables.length; i+=2){
              if(isNumeric(variables[i])){
                char theNumber = variables[i].charAt(0);
                int thisNumber = Character.getNumericValue(theNumber);
                startState[rowIndex][colIndex] = thisNumber;
              } else {
                startState[rowIndex][colIndex] = 0;
              }
              
              //System.out.println(rowIndex + " " + colIndex);
              colIndex++;
          }
          colIndex = 0;
          rowIndex++;
        }
        br.close();
    } catch (IOException e){
      
    }
    currentState = startState;
    
  }
  /*converts a string to a number and checks if it is numeric.
  This function is used to determine whether the the value
  at some position is not an unknown (has a value != 0, in this case value != " ")*/
  public static boolean isNumeric(String str) { 
  try {  
    Double.parseDouble(str);  
    return true;
  } catch(NumberFormatException e){
    return false;  
  }  
}
  //printBoard function:
  //Used to return the currentState in the proper output
  //format and print the board on the screen.
  public static String printBoard(int[][] currentState2){
    String curBoard2 = "-------------------\n";
    String curBoard = "";
    for (int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        if(currentState2[i][j] != 0){
          if(j == 0){
            curBoard += "|" + currentState2[i][j] + "|";
          } else {
            curBoard += currentState2[i][j] + "|";
          }
        } else {
          if(j == 0){
            curBoard += "| |";
          } else {
            curBoard += " |";
          }
        }
        
        
      }
      curBoard += "\n";
    }
    curBoard2 += curBoard;
    curBoard2 += "--------------------";
    System.out.println(curBoard2);
    return curBoard;
  }
  
  //Backtracking/Searching function:
  static boolean solveSudokuBoard(int[][] curState){
    //Set default position on the board
    int curRow = 0;
    int curCol = 0;
    //Set isDone to true by default
    boolean isDone = true;
    //Check each position on the board until you reach an unknown value (0)
    //at some position (i, j). When we find an unknown, set current position
    //and isDone to false and continue...
    for(int i = 0; i < 9; i++){
      for(int j = 0; j < 9; j++){
        if(curState[i][j] == 0){
          curRow = i;
          curCol = j;
          isDone = false;
          break;
        }
      }
      if(isDone == false){
        break;
      }
    }
    /*
    If we found no unknown values on the board,
    return true/write the currentState (solution)
    */
    if(isDone == true){
      try {
        FileWriter myWriter = new FileWriter("output.txt");
        myWriter.write(printBoard(currentState));
        myWriter.close();
        System.out.println("Successfully output solution into output.txt");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      return true;
    }
    //Check each possible value (1-9) for the current position (Search)
    for (int possibleVal = 1; possibleVal < 10; possibleVal++){
      //(Checking vertical columns (1-9), horizontal rows [1-9], and 
      //the current individual 3x3 grid at that position)
      if(isPossibleValue(curState, curRow, curCol, possibleVal)){
        //if possibleVal is a valid value at the position, set it to that value
        curState[curRow][curCol] = possibleVal;
        if (solveSudokuBoard(curState)){
          //recursively call function to check each possibility of the 
          //current board's state until the solution is found. 
          return true;
        } else { //otherwise if constraint was broken, backtrack on the last 
          //position (reset to 0 and continue iterating through possible values)
          curState[curRow][curCol] = 0;
        }
      }
    }
    return false;
  }

  /*
  Consistency check function: Uses value to check at curRow, curCol and valueToCheck to
  check the current position and if valueToCheck is a possible value in that row, column 
  and its individual 3x3 grid.
  Constraints: for each position at [r^n][c^m] where n and m specify curRow and curCol, 
  The value being checked at that position must not take a value within any position in 
  it's current row [r^n], it's column [c^m], or 
  current 3x3 grid [r^n-(r^n%3)][c^m-(c^m%3)].
  */
  static boolean isPossibleValue(int[][] curState, int curRow, int curCol, int valueToCheck){
    //Check the row vertically, if the value already exists at the row index in a different column, return false - Value cannot be possible if one already exists
    for(int i = 0; i <= 8; i++){
      if(curState[curRow][i] == valueToCheck){
        return false;
      }
    }
    //Check the columns horizontally to see if value already exists in the row, if it does return false.
    for(int i = 0; i < 8; i++){
      if(curState[i][curCol] == valueToCheck){
        return false;
      }
    }
    //get the start row and column of the current 3x3 grid at current position (curRow, curCol)
    int startRow = curRow - curRow % 3;
    int startCol = curCol - curCol % 3;
    //Check the 3x3 grid at the position to make sure it is a valid value
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 3; j++){
        if(curState[i + startRow][j + startCol] == valueToCheck){
          return false;
        }
      }
    }
    //if we get here, none of the constraints were violated, return true
    return true;
  }



}