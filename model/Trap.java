package model;

import tools.Coordinates;

public class Trap extends Item implements SingleTargetDamage {
    private int damage;

    private final static int DAMAGE_UPGRADE_VALUE = 5;
    private final static int DURATION_UPGRADE_VALUE = 1;

    public Trap(int level, Coordinates position) {
        super(10 + level, level, position);
    }

    @Override
    void upgrade() {
        this.level++;
        this.damage += DAMAGE_UPGRADE_VALUE;
        this.dureeDeVie += DURATION_UPGRADE_VALUE;
    }

    @Override
    public void attack(Mob mob) {
        mob.beingAttacked(this.damage);
    }
}
