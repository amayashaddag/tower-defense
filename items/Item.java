package items;

import model.Weapon;
import tools.Coordinates;

public abstract class Item implements Weapon {
    private Coordinates position;

    public Item(Coordinates position) {
        this.position = position;
    }

    public Coordinates getPosition() {
        return this.position;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    abstract public void upgrade();

    abstract public boolean upgradable();

    abstract public int getCost();
}
