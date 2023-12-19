package model;

import tools.Coordinates;

public class Mob {


    private static final int INITIAL_HP = 1;
    private static final int INITIAL_SPEED = 1;
    private static final int INITIAL_DAMAGE = 1;
    public static final int MAX_LEVEL = 2;

    private int level;
    private int hp; // Nombre d'HP d'un mob
    private int speed; // Vitesse du mob
    private Coordinates position; // Cordonnées de la position du mob
    private int damage;
    private Direction direction;

    /* Constructeur */
    public Mob(int level) {
        this.hp = INITIAL_HP + level;
        this.speed = INITIAL_SPEED + level;
        this.damage = INITIAL_DAMAGE + level;
        this.level = level;
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

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction d) {
        this.direction = d;
    }

    public void attackBase(Base b) {
        if(b != null) {
            b.decrementHp(damage);
        }
    }

    public int getLevel() {
        return this.level;
    }

    public void updatePosition() {

    }

    @Override
    public String toString() {
        return String.valueOf(hp);
    }
}