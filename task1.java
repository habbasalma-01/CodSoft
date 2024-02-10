import java.util.Random;
import java.util.Scanner;

public class task1 {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);

        int lowerBound = 1;
        int upperBound = 100;
        int maxAttempts = 10;
        int rounds = 0;
        int totalScore = 0;

        boolean startOver;
        Random random = new Random();
        // 1. Generate a random number within a specified range (1 to 100).
        // 2. Prompt the user to enter their guess and provide feedback until they guess
        // correctly.
        // 5. Limit the number of attempts and allow multiple rounds with a score
        // display.
        do {
            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("Welcome to the Number Guessing Game!");
            System.out.println(
                    "I've selected a number between " + lowerBound + " and " + upperBound + ". Try to guess it.");

            // 4. Repeat steps 2 and 3 until the user guesses the correct number.
            do {
                System.out.print("Enter your guess: ");
                int userGuess = scn.nextInt();
                attempts++;

                // 3. Compare the user's guess and provide feedback.
                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the correct number in " + attempts + " attempts.");
                    // 7. Display the user's score based on the number of attempts.
                    totalScore += maxAttempts - attempts + 1;
                    guessedCorrectly = true;
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low. Try again.");
                } else {
                    System.out.println("Too high. Try again.");
                }
            } while (attempts < maxAttempts && !guessedCorrectly);

            // 5. If the user doesn't guess correctly, inform them of the correct number.
            if (!guessedCorrectly) {
                System.out.println("Sorry but you've reached the maximum number of attempts. The correct number was: "
                        + targetNumber);
            }

            // 6. Increment the rounds, display the user's score, and ask if they want to
            // play again.
            rounds++;
            System.out.println("Your current score: " + totalScore);
            System.out.print("Do you want to start over? (yes/no): ");
            String playAgainInput = scn.next().toLowerCase();
            startOver = playAgainInput.equals("yes");
        } while (startOver);

        // 7. Display the user's final score after multiple rounds.
        System.out.println("Thanks for playing! Your final score after " + rounds + " rounds is: " + totalScore);
        scn.close();
    }
}
