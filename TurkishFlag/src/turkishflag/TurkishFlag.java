import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

class TurkishFlag extends Canvas {
    private int flagWidth;
    private int flagHeight;

    public TurkishFlag(int w) {
        flagWidth = w;
        flagHeight = w * 2 / 3;
    }

    void paintMoon(Graphics g, java.awt.Color bgcolor, int radius, int centerx, int centery) {
        g.setColor(bgcolor);
        g.fillOval(centerx - radius, centery - radius, 2 * radius, 2 * radius);
    }

    public void paintStar(double centerX, double centerY, double radius, double angle, Graphics g) {
        g.setColor(Color.WHITE);
        int[][] d = vertices(centerX, centerY, radius, angle);
        g.fillPolygon(d[0], d[1], 10);
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, flagWidth, flagHeight);

        int G = flagWidth;
        int A = G / 2;
        int B = G / 2;
        int C = (int)(0.0625 * G);
        int D = (int)(0.4 * G);
        int E = G / 3;
        int F = G / 4;

        int set = 55;
        int Yset = 20;
        int centerxWhiteMoon = A - B / 2 + set;
        int centeryWhiteMoon = flagHeight / 2 - Yset;
       double scaleFactor = 0.8;
        int radiusWhiteMoon = (int) (B / 2 * scaleFactor);
        paintMoon(g, Color.WHITE, radiusWhiteMoon, centerxWhiteMoon, centeryWhiteMoon);


        int centerxRedMoon = centerxWhiteMoon + C;
        int centeryRedMoon = centeryWhiteMoon;
        double scaleFactor2 = 0.8;
        int radiusRedMoon = (int) ( D / 2 * scaleFactor2);
        paintMoon(g, Color.RED, radiusRedMoon, centerxRedMoon, centeryRedMoon);

        int offset = -50;
        int XStar = -80;
        int centerxStar = centerxRedMoon + E + XStar;
        int centeryStar = centeryRedMoon + (int)(0.1 * G) + offset; 
        double scaleFactor3 = 0.8;
        int radiusStar = (int) ( F / 2 * scaleFactor3);
        paintStar(centerxStar, centeryStar, radiusStar, Math.toRadians(30), g); 
    }

    int[][] vertices(double cx, double cy, double r, double angle) {
        double outerRadius = 0.9;
        double innerRadius = outerRadius * Math.sin(Math.PI / 10) / Math.sin(3 * Math.PI / 10);

        double angleIncrement = (Math.PI / 5);
        double startAngle = angle;
        int[][] d = new int[2][10];
        for (int i = 0; i < 10; i++) {
            angle = startAngle + i * angleIncrement;
            double radius = r * ((i % 2) == 0 ? outerRadius : innerRadius);
            double x = radius * Math.cos(angle) + cx;
            double y = radius * Math.sin(angle) + cy;
            d[0][i] = (int)Math.round(x);
            d[1][i] = (int)Math.round(y);
        }
        return d;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Turkish National Flag Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TurkishFlag canvas = new TurkishFlag(576);
        frame.setSize(canvas.flagWidth, canvas.flagHeight);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
    }
}