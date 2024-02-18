import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class task2 extends JFrame {

    private JTextArea textArea;
    private JLabel resultLabel;

    public task2() {
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create components
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton countButton = new JButton("Count Words");
        resultLabel = new JLabel("Total number of words: ");

        // Set layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Add components to the frame
        add(scrollPane);
        add(countButton);
        add(resultLabel);

        // Add action listener to the countButton
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countWords();
            }
        });
    }

    // Method to handle word counting logic
    private void countWords() {
        String content = textArea.getText();
        String[] words = content.split("[\\s\\p{Punct}]+");

        int wordCount = words.length;

        HashSet<String> stopWords = new HashSet<>();
        stopWords.add("the");
        stopWords.add("and");
        stopWords.add("is");

        int nonStopWordCount = 0;

        for (String word : words) {
            if (!stopWords.contains(word.toLowerCase())) {
                nonStopWordCount++;
            }
        }

        HashSet<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            uniqueWords.add(word.toLowerCase());
        }

        // Display the results in the resultLabel
        resultLabel.setText("Total words: " + wordCount +
                " | Non-stop words: " + nonStopWordCount +
                " | Unique words: " + uniqueWords.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new task2().setVisible(true);
            }
        });
    }
}
