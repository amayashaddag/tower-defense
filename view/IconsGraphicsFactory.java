package view;

import javax.swing.ImageIcon;
import java.awt.Image;

public class IconsGraphicsFactory {

    private static final String ICONS_REPOSITORY = "resources/icons/";
    private static final String COIN_IMAGE = "coin.png";
    private static final String HEART_IMAGE = "heart.png";

    public static final int COIN_ICON_WIDTH = 32, COIN_ICON_HEIGHT = 32;
    public static final int HEART_ICON_WIDTH = 32, HEART_ICON_HEIGHT = 32;

    public static ImageIcon loadCoinIcon() {
        String url = ICONS_REPOSITORY + COIN_IMAGE;
        ImageIcon coinIcon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(COIN_ICON_WIDTH, COIN_ICON_HEIGHT, Image.SCALE_SMOOTH));
        return coinIcon;
    }

    public static ImageIcon loadHeartIcon() {
        String url = ICONS_REPOSITORY + HEART_IMAGE;
        ImageIcon coinIcon = new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(HEART_ICON_WIDTH, HEART_ICON_HEIGHT, Image.SCALE_SMOOTH));
        return coinIcon;
    }
}
