package model;

import java.util.Timer;
import java.util.TimerTask;

public class Freeze extends Item implements SingleTargetDamage {
    private static final int FREEZE_DURATION = 5;
    private static final int DURATION_UPGRADE_VALUE = 1;

    private int freezeDuration = FREEZE_DURATION;

    public Freeze(int level) {
        super(FREEZE_DURATION + level * 1, null, level);
    }

    @Override
    public void attack(Mob mob) {
        if (mob != null) {
            int currentMobSpeed = mob.getSpeed();
            mob.setSpeed(0);
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    mob.setSpeed(currentMobSpeed);
                }
            }, freezeDuration);
        }
    }

    @Override
    void upgrade() {
        freezeDuration += DURATION_UPGRADE_VALUE;
        this.level++;
    }
}
