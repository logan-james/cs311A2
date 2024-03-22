import java.util.*;

public class PoliticalPartyGuesser {
    // Declare an array of political parties
    private static final String[] PARTIES = {"Democratic", "Republican", "Libertarian", "Green"};
    
    // Create a map to store the party answers and their corresponding weights
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
        // Create a map to store the user's answers and their corresponding weights
        Map<String, Integer> userAnswers = new HashMap<>();

        // Define the survey questions
        String[] questions = {
                //Question 1
                "What should the government do to help the poor?",
                //Question 2
                "What is your stance on gun control?",
                //Question 3
                "How do you view the role of government in the economy?",
        };

        // Define the answer options for each question
        String[][] options = {
                //Question 1 
                {"A. Make it easier to apply for assistance.", 
                "B. Allow parents to use education funds for charter schools.",
                "C. Create welfare to work programs.",
                "D. Nothing."},
                
                //Question 2
                {"A. I support strict gun control laws.", 
                "B. I believe in upholding the Second Amendment rights but with reasonable regulations.",
                "C. I strongly oppose any form of gun control",
                "D. I advocate for significant restrictions on gun ownership and promote non-violent resolutions"},

                //Question 3
                {"A. I believe in a strong government role in regulating the economy and providing social services",
                "B. I support free-market principles and minimal government intervention",
                "C. I advocate for a laissez-faire approach where the governments role in the economy is limited",
                "D. I endorse sustainable and equitable economic policies that prioritize environment over profit"},
        };

        // Loop through the questions and get the user's answers
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.print("Enter your answer (A/B/C/D): ");
            String answer = scanner.nextLine().toUpperCase();
            userAnswers.put(questions[i], getAnswerWeight(answer, i));
        }

        // Guess the user's political party based on their answers
        String guessedParty = guessPoliticalParty(userAnswers);
        System.out.println("Based on your answers, I guess you are affiliated with the " + guessedParty + " party.");

        // Ask the user for their actual political party
        System.out.print("What is your actual political party affiliation? ");
        String actualParty = scanner.nextLine();

        // Update the party answer data with the user's actual party affiliation
        updatePartyAnswerData(actualParty, userAnswers);
    }

    private static String guessPoliticalParty(Map<String, Integer> userAnswers) {
        // Create a map to store the scores for each political party
        Map<String, Double> partyScores = new HashMap<>();

        // Initialize the scores for each party to 0
        for (String party : PARTIES) {
            partyScores.put(party, 0.0);
        }

        // Calculate the score for each political party based on the user's answers
        for (Map.Entry<String, Integer> entry : userAnswers.entrySet()) {
            String question = entry.getKey();
            int answerWeight = entry.getValue();

            // Update the score for each party based on the answer weight
            for (String party : PARTIES) {
                double score = partyScores.get(party);
                score += PARTY_ANSWERS.getOrDefault(party, new HashMap<>()).getOrDefault(question, 0) * answerWeight;
                partyScores.put(party, score);
            }
        }

        // Return the political party with the highest score
        return Collections.max(partyScores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private static void updatePartyAnswerData(String party, Map<String, Integer> userAnswers) {
        // Get the existing answer data for the user's actual political party
        Map<String, Integer> partyAnswers = PARTY_ANSWERS.getOrDefault(party, new HashMap<>());
        
        // Update the party answer data with the user's answers
        for (Map.Entry<String, Integer> entry : userAnswers.entrySet()) {
            String question = entry.getKey();
            int answerWeight = entry.getValue();
            partyAnswers.put(question, partyAnswers.getOrDefault(question, 0) + answerWeight);
        }
        
        // Store the updated party answer data
        PARTY_ANSWERS.put(party, partyAnswers);
    }

    private static int getAnswerWeight(String answer, int questionIndex) {
        // Assign weights to answers based on their alignment with different political parties
        // Modify these weights according to your understanding of political party positions
        switch (questionIndex) {
            case 0: // Question 1
                switch (answer) {
                    case "A":
                        return 3; // Democratic
                    case "B":
                        return 2; // Republican
                    case "C":
                        return 1; // Libertarian
                    case "D":
                        return 0; // Green
                    default:
                        return 0;
                }
            case 1: // Question 2
                switch (answer) {
                    case "A":
                        return 3; // Democratic
                    case "B":
                        return 2; // Republican
                    case "C":
                        return 0; // Libertarian
                    case "D":
                        return 1; // Green
                    default:
                        return 0;
                }
            case 2: // Question 3
                switch (answer) {
                    case "A":
                        return 3; // Democratic
                    case "B":
                        return 2; // Republican
                    case "C":
                        return 1; // Libertarian
                    case "D":
                        return 0; // Green
                    default:
                        return 0;
                }
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