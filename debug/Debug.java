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
        graphicalVersion();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();
        b.addMob(new Mob(0));

        Player p = new Player("Amayas");
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(ItemTower.bombTower());
        p.addToTowersInventory(new SimpleTower());

        Triplet tr = new Triplet(2, 2, 1);
        List<Triplet> waves = new LinkedList<>();
        waves.add(tr);

        Game game = new Game(p, b, waves);

        game.startRound();
    }

    public static void graphicalVersion() {
        Board b = Board.boardExample();
        b.addMob(new Mob(0));

        Player p = new Player("Amayas");
        Game game = new Game(p, b, null);

        GameView view = new GameView(game);

        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
            view.startGame();
        });

    }
}
