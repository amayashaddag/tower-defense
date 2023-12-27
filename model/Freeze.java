package model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Freeze extends Item implements ZoneDamage {
    private static int FREEZE_DURATION = 5;
    private static int CURRENT_LEVEL = 0;
    private static int RANGE = 1;

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

    public static void upgrade() {
        FREEZE_DURATION++;
        CURRENT_LEVEL++;
    }

    public int getRange() {
        return RANGE;
    }

    public int getLevel() {
        return CURRENT_LEVEL;
    }
}
