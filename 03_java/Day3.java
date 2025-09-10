import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Day3 {
    static String fname;
    public static void main(String[] args) throws IOException {
        if(args.length>0)
            fname=args[0];
        else
          Usage();
        Map<String,String> lines = readFile(fname);
        System.out.println("load "+lines.size()+" lines");

        String cmd ="";
        var scanner = new java.util.Scanner(System.in);
        while(!cmd.equals("exit")){
            System.out.print("Parola (exit per uscire): ");
            cmd = scanner.nextLine();

            if (cmd.equals("Exit") )
                break;

            if(lines.containsKey(cmd))
                System.out.println(cmd + " => " + lines.get(cmd));
            else
                System.out.println("Not found");
        }
    
        scanner.close();
    }

    static Map<String,String> readFile(String fname) throws IOException {
        Map<String,String> lines = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fname));
        String line;
        while((line=br.readLine())!=null){
            String[] parts = line.split(":");
            lines.put(parts[0], parts[1]);
        }
        br.close();
        return lines;
    }
    
    static void Usage(){
        System.out.println("Usage: java Day3 <filename>");
        System.exit(1);
    }
}
