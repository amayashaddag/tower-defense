package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.*;

public class GameView extends JFrame {

    private static final int IMAGE_WIDTH = 128, IMAGE_HEIGHT = 128;
    private static final String WINDOW_TITLE = "Tower Defense Game - Le titre est à changer";
    private static final boolean RESIZABILITY = false;

    private class MapView extends JPanel {
        private Image[][] map;

        public MapView() {
            super();
            this.map = new Image[currentBoard.getHeight()][currentBoard.getWidth()];
            this.loadMap();
            this.setSize(IMAGE_WIDTH * currentBoard.getWidth(), IMAGE_HEIGHT * currentBoard.getHeight());
        }

        public void loadMap() {
            for (int i = 0; i < currentBoard.getHeight(); i++) {
                for (int j = 0; j < currentBoard.getWidth(); j++) {
                    ImageIcon image;
                    String url = "resources/" + this.urlToFrame(i, j);
                    image = new ImageIcon(url);
                    this.map[i][j] = image.getImage();
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < currentBoard.getHeight(); i++) {
                for (int j = 0; j < currentBoard.getWidth(); j++) {
                    Image cellTexture = this.map[i][j];
                    g.drawImage(cellTexture, j * IMAGE_WIDTH, i * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT, this);
                }
            }
        }

        private String urlToFrame(int i, int j) {
            switch (currentBoard.cellType(i, j)) {
                case "PLUS":
                    return "plusPath.png";
                case "NORTH-T":
                    return "north-t-path.png";
                case "SOUTH-T":
                    return "south-t-path.png";
                case "EAST-T":
                    return "east-t-path.png";
                case "WEST-T":
                    return "west-t-path.png";
                case "NORTH-WEST":
                    return "north-west-path.png";
                case "SOUTH-WEST":
                    return "south-west-path.png";
                case "NORTH-EAST":
                    return "north-east-path.png";
                case "SOUTH-EAST":
                    return "south-east-path.png";
                case "HORIZONTAL":
                    return "horizontal-path.png";
                case "VERTICAL":
                    return "vertical-path.png";
                default:
                    return "grass.png";
            }
        }
    }

    private Board currentBoard;
    private MapView mapView;

    public GameView(Board currentBoard) {
        this.currentBoard = currentBoard;
        this.mapView = new MapView();
        this.setLayout(new BorderLayout());
        this.add(this.mapView, BorderLayout.CENTER);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(this.mapView.getWidth(), this.mapView.getHeight());
        this.setTitle(WINDOW_TITLE);
        this.setResizable(RESIZABILITY);
    }

    public JPanel getMapView() {
        return this.mapView;
    }
}
