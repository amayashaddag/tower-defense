package view;

import java.util.List;
import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.*;
import tools.IntCoordinates;

public class GameView extends JFrame {

    public static final int IMAGE_WIDTH = 96, IMAGE_HEIGHT = 96;
    private static final String WINDOW_TITLE = "Tower Defense Game - Le titre est à changer";
    private static final boolean RESIZABILITY = false;
    private static final int INVENTORY_FRAME_HEIGHT = 96, INVENTORY_FRAME_WIDTH = 96;
    private static final int MOB_IMAGE_HEIGHT = 64, MOB_IMAGE_WIDTH = 64;
    private static final int TOWER_IMAGE_WIDTH = 96, TOWER_IMAGE_HEIGHT = 96;
    private static final int MOB_ANIMATION_DELAY = 50;

    private class MapView extends JPanel {

        private class MobDisplay {
            private Image[] northFrames;
            private Image[] southFrames;
            private Image[] eastFrames;
            private Image[] westFrames;

            private Image currentFrame;
            private int frameIndex = 0;
            private Timer animationTimer;

            private Mob mob;

            public MobDisplay(Mob mob) {
                this.mob = mob;
                this.northFrames = EntityGraphicsFactory.loadNorthFrames(mob);
                this.westFrames = EntityGraphicsFactory.loadWestFrames(mob);
                this.southFrames = EntityGraphicsFactory.loadSouthFrames(mob);
                this.eastFrames = EntityGraphicsFactory.loadEastFrames(mob);
                this.animationTimer = new Timer(MOB_ANIMATION_DELAY, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Image[] currentFrames;
                        switch (mob.getDirection()) {
                            case EAST:
                                currentFrames = eastFrames;
                                break;
                            case NORTH:
                                currentFrames = northFrames;
                                break;
                            case SOUTH:
                                currentFrames = southFrames;
                                break;
                            default:
                                currentFrames = westFrames;
                                break;
                        }
                        currentFrame = currentFrames[frameIndex];
                        frameIndex++;
                        frameIndex %= EntityGraphicsFactory.NB_OF_FRAMES;
                    }
                });
            }

            public void startMobAnimation() {
                this.animationTimer.start();
            }

            public void stopMobAnimation() {
                this.animationTimer.stop();
            }
        }

        private class TowerDisplay {
            private Image towerFrame;
            private Tower tower;

            public TowerDisplay(Tower t) {
                this.tower = t;
                String index = t instanceof SimpleTower ? Slot.SIMPLE_TOWER_INDEX : Slot.BOMB_TOWER_INDEX;
                this.towerFrame = EntityGraphicsFactory.loadSlot(index, true);
            }
        }

        private Image[][] map;
        private List<MobDisplay> mobDisplays;
        private List<TowerDisplay> towerDisplays;

        public MapView() {
            super();
            this.map = MapGraphicsFactory.loadMap(currentBoard);
            this.mobDisplays = new LinkedList<>();
            this.towerDisplays = new LinkedList<>();
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
            for (MobDisplay mobDisplay : mobDisplays) {
                if (!currentBoard.getCurrentMobs().contains(mobDisplay.mob)) {
                    mobDisplays.remove(mobDisplay);
                    mobDisplay.stopMobAnimation();
                }
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
                if (mobDisplay.mob == mob) {
                    return true;
                }
            }
            return false;
        }

        private boolean towerDisplayExists(Tower t) {
            for (TowerDisplay towerDisplay : towerDisplays) {
                if (towerDisplay.tower == t) {
                    return true;
                }
            }
            return false;
        }

        public void updateMobsPosition(Graphics g) {

            for (MobDisplay mobDisplay : this.mobDisplays) {
                g.drawImage(mobDisplay.currentFrame,
                        (int) (mobDisplay.mob.getPosition().getY() * IMAGE_WIDTH) + (IMAGE_WIDTH - MOB_IMAGE_WIDTH) / 2,
                        (int) (mobDisplay.mob.getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - MOB_IMAGE_HEIGHT) / 2,
                        MOB_IMAGE_WIDTH,
                        MOB_IMAGE_HEIGHT, this);
            }
        }

        public void updateTowersPosition(Graphics g) {
            for (TowerDisplay towerDisplay : towerDisplays) {
                g.drawImage(towerDisplay.towerFrame,
                        (int) (towerDisplay.tower.getPosition().getY() * IMAGE_WIDTH)
                                + (IMAGE_WIDTH - TOWER_IMAGE_WIDTH) / 2,
                        (int) (towerDisplay.tower.getPosition().getX() * IMAGE_HEIGHT)
                                + (IMAGE_HEIGHT - TOWER_IMAGE_HEIGHT) / 2,
                        TOWER_IMAGE_WIDTH,
                        TOWER_IMAGE_HEIGHT, this);
            }
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
        }
    }

    private class InventoryView extends JPanel {
        private class InventorySlot extends JButton {
            private Image image;
            private Slot slot;

            public InventorySlot(Slot slot) {
                this.image = EntityGraphicsFactory.loadSlot(slot.getIndex(), slot.isUnlocked());
                this.slot = slot;
                this.setPreferredSize(new Dimension(INVENTORY_FRAME_WIDTH, INVENTORY_FRAME_HEIGHT));
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
                            // FIXME IMPLEMENTER MESSAGE ERREUR 
                            // Afficher un message d'erreur ... etc
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
                            // FIXME IMPLEMENTER MESSAGE ERREUR 
                            // Afficher un message d'erreur ... etc
                            return;
                        }
                        Item i = itemSlot.slot.getItem();
                        selectionFrame.setItem(i);
                    }
                });
            }
            this.setLayout(new GridLayout(InterfaceGraphicsFactory.INVENTORY_LINES, InterfaceGraphicsFactory.INVENTORY_COLUMNS));
            this.setSize(InterfaceGraphicsFactory.INVENTORY_COLUMNS * INVENTORY_FRAME_WIDTH, InterfaceGraphicsFactory.INVENTORY_LINES * INVENTORY_FRAME_HEIGHT);
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

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (selectionFrame.getPosition() != null) {
                g.drawImage(selectionFrame.getImage(), selectionFrame.getPosition().getX() * IMAGE_WIDTH,
                        selectionFrame.getPosition().getY() * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
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

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(WINDOW_TITLE);
        this.setResizable(RESIZABILITY);
        this.setSize(mapView.getWidth(), mapView.getHeight() + inventoryView.getHeight() + 37);

        this.add(this.mapView);
        this.add(this.inventoryView, BorderLayout.SOUTH);

        this.selectionFrame = new SelectionFrame();
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

    public boolean inInvetory(IntCoordinates position) {
        return inTowersInventory(position) || inItemsInventory(position);
    }

    public boolean inMap(IntCoordinates position) {
        return !inInvetory(position);
    }

    public boolean inTowersInventory(IntCoordinates position) {
        return position.getY() / GameView.IMAGE_HEIGHT - (this.getMapView().getHeight() / GameView.IMAGE_HEIGHT) == 0;
    }

    public boolean inItemsInventory(IntCoordinates position) {
        return position.getY() / GameView.IMAGE_HEIGHT - (this.getMapView().getHeight() / GameView.IMAGE_HEIGHT) == 1;
    }
}
