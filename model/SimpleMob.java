package model;

import tools.Coordinates;

public class SimpleMob extends Mob {

    private static final int SIMPLE_MOB_HP = 5;
    private static final int SIMPLE_MOB_SPEED = 3;
    private static final int DAMAGE = 2;

    public SimpleMob(Coordinates position) {
        super(position, SIMPLE_MOB_HP, SIMPLE_MOB_SPEED, DAMAGE);
    }
}