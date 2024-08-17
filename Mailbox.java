/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

import java.util.*;
import java.io.*;

/**
 * Represents a user's mailbox
 * Contains pre-made inbox and trash folders
 * Can perform various folder operations
 */
public class Mailbox implements Serializable {
    private Folder inbox;
    private Folder trash;
    private ArrayList<Folder> folders;
    private static Mailbox mailbox = loadMailbox();

    /**
     * Constructor for the mailbox
     */
    public Mailbox() {
        this.inbox = new Folder("inbox");
        this.trash = new Folder("trash");
        this.folders = new ArrayList<>();
    }

    /**
     * Gets the inbox folder
     * @return inbox folder
     */
    public Folder getInbox() {
        return inbox;
    }

    /**
     * Gets the trash folder
     * @return trash folder
     */
    public Folder getTrash() {
        return trash;
    }

    /**
     * Gets the folder list
     * @return folder list
     */
    public ArrayList<Folder> getFolders() {
        return folders;
    }

    /**
     * Sets the inbox folder
     * @param inbox folder
     * @throws InvalidMailboxException if the inbox folder is null
     */
    public void setInbox(Folder inbox) throws InvalidMailboxException {
        if (inbox == null) {
            throw new InvalidMailboxException("Inbox cannot be null");
        }
        this.inbox = inbox;
    }

    /**
     * Sets the trash folder
     * @param trash folder
     * @throws InvalidMailboxException if the trash folder is null
     */
    public void setTrash(Folder trash) throws InvalidMailboxException {
        if (trash == null) {
            throw new InvalidMailboxException("Trash cannot be null");
        }
        this.trash = trash;
    }

    /**
     * Sets the folders list
     * @param folders list
     * @throws InvalidMailboxException if the folder list is null
     */
    public void setFolders(ArrayList<Folder> folders) throws InvalidMailboxException {
        if (folders == null) {
            throw new InvalidMailboxException("Folders cannot be null");
        }
        this.folders = folders;
    }

