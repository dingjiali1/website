import java.io.*;
import java.net.*;
import java.util.*;
public class SimpleHTTPServer
{
    private ServerSocket serverSocket;
    private final int port;
    public SimpleHTTPServer(int port)
    {
        this.port = port;
    }
    public void start()
    {
        try {
            serverSocket= new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while(true){
                Socket clientSocket=serverSocket.accept();
                Thread thread = new Thread(()->handleClient(clientSocket));
                thread.start();
            }
        }catch (IOException e){e.printStackTrace();
        }
        }
    private void handleClient(Socket clientSocket){
    try{
        BufferedReader in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream out= new DataOutputStream(clientSocket.getOutputStream());
        // Read the request headers
        Map headers = readHeaders(in);
        System.out.println(headers);
        // Check ifit's a multipart/form-data request
        String str =headers.toString();
        int i=str.indexOf("GET");
        int j=str.indexOf("HTTP");
        String path=str.substring(i+3,j).trim().replace("/", "\\").replace('=',':');
        System.out.println(path);
        Scanner s= new Scanner(new File(path));
        try{
        String content=s.useDelimiter("\\Z").next();
        
        // Send response
        String response="HTTP/1.1 200 OK\r\n"+"Content-Type:text/plain\r\n"+"\r\n "+content;
        out.writeBytes(response);
        }catch(Exception e){}
        in.close();
        out.close();
        clientSocket.close();
    }catch (IOException e){e.printStackTrace();
    }
    }
    private Map<String, String> readHeaders(BufferedReader in) throws IOException {
    Map<String, String> headers = new HashMap<>();
    String line;
    while (!(line = in.readLine()).isEmpty()) {
        String[] parts = line.split(":", 2);
        if (parts.length == 2) {
            headers.put(parts[0].trim(), parts[1].trim());
        }
    }
    return headers;
}
    public static void main(String[] args){
        SimpleHTTPServer server=new SimpleHTTPServer(1616);
        server.start();
    }
}