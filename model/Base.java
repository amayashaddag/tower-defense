package model;

public class Base {
    private int hp;

    Base(int hp) {
        this.hp = hp;
    }

    /* Getters */
    public int getHp() {
        return hp;
    }

    public void decrementHp(int decrement) {
        this.hp -= decrement;
    }
    public boolean baseLost(){
        return hp<=0;
    }

    @Override
    public String toString() {
        return "Health points : " + hp;
    }
}