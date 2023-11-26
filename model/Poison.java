package model;

import java.util.List;

public class Poison extends Item implements ZoneDamage {
    private int damage;

    private final static int DAMAGE_UPGRADE_VALUE = 2;

    public Poison(int dureeDeVie, List<Mob> targetMobs, int level) {
        super(dureeDeVie, targetMobs, level);
        this.damage = 10 + this.level * 2; // Pour le niveau 0 on met 10 de dégâts puis on rajoute 2 points de dégâts
        // pour chaque level
    }

    @Override
    void upgrade() {
        this.level++;
        this.damage += DAMAGE_UPGRADE_VALUE;
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(this.damage);
        }
    }

}
