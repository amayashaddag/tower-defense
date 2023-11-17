package model;

import java.util.List;

public interface ZoneDamage {
    void affectZone(List<Mob> targetMobs); // x et y représentent le centre de la zone à attaquer
    /* Vaut mieux l'appeler affectZone car à noter que cette fonction peut être appelée
     * par soit les slowing down towers soit les attacking towers (idem pour les items)
     * donc vaut mieux l'appeler ainsi.
     * 
     * A noter également que affectZone fera sûrement appel aux méthodes attack ou slowDown de l'interface
     * selon le type de l'objet
     */
}
