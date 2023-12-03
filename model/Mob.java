package model;

import tools.Coordinates;

public abstract class Mob {
    private int hp; // Nombre d'HP d'un mob
    private int speed; // Vitesse du mob
    private Coordinates position; // Cordonnées de la position du mob
    private int damage;

    /* Constructeur */
    public Mob(Coordinates position, int hp, int speed, int damage) {
        this.hp = hp;
        this.speed = speed;
        this.position = position;
        this.damage = damage;
    }

    /* Getters */
    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDamage() {
        return this.damage;
    }

    public Coordinates getPosition() { // On crée un nouvel objet
        return new Coordinates(this.position.getX(), this.position.getY());
    }

    /* Setters */
    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    /* Get Info */
    public boolean isDead() {
        return (this.hp <= 0);
    }

    public void beingAttacked(int damage) {
        this.hp = this.hp - damage;
    }

    public void attackBase(Base b) {
        if(b != null) {
            b.decrementHp(damage);
        }
    }
}