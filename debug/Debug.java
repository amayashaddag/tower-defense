package debug;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import control.*;
import menu.MenuView;
import model.*;
import view.*;

// Ceci est un fichier de test"
public class Debug {
    public static void main(String[] args) {
        test();
    }

    // public static void consoleVersion() {
    //     Board b = Board.boardExample();
    //     b.addMob(new Mob(0));

    //     Player p = new Player("Amayas");

    //     Triplet tr = new Triplet(2, 2, 1);
    //     List<Triplet> waves = new LinkedList<>();
    //     waves.add(tr);
    //     waves.add(new Triplet(0, 0, 4));
    //     Game game = new Game(p, b, waves);
    //     game.startRound();
    // }

    public static void graphicalVersion() {
        Player p = new Player("Amayas");
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
        Player player = new Player("Lamine");
        MenuView menu = new MenuView(player);
        SwingUtilities.invokeLater(() -> {
            JFrame window = new JFrame();
            window.setContentPane(menu);
            window.setSize(1280, 720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setVisible(true);
        });
    }
}
