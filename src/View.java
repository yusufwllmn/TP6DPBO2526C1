import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {

    int width = 360;
    int height = 640;

    private Logic logic;
    private Image backgroundImage;
    private JLabel scoreLabel;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));

        // Background dari assets (menggantikan warna cyan)
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();

        // JLabel untuk menampilkan skor
        setLayout(null);
        scoreLabel = new JLabel("Skor: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 28));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(10, 10, 200, 40);
        add(scoreLabel);

        // Tambahkan fokus dan Key Listener
        // Untuk menerima input
        setFocusable(true);
        addKeyListener(logic);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Skor: " + score);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, width, height, null);
        draw(g);
    }

    private void draw(Graphics g) {
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null) {
            for (int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        if (logic.isGameOver()) {
            drawGameOver(g);
        }
    }

    private void drawGameOver(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, width, height);

        g.setColor(Color.WHITE);
        drawCenteredString(g, "GAME OVER", new Font("Arial", Font.BOLD, 40), height / 2 - 30);
        drawCenteredString(g, "Skor: " + logic.getScore(), new Font("Arial", Font.BOLD, 24), height / 2 + 10);
        drawCenteredString(g, "Tekan R untuk restart", new Font("Arial", Font.PLAIN, 18), height / 2 + 50);
    }

    private void drawCenteredString(Graphics g, String text, Font font, int y) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (width - fm.stringWidth(text)) / 2;
        g.drawString(text, x, y);
    }
}