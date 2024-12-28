package response;

import java.util.ArrayList;

/**
 * Created by zhong on 11/16/2017.
 */

public class Response {
    int num = 0;
    String session = "";
    long timemoment = 0;
    int numberq = 0;
    String content = "";
    ArrayList<String> questions = new ArrayList<>();
    public Response(
            int num,
            String session,
            long timemoment,
            int numberq,
            String content)
    {
        this.num = num;
        this.session =session;
        this.timemoment = timemoment;
        this.numberq = numberq;
        this.content = content;
    }

    public String toString()
    {
        return num + "," + session + "," + timemoment + "," + numberq + "," + content;
    }
    public Response(String x)
    {
        int i=0, j = x.indexOf(",");
        num = Integer.parseInt(x.substring(0,j));
        i =j+1; j = x.indexOf(",",i);
        if (j==i) session = "";
        else session = x.substring(i,j);
        i =j+1; j = x.indexOf(",",i);
        timemoment = Long.parseLong(x.substring(i,j));
        i =j+1; j = x.indexOf(",",i);
        numberq = Integer.parseInt(x.substring(i,j));
        i =j+1;
        content = x.substring(i);

    }
}
