package response;

 

class Msg
{
    boolean isfilename = false;
    long length=-1;

    int questionNumber=-1;
    String sid="",password="";
    String answer="";
    boolean valid = false;
    boolean isdownload = false;

    public Msg(String sentence)
    {
        if (sentence!=null && !sentence.equals(""))
        {
            if (sentence.charAt(0) == '-')
                 isdownload = sentence.charAt(0) == '-';
            else
            {
                 isfilename = sentence.charAt(0) != '0';
            }
            int i1 = sentence.indexOf(":");
            int i2 = sentence.indexOf(":", i1 + 1);
            int i3 = sentence.indexOf(":", i2 + 1);
            int i4 = sentence.indexOf(":", i3 + 1);
            if (i1 > 0 && i4 > i3 + 1   && i2 > i1 && i3>i2)
            {
                if (!isdownload)
                    length = Integer.parseInt(sentence.substring(0, i1).replaceFirst("^0", ""));
                else
                    length = Integer.parseInt(sentence.substring(1, i1));
                if (i2 > i1 + 1) sid = sentence.substring(1 + i1, i2);
                if (i3 > i2 + 1) password = sentence.substring(1 + i2, i3);
                if (i4 > i3 + 1) questionNumber = Integer.parseInt(sentence.substring(i3 + 1, i4));
                if (i4 + 1<sentence.length())
                answer = sentence.substring(i4 + 1);
                valid = true;
            }
        }
    }

}
