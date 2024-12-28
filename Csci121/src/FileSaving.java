import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 27364
 */
public class FileSaving {
    static AtomicBoolean b = new AtomicBoolean(false);
    static class FileThread extends Thread
    {
        String path;
        FileThread(String p)
        {
            path = p;
        }
        @Override
        public void run()
        {

            FileWriter f = null;
            String n = Thread.currentThread().getName();
            synchronized (b)
            {
            try{
                f = new FileWriter(path,true);
                f.write(n + "\n");
            }catch(IOException e)
            {
                System.out.println(e.toString());
            }
            finally
            {
                if (f != null) try{
                    f.close();
                }catch(IOException e1)
                {
                    System.out.println(e1.toString());
                }
            }
        }
    }
    
    public static void main(String [] args)
    {
        Thread [] ts = new Thread[10];
        for (int i=0;i < 10;i++)
        {
            ts[i] = new FileThread("sharedfile.txt");
            ts[i].setName("t" + i);
            ts[i].start();
        }
        for (int i=0;i < 10;i++)
            try{
                ts[i].join();//main thread waits fot ts[i]}
            }catch(InterruptedException e){}
        try{
        Scanner s = new Scanner(new File("sharedfile.txt"));
        System.out.println(s.useDelimiter("\\Z").next());
    }catch(Exception e){}   
}
    }
}
