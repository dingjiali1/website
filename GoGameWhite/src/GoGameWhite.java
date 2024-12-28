import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import javax.swing.JOptionPane;



public class GoGameWhite {
    
    static int  port = 2311;
    Map<String,TreeSet<String>> sofar = new HashMap<>();
    
    class Worker extends Thread
    {
    Socket socket;
    public Worker(Socket s){socket  = s;}
    
    @Override
    public void run()
    {
        try{ 
            String ip = socket.getInetAddress().getHostAddress();
            System.out.println(ip);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            int r = inputStream.readInt();
            int c = inputStream.readInt();
            socket.close();
            if (r==-1)
            {
               sofar.remove(ip);
               return;
            }
            TreeSet<String> p = sofar.get(ip);
            if (p == null) p = new TreeSet<>();
            p.add(r  + "_" + c );
            int dist = 1000000; 
            int minr=-1,minc=-1,mind=dist;
            for (int  row = 0; row < 19; row++)
            {
                for (int col = 0; col < 19; col++)
                {
                    if (p.contains(row + "_" + col)) continue;
                    int d = (col>=c?(col-c):(c-col)) + (row>=r?(row-r):(r-row));
                    if (d < dist)
                    {
                        minr=row;minc=col;dist=d; 
                    }
                }
            }
            if (dist == 1000000) return;
            p.add(minr  + "_" + minc);
            sofar.put(ip, p);
            System.out.println("r=" + r + ", c=" + c + ", mr=" + minr +", mc=" + minc);
            sendnum(minr,minc,  ip);
            }catch(IOException e){}
        }
        
    }
    public GoGameWhite()
    {
       
    }
    void sendnum(int row, int col, String ip)
    {
        try 
        {
            Socket socket = new Socket(ip, port);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeInt(row);
            outputStream.writeInt(col);
            outputStream.close();
            socket.close();
            //receive(true);
        } catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.toString());
             
        }
        
    }
    public void sendstr(String msg, String ip)
    {
        try 
        {
            Socket socket = new Socket(ip, port);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeUTF(msg);
            outputStream.close();
            socket.close();
           //receive(true);
        } catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    void receive(boolean num)
    {
         try {
                    ServerSocket serverSocket = new ServerSocket(port); 
                    while (true)
                    {
                        Socket socket = serverSocket.accept();
                        new Worker(socket).start();
                    }
                    //serverSocket.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
          
    } 
    public static void main(String [] args)
    {
        new GoGameWhite().receive(true);
    }
}
