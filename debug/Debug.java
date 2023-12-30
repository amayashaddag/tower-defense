package debug;

import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;

import control.*;
import model.*;
import shopview.ShopView;
import tools.*;
import view.*;

// Ceci est un fichier de test"
public class Debug {
    public static void main(String[] args) {
        test();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();
        b.addMob(new Mob(0));

        Player p = new Player("Amayas");

        Triplet tr = new Triplet(2, 2, 1);
        List<Triplet> waves = new LinkedList<>();
        waves.add(tr);
        waves.add(new Triplet(0, 0, 4));
        Game game = new Game(p, b, waves);
        game.startRound();
    }

    public static void graphicalVersion() {
        Player p = new Player("Amayas");
        Game game = Game.getMediumModeGame(p);

        GameView view = new GameView(game);
        GameControl gameControl = new GameControl(game, view);
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
            gameControl.startGame();
        });

    }

    public static void test() {
        Player player = new Player("Amayas");
        ShopView view = new ShopView(player);
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
        });
    }
}
