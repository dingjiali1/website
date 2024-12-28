package csci121;

import java.util.Scanner;

public class Csci121 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows = 48;
        int cols = 9;
        String [] [] data = new String[rows] [];
        for (int r=0; r < rows; r++)
        {
            data[r] = new String[cols];
        }
        int row = 0,col = 0;
        while (s.hasNext())
        {
            data[row][col] = s.next();
            col++;
            if (col==cols)
            {
                col = 0;
                row++;  
            }
    }
        float [][] nums = new float[rows] [];
        String grades = "FDCBA";
        for (row = 0; row < rows; row++)
        {
            nums[row] = new float[cols];
            if (row > 0)
            {
                if (col != 1)
                nums[row][col] = Float.parseFloat(data[row][col]);
                else
                    nums[row][col] = grades.indexOf(data[row][col]);      
            }
        }
        float means [] = new float[cols];
        float sdvs [] = new float[cols];
        for (col = 0; col < cols; col++)
        {
            float sum = 0;
            for (row=0; row < rows; row++)
            {
                sum += nums[row][col];
            }
            means[col] = sum/(rows-1);
            System.out.println("mean of" + data[0][col] + ":" + means[col]);
        }
        for (col = 0; col < cols; col++)
        {
            float sum = 0;
            for (row=1; row < rows; row++)
            {
                sum += Math.pow(nums[row][col] - means[j], 2);
            }
            sdvs[col] = Math.sqrt(sum/(rows-1));
            System.out.println("standard deviation of" + data[0][col] + ":" + sdvs[col]);
        }
            
}
