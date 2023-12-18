package debug;

import javax.swing.SwingUtilities;

import model.*;
import tools.*;
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
        Player p = new Player("Amayas");
        Game game = new Game(p, b);

        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToTowersInventory(new SimpleTower());
        p.addToItemsInventory(new Freeze(0, null));
        
        b.addMob(new SimpleMob(new Coordinates(0, 0)));

        GameView view = new GameView(game);
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
        });
    }
}
