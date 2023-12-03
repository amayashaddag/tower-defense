package debug;

import model.*;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        Player player = new Player("Amayas");
        player.addToTowersInventory(new SimpleTower());
        Board board = Board.boardExample();
        Game game = new Game(player, board);
        game.playerAction();
    }
}
