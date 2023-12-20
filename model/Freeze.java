package model;

import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Freeze extends Item implements SingleTargetDamage {
    private static final int FREEZE_DURATION = 5;
    private static final int DURATION_UPGRADE_VALUE = 1;

    private int freezeDuration = FREEZE_DURATION;
    public static int CURRENT_LEVEL = 0;

    public Freeze(int level, Coordinates position) {
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
            }, freezeDuration);
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        freezeDuration += DURATION_UPGRADE_VALUE;
    }
}
