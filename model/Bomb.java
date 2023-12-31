package model;

import java.util.List;

import tools.Coordinates;

public class Bomb extends Item implements ZoneDamage {
    private static int DAMAGE = 1;
    private static final int RANGE = 1;
    public static int CURRENT_LEVEL = 0;
    private static int COST = 10;
    private static int MAX_LEVEL=3;


    public Bomb() {
        super(null);
    }

    public Bomb(Coordinates position) {
        super(position);
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(DAMAGE);
        }
    }

    public void upgrade() {
        DAMAGE++;
        CURRENT_LEVEL++;
    }

    public int getRange() {
        return RANGE;
    }
    @Override
    public int getCost() {
        return COST;
    }
    @Override
    public boolean upgradable() {
        return CURRENT_LEVEL < MAX_LEVEL;
    }
}
