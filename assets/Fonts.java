package assets;

import java.awt.Font;
import java.io.File;

public class Fonts {

    private static final String FONTS_REPOSITORY = "resources/fonts/";
    private static final String SANS_SERIF_REGULAR_FONT = "sans-serif-regular.ttf";
    private static final String SANS_SERIF_BOLD_FONT = "sans-serif-bold.ttf";
    private static final String SANS_SERIF_BOLD_ITALIC_FONT = "sans-serif-bold-italic.ttf";

    public static Font sansSerifRegularFont(int fontSize) {
        String filePath = FONTS_REPOSITORY + SANS_SERIF_REGULAR_FONT;
        File fontFile = new File(filePath);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, fontSize);
        }
        return font;
    }

    public static Font sansSerifBoldFont(int fontSize) {
        String filePath = FONTS_REPOSITORY + SANS_SERIF_BOLD_FONT;
        File fontFile = new File(filePath);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, fontSize);
        }
        return font;
    }

    public static Font sansSerifBoldItalicFont(int fontSize) {
        String filePath = FONTS_REPOSITORY + SANS_SERIF_BOLD_ITALIC_FONT;
        File fontFile = new File(filePath);
        Font font;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        } catch (Exception e) {
            font = new Font("Arial", Font.PLAIN, fontSize);
        }
        return font;
    }
}
