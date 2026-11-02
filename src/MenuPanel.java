import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private Image backgroundImage;

    public MenuPanel(Runnable onStart, Runnable onExit) {
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        setPreferredSize(new Dimension(360, 640));
        setLayout(new GridBagLayout());

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("FLAPPY BIRD");
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = createButton("Mulai Permainan");
        startButton.addActionListener(e -> onStart.run());

        JButton exitButton = createButton("Keluar");
        exitButton.addActionListener(e -> onExit.run());

        box.add(title);
        box.add(Box.createRigidArea(new Dimension(0, 40)));
        box.add(startButton);
        box.add(Box.createRigidArea(new Dimension(0, 15)));
        box.add(exitButton);

        add(box);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        Dimension size = new Dimension(200, 45);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusable(false);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}