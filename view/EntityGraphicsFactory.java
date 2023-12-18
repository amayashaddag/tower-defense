package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.*;

public class EntityGraphicsFactory {

    private static final String ENTITIES_RESOURCES_REPOSITORY = "resources/entity/";

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

    //TODO: A implémenter
    public static Image loadMobImage(Mob m) {
        String url = ENTITIES_RESOURCES_REPOSITORY + "mob-try.png";
        Image mobImage = new ImageIcon(url).getImage();
        return mobImage;
    }
}
