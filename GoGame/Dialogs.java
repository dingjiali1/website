
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;


public class Dialogs {
    public void handlenum(int r, int c){System.out.println(r + " " + c);}
    public void handlestr(String x){System.out.println(x);}
    int  port = 2311;
    ServerSocket serverSocket = null;
    public Dialogs()
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
            receive(true);
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
            receive(true);
            
        } catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    void receive(boolean num)
    {
        new Thread(){
            public void run(){
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    System.out.println("wait ....");
                    Socket socket = serverSocket.accept();
                    System.out.println("get one ....");
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    if (num)
                    {int row = inputStream.readInt();
                    int col = inputStream.readInt();
                    handlenum(row, col);
                    }
                    else
                    {
                         String s= inputStream.readLine();
                         handlestr(s);
                    }
                    socket.close();
                    serverSocket.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            }
        }.start();
    } 
}

