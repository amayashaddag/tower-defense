package model;

import tools.Coordinates;

public class SimpleMob extends Mob {

    private static int SIMPLE_MOB_HP = 5;
    private static int SIMPLE_MOB_SPEED = 3;

    SimpleMob(Coordinates position) {
        super(position, SIMPLE_MOB_HP, SIMPLE_MOB_SPEED);
    }
}
