package it.eforhum.day12;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Manages the Wall of Fame with the top 5 players and their scores.
 * Handles loading and saving scores to a file.
 */
public class WallOfFame {
    private static final int MAX_ENTRIES = 5;
    private List<PlayerScore> topScores;
    private String filePath;

    /**
     * Represents a player's score entry.
     */
    public static class PlayerScore implements Comparable<PlayerScore> {
        private String playerName;
        private int score;
        private int questionsAnswered;
        private int correctAnswers;
        private LocalDateTime achievedDate;

        public PlayerScore(String playerName, int score, int questionsAnswered, int correctAnswers) {
            this.playerName = playerName;
            this.score = score;
            this.questionsAnswered = questionsAnswered;
            this.correctAnswers = correctAnswers;
            this.achievedDate = LocalDateTime.now();
        }

        public PlayerScore(String playerName, int score, int questionsAnswered, int correctAnswers, LocalDateTime achievedDate) {
            this.playerName = playerName;
            this.score = score;
            this.questionsAnswered = questionsAnswered;
            this.correctAnswers = correctAnswers;
            this.achievedDate = achievedDate;
        }

        // Getters
        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }
        public int getQuestionsAnswered() { return questionsAnswered; }
        public int getCorrectAnswers() { return correctAnswers; }
        public LocalDateTime getAchievedDate() { return achievedDate; }

        /**
         * Calculates the accuracy percentage.
         * 
         * @return Accuracy as percentage (0-100)
         */
        public double getAccuracy() {
            if (questionsAnswered == 0) return 0.0;
            return (double) correctAnswers / questionsAnswered * 100.0;
        }

