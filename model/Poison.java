package model;

import java.util.List;

public class Poison extends Item implements ZoneDamage {
    private int damage;
    private final static int range=5;

    Poison(int dureeDeVie, List<Mob> targetMobs, int level) {
        super(dureeDeVie, targetMobs, level);
        this.damage = 10 + this.level * 2; // Pour le niveau 0 on met 10 de dégâts puis on rajoute 2 points de dégâts
                                           // pour chaque level
    }

    @Override
    void upgrade() {
        this.level++;
        this.damage += 2;
    }

    @Override
    void doWhenDead(List<Mob> targetsMob) { 
        this.damage=0; //Solution temporaire
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(this.damage);
        }
    }
    public static int getRange() {
        return range;
    }

}
