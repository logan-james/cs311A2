
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PPG {
    private static final String[] PARTIES = {"Democratic", "Republican", "Libertarian", "Green"};
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] scores = new double[PARTIES.length];

        System.out.println("Welcome to the Political Party Guesser!");
        System.out.println("Please answer the following questions:");

        String[] questions = {
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

        String[][] options = {
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

        for (int i = 0; i < questions.length; i++) {
            System.out.println((i + 1) + ". " + questions[i]);
            for (String option : options[i]) {
                System.out.println("   " + option);
            }

            String answer = "";
            while (!isValidAnswer(answer)) {
                System.out.print("Enter your answer (A/B/C/D): ");
                answer = scanner.nextLine().toUpperCase();
            }

            int index = answer.charAt(0) - 'A';

            for (int j = 0; j < PARTIES.length; j++) {
                scores[j] += WEIGHTS[i][index];
            }
        }

        System.out.println("Scores:");
        for (int i = 0; i < PARTIES.length; i++) {
            System.out.println(PARTIES[i] + ": " + scores[i]);
        }

        String predictedParty = predictParty(scores);
        System.out.println("Based on your responses, we guess that you are most likely affiliated with the " + predictedParty + " Party.");

        System.out.println("Is our guess correct? (Y/N)");
        String confirmation = scanner.nextLine().toUpperCase();

        if (confirmation.equals("Y")) {
            System.out.println("Great! Thank you for confirming.");
        } else {
            System.out.println("Which political party do you most closely affiliate with?");
            for (int i = 0; i < PARTIES.length; i++) {
                System.out.println((char) ('A' + i) + ". " + PARTIES[i] + " Party");
            }

            String affiliatedParty = "";
            while (!isValidParty(affiliatedParty)) {
                System.out.print("Enter your affiliated party (A/B/C/D): ");
                affiliatedParty = scanner.nextLine().toUpperCase();
            }

            // Store the user's responses and affiliated party in a file
            try {
                FileWriter writer = new FileWriter(affiliatedParty + ".txt", true);
                writer.write(String.join(",", questions) + "\n");
                writer.close();
                System.out.println("Data stored successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while storing the data.");
                e.printStackTrace();
            }
        }

        System.out.println("Thank you for using the Political Party Guesser!");
    }

    private static String predictParty(double[] scores) {
        int maxIndex = 0;
        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[maxIndex]) {
                maxIndex = i;
            }
        }
        return PARTIES[maxIndex];
    }

    private static boolean isValidAnswer(String answer) {
        return answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) <= 'D';
    }

    private static boolean isValidParty(String party) {
        return party.length() == 1 && party.charAt(0) >= 'A' && party.charAt(0) <= 'D';
    }
}