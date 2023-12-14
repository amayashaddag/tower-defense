package model;

import tools.Coordinates;

public class Trap extends Item implements SingleTargetDamage {
    private int damage;

    private final static int DAMAGE_UPGRADE_VALUE = 5;

    public Trap(int level, Coordinates position) {
        super(10 + level, level, position);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.damage += DAMAGE_UPGRADE_VALUE;
    }

    @Override
    public void attack(Mob mob) {
        mob.beingAttacked(this.damage);
    }
}
