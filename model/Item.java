package model;

import tools.Coordinates;

public abstract class Item {
    protected int dureeDeVie; // durée de vie de l'item
    protected int level; // Chaque item aura un level, on commence par 0.
    protected final Coordinates position;

    /* Constructeur */

    public Item(int dureeDeVie, int level, Coordinates position) {
        this.dureeDeVie = dureeDeVie;
        this.level = level;
        this.position = position;
    }

    /* Concrete functions */
    boolean finDureeDeVie() {
        return this.dureeDeVie <= 0;
    }
    /* Abstract methods */

    abstract void upgrade(); // On pourra upgrade level d'un item.

}
