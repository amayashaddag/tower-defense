package view;

import java.awt.Image;

import tools.*;

public class Bullet {
    private Image image;
    private Coordinates position;

    public Bullet(Coordinates position) {
        this.position = position;
        this.image = EntityGraphicsFactory.loadBullet();
    }

    public Image getImage() {
        return image;
    }

    public Coordinates getPosition() {
        return position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }
}
