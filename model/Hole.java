package model;

import java.util.List;

import tools.*;

public class Hole extends Item implements SingleTargetDamage {

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
        dureeDeVie++;
    }
    @Override
    void doWhenDead(List<Mob> targetsMob) {
        return;
    }
}
