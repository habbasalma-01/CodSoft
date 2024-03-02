import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    // Question 1: Create a Contact class to represent individual contacts.
    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    // Getters and setters for contact attributes.
    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    // Override toString for easy display.
    @Override
    public String toString() {
        return "Name: " + name + ", Phone: " + phoneNumber + ", Email: " + emailAddress;
    }
}

class AddressBook {
    private ArrayList<Contact> contacts;

    // Question 2: Implement an AddressBook class to manage the collection of
    // contacts.
    public AddressBook() {
        contacts = new ArrayList<>();
    }

    // Methods to add, remove, search, and display contacts.
    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public ArrayList<Contact> getAllContacts() {
        return contacts;
    }
}

class AddressBookGUI extends JFrame implements ActionListener {
    private AddressBook addressBook;
    private JTextField nameField, phoneField, emailField;
    private JTextArea resultArea;

    // Question 3: Design the user interface for the Address Book System using
    // Swing.
    public AddressBookGUI(AddressBook addressBook) {
        this.addressBook = addressBook;

        setTitle("Address Book System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        createGUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createGUI() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel emailLabel = new JLabel("Email:");

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();

        // Modified button colors
        // Question 5: Allow users to interact with the Address Book System by providing
        // options.
        JButton addButton = new JButton("Add Contact");
        addButton.setBackground(Color.GREEN);
        JButton searchButton = new JButton("Search Contact");
        searchButton.setBackground(Color.CYAN);
        JButton displayButton = new JButton("Display All Contacts");
        displayButton.setBackground(Color.YELLOW);
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.RED);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(phoneLabel);
        mainPanel.add(phoneField);
        mainPanel.add(emailLabel);
        mainPanel.add(emailField);
        mainPanel.add(addButton);
        mainPanel.add(searchButton);
        mainPanel.add(displayButton);
        mainPanel.add(exitButton);

        add(mainPanel, BorderLayout.CENTER);
        add(resultArea, BorderLayout.SOUTH);

        addButton.addActionListener(this);
        searchButton.addActionListener(this);
        displayButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add Contact":
                addContact();
                break;

            case "Search Contact":
                searchContact();
                break;

            case "Display All Contacts":
                displayContacts();
                break;

            case "Exit":
                System.exit(0);
                break;
        }
    }

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        // Question 6: Implement input validation to ensure that required fields are not
        // left empty.
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            resultArea.setText("Please fill in all required fields.");
            return;
        }

        // Add the contact to the address book.
        Contact newContact = new Contact(name, phone, email);
        addressBook.addContact(newContact);

        resultArea.setText("Contact added successfully.");
        clearFields();
    }

    private void searchContact() {
        String name = nameField.getText();
        Contact foundContact = addressBook.searchContact(name);

        if (foundContact != null) {
            resultArea.setText("Contact Found: " + foundContact.toString());
        } else {
            resultArea.setText("Contact not found.");
        }
    }

    private void displayContacts() {
        ArrayList<Contact> allContacts = addressBook.getAllContacts();
        if (!allContacts.isEmpty()) {
            StringBuilder displayText = new StringBuilder("All Contacts:\n");
            for (Contact contact : allContacts) {
                displayText.append(contact.toString()).append("\n");
            }
            resultArea.setText(displayText.toString());
        } else {
            resultArea.setText("No contacts in the address book.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }
}

class AddressBookFileIO {
    private static final String FILE_PATH = "address_book.ser";

    // Question 4: Implement methods to read and write contact data to a storage
    // medium.
    public static void writeToFile(AddressBook addressBook) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            outputStream.writeObject(addressBook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AddressBook readFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            return (AddressBook) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new AddressBook(); // Return a new AddressBook if file not found or cannot be read.
        }
    }
}

public class task5 {
    public static void main(String[] args) {
        // Question 5 (continued): by providing options such as adding a new contact,
        // searching for a contact, displaying all contacts, and exiting.
        AddressBook addressBook = AddressBookFileIO.readFromFile();
        SwingUtilities.invokeLater(() -> new AddressBookGUI(addressBook));

        // Save the address book to the file when the program exits.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> AddressBookFileIO.writeToFile(addressBook)));
    }
}
