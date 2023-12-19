package debug;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import model.*;
import tools.Triplet;
import view.*;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        consoleVersion();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();

        Player p = new Player("Amayas");
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());

        Triplet tr = new Triplet(2, 2, 1);
        List<Triplet> waves = new LinkedList<>();
        waves.add(tr);

        Game game = new Game(p, b, waves);

        game.startRound();
    }

    public static void graphicalVersion() {
        Board b = Board.boardExample();
        Player p = new Player("Amayas");
        Game game = new Game(p, b, null);

        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToItemsInventory(new Freeze(0, null));

        SwingUtilities.invokeLater(() -> {
            GameView view = new GameView(game);
            view.setVisible(true);
        });
    }
}
