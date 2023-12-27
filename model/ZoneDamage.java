package model;

import java.util.List;

public interface ZoneDamage {
    void attack(List<Mob> targetMobs);
    int getRange();
}
