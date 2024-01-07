package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import menu.*;
import model.*;

public class Application extends JFrame {

    private static final boolean RESIZABILITY = false;

    public Application() {
        this.setResizable(RESIZABILITY);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void showFrame() {
        SwingUtilities.invokeLater(() -> {
            this.setVisible(true);
        });
    }

    public void run(Player player) {
        MenuView menu = new MenuView(player);
        this.setContentPane(menu);
        int windowWidth = menu.getWidth();
        int windowHeight = menu.getHeight();
        this.setSize(windowWidth, windowHeight);
        showFrame();
    }
}
