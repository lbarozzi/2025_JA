import java.util.Scanner;
import java.util.HashMap;

/**
 * Day2 class handles the player management, game statistics and user interaction
 * for various games including Tic-tac-toe and Hangman
 */
public class Day2 {
    private Scanner scanner;
    private String playerName;
    private HashMap<String, PlayerStats> stats;
    
    public Day2() {
        scanner = new Scanner(System.in);
        stats = new HashMap<>();
    }
    
    /**
     * Start the application and handle main menu
     */
    public void start() {
        System.out.println("Welcome to Game Center!");
        System.out.print("Please enter your name: ");
        playerName = scanner.nextLine();
        
        // Initialize player stats if not exists
        if (!stats.containsKey(playerName)) {
            stats.put(playerName, new PlayerStats());
        }
        
        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    playTicTacToe();
                    break;
                case 2:
                    playHangman();
                    break;
                case 3:
                    showStats();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for playing!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
    
    /**
     * Print the main menu options
     */
    private void printMenu() {
        System.out.println("\n----- GAME CENTER MENU -----");
        System.out.println("1. Play Tic-Tac-Toe");
        System.out.println("2. Play Hangman");
        System.out.println("3. Show statistics");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
    }
    
    /**
     * Get menu choice from user
     */
    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0; // Invalid input
        }
    }
    
    /**
     * Start a Tic-Tac-Toe game with difficulty selection
     */
    private void playTicTacToe() {
        System.out.println("\n----- TIC-TAC-TOE DIFFICULTY -----");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Select difficulty: ");
        
        int difficulty = 2; // Default medium
        try {
            difficulty = Integer.parseInt(scanner.nextLine());
            if (difficulty < 1 || difficulty > 3) {
                System.out.println("Invalid difficulty. Setting to medium.");
                difficulty = 2;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Setting difficulty to medium.");
        }
        
        // Set depth based on difficulty
        int depth = difficulty == 1 ? 2 : (difficulty == 2 ? 5 : 9);
        
        // Create and start game using the external TicTacToe class
        TicTacToe game = new TicTacToe(difficulty, depth);
        game.playGame(scanner);
        
        // Update stats
        PlayerStats playerStats = stats.get(playerName);
        playerStats.gamesPlayed++;
        
        // Note: Currently not tracking wins/losses from TicTacToe game
        // as the method doesn't return the result anymore
    }
    
    /**
     * Start a Hangman game with difficulty selection
     */
    private void playHangman() {
        System.out.println("\n----- HANGMAN DIFFICULTY -----");
        System.out.println("1. Easy (shorter words)");
        System.out.println("2. Medium (medium-length words)");
        System.out.println("3. Hard (longer words)");
        System.out.print("Select difficulty: ");
        
        int difficulty = 2; // Default medium
        try {
            difficulty = Integer.parseInt(scanner.nextLine());
            if (difficulty < 1 || difficulty > 3) {
                System.out.println("Invalid difficulty. Setting to medium.");
                difficulty = 2;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Setting difficulty to medium.");
        }
        
        // Create and start game using the external HangMan class
        HangMan game = new HangMan(difficulty);
        boolean result = game.playGame(scanner);
        
        // Update stats
        PlayerStats playerStats = stats.get(playerName);
        playerStats.hangmanGamesPlayed++;
        
        if (result) {
            playerStats.hangmanWins++;
            System.out.println("Congratulations " + playerName + "! You won!");
        } else {
            System.out.println("Better luck next time!");
        }
    }
    
    /**
     * Display player statistics
     */
    private void showStats() {
        System.out.println("\n----- STATISTICS FOR " + playerName.toUpperCase() + " -----");
        PlayerStats playerStats = stats.get(playerName);
        
        System.out.println("\nTic-Tac-Toe Stats:");
        System.out.println("Games played: " + playerStats.gamesPlayed);
        System.out.println("Wins: " + playerStats.wins);
        System.out.println("Losses: " + playerStats.losses);
        System.out.println("Draws: " + playerStats.draws);
        
        // Calculate win percentage for Tic-Tac-Toe
        if (playerStats.gamesPlayed > 0) {
            double winPercent = (double) playerStats.wins / playerStats.gamesPlayed * 100;
            System.out.printf("Win rate: %.1f%%\n", winPercent);
        }
        
        System.out.println("\nHangman Stats:");
        System.out.println("Games played: " + playerStats.hangmanGamesPlayed);
        System.out.println("Wins: " + playerStats.hangmanWins);
        
        // Calculate win percentage for Hangman
        if (playerStats.hangmanGamesPlayed > 0) {
            double winPercent = (double) playerStats.hangmanWins / playerStats.hangmanGamesPlayed * 100;
            System.out.printf("Win rate: %.1f%%\n", winPercent);
        }
    }
    
    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        Day2 game = new Day2();
        game.start();
    }
}
