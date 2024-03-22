import java.util.*;

public class PoliticalPartyGuesser {
    private static final String[] PARTIES = {"Democratic", "Republican", "Libertarian", "Green"};
    private static final Map<String, Map<String, Integer>> PARTY_ANSWERS = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load existing party answer data (if any)
        loadPartyAnswerData();

        // Conduct the survey
        conductSurvey(scanner);

        // Save the updated party answer data
        savePartyAnswerData();

        scanner.close();
    }

    private static void conductSurvey(Scanner scanner) {
        Map<String, Integer> userAnswers = new HashMap<>();

        // Ask survey questions
        String[] questions = {
                "What should the government do to help the poor?",
                // Add more questions here
        };

        String[][] options = {
                {"A. Make it easier to apply for assistance.", "B. Allow parents to use education funds for charter schools.",
                        "C. Create welfare to work programs.", "D. Nothing."},
                // Add more answer options for each question
        };

        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.print("Enter your answer (A/B/C/D): ");
            String answer = scanner.nextLine().toUpperCase();
            userAnswers.put(questions[i], getAnswerWeight(answer));
        }

        // Guess the user's political party
        String guessedParty = guessPoliticalParty(userAnswers);
        System.out.println("Based on your answers, I guess you are affiliated with the " + guessedParty + " party.");

        // Ask the user for their actual political party
        System.out.print("What is your actual political party affiliation? ");
        String actualParty = scanner.nextLine();

        // Update the party answer data
        updatePartyAnswerData(actualParty, userAnswers);
    }

    private static String guessPoliticalParty(Map<String, Integer> userAnswers) {
        Map<String, Integer> partyScores = new HashMap<>();

        for (String party : PARTIES) {
            int score = 0;
            for (Map.Entry<String, Integer> entry : userAnswers.entrySet()) {
                String question = entry.getKey();
                int answerWeight = entry.getValue();
                score += PARTY_ANSWERS.getOrDefault(party, new HashMap<>()).getOrDefault(question, 0) * answerWeight;
            }
            partyScores.put(party, score);
        }

        return Collections.max(partyScores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static void updatePartyAnswerData(String party, Map<String, Integer> userAnswers) {
        Map<String, Integer> partyAnswers = PARTY_ANSWERS.getOrDefault(party, new HashMap<>());
        for (Map.Entry<String, Integer> entry : userAnswers.entrySet()) {
            String question = entry.getKey();
            int answerWeight = entry.getValue();
            partyAnswers.put(question, partyAnswers.getOrDefault(question, 0) + answerWeight);
        }
        PARTY_ANSWERS.put(party, partyAnswers);
    }

    private static int getAnswerWeight(String answer) {
        // Assign weights to answers based on their intensity
        // Modify this method to adjust the weights according to your requirements
        switch (answer) {
            case "A":
                return 3;
            case "B":
                return 2;
            case "C":
                return 1;
            case "D":
                return 0;
            default:
                return 0;
        }
    }

    private static void loadPartyAnswerData() {
        // Load party answer data from a file or database
        // Implement this method based on your data storage mechanism
    }

    private static void savePartyAnswerData() {
        // Save party answer data to a file or database
        // Implement this method based on your data storage mechanism
    }
}