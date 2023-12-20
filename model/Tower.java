package model;

import tools.*;

public abstract class Tower {
    private Coordinates position;
    private int rateOfFire;
    private int range; // Rayon du perimètre sur lequel il attaque
    private int level;

    public final static int MAX_LEVEL = 3;

    public Tower(Coordinates position,int range, int rateOfFire, int level) {
        this.position = position;
        this.range = range;
        this.level = level;
        this.rateOfFire = rateOfFire;
    }
    /* Getters */

    public Coordinates getPosition() {
        return this.position;
    }

  


    public int getRange() {
        return range;
    }

    public int getLevel() {
        return level;
    }

    public int getRateOfFire() {
        return rateOfFire;
    }

    /* Setters */
  
    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setRange(int range) {
        this.range = range;
    }

    /* Abstract methods */
    public abstract void upgrade(); // Uprade les tours
}
