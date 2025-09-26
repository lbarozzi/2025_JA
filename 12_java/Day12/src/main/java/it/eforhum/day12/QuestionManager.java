package it.eforhum.day12;

import java.util.List;
import java.util.Scanner;

/**
 * Provides CRUD (Create, Read, Update, Delete) operations for managing quiz questions.
 * Acts as a service layer between the UI and the Questionnaire data.
 */
public class QuestionManager {
    private Questionnaire questionnaire;
    private Scanner scanner;

    /**
     * Constructor that initializes the manager with a questionnaire.
     * 
     * @param questionnaire The questionnaire to manage
     */
    public QuestionManager(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Creates a new question interactively.
     * Prompts user for all question details.
     * 
     * @return The created question or null if creation was cancelled
     */
    public Question createQuestion() {
        System.out.println("\n=== CREATE NEW QUESTION ===");
        
        System.out.print("Enter question text: ");
        String questionText = scanner.nextLine().trim();
        if (questionText.isEmpty()) {
            System.out.println("Question text cannot be empty.");
            return null;
        }

        System.out.print("Enter correct answer: ");
        String correctAnswer = scanner.nextLine().trim();
        if (correctAnswer.isEmpty()) {
            System.out.println("Correct answer cannot be empty.");
            return null;
        }

        System.out.print("Enter all possible answers (comma-separated, include correct answer): ");
        String possibleAnswers = scanner.nextLine().trim();
        if (possibleAnswers.isEmpty()) {
            System.out.println("Possible answers cannot be empty.");
            return null;
        }

        System.out.print("Enter points for this question: ");
        try {
            int points = Integer.parseInt(scanner.nextLine().trim());
            
            Question newQuestion = new Question(questionText, correctAnswer, possibleAnswers, points);
            questionnaire.addQuestion(newQuestion);
            System.out.println("Question created successfully!");
            return newQuestion;
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid points value. Please enter a number.");
            return null;
        }
    }

    /**
     * Creates a question programmatically with provided data.
     * 
     * @param questionText The question text
     * @param correctAnswer The correct answer
     * @param possibleAnswers Comma-separated string of possible answers
     * @param points Points awarded for correct answer
     * @return The created question or null if invalid data
     */
    public Question createQuestion(String questionText, String correctAnswer, String possibleAnswers, int points) {
        if (questionText == null || questionText.trim().isEmpty() ||
            correctAnswer == null || correctAnswer.trim().isEmpty() ||
            possibleAnswers == null || possibleAnswers.trim().isEmpty()) {
            System.out.println("Invalid question data provided.");
            return null;
        }

        Question newQuestion = new Question(questionText, correctAnswer, possibleAnswers, points);
        questionnaire.addQuestion(newQuestion);
        System.out.println("Question created successfully!");
        return newQuestion;
    }

    /**
     * Displays all questions in the questionnaire.
     */
    public void readAllQuestions() {
        System.out.println("\n=== ALL QUESTIONS ===");
        
        if (questionnaire.isEmpty()) {
            System.out.println("No questions found.");
            return;
        }

        List<Question> questions = questionnaire.getAllQuestions();
        for (int i = 0; i < questions.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, questions.get(i));
        }
    }

    /**
     * Displays a specific question by index.
     * 
     * @param index The index of the question (0-based)
     */
    public void readQuestion(int index) {
        Question question = questionnaire.getQuestion(index);
        if (question != null) {
            System.out.printf("Question %d: %s\n", index + 1, question);
        } else {
            System.out.println("Question not found at index " + (index + 1));
        }
    }

