import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberSumScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double sum = 0;
        boolean hasNonNumber = false;

        System.out.println("Please enter numbers (separated by spaces) and press Enter when done:");

        while (scanner.hasNext()) {
            try {
                double num = scanner.nextDouble();
                sum += num;
            } catch (InputMismatchException e) {
                hasNonNumber = true;
                scanner.next(); // Consume the non-numeric input so the loop can continue
            }
        }

        scanner.close();

        if (hasNonNumber) {
            System.out.println("NaN");
        } else {
            System.out.println("The sum of the entered numbers is: " + sum);
        }
    }
}
