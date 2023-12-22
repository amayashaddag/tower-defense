package control;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

import model.Game;
import model.Item;
import model.Tower;
import tools.Coordinates;
import tools.IntCoordinates;
import view.GameView;

public class CursorControl {
    private GameView gameView;
    private Game gameModel;
    private GameCursor gameCursor;

    private class GameCursor implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            IntCoordinates cursorPosition = new IntCoordinates(arg0.getX(), arg0.getY());
            if (gameView.inInvetory(cursorPosition)) {
                if (gameView.inTowersInventory(cursorPosition)) {
                    int towerIndex = cursorPosition.getX() / GameView.IMAGE_WIDTH;
                    if (towerIndex < gameModel.getCurrentPlayer().getTowersInventory().size()) {
                        Tower selectedTower = gameModel.getCurrentPlayer().getTowerFromIndex(towerIndex);
                        gameView.getSelectionFrame().setTower(selectedTower);
                        System.out.println("Selected tower");

                    }
                } else {
                    int itemIndex = cursorPosition.getX() / GameView.IMAGE_WIDTH;
                    if (itemIndex < gameModel.getCurrentPlayer().getItemsInventory().size()) {
                        Item selectedItem = gameModel.getCurrentPlayer().getItemFromIndex(itemIndex);
                        gameView.getSelectionFrame().setItem(selectedItem);

                    }

                }
            } else {
                if (!gameView.getSelectionFrame().isEmpty()) {
                    Tower containtedTower = gameView.getSelectionFrame().getTower();
                    if (containtedTower != null) {
                        Coordinates mapCoordinates = new Coordinates(cursorPosition.getY() / GameView.IMAGE_HEIGHT, cursorPosition.getX() / GameView.IMAGE_WIDTH);
                        System.out.println(mapCoordinates);
                        if (!gameModel.getCurrentBoard().getCell(mapCoordinates).isPath()) {
                            containtedTower.setPosition(new Coordinates(mapCoordinates.getX(), mapCoordinates.getY()));
                            gameModel.getCurrentBoard().addTower(containtedTower);
                            gameView.getSelectionFrame().removeTower();
                            gameView.repaint();
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {

        }

        @Override
        public void mouseExited(MouseEvent arg0) {

        }

        @Override
        public void mousePressed(MouseEvent arg0) {

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {

        }

        @Override
        public void mouseDragged(MouseEvent arg0) {

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {
            gameView.getSelectionFrame().setPosition(arg0.getX() / GameView.IMAGE_WIDTH,
                    arg0.getY() / GameView.IMAGE_HEIGHT);
            gameView.repaint();
        }
    }

    public CursorControl(Game gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.gameCursor = new GameCursor();
        this.gameView.addMouseListener(gameCursor);
        this.gameView.addMouseMotionListener(gameCursor);
    }
}
