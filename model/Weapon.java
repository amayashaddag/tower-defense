package model;

public interface Weapon {
    public void upgrade();
    public boolean upgradable();
    public int getCost();
    public int getUnlockingCost();
    public int getUpgradingCost();
}
