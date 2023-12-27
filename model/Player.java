package model;

public class Player {

    private String pseudo;
    private Slot[] towersInventory;
    private Slot[] itemsInventory;

    public static int TOWERS_INVENTORY_SIZE = 2;
    public static int ITEMS_INVENTORY_SIZE = 4;

    private static final Slot[] DEFAULT_TOWERS_INVENTORY = {
        new Slot(Slot.SIMPLE_TOWER_INDEX, true),
        new Slot(Slot.BOMB_TOWER_INDEX, false)
    };

    private static final Slot[] DEFAULT_ITEMS_INVENTORY = {
        new Slot(Slot.BOMB_INDEX, true),
        new Slot(Slot.FREEZE_INDEX, true),
        new Slot(Slot.TRAP_INDEX, true),
        new Slot(Slot.POISON_INDEX, true),
    };

    public Player(String pseudo) {
        this.pseudo = pseudo;
        this.towersInventory = DEFAULT_TOWERS_INVENTORY;
        this.itemsInventory = DEFAULT_ITEMS_INVENTORY;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public Slot[] getTowersInventory() {
        return towersInventory;
    }

    public Slot[] getItemsInventory() {
        return itemsInventory;
    }

    public Tower getTowerFromIndex(int index) {
        Tower t;
        String towerIndex = towersInventory[index].getIndex();
        switch (towerIndex) {
            case Slot.SIMPLE_TOWER_INDEX : 
                t = new SimpleTower();
                break;
            default : return null;
        }
        return t;

    }

    public Item getItemFromIndex(int index) {
        Item i;
        String itemIndex = itemsInventory[index].getIndex();
        switch (itemIndex) {
            case Slot.BOMB_INDEX : 
                i = new Bomb();
                break;
            default : return null;
        }
        return i;
    }
}