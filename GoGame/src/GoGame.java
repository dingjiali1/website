
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

public class GoGame extends JFrame {
    final int N = 19;
    int cellSize;
    private JLabel lblmyip = new JLabel("My IP");
    private JLabel lblpip = new JLabel("Partner IP");
    private JTextField txtmyip = new JTextField("10.28.13.30");
    private JTextField txtpip = new JTextField("10.28.21.51");
    private JButton btnStart = new JButton("Listen");
    private JLabel lblmycolor = new JLabel("Black");
    Container contentpane = getContentPane();
    Color[][] colors = new Color[N][];
    JPanel board = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintGrid(width, g);
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (colors[row][col] == null) continue;
                    g.setColor(colors[row][col]);
                    if (colors[row][col]!= Color.ORANGE)
                        g.fillOval((int) ((0.65 + col) * cellSize), (int) ((0.63 + row) * cellSize), 3 * cellSize / 4, 3 * cellSize / 4);
                }
            }
        }

    };
    int width = 500;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;

    public GoGame() {
        initComponents();
    }

    public void paintGrid(int w, Graphics g) {
        cellSize = width / N;
        width = N * cellSize;
        g.setColor(java.awt.Color.BLACK);
        for (int row = 1; row <= N; row++) {
            g.drawLine(cellSize, row * cellSize, width, row * cellSize);
        }
        for (int col = 1; col <= N; col++) {
            g.drawLine(col * cellSize, cellSize, col * cellSize, width);
        }
    }

    private void place(JComponent e, int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.gridwidth = (y == 0? 1 : 6);
        c.weightx = 1.0;
        c.weighty = (y == 0? 0.0 : 1.0);
        if (y == 1 || x % 2 == 1) c.fill = GridBagConstraints.BOTH;
        c.anchor = x % 2 == 0? GridBagConstraints.EAST : GridBagConstraints.WEST;
        c.insets = new java.awt.Insets(3, 3, 0, 0);
        contentpane.add(e, c);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        for (int i = 0; i < N; i++) {
            colors[i] = new Color[N];
            for (int j = 0; j < N; j++)
                colors[i][j] = null;
        }
        java.awt.GridBagConstraints c;
        Container contentpane = getContentPane();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");

        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openGame();
            }
        });

        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        contentpane.setLayout(new java.awt.GridBagLayout());
        place(lblmyip, 0, 0);
        place(txtmyip, 1, 0);
        place(lblpip, 2, 0);
        place(txtpip, 3, 0);
        place(lblmycolor, 4, 0);
        place(btnStart, 5, 0);
        board.setSize(width + 2 * cellSize, width + 2 * cellSize);
        board.setVisible(true);
        place(board, 0, 1);
        setSize((int) (width * 1.09), (int) (width * 1.18));
        setMinimumSize(new java.awt.Dimension((int) (width * 1.08), (int) (width * 1.18)));
        pack();
        board.setBackground(Color.ORANGE);
        board.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (state == State.listening) return;
                int x0 = board.getX();
                int y0 = board.getY();
                int x = (evt.getX() - x0) / cellSize;
                int y = (evt.getY() - y0 + cellSize) / cellSize;
                if (y < 0) y = 0;
                if (y >= N) y = N - 1;
                if (x < 0) x = 0;
                if (x >= N) x = N - 1;
                drawStone(x, y, mycolor);
                comm.sendnum(x, y, txtpip.getText());
                state = State.listening;
                comm.receive(true);

            }
        });

        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (state == State.init) {
                    comm.receive(true);
                    mycolor = Color.WHITE;
                } else if (state == State.listening)
                    quit();
                else
                    comm.sendnum(-1, 0, txtpip.getText());
            }
        });
    }

    void drawStone(int x, int y, Color cl) {
        colors[y][x] = cl;
        board.repaint();
    }

    Color mycolor = Color.BLACK;
    Dialogs comm = new Dialogs() {
        public void handlenum(int r, int c) {
            if (r == -1) {
                state = State.init;
                return;
            }
            drawStone(r, c, mycolor == Color.WHITE? Color.BLACK : Color.WHITE);
            state = State.thinking;
        }

    };
    enum State {
        init, listening, thinking
    };
    State state = State.init;

    void quit() {
        comm.sendnum(-1, 0, "10.4.29.230");
    }


    private void saveGame() {
    try {
        FileOutputStream fileOutputStream = new FileOutputStream("gogame.obj");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(colors);
        byte stateByte;
        if (mycolor == Color.BLACK) {
            stateByte = 0;
        } else {
            stateByte = 1;
        }
        objectOutputStream.writeByte(stateByte);
        objectOutputStream.close();
        fileOutputStream.close();
        JOptionPane.showMessageDialog(this, "Game saved successfully!");
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error saving game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void openGame() {
    try {
        FileInputStream fileInputStream = new FileInputStream("gogame.obj");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        colors = (Color[][]) objectInputStream.readObject();
        byte stateByte = objectInputStream.readByte();
        if (stateByte == 0) {
            mycolor = Color.BLACK;
        } else {
            mycolor = Color.WHITE;
        }
        objectInputStream.close();
        fileInputStream.close();
        board.repaint();
        if (state == State.listening) {
            JOptionPane.showMessageDialog(this, "To resume the game, click the Listen button");
        } else {
            JOptionPane.showMessageDialog(this, "Game opened successfully!");
        }
    } catch (IOException | ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(this, "Error opening game: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GoGame().setVisible(true);
            }
        });
    }
}