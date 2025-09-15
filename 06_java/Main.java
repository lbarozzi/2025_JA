import java.io.*;
import java.util.*;

/**
 * Main class for the text analysis application.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MyDict dictionary = new MyDict();
        
        System.out.println("=== Text Analyzer ===");
        
        try {
            String path;
            if (args.length > 0) {
                path = args[0];
            } else {
                // Ask for the path of the file or directory
                System.out.print("Enter the path of the file or directory to analyze: ");
                path = scanner.nextLine();
            }
            
            File fileOrDir = new File(path);
            
            if (fileOrDir.isDirectory()) {
                // If it's a directory, analyze all .txt files
                System.out.println("Analyzing all .txt files in directory: " + path);
                analyzeDirectory(fileOrDir, dictionary);
            } else if (fileOrDir.isFile()) {
                // If it's a single file, analyze it
                System.out.println("Analyzing file: " + path);
                dictionary.analyzeFile(path);
            } else {
                System.out.println("The specified path does not exist.");
                System.exit(1);
            }
            
            // Generate and display the report
            System.out.println("\nGenerating report...");
            String report = dictionary.generateMarkdownReport();
            System.out.println(report);
            
            // Ask the user if they want to save the report
            System.out.print("\nDo you want to save the report to a file? (y/n): ");
            String response = scanner.nextLine().toLowerCase();
            
            if (response.equals("y")) {
                System.out.print("Enter the path where to save the report: ");
                String reportPath = scanner.nextLine();
                dictionary.saveMarkdownReport(reportPath);
                System.out.println("Report successfully saved to: " + reportPath);
            }
            
        } catch (IOException e) {
            System.out.println("Error during file analysis: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    /**
     * Analyzes all .txt files in a directory.
     * 
     * @param directory the directory to analyze
     * @param dictionary the MyDict object to use for the analysis
     * @throws IOException if there are problems reading the files
     */
    private static void analyzeDirectory(File directory, MyDict dictionary) throws IOException {
        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
        
        if (files == null || files.length == 0) {
            System.out.println("No .txt files found in the directory.");
            return;
        }
        
        for (File file : files) {
            System.out.println("Analyzing file: " + file.getPath());
            dictionary.analyzeFile(file.getPath());
        }
    }
}