package model;

import tools.Coordinates;

public abstract class Item {
    private Coordinates position;

    public Item(Coordinates position) {
        this.position = position;
    }

    public Coordinates getPosition() {
        return this.position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

}
