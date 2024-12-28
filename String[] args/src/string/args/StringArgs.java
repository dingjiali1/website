public class TypeConversionCheck {
    public static void main(String[] args) {
        int x = 1000000;
        byte y = (byte)x;

        byte z = 120;
        long w = (long)z;

        System.out.println("y: " + y);
        boolean xAgreesToY = x == (int)y;
        System.out.println("Does x agree to y? " + (xAgreesToY? "yes" : "no"));

        System.out.println("w: " + w);
        boolean wAgreesToZ = z == (byte)w;
        System.out.println("Does w agree to z? " + (wAgreesToZ? "yes" : "no"));
    }
}