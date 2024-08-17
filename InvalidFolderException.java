/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

/**
 * Exception thrown when there is an error with the folder
 */
public class InvalidFolderException extends Exception {
    public InvalidFolderException(String message) {
        super(message);
    }
}
