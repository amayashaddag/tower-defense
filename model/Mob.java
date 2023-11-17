package model;

import tools.Coordinates;

public abstract class Mob { 
    private int hp; //Nombre d'HP d'un mob
    private int speed; //Vitesse du mob
    private Coordinates position; //Cordonnées de la position du mob
    /*Constructeur */
    Mob(int x,int y,int hp,int speed){
        this.hp=hp;
        this.speed=speed;
        this.position = new Coordinates(x, y);
    }
    /*Getters */
    public int getHp() {
        return hp;
    }
    public int getSpeed() {
        return speed;
    }
    public Coordinates getPosition(){ //On crée un nouvel objet
        return new Coordinates(this.position.getX(), this.position.getY());
    }
    /*Setters */
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void setPosition (int x , int y ){
        this.position.setX(x);
        this.position.setY(y);
    }
    /*Get Info */
    public boolean isDead(){
        return (this.hp<=0);
    }
    /*Abstract methods */
    abstract void move(); //On définit leur comportement devant une intersection par exemple

}