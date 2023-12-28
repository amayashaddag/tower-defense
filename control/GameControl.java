package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import model.*;
import view.*;

public class GameControl {

    private static final int FPS = 60;
    public static final int PERIOD = 1000 / FPS;

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

    public void updateTowers(){
        Board currentBoard = gameModel.getCurrentBoard();
          for (Tower t :  currentBoard.getCurrentTowers() ) {
                Mob mob = currentBoard.getMobTargetInRange(t.getPosition(), t.getRange());
                if (mob != null) {
                    if (t instanceof SingleTargetDamage targetTower) {
                        targetTower.attack(mob);
                    } else if (t instanceof ZoneDamage zoneTower) {
                        List<Mob> mobsInRange = currentBoard.getMobsInRange(mob.getPosition(), t.getRange());
                        zoneTower.attack(mobsInRange);
                    }
                    
                    currentBoard.removeDeadMobs();
                }
            }
    }

    public void startGame() {
        this.gameTimer.start();
    }

    public void stopTimer() {
        this.gameTimer.stop();
    }

    public CursorControl getCursorControl() {
        return cursorControl;
    }

}
