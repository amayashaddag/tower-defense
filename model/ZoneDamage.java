package model;

import java.util.List;

public interface ZoneDamage {
    void affectZone(List<Mob> targetMobs); // x et y représentent le centre de la zone à attaquer
}
