package assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class Fonts {

    private static final String FONTS_REPOSITORY = "resources/fonts/";
    private static final String SANS_SERIF_REGULAR_FONT = "sans-serif-regular.ttf";
    private static final String SANS_SERIF_BOLD_FONT = "sans-serif-bold.ttf";

    public static Font sansSerifRegularFont(int fontSize) throws IOException, FontFormatException{
        String filePath = FONTS_REPOSITORY + SANS_SERIF_REGULAR_FONT;
        File fontFile = new File(filePath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        return font;
    }

    public static Font sansSerifBoldFont(int fontSize) throws IOException, FontFormatException{
        String filePath = FONTS_REPOSITORY + SANS_SERIF_BOLD_FONT;
        File fontFile = new File(filePath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, fontSize);
        return font;
    }
}
