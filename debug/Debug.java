package debug;

import java.util.Scanner;

import javax.swing.SwingUtilities;

import model.*;
import tools.*;
import view.GameView;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        graphicalVersion();
    }

    public static void consoleVersion() {
        Board b = Board.boardExample();
        Mob mob = new SimpleMob(new Coordinates(0, 0));
        b.addMob(mob);
        Scanner scanner = new Scanner(System.in);
        String quit = "";
        while(!quit.equals("q")) {
            System.out.println(b);
            quit = scanner.next();
            b.updateMobsPosition();
        }
        scanner.close();
        System.out.println(b.cellType(2, 3));
    }

    public static void graphicalVersion() {
        Board b = Board.boardExample();
        GameView view = new GameView(b);
        SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
        });
    }
}
