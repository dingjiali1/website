
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;   
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class GogameServer {
        Map<String,Set<Integer>> stones = new HashMap<>();
        

    class Worker implements Runnable
    {
        Socket socket;
        public Worker(Socket s)
        {
            socket = s;
        }
        final int N = 19;
        @Override
        public void run()
        {
            try{
                DataInputStream d = new DataInputStream(socket.getInputStream());
                socket.getInetAddress().toString();
                String ip = socket.getInetAddress().toString();
                Set<Integer> coordinates = stones.get(ip);
                int r = d.readInt();
                int c = d.readInt();
                coordinates.add(r*100 + c);
                double distance = 2*N;
                int minr=0,minc=0;
                for (int row = 0; row < N;row++)
                {
                    for (int col=0;col < N;col++)
                    {
                        if (coordinates.contains(row*100 + col))
                            continue;
                        double dist = Math.sqrt((col-c)*(col-c) + (row-r)*(row-r));
                        if (dist < distance)
                        {
                            distance = dist;
                            minr = row;
                            minc = col;   
                        } 
                    }
                }
                coordinates.add(minr*100 + minc);
                socket.close();
                Thread.sleep(1000);
                sendnum(ip,minr,minc);      
        }catch(Exception e){}
    }
    }
    final int port = 2311;
    protected void sendnum(String ip, int r, int c)
    {
        new Thread(){public void run(){
            try{
            Socket s = new Socket(ip,port);
            DataOutputStream o = new DataOutputStream(s.getOutputStream());
            o.writeInt(r);
            o.writeInt(c);
            }catch(Exception e){}
        }
        }.start();
    }   
    ExecutorService  pool = Executors.newFixedThreadPool(10);
    public void receive()
    {
        try{
        ServerSocket ss = new ServerSocket(port);
        while(true)
        {
            Socket s = ss.accept();
            Worker w = new Worker(s);
            pool.execute(w);
        }
        //ss.close();
        }catch(Exception e){}
    }
    public static void main(String [] args)
    {
        new GogameServer().receive();
    }
    
}
