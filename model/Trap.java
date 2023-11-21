package model;

import tools.*;

public class Trap extends Item implements SingleTargetDamage {

    private Coordinates teleportationPoint;

    public Trap(Coordinates currentPosition, Coordinates teleportationPoint) {
        this.teleportationPoint = teleportationPoint;
    }

    @Override
    public void attack(Mob mob) {
        if(mob != null && teleportationPoint != null) {
            mob.setPosition(teleportationPoint);
        }
    }
}
