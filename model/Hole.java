package model;

import java.util.List;

import tools.*;

public class Hole extends Item implements SingleTargetDamage {

    private static final int DURATION_UPGRADE_VALUE = 1;

    private Coordinates teleportationPoint;
    private static int dureeDeVie = 10;

    public Hole(Coordinates teleportationPoint, int level) {
        super(dureeDeVie+level, null, level);
        this.teleportationPoint = teleportationPoint;
    }

    @Override
    public void attack(Mob mob) {
        if (mob != null && teleportationPoint != null) {
            mob.setPosition(teleportationPoint);
        }
    }
    @Override
    void upgrade() {
        this.level++;
        dureeDeVie += DURATION_UPGRADE_VALUE;
    }
    @Override
    void doWhenDead(List<Mob> targetsMob) {
        return;
    }
}
