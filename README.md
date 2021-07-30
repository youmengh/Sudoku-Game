# Sudoku-Game
 A 9x9 text-based sudoku game written in Java

Introduction:
The following Java project is a sudoku progra that contains up to 30 9x9 puzzles.
Each puzzle is unique and can be solved by having the player input correct numbers into their designated cells.
Player can also choose to "clear puzzle" by input the character "c".
The game will then choose a new 9x9 puzzle at random from "Data\" directory.
On top of that, the player can choose option "g" to get all possible values in a desired cell given the current state of the puzzle.

Some hidden background features within the programs include InvalidCharcterException and PuzzleSizeException.
    InvalidCharacterException - A custom exception created to detect any invlid character within each puzzle file. A file is marked as "invalid" if any character other than 1-9 exists in the puzzle file.
    PuzzleSizeException - A custom exception created to make sure each puzzle file has a 9x9 puzzle in it, nothing larger nothing smaller. A file is marked as "invalid" if the puzzle within has incorrect dimensions.
