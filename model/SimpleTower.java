package model;

import tools.*;

public class SimpleTower extends Tower implements SingleTargetDamage {

    private static int DAMAGE = 1;
    private static int CURRENT_LEVEL = 0;

    private static final int RANGE = 2;
    private static final int RATE_OF_FIRE = 2;


    public SimpleTower(Coordinates position) {
        super(position, RANGE, RATE_OF_FIRE, CURRENT_LEVEL);
    }

    public SimpleTower() {
        super(null, RANGE, RATE_OF_FIRE, CURRENT_LEVEL);
    }

    public void upgrade() {
        DAMAGE++;
        CURRENT_LEVEL++;
    }

    public void attack(Mob mob) {
        if(mob != null) {
            mob.beingAttacked(DAMAGE);
        }
    }

    @Override
    public String toString() {
        return "SimpleTower (2 damage points)";
    }

    public static int getCurrentLevel() {
        return CURRENT_LEVEL;
    }
}
