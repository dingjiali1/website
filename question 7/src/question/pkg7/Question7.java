/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package question.pkg7;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author 27364
 */
public class Question7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            String x = "we are students. 123";
            byte [] y = x.getBytes("UTF-8");
            String z = new String(y, "gbk");
            System.out.println(z);
       }catch(UnsupportedEncodingException e){}
    }
