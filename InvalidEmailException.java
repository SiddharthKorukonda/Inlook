/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

/**
 * Exception thrown when there is an error with the email
 */
public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
