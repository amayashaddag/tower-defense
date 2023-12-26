package view;

import java.awt.Image;

import model.Item;
import model.Tower;
import tools.*;

public class SelectionFrame {
    private Image image;
    private IntCoordinates position;
    private Tower isTower;
    private Item isItem;

    public SelectionFrame() {
        this.image = InterfaceGraphicsFactory.loadSelectionFrame();
    }

    public void setPosition(int x, int y) {
        this.position = new IntCoordinates(x, y);
    }

    public void setItem(Item isItem) {
        this.isItem = isItem;
        this.isTower = null;
    }

    public void setTower(Tower isTower) {
        this.isTower = isTower;
        this.isItem = null;
    }

    public Image getImage() {
        return this.image;
    }

    public IntCoordinates getPosition() {
        return this.position;
    }

    public Item getItem() {
        return isItem;
    }

    public Tower getTower() {
        return isTower;
    }

    public boolean isEmpty() {
        return isItem == null && isTower == null;
    }

    public void removeTower() {
        this.isTower = null;
    }
}
