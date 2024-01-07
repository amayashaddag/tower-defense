package model;

import items.Bomb;
import items.Freeze;
import items.Item;
import items.Trap;
import towers.BombTower;
import towers.SimpleTower;
import towers.Tower;

public class Slot {
    public static final String SIMPLE_TOWER_INDEX = "ST";
    public static final String BOMB_TOWER_INDEX = "BT";

    public static final String BOMB_INDEX = "B";
    public static final String FREEZE_INDEX = "F";
    public static final String POISON_INDEX = "P";
    public static final String TRAP_INDEX = "T";

    private final String index;
    private boolean isUnlocked;
    private boolean isUpgradable;

    public Slot(String index, boolean isUnlocked, boolean isUpgradable) {
        this.index = index;
        this.isUnlocked = isUnlocked;
        this.isUpgradable = isUpgradable;
    }

    public String getIndex() {
        return index;
    }

    public boolean isUnlocked() {
        return isUnlocked;
    }

    public void unlock() {
        this.isUnlocked = true;
    }

    public boolean isUpgradable() {
        return isUpgradable;
    }

    public void upgrade() {
        getWeapon().upgrade();
        this.isUpgradable = getWeapon().upgradable();
    }

    public Weapon getWeapon() {
        switch (index) {
            case SIMPLE_TOWER_INDEX:
                return new SimpleTower();
            case BOMB_TOWER_INDEX:
                return new BombTower();
            case BOMB_INDEX:
                return new Bomb();
            case FREEZE_INDEX:
                return new Freeze();
            case POISON_INDEX:
                return new Poison();
            case TRAP_INDEX:
                return new Trap();
            default:
                return null;
        }

    }

    public Item getItem() {
        switch (index) {
            case BOMB_INDEX:
                return new Bomb();
            case FREEZE_INDEX:
                return new Freeze();
            case POISON_INDEX:
                return new Poison();
            case TRAP_INDEX:
                return new Trap();
            default:
                return null;
        }
    }

    public Tower getTower() {
        if (index.equals(SIMPLE_TOWER_INDEX))
            return new SimpleTower();
        return new BombTower();
    }

    public int getCost() {
        return getWeapon().getCost();
    }
}
