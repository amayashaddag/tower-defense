package model;

import java.util.List;
import java.io.Serializable;

public class Player implements Serializable {

    private String pseudo;
    private Slot[] towersInventory;
    private Slot[] itemsInventory;
    private int credit;



    public final static int TOWERS_INVENTORY_SIZE = 2;
    public final static int ITEMS_INVENTORY_SIZE = 4;
    public static final int INITIAL_CREDIT = 200000;
    
    //FIXME READ HERE
    /* Implement a file-reading system where it takes the list of all players and put it in PLAYERS_LIST
    Create a login system that uses pseudo and password and handle none-existing users errors 
    Implement writing player's progression in file
    Choose file storing system */

    private static List<Player> PLAYERS_LIST;

    private final Slot[] DEFAULT_TOWERS_INVENTORY = {
            new Slot(Slot.SIMPLE_TOWER_INDEX, true, true),
            new Slot(Slot.BOMB_TOWER_INDEX, false, true)
    };

    private final Slot[] DEFAULT_ITEMS_INVENTORY = {
            new Slot(Slot.BOMB_INDEX, true, true),
            new Slot(Slot.FREEZE_INDEX, false, true),
            new Slot(Slot.TRAP_INDEX, false, true),
            new Slot(Slot.POISON_INDEX, false, true)
    };

    public Player(String pseudo) {
        this.pseudo = pseudo;
        this.towersInventory = DEFAULT_TOWERS_INVENTORY;
        this.itemsInventory = DEFAULT_ITEMS_INVENTORY;
        this.credit = INITIAL_CREDIT;
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

    public boolean isUpgradable(String index) {
        if (index.length() > 1) {
            return towerIsUpgradable(index);
        }
        return itemIsUpgradable(index);
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

    private boolean towerIsUpgradable(String index) {
        for (Slot tower : towersInventory) {
            if (tower.getIndex().equals(index)) {
                return tower.isUpgradable();
            }
        }
        return false;
    }

    private boolean itemIsUpgradable(String index) {
        for (Slot item : itemsInventory) {
            if (item.getIndex().equals(index)) {
                return item.isUpgradable();
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

    public boolean hasEnoughCredit(int price) {
        return credit >= price;
    }
}