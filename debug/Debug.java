package debug;

import javax.swing.SwingUtilities;

import model.*;
import tools.Coordinates;
import view.*;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        graphicalVersion();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();
        b.addMob(new SimpleMob(new Coordinates(0, 0)));
        Player p = new Player("Amayas");
        Game game = new Game(p, b);

        game.play();
    }

    public static void graphicalVersion() {
        Board b = Board.boardExample();
        GameView view = new GameView(b);
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
        });
    }
}
