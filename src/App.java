import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flappy Bird");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // CardLayout: berpindah antara Main Menu dan Game
            CardLayout cardLayout = new CardLayout();
            JPanel root = new JPanel(cardLayout);

            Logic logika = new Logic();
            View tampilan = new View(logika);
            logika.setView(tampilan);

            MenuPanel menu = new MenuPanel(  
                    () -> {
                        cardLayout.show(root, "game");
                        SwingUtilities.invokeLater(tampilan::requestFocusInWindow);
                        logika.startGame();
                    },
                    () -> System.exit(0)
            );

            root.add(menu, "menu");
            root.add(tampilan, "game");
            cardLayout.show(root, "menu");

            frame.add(root);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}