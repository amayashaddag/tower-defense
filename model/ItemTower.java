package model;

import java.util.List;

import tools.*;

public class ItemTower<T extends Item & ZoneDamage> extends Tower implements ZoneDamage {
    private T item;

    private static int RANGE;
    private static int NB_OF_ATTACKS;

    public ItemTower(Coordinates position, T item) {
        super(position, NB_OF_ATTACKS, RANGE);
        this.item = item;
    }

    public T getItem() {
        return this.item;
    }

    public void attack(List<Mob> mobs) {
        
    }

    public void upgrade() {

    }
}
