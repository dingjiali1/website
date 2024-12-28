import java.util.Scanner; 

public class ScannerLab2 { 
    public static void main(String[] args) { 
         System.out.println("Enter 'We are students' and hit return:"); 
        Scanner scanner = new Scanner(System.in); 
        String word = scanner.next(); 
        System.out.println("The 1st word read is: " + word); 
        word = scanner.next(); 
        System.out.println("The  2nd word read is: " + word); 
        scanner.close(); 
    } 
} 
