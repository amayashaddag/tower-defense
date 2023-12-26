package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.*;

public class EntityGraphicsFactory {

    private final static String ENTITIES_RESOURCES_REPOSITORY = "resources/entity/";
    private final static String SKELTON_FRAMES_REPOSITORY = "skelton-frames/";
    private final static String SOLDIER_FRAMES_REPOSITORY = "soldier-frames/";
    private final static String KNIGHT_FRAMES_REPOSITORY = "knight-frames/";
    private final static String TOWER_FRAMES_REPOSITORY = "tower-frames/";
    private final static String ITEM_FRAMES_REPOSITORY = "?";

    private final static String SIMPLE_TOWER = "projectile-tower.png";
    private final static String UPGRADED_SIMPLE_TOWER = "advanced-projectile-tower.png";

    private final static String BOMB_TOWER = "bomb-tower.png";
    private final static String UPGRADED_BOMB_TOWER = "advanced-bomb-tower.png";
    private final static String LOCKED_BOMB_TOWER = "bomb-tower-locked.png";

    public final static int NB_OF_FRAMES = 9;

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
                case model.Slot.SIMPLE_TOWER_INDEX : 
                    if (SimpleTower.getCurrentLevel() == 0) url += SIMPLE_TOWER;
                    else url += UPGRADED_SIMPLE_TOWER;
                    break;
                case model.Slot.BOMB_TOWER_INDEX : 
                    if (!isUnlocked) {
                        url += LOCKED_BOMB_TOWER;
                    } else {
                        if (SimpleTower.getCurrentLevel() == 0) url += BOMB_TOWER;
                        else url += UPGRADED_BOMB_TOWER;
                    }
                    break;
                default : break;
            }
        }
        else url += ITEM_FRAMES_REPOSITORY;
        Image slot = new ImageIcon(url).getImage();
        return slot;
    }
}
