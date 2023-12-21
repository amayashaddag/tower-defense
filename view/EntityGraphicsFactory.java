package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.*;

public class EntityGraphicsFactory {

    private static final String ENTITIES_RESOURCES_REPOSITORY = "resources/entity/";
    private final static String SKELTON_FRAMES_REPOSITORY = "skelton-frames/";
    private final static String SOLDIER_FRAMES_REPOSITORY = "soldier-frames/";
    private final static String KNIGHT_FRAMES_REPOSITORY = "knight-frames/";
    public final static int NB_OF_FRAMES = 9;

    //TODO: A implémenter
    public static Image laodTowerInventoryIcon(Tower t) {
        String url = ENTITIES_RESOURCES_REPOSITORY + "slot.png";
        Image towerImage = new ImageIcon(url).getImage();
        return towerImage;
    }

    //TODO: A implémenter
    public static Image loadItemInventoryIcon(Item i) {
        String url = ENTITIES_RESOURCES_REPOSITORY + "slot.png";
        Image itemImage = new ImageIcon(url).getImage();
        return itemImage;
    }

    // FIXME : Factoriser le code des 4 fonctions des frames
    public static Image[] loadNorthFrames(Mob mob) {
        String url = ENTITIES_RESOURCES_REPOSITORY;
        Image[] frames = new Image[NB_OF_FRAMES];

        if (mob.getLevel() == 0) {
            url += SKELTON_FRAMES_REPOSITORY;
        }else if (mob.getLevel()==1){
            url+=SOLDIER_FRAMES_REPOSITORY;
        }else{
            url+=KNIGHT_FRAMES_REPOSITORY;

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
        }
        else if (mob.getLevel()==1){
            url+=SOLDIER_FRAMES_REPOSITORY;
        }else{
            url+=KNIGHT_FRAMES_REPOSITORY;

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
        }else if (mob.getLevel()==1){
            url+=SOLDIER_FRAMES_REPOSITORY;
        }else{
            url+=KNIGHT_FRAMES_REPOSITORY;

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
        }else if (mob.getLevel()==1){
            url+=SOLDIER_FRAMES_REPOSITORY;
        }else{
            url+=KNIGHT_FRAMES_REPOSITORY;

        }
        for (int i = 0; i < NB_OF_FRAMES; i++) {
            Image image = new ImageIcon(url + "south-" + (i + 1) + ".png").getImage();
            frames[i] = image;
        }
        return frames;
    }
}
