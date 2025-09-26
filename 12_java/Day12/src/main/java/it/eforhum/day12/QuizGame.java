package it.eforhum.day12;

import java.util.*;

/**
 * Main quiz game class that handles the gameplay mechanics.
 * Manages quiz sessions, scoring, and player interactions.
 */
public class QuizGame {
    private static final int MAX_QUIZ_QUESTIONS = 10; // Maximum questions per quiz
    private Questionnaire questionnaire;
    private WallOfFame wallOfFame;
    private Scanner scanner;
    private Random random;

    /**
     * Constructor that initializes the quiz game.
     * 
     * @param questionnaire The questionnaire containing questions
     * @param wallOfFame The Wall of Fame for high scores
     */
    public QuizGame(Questionnaire questionnaire, WallOfFame wallOfFame) {
        this.questionnaire = questionnaire;
        this.wallOfFame = wallOfFame;
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    /**
     * Starts a new quiz game session.
     */
    public void startQuiz() {
        if (questionnaire.isEmpty()) {
            System.out.println("No questions available! Please add some questions first.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    🎯 QUIZ GAME STARTED! 🎯");
        System.out.println("=".repeat(60));

        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();
        if (playerName.isEmpty()) {
            playerName = "Anonymous Player";
        }

        System.out.println("\nWelcome, " + playerName + "!");
        System.out.println("Answer the multiple choice questions. Type the number of your choice.");
        System.out.printf("You will answer up to %d questions.\n", MAX_QUIZ_QUESTIONS);
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        // Get shuffled questions for the game (limited to MAX_QUIZ_QUESTIONS)
        List<Question> gameQuestions = questionnaire.getShuffledQuestions(MAX_QUIZ_QUESTIONS);
        
        if (gameQuestions.isEmpty()) {
            System.out.println("No questions available for the quiz!");
            return;
        }
        
        int totalScore = 0;
        int correctAnswers = 0;
        int questionNumber = 1;

        // Play through all questions
        for (Question question : gameQuestions) {
            System.out.println("\n" + "-".repeat(60));
            System.out.printf("Question %d of %d\n", questionNumber, gameQuestions.size());
            System.out.println("-".repeat(60));

            int pointsEarned = askQuestion(question);
            totalScore += pointsEarned;
            
            if (pointsEarned > 0) {
                correctAnswers++;
            }

            questionNumber++;
        }

        // Show final results
        showGameResults(playerName, totalScore, gameQuestions.size(), correctAnswers);

        // Check if player made it to Wall of Fame
        boolean madeItToWallOfFame = wallOfFame.addScore(playerName, totalScore, gameQuestions.size(), correctAnswers);
        
        if (madeItToWallOfFame) {
            System.out.println("\n🎉 CONGRATULATIONS! You made it to the Wall of Fame! 🎉");
        } else if (wallOfFame.qualifiesForWallOfFame(totalScore)) {
            System.out.println("\n✨ Great job! Your score qualifies for the Wall of Fame!");
        } else {
            int minScore = wallOfFame.getMinScoreForWallOfFame();
            System.out.printf("\n💪 Keep trying! You need %d points to enter the Wall of Fame.\n", minScore);
        }

        // Save the updated Wall of Fame
        wallOfFame.saveToFile();

        System.out.println("\nWould you like to:");
        System.out.println("1. View Wall of Fame");
        System.out.println("2. Play again");
        System.out.println("3. Return to main menu");
        System.out.print("Choose an option (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    wallOfFame.displayWallOfFame();
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();
                    break;
                case 2:
                    startQuiz();
                    break;
                case 3:
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            // Just continue to main menu
        }
    }

    /**
     * Asks a single question and returns the points earned.
     * 
     * @param question The question to ask
     * @return Points earned (question points if correct, 0 if incorrect)
     */
    private int askQuestion(Question question) {
        System.out.println("📋 " + question.getQuestionText());
        System.out.println("    (Worth " + question.getPoints() + " points)");
        System.out.println();

        // Get shuffled answers for display
        List<String> answers = new ArrayList<>(question.getPossibleAnswers());
        Collections.shuffle(answers);

        // Find the index of the correct answer in the shuffled list
        int correctIndex = -1;
        for (int i = 0; i < answers.size(); i++) {
            if (question.isCorrectAnswer(answers.get(i))) {
                correctIndex = i;
                break;
            }
        }

        // Display the options
        for (int i = 0; i < answers.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, answers.get(i));
        }

        System.out.print("\nYour answer (1-" + answers.size() + "): ");
        
        try {
            int userChoice = Integer.parseInt(scanner.nextLine().trim());
            
            if (userChoice < 1 || userChoice > answers.size()) {
                System.out.println("❌ Invalid choice! No points awarded.");
                return 0;
            }

            // Check if the selected answer is correct
            if (userChoice - 1 == correctIndex) {
                System.out.println("✅ Correct! You earned " + question.getPoints() + " points!");
                return question.getPoints();
            } else {
                System.out.printf("❌ Incorrect! The correct answer was: %s\n", question.getCorrectAnswer());
                return 0;
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input! Please enter a number. No points awarded.");
            return 0;
        }
    }

    /**
     * Shows the final game results.
     * 
     * @param playerName Name of the player
     * @param totalScore Total score achieved
     * @param totalQuestions Total number of questions
     * @param correctAnswers Number of correct answers
     */
    private void showGameResults(String playerName, int totalScore, int totalQuestions, int correctAnswers) {
        double accuracy = (double) correctAnswers / totalQuestions * 100.0;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    📊 GAME RESULTS 📊");
        System.out.println("=".repeat(60));
        System.out.printf("Player: %s\n", playerName);
        System.out.printf("Final Score: %d points\n", totalScore);
        System.out.printf("Questions Answered: %d\n", totalQuestions);
        System.out.printf("Correct Answers: %d\n", correctAnswers);
        System.out.printf("Accuracy: %.1f%%\n", accuracy);
        System.out.println("=".repeat(60));

        // Give encouraging feedback based on performance
        if (accuracy >= 90) {
            System.out.println("🌟 Outstanding performance! You're a quiz master! 🌟");
        } else if (accuracy >= 75) {
            System.out.println("🎯 Great job! You really know your stuff! 🎯");
        } else if (accuracy >= 60) {
            System.out.println("👍 Good work! Keep studying and you'll improve! 👍");
        } else if (accuracy >= 40) {
            System.out.println("📚 Not bad! More practice will help you excel! 📚");
        } else {
            System.out.println("💪 Don't give up! Everyone starts somewhere! 💪");
        }
    }

    /**
     * Starts a practice mode where wrong answers are explained.
     */
    public void startPracticeMode() {
        if (questionnaire.isEmpty()) {
            System.out.println("No questions available! Please add some questions first.");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                   📚 PRACTICE MODE 📚");
        System.out.println("=".repeat(60));
        System.out.println("In practice mode, you can take your time and learn from mistakes.");
        System.out.println("Your score won't be saved to the Wall of Fame.");
        System.out.printf("You will practice with up to %d questions.\n", MAX_QUIZ_QUESTIONS);
        System.out.println("Press Enter to start...");
        scanner.nextLine();

        List<Question> practiceQuestions = questionnaire.getShuffledQuestions(MAX_QUIZ_QUESTIONS);
        int totalScore = 0;
        int correctAnswers = 0;
        int questionNumber = 1;

        for (Question question : practiceQuestions) {
            System.out.println("\n" + "-".repeat(60));
            System.out.printf("Practice Question %d of %d\n", questionNumber, practiceQuestions.size());
            System.out.println("-".repeat(60));

            int pointsEarned = askQuestionPractice(question);
            totalScore += pointsEarned;
            
            if (pointsEarned > 0) {
                correctAnswers++;
            }

            System.out.println("\nPress Enter to continue to next question...");
            scanner.nextLine();
            questionNumber++;
        }

        showPracticeResults(totalScore, practiceQuestions.size(), correctAnswers);
    }

    /**
     * Asks a practice question with additional explanations.
     */
    private int askQuestionPractice(Question question) {
        int pointsEarned = askQuestion(question);
        
        if (pointsEarned == 0) {
            System.out.println("\n💡 All possible answers were:");
            List<String> allAnswers = question.getPossibleAnswers();
            for (int i = 0; i < allAnswers.size(); i++) {
                String marker = question.isCorrectAnswer(allAnswers.get(i)) ? " ✓ (Correct)" : "";
                System.out.printf("   • %s%s\n", allAnswers.get(i), marker);
            }
        }
        
        return pointsEarned;
    }

    /**
     * Shows practice mode results.
     */
    private void showPracticeResults(int totalScore, int totalQuestions, int correctAnswers) {
        double accuracy = (double) correctAnswers / totalQuestions * 100.0;
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                   📊 PRACTICE RESULTS 📊");
        System.out.println("=".repeat(60));
        System.out.printf("Practice Score: %d points\n", totalScore);
        System.out.printf("Questions Answered: %d\n", totalQuestions);
        System.out.printf("Correct Answers: %d\n", correctAnswers);
        System.out.printf("Accuracy: %.1f%%\n", accuracy);
        System.out.println("=".repeat(60));
        System.out.println("Great practice session! Ready for the real quiz?");
    }

    /**
     * Shows the main game menu.
     */
    public void showGameMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("                🎮 QUIZ GAME 🎮");
            System.out.println("=".repeat(50));
            System.out.println("1. Start Quiz Game");
            System.out.println("2. Practice Mode");
            System.out.println("3. View Wall of Fame");
            System.out.println("4. View Statistics");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        startQuiz();
                        break;
                    case 2:
                        startPracticeMode();
                        break;
                    case 3:
                        wallOfFame.displayWallOfFame();
                        System.out.println("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;
                    case 4:
                        showStatistics();
                        break;
                    case 0:
                        System.out.println("Returning to main menu...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Shows game statistics.
     */
    private void showStatistics() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                     📈 GAME STATISTICS 📈");
        System.out.println("=".repeat(60));
        System.out.printf("Total Questions Available: %d\n", questionnaire.getQuestionCount());
        System.out.printf("Players on Wall of Fame: %d\n", wallOfFame.getScoreCount());
        
        if (wallOfFame.getScoreCount() > 0) {
            System.out.printf("Minimum Score for Wall of Fame: %d points\n", 
                wallOfFame.getMinScoreForWallOfFame());
        }
        
        System.out.println("=".repeat(60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Gets the questionnaire used by this game.
     * 
     * @return The questionnaire
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * Gets the Wall of Fame used by this game.
     * 
     * @return The Wall of Fame
     */
    public WallOfFame getWallOfFame() {
        return wallOfFame;
    }
}