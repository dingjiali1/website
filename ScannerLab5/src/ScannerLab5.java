import java.util.Scanner;

public class ScannerLab5 {
   public static void main(String[] args) {
       System.out.println("Enter '111,322' and hit return: "  );
       Scanner scanner = new Scanner(System.in);
       scanner.useDelimiter(",");
       String token = scanner.next();
       System.out.println("The token read is: " + token);
       System.out.println("2nd num is: " + token);
       scanner.close();
   }
}