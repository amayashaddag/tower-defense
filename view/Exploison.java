package view;

import java.awt.Image;

import tools.*;

public class Exploison {
    private Image[] frames;
    private Coordinates position;
    private int framesIndex = 0;

    public Exploison(Coordinates position, Image[] frames) {
        this.position = position;
        this.frames = frames;
    }

    public boolean hastNextFrame() {
        return framesIndex + 1 < frames.length;
    }

    public void setNextFrame() {
        this.framesIndex++;
    }

    public Image getFrame() {
        return frames[framesIndex];
    }

    public Coordinates getPosition() {
        return this.position;
    }

    public static Exploison bombExploison(Coordinates position) {
        return new Exploison(position, EntityGraphicsFactory.loadBombExploisonFrames());
    }

    public static Exploison poisonExploison(Coordinates position) {
        return new Exploison(position, EntityGraphicsFactory.loadPoisonExploisonFrames());
    }

    public static Exploison freezeExploison(Coordinates position) {
        return new Exploison(position, EntityGraphicsFactory.loadFreezeExploisonFrames());
    }
}
