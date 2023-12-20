package model;

import tools.*;

public class Hole extends Item implements SingleTargetDamage {

    public static int CURRENT_LEVEL = 0;
    
    private Coordinates teleportationPoint;

    public Hole(Coordinates position, Coordinates teleportationPoint, int level) {
        super(position);
        this.teleportationPoint = teleportationPoint;
    }

    @Override
    public void attack(Mob mob) {
        if (mob != null && teleportationPoint != null) {
            mob.setPosition(teleportationPoint);
        }
    }

    @Override
    public void upgrade() {
        
    }
}
