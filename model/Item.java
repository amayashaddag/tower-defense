package model;

import tools.Coordinates;

public abstract class Item {
    private int lifeDuration; // durée de vie de l'item
    private int level; // Chaque item aura un level, on commence par 0.
    private final Coordinates position;

    /* Constructeur */

    public Item(int lifeDuration, int level, Coordinates position) {
        this.lifeDuration = lifeDuration;
        this.level = level;
        this.position = position;
    }

    /* Concrete functions */
    boolean endOfLifeDuration() {
        return this.lifeDuration <= 0;
    }
    /* Abstract methods */

    public Coordinates getPosition() {
        return this.position;
    }

    public int getLevel() {
        return this.level;
    }

    public int getLifeDuration() {
        return this.lifeDuration;
    }

    public void upgrade() {
        this.level++;
        this.lifeDuration++;
    } // On pourra upgrade level d'un item.

}
