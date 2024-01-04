package view;

import java.util.List;
import java.util.TimerTask;
import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import assets.Colors;
import assets.Fonts;
import assets.InterfaceMessages;
import model.*;
import tools.*;

public class GameView extends JPanel {

    private static final int UNIT_WIDTH = 96, UNIT_HEIGHT = 96;

    public static final int IMAGE_WIDTH = UNIT_WIDTH, IMAGE_HEIGHT = UNIT_HEIGHT;
    private static final int INVENTORY_FRAME_HEIGHT = UNIT_HEIGHT, INVENTORY_FRAME_WIDTH = UNIT_WIDTH;
    private static final int MOB_IMAGE_HEIGHT = UNIT_HEIGHT * 2 / 3, MOB_IMAGE_WIDTH = UNIT_WIDTH * 2 / 3;
    private static final int INVENTORY_SLOT_HEIGHT = UNIT_HEIGHT * 3 / 4, INVENTORY_SLOT_WIDTH = UNIT_WIDTH * 3 / 4;
    private static final int TOWER_IMAGE_WIDTH = UNIT_WIDTH, TOWER_IMAGE_HEIGHT = UNIT_HEIGHT;
    private static final int BULLET_IMAGE_WIDTH = UNIT_WIDTH, BULLET_IMAGE_HEIGHT = UNIT_HEIGHT;
    private static final double BULLET_SPEED_FACTOR = 0.5;
    private static final int EXPLOISON_FRAME_WIDTH = UNIT_WIDTH, EXPLOISON_FRAME_HEIGHT = UNIT_HEIGHT;
    private static final int TRAP_WIDTH = UNIT_WIDTH * 2 / 3, TRAP_HEIGHT = UNIT_HEIGHT * 2 / 3;
    private final static int EXPLOISON_DELAY = 100;
    private static final int FONT_SIZE = 24;
    private static final int SCORE_FONT_SIZE = 32;
    private static final int DISPLAY_MESSAGE_DURATION = 3000;
    private static final int PADDING = 48;
    private static final int INVENTORY_SLOT_BORDER = 3;
    public static int WINDOW_HEIGHT, WINDOW_WIDTH;


    private class MapView extends JPanel {

        private Image[][] map;
        private List<MobDisplay> mobDisplays;
        private List<TowerDisplay> towerDisplays;
        private List<TrapDisplay> traps;
        private List<Bullet> bullets;
        private List<Exploison> exploisons;
        private String interfaceMessage;

        public MapView() {
            super();
            this.map = MapGraphicsFactory.loadMap(currentBoard);
            this.mobDisplays = new LinkedList<>();
            this.towerDisplays = new LinkedList<>();
            this.bullets = new LinkedList<>();
            this.exploisons = new LinkedList<>();
            this.traps = new LinkedList<>();
            this.setSize(IMAGE_WIDTH * currentBoard.getWidth(), IMAGE_HEIGHT * currentBoard.getHeight());
        }

        public void addMissingMobs() {
            for (Mob m : currentBoard.getCurrentMobs()) {
                if (!this.mobDisplayExists(m)) {
                    MobDisplay mobDisplay = new MobDisplay(m);
                    this.mobDisplays.add(mobDisplay);
                    mobDisplay.startMobAnimation();
                }
            }
        }

        public void addMissingTowers() {
            for (Tower t : currentBoard.getCurrentTowers()) {
                if (!towerDisplayExists(t)) {
                    TowerDisplay towerDisplay = new TowerDisplay(t);
                    this.towerDisplays.add(towerDisplay);
                }
            }
        }

        public void removeEliminatedMobs() {
            List<MobDisplay> mobsToEliminate = new LinkedList<>();

            for (MobDisplay mobDisplay : mobDisplays) {
                if (!currentBoard.getCurrentMobs().contains(mobDisplay.getMob())) {
                    mobsToEliminate.add(mobDisplay);
                    mobDisplay.stopMobAnimation();
                }
            }

            for (MobDisplay m : mobsToEliminate) {
                mobDisplays.remove(m);
            }
        }

        public void updateMobDisplaysList() {
            removeEliminatedMobs();
            addMissingMobs();
        }

        public void updateTowerDisplays() {
            addMissingTowers();
        }

        private boolean mobDisplayExists(Mob mob) {
            for (MobDisplay mobDisplay : this.mobDisplays) {
                if (mobDisplay.getMob() == mob) {
                    return true;
                }
            }
            return false;
        }

