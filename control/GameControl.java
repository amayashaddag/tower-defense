package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.*;
import view.*;

public class GameControl {

    private static final int FPS = 60;
    private static final int PERIOD = 1000 / FPS;

    private Game gameModel;
    private GameView gameView;
    private CursorControl cursorControl;

    private Timer gameTimer;
    private long lastTime;

    public GameControl(Game gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.cursorControl = new CursorControl(gameModel, gameView);
        this.gameTimer = new Timer(PERIOD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                long currentTime = System.nanoTime();
                if (lastTime == 0) {
                    lastTime = currentTime;
                } else {
                    long deltaT = currentTime - lastTime;
                    lastTime = currentTime;
                    update(deltaT);
                }
            }
        });
    }

    public void update(long deltaT) {
        gameModel.getCurrentBoard().updateMobsPosition(deltaT);
        gameView.getMapView().repaint();
    }

    public void startGame() {
        this.gameTimer.start();
    }

    public void stopTimer() {
        this.gameTimer.stop();
    }


}
