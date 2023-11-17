package model;

public interface SingleTarget {
    void attaqueMob(Mob victime); // Attaquer le mob

    Mob chooseTarget(); // Retourne quel mob à attaquer : exemple le plus proche de la tour

}
