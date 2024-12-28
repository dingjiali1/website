public static void main(String [] a)
{
     byte [] wh = new byte[]{(byte) 0xE4,(byte)0xB8 ,(byte)0xAD};
       try{
       String x = new String(wh,"UTF-8");
       System.out.println(x);
       wh = new byte[]{(byte)0xD6,(byte)0xD0};
       x = new String(wh,"gbk");
       System.out.println(x);
       }catch(Exception e){}
}
