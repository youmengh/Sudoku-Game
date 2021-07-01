package sudokugame;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

/**
 * Determines valid puzzles from Data folder and initializes random valid puzzle
 * keeps count of invalid puzzles
 *
 * @author Youmeng Hin
 * @version April 2020
 */
public class PuzzlePool {

    private Stack<SudokuPuzzle> puzzlePool;
    //    private ArrayList<SudokuPuzzle> puzzlePool;       //Previous code declaring the puzzle pool as an ArrayList object type SudokuPuzzle
    private int invalidFiles;

    /**
     * Constructor initializes puzzlePool and invalidFiles and loads files from
     * data folder
     */
    public PuzzlePool() {
        puzzlePool = new Stack();
        invalidFiles = 0;
        loadFiles(Paths.get(".\\Data"));
    }

    /**
     * Recursively loads all the files in the given path into the files array
     *
     * @param path the current file path
     */
    private void loadFiles(Path path) {
        File[] tempFiles;
        File aFile;

        //Dump all the path contents into the local File array, tempFiles
        //e.g., all files and folders in the Data folder are collected
        aFile = new File(path.toString());
        tempFiles = aFile.listFiles();

        SudokuPuzzle puzzle;

        for (File file : tempFiles) {
            if (file.isFile()) { //base case, file is a text file
                puzzle = new SudokuPuzzle();
                if (puzzle.initializePuzzle(file))//if the puzzle is valid
                {
                    puzzlePool.add(puzzle); //adds valid puzzle to puzzle pool
                } else {
                    invalidFiles++; //counts invalid puzzles
                }
            } else if (file.isDirectory()) //general base
            {
                loadFiles(Paths.get(file.toString()));
            }
        }
    }

    /**
     * Getter method that gets a random valid puzzle from the puzzlePool
     *
     * @return the random valid puzzle from the puzzlePool
     */
    public SudokuPuzzle getPuzzle() {
        Random rand = new Random();
        return puzzlePool.get(rand.nextInt(puzzlePool.size()));
    }

    /**
     * A to String method that prints the total number of files and the number
     * of invalid files in the data folder
     *
     * @return The number of invalid and total files in the data folder
     */
    @Override
    public String toString() {
        return "The total number of files is : " + (puzzlePool.size() + invalidFiles)
                + "\nThe total number of invalid files is : " + invalidFiles;

    }

    //Iterator is being used here
    /**
     * Using iterator to print the path for all valid puzzles.
     *
     * @return a list of puzzle paths
     */
    public String printPuzzles() {
        String str = "\n";
        Iterator<SudokuPuzzle> it = puzzlePool.iterator();
        int count = 1;
        while (it.hasNext()) {
            str += (count) + ". " + (it.next().getPath()) + "\n";
            count++;
        }
        return str;
    }
}
