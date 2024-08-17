/* Siddharth Korukonda
 * 115607752
 * CSE 214.30
 */

package Homework5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame {
    private Mailbox mailboxBackend;

    public GUI() {
        mailboxBackend = Mailbox.loadMailbox();
        if (mailboxBackend == null) {
            mailboxBackend = new Mailbox();
        }

        JFrame mailboxFrame = new JFrame("Inlook");
        mailboxFrame.setSize(600, 400);
        mailboxFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mailboxFrame.setLayout(null);
        mailboxFrame.setResizable(true);

        JLabel titleLabel = new JLabel("Mailbox");
        titleLabel.setBounds(15, 15, 300, 30);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        mailboxFrame.add(titleLabel);

        JButton addFolderButton = new JButton("Add Folder");
        addFolderButton.setBounds(150, 60, 150, 30);
        mailboxFrame.add(addFolderButton);

        JButton removeFolderButton = new JButton("Remove Folder");
        removeFolderButton.setBounds(150, 100, 150, 30);
        mailboxFrame.add(removeFolderButton);

        JButton composeEmailButton = new JButton("Compose Email");
        composeEmailButton.setBounds(150, 140, 150, 30);
        mailboxFrame.add(composeEmailButton);

        JButton openInboxButton = new JButton("Open Inbox");
        openInboxButton.setBounds(150, 180, 150, 30);
        mailboxFrame.add(openInboxButton);

        JButton openTrashButton = new JButton("Open Trash");
        openTrashButton.setBounds(150, 220, 150, 30);
        mailboxFrame.add(openTrashButton);

        JButton emptyTrashButton = new JButton("Empty Trash");
        emptyTrashButton.setBounds(150, 260, 150, 30);
        mailboxFrame.add(emptyTrashButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(150, 300, 150, 30);
        mailboxFrame.add(quitButton);

        addFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(mailboxFrame, "Enter folder name:");
                if (folderName != null && !folderName.trim().isEmpty()) {
                    try {
                        mailboxBackend.addFolder(new Folder(folderName.trim()));
                        Mailbox.saveMailbox(mailboxBackend);
                        JOptionPane.showMessageDialog(mailboxFrame, "Folder '" + folderName + "' added successfully.");
                    } catch (InvalidMailboxException ex) {
                        JOptionPane.showMessageDialog(mailboxFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (folderName != null) {
                    JOptionPane.showMessageDialog(mailboxFrame, "Folder name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderName = JOptionPane.showInputDialog(mailboxFrame, "Enter folder name to remove:");
                if (folderName != null && !folderName.trim().isEmpty()) {
                    try {
                        mailboxBackend.deleteFolder(folderName.trim());
                        Mailbox.saveMailbox(mailboxBackend);
                        JOptionPane.showMessageDialog(mailboxFrame, "Folder '" + folderName + "' deleted successfully.");
                    } catch (InvalidMailboxException ex) {
                        JOptionPane.showMessageDialog(mailboxFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (folderName != null) {
                    JOptionPane.showMessageDialog(mailboxFrame, "Folder name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        composeEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String to = JOptionPane.showInputDialog(mailboxFrame, "Enter recipient (To):");
                String cc = JOptionPane.showInputDialog(mailboxFrame, "Enter carbon copy recipients (CC):");
                String bcc = JOptionPane.showInputDialog(mailboxFrame, "Enter blind carbon copy recipients (BCC):");
                String subject = JOptionPane.showInputDialog(mailboxFrame, "Enter subject line:");
                String body = JOptionPane.showInputDialog(mailboxFrame, "Enter email body:");

                if (to != null && subject != null && body != null) {
                    Email email = null;
                    try {
                        email = new Email(to, cc, bcc, subject, body, new GregorianCalendar());
                    } catch (InvalidEmailException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        mailboxBackend.getInbox().addEmail(email);
                    } catch (InvalidFolderException ex) {
                        throw new RuntimeException(ex);
                    }
                    Mailbox.saveMailbox(mailboxBackend);
                    JOptionPane.showMessageDialog(mailboxFrame, "Email successfully added to Inbox.");
                } else {
                    JOptionPane.showMessageDialog(mailboxFrame, "Email composition cancelled.", "Cancelled", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        openInboxButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Folder inbox = mailboxBackend.getInbox();
                try {
                    openFolderOperations(inbox);
                } catch (Exception ex) {
                    try {
                        throw new GUIError(ex);
                    } catch (GUIError exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        });

        openTrashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Folder trash = mailboxBackend.getTrash();
                try {
                    openFolderOperations(trash);
                } catch (Exception ex) {
                    try {
                        throw new GUIError(ex);
                    } catch (GUIError exc) {
                        throw new RuntimeException(exc);
                    }
                }
            }
        });

        emptyTrashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mailboxBackend.clearTrash();
                Mailbox.saveMailbox(mailboxBackend);
                JOptionPane.showMessageDialog(mailboxFrame, "Trash emptied successfully.");
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mailbox.saveMailbox(mailboxBackend);
                JOptionPane.showMessageDialog(mailboxFrame, "Mailbox saved. Exiting program.");
                System.exit(0);
            }
        });

        mailboxFrame.setVisible(true);
    }

    private void openFolderOperations(Folder folder) throws Exception {
        String[] options = {"Move Email", "Delete Email", "View Email", "Sort by Subject Ascending",
                "Sort by Subject Descending", "Sort by Date Ascending", "Sort by Date Descending", "Return"};
        boolean continueOperations = true;

        while (continueOperations) {
            int choice = JOptionPane.showOptionDialog(null, "Select an operation for folder: " + folder.getName(),
                    "Folder Operations", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> moveEmailOperation(folder);
                case 1 -> deleteEmailOperation(folder);
                case 2 -> viewEmailOperation(folder);
                case 3 -> {
                    folder.sortBySubjectAscending();
                    JOptionPane.showMessageDialog(null, "Emails sorted by subject in ascending order.");
                }
                case 4 -> {
                    folder.sortBySubjectDescending();
                    JOptionPane.showMessageDialog(null, "Emails sorted by subject in descending order.");
                }
                case 5 -> {
                    folder.sortByDateAscending();
                    JOptionPane.showMessageDialog(null, "Emails sorted by date in ascending order.");
                }
                case 6 -> {
                    folder.sortByDateDescending();
                    JOptionPane.showMessageDialog(null, "Emails sorted by date in descending order.");
                }
                default -> continueOperations = false;
            }

        }
    }

    private void moveEmailOperation(Folder folder) throws Exception {
        int emailIndex = getEmailIndexFromUser(folder, "Move");
        if (emailIndex >= 0) {
            Email emailToMove = folder.removeEmail(emailIndex);
            String targetFolderName = JOptionPane.showInputDialog(null, "Enter the target folder name:");

            if (targetFolderName != null && !targetFolderName.trim().isEmpty()) {
                try {
                    Folder targetFolder = mailboxBackend.getFolder(targetFolderName);
                    mailboxBackend.moveEmail(emailToMove, targetFolder);
                    Mailbox.saveMailbox(mailboxBackend);
                    JOptionPane.showMessageDialog(null, "Email moved to " + targetFolder.getName() + ".");
                } catch (InvalidMailboxException | InvalidFolderException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void deleteEmailOperation(Folder folder) throws Exception {
        int emailIndex = getEmailIndexFromUser(folder, "Delete");
        if (emailIndex >= 0) {
            Email emailToDelete = folder.removeEmail(emailIndex);
            try {
                mailboxBackend.deleteEmail(emailToDelete);
                Mailbox.saveMailbox(mailboxBackend);
                JOptionPane.showMessageDialog(null, "Email deleted and moved to trash.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewEmailOperation(Folder folder) {
        int emailIndex = getEmailIndexFromUser(folder, "View");
        if (emailIndex >= 0) {
            Email emailToView = folder.getEmails().get(emailIndex);
            JOptionPane.showMessageDialog(null,
                    "To: " + emailToView.getTo() + "\nCC: " + emailToView.getCc() + "\nBCC: " + emailToView.getBcc() +
                            "\nSubject: " + emailToView.getSubject() + "\n\n" + emailToView.getBody(),
                    "Email Content", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private int getEmailIndexFromUser(Folder folder, String action) {
        StringBuilder emailList = new StringBuilder("Select email index to " + action + ":\n");
        for (int i = 0; i < folder.getEmails().size(); i++) {
            Email email = folder.getEmails().get(i);
            emailList.append(i + 1).append(": ").append(email.getSubject()).append(" (").append(email.getTimestamp().getTime()).append(")\n");
        }
        String emailIndexStr = JOptionPane.showInputDialog(null, emailList.toString());

        try {
            int emailIndex = Integer.parseInt(emailIndexStr) - 1;
            if (emailIndex >= 0 && emailIndex < folder.getEmails().size()) {
                return emailIndex;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid email index.", "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input.", "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUI().setVisible(true));
    }
}
