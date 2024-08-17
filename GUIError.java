/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

/**
 * Exception thrown if there is an error with the GUI
 */
public class GUIError extends Exception {
    public GUIError(Exception message) {
        super(message);
    }
}
