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
                    if (gameModel.gameFinished()) {
                        System.out.println("Game finished ! ");
                        stopTimer();
                        //FIXME : méthode pas "gentille" pour fermer la fenêtre
                        System.exit(0);
                    } else {
                        if (gameModel.roundFinished()) {
                            gameModel.nextRound();
                            if (gameModel.getIndexCurrentWave() < gameModel.nbRounds()) {
                                gameModel.startCurrentRound();
                            }
                        }
                    }
                }
            }
        });
    }

    public void update(long deltaT) {
        gameModel.getCurrentBoard().updateMobsPosition(deltaT);
        gameView.repaint();
    }

    public void updateTowers() {
        Board currentBoard = gameModel.getCurrentBoard();
        for (Tower t : currentBoard.getCurrentTowers()) {
            Mob mob = currentBoard.getMobTargetInRange(t.getPosition(), t.getRange());
            if (mob != null) {
                if (t instanceof SingleTargetDamage) {
                    SingleTargetDamage targetTower = (SingleTargetDamage) t;
                    targetTower.attack(mob);
                } else if (t instanceof ZoneDamage) {
                    ZoneDamage zoneTower = (ZoneDamage) t;
                    List<Mob> mobsInRange = currentBoard.getMobsInRange(mob.getPosition(), t.getRange());
                    zoneTower.attack(mobsInRange);
                }
                currentBoard.removeDeadMobs();
            }
        }
    }

    public void startGame() {
        this.gameModel.startCurrentRound();
        this.gameTimer.start();
    }

    public void stopTimer() {
        this.gameTimer.stop();
    }

    public CursorControl getCursorControl() {
        return cursorControl;
    }

}
