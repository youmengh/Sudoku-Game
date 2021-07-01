package sudokugame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Simulates a Sudoku puzzle. Sudoku is a popular puzzle that uses 9x9 array of
 * squares that are organized into 3x3 subarrays. The puzzle solver must fill in
 * the squares with the digits 1 to 9 such that no digit is repeated in any row,
 * any column, or any of the nine 3x3 subgroups of squares.
 *
 * @author Youmeng Hin
 * @version April 2020
 */
public class SudokuPuzzle {

    private File puzzleFile;    //The file of the puzzle
    private int board[][];      //digits on the puzzle
    private boolean orig[][];   //indicates whether the digit is original or not
    private int size;           //size of the puzzle, typically puzzle is 9 or 9x9

    /**
     * Constructs a new instance of SudokuPuzzle
     */
    public SudokuPuzzle() {
        this(9);
    }

    /**
     * Constructs a SudokuPuzzle using a given size
     *
     * @param size the size of the puzzle
     */
    public SudokuPuzzle(int size) {
        board = new int[size][size];     //array of integers that represents the current state of the puzzle, where 0 indicates a blank square
        orig = new boolean[size][size];  //array of boolean values that indicates which squares in board are given values that cannot be changed
        this.size = size;
    }

    /**
     * Returns a string representation of the puzzle
     *
     * @return the string formatted representation of the puzzle
     */
    @Override
    public String toString() {
        String puzzleString = "    1  2  3  | 4  5  6  | 7  8  9\n";
        puzzleString = puzzleString + "----------------------------------\n";
        for (int i = 0; i < size; i++) {
            puzzleString = puzzleString + (i + 1) + " | ";
            for (int j = 0; j < size; j++) {
                if (board[i][j] == 0) {
                    puzzleString = puzzleString + ".  ";
                } else {
                    puzzleString = puzzleString + board[i][j] + "  ";
                }
                if (j == 2 || j == 5) {
                    puzzleString += "| ";
                }
            }
            puzzleString = puzzleString + "\n";
            if (i == 2 || i == 5) {
                puzzleString += "----------------------------------\n";
            }
        }
        return puzzleString;
    }

    /**
     * Sets the given square to the given value as an initial value that cannot
     * be changed by the puzzle solver
     *
     * @param row   the row number of the square
     * @param col   the column number of the square
     * @param value the initial value for the square
     */
    public void addInitial(int row, int col, int value) {
        if (row >= 0 && row <= size && col >= 0 && col <= size && value >= 1 && value <= size) {
            board[row][col] = value;
            orig[row][col] = true;
        }
    }

    /**
     * Sets the given square to the given value; the value can be changed later
     * by another call to addGuess
     *
     * @param row
     * @param col
     * @param value
     */
    public void addGuess(int row, int col, int value) {
        // only set the value if the orig is false
        if (row >= 0 && row <= size && col >= 0 && col <= size && value >= 1 && value <= size && !orig[row][col]) {
            board[row][col] = value;
        }
    }

    /**
     * Returns true if the values in the puzzle do not violate the restrictions
     *
     * @return true if puzzle does not violate restrictions, false otherwise
     */
    public boolean checkPuzzle() {
        boolean looksGood = true;
        // check if the squares are legal against each row and each column
        for (int i = 0; i < size; i++) {
            looksGood = looksGood && checkRow(i);
            looksGood = looksGood && checkCol(i);
        }

        // check if each 3x3 subarray is legal
        for (int i = 0; i < size; i = i + 3) {
            for (int j = 0; j < size; j = j + 3) {
                looksGood = looksGood && checkSubArray(i, j);
            }
        }

        return looksGood;
    }

    /**
     * Returns the value in the given square
     *
     * @param row the row number for the square
     * @param col the column number for the square
     * @return the value in the given square
     */
    public int getValueIn(int row, int col) {
        return board[row][col];
    }

