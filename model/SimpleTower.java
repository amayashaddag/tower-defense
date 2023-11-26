package model;

import tools.*;

public class SimpleTower extends Tower implements SingleTargetDamage {

    private static final int NB_OF_ATTACKS = 20;
    private static final int RANGE = 3;
    private static final int DAMAGE = 2;

    private int damage;

    public SimpleTower(Coordinates position) {
        super(position, NB_OF_ATTACKS, RANGE);
        this.damage = DAMAGE;
    }

    public SimpleTower() {
        super(null, NB_OF_ATTACKS, RANGE);
        this.damage = DAMAGE;
    }


    // A implémenter plus tard ...

    public void upgrade() {

    }

    public void attack(Mob mob) {
        if(mob != null) {
            mob.beingAttacked(this.damage);
        }
    }
}
