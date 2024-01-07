package items;

import javax.swing.Timer;

import mobs.Mob;
import model.SingleTargetDamage;
import tools.Coordinates;

public class Trap extends Item implements SingleTargetDamage {

    private static int DAMAGE = 3;
    private static int CURRENT_LEVEL;
    private final static int MAX_ATTACKS = 10;
    private int attacks = 0;
    private Timer attackTimer;
    private static final int COST = 10;
    private static final int MAX_LEVEL = 2;

    private static final int UNLOCKING_COST = 100;
    private static final int UPGRADING_COST = 100;

    public Trap(Coordinates position) {
        super(position);
    }

    public Trap() {
        super(null);
    }

    public void upgrade() {
        DAMAGE++;
        CURRENT_LEVEL++;
    }

    public static int getLevel() {
        return CURRENT_LEVEL;
    }

    public boolean isDead() {
        return this.attacks >= MAX_ATTACKS;
    }

    @Override
    public void attack(Mob mob) {
        mob.beingAttacked(DAMAGE);
        this.attacks++;
    }

    public void setTimer(Timer attackTimer) {
        this.attackTimer = attackTimer;
    }

    public void startAttack() {
        this.attackTimer.start();
    }

    @Override
    public int getCost() {
        return COST;
    }

    @Override
    public boolean upgradable() {
        return CURRENT_LEVEL < MAX_LEVEL;
    }

    @Override
    public int getUnlockingCost() {
        return UNLOCKING_COST;
    }

    @Override
    public int getUpgradingCost() {
        return UPGRADING_COST;
    }
}