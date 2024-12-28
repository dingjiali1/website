import java.util.Scanner;

public class ScannerLab6 {
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       if (scanner.hasNext()) {
           String input = scanner.next();
           if (!input.isEmpty()) {
               char ch = input.charAt(0);
               System.out.println("The character read is: " + ch);
           } else {
               System.out.println("Empty input.");
           }
       } else {
           System.out.println("No input available.");
       }
       scanner.close();
   }
}