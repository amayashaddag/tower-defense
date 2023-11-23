package model;

import java.util.List;

public abstract class Item {
    protected int dureeDeVie; // durée de vie de l'item
    protected List<Mob> targetMobs;// Listes des mobs à attaquer
    protected int level; // Chaque item aura un level, on commence par 0.

    /* Constructeur */

    Item(int dureeDeVie, List<Mob> targetMobs, int level) {
        this.dureeDeVie = dureeDeVie;
        this.targetMobs = targetMobs;
        this.level = level;
    }

    /* Concrete functions */
    boolean finDureeDeVie() {
        return this.dureeDeVie <= 0;
    }

    void setTargetMobs(List<Mob> targetMobs) {
        this.targetMobs = targetMobs;
    }
    /* Abstract methods */

    abstract void doWhenDead(List<Mob> targetsMob);

    abstract void upgrade(); // On pourra upgrade level d'un item.

}
