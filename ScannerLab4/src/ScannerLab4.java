import java.util.Scanner;

public class ScannerLab4 {
   public static void main(String[] args) {
       System.out.println("Enter '12.34 123 abcd' and hit return:");
       Scanner scanner = new Scanner(System.in);
       if (scanner.hasNextFloat()) {
           float num = scanner.nextFloat();
           System.out.println("The float read is: " + num);
       } else {
           System.out.println("Invalid input. Expected a float.");
       }

       if (scanner.hasNextFloat()) {
           float num = scanner.nextFloat();
           System.out.println("2nd item  is: " + num);
       } else {
           System.out.println("Invalid input. Expected a float.");
       }

         if (scanner.hasNextFloat()) {
           float num = scanner.nextFloat();
           System.out.println("3rd item is : " + num);
       } else {
           System.out.println("Invalid input. Expected a float.");
       }

       scanner.close();
   }
}