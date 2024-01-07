package items;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mobs.Mob;
import model.ZoneDamage;
import tools.Coordinates;

public class Freeze extends Item implements ZoneDamage {
    private static int FREEZE_DURATION = 5;
    private static int CURRENT_LEVEL = 0;
    private static int RANGE = 1;
    private static final int COST = 10;
    private static final int MAX_LEVEL = 2;

    private static final int UNLOCKING_COST = 100;
    private static final int UPGRADING_COST = 100;

    public Freeze(Coordinates position) {
        super(position);
    }

    public Freeze() {
        super(null);
    }

    @Override
    public void attack(List<Mob> mobs) {
        for (Mob mob : mobs) {
            if (mob != null) {
                double currentMobSpeed = mob.getSpeed();
                mob.setSpeed(0);
                Timer timer = new Timer();
                (timer).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mob.setSpeed(currentMobSpeed);
                    }
                }, FREEZE_DURATION * 1000);
            }
        }
    }

    public void upgrade() {
        FREEZE_DURATION++;
        CURRENT_LEVEL++;
    }

    public int getRange() {
        return RANGE;
    }

    public int getLevel() {
        return CURRENT_LEVEL;
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
}
