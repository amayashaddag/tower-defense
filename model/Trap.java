package model;

import tools.Coordinates;

public class Trap extends Item implements SingleTargetDamage {

    private static int DAMAGE = 3;
    private static int CURRENT_LEVEL;

    public Trap(Coordinates position) {
        super(position);
    }

    public static void upgrade() {
        DAMAGE++;
        CURRENT_LEVEL++;
    }

    public static int getLevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public void attack(Mob mob) {
        mob.beingAttacked(DAMAGE);
    }
}