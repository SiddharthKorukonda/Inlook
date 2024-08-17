/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

/**
 * Exception thrown when there is an error with the mailbox
 */
public class InvalidMailboxException extends Exception {
    public InvalidMailboxException(String message) {
        super(message);
    }
}
