package model;

import java.util.List;

public class Trap extends Item implements SingleTargetDamage {
    private int damage;

    private final static int DAMAGE_UPGRADE_VALUE = 5;
    private final static int DURATION_UPGRADE_VALUE = 1;

    public Trap(List<Mob> targetMobs, int level) {
        super(10 + level, targetMobs, level);
    }

    @Override
    void upgrade() {
        this.level++;
        this.damage += DAMAGE_UPGRADE_VALUE;
        this.dureeDeVie += DURATION_UPGRADE_VALUE;
    }

    @Override
    public void attack(Mob mob) {
        this.targetMobs.get(0).beingAttacked(this.damage);
    }
}
