package items;

import java.util.List;

import mobs.Mob;
import model.ZoneDamage;
import tools.Coordinates;

public class Bomb extends Item implements ZoneDamage {
    private static int DAMAGE = 1;
    public static int CURRENT_LEVEL = 0;

    private final static int MAX_LEVEL = 2;
    private static final int COST = 10;
    private static final int RANGE = 1;
    private static final int UNLOCKING_COST = 0;
    private static final int UPGRADING_COST = 50;

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

    @Override
    public int getUnlockingCost() {
        return UNLOCKING_COST;
    }

    @Override
    public int getUpgradingCost() {
        return UPGRADING_COST;
    }

    @Override
    public int getCurrentLevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public void setCurrentLevel(int currentLevel) {
        CURRENT_LEVEL = currentLevel;
    }
}
