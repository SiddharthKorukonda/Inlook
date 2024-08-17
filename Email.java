/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

import java.util.*;
import java.io.*;

/**
 * Represents an email with fields for recipient (to), carbon copy (cc), blind carbon copy (bcc), subject, body, and a timestamp.
 */
public class Email implements Serializable {
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
    private GregorianCalendar timestamp;

    /**
     * Constructor for the email
     * @param to field of the email
     * @param cc field of the email
     * @param bcc field of the email
     * @param subject of the email
     * @param body of the email
     * @param timestamp of the email
     * @throws InvalidEmailException if the components of the email are invalid
     */
    public Email(String to, String cc, String bcc, String subject, String body, GregorianCalendar timestamp) throws InvalidEmailException {
        setTo(to);
        setCc(cc);
        setBcc(bcc);
        setSubject(subject);
        setBody(body);
        setTimestamp(timestamp);
    }

    /**
     * Gets the to field of the email
     * @return to field
     */
    public String getTo() {
        return to;
    }

    /**
     * Gets the cc field of the email
     * @return cc field
     */
    public String getCc() {
        return cc;
    }

    /**
     * Gets the bcc field of the email
     * @return bcc field
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * Gets the subject of the email
     * @return subject of the email
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Gets the body of the email
     * @return body of the email
     */
    public String getBody() {
        return body;
    }

    /**
     * Gets the timestamp of the email
     * @return timestamp of the email
     */
    public GregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the to field of the email
     * @param to field of the email
     * @throws InvalidEmailException if the "to" field of the email is null, empty, or contains invalid email address(es)
     */
    public void setTo(String to) throws InvalidEmailException {
        validateEmails(to, "To");
        this.to = to;
    }

    /**
     * Sets the cc field of the email
     * @param cc field of the email
     * @throws InvalidEmailException if the "cc" field of the email is null, empty, or contains invalid email address(es)
     */
    public void setCc(String cc) throws InvalidEmailException {
        if (cc != null && !cc.trim().isEmpty()) {
            validateEmails(cc, "Cc");
        }
        this.cc = cc;
    }

    /**
     * Sets the bcc field of the email
     * @param bcc field of the email
     * @throws InvalidEmailException if the "bcc" field of the email is null, empty, or contains invalid email address(es)
     */
    public void setBcc(String bcc) throws InvalidEmailException {
        if (bcc != null && !bcc.trim().isEmpty()) {
            validateEmails(bcc, "Bcc");
        }
        this.bcc = bcc;
    }

    /**
     * Sets the subject of the email
     * @param subject of the email
     * @throws InvalidEmailException if the subject of the email is null or empty
     */
    public void setSubject(String subject) throws InvalidEmailException {
        if (subject == null) {
            throw new InvalidEmailException("The subject cannot be null");
        } else if (subject.trim().isEmpty()) {
            throw new InvalidEmailException("The subject cannot be empty");
        }
        this.subject = subject;
    }

    /**
     * Sets the body of the email
     * @param body of the email
     * @throws InvalidEmailException if the body of the email is null or empty
     */
    public void setBody(String body) throws InvalidEmailException {
        if (body == null) {
            throw new InvalidEmailException("The body cannot be null");
        } else if (body.trim().isEmpty()) {
            throw new InvalidEmailException("The body cannot be empty");
        }
        this.body = body;
    }

    /**
     * Sets the timestamp for the email
     * @param timestamp of the email
     * @throws InvalidEmailException if the timestamp of the email is null or in the future
     */
    public void setTimestamp(GregorianCalendar timestamp) throws InvalidEmailException {
        if (timestamp == null) {
            throw new InvalidEmailException("Timestamp cannot be null");
        }
        if (timestamp.after(new GregorianCalendar())) {
            throw new InvalidEmailException("Your timestamp is set in the future");
        }
        this.timestamp = timestamp;
    }

    /**
     * Method to check whether the emails are valid or not
     * @param emails in the to, cc, and bcc fields
     * @param fieldName of the place where the email is supposed to be entered
     * @throws InvalidEmailException if the email entered is invalid
     */
    private void validateEmails(String emails, String fieldName) throws InvalidEmailException {
        if (emails == null) {
            throw new InvalidEmailException("The \"" + fieldName + "\" field cannot be null");
        } else if (emails.trim().isEmpty()) {
            throw new InvalidEmailException("The \"" + fieldName + "\" field cannot be empty");
        }

        String[] emailArray = emails.split(",");

        for (int i = 0; i < emailArray.length; i++) {
            String email = emailArray[i].trim();
            if (!email.contains("@")) {
                throw new InvalidEmailException("The \"" + fieldName + "\" field contains an email without '@': " + email);
            } else if (email.lastIndexOf('.') < email.indexOf('@') + 2) {
                throw new InvalidEmailException("The \"" + fieldName + "\" field contains an email without a valid domain: " + email);
            }
        }
    }
}
