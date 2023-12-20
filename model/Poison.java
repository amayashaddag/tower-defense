package model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tools.Coordinates;

public class Poison extends Item implements ZoneDamage {
    private int damage;

    public static int CURRENT_LEVEL = 0;
    private static int POISON_DURATION;

    private final int RANGE = 3;;

    private final static int DAMAGE_UPGRADE_VALUE = 2;

    public Poison(int dureeDeVie, int level, Coordinates position) {
        super(position);
        this.damage = 10 + CURRENT_LEVEL * 2; // Pour le niveau 0 on met 10 de dégâts puis on rajoute 2 points de dégâts
        // pour chaque level
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.damage += DAMAGE_UPGRADE_VALUE;
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mob.beingAttacked(damage);
                }
            }, 
            POISON_DURATION);
        }
    }

    public int getRange() {
        return RANGE;
    }

}
