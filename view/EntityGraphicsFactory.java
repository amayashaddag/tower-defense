package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import mobs.Mob;
import model.*;
import towers.SimpleTower;

public class EntityGraphicsFactory {

    private final static String ENTITIES_RESOURCES_REPOSITORY = "resources/entity/";
    private final static String SKELTON_FRAMES_REPOSITORY = "skelton-frames/";
    private final static String SOLDIER_FRAMES_REPOSITORY = "soldier-frames/";
    private final static String KNIGHT_FRAMES_REPOSITORY = "knight-frames/";
    private final static String TOWER_FRAMES_REPOSITORY = "tower-frames/";
    private final static String EXPLOISON_FRAMES_REPOSITORY = "exploison-frames/";
    private final static String ITEM_FRAMES_REPOSITORY = "item-frames/";

    private final static String SIMPLE_TOWER = "projectile-tower.png";
    private final static String UPGRADED_SIMPLE_TOWER = "advanced-projectile-tower.png";

    private final static String BOMB_TOWER = "bomb-tower.png";
    private final static String UPGRADED_BOMB_TOWER = "advanced-bomb-tower.png";
    private final static String LOCKED_BOMB_TOWER = "bomb-tower-locked.png";
    private final static String BULLET = "bullet.png";

    private final static String BOMB = "bomb.png";
    private final static String BOMB_LOCKED = "bomb-locked.png";
    private final static String TRAP = "trap.png";
    private final static String TRAP_LOCKED = "trap-locked.png";
    private final static String FREEZE = "freeze.png";
    private final static String FREEZE_LOCKED = "freeze-locked.png";
    private final static String POISON = "poison.png";
    private final static String POISON_LOCKED = "poison-locked.png";

    public final static int NB_OF_FRAMES = 9;
    private final static int EXPLOISON_FRAMES = 5;

    // FIXME : Factoriser le code des 4 fonctions des frames
    public static Image[] loadNorthFrames(Mob mob) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        Image[] frames = new Image[NB_OF_FRAMES];

        if (mob.getLevel() == 0) {
            url += SKELTON_FRAMES_REPOSITORY;
        } else if (mob.getLevel() == 1) {
            url += SOLDIER_FRAMES_REPOSITORY;
        } else {
            url += KNIGHT_FRAMES_REPOSITORY;

        }
        for (int i = 0; i < NB_OF_FRAMES; i++) {
            Image image = new ImageIcon(url + "north-" + (i + 1) + ".png").getImage();
            frames[i] = image;
        }
        return frames;
    }

    public static Image[] loadEastFrames(Mob mob) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        Image[] frames = new Image[NB_OF_FRAMES];

        if (mob.getLevel() == 0) {
            url += SKELTON_FRAMES_REPOSITORY;
        } else if (mob.getLevel() == 1) {
            url += SOLDIER_FRAMES_REPOSITORY;
        } else {
            url += KNIGHT_FRAMES_REPOSITORY;

        }
        for (int i = 0; i < NB_OF_FRAMES; i++) {
            Image image = new ImageIcon(url + "east-" + (i + 1) + ".png").getImage();
            frames[i] = image;
        }
        return frames;
    }

    public static Image[] loadWestFrames(Mob mob) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        Image[] frames = new Image[NB_OF_FRAMES];

        if (mob.getLevel() == 0) {
            url += SKELTON_FRAMES_REPOSITORY;
        } else if (mob.getLevel() == 1) {
            url += SOLDIER_FRAMES_REPOSITORY;
        } else {
            url += KNIGHT_FRAMES_REPOSITORY;

        }
        for (int i = 0; i < NB_OF_FRAMES; i++) {
            Image image = new ImageIcon(url + "west-" + (i + 1) + ".png").getImage();
            frames[i] = image;
        }
        return frames;
    }

    public static Image[] loadSouthFrames(Mob mob) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        Image[] frames = new Image[NB_OF_FRAMES];

        if (mob.getLevel() == 0) {
            url += SKELTON_FRAMES_REPOSITORY;
        } else if (mob.getLevel() == 1) {
            url += SOLDIER_FRAMES_REPOSITORY;
        } else {
            url += KNIGHT_FRAMES_REPOSITORY;

        }
        for (int i = 0; i < NB_OF_FRAMES; i++) {
            Image image = new ImageIcon(url + "south-" + (i + 1) + ".png").getImage();
            frames[i] = image;
        }
        return frames;
    }

    public static Image loadSlot(String slotIndex, boolean isUnlocked) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        if (slotIndex.length() == 2) {
            url += TOWER_FRAMES_REPOSITORY;
            switch (slotIndex) {
                case model.Slot.SIMPLE_TOWER_INDEX:
                    if (SimpleTower.getLevel() == 0)
                        url += SIMPLE_TOWER;
                    else
                        url += UPGRADED_SIMPLE_TOWER;
                    break;
                case model.Slot.BOMB_TOWER_INDEX:
                    if (!isUnlocked) {
                        url += LOCKED_BOMB_TOWER;
                    } else {
                        if (SimpleTower.getLevel() == 0)
                            url += BOMB_TOWER;
                        else
                            url += UPGRADED_BOMB_TOWER;
                    }
                    break;
                default:
                    break;
            }
        } else {
            url += ITEM_FRAMES_REPOSITORY;
            switch (slotIndex) {
                case Slot.BOMB_INDEX : 
                    url += isUnlocked ? BOMB : BOMB_LOCKED;
                    break;
                case Slot.FREEZE_INDEX : 
                    url += isUnlocked ? FREEZE : FREEZE_LOCKED;
                    break;
                case Slot.POISON_INDEX :
                    url += isUnlocked ? POISON : POISON_LOCKED;
                    break;
                case Slot.TRAP_INDEX : 
                    url += isUnlocked ? TRAP : TRAP_LOCKED;
                    break;
                default : break;
            }
        } 
        Image slot = new ImageIcon(url).getImage();
        return slot;
    }

    public static Image loadBullet() {
        String url = ENTITIES_RESOURCES_REPOSITORY + TOWER_FRAMES_REPOSITORY + BULLET;
        return new ImageIcon(url).getImage();
    }

    public static Image[] loadBombExploisonFrames() {
        return loadExploisonFrames("bomb");
    }

    public static Image[] loadPoisonExploisonFrames() {
        return loadExploisonFrames("poison");
    }

    public static Image[] loadFreezeExploisonFrames() {
        return loadExploisonFrames("freeze");
    }

    public static Image loadTrap() {
        String url = ENTITIES_RESOURCES_REPOSITORY + ITEM_FRAMES_REPOSITORY + "trap.png";
        Image image = new ImageIcon(url).getImage();
        return image;
    }

    public static Image[] loadExploisonFrames(String type) {
        Image[] frames = new Image[EXPLOISON_FRAMES];
        String url = ENTITIES_RESOURCES_REPOSITORY + EXPLOISON_FRAMES_REPOSITORY;
        for (int i = 0; i < EXPLOISON_FRAMES; i++) {
            Image frame = new ImageIcon(url + type + "-exploison-" + (i + 1) + ".png").getImage();
            frames[i] = frame;
        }
        return frames;
    }
}
