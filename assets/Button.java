package assets;

import java.awt.Dimension;
import javax.swing.JButton;

import menu.MenuView;

public class Button extends JButton {

    private final static int SHOP_BUTTON_WIDTH = 128, SHOP_BUTTON_HEIGHT = 64;
    private final static int BUTTON_FONT_SIZE = 18;

    public final static int SMALL_MENU_BUTTON_WIDTH = 128, SMALL_MENU_BUTTON_HEIGHT = 64;
    public final static int LARGE_MENU_BUTTON_WIDTH = MenuView.WINDOW_WIDTH * 60 / 100, LARGE_MENU_BUTTON_HEIGHT = 64;

    public final static String EASY_BUTTON_LABEL = "EASY";
    public final static String MEDIUM_BUTTON_LABEL = "MEDIUM";
    public final static String HARD_BUTTON_LABEL = "HARD";

    public final static String MARATHON_BUTTON_LABEL = "MARATHON";
    public final static String SHOP_BUTTON_LABEL = "SHOP";
    public final static String EXIT_BUTTON_LABEL = "EXIT";

    public final static String UNLOCK_BUTTON_LABEL = "UNLOCK";
    public final static String UPGRADE_BUTTON_LABEL = "UPGRADE";

    public final static String GO_TO_MENU_BUTTON_LABEL = "GO TO MENU";

    public Button(String text) {
        super(text);
    }

    public static Button shopButton(String text) {
        Button shopButton = new Button(text);
        shopButton.setFont(Fonts.sansSerifBoldFont(BUTTON_FONT_SIZE));
        shopButton.setForeground(Colors.BUTTON_FONT_COLOR);
        shopButton.setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        shopButton.setHorizontalAlignment(JButton.CENTER);
        shopButton.setVerticalAlignment(JButton.CENTER);
        shopButton.setBorderPainted(false);
        shopButton.setPreferredSize(new Dimension(SHOP_BUTTON_WIDTH, SHOP_BUTTON_HEIGHT));
        return shopButton;
    }

    public static Button smallMenuButton(String text) {
        Button shopButton = new Button(text);
        shopButton.setFont(Fonts.sansSerifBoldFont(BUTTON_FONT_SIZE));
        shopButton.setForeground(Colors.BUTTON_FONT_COLOR);
        shopButton.setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        shopButton.setHorizontalAlignment(JButton.CENTER);
        shopButton.setVerticalAlignment(JButton.CENTER);
        shopButton.setBorderPainted(false);
        shopButton.setPreferredSize(new Dimension(SMALL_MENU_BUTTON_WIDTH, SMALL_MENU_BUTTON_HEIGHT));
        return shopButton;
    }

    public static Button largeMenuButton(String text) {
        Button shopButton = new Button(text);
        shopButton.setFont(Fonts.sansSerifBoldFont(BUTTON_FONT_SIZE));
        shopButton.setForeground(Colors.BUTTON_FONT_COLOR);
        shopButton.setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        shopButton.setHorizontalAlignment(JButton.CENTER);
        shopButton.setVerticalAlignment(JButton.CENTER);
        shopButton.setBorderPainted(false);
        shopButton.setPreferredSize(new Dimension(LARGE_MENU_BUTTON_WIDTH, LARGE_MENU_BUTTON_HEIGHT));
        return shopButton;
    }
}
