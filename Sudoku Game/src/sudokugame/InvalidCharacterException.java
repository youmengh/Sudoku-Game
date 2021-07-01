package sudokugame;

import java.util.InputMismatchException;

/**
 * Exception for an invalid character in the puzzle initialization
 *
 * @author Youmeng Hin
 * @version April 2020
 */
public class InvalidCharacterException extends InputMismatchException {

    /**
     * Exception for characters that are not between 1-9
     *
     * @param message the error message of InputMismatchException
     */
    InvalidCharacterException(String message) {
        super(message);
    }
}
