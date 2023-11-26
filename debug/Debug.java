package debug;

import model.*;

// Ceci est un fichier de test
public class Debug {
    public static void main(String[] args) {
        Board example = Board.boardExample();
        Player player = new Player("Amayas");
        player.addToTowersInventory(new SimpleTower());
        Game game = new Game(player, example);
        System.out.println(example);
        game.addTower();
        System.out.println(example);
    }
}
