package app;

import model.Game;
import model.Player;

public class ConsoleGame {
    public static void main(String[] args) {
        Player player = new Player();
        Game game = Game.getEasyModeGame(player);
        game.startRound();
    }
}
