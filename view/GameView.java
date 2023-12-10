package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.*;


public class GameView extends JFrame {
    private class MapView extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (currentBoard != null) {
                for (int i = 0; i < currentBoard.getHeight(); i++) {
                    for (int j = 0; j < currentBoard.getWidth(); j++) {
                        ImageIcon image;
                        String url = "resources/" + this.urlToFrame(i, j);
                        image = new ImageIcon(url);
                        Image cellTexture = image.getImage();

                        int width = cellTexture.getWidth(this);
                        int height = cellTexture.getHeight(this);

                        g.drawImage(cellTexture, j * width, i * height, width, height, this);
                    }
                }
            }
        }

        private String urlToFrame(int i, int j) {
            switch(currentBoard.cellType(i, j)) {
                case "PLUS" : return "plusPath.png";
                case "NORTH-T" : return "north-t-path.png";
                case "SOUTH-T" : return "south-t-path.png";
                case "EAST-T" : return "east-t-path.png";
                case "WEST-T" : return "west-t-path.png";
                case "NORTH-WEST" : return "north-west-path.png";
                case "SOUTH-WEST" : return "south-west-path.png";
                case "NORTH-EAST" : return "north-east-path.png";
                case "SOUTH-EAST" : return "south-east-path.png";
                case "HORIZONTAL" : return "horizontal-path.png";
                case "VERTICAL" : return "vertical-path.png";
                default : return "grass.png";
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
        this.setSize(1280, 720);
        this.setTitle("Tower Defense - Le titre est à changer");
    }

    public MapView getMapView() {
        return this.mapView;
    }
}
