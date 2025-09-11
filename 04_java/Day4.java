//package 04_java;

import java.util.Scanner;

public class Day4 {
    public static void main(String[] args) {
        System.out.println("Starting Java program");
        /*
        boolean a=true,b=false;

        // Uso un assegnazione booleana nella condizione if
        if (a=b) {
            System.out.println("a is true");
        } else {
            System.out.println("a is false");
        }
        //*/
        //
        /* 
        int a=6,b=11;
        System.out.println("a="+a+" b="+b);
        a^=b;
        b^=a;
        a^=b;
        System.out.println("a="+a+" b="+b);*/
        /* 1/
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an expression (e.g., 3+4): "); 
        String input = scanner.nextLine();
        String[] parts = parsed(input); 
        scanner.close();

        for(String part : parts){
            System.out.println(part);
        }
        //*/
        
        CalculatorMenu();
    } 

    static void CalculatorMenu(){
        Calculator calc = new Calculator();
        System.out.println("Calculator Menu:");
        System.out.println("1. Frequency to Wavelength");
        System.out.println("2. Wavelength to Frequency");
        System.out.println("3. Fibonacci (Non-recursive)");
        System.out.println("4. Fibonacci (Recursive)");
        System.out.println("5. Min, Max, Mean");
        System.out.println("6. Watt to dBm");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        //scanner.close();
        switch(choice){ 
            case 1:
                System.out.print("Enter frequency (kHz): ");
                double freq = scanner.nextDouble();
                double lamda = calc.FrequencyToLamda(freq);
                System.out.println("Wavelength: " + lamda + " m");
                break;
            case 2:
                System.out.print("Enter wavelength (m): ");
                double lamda2 = scanner.nextDouble();
                double freq2 = calc.LamdaToFrequency(lamda2);
                System.out.println("Frequency: " + freq2 + " KHz");
                break;
            case 3:
                System.out.print("Enter n for Fibonacci (Non-recursive): ");    
                int n = scanner.nextInt();
                int[] fib = calc.Fibonacci(n);
                System.out.print("Fibonacci series: ");
                for(int val : fib){
                    System.out.print(val + " ");
                }   
                System.out.println();
                break;  
            case 4:
                System.out.print("Enter n for Fibonacci (Recursive): ");
                int n2 = scanner.nextInt();
                int fib2 = calc.FibonacciR(n2);
                System.out.println("Fibonacci (Recursive): " + fib2);
                break;
            case 5:
                System.out.print("Enter number of elements: ");
                int numElements = scanner.nextInt();
                int[] values = new int[numElements];
                for (int i = 0; i < numElements; i++) {
                    System.out.print("Enter value " + (i + 1) + ": ");
                    values[i] = scanner.nextInt();
                }
                int  [] res = calc.MinMaxMean(values);
                System.out.println("Min: " + res[0]);
                System.out.println("Max: " + res[1]);
                System.out.println("Mean: " + res[2]    );
                break;
            case 6:
                System.out.print("Enter power in Watts: "); 
                double watt = scanner.nextDouble();
                double dbm = calc.WattToDBm(watt);
                System.out.println("Power in dBm: " + dbm + " dBm");
                break;
            case 7:
                System.out.println("Exiting...");       
                break;
            default:
                System.out.println("Invalid choice. Please try again.");6
        }
        scanner.close();
    }

    static String[] parsed(String exp){
        return exp.split("(?<=[-+*/])|(?=[-+*/])");       
    }
}
