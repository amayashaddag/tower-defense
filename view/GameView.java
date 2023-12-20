package view;

import java.util.List;
import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import model.*;
import tools.*;

public class GameView extends JFrame {

    private static final int IMAGE_WIDTH = 96, IMAGE_HEIGHT = 96;
    private static final String WINDOW_TITLE = "Tower Defense Game - Le titre est à changer";
    private static final boolean RESIZABILITY = false;
    private static final int INVENTORY_FRAME_HEIGHT = 96, INVENTORY_FRAME_WIDTH = 96;

    private class SelectionFrame {
        private Image image;
        private IntCoordinates position;

        public SelectionFrame(Image selectionFrameImage) {
            this.image = selectionFrameImage;
        }

        public void setPosition(int x, int y) {
            this.position = new IntCoordinates(x, y);
        }
    }

    private class GameCursor implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {

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
            selectionFrame.setPosition(arg0.getX() / IMAGE_WIDTH, arg0.getY() / IMAGE_HEIGHT);
            repaint();
        }
    }

    private class MapView extends JPanel {

        private class MobDisplay {
            private Image mobImage;
            private Mob mob;

            public MobDisplay(Mob mob) {
                this.mob = mob;
                this.mobImage = EntityGraphicsFactory.loadMobImage(mob);
            }
        }

        private Image[][] map;
        private List<MobDisplay> mobDisplays;

        public MapView() {
            super();
            this.map = MapGraphicsFactory.loadMap(currentBoard);
            this.mobDisplays = new LinkedList<>();
            this.setSize(IMAGE_WIDTH * currentBoard.getWidth(), IMAGE_HEIGHT * currentBoard.getHeight());
        }

        private void updateMobDisplaysList() {
            for (Mob m : currentBoard.getCurrentMobs()) {
                if (!this.mobDisplayExists(m)) {
                    this.mobDisplays.add(new MobDisplay(m));
                }
            }
        }

        private boolean mobDisplayExists(Mob mob) {
            for (MobDisplay mobDisplay : this.mobDisplays) {
                if (mobDisplay.mob == mob) {
                    return true;
                }
            }
            return false;   
        }

        public void updateMobsPosition(Graphics g) {
            for (MobDisplay mobDisplay : this.mobDisplays) {
                g.drawImage(mobDisplay.mobImage,
                        (int) (mobDisplay.mob.getPosition().getY() * IMAGE_WIDTH),
                        (int) (mobDisplay.mob.getPosition().getX() * IMAGE_HEIGHT),
                        IMAGE_WIDTH,
                        IMAGE_HEIGHT, this);
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

            if (selectionFrame.position != null) {
                g.drawImage(selectionFrame.image, selectionFrame.position.getX() * IMAGE_WIDTH,
                        selectionFrame.position.getY() * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
            }

            this.updateMobDisplaysList();
            this.updateMobsPosition(g);
        }
    }

    private class InventoryView extends JPanel {
        private class InventoryTower {
            private Image image;
            private Tower tower;

            public InventoryTower(Tower t) {
                this.image = EntityGraphicsFactory.laodTowerInventoryIcon(t);
                this.tower = t;
            }
        }

        private class InventoryItem {
            private Image image;
            private Item item;

            public InventoryItem(Item i) {
                this.image = EntityGraphicsFactory.loadItemInventoryIcon(i);
                this.item = i;
            }
        }

        private List<InventoryTower> towersInventory;
        private List<InventoryItem> itemsInventory;

        public InventoryView() {
            this.towersInventory = new LinkedList<>();
            this.itemsInventory = new LinkedList<>();
            this.copyInventories();
            this.setSize(mapView.getWidth(), 2 * INVENTORY_FRAME_HEIGHT);
        }

        public void copyInventories() {
            for (Tower t : currentPlayer.getTowersInventory()) {
                this.towersInventory.add(new InventoryTower(t));
            }

            for (Item i : currentPlayer.getItemsInventory()) {
                this.itemsInventory.add(new InventoryItem(i));
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int j = 0; j < this.towersInventory.size(); j++) {
                g.drawImage(this.towersInventory.get(j).image, j * INVENTORY_FRAME_WIDTH, mapView.getHeight(),
                        INVENTORY_FRAME_WIDTH, INVENTORY_FRAME_HEIGHT, this);
            }

            for (int j = 0; j < this.itemsInventory.size(); j++) {
                g.drawImage(this.itemsInventory.get(j).image, j * INVENTORY_FRAME_WIDTH,
                        mapView.getHeight() + INVENTORY_FRAME_HEIGHT, INVENTORY_FRAME_WIDTH, INVENTORY_FRAME_HEIGHT,
                        this);
            }

            if (selectionFrame.position != null) {
                g.drawImage(selectionFrame.image, selectionFrame.position.getX() * IMAGE_WIDTH,
                        selectionFrame.position.getY() * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
            }
        }

        public void removeTower(Tower t) {
            for (InventoryTower invTower : this.towersInventory) {
                if (invTower.tower.equals(t)) {
                    this.towersInventory.remove(invTower);
                    return;
                }
            }
        }

        public void removeItem(Item i) {
            for (InventoryItem invItem : this.itemsInventory) {
                if (invItem.item.equals(i)) {
                    this.itemsInventory.remove(invItem);
                    return;
                }
            }
        }
    }

    private Game game;
    private Board currentBoard;
    private Player currentPlayer;

    private MapView mapView;
    private InventoryView inventoryView;
    private SelectionFrame selectionFrame;


    //FIXME : find a solution for window size not including the toolbar height to its getHeight()
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
        this.setSize(mapView.getWidth(),mapView.getHeight()+inventoryView.getHeight()+37);

        GameCursor cursor = new GameCursor();
        this.addMouseListener(cursor);
        this.addMouseMotionListener(cursor);
        this.add(this.mapView);
        this.add(this.inventoryView);



        this.selectionFrame = new SelectionFrame(InterfaceGraphicsFactory.loadSelectionFrame());

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

    private static boolean isInPanel(JPanel panel, Point p) {
        if (panel == null || p == null)
            return false;
        return p.getX() >= panel.getX() && p.getY() >= panel.getY()
                && p.getX() < panel.getX() + panel.getWidth()
                && p.getY() < panel.getY() + panel.getHeight();
    }

}
