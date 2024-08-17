/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

import java.util.*;

/**
 * Implements the comparator interface to compare the emails
 */
public class EmailComparator implements Comparator<Email> {
    private String field;

    /**
     * Constructor for the email comparator
     * @param field of the emails you want to compare by
     */
    public EmailComparator(String field) {
        this.field = field;
    }

    /**
     * Gets the field of the emails you want to compare by
     * @return the field of the email you want to compare by
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the field of the emails you want to compare by
     * @param field of the emails you want to compare by
     * @throws InvalidEmailException if the field of the email you want to compare by is invalid
     */
    public void setField(String field) throws InvalidEmailException{
        if (field == null) {
            throw new InvalidEmailException("The field to compare cannot be null");
        } else if (field.trim().isEmpty()) {
            throw new InvalidEmailException("The field to compare cannot be empty");
        }
        this.field = field;
    }

    /**
     * Compares the emails you want to compare by the specified field
     * @param email1 the first object to be compared.
     * @param email2 the second object to be compared.
     * @return a negative integer if email1 is greater, zero if the emails are equal, a positive number if email2 is greater
     */
    @Override
    public int compare(Email email1, Email email2) {
        return switch (field) {
            case "subjectAscending" -> email1.getSubject().compareTo(email2.getSubject());
            case "subjectDescending" -> email2.getSubject().compareTo(email1.getSubject());
            case "dateAscending" -> email1.getTimestamp().compareTo(email2.getTimestamp());
            default -> email2.getTimestamp().compareTo(email1.getTimestamp());
        };
    }

    /**
     * Indicates whether the object is equal to the comparator
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the object argument. False is this object is not the same as the object argument
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmailComparator that = (EmailComparator) o;
        return Objects.equals(field, that.field);
    }

    /**
     * A string representation of the EmailComparator object
     * @return A string representation of the EmailComparator object
     */
    @Override
    public String toString() {
        return "EmailComparator{" +
                "field='" + field + '\'' +
                '}';
    }
}
