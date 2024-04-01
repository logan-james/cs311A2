import java.io.FileWriter; // Import the FileWriter class for writing to files.
import java.io.IOException; // Import the IOException class for handling input/output errors.
import java.util.Scanner; // Import the Scanner class for reading user input.

public class PoliticalPartyGuesser { // Define the main class named PoliticalPartyGuesser.
    // Declare an array to store the names of the political parties.
    private static final String[] PARTIES = {"Democratic", "Republican", "Libertarian", "Green"};
    // Declare a 2D array to store the weights for each question and party combination.
    private static final double[][] WEIGHTS = {
            {0.8, 0.1, 0.0, 0.1}, 
            {0.1, 0.7, 0.2, 0.0}, 
            {0.7, 0.1, 0.0, 0.2}, 
            {0.6, 0.3, 0.1, 0.0},
            {0.8, 0.1, 0.0, 0.1}, 
            {0.9, 0.1, 0.0, 0.0}, 
            {0.7, 0.2, 0.1, 0.0}, 
            {0.8, 0.1, 0.1, 0.0},
            {0.9, 0.1, 0.0, 0.0}, 
            {0.8, 0.1, 0.1, 0.0}
    };
    // Declare an array to store the questions.
    private static final String[] questions = {
            "What should the government do to help the poor?",
            "What is your stance on gun control?",
            "What is your view on climate change?",
            "What should be the primary focus of U.S. foreign policy?",
            "What is your stance on immigration?",
            "What should be the government's role in healthcare?",
            "What is your view on taxes and government spending?",
            "What should be the government's role in regulating businesses?",
            "What is your stance on social issues like abortion and same-sex marriage?",
            "What should be the government's role in education?"
    };
    // Declare a 2D array to store the answer options for each question.
    private static final String[][] options = {
            {"A. Make it easier to apply for assistance.", "B. Allow parents to use education funds for charter schools.",
                    "C. Create welfare to work programs.", "D. Nothing."},
            {"A. Stricter gun control laws are needed.", "B. Maintain current gun control laws.",
                    "C. Loosen gun control laws.", "D. No gun control laws are necessary."},
            {"A. Urgent action is needed to combat climate change.", "B. Some action is needed, but it should be balanced with economic considerations.",
                    "C. More research is needed before taking action.", "D. Climate change is not a significant concern."},
            {"A. Promote democracy and human rights.", "B. Prioritize national security and counter-terrorism.",
                    "C. Focus on free trade and global economic growth.", "D. Reduce U.S. involvement in foreign affairs."},
            {"A. Provide a pathway to citizenship for undocumented immigrants.", "B. Secure borders and enforce existing immigration laws.",
                    "C. Reduce legal immigration levels.", "D. Open borders and welcome all immigrants."},
            {"A. Implement a universal healthcare system.", "B. Maintain a mix of private and public healthcare options.",
                    "C. Reduce government involvement in healthcare.", "D. Eliminate all government healthcare programs."},
            {"A. Raise taxes on the wealthy and increase government spending.", "B. Maintain current tax rates and balance the budget.",
                    "C. Lower taxes and reduce government spending.", "D. Eliminate all taxes and drastically reduce government spending."},
            {"A. Increase regulations to protect consumers and the environment.", "B. Maintain current levels of regulation.",
                    "C. Reduce regulations to promote business growth.", "D. Eliminate most government regulations on businesses."},
            {"A. Support progressive policies like pro-choice and same-sex marriage.", "B. Maintain current laws and leave decisions to states.",
                    "C. Oppose abortion and same-sex marriage.", "D. Government should not be involved in these issues."},
            {"A. Increase funding for public education.", "B. Provide vouchers for private school choice.",
                    "C. Reduce federal involvement in education.", "D. Eliminate the Department of Education."}
    };

    public static void main(String[] args) { // Define the main method.
        Scanner scanner = new Scanner(System.in); // Create a Scanner object to read user input.
        double[] scores = new double[PARTIES.length]; // Declare an array to store the scores for each political party.
        String[] userAnswers = new String[questions.length]; // Declare an array to store the user's answers.

        System.out.println("Welcome to the Political Party Guesser!"); // Print a welcome message.
        System.out.println("Please answer the following questions:"); // Print instructions for the user.

        for (int i = 0; i < questions.length; i++) { // Loop through each question.
            System.out.println((i + 1) + ". " + questions[i]); // Print the question number and text.
            for (String option : options[i]) { // Loop through each answer option for the current question.
                System.out.println("   " + option); // Print the answer option.
            }

            String answer = ""; // Declare a variable to store the user's answer.
            while (!isValidAnswer(answer)) { // Keep asking for input until a valid answer is provided.
                System.out.print("Enter your answer (A/B/C/D): "); // Prompt the user to enter their answer.
                answer = scanner.nextLine().toUpperCase(); // Read the user's answer and convert it to uppercase.
            }

            userAnswers[i] = answer; // Store the user's answer in the userAnswers array.
            int index = answer.charAt(0) - 'A'; // Convert the user's answer to an index (0-3).

            for (int j = 0; j < PARTIES.length; j++) { // Loop through each political party.
                scores[j] += WEIGHTS[i][index] * (index == j ? 1 : 0); // Apply the weight to the party's score based on the user's answer.
            }
        }

        String predictedParty = predictParty(scores); // Predict the party based on the scores.
        System.out.println("Based on your responses, we guess that you are most likely affiliated with the " + predictedParty + " Party."); // Print the predicted party.

        for (int i = 0; i < PARTIES.length; i++) { // Loop through each political party.
            try (FileWriter writer = new FileWriter(PARTIES[i] + ".txt", true)) { // Create a FileWriter object to write to a file for the current party.
                writer.write("User's Answers for " + PARTIES[i] + " Party:\n"); // Write a header to the file.
                for (int j = 0; j < questions.length; j++) { // Loop through each question.
                    writer.write(questions[j] + "\n" + options[j][userAnswers[j].charAt(0) - 'A'] + "\n\n"); // Write the question and user's answer to the file.
                }
                writer.write("Predicted Party: " + predictedParty + "\n\n"); // Write the predicted party to the file.
                
            } catch (IOException e) { // Catch any IOException that may occur.
                System.out.println("An error occurred while storing the data for " + PARTIES[i] + " party."); // Print an error message.
                e.printStackTrace(); // Print the stack trace of the exception.
            }
        }
        System.out.println("Thank you for using the Political Party Guesser!"); // Print a thank you message.
    }

    private static String predictParty(double[] scores) { // Define a method to predict the party based on the scores.
        int maxIndex = 0; // Assume the first party has the highest score.
        for (int i = 1; i < scores.length; i++) { // Loop through the scores starting from the second party.
            if (scores[i] > scores[maxIndex]) { // If the current party's score is higher than the current maximum, update the maximum index.
                maxIndex = i;
            }
        }
        return PARTIES[maxIndex]; // Return the name of the party with the highest score.
    }

    private static boolean isValidAnswer(String answer) { // Define a method to validate the user's answer.
        return answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) <= 'D'; // Check if the answer is a single character between 'A' and 'D'.

        
    } // Closing Main Method
} // Closing Class