    /**
     * Updates an existing question interactively.
     * 
     * @param index The index of the question to update (0-based)
     * @return true if update was successful, false otherwise
     */
    public boolean updateQuestion(int index) {
        Question existingQuestion = questionnaire.getQuestion(index);
        if (existingQuestion == null) {
            System.out.println("Question not found at index " + (index + 1));
            return false;
        }

        System.out.println("\n=== UPDATE QUESTION " + (index + 1) + " ===");
        System.out.println("Current question: " + existingQuestion);
        System.out.println("Enter new values (press Enter to keep current value):");

        System.out.print("Question text [" + existingQuestion.getQuestionText() + "]: ");
        String newQuestionText = scanner.nextLine().trim();
        if (newQuestionText.isEmpty()) {
            newQuestionText = existingQuestion.getQuestionText();
        }

        System.out.print("Correct answer [" + existingQuestion.getCorrectAnswer() + "]: ");
        String newCorrectAnswer = scanner.nextLine().trim();
        if (newCorrectAnswer.isEmpty()) {
            newCorrectAnswer = existingQuestion.getCorrectAnswer();
        }

        System.out.print("Possible answers [" + String.join(",", existingQuestion.getPossibleAnswers()) + "]: ");
        String newPossibleAnswers = scanner.nextLine().trim();
        if (newPossibleAnswers.isEmpty()) {
            newPossibleAnswers = String.join(",", existingQuestion.getPossibleAnswers());
        }

        System.out.print("Points [" + existingQuestion.getPoints() + "]: ");
        String pointsInput = scanner.nextLine().trim();
        int newPoints = existingQuestion.getPoints();
        if (!pointsInput.isEmpty()) {
            try {
                newPoints = Integer.parseInt(pointsInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid points value. Keeping current value.");
            }
        }

        Question updatedQuestion = new Question(newQuestionText, newCorrectAnswer, newPossibleAnswers, newPoints);
        boolean success = questionnaire.updateQuestion(index, updatedQuestion);
        
        if (success) {
            System.out.println("Question updated successfully!");
        } else {
            System.out.println("Failed to update question.");
        }
        
        return success;
    }

    /**
     * Updates a question programmatically with provided data.
     * 
     * @param index The index of the question to update
     * @param questionText New question text
     * @param correctAnswer New correct answer
     * @param possibleAnswers New possible answers (comma-separated)
     * @param points New points value
     * @return true if update was successful, false otherwise
     */
    public boolean updateQuestion(int index, String questionText, String correctAnswer, String possibleAnswers, int points) {
        Question updatedQuestion = new Question(questionText, correctAnswer, possibleAnswers, points);
        boolean success = questionnaire.updateQuestion(index, updatedQuestion);
        
        if (success) {
            System.out.println("Question updated successfully!");
        } else {
            System.out.println("Failed to update question at index " + (index + 1));
        }
        
        return success;
    }

    /**
     * Deletes a question at the specified index.
     * 
     * @param index The index of the question to delete (0-based)
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteQuestion(int index) {
        Question questionToDelete = questionnaire.getQuestion(index);
        if (questionToDelete == null) {
            System.out.println("Question not found at index " + (index + 1));
            return false;
        }

        System.out.println("Are you sure you want to delete this question?");
        System.out.println(questionToDelete);
        System.out.print("Type 'yes' to confirm: ");
        
        String confirmation = scanner.nextLine().trim();
        if (!"yes".equalsIgnoreCase(confirmation)) {
            System.out.println("Deletion cancelled.");
            return false;
        }

        boolean success = questionnaire.removeQuestionAt(index);
        if (success) {
            System.out.println("Question deleted successfully!");
        } else {
            System.out.println("Failed to delete question.");
        }
        
        return success;
    }

    /**
     * Deletes a question without confirmation (programmatic deletion).
     * 
     * @param index The index of the question to delete (0-based)
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteQuestionDirect(int index) {
        boolean success = questionnaire.removeQuestionAt(index);
        if (success) {
            System.out.println("Question deleted successfully!");
        } else {
            System.out.println("Failed to delete question at index " + (index + 1));
        }
        return success;
    }

    /**
     * Saves the questionnaire to file.
     * 
     * @return true if save was successful, false otherwise
     */
    public boolean saveQuestionnaire() {
        return questionnaire.saveToFile();
    }

    /**
     * Loads the questionnaire from file.
     * 
     * @return true if load was successful, false otherwise
     */
    public boolean loadQuestionnaire() {
        return questionnaire.loadFromFile();
    }

    /**
     * Gets the total number of questions.
     * 
     * @return Number of questions
     */
    public int getQuestionCount() {
        return questionnaire.getQuestionCount();
    }

    /**
     * Gets the underlying questionnaire object.
     * 
     * @return The questionnaire
     */
    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    /**
     * Interactive menu for CRUD operations.
     */
    public void showCrudMenu() {
        while (true) {
            System.out.println("\n=== QUESTION MANAGEMENT ===");
            System.out.println("1. Create new question");
            System.out.println("2. View all questions");
            System.out.println("3. View specific question");
            System.out.println("4. Update question");
            System.out.println("5. Delete question");
            System.out.println("6. Save to file");
            System.out.println("7. Load from file");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        createQuestion();
                        break;
                    case 2:
                        readAllQuestions();
                        break;
                    case 3:
                        System.out.print("Enter question number (1-" + getQuestionCount() + "): ");
                        int viewIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        readQuestion(viewIndex);
                        break;
                    case 4:
                        if (getQuestionCount() == 0) {
                            System.out.println("No questions to update.");
                            break;
                        }
                        readAllQuestions();
                        System.out.print("Enter question number to update (1-" + getQuestionCount() + "): ");
                        int updateIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        updateQuestion(updateIndex);
                        break;
                    case 5:
                        if (getQuestionCount() == 0) {
                            System.out.println("No questions to delete.");
                            break;
                        }
                        readAllQuestions();
                        System.out.print("Enter question number to delete (1-" + getQuestionCount() + "): ");
                        int deleteIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                        deleteQuestion(deleteIndex);
                        break;
                    case 6:
                        saveQuestionnaire();
                        break;
                    case 7:
                        loadQuestionnaire();
                        break;
                    case 0:
                        System.out.println("Exiting question management.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
}