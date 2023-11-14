package model;

import tools.*;

public abstract class Tower {
    private Coordinates position;
    private int nbOfAttacks;

    public Tower(Coordinates position, int nbOfAttacks) {
        this.position = position;
        this.nbOfAttacks = nbOfAttacks;
    }

    public Coordinates getPosition() {
        return this.position;
    }

    public int getNbOfAttacks() {
        return this.nbOfAttacks;
    }
}