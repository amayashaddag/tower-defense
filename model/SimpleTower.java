package model;

import tools.*;

public class SimpleTower extends Tower implements SingleTargetDamage {

    private static int DAMAGE = 1;
    private static int CURRENT_LEVEL = 0;

    private static final int RANGE = 2;
    private static final int RATE_OF_FIRE = 2;
    private static int cost = 100;
    private static final int MAX_LEVEL = 3;

    public SimpleTower(Coordinates position) {
        super(position);
    }

    public SimpleTower() {
        super(null);
    }

    public void upgrade() {
        DAMAGE++;
        CURRENT_LEVEL++;
    }

    public void attack(Mob mob) {
        if (mob != null) {
            mob.beingAttacked(DAMAGE);
        }
    }

    @Override
    public String toString() {
        return "SimpleTower (2 damage points)";
    }

    @Override
    public int getRateOfFire() {
        return RATE_OF_FIRE;
    }

    @Override
    public int getRange() {
        return RANGE;
    }

    public static int getLevel() {
        return CURRENT_LEVEL;
    }

@Override
public int getCost() {
    return cost;
}
    @Override
    public boolean upgradable() {
        return CURRENT_LEVEL < MAX_LEVEL;
    }
}