        /**
         * Converts the score to file format.
         * Format: playerName\tscore\tquestionsAnswered\tcorrectAnswers\tachievedDate
         */
        public String toFileFormat() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return String.join("\t", 
                playerName, 
                String.valueOf(score), 
                String.valueOf(questionsAnswered),
                String.valueOf(correctAnswers),
                achievedDate.format(formatter)
            );
        }

        /**
         * Creates a PlayerScore from file format line.
         */
        public static PlayerScore fromFileFormat(String line) {
            try {
                String[] parts = line.split("\t");
                if (parts.length != 5) return null;
                
                String name = parts[0].trim();
                int score = Integer.parseInt(parts[1].trim());
                int questions = Integer.parseInt(parts[2].trim());
                int correct = Integer.parseInt(parts[3].trim());
                LocalDateTime date = LocalDateTime.parse(parts[4].trim(), 
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                
                return new PlayerScore(name, score, questions, correct, date);
            } catch (Exception e) {
                System.err.println("Error parsing player score line: " + line + " - " + e.getMessage());
                return null;
            }
        }

        @Override
        public int compareTo(PlayerScore other) {
            // Sort by score descending, then by accuracy descending, then by date ascending
            int scoreCompare = Integer.compare(other.score, this.score);
            if (scoreCompare != 0) return scoreCompare;
            
            int accuracyCompare = Double.compare(other.getAccuracy(), this.getAccuracy());
            if (accuracyCompare != 0) return accuracyCompare;
            
            return this.achievedDate.compareTo(other.achievedDate);
        }

        @Override
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return String.format("%-20s | Score: %4d | Accuracy: %5.1f%% (%d/%d) | Date: %s",
                playerName, score, getAccuracy(), correctAnswers, questionsAnswered, 
                achievedDate.format(formatter));
        }
    }

    /**
     * Constructor that initializes the Wall of Fame.
     * 
     * @param filePath Path to the scores file
     */
    public WallOfFame(String filePath) {
        this.topScores = new ArrayList<>();
        this.filePath = filePath;
    }

    /**
     * Loads scores from the file.
     * 
     * @return true if loading was successful, false otherwise
     */
    public boolean loadFromFile() {
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                System.out.println("Scores file does not exist: " + filePath + ". Starting with empty Wall of Fame.");
                return true;
            }

            List<String> lines = Files.readAllLines(path);
            topScores.clear();
            
            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) continue;
                
                PlayerScore score = PlayerScore.fromFileFormat(line);
                if (score != null) {
                    topScores.add(score);
                }
            }
            
            // Sort scores and keep only top entries
            Collections.sort(topScores);
            if (topScores.size() > MAX_ENTRIES) {
                topScores = topScores.subList(0, MAX_ENTRIES);
            }
            
            System.out.println("Loaded " + topScores.size() + " scores from Wall of Fame.");
            return true;
            
        } catch (IOException e) {
            System.err.println("Error reading scores file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Saves scores to the file.
     * 
     * @return true if saving was successful, false otherwise
     */
    public boolean saveToFile() {
        try {
            Path path = Paths.get(filePath);
            
            // Only create directories if there's a parent directory
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            
            List<String> lines = new ArrayList<>();
            for (PlayerScore score : topScores) {
                lines.add(score.toFileFormat());
            }
            
            Files.write(path, lines);
            System.out.println("Saved " + topScores.size() + " scores to Wall of Fame.");
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing scores file: " + e.getMessage());
            return false;
        }
    }

    /**
     * Adds a new score to the Wall of Fame.
     * Only keeps the top scores up to MAX_ENTRIES.
     * 
     * @param playerName Name of the player
     * @param score Total score achieved
     * @param questionsAnswered Total questions answered
     * @param correctAnswers Number of correct answers
     * @return true if the score made it to the Wall of Fame, false otherwise
     */
    public boolean addScore(String playerName, int score, int questionsAnswered, int correctAnswers) {
        PlayerScore newScore = new PlayerScore(playerName, score, questionsAnswered, correctAnswers);
        
        // Add the new score
        topScores.add(newScore);
        
        // Sort all scores
        Collections.sort(topScores);
        
        // Keep only top MAX_ENTRIES
        boolean madeItToWallOfFame = false;
        for (int i = 0; i < Math.min(MAX_ENTRIES, topScores.size()); i++) {
            if (topScores.get(i) == newScore) {
                madeItToWallOfFame = true;
                break;
            }
        }
        
        if (topScores.size() > MAX_ENTRIES) {
            topScores = topScores.subList(0, MAX_ENTRIES);
        }
        
        return madeItToWallOfFame;
    }

    /**
     * Displays the Wall of Fame.
     */
    public void displayWallOfFame() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("                            🏆 WALL OF FAME 🏆");
        System.out.println("=".repeat(80));
        
        if (topScores.isEmpty()) {
            System.out.println("                        No scores recorded yet!");
            System.out.println("                     Be the first to make history!");
        } else {
            System.out.printf("%-4s %-20s   %-6s   %-12s   %-20s\n", 
                "Rank", "Player", "Score", "Accuracy", "Date");
            System.out.println("-".repeat(80));
            
            for (int i = 0; i < topScores.size(); i++) {
                PlayerScore score = topScores.get(i);
                String rank = getRankEmoji(i + 1) + " " + (i + 1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                
                System.out.printf("%-4s %-20s   %4d     %5.1f%% (%d/%d)   %s\n",
                    rank,
                    score.getPlayerName(),
                    score.getScore(),
                    score.getAccuracy(),
                    score.getCorrectAnswers(),
                    score.getQuestionsAnswered(),
                    score.getAchievedDate().format(formatter)
                );
            }
        }
        
        System.out.println("=".repeat(80));
    }

    /**
     * Gets the appropriate emoji for the rank.
     */
    private String getRankEmoji(int rank) {
        switch (rank) {
            case 1: return "🥇";
            case 2: return "🥈";
            case 3: return "🥉";
            default: return "🏅";
        }
    }

    /**
     * Checks if a score qualifies for the Wall of Fame.
     * 
     * @param score The score to check
     * @return true if the score would make it to the Wall of Fame
     */
    public boolean qualifiesForWallOfFame(int score) {
        if (topScores.size() < MAX_ENTRIES) {
            return true;
        }
        
        return score > topScores.get(topScores.size() - 1).getScore();
    }

    /**
     * Gets the minimum score needed to enter the Wall of Fame.
     * 
     * @return Minimum score needed, or 0 if Wall of Fame is not full
     */
    public int getMinScoreForWallOfFame() {
        if (topScores.size() < MAX_ENTRIES) {
            return 0;
        }
        
        return topScores.get(topScores.size() - 1).getScore();
    }

    /**
     * Gets a copy of all scores.
     * 
     * @return List of all scores
     */
    public List<PlayerScore> getAllScores() {
        return new ArrayList<>(topScores);
    }

    /**
     * Gets the number of scores recorded.
     * 
     * @return Number of scores
     */
    public int getScoreCount() {
        return topScores.size();
    }

    /**
     * Clears all scores.
     */
    public void clearScores() {
        topScores.clear();
    }

    /**
     * Gets the file path used for scores.
     * 
     * @return The file path
     */
    public String getFilePath() {
        return filePath;
    }
}