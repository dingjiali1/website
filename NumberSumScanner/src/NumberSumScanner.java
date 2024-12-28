import java.util.Scanner;
import java.util.ArrayList;

public class NumberSumScanner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> numbers = new ArrayList<>();
        String input;
        
        System.out.println("Enter numbers separated by space (type 'done' to finish):");

        // Loop to read multiple inputs until 'done' is entered
        while (scanner.hasNext()) {
            input = scanner.next();
            if ("done".equalsIgnoreCase(input)) {
                break; // Exit loop when 'done' is entered
            }
            try {
                // Try to parse the input as a double and add it to the list
                double number = Double.parseDouble(input);
                numbers.add(number);
            } catch (NumberFormatException e) {
                // If parsing fails, print NaN and exit the program
                System.out.println("NaN");
                return;
            }
        }

        // Calculate the sum of the numbers
        double sum = 0;
        for (double num : numbers) {
            sum += num;
        }

        // Print the sum
        System.out.println("The sum of the numbers is: " + sum);

        scanner.close(); // Close the scanner object
    }
}
