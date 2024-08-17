/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

import java.util.*;
import java.io.*;

/**
 * Represents a folder that contains the list of emails
 * You can manipulate the emails in the folder as well as organize them
 */
public class Folder implements Serializable {
    private ArrayList<Email> emails;
    private String name;
    private String currentSortingMethod;

    /**
     * Constructor for the folder
     * @param name of the folder
     */
    public Folder(String name) {
        this.emails = new ArrayList<>();
        this.name = name;
        this.currentSortingMethod = "dateDescending";
    }

    /**
     * Gets the list of emails in the folder
     * @return the list of emails in the folder
     */
    public ArrayList<Email> getEmails() {
        return emails;
    }

    /**
     * Gets the name of the folder
     * @return the name of the email
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current sorting method
     * @return the current sorting method
     */
    public String getCurrentSortingMethod() {
        return currentSortingMethod;
    }

    /**
     * Sets the list of emails in the folder
     * @param emails list
     * @throws InvalidFolderException if the list of emails is null
     */
    public void setEmails(ArrayList<Email> emails) throws InvalidFolderException {
        if (emails == null) {
            throw new InvalidFolderException("Email cannot be null");
        }
        this.emails = emails;
    }

    /**
     * Sets the name of the folder
     * @param name of the folder
     * @throws InvalidFolderException if the name of the folder is either null or empty
     */
    public void setName(String name) throws InvalidFolderException {
        if (name == null) {
            throw new InvalidFolderException("The name field cannot be null");
        } else if (name.trim().isEmpty()) {
            throw new InvalidFolderException("The name field cannot be empty");
        }
        this.name = name;
    }

    /**
     * Sets the current sorting method
     * @param currentSortingMethod of the folder
     * @throws InvalidFolderException if the current sorting method is null or empty
     */
    public void setCurrentSortingMethod(String currentSortingMethod) throws InvalidFolderException {
        if (currentSortingMethod == null) {
            throw new InvalidFolderException("The sorting method cannot be null");
        } else if (currentSortingMethod.trim().isEmpty()) {
            throw new InvalidFolderException("The sorting method cannot be empty");
        }
        this.currentSortingMethod = currentSortingMethod;
    }

    /**
     * Sorts the emails in the folder based on the current sorting method
     */
    public void sortEmails() {
        emails.sort(new EmailComparator(currentSortingMethod));
    }

    /**
     * Adds an email to the folder
     * @param email to be added to the folder
     * @throws InvalidFolderException if the email is null
     */
    public void addEmail(Email email) throws InvalidFolderException {
        if (email == null) {
            throw new InvalidFolderException("Email cannot be null");
        }
        emails.add(email);
        sortEmails();
    }

    /**
     * Removes an email to the folder
     * @param index of the email
     * @return the removed email
     * @throws InvalidFolderException if the index is less than 0 or if the index is greater than the folder size
     */
    public Email removeEmail(int index) throws InvalidFolderException {
        if (index < 0 || index > emails.size()) {
            throw new InvalidFolderException("Index out of bounds");
        }
        return emails.remove(index);
    }

    /**
     * Manually sorts the emails via the comparator
     * @param comparator class
     */
    public void manualSort(Comparator<Email> comparator) {
        for (int i=0; i<emails.size()-1; i++) {
            for (int j=i+1; j<emails.size(); j++) {
                if (comparator.compare(emails.get(i), emails.get(j)) > 0) {
                    Email temp = emails.get(i);
                    emails.set(i, emails.get(j));
                    emails.set(j, temp);
                }
            }
        }
    }

    /**
     * Sorts the emails based on subject ascending
     */
    public void sortBySubjectAscending() {
        manualSort(new EmailComparator("subjectAscending"));
        currentSortingMethod = "subjectAscending";
    }

    /**
     * Sorts the emails based on subject descending
     */
    public void sortBySubjectDescending() {
        manualSort(new EmailComparator("subjectDescending"));
        currentSortingMethod = "subjectDescending";
    }

    /**
     * Sorts the emails based on date ascending
     */
    public void sortByDateAscending() {
        manualSort(new EmailComparator("dateAscending"));
        currentSortingMethod = "dateAscending";
    }

    /**
     * Sorts the emails based on date descending
     */
    public void sortByDateDescending() {
        manualSort(new EmailComparator("dateDescending"));
        currentSortingMethod = "dateDescending";
    }
}