        private boolean towerDisplayExists(Tower t) {
            for (TowerDisplay towerDisplay : towerDisplays) {
                if (towerDisplay.getTower() == t) {
                    return true;
                }
            }
            return false;
        }

        public void addTrap(Trap t) {
            TrapDisplay trapDisplay = new TrapDisplay(t);
            this.traps.add(trapDisplay);
        }

        public void removeTrap(Trap t) {
            for (TrapDisplay trapDisplay : traps) {
                if (trapDisplay.getTrap().equals(t)) {
                    traps.remove(trapDisplay);
                }
            }
        }

        public void updateMobsPosition(Graphics g) {

            for (MobDisplay mobDisplay : this.mobDisplays) {
                g.drawImage(mobDisplay.getCurrentFrame(),
                        (int) (mobDisplay.getMob().getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - MOB_IMAGE_WIDTH) / 2,
                        (int) (mobDisplay.getMob().getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - MOB_IMAGE_HEIGHT) / 2,
                        MOB_IMAGE_WIDTH,
                        MOB_IMAGE_HEIGHT, this);
            }
        }

        public void updateTowersPosition(Graphics g) {
            for (TowerDisplay towerDisplay : towerDisplays) {
                g.drawImage(towerDisplay.getTowerFrame(),
                        (int) (towerDisplay.getTower().getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - TOWER_IMAGE_WIDTH) / 2,
                        (int) (towerDisplay.getTower().getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - TOWER_IMAGE_HEIGHT) / 2,
                        TOWER_IMAGE_WIDTH,
                        TOWER_IMAGE_HEIGHT, this);
            }
        }

        public void updateBulletsPosition(Graphics g) {
            for (Bullet b : bullets) {
                g.drawImage(b.getImage(),
                        (int) (b.getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - BULLET_IMAGE_WIDTH) / 2,
                        (int) (b.getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - BULLET_IMAGE_HEIGHT) / 2,
                        BULLET_IMAGE_WIDTH,
                        BULLET_IMAGE_HEIGHT, this);
            }
        }

        public void updateExploisons(Graphics g) {
            for (Exploison e : exploisons) {
                g.drawImage(e.getFrame(),
                        (int) (e.getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - EXPLOISON_FRAME_WIDTH) / 2,
                        (int) (e.getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - EXPLOISON_FRAME_HEIGHT) / 2,
                        EXPLOISON_FRAME_WIDTH,
                        EXPLOISON_FRAME_HEIGHT, this);
            }
        }

        public void updateTraps(Graphics g) {
            for (TrapDisplay trapDisplay : traps) {
                g.drawImage(trapDisplay.getFrame(),
                        (int) (trapDisplay.getTrap().getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - TRAP_WIDTH) / 2,
                        (int) (trapDisplay.getTrap().getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - TRAP_HEIGHT) / 2,
                        TRAP_WIDTH,
                        TRAP_HEIGHT, this);
            }
        }