    /**
     * Returns a one-dimensional array of nine booleans, each of which
     * corresponds to a digit and is true if the digit can be placed in the
     * given square without violating the restrictions
     *
     * @param row the row number for the square
     * @param col the column number for the square
     * @return an array of booleans indicating whether each digit may or may not
     * go into the square
     */
    public boolean[] getAllowedValues(int row, int col) {
        // Save the value at the location, then try all SIZE values
        int savedValue = board[row][col];
        boolean result[] = new boolean[size];

        for (int value = 1; value <= size; value++) {
            board[row][col] = value;
            result[value - 1] = checkPuzzle();
        }

        board[row][col] = savedValue;
        return result;
    }

    /**
     * Sets the initial values in the puzzle base off puzzle files that are
     * deemed valid
     *
     * @return true if a puzzle is valid false otherwise
     */
    public boolean initializePuzzle(File fileName) {
        puzzleFile = fileName;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(puzzleFile));
            String line;
            int row = 0;
            char digit;

            while ((line = reader.readLine()) != null && row < 9) {
                if (line.length() != 9) {
                    throw new PuzzleSizeException("Puzzle Size Invalid");
                }
                for (int col = 0; col < 9; col++) {
                    digit = line.charAt(col);
                    if (Character.isDigit(digit) && Character.getNumericValue(digit) > 0) {
                        addInitial(row, col, Character.getNumericValue(digit));
                    } else if (digit != '.') {
                        throw new InvalidCharacterException("Invalid Character");
                    }
                }
                row++;
            }
            if (row != 9) {
                throw new PuzzleSizeException("Puzzle Size is Invalid");
            } else {
                reader.close();
            }
            return true;

        } catch (IOException e1) {
            System.out.println("IOException");
            return false;
        } catch (InvalidCharacterException e1) {
            System.out.println("Invalid Character During Initialization");
            return false;
        } catch (PuzzleSizeException e1) {
            System.out.println("Invalid Puzzle Size During Initialization");
            return false;
        }
    }

    /**
     * Returns true if every square has a value
     *
     * @return true if every square is filled (non-zero)
     */
    public boolean isFull() {
        boolean allFilled = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                allFilled = allFilled && board[i][j] > 0;
            }
        }
        return allFilled;
    }

    /**
     * Changes all the non-original squares back to blanks (0s)
     */
    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!orig[i][j]) {
                    board[i][j] = 0;
                }
            }
        }
    }

    /**
     * Checks a row to determine if each number appears only once
     *
     * @param row the row number to check
     * @return true if there is no duplicate digit in the row
     */
    private boolean checkRow(int row) {
        int count[] = new int[10];  //keeps the count of each digit
        for (int col = 0; col < size; col++) {
            count[board[row][col]]++;  //uses the square content as index for count
        }

        boolean countIsOk = true;
        for (int i = 1; i <= size; i++) {
            countIsOk = countIsOk && (count[i] <= 1);  //makes sure there is no more than 1 of any digit in the row
        }
        return countIsOk;
    }

    /**
     * Checks a column to determine if each number appears only once
     *
     * @param col the column number to check
     * @return true if there is no duplicate digit in the column
     */
    private boolean checkCol(int col) {
        int count[] = new int[10];  //keeps the count of each digit
        for (int row = 0; row < size; row++) {
            count[board[row][col]]++;   //uses the square content as index for count
        }

        boolean countIsOk = true;
        for (int i = 1; i <= size; i++) {
            countIsOk = countIsOk && (count[i] <= 1); //makes sure there is no more than 1 of any digit in the column
        }
        return countIsOk;
    }

    /**
     * Checks a 3x3 subarray to determine if each number appears only once
     *
     * @param rowBase the upper-left row number of the subarray
     * @param colBase the upper-left column number of the subarray
     * @return true if there is no duplicate digit in the subarray
     */
    private boolean checkSubArray(int rowBase, int colBase) {
        int count[] = new int[10];  //keeps the count of each digit
        boolean countIsOk = true;

        //count the number of each digit in this subarray
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                count[board[rowBase + i][colBase + j]]++;
            }
        }

        //check that none of the counts are more than 1
        for (int i = 1; i < size; i++) {
            countIsOk &= (count[i] <= 1);
        }

        return countIsOk;
    }

    /**
     * Used to access the file path of the puzzle.
     *
     * @return a string that represents the puzzle path.
     */
    public String getPath() {
        return puzzleFile.getPath();
    }
}
