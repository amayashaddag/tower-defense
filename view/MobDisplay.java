package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import model.*;

public class MobDisplay {
    private Image[] northFrames;
    private Image[] southFrames;
    private Image[] eastFrames;
    private Image[] westFrames;

    private Image currentFrame;
    private int frameIndex = 0;
    private Timer animationTimer;

    private Mob mob;

    private static final int MOB_ANIMATION_DELAY = 50;

    public MobDisplay(Mob mob) {
        this.mob = mob;
        this.northFrames = EntityGraphicsFactory.loadNorthFrames(mob);
        this.westFrames = EntityGraphicsFactory.loadWestFrames(mob);
        this.southFrames = EntityGraphicsFactory.loadSouthFrames(mob);
        this.eastFrames = EntityGraphicsFactory.loadEastFrames(mob);
        this.animationTimer = new Timer(MOB_ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Image[] currentFrames;
                switch (mob.getDirection()) {
                    case EAST:
                        currentFrames = eastFrames;
                        break;
                    case NORTH:
                        currentFrames = northFrames;
                        break;
                    case SOUTH:
                        currentFrames = southFrames;
                        break;
                    default:
                        currentFrames = westFrames;
                        break;
                }
                currentFrame = currentFrames[frameIndex];
                frameIndex++;
                frameIndex %= EntityGraphicsFactory.NB_OF_FRAMES;
            }
        });
    }

    public void startMobAnimation() {
        this.animationTimer.start();
    }

    public void stopMobAnimation() {
        this.animationTimer.stop();
    }

    public Mob getMob() {
        return mob;
    }

    public Image getCurrentFrame() {
        return currentFrame;
    }
}