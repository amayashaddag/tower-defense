package debug;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import control.GameControl;
import model.*;
import tools.Coordinates;
import tools.Triplet;
import view.*;

// Ceci est un fichier de test"
public class Debug {
    public static void main(String[] args) {
        graphicalVersion();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();
        b.addMob(new Mob(0));

        Player p = new Player("Amayas");

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
        GameView view = new GameView(game);
        GameControl gameControl = new GameControl(game, view);
        Timer Round = game.MakeRound(7,7,5);
        Round.start();

        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
            gameControl.startGame();
        });

    }
}
