import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Question 4: Create a class to represent the user's bank account, which stores the account balance.
class BankAccount {
    private double balance;

    // Constructor to initialize the account balance.
    public BankAccount(double balance) {
        this.balance = balance;
    }

    // Question 3: Implement methods for each option - deposit, withdraw, and
    // checkBalance.
    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public double checkBalance() {
        return balance;
    }
}

// Question 1: Create a class to represent the ATM machine.
class ATMGUI extends JFrame implements ActionListener {
    private BankAccount userAccount;

    private JTextField amountField;
    private JTextArea resultArea;

    // Question 5: Connect the ATM class with the user's bank account class.
    public ATMGUI(BankAccount userAccount) {
        this.userAccount = userAccount;

        // Set up the JFrame properties.
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the GUI components.
        createGUI();

        // Set JFrame properties for visibility.
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Question 2: Design the user interface for the ATM, including options for
    // withdrawing, depositing, and checking the balance.
    private void createGUI() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));

        // Labels and Text Field for Amount
        JLabel amountLabel = new JLabel("Enter Amount:");
        amountField = new JTextField();

        // Buttons
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Adding Components to the Panels
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);
        mainPanel.add(withdrawButton);
        mainPanel.add(depositButton);
        mainPanel.add(checkBalanceButton);

        add(mainPanel, BorderLayout.CENTER);
        add(resultArea, BorderLayout.SOUTH);

        // Adding Action Listeners
        withdrawButton.addActionListener(this);
        depositButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Question 6: Validate user input to ensure it is within acceptable limits.
        switch (command) {
            case "Withdraw":
                performTransaction("withdraw");
                break;

            case "Deposit":
                performTransaction("deposit");
                break;

            case "Check Balance":
                displayBalance();
                break;
        }
    }

    // Helper method to handle transactions.
    private void performTransaction(String transactionType) {
        double amount = 0;

        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            resultArea.setText("Invalid amount. Please enter a numeric value.");
            return;
        }

        // Connect with the BankAccount class to perform transactions.
        if (transactionType.equals("withdraw")) {
            if (userAccount.withdraw(amount)) {
                resultArea.setText(
                        String.format("Withdrawal successful. Remaining balance: $%.2f", userAccount.checkBalance()));
            } else {
                resultArea.setText("Withdrawal failed. Insufficient funds or invalid amount.");
            }
        } else if (transactionType.equals("deposit")) {
            if (userAccount.deposit(amount)) {
                resultArea.setText(String.format("Deposit successful. New balance: $%.2f", userAccount.checkBalance()));
            } else {
                resultArea.setText("Deposit failed. Invalid amount.");
            }
        }
    }

    // Helper method to display the current balance.
    private void displayBalance() {
        resultArea.setText(String.format("Current balance: $%.2f", userAccount.checkBalance()));
    }
}

// Entry point for the program.
public class task4 {
    public static void main(String[] args) {
        // Question 7: Display appropriate messages to the user based on their chosen
        // options and the success or failure of transactions.
        BankAccount userAccount = new BankAccount(1000);
        SwingUtilities.invokeLater(() -> new ATMGUI(userAccount));
    }
}
