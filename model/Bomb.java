package model;

import java.util.List;

import tools.Coordinates;

public class Bomb extends Item implements ZoneDamage {
    private static final int INITIAL_BOMB_DAMAGE = 5;
    private static final int RANGE = 5;
    private static final int DAMAGE_UPGRADE_VALUE = 10;

    private int damage = INITIAL_BOMB_DAMAGE;


    public Bomb(int dureeDeclenchement, int level, Coordinates position) {
        super(dureeDeclenchement, level, position);
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(this.damage);
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.damage += DAMAGE_UPGRADE_VALUE;
    }

    public static int getRange() {
        return RANGE;
    }
}
