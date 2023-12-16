package view;

import java.awt.Image;
import javax.swing.ImageIcon;

import model.*;

public class EntityGraphicsFactory {
    //TODO: A implémenter
    public static Image laodTowerInventoryIcon(Tower t) {
        String url = "resources/entity/slot.png";
        Image selectionFrame = new ImageIcon(url).getImage();
        return selectionFrame;
    }

    //TODO: A implémenter
    public static Image loadItemInventoryIcon(Item i) {
        String url = "resources/entity/slot.png";
        Image selectionFrame = new ImageIcon(url).getImage();
        return selectionFrame;
    }
}
