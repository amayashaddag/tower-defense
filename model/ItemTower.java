package model;

import java.util.List;

import tools.*;

public class ItemTower<T extends Item & ZoneDamage> extends Tower implements ZoneDamage {
    private T item;

    private static int CURRENT_LEVEL = 0;
    private static final int RANGE = 3;
    private static final int RATE_OF_FIRE = 5;

    public ItemTower(Coordinates position, T item) {
        super(position, RANGE, RATE_OF_FIRE, CURRENT_LEVEL);
        this.item = item;
    }

    public ItemTower(T item) {
        super(null, RANGE, RATE_OF_FIRE, CURRENT_LEVEL);
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }

    public void attack(List<Mob> mobs) {
        
    }

    public void upgrade() {
        
    }

    public ItemTower<Bomb> bombTower() {
        Bomb bomb = new Bomb();
        return new ItemTower<Bomb>(bomb);
    }
}
