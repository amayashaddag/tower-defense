package model;

import java.util.List;

public class Bomb extends Item implements ZoneDamage {
    private static final int INITIAL_BOMB_DAMAGE = 5;
    private static final int range = 5;
    private static final int DAMAGE_UPGRADE_VALUE = 10;

    private int damage = INITIAL_BOMB_DAMAGE;


    Bomb(int dureeDeclenchement, List<Mob> targetMobs, int level) {
        super(dureeDeclenchement, targetMobs, level);
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(this.damage);
        }
    }

    @Override
    void doWhenDead(List<Mob> targetsMob) {
        attack(targetsMob);
    }

    @Override
    void upgrade() {
        this.level++;
        this.damage += DAMAGE_UPGRADE_VALUE;
    }
    public static int getRange() {
        return range;
    }

}
