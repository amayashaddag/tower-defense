package control;

import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import assets.InterfaceMessages;
import items.Bomb;
import items.Freeze;
import items.Item;
import items.Trap;
import mobs.Mob;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import model.*;
import tools.*;
import towers.Tower;
import view.*;

public class CursorControl {
    private GameView gameView;
    private Game gameModel;
    private GameCursor gameCursor;

    private final static int TIMER_SCALE = 500;

    private class GameCursor implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
            Coordinates mapCoordinates = new Coordinates(arg0.getY() / GameView.IMAGE_HEIGHT,
                    arg0.getX() / GameView.IMAGE_WIDTH);
            Board board = gameModel.getCurrentBoard();

            if (gameView.getSelectionFrame().isEmpty()) {
                return;
            }
            Tower containedTower = gameView.getSelectionFrame().getTower();
            Item containedItem = gameView.getSelectionFrame().getItem();

            if (containedTower != null && !board.getCell(mapCoordinates).isPath()) {
                addTower(mapCoordinates, containedTower);
                gameView.updateTowerDisplays();
                return;
            }
            if (containedItem == null || !board.getCell(mapCoordinates).isPath()) {
                return;
            }
            if (containedItem instanceof Trap) {
                Trap trap = (Trap) containedItem;
                addTrap(mapCoordinates, trap);
            } else {
                ZoneDamage zoneDamage = (ZoneDamage) containedItem;
                dropZoneDamageItem(mapCoordinates, zoneDamage);

            }
            gameView.getSelectionFrame().removeItem();
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

    private void addTower(Coordinates mapCoordinates, Tower containedTower) {
        Board board = gameModel.getCurrentBoard();
        if (!gameModel.playerHasEnoughCreditFor(containedTower.getCost())) {
            gameView.displayMessage(InterfaceMessages.NOT_ENOUGH_CREDIT);
            return;
        }
        containedTower.setPosition(new Coordinates(mapCoordinates.getX(), mapCoordinates.getY()));
        gameModel.getCurrentPlayer().lostCredit(containedTower.getCost());
        Timer attackTimer = new Timer(containedTower.getRateOfFire() * TIMER_SCALE,
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Mob mob = board.getMobTargetInRange(containedTower.getPosition(),
                                containedTower.getRange());
                        if (mob == null) {
                            return;
                        }
                        if (containedTower instanceof SingleTargetDamage) {
                            SingleTargetDamage targetTower = (SingleTargetDamage) containedTower;
                            gameView.animateBullet(containedTower.getPosition(),
                                    mob.getPosition());
                            targetTower.attack(mob);
                        } else {
                            ZoneDamage zoneTower = (ZoneDamage) containedTower;
                            List<Mob> mobsInRange = board.getMobsInRange(mob.getPosition(),
                                    containedTower.getRange());
                            zoneTower.attack(mobsInRange);
                            gameView.animateBombExploison(mob.getPosition());
                        }
                        gameModel.getCreditsFromMobs();
                        board.removeDeadMobs();

                    };
                });
        containedTower.setTimer(attackTimer);
        containedTower.startAttack();
        board.addTower(containedTower);
        gameView.getSelectionFrame().removeTower();
    }

    private void addTrap(Coordinates mapCoordinates, Trap trap) {
        Board board = gameModel.getCurrentBoard();
        if (!gameModel.playerHasEnoughCreditFor(trap.getCost())) {
            gameView.displayMessage(InterfaceMessages.NOT_ENOUGH_CREDIT);
            return;
        }
        trap.setPosition(mapCoordinates);
        gameModel.getCurrentPlayer().lostCredit(trap.getCost());
        Timer attackTimer = new Timer(GameControl.PERIOD, new ActionListener() {
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
    }

    private void dropZoneDamageItem(Coordinates mapCoordinates, ZoneDamage zoneDamage) {
        Item containedItem = (Item) zoneDamage;
        Board board = gameModel.getCurrentBoard();

        if (!gameModel.playerHasEnoughCreditFor(containedItem.getCost())) {
            gameView.displayMessage(InterfaceMessages.NOT_ENOUGH_CREDIT);
            return;
        }
        gameModel.getCurrentPlayer().lostCredit(containedItem.getCost());
        List<Mob> mobsInRange = board.getMobsInRange(mapCoordinates, zoneDamage.getRange());
        zoneDamage.attack(mobsInRange);
        if (containedItem instanceof Bomb) {
            gameView.animateBombExploison(mapCoordinates);
        } else if (containedItem instanceof Freeze) {
            gameView.animateFreezeExploison(mapCoordinates);
        } else {
            gameView.animatePoisonExploison(mapCoordinates);
        }
    }
}
