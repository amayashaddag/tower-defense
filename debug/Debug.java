package debug;

import model.*;
import tools.Coordinates;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        Player player = new Player("Amayas");
        player.addToTowersInventory(new SimpleTower());
        Board board = Board.boardExample();
        SimpleMob mob = new SimpleMob(new Coordinates(0.5, 0.5));
        board.addMob(mob);
        Game game = new Game(player, board);
        game.playerAction();
    }
}