        public void displayGameInformation(Graphics g) {
            Image heartIcon = IconsGraphicsFactory.loadHeartIcon().getImage();
            Image coinIcon = IconsGraphicsFactory.loadCoinIcon().getImage();
            String healthPoints = String.valueOf(currentBoard.getCurrentBase().getHp());
            String credit = String.valueOf(currentPlayer.getCredit());

            // FIXME Trouver une formule logique pour centrer les icones

            g.setFont(Fonts.sansSerifBoldItalicFont(SCORE_FONT_SIZE));
            g.setColor(Colors.INTERFACE_MESSAGE_COLOR);

            FontMetrics fontMetrics = g.getFontMetrics();
            int x = SCORE_FONT_SIZE;
            int fontHeight = fontMetrics.getHeight();
            int y = fontHeight + fontMetrics.getAscent();

            g.drawImage(heartIcon, x,
                    y + fontHeight / 6 - IconsGraphicsFactory.HEART_ICON_HEIGHT, IconsGraphicsFactory.HEART_ICON_WIDTH,
                    IconsGraphicsFactory.HEART_ICON_HEIGHT, this);
            g.drawString(healthPoints, x + PADDING, y);
            g.drawImage(coinIcon, x,
                    y + fontHeight / 6  + SCORE_FONT_SIZE - IconsGraphicsFactory.COIN_ICON_HEIGHT, IconsGraphicsFactory.COIN_ICON_WIDTH,
                    IconsGraphicsFactory.COIN_ICON_HEIGHT, this);
            g.drawString(credit, x + PADDING, y + SCORE_FONT_SIZE);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Drawing map content

            for (int i = 0; i < currentBoard.getHeight(); i++) {
                for (int j = 0; j < currentBoard.getWidth(); j++) {
                    Image cellTexture = this.map[i][j];
                    g.drawImage(cellTexture, j * IMAGE_WIDTH, i * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
                }
            }

            if (selectionFrame.getPosition() != null) {
                g.drawImage(selectionFrame.getImage(), selectionFrame.getPosition().getX() * IMAGE_WIDTH,
                        selectionFrame.getPosition().getY() * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
            }

            this.updateMobDisplaysList();
            this.updateTowerDisplays();

            this.updateMobsPosition(g);
            this.updateTowersPosition(g);
            this.updateBulletsPosition(g);
            this.updateExploisons(g);
            this.updateTraps(g);
            this.displayGameInformation(g);

            g.setFont(Fonts.sansSerifBoldFont(FONT_SIZE));
            if (interfaceMessage == null)
                return;
            FontMetrics fontMetrics = g.getFontMetrics();
            int x = (getWidth() - fontMetrics.stringWidth(interfaceMessage)) / 2;
            int y = fontMetrics.getHeight() + fontMetrics.getAscent();
            g.setColor(Colors.INTERFACE_MESSAGE_COLOR);
            g.drawString(this.interfaceMessage, x, y);
        }
    }

    private class InventoryView extends JPanel {
        private class InventorySlot extends JButton {
            private Image image;
            private Slot slot;

            public InventorySlot(Slot slot) {
                this.image = EntityGraphicsFactory.loadSlot(slot.getIndex(), slot.isUnlocked())
                        .getScaledInstance(INVENTORY_SLOT_WIDTH, INVENTORY_SLOT_HEIGHT, Image.SCALE_SMOOTH);
                this.slot = slot;
                this.setPreferredSize(new Dimension(INVENTORY_FRAME_WIDTH, INVENTORY_FRAME_HEIGHT));
                this.setContentAreaFilled(false);
                this.setIcon(new ImageIcon(image));
                Border buttonBorder = BorderFactory.createLineBorder(Colors.INVENTORY_SLOT_BORDER_COLOR, INVENTORY_SLOT_BORDER);
                this.setBorder(buttonBorder);
            }
        }

        private InventorySlot[] towersInventory;
        private InventorySlot[] itemsInventory;

        public InventoryView() {
            this.towersInventory = new InventorySlot[model.Player.TOWERS_INVENTORY_SIZE];
            this.copyTowersInventory();
            for (InventorySlot towerSlot : towersInventory) {
                towerSlot.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!towerSlot.slot.isUnlocked()) {
                            displayMessage(InterfaceMessages.TOWER_NOT_UNLOCKED);
                            return;
                        }
                        Tower t = towerSlot.slot.getTower();
                        selectionFrame.setTower(t);
                    }
                });
            }
            this.itemsInventory = new InventorySlot[model.Player.ITEMS_INVENTORY_SIZE];
            this.copyItemsInventory();
            for (InventorySlot itemSlot : itemsInventory) {
                itemSlot.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!itemSlot.slot.isUnlocked()) {
                            displayMessage(InterfaceMessages.ITEM_NOT_UNLOCKED);
                            return;
                        }
                        Item i = itemSlot.slot.getItem();
                        selectionFrame.setItem(i);
                    }
                });
            }
            this.setLayout(new FlowLayout());
            this.setSize(InterfaceGraphicsFactory.INVENTORY_COLUMNS * INVENTORY_FRAME_WIDTH, INVENTORY_FRAME_HEIGHT);
            for (int i = 0; i < itemsInventory.length; i++) {
                this.add(itemsInventory[i]);
            }
            for (int i = 0; i < towersInventory.length; i++) {
                this.add(towersInventory[i]);
            }
        }

        public void copyTowersInventory() {
            Slot[] towersInventory = game.getCurrentPlayer().getTowersInventory();
            for (int i = 0; i < this.towersInventory.length; i++) {
                Slot slot = towersInventory[i];
                this.towersInventory[i] = new InventorySlot(slot);
            }
        }

        public void copyItemsInventory() {
            Slot[] itemsInventory = game.getCurrentPlayer().getItemsInventory();
            for (int i = 0; i < this.itemsInventory.length; i++) {
                Slot slot = itemsInventory[i];
                this.itemsInventory[i] = new InventorySlot(slot);
            }
        }
    }

    private Game game;
    private Board currentBoard;
    private Player currentPlayer;

    private MapView mapView;
    private InventoryView inventoryView;
    private SelectionFrame selectionFrame;

    // FIXME : find a solution for window size not including the toolbar height to
    // its getHeight()
    public GameView(Game game) {
        this.game = game;
        this.currentBoard = game.getCurrentBoard();
        this.currentPlayer = game.getCurrentPlayer();

        this.setLayout(new BorderLayout());

        this.mapView = new MapView();
        this.inventoryView = new InventoryView();
        this.setSize(mapView.getWidth(), mapView.getHeight() + inventoryView.getHeight());

        this.add(this.mapView);
        this.add(this.inventoryView, BorderLayout.SOUTH);

        this.selectionFrame = new SelectionFrame();
        WINDOW_WIDTH = mapView.getWidth();
        WINDOW_HEIGHT = mapView.getHeight() + inventoryView.getHeight();
    }

    public void animateBullet(Coordinates startingPosition, Coordinates landingPosition) {
        Coordinates deltaVector = new Coordinates(landingPosition.getX() - startingPosition.getX(),
                landingPosition.getY() - startingPosition.getY()).times(BULLET_SPEED_FACTOR);
        Bullet bullet = new Bullet(startingPosition);
        mapView.bullets.add(bullet);
        Timer bulletTimer = new Timer(control.GameControl.PERIOD, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bullet.getPosition().distance(startingPosition) >= landingPosition.distance(startingPosition)) {
                    ((Timer) e.getSource()).setRepeats(false);
                    mapView.bullets.remove(bullet);
                } else {
                    bullet.setPosition(bullet.getPosition().plus(deltaVector));
                }
            }
        });
        bulletTimer.start();
    }

    public void animateBombExploison(Coordinates coordinates) {
        Exploison exploison = Exploison.bombExploison(coordinates);
        mapView.exploisons.add(exploison);
        Timer timer = new Timer(EXPLOISON_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!exploison.hastNextFrame()) {
                    mapView.exploisons.remove(exploison);
                    ((Timer) arg0.getSource()).setRepeats(false);
                    return;
                }
                exploison.setNextFrame();
            }
        });
        timer.start();
    }

    public void animateFreezeExploison(Coordinates coordinates) {
        Exploison exploison = Exploison.freezeExploison(coordinates);
        mapView.exploisons.add(exploison);
        Timer timer = new Timer(EXPLOISON_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!exploison.hastNextFrame()) {
                    mapView.exploisons.remove(exploison);
                    ((Timer) arg0.getSource()).setRepeats(false);
                    return;
                }
                exploison.setNextFrame();
            }
        });
        timer.start();
    }

    public void animatePoisonExploison(Coordinates coordinates) {
        Exploison exploison = Exploison.poisonExploison(coordinates);
        mapView.exploisons.add(exploison);
        Timer timer = new Timer(EXPLOISON_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!exploison.hastNextFrame()) {
                    mapView.exploisons.remove(exploison);
                    ((Timer) arg0.getSource()).setRepeats(false);
                    return;
                }
                exploison.setNextFrame();
            }
        });
        timer.start();
    }

    public JPanel getMapView() {
        return this.mapView;
    }

    public Player getPlayer() {
        return this.currentPlayer;
    }

    public JPanel getInventoryView() {
        return this.inventoryView;
    }

    public Game getGame() {
        return this.game;
    }

    public SelectionFrame getSelectionFrame() {
        return this.selectionFrame;
    }

    public void addTrap(Trap t) {
        mapView.addTrap(t);
    }

    public void removeTrap(Trap t) {
        mapView.removeTrap(t);
    }

    public void displayMessage(String message) {
        this.mapView.interfaceMessage = message;
        java.util.Timer displayDuration = new java.util.Timer();
        displayDuration.schedule(new TimerTask() {
            @Override
            public void run() {
                mapView.interfaceMessage = null;
            }
        }, DISPLAY_MESSAGE_DURATION);
    }
}
