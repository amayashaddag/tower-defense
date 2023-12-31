package model;

import java.util.List;

import tools.*;

public class BombTower extends Tower implements ZoneDamage {
    private static int CURRENT_LEVEL = 0;
    private static final int RANGE = 3;
    private static final int RATE_OF_FIRE = 5;
    private static int DAMAGE = 3;
    private static final int MAX_LEVEL = 3;
    private static int cost = 100;

    public BombTower(Coordinates position) {
        super(position);
    }

    public BombTower() {
        super(null);
    }

    public void attack(List<Mob> mobs) {
        for (Mob mob : mobs) {
            mob.beingAttacked(DAMAGE);
        }
    }

    @Override
    public int getRange() {
        return RANGE;
    }

    @Override
    public int getRateOfFire() {
        return RATE_OF_FIRE;
    }

    public void upgrade() {
        CURRENT_LEVEL++;
        DAMAGE++;
    }

    public static int getLevel() {
        return CURRENT_LEVEL;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public boolean upgradable() {
        return CURRENT_LEVEL < MAX_LEVEL;
    }
}