    /**
     * Adds a folder to the mailbox
     * @param folder to add
     * @throws InvalidMailboxException if the folder is null
     */
    public void addFolder(Folder folder) throws InvalidMailboxException {
        try {
            if (folder == null) {
                throw new InvalidMailboxException("Folder cannot be null");
            }
            for (int i = 0; i < folders.size(); i++) {
                if (folders.get(i).getName().equalsIgnoreCase(folder.getName())) {
                    throw new InvalidMailboxException("Folder with this name already exists.");
                }
            }
            folders.add(folder);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Removes a folder from the mailbox
     * @param name of the folder
     * @throws InvalidMailboxException if the name of the folder is null or empty
     */
    public void deleteFolder(String name) throws InvalidMailboxException {
        try {
            if (name == null) {
                throw new InvalidMailboxException("Folder name cannot be null");
            } else if (name.trim().isEmpty()) {
                throw new InvalidMailboxException("Folder cannot be empty");
            }

            boolean removed = true;

            for (int i=0; i<folders.size(); i++) {
                if (folders.get(i).getName().equalsIgnoreCase(name)) {
                    folders.remove(i);
                    removed = false;
                    break;
                }
            }
            if (removed) {
                throw new InvalidMailboxException("Folder not found: " + name);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Creates a new email and adds it to the inbox
     */
    public void composeEmail() {
        Scanner s = new Scanner(System.in);

        try {
            System.out.print("Enter recipient (To): ");
            String to = s.nextLine();
            System.out.print("Enter carbon copy recipients (CC): ");
            String cc = s.nextLine();
            System.out.print("Enter blind carbon copy recipients (BCC): ");
            String bcc = s.nextLine();
            System.out.print("Enter subject line: ");
            String subject = s.nextLine();
            System.out.print("Enter body: ");
            String body = s.nextLine();

            Email email = new Email(to, cc, bcc, subject, body, new GregorianCalendar());

            inbox.addEmail(email);
            System.out.println("Email successfully added to Inbox.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes email from the inbox
     * @param email needed to be deleted
     * @throws Exception if the email is null
     */
    public void deleteEmail(Email email) throws Exception {
        try {
            if (email == null) {
                throw new InvalidMailboxException("Email cannot be null");
            }
            trash.addEmail(email);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Clears the trash folder
     */
    public void clearTrash() {
        try {
            trash.getEmails().clear();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Moves an email to a specified folder
     * @param email that needs to be moved
     * @param target folder for the email
     * @throws Exception if the email or target is null
     */
    public void moveEmail(Email email, Folder target) throws Exception {
        try {
            if (email == null) {
                throw new InvalidMailboxException("Email cannot be null");
            }

            if (target == null) {
                inbox.addEmail(email);
            } else {
                target.addEmail(email);
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Retrieves a folder by its name
     * @param name of the folder
     * @return folder
     * @throws InvalidMailboxException if the name of the folder is null or empty
     */
    public Folder getFolder(String name) throws InvalidMailboxException {
        try {
            if (name == null) {
                throw new InvalidMailboxException("Folder name cannot be null");
            } else if (name.trim().isEmpty()) {
                throw new InvalidMailboxException("Folder name cannot be empty");
            }

            for (int i=0; i<folders.size(); i++) {
                if (folders.get(i).getName().equalsIgnoreCase(name)) {
                    return folders.get(i);
                }
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        throw new InvalidMailboxException("Folder not found: " + name);
    }

    /**
     * Loads the mailbox to a serialized file
     * @return mailbox object
     */
    static Mailbox loadMailbox() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("mailbox.obj"))) {
            return (Mailbox) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Saves the object to a serialized file
     * @param mailbox object
     */
    static void saveMailbox(Mailbox mailbox) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("mailbox.obj"))) {
            oos.writeObject(mailbox);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to perform operations on a specified folder
     * @param folder needed to do actions on
     */
    private static void folderOperations(Folder folder) {
        Scanner scanner = new Scanner(System.in);
        boolean folderOperationsMenu = true;

        while (folderOperationsMenu) {
            try {
                System.out.println("\n" + folder.getName());
                System.out.println("\nIndex |        Time       | Subject");
                System.out.println("-----------------------------------");

                for (int i=0; i<folder.getEmails().size(); i++) {
                    Email email = folder.getEmails().get(i);
                    System.out.printf("%4d   |  %tT %<tD | %s\n", i+1, email.getTimestamp(), email.getSubject());
                }

                System.out.println("\nM – Move email" +
                        "\nD – Delete email" +
                        "\nV – View email contents" +
                        "\nSA – Sort by subject ascending" +
                        "\nSD – Sort by subject descending" +
                        "\nDA – Sort by date ascending" +
                        "\nDD – Sort by date descending" +
                        "\nR – Return to mailbox" +
                        "\n\nEnter a user option: ");

                String choice = scanner.nextLine().toUpperCase();

                switch (choice) {
                    case "M" -> {
                        System.out.print("Enter the index of the email to move: ");
                        int indexToMove = scanner.nextInt()-1;
                        scanner.nextLine();

                        if (indexToMove>=0 && indexToMove<folder.getEmails().size()) {
                            Email emailToMove = folder.removeEmail(indexToMove);
                            System.out.println("Folders:");
                            System.out.println("Inbox");
                            System.out.println("Trash");

                            for (int i=0; i<mailbox.getFolders().size(); i++) {
                                System.out.println(mailbox.getFolders().get(i).getName());
                            }

                            System.out.print("\nSelect a folder to move \"" + emailToMove.getSubject() + "\" to: ");
                            String targetFolderName = scanner.nextLine();

                            Folder targetFolder = mailbox.getFolder(targetFolderName);

                            mailbox.moveEmail(emailToMove, targetFolder);
                            System.out.println("\"" + emailToMove.getSubject() + "\" successfully moved to " + (targetFolder != null ? targetFolder.getName() : "Inbox") + ".");
                        } else {
                            System.out.println("Invalid index");
                        }
                    }
                    case "D" -> {
                        System.out.print("Enter email index: ");
                        int indexToDelete = scanner.nextInt() - 1;
                        scanner.nextLine();

                        if (indexToDelete>=0 && indexToDelete<folder.getEmails().size()) {
                            Email emailToDelete = folder.removeEmail(indexToDelete);
                            mailbox.deleteEmail(emailToDelete);
                            System.out.println("\"" + emailToDelete.getSubject() + "\" has successfully been moved to the trash.");
                        } else {
                            System.out.println("Invalid index.");
                        }
                    }
                    case "V" -> {
                        System.out.print("Enter email index: ");
                        int indexToView = scanner.nextInt() - 1;
                        scanner.nextLine();

                        if (indexToView>=0 && indexToView<folder.getEmails().size()) {
                            Email emailToView = folder.getEmails().get(indexToView);

                            System.out.println("\nTo: " + emailToView.getTo() +
                                    "\nCC: " + emailToView.getCc() +
                                    "\nBCC: " + emailToView.getBcc() +
                                    "\nSubject: " + emailToView.getSubject() +
                                    "\nBody: " + emailToView.getBody());
                        } else {
                            System.out.println("Invalid index.");
                        }
                    }
                    case "SA" -> {
                        folder.sortBySubjectAscending();
                        System.out.println("Emails sorted by subject in ascending order.");
                    }
                    case "SD" -> {
                        folder.sortBySubjectDescending();
                        System.out.println("Emails sorted by subject in descending order.");
                    }
                    case "DA" -> {
                        folder.sortByDateAscending();
                        System.out.println("Emails sorted by date in ascending order.");
                    }
                    case "DD" -> {
                        folder.sortByDateDescending();
                        System.out.println("Emails sorted by date in descending order.");
                    }
                    case "R" -> {
                        return;
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Main method
     * @param args command line
     */
    public static void main(String[] args) {
        try {
            if (mailbox == null) {
                mailbox = new Mailbox();
                System.out.println("Previous save not found, starting with an empty mailbox.");
            }

            Scanner s = new Scanner(System.in);
            boolean menu = true;

            while (menu) {
                System.out.println("\nMailbox:\n" +
                        "--------\n" +
                        "Inbox\n" +
                        "Trash");

                for (int i=0; i<mailbox.getFolders().size(); i++) {
                    System.out.println(mailbox.getFolders().get(i).getName());
                }

                System.out.println("\nA – Add folder\n" +
                        "R – Remove folder\n" +
                        "C – Compose email\n" +
                        "F – Open folder\n" +
                        "I – Open Inbox\n" +
                        "T – Open Trash\n" +
                        "E – Empty Trash\n" +
                        "Q – Quit\n" +
                        "Enter a user option: ");
                String choice = s.nextLine().toUpperCase();

                try {
                    switch (choice) {
                        case "A" -> {
                            System.out.print("Enter folder name: ");
                            String folderName = s.nextLine();
                            mailbox.addFolder(new Folder(folderName));
                        }
                        case "R" -> {
                            System.out.print("Enter folder name: ");
                            String folderNameToRemove = s.nextLine();
                            mailbox.deleteFolder(folderNameToRemove);
                        }
                        case "C" -> mailbox.composeEmail();

                        case "F" -> {
                            System.out.print("Enter folder name: ");
                            String folderNameToView = s.nextLine();
                            Folder folderToView = mailbox.getFolder(folderNameToView);
                            folderOperations(folderToView);
                        }
                        case "I" -> folderOperations(mailbox.getInbox());
                        case "T" -> folderOperations(mailbox.getTrash());
                        case "E" -> {
                            mailbox.clearTrash();
                            System.out.println(mailbox.getTrash().getEmails().size() + " item(s) successfully deleted");
                        }
                        case "Q" -> {
                            saveMailbox(mailbox);
                            System.out.println("Program successfully exited and mailbox saved.");
                            return;
                        }
                        default -> System.out.println("Invalid option. Please try again.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}