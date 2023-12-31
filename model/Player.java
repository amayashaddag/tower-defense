package model;

public class Player {

    private String pseudo;
    private Slot[] towersInventory;
    private Slot[] itemsInventory;
    public final static int TOWERS_INVENTORY_SIZE = 2;
    public final static int ITEMS_INVENTORY_SIZE = 4;
    private int credit;

    private final Slot[] DEFAULT_TOWERS_INVENTORY = {
            new Slot(Slot.SIMPLE_TOWER_INDEX, true, true, this),
            new Slot(Slot.BOMB_TOWER_INDEX, false, true, this)
    };

    private final Slot[] DEFAULT_ITEMS_INVENTORY = {
            new Slot(Slot.BOMB_INDEX, true, true, this),
            new Slot(Slot.FREEZE_INDEX, true, true, this),
            new Slot(Slot.TRAP_INDEX, true, true, this),
            new Slot(Slot.POISON_INDEX, true, true, this)
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
            case Slot.SIMPLE_TOWER_INDEX:
                t = new SimpleTower();
                break;
            default:
                return null;
        }
        return t;

    }

    public Item getItemFromIndex(int index) {
        Item i;
        String itemIndex = itemsInventory[index].getIndex();
        switch (itemIndex) {
            case Slot.BOMB_INDEX:
                i = new Bomb();
                break;
            default:
                return null;
        }
        return i;
    }

    public boolean isUnlocked(String index) {
        if (index.length() > 1) {
            return towerIsUnlocked(index);
        }
        return itemIsUnlocked(index);
    }

    private boolean towerIsUnlocked(String index) {
        for (Slot tower : towersInventory) {
            if (tower.getIndex().equals(index)) {
                return tower.isUnlocked();
            }
        }
        return false;
    }

    private boolean itemIsUnlocked(String index) {
        for (Slot item : itemsInventory) {
            if (item.getIndex().equals(index)) {
                return item.isUnlocked();
            }
        }
        return false;
    }

    public int getCredit() {
        return this.credit;
    }

    public void wonCredit(int wonAmmount) {
        this.credit += wonAmmount;
    }

    public void lostCredit(int lostAmmount) {
        this.credit -= lostAmmount;
    }
}