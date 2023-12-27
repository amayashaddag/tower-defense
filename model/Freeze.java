package model;

import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Freeze extends Item implements SingleTargetDamage {
    private static int FREEZE_DURATION = 5;
    public static int CURRENT_LEVEL = 0;

    public Freeze(Coordinates position) {
        super(position);
    }

    @Override
    public void attack(Mob mob) {
        if (mob != null) {
            int currentMobSpeed = mob.getSpeed();
            mob.setSpeed(0);
            Timer timer = new Timer();
            (timer).schedule(new TimerTask() {
                @Override
                public void run() {
                    mob.setSpeed(currentMobSpeed);
                }
            }, FREEZE_DURATION);
        }
    }

    public static void upgrade() {
        FREEZE_DURATION++;
        CURRENT_LEVEL++;
    }
}
