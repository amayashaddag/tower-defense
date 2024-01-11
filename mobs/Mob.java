package mobs;

import java.util.LinkedList;
import java.util.List;

import model.Base;
import model.Direction;
import tools.Coordinates;
import tools.IntCoordinates;

public class Mob {


    private static final int INITIAL_HP = 1;
    private static final double INITIAL_SPEED = 1.0;
    private static final int INITIAL_DAMAGE = 1;
    public static final int MAX_LEVEL = 2;

    private int level;
    private int hp; // Nombre d'HP d'un mob
    private double speed; // Vitesse du mob
    private Coordinates position; // Cordonnées de la position du mob
    private int damage;
    private Direction direction;
    private List<IntCoordinates> lastVisitedCells;

    /* Constructeur */
    public Mob(int level) {
        this.hp = INITIAL_HP + level;
        this.speed = INITIAL_SPEED + level*0.5;
        this.damage = INITIAL_DAMAGE + level;
        this.level = level;
        this.lastVisitedCells = new LinkedList<>();
    }

    /* Getters */
    public int getHp() {
        return hp;
    }

    public double getSpeed() {
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

    public void setSpeed(double speed) {
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

    public void addToLastVisitedCells(IntCoordinates lastVisitedCell) {
        this.lastVisitedCells.add(lastVisitedCell);
    }

    public List<IntCoordinates> getLastVisitedCells() {
        return lastVisitedCells;
    }

    public boolean isToEliminate(Coordinates baseCoordinates) {
        return isDead() || this.position.round().equals(baseCoordinates.round());
    }

    public boolean isInLastVisitedCells(IntCoordinates coordinates) {
        return lastVisitedCells.contains(coordinates);
    }

    @Override
    public String toString() {
        return String.valueOf(hp);
    }
}