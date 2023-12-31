package model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Poison extends Item implements ZoneDamage {

    private static int CURRENT_LEVEL = 0;
    private static int POISON_DURATION = 3;
    private static int DAMAGE = 1;
    private final int RANGE = 3;
    private static int cost = 100;
    private static final int MAX_LEVEL = 3;

    public Poison(Coordinates position) {
        super(position);
    }

    public Poison() {
        super(null);
    }

    public void upgrade() {
        DAMAGE++;
        POISON_DURATION++;
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mob.beingAttacked(DAMAGE);
                }
            },
                    POISON_DURATION * 1000);
        }
    }

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
