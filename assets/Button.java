package assets;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

public class Button extends JButton {

    private final static int BUTTON_WIDTH = 150, BUTTON_HEIGHT = 40;
    private final static int BUTTON_FONT_SIZE = 18;

    public Button(String text) {
        super(text);
        try {
            this.setFont(Fonts.sansSerifBoldFont(BUTTON_FONT_SIZE));
        } catch (Exception e) {
            this.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
        }
        this.setForeground(Colors.BUTTON_FONT_COLOR);
        this.setBackground(Colors.BUTTON_BACKGROUND_COLOR);
        this.setHorizontalAlignment(JButton.CENTER);
        this.setVerticalAlignment(JButton.CENTER);
        this.setBorderPainted(false);
        this.setSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    }

    public Button(String text, int value) {
        
    }
}
