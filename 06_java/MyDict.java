import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Class that analyzes and manages the words in a text, distinguishing between normal words,
 * simple conjunctions, and adversative conjunctions.
 */
public class MyDict {
    // Map to keep track of words and their frequency
    private Map<String, Integer> words;
    // Map for simple conjunctions
    private Map<String, Integer> simpleConjunctions;
    // Map for adversative conjunctions
    private Map<String, Integer> adversativeConjunctions;
    
    // Predefined lists of conjunctions
    private static final Set<String> SIMPLE_CONJUNCTIONS = new HashSet<>(Arrays.asList(
        "e", "anche", "pure", "inoltre", "quindi", "dunque", "perciò", "pertanto", 
        "così", "allora", "se", "poiché", "siccome", "perché", "affinché", "quando", 
        "mentre", "come", "dove", "né", "o", "oppure", "ovvero"
    ));
    
    private static final Set<String> ADVERSATIVE_CONJUNCTIONS = new HashSet<>(Arrays.asList(
        "ma", "però", "tuttavia", "eppure", "nondimeno", "ciononostante", "invece", 
        "bensì", "anzi", "sebbene", "benché", "malgrado", "nonostante"
    ));
    
    /**
     * Constructor of the MyDict class.
     */
    public MyDict() {
        this.words = new HashMap<>();
        this.simpleConjunctions = new HashMap<>();
        this.adversativeConjunctions = new HashMap<>();
    }
    
    /**
     * Analyzes a text file.
     * 
     * @param filePath path of the file to analyze
     * @throws IOException if there are problems reading the file
     */
    public void analyzeFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        
        for (String line : lines) {
            analyzeLine(line);
        }
    }
    
    /**
     * Analyzes a single line of text.
     * 
     * @param line the line to analyze
     */
    public void analyzeLine(String line) {
        // Removes punctuation and converts to lowercase
        String cleanLine = line.toLowerCase()
            .replaceAll("[.,;:!?\"()\\[\\]{}]", " ")
            .replaceAll("\\s+", " ")
            .trim();
        
        if (cleanLine.isEmpty()) {
            return;
        }
        
        String[] wordsInLine = cleanLine.split(" ");
        
        for (String word : wordsInLine) {
            // Skip empty words
            if (word.isEmpty()) {
                continue;
            }
            
            // Check if it's a simple conjunction
            if (SIMPLE_CONJUNCTIONS.contains(word)) {
                simpleConjunctions.put(word, simpleConjunctions.getOrDefault(word, 0) + 1);
            } 
            // Check if it's an adversative conjunction
            else if (ADVERSATIVE_CONJUNCTIONS.contains(word)) {
                adversativeConjunctions.put(word, adversativeConjunctions.getOrDefault(word, 0) + 1);
            } 
            // Otherwise it's a normal word
            else {
                words.put(word, words.getOrDefault(word, 0) + 1);
            }
        }
    }
    
    /**
     * Returns the most frequent word in the text.
     * 
     * @return the most frequent word
     */
    public String getMostFrequentWord() {
        String mostFrequent = "No words found";
        int maxCount = 0;
        
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        
        return mostFrequent;
    }
    
    /**
     * Calculates the total number of words (excluding conjunctions).
     * 
     * @return the total number of words
     */
    public int getTotalWordCount() {
        int total = 0;
        for (int count : words.values()) {
            total += count;
        }
        return total;
    }
    
    /**
     * Calculates the total number of simple conjunctions.
     * 
     * @return the total number of simple conjunctions
     */
    public int getTotalSimpleConjunctionsCount() {
        int total = 0;
        for (int count : simpleConjunctions.values()) {
            total += count;
        }
        return total;
    }
    
    /**
     * Calculates the total number of adversative conjunctions.
     * 
     * @return the total number of adversative conjunctions
     */
    public int getTotalAdversativeConjunctionsCount() {
        int total = 0;
        for (int count : adversativeConjunctions.values()) {
            total += count;
        }
        return total;
    }
    
    /**
     * Calculates the ratio between words and adversative conjunctions.
     * 
     * @return the words/adversative conjunctions ratio
     */
    public double getWordsToAdversativeConjunctionsRatio() {
        int adversativeCount = getTotalAdversativeConjunctionsCount();
        return adversativeCount > 0 ? (double) getTotalWordCount() / adversativeCount : 0;
    }
    
    /**
     * Calculates the ratio between adversative conjunctions and all conjunctions.
     * 
     * @return the adversative/total conjunctions ratio as a percentage
     */
    public double getAdversativeToTotalConjunctionsRatio() {
        int totalConjunctions = getTotalSimpleConjunctionsCount() + getTotalAdversativeConjunctionsCount();
        return totalConjunctions > 0 ? 
            (double) getTotalAdversativeConjunctionsCount() / totalConjunctions * 100 : 0;
    }
    
    /**
     * Calculates the ratio between adversative conjunctions and words.
     * 
     * @return the adversative conjunctions/words ratio as a percentage
     */
    public double getAdversativeToWordsRatio() {
        int totalWords = getTotalWordCount();
        return totalWords > 0 ? 
            (double) getTotalAdversativeConjunctionsCount() / totalWords * 100 : 0;
    }
    
    /**
     * Generates a report in markdown format with the statistics of the analyzed text.
     * 
     * @return a string containing the report in markdown format
     */
    public String generateMarkdownReport() {
        StringBuilder report = new StringBuilder();
        report.append("# Text Analysis\n\n");
        
        // General statistics
        report.append("## Statistics\n\n");
        report.append(String.format("- **Total words**: %d\n", getTotalWordCount()));
        report.append(String.format("- **Total conjunctions**: %d\n", 
            getTotalSimpleConjunctionsCount() + getTotalAdversativeConjunctionsCount()));
        report.append(String.format("- **Adversative conjunctions**: %d\n", 
            getTotalAdversativeConjunctionsCount()));
        report.append(String.format("- **Adversative/conjunctions ratio**: %.2f%%\n", 
            getAdversativeToTotalConjunctionsRatio()));
        report.append(String.format("- **Adversative/words ratio**: %.2f%%\n", 
            getAdversativeToWordsRatio()));
        
        return report.toString();
    }
    
    /**
     * Saves the report in markdown format to a file.
     * 
     * @param filePath path of the file where to save the report
     * @throws IOException if there are problems writing the file
     */
    public void saveMarkdownReport(String filePath) throws IOException {
        String report = generateMarkdownReport();
        Files.write(Paths.get(filePath), report.getBytes());
    }
}