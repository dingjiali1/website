import java.util.Scanner;

public class ScannerLab3 {
   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Enter '123 notnum' and hit return:");
       if (scanner.hasNextInt()) {
           int num = scanner.nextInt();
           System.out.println("The integer read is: " + num);
       } else {
           System.out.println("Invalid input. Expected an integer.");
       }

       if (scanner.hasNextInt()) {
           int num = scanner.nextInt();
           System.out.println("The integer read is: " + num);
       } else {
           System.out.println("Invalid input. Expected an integer.");
       }

       scanner.close();


   }
}
