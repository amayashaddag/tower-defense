package shop;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

import view.*;

public class WeaponDisplay extends JPanel {
    private Image image;

    public WeaponDisplay(String index) {
        super();
        this.image = EntityGraphicsFactory.loadSlot(index, true);
        this.setPreferredSize(new Dimension(GameView.IMAGE_WIDTH, GameView.IMAGE_HEIGHT));
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, GameView.IMAGE_WIDTH, GameView.IMAGE_HEIGHT, this);
    }

    public Image getImage() {
        return image;
    }
}
