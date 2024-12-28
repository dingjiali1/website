import java.util.Scanner; 

public class ScannerLab1 { 
    public static void main(String[] args) { 
        System.out.println("Enter some char and hit return:"); 
        Scanner scanner = new Scanner(System.in); 
        if (scanner.hasNext()) { 
            System.out.println("There is more input."); 
        } else { 
            System.out.println("No more input."); 
        } 
        scanner.close(); 

        System.out.println("Enter nothing but hit return:"); 
         scanner = new Scanner(System.in); 
        if (scanner.hasNext()) { 
            System.out.println("There is more input."); 
        } else { 
            System.out.println("No more input."); 
        } 
        scanner.close(); 
    } 
} 
