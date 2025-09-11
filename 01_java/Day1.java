
import java.io.IOException;
//
import java.util.Scanner;

public class Day1 {
    public static void main(String[] args)
    throws IOException {

        System.out.println("Hello, World!");
        /*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String t = reader.readLine();
        System.out.println("Ciao " +t);     // Evil CODE ! 
        System.out.print("Quanti anni hai ?");
        String eta = reader.readLine();
        int age = Integer.parseInt(eta);
        System.out.println("Sei Nato nel " + (2025 - age) + " grande annata!");
        //*/
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Quanti anni hai ? ");
        int age = scanner.nextInt();
        System.out.println("Sei Nato nel " + (2025 - age) + " grande annata!");
        scanner.close(); // Memento
    }
    
}
