package it.eforhum.day12;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Manages a collection of quiz questions and handles file I/O operations.
 * Can read from and write to a tab-separated file format.
 */
public class Questionnaire {
    private List<Question> questions;
    private String filePath;

    /**
     * Constructor that initializes an empty questionnaire.
     * 
     * @param filePath Path to the data file
     */
    public Questionnaire(String filePath) {
        this.questions = new ArrayList<>();
        this.filePath = filePath;
    }

    /**
     * Loads questions from the specified file.
     * File format: questionText\tcorrectAnswer\tpossibleAnswers\tpoints
     * 
     * @return true if loading was successful, false otherwise
     */
    public boolean loadFromFile() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("File does not exist: " + filePath + ". Creating new empty questionnaire.");
                return true;
            }

            List<String> lines = Files.readAllLines(path);
            questions.clear();
            
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                
                Question question = Question.fromFileFormat(line);
                if (question != null) {
                    questions.add(question);
                } else {
                    System.err.println("Failed to parse line " + (i + 1) + ": " + line);
                }
            }
            
            System.out.println("Loaded " + questions.size() + " questions from " + filePath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves all questions to the file.
     * 
     * @return true if saving was successful, false otherwise
     */
    public boolean saveToFile() {
        try {
            // Create parent directories if they don't exist
            Path path = Paths.get(filePath);
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            
            List<String> lines = new ArrayList<>();
            for (Question question : questions) {
                lines.add(question.toFileFormat());
            }
            
            Files.write(path, lines);
            System.out.println("Saved " + questions.size() + " questions to " + filePath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new question to the questionnaire.
     * 
     * @param question The question to add
     */
    public void addQuestion(Question question) {
        if (question != null) {
            questions.add(question);
        }
    }

    /**
     * Removes a question from the questionnaire.
     * 
     * @param question The question to remove
     * @return true if the question was removed, false if not found
     */
    public boolean removeQuestion(Question question) {
        return questions.remove(question);
    }

    /**
     * Removes a question at the specified index.
     * 
     * @param index The index of the question to remove
     * @return true if successful, false if index is out of bounds
     */
    public boolean removeQuestionAt(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Gets a question at the specified index.
     * 
     * @param index The index of the question
     * @return The question or null if index is out of bounds
     */
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

    /**
     * Updates a question at the specified index.
     * 
     * @param index The index of the question to update
     * @param newQuestion The new question data
     * @return true if successful, false if index is out of bounds
     */
    public boolean updateQuestion(int index, Question newQuestion) {
        if (index >= 0 && index < questions.size() && newQuestion != null) {
            questions.set(index, newQuestion);
            return true;
        }
        return false;
    }

    /**
     * Gets a copy of all questions.
     * 
     * @return List of all questions
     */
    public List<Question> getAllQuestions() {
        return new ArrayList<>(questions);
    }

    /**
     * Gets all questions in a shuffled order.
     * 
     * @return Shuffled list of all questions
     */
    public List<Question> getShuffledQuestions() {
        List<Question> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    /**
     * Gets a limited number of questions in a shuffled order.
     * 
     * @param maxQuestions Maximum number of questions to return
     * @return Shuffled list of questions (limited to maxQuestions)
     */
    public List<Question> getShuffledQuestions(int maxQuestions) {
        List<Question> shuffled = new ArrayList<>(questions);
        Collections.shuffle(shuffled);
        
        if (shuffled.size() <= maxQuestions) {
            return shuffled;
        }
        
        return shuffled.subList(0, maxQuestions);
    }

    /**
     * Gets the total number of questions.
     * 
     * @return Number of questions
     */
    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * Checks if the questionnaire is empty.
     * 
     * @return true if no questions exist
     */
    public boolean isEmpty() {
        return questions.isEmpty();
    }

    /**
     * Clears all questions from the questionnaire.
     */
    public void clear() {
        questions.clear();
    }

    /**
     * Gets the file path used for this questionnaire.
     * 
     * @return The file path
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets a new file path for this questionnaire.
     * 
     * @param filePath The new file path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return String.format("Questionnaire[%d questions, file: %s]", questions.size(), filePath);
    }
}