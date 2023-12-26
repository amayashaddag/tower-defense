package control;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

import model.*;
import tools.*;
import view.*;

public class CursorControl {
    private GameView gameView;
    private Game gameModel;
    private GameCursor gameCursor;

    private class GameCursor implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            IntCoordinates cursorPosition = new IntCoordinates(arg0.getX(), arg0.getY());
            if (!gameView.getSelectionFrame().isEmpty()) {
                Tower containtedTower = gameView.getSelectionFrame().getTower();
                if (containtedTower != null) {
                    Coordinates mapCoordinates = new Coordinates(cursorPosition.getY() / GameView.IMAGE_HEIGHT,
                            cursorPosition.getX() / GameView.IMAGE_WIDTH);
                    if (!gameModel.getCurrentBoard().getCell(mapCoordinates).isPath()) {
                        containtedTower.setPosition(new Coordinates(mapCoordinates.getX(), mapCoordinates.getY()));
                        gameModel.getCurrentBoard().addTower(containtedTower);
                        gameView.getSelectionFrame().removeTower();
                        gameView.repaint();
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
        this.gameView.getMapView().addMouseListener(gameCursor);
        this.gameView.getMapView().addMouseMotionListener(gameCursor);
    }
}
