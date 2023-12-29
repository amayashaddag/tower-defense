package control;

import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import model.*;
import tools.*;
import view.*;

public class CursorControl {
    private GameView gameView;
    private Game gameModel;
    private GameCursor gameCursor;

    private final static int TIMER_SCALE = 500;

    private class GameCursor implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            Board board = gameModel.getCurrentBoard();
            Timer attackTimer;
            Coordinates mapCoordinates = new Coordinates(arg0.getY() / GameView.IMAGE_HEIGHT,
                    arg0.getX() / GameView.IMAGE_WIDTH);
            if (!gameView.getSelectionFrame().isEmpty()) {
                Tower containtedTower = gameView.getSelectionFrame().getTower();
                if (containtedTower != null) {
                    if (!board.getCell(mapCoordinates).isPath()) {
                        containtedTower.setPosition(new Coordinates(mapCoordinates.getX(), mapCoordinates.getY()));
                        attackTimer = new Timer(containtedTower.getRateOfFire() * TIMER_SCALE,
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {
                                        Mob mob = board.getMobTargetInRange(containtedTower.getPosition(),
                                                containtedTower.getRange());
                                        if (mob != null) {

                                            if (containtedTower instanceof SingleTargetDamage) {
                                                SingleTargetDamage targetTower = (SingleTargetDamage) containtedTower;
                                                gameView.animateBullet(containtedTower.getPosition(),
                                                        mob.getPosition());
                                                targetTower.attack(mob);
                                            } else if (containtedTower instanceof ZoneDamage) {
                                                ZoneDamage zoneTower = (ZoneDamage) containtedTower;
                                                List<Mob> mobsInRange = board.getMobsInRange(mob.getPosition(),
                                                        containtedTower.getRange());
                                                zoneTower.attack(mobsInRange);
                                            }

                                            board.removeDeadMobs();
                                        }
                                    };
                                });
                        containtedTower.setTimer(attackTimer);
                        containtedTower.startAttack();
                        board.addTower(containtedTower);
                        gameView.getSelectionFrame().removeTower();
                    }
                }

                Item containedItem = gameView.getSelectionFrame().getItem();
                if (containedItem == null)
                    return;
                if (!board.getCell(mapCoordinates).isPath())
                    return;

                if (containedItem instanceof Trap) {
                    Trap trap = (Trap) containedItem;
                    trap.setPosition(mapCoordinates);
                    attackTimer = new Timer(GameControl.PERIOD, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (trap.isDead()) {
                                ((Timer) e.getSource()).setRepeats(false);
                                board.removeItem(trap);
                                gameView.removeTrap(trap);
                                return;
                            }
                            Mob mob = board.getMob(mapCoordinates);
                            if (mob == null)
                                return;
                            trap.attack(mob);
                        }
                    });
                    trap.setTimer(attackTimer);
                    board.addItem(trap);
                    gameView.addTrap(trap);
                    trap.startAttack();
                } else {
                    ZoneDamage zoneDamage = (ZoneDamage) containedItem;
                    List<Mob> mobsInRange = board.getMobsInRange(mapCoordinates, zoneDamage.getRange());
                    zoneDamage.attack(mobsInRange);
                    gameView.animateExpoison(mapCoordinates, containedItem);
                }
                gameView.getSelectionFrame().removeItem();
            }
            gameView.repaint();
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
