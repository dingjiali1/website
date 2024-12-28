import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class YellowStarDrawing extends JFrame {

    public YellowStarDrawing() {
        setTitle("Yellow Five-Pointer Star");
        setSize(400, 400);
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        Canvas canvas = new Canvas() {
            @Override
            public void paint(Graphics g) {
                g.setColor(Color.YELLOW);

                // Coordinates for the points of the five-pointer star
                int[] xPoints = {150, 200, 250, 200, 225};
                int[] yPoints = {200, 250, 200, 150, 125};

                g.fillPolygon(xPoints, yPoints, 5);
            }
        };

        add(canvas);
        setVisible(true);
    }

    public static void main(String[] args) {
        new YellowStarDrawing();
    }
}
