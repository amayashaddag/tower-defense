package model;

import java.util.List;

public class Bomb extends Item implements ZoneDamage {
    private static int BOMB_DAMAGE = 5;
    private static int range  =5;

    Bomb(int dureeDeclenchement, List<Mob> targetMobs, int level) {
        super(dureeDeclenchement, targetMobs, level);
    }

    @Override
    public void attack(List<Mob> targetMobs) {
        for (Mob mob : targetMobs) {
            mob.beingAttacked(BOMB_DAMAGE);
        }
    }

    @Override
    void doWhenDead(List<Mob> targetsMob) {
        attack(targetsMob);
    }

    @Override
    void upgrade() {
        this.level++;
        BOMB_DAMAGE = +10;
    }
    public static int getRange() {
        return range;
    }

}
