package model;

import tools.*;

public abstract class Tower {
    private Coordinates position;
    private int nbOfAttacks;
    private int range; // Rayon du perimètre sur lequel il attaque

    public Tower(Coordinates position, int nbOfAttacks, int range) {
        this.position = position;
        this.nbOfAttacks = nbOfAttacks;
        this.range = range;
    }
    /* Getters */

    public Coordinates getPosition() {
        return this.position;
    }

    public int getNbOfAttacks() {
        return this.nbOfAttacks;
    }

    public boolean outOfAttack() {
        return this.nbOfAttacks <= 0;
    }

    public int getRange() {
        return range;
    }
    /*Setters */
    public void setNbOfAttacks(int nbOfAttacks) {
        this.nbOfAttacks = nbOfAttacks;
    }
    public void setPosition(int x,int y) {
        this.position.setX(x);
        this.position.setY(y);
    }
    public void setRange(int range) {
        this.range = range;
    }
    /*Abstract methods */
    abstract void upgrade(); //Uprade les tours
}
