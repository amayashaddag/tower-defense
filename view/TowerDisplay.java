package view;

import java.awt.Image;

import model.*;

public class TowerDisplay {
    private Image towerFrame;
    private Tower tower;

    public TowerDisplay(Tower t) {
        this.tower = t;
        String index = t instanceof SimpleTower ? Slot.SIMPLE_TOWER_INDEX : Slot.BOMB_TOWER_INDEX;
        this.towerFrame = EntityGraphicsFactory.loadSlot(index, true);
    }

    public Image getTowerFrame() {
        return towerFrame;
    }

    public Tower getTower() {
        return tower;
    }
}
