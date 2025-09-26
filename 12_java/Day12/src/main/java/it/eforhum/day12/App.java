package it.eforhum.day12;

import java.util.Scanner;

/**
 * Quiz Game Application - Main entry point
 * 
 * This application provides a complete quiz management system with:
 * - Question management (CRUD operations)
 * - Quiz gameplay with multiple choice questions
 * - Wall of Fame for top 5 players
 * - File-based persistence for questions and scores
 * 
 * File formats:
 * - Domande.dat: questionText\tcorrectAnswer\tpossibleAnswers\tpoints
 * - WallOfFame.dat: playerName\tscore\tquestionsAnswered\tcorrectAnswers\tdate
 */
public class App {
    private static String QUESTIONS_FILE = "Domande.dat";
    private static String SCORES_FILE = "WallOfFame.dat";
    
    private static Questionnaire questionnaire;
    private static WallOfFame wallOfFame;
    private static QuestionManager questionManager;
    private static QuizGame quizGame;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.out.println("🎯 Welcome to the Quiz Game System! 🎯");
        if (args.length >= 2) {
            QUESTIONS_FILE = args[0];
            SCORES_FILE = args[1];
            System.out.printf("Using custom files: Questions='%s', Scores='%s'\n", QUESTIONS_FILE, SCORES_FILE);
        } else {
            System.out.println("Using default files: Questions='Domande.dat', Scores='WallOfFame.dat'");
        }

        // Initialize system components
        initializeSystem();
        
        // Show main menu
        showMainMenu();
        
