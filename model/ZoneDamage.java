package model;

import java.util.List;

import mobs.Mob;

public interface ZoneDamage {
    void attack(List<Mob> targetMobs);
    int getRange();
}
