package model;

import items.Bomb;
import items.Item;
import towers.SimpleTower;
import towers.Tower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;

import java.util.HashMap;

public class Player implements Serializable {

    private Slot[] towersInventory;
    private Slot[] itemsInventory;
    private int credit;

    private final static String DATA_REPOSITORY_PATH = "resources/data/";
    private final static String PLAYER_DATA_FILE = "player-data-file.txt";
    private final static String WEAPONS_DATA_FILE = "weapons-data-file.txt";

    public final static int TOWERS_INVENTORY_SIZE = 2;
    public final static int ITEMS_INVENTORY_SIZE = 4;
    public static final int INITIAL_CREDIT = 200;

    private final Slot[] DEFAULT_TOWERS_INVENTORY = {
            new Slot(Slot.SIMPLE_TOWER_INDEX, true),
            new Slot(Slot.BOMB_TOWER_INDEX, false)
    };

    private final Slot[] DEFAULT_ITEMS_INVENTORY = {
            new Slot(Slot.BOMB_INDEX, true),
            new Slot(Slot.FREEZE_INDEX, false),
            new Slot(Slot.TRAP_INDEX, false),
            new Slot(Slot.POISON_INDEX, false)
    };

    public Player() {

        Player savedPlayer;
        try {
            savedPlayer = readPlayerUnlockingData();
            readPlayerWeaponLevels();
        } catch (Exception e) {
            savedPlayer = null;
        }

        this.towersInventory = savedPlayer != null ? savedPlayer.towersInventory : DEFAULT_TOWERS_INVENTORY;
        this.itemsInventory = savedPlayer != null ? savedPlayer.itemsInventory : DEFAULT_ITEMS_INVENTORY;
        this.credit = savedPlayer != null ? savedPlayer.credit : INITIAL_CREDIT;
    }

    public Slot[] getTowersInventory() {
        return towersInventory;
    }

    public Slot[] getItemsInventory() {
        return itemsInventory;
    }

    public void setCredit(int credit) {
        this.credit = credit;
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

    private Weapon getWeaponFromIndex(String index) {
        for (Slot s : DEFAULT_TOWERS_INVENTORY) {
            if (s.getIndex().equals(index)) {
                return s.getWeapon();
            }
        }
        for (Slot s : DEFAULT_ITEMS_INVENTORY) {
            if (s.getIndex().equals(index)) {
                return s.getWeapon();
            }
        }
        return null;
    }

    public void savePlayerData() throws IOException, ClassNotFoundException {
        saveUnlockingPlayerData();
        savePlayerWeaponLevels();
    }

    private void saveUnlockingPlayerData() throws IOException {
        String url = DATA_REPOSITORY_PATH + PLAYER_DATA_FILE;
        File playerDataFile = new File(url);
        FileOutputStream playerDataFileStream = new FileOutputStream(playerDataFile);
        ObjectOutputStream playerDataObjectStream = new ObjectOutputStream(playerDataFileStream);
        playerDataObjectStream.writeObject(this);
        playerDataObjectStream.close();
    }

    private Player readPlayerUnlockingData() throws IOException, ClassNotFoundException {
        String url = DATA_REPOSITORY_PATH + PLAYER_DATA_FILE;
        File playerDataFile = new File(url);
        FileInputStream playerDataFileStream = new FileInputStream(playerDataFile);
        ObjectInputStream playerDataObjectStream = new ObjectInputStream(playerDataFileStream);
        Player savedPlayer = (Player) playerDataObjectStream.readObject();
        playerDataObjectStream.close();
        return savedPlayer;
    }

    private void savePlayerWeaponLevels() throws IOException {
        Map<String, Integer> weaponLevels = getWeaponLevels();
        String url = DATA_REPOSITORY_PATH + WEAPONS_DATA_FILE;
        File weaponsDataFile = new File(url);
        FileOutputStream weaponsDataOutputStream = new FileOutputStream(weaponsDataFile);
        ObjectOutputStream weaponsDataObjectStream = new ObjectOutputStream(weaponsDataOutputStream);
        weaponsDataObjectStream.writeObject(weaponLevels);
        weaponsDataObjectStream.close();
    }

    private void readPlayerWeaponLevels() throws IOException, ClassNotFoundException, ClassCastException {
        String url = DATA_REPOSITORY_PATH + WEAPONS_DATA_FILE;
        File weaponsDataFile = new File(url);
        FileInputStream weaponsDataInputStream = new FileInputStream(weaponsDataFile);
        ObjectInputStream weaponsDataObjectStream = new ObjectInputStream(weaponsDataInputStream);

        @SuppressWarnings("unchecked")
        Map<String, Integer> weaponsData = (Map<String, Integer>) weaponsDataObjectStream.readObject();
        applyWeaponLevels(weaponsData);
        weaponsDataObjectStream.close();
    }

    private void applyWeaponLevels(Map<String, Integer> weaponLevels) {
        for (String weaponIndex : weaponLevels.keySet()) {
            int weaponLevel = weaponLevels.get(weaponIndex);
            Weapon associatedWeaponInstance = getWeaponFromIndex(weaponIndex);
            associatedWeaponInstance.setCurrentLevel(weaponLevel);
        }
    }

    private Map<String, Integer> getWeaponLevels() {
        Map<String, Integer> weaponLevelsMap = new HashMap<>();
        for (Slot s : towersInventory) {
            String weaponIndex = s.getIndex();
            Weapon associatedWeaponInstance = s.getWeapon();
            int weaponLevel = associatedWeaponInstance.getCurrentLevel();
            weaponLevelsMap.put(weaponIndex, weaponLevel);
        }
        for (Slot s : itemsInventory) {
            String weaponIndex = s.getIndex();
            Weapon associatedWeaponInstance = s.getWeapon();
            int weaponLevel = associatedWeaponInstance.getCurrentLevel();
            weaponLevelsMap.put(weaponIndex, weaponLevel);
        }
        return weaponLevelsMap;
    }
}