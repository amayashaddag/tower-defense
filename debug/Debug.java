package debug;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import app.*;
import control.*;
import model.*;
import view.*;

// Ceci est un fichier de test"
public class Debug {
    public static void main(String[] args) {
        test();
    }

    public static void graphicalVersion() {
        Player p = new Player();
        Game game = Game.getMarathonMode(p);
        GameView view = new GameView(game);
        GameControl gameControl = new GameControl(game, view);
        SwingUtilities.invokeLater(() -> {
            JFrame windows = new JFrame();
            windows.setContentPane(view);
            windows.setSize(GameView.WINDOW_WIDTH,GameView.WINDOW_HEIGHT);
            windows.setVisible(true);
            gameControl.startGame();
        });

    }

    public static void test() {
        Player player = new Player();
        Application app = new Application();
        app.run(player);
    }
}
