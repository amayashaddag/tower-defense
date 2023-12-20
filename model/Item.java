package model;

import tools.Coordinates;

public abstract class Item {
    private Coordinates position;

    /* Constructeur */

    public Item(Coordinates position) {
        this.position = position;
    }

    /* Abstract methods */

    public Coordinates getPosition() {
        return this.position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }


    //TODO : A implémenter
    public void upgrade() {
        
    } // On pourra upgrade level d'un item.

}
