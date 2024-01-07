package towers;

import javax.swing.Timer;

import model.Weapon;
import tools.*;

public abstract class Tower implements Weapon {
    private Coordinates position;
    private Timer attackFrequencyTimer;

    public Tower(Coordinates position) {
        this.position = position;
    }

    public Coordinates getPosition() {
        return this.position;
    }

    public abstract int getRange();

    public abstract int getRateOfFire();

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setTimer(Timer timer) {
        this.attackFrequencyTimer = timer;
    }

    public void startAttack() {
        this.attackFrequencyTimer.start();
    }

    public void stopAttack() {
        this.attackFrequencyTimer.stop();
    }

    public abstract void upgrade();

    public abstract boolean upgradable();

    public abstract int getCost();
}
