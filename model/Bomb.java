package model;

import java.util.List;

public class Bomb extends Item implements ZoneDamage {
    private static int BOMB_DAMAGE = 5;

    @Override
    public void attack(List<Mob> targetMobs) {
        for(Mob mob : targetMobs) {
            mob.setHp(mob.getHp() - BOMB_DAMAGE);
        }
    }
}
