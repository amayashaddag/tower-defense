package app;

import model.Player;

public class GraphicalGame {
    public static void main(String[] args) {
        Player player = new Player();
        Application app = new Application();
        app.run(player);
    }
}
