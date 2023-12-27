package model;

public class Slot {

    public static final String SIMPLE_TOWER_INDEX = "ST";
    public static final String BOMB_TOWER_INDEX = "BT";

    public static final String BOMB_INDEX = "B";
    public static final String FREEZE_INDEX = "F";
    public static final String POISON_INDEX = "P";
    public static final String TRAP_INDEX = "T";

    private final String index;
    private boolean isUnlocked;

    public Slot(String index, boolean isUnlocked) {
        this.index = index;
        this.isUnlocked = isUnlocked;
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

    public Tower getTower() {
        if (index.equals(SIMPLE_TOWER_INDEX)) return new SimpleTower();
        else return null;
    }

    public Item getItem() {
        switch (index) {
            case BOMB_INDEX : return new Bomb();
            case FREEZE_INDEX : return new Freeze();
            case POISON_INDEX : return new Poison();
            case TRAP_INDEX : return new Trap();
            default : return null;
        }
    }
}
