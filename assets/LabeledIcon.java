package assets;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class LabeledIcon extends JLabel {

    private final static int TEXT_FONT_SIZE = 18;

    public LabeledIcon(String content, ImageIcon icon, Color fontColor) {
        super(content, icon, JLabel.CENTER);
        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setFont(Fonts.sansSerifBoldFont(TEXT_FONT_SIZE));
        this.setHorizontalTextPosition(JLabel.RIGHT);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        this.setForeground(fontColor);
    }
}
