package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import assets.InterfaceMessages;
import menu.MenuView;
import mobs.Mob;
import model.*;
import tools.Triplet;
import towers.Tower;
import view.*;

public class GameControl {

    private static final int FPS = 60;
    public static final int PERIOD = 1000 / FPS;

    private Game gameModel;
    private GameView gameView;
    private CursorControl cursorControl;

    private Timer gameTimer;
    private long lastTime;

    private int roundNumber = 0;

    public GameControl(Game gameModel, GameView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
        this.cursorControl = new CursorControl(gameModel, gameView);
        boolean isMarathonMode = gameModel.isMarathonMode();
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

                    Player currentPlayer = gameModel.getCurrentPlayer();

                    if (gameModel.gameFinished()) {
                        stopTimer();
                        returnToMenu();

                        try {
                            currentPlayer.savePlayerData();
                        } catch (Exception e) {
                            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(gameView);
                            MenuView menuView = (MenuView) parentFrame.getContentPane();
                            menuView.displayMessage(InterfaceMessages.SAVING_DATA_ERROR);
                        }
                    } else {
                        if (isMarathonMode) {
                            marathonModeNextRound();
                            return;
                        }
                        normalModeNextRound();
                    }
                }
            }
        });
        ;
    }

    private void normalModeNextRound() {
        if (gameModel.roundFinished()) {
            gameModel.nextRound();
            if (!gameModel.getWaves().isEmpty()) {
                gameModel.startCurrentRound();
            }
        }
    }

    private void marathonModeNextRound() {
        if (gameModel.roundFinished()) {
            Triplet nextWave = gameModel.nextWave(Game.MARATHON_SEED, roundNumber);
            roundNumber++;
            gameModel.nextRound();
            gameModel.addWave(nextWave);
            gameModel.startCurrentRound();
        }
    }

    public void update(long deltaT) {
        gameModel.getCurrentBoard().updateMobsPosition(deltaT);
        gameView.repaint();
    }

    private void returnToMenu() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(gameView);
        parentFrame.setContentPane(new MenuView(gameModel.getCurrentPlayer()));
        parentFrame.setSize(MenuView.WINDOW_WIDTH, MenuView.WINDOW_HEIGHT);
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
