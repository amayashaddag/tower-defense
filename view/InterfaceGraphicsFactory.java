package view;

import java.awt.Image;
import javax.swing.ImageIcon;

public class InterfaceGraphicsFactory {

    private final static String GUI_RESOURCES_REPOSITORY = "resources/gui/";

    public final static int INVENTORY_LINES = 2;
    public final static int INVENTORY_COLUMNS = model.Player.INVENTORY_SIZE;

    public static Image[][] loadInventoryBackground() {
        Image[][] inventoryBackground = new Image[INVENTORY_LINES][INVENTORY_COLUMNS];
        String url = GUI_RESOURCES_REPOSITORY + "inventory-slot.png";
        Image inventorySlot = new ImageIcon(url).getImage();
        for (int i = 0; i < INVENTORY_LINES; i++) {
            for (int j = 0; j < INVENTORY_COLUMNS; j++) {
                inventoryBackground[i][j] = inventorySlot;
            }
        }
        return inventoryBackground;
    }

    public static Image loadSelectionFrame() {
        String url = "resources/gui/selection-frame.png";
        Image selectionFrame = new ImageIcon(url).getImage();
        return selectionFrame;
    }
}
