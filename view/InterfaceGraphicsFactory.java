package view;

import java.awt.Image;
import javax.swing.ImageIcon;

public class InterfaceGraphicsFactory {
    public static Image loadSelectionFrame() {
        String url = "resources/gui/selection-frame.png";
        Image selectionFrame = new ImageIcon(url).getImage();
        return selectionFrame;
    }
}
