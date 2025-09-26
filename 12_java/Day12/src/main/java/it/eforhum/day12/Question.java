package it.eforhum.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a single quiz question with multiple choice answers.
 * Contains the question text, correct answer, all possible answers, and points awarded.
 */
public class Question {
    private String questionText;
    private String correctAnswer;
    private List<String> possibleAnswers;
    private int points;

    /**
     * Constructor for creating a new question.
     * 
     * @param questionText The text of the question
     * @param correctAnswer The correct answer (should be included in possibleAnswers)
     * @param possibleAnswers List of all possible answers including the correct one
     * @param points Points awarded for correct answer
     */
    public Question(String questionText, String correctAnswer, List<String> possibleAnswers, int points) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = new ArrayList<>(possibleAnswers);
        this.points = points;
    }

    /**
     * Constructor that takes possible answers as a comma-separated string.
     * The correct answer must be included in the possible answers.
     * 
     * @param questionText The text of the question
     * @param correctAnswer The correct answer
     * @param possibleAnswersString Comma-separated string of possible answers
     * @param points Points awarded for correct answer
     */
    public Question(String questionText, String correctAnswer, String possibleAnswersString, int points) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.possibleAnswers = Arrays.asList(possibleAnswersString.split(","));
        // Trim whitespace from each answer
        this.possibleAnswers = this.possibleAnswers.stream()
                .map(String::trim)
                .collect(ArrayList::new, (list, item) -> list.add(item), (list1, list2) -> list1.addAll(list2));
        this.points = points;
        
        // Ensure the correct answer is in the possible answers list
        boolean correctAnswerFound = this.possibleAnswers.stream()
                .anyMatch(answer -> answer.equalsIgnoreCase(correctAnswer.trim()));
        if (!correctAnswerFound) {
            // Add correct answer to the beginning of the list if not found
            this.possibleAnswers.add(0, correctAnswer.trim());
        }
    }

    /**
     * Checks if the provided answer is correct.
     * 
     * @param answer The answer to check
     * @return true if the answer is correct, false otherwise
     */
    public boolean isCorrectAnswer(String answer) {
        return correctAnswer.trim().equalsIgnoreCase(answer.trim());
    }

    /**
     * Converts the question to a tab-separated format for file storage.
     * Format: questionText\tcorrectAnswer\tpossibleAnswers\tpoints
     * 
     * @return Tab-separated string representation
     */
    public String toFileFormat() {
        String possibleAnswersString = String.join(",", possibleAnswers);
        return String.join("\t", questionText, correctAnswer, possibleAnswersString, String.valueOf(points));
    }

    /**
     * Creates a Question object from a tab-separated line.
     * 
     * @param line Tab-separated line containing question data
     * @return Question object or null if parsing fails
     */
    public static Question fromFileFormat(String line) {
        try {
            String[] parts = line.split("\t");
            if (parts.length != 4) {
                return null;
            }
            
            String questionText = parts[0].trim();
            String correctAnswer = parts[1].trim();
            String possibleAnswersString = parts[2].trim();
            int points = Integer.parseInt(parts[3].trim());
            
            return new Question(questionText, correctAnswer, possibleAnswersString, points);
        } catch (Exception e) {
            System.err.println("Error parsing question line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getPossibleAnswers() {
        return new ArrayList<>(possibleAnswers);
    }

    public void setPossibleAnswers(List<String> possibleAnswers) {
        this.possibleAnswers = new ArrayList<>(possibleAnswers);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return String.format("Question: %s | Correct: %s | Options: %s | Points: %d", 
                           questionText, correctAnswer, possibleAnswers, points);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Question question = (Question) obj;
        return points == question.points &&
               questionText.equals(question.questionText) &&
               correctAnswer.equals(question.correctAnswer) &&
               possibleAnswers.equals(question.possibleAnswers);
    }

    @Override
    public int hashCode() {
        return questionText.hashCode() + correctAnswer.hashCode() + possibleAnswers.hashCode() + points;
    }
}