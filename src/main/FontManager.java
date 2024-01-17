package main;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/** Helper class to manage the different fonts used in the game.
 * @author juanjose.restrepo@opendeusto.es*/
public class FontManager {
    
    public static Font titleFont;
    public static Font optionFont;
    public static Color fontColor;
    public static Color highlightColor;

    /** Constructor for FontManager. Initializer the default
     * fonts used all around the project.*/
    public FontManager() {
        // DEFAULT FONTS
        try {
            // Load the font from a file
            Font customTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/fonts/Blackside.ttf"));
            Font customOptionFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/fonts/digitany.ttf"));
            // Create the title and option fonts using the custom font
            titleFont = customTitleFont.deriveFont(Font.BOLD, 100);
            optionFont = customOptionFont.deriveFont(Font.BOLD, 24);

        } catch (FontFormatException | IOException e) {
            // Fallback to default fonts if the custom font could not be loaded
            titleFont = new Font("Arial", Font.BOLD, 100);
            optionFont = new Font("Arial", Font.PLAIN, 24);
        }

        // COLORS
        fontColor = Color.WHITE;
        highlightColor = Color.RED;
    }
}
