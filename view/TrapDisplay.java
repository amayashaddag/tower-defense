package view;

import java.awt.Image;

import items.Trap;

public class TrapDisplay {
    private Image frame;
    private Trap trap;

    public TrapDisplay(Trap trap) {
        this.frame = EntityGraphicsFactory.loadTrap();
        this.trap = trap;
    }

    public Image getFrame() {
        return frame;
    }

    public Trap getTrap() {
        return trap;
    }
}
