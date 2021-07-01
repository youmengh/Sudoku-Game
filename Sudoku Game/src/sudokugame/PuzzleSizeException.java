package sudokugame;

/**
 * Exception for an invalid character in the initialized puzzle
 *
 * @author Youmeng Hin
 * @version April 2020
 */
public class PuzzleSizeException extends ArrayIndexOutOfBoundsException {

    /**
     * Exception for an invalid puzzle size
     *
     * @param message the error message for ArrayIndexOutOfBoundsException
     */
    PuzzleSizeException(String message) {
        super(message);
    }
}
