package view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Board;

public class GameView extends JFrame {
    private class MapView extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (currentBoard != null) {
                for (int i = 0; i < currentBoard.getHeight(); i++) {
                    for (int j = 0; j < currentBoard.getWidth(); j++) {
                        ImageIcon image = new ImageIcon("resources/grass.png");
                        Image cellTexture = image.getImage();

                        int width = cellTexture.getWidth(this);
                        int height = cellTexture.getHeight(this);

                        g.drawImage(cellTexture, j * width, i * height, width, height, this);
                    }
                }
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
