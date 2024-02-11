import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class task1 extends JFrame {
    private final Random random = new Random();
    private final int lowerBound = 1;
    private final int upperBound = 100;
    private final int maxAttempts = 10;
    private int rounds = 0;
    private int totalScore = 0;

    public task1() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        add(startButton);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void startGame() {
        rounds++;
        int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
        int[] attempts = { 0 }; // Mutable integer array to allow modification within ActionListener
        AtomicBoolean guessedCorrectly = new AtomicBoolean(false);

        getContentPane().removeAll(); // Clear the frame

        JLabel welcomeLabel = new JLabel("Welcome to the Number Guessing Game!");
        JLabel instructionLabel = new JLabel(
                "I've selected a number between " + lowerBound + " and " + upperBound + ". Try to guess it.");
        JTextField guessField = new JTextField(5); // Set the number of columns
        JButton guessButton = new JButton("Submit");
        guessButton.setPreferredSize(new Dimension(100, 30));

        // Set RGB color for the button (e.g., red color)
        Color buttonColor = new Color(255, 0, 0);
        guessButton.setBackground(buttonColor);

        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int userGuess = Integer.parseInt(guessField.getText());
                    attempts[0]++;

                    if (userGuess == targetNumber) {
                        JOptionPane.showMessageDialog(null,
                                "Congratulations! You guessed the correct number in " + attempts[0] + " attempts.");
                        totalScore += maxAttempts - attempts[0] + 1;
                        guessedCorrectly.set(true);
                        resetGame();
                    } else if (userGuess < targetNumber) {
                        JOptionPane.showMessageDialog(null, "Too low. Try again.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Too high. Try again.");
                    }

                    if (!guessedCorrectly.get() && attempts[0] >= maxAttempts) {
                        JOptionPane.showMessageDialog(null,
                                "Sorry, you've reached the maximum number of attempts. The correct number was: "
                                        + targetNumber);
                        resetGame();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid number.");
                }
            }
        });

        add(welcomeLabel);
        add(instructionLabel);
        add(guessField);
        add(guessButton);

        revalidate(); // Refresh the frame to display new components
    }

    private void resetGame() {
        getContentPane().removeAll(); // Clear the frame

        JLabel scoreLabel = new JLabel("Your current score after " + rounds + " rounds: " + totalScore);
        JButton playAgainButton = new JButton("Play Again");

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        add(scoreLabel);
        add(playAgainButton);

        revalidate(); // Refresh the frame to display new components
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new task1();
            }
        });
    }
}
