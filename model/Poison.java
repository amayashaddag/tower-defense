package model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Poison extends Item implements ZoneDamage {

    private static int CURRENT_LEVEL = 0;
    private static int POISON_DURATION = 5;
    private static int DAMAGE = 1;

    private final int RANGE = 3;

    public Poison(Coordinates position) {
        super(position);
    }

    public static void upgrade() {
        DAMAGE++;
        POISON_DURATION ++;
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
            POISON_DURATION);
        }
    }

    public int getRange() {
        return RANGE;
    }

    public static int getLevel() {
        return CURRENT_LEVEL;
    }

}
