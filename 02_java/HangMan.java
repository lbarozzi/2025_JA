import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * HangMan game implementation
 * Allows selecting difficulty levels based on word length
 */
public class HangMan {
    private String[] words = {
        "programming", "computer", "algorithm", "language", "interface",
        "inheritance", "polymorphism", "encapsulation", "abstraction", "exception",
        "framework", "database", "development", "function", "variable"
    };
    private String wordToGuess;
    private char[] currentGuess;
    private ArrayList<Character> guessedLetters;
    private int wrongGuesses;
    private final int MAX_WRONG_GUESSES = 6;
    
    public HangMan(int difficulty) {
        Random random = new Random();
        
        // Select word based on difficulty
        ArrayList<String> difficultyWords = new ArrayList<>();
        for (String word : words) {
            if ((difficulty == 1 && word.length() <= 6) ||
                (difficulty == 2 && word.length() > 6 && word.length() <= 10) ||
                (difficulty == 3 && word.length() > 10)) {
                difficultyWords.add(word);
            }
        }
        
        // If no words match difficulty, use all words
        if (difficultyWords.isEmpty()) {
            wordToGuess = words[random.nextInt(words.length)];
        } else {
            wordToGuess = difficultyWords.get(random.nextInt(difficultyWords.size()));
        }
        
        // Initialize game state
        currentGuess = new char[wordToGuess.length()];
        Arrays.fill(currentGuess, '_');
        guessedLetters = new ArrayList<>();
        wrongGuesses = 0;
    }
    
    /**
     * Display the current state of the hangman
     */
    public void displayHangman() {
        System.out.println("\n----- HANGMAN -----");
        System.out.println(" _________");
        System.out.println(" |        |");
        
        if (wrongGuesses >= 1)
            System.out.println(" |        O");
        else
            System.out.println(" |");
            
        if (wrongGuesses >= 2) {
            if (wrongGuesses == 2)
                System.out.println(" |        |");
            else if (wrongGuesses == 3)
                System.out.println(" |       /|");
            else
                System.out.println(" |       /|\\");
        } else {
            System.out.println(" |");
        }
        
        if (wrongGuesses >= 5) {
            if (wrongGuesses == 5)
                System.out.println(" |       /");
            else
                System.out.println(" |       / \\");
        } else {
            System.out.println(" |");
        }
        
        System.out.println(" |");
        System.out.println("_|_");
        System.out.println();
    }
    
    /**
     * Display the current state of the word being guessed
     */
    public void displayWord() {
        System.out.print("Word: ");
        for (char c : currentGuess) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        System.out.print("Guessed letters: ");
        for (char c : guessedLetters) {
            System.out.print(c + " ");
        }
        System.out.println();
        
        System.out.println("Wrong guesses: " + wrongGuesses + "/" + MAX_WRONG_GUESSES);
    }
    
    /**
     * Make a guess with a letter
     * @param letter the letter to guess
     * @return true if the letter is in the word, false otherwise
     */
    public boolean makeGuess(char letter) {
        letter = Character.toLowerCase(letter);
        
        // Check if letter has already been guessed
        if (guessedLetters.contains(letter)) {
            System.out.println("You already guessed that letter!");
            return false;
        }
        
        guessedLetters.add(letter);
        
        boolean foundLetter = false;
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter) {
                currentGuess[i] = letter;
                foundLetter = true;
            }
        }
        
        if (!foundLetter) {
            wrongGuesses++;
            System.out.println("Wrong guess!");
        } else {
            System.out.println("Good guess!");
        }
        
        return foundLetter;
    }
    
    /**
     * Check if the player has won
     */
    public boolean hasWon() {
        for (char c : currentGuess) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if the player has lost
     */
    public boolean hasLost() {
        return wrongGuesses >= MAX_WRONG_GUESSES;
    }
    
    /**
     * Main game loop for Hangman
     * @return true if player won, false if lost
     */
    public boolean playGame(Scanner scanner) {
        System.out.println("\nWelcome to Hangman!");
        System.out.println("Guess the word one letter at a time.");
        System.out.println("You can make " + MAX_WRONG_GUESSES + " wrong guesses before losing.");
        
        while (!hasWon() && !hasLost()) {
            displayHangman();
            displayWord();
            
            System.out.print("Enter a letter: ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println("Please enter a letter.");
                continue;
            }
            
            char letter = input.charAt(0);
            if (!Character.isLetter(letter)) {
                System.out.println("Please enter a valid letter.");
                continue;
            }
            
            makeGuess(letter);
        }
        
        displayHangman();
        displayWord();
        
        if (hasWon()) {
            System.out.println("Congratulations! You guessed the word: " + wordToGuess);
            return true;
        } else {
            System.out.println("Game over! The word was: " + wordToGuess);
            return false;
        }
    }
}
