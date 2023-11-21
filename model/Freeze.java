package model;

import java.util.Timer;
import java.util.TimerTask;

public class Freeze extends Item implements SingleTargetDamage { 
    private static int FREEZE_DURATION = 5;

    @Override
    public void attack(Mob mob) {
        if(mob != null) {
            int currentMobSpeed = mob.getSpeed();
            mob.setSpeed(0);
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    mob.setSpeed(currentMobSpeed);
                }
            }, FREEZE_DURATION);
        }
    }
}
