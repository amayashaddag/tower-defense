package model;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private String pseudo;
    private List<Tower> towersInventory;
    private List<Item> itemsInventory;

    private static int TOWERS_INVENTORY_SIZE = 8;
    private static int ITEMS_INVENTORY_SIZE = 4;

    public Player(String pseudo) {
        this.pseudo = pseudo;
        this.towersInventory = new ArrayList<Tower>();
        this.itemsInventory = new ArrayList<Item>();
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public List<Tower> getTowersInventory() {
        return this.towersInventory;
    }

    public List<Item> getItemsInventory() {
        return this.itemsInventory;
    }

    private void removeFromTowersInventory(Tower t) {
        this.towersInventory.remove(t);
    }

    public void removeFromItemsInventory(Item i) {
        this.itemsInventory.remove(i);
    }

    /* Ici les fonctions add renvoient des booleans pour le cas où
     * on veut tester si l'inventaire serait rempli ou pas afin de
     * pouvoir afficher un message dans le cas où le joueur ne peut pas
     * ajouter quelque chose à un des inventaires */

    public boolean addToTowersInventory(Tower t) {
        if(this.towersInventory.size() < TOWERS_INVENTORY_SIZE) {
            this.towersInventory.add(t);
            return true;
        }
        return false;
    }

    public boolean addToItemsInventory(Item i) {
        if(this.itemsInventory.size() < ITEMS_INVENTORY_SIZE) {
            this.itemsInventory.add(i);
            return true;
        }
        return false;
    }

    public Tower getTowerFromIndex(int index) {
        Tower t = this.towersInventory.get(index);
        this.removeFromTowersInventory(t);
        return t;
    }

    public Item getItemFromIndex(int index) {
        Item i = this.itemsInventory.get(index);
        this.removeFromItemsInventory(i);
        return i;
    }
}