        // Cleanup
        scanner.close();
        System.out.println("Thank you for using Quiz Game System! Goodbye! 👋");
    }

    /**
     * Initializes all system components and loads data from files.
     */
    private static void initializeSystem() {
        System.out.println("Initializing Quiz Game System...");
        
        // Initialize core components
        questionnaire = new Questionnaire(QUESTIONS_FILE);
        wallOfFame = new WallOfFame(SCORES_FILE);
        questionManager = new QuestionManager(questionnaire);
        quizGame = new QuizGame(questionnaire, wallOfFame);
        scanner = new Scanner(System.in);
        
        // Load data from files
        System.out.println("Loading questions...");
        questionnaire.loadFromFile();
        
        System.out.println("Loading Wall of Fame...");
        wallOfFame.loadFromFile();
        
        // Create sample questions if none exist
        if (questionnaire.isEmpty()) {
            System.out.println("No questions found. Creating sample questions...");
            createSampleQuestions();
            questionManager.saveQuestionnaire();
        }
        
        System.out.println("System initialized successfully!\n");
    }

    /**
     * Creates sample questions for demonstration purposes.
     */
    private static void createSampleQuestions() {
        // Java programming questions
        questionManager.createQuestion(
            "What is the correct way to declare a variable in Java?", 
            "int x = 5;", 
            "int x = 5;,var x = 5,x = 5 int,integer x = 5", 
            10
        );
        
        questionManager.createQuestion(
            "Which keyword is used to create a class in Java?", 
            "class", 
            "class,Class,CLASS,define", 
            5
        );
        
        questionManager.createQuestion(
            "What is the entry point of a Java application?", 
            "main method", 
            "main method,start method,run method,init method", 
            15
        );
        
        questionManager.createQuestion(
            "Which access modifier makes a member visible to all classes?", 
            "public", 
            "public,private,protected,default", 
            8
        );
        
        // General knowledge questions
        questionManager.createQuestion(
            "What is the capital of Italy?", 
            "Rome", 
            "Rome,Milan,Naples,Turin", 
            5
        );
        
        questionManager.createQuestion(
            "In which year was the first moon landing?", 
            "1969", 
            "1969,1968,1970,1971", 
            12
        );
        
        questionManager.createQuestion(
            "What is the largest planet in our solar system?", 
            "Jupiter", 
            "Jupiter,Saturn,Earth,Mars", 
            7
        );
        
        questionManager.createQuestion(
            "Who wrote 'Romeo and Juliet'?", 
            "William Shakespeare", 
            "William Shakespeare,Charles Dickens,Mark Twain,Oscar Wilde", 
            10
        );
        
        System.out.println("Created " + questionnaire.getQuestionCount() + " sample questions.");
    }

    /**
     * Shows the main application menu.
     */
    private static void showMainMenu() {
        while (true) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("                    🎯 QUIZ GAME SYSTEM 🎯");
            System.out.println("=".repeat(70));
            System.out.println("1. 🎮 Play Quiz Game");
            System.out.println("2. ⚙️  Question Management (CRUD)");
            System.out.println("3. 🏆 View Wall of Fame");
            System.out.println("4. 📊 System Information");
            System.out.println("5. 💾 Save All Data");
            System.out.println("6. 🔄 Reload All Data");
            System.out.println("7. 🧪 Create Sample Questions");
            System.out.println("0. 🚪 Exit");
            System.out.println("=".repeat(70));
            System.out.print("Choose an option (0-7): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        quizGame.showGameMenu();
                        break;
                    case 2:
                        questionManager.showCrudMenu();
                        break;
                    case 3:
                        wallOfFame.displayWallOfFame();
                        System.out.println("\nPress Enter to continue...");
                        scanner.nextLine();
                        break;
                    case 4:
                        showSystemInformation();
                        break;
                    case 5:
                        saveAllData();
                        break;
                    case 6:
                        reloadAllData();
                        break;
                    case 7:
                        createMoreSampleQuestions();
                        break;
                    case 0:
                        // Save data before exiting
                        System.out.println("Saving data before exit...");
                        saveAllData();
                        return;
                    default:
                        System.out.println("❌ Invalid choice. Please enter a number between 0 and 7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("❌ An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows system information and statistics.
     */
    private static void showSystemInformation() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                📋 SYSTEM INFORMATION 📋");
        System.out.println("=".repeat(60));
        System.out.printf("Questions File: %s\n", QUESTIONS_FILE);
        System.out.printf("Scores File: %s\n", SCORES_FILE);
        System.out.printf("Total Questions: %d\n", questionnaire.getQuestionCount());
        System.out.printf("Wall of Fame Entries: %d\n", wallOfFame.getScoreCount());
        
        if (wallOfFame.getScoreCount() > 0) {
            System.out.printf("Minimum Score for Fame: %d points\n", wallOfFame.getMinScoreForWallOfFame());
        }
        
        System.out.println("\n📁 File Paths:");
        System.out.printf("• Questions: %s\n", questionnaire.getFilePath());
        System.out.printf("• Wall of Fame: %s\n", wallOfFame.getFilePath());
        
        System.out.println("\n🎯 System Features:");
        System.out.println("• Multiple choice quiz gameplay");
        System.out.println("• Practice mode with explanations");
        System.out.println("• Complete question CRUD management");
        System.out.println("• Wall of Fame with top 5 players");
        System.out.println("• File-based data persistence");
        System.out.println("• Randomized question and answer order");
        
        System.out.println("=".repeat(60));
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Saves all data to files.
     */
    private static void saveAllData() {
        System.out.println("💾 Saving all data...");
        
        boolean questionsaved = questionManager.saveQuestionnaire();
        boolean scoresaved = wallOfFame.saveToFile();
        
        if (questionsaved && scoresaved) {
            System.out.println("✅ All data saved successfully!");
        } else {
            System.out.println("⚠️ Some data may not have been saved properly.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Reloads all data from files.
     */
    private static void reloadAllData() {
        System.out.println("🔄 Reloading all data from files...");
        
        boolean questionLoaded = questionManager.loadQuestionnaire();
        boolean scoresLoaded = wallOfFame.loadFromFile();
        
        if (questionLoaded && scoresLoaded) {
            System.out.println("✅ All data reloaded successfully!");
        } else {
            System.out.println("⚠️ Some data may not have been loaded properly.");
        }
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }

    /**
     * Creates additional sample questions for testing.
     */
    private static void createMoreSampleQuestions() {
        System.out.println("🧪 Creating additional sample questions...");
        
        // Math questions
        questionManager.createQuestion(
            "What is 15 + 27?", 
            "42", 
            "42,41,43,44", 
            5
        );
        
        questionManager.createQuestion(
            "What is the square root of 64?", 
            "8", 
            "8,6,7,9", 
            8
        );
        
        // Science questions
        questionManager.createQuestion(
            "What is the chemical symbol for gold?", 
            "Au", 
            "Au,Ag,Go,Gd", 
            10
        );
        
        questionManager.createQuestion(
            "How many bones are in an adult human body?", 
            "206", 
            "206,208,204,210", 
            12
        );
        
        // Technology questions
        questionManager.createQuestion(
            "What does CPU stand for?", 
            "Central Processing Unit", 
            "Central Processing Unit,Computer Personal Unit,Central Program Unit,Core Processing Unit", 
            8
        );
        
        System.out.println("✅ Additional sample questions created!");
        System.out.println("Total questions now: " + questionnaire.getQuestionCount());
        
        // Save the new questions
        questionManager.saveQuestionnaire();
        
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
