package main;

import main.interfaces.Drawable;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

public class TitleScreen implements Drawable {

    // SETTINGS
    private final String title;
    private Font titleFont;
    private Font optionFont;
    private final Color fontColor;
    private final Color highlightColor;

    // OPTIONS
    public GamePanel gamePanel;

    // INDEX
    private boolean upToggled = false;
    private boolean downToggled = false;
    private int selectionIndex = 0;

    // STATES
    private boolean gameLoad = false;
    private boolean gameTitle = true;

    TitleScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        // TITLE
        title = "Shadows Of Despair";

        // FONTS
        try {
            // Load the font from a file
            Font customTitleFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/fonts/Blackside.ttf"));
            Font customOptionFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/fonts/digitany.ttf"));
            // Create the title and option fonts using the custom font
            titleFont = customTitleFont.deriveFont(Font.BOLD, 100);
            optionFont = customOptionFont.deriveFont(Font.BOLD, 24);

        } catch (FontFormatException | IOException e) {
            // Fallback to default fonts if the custom font could not be loaded
            titleFont = new Font("Arial", Font.BOLD, 36);
            optionFont = new Font("Arial", Font.PLAIN, 24);
        }

        // COLORS
        fontColor = Color.WHITE;
        highlightColor = Color.YELLOW;
    }

    public void update() {

        if (gameTitle) {
            if(selectionIndex == 0 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.titleScreenOn = false;
            }

            if(selectionIndex == 4 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                System.exit(0);
            }

            if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
                upToggled = !upToggled;
                selectionIndex--;
                if(selectionIndex < 0) {
                    selectionIndex = 4;
                }
            }

            if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
                downToggled = !downToggled;
                selectionIndex++;
                if(selectionIndex > 4) {
                    selectionIndex = 0;
                }
            }
        } else if (gameLoad) {
            //TODO: Load game screen
        }

    }

    @Override
    public void draw(Graphics2D g2) {

        if(gameTitle) {
            
            // DRAWING BACKGROUND
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // DRAWING TITLE
            g2.setFont(titleFont);
            g2.setColor(fontColor);
            int titleX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            int titleY = 180;
            g2.drawString(title, titleX, titleY);

            // DRAWING NEW GAME BUTTON
            g2.setFont(optionFont);
            int startX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("NEW GAME")) / 2;
            int startY = 320;
            g2.setColor(selectionIndex == 0 ? highlightColor : fontColor);
            g2.drawString("NEW GAME", startX, startY);

            // DRAWING CONTINUE BUTTON
            g2.setFont(optionFont);
            int continueX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("CONTINUE GAME")) / 2;
            int continueY = 360;
            g2.setColor(selectionIndex == 1 ? highlightColor : fontColor);
            g2.drawString("CONTINUE GAME", continueX, continueY);

            // DRAWING OPTIONS BUTTON
            g2.setFont(optionFont);
            int optionsX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("OPTIONS")) / 2;
            int optionsY = 400;
            g2.setColor(selectionIndex == 2 ? highlightColor : fontColor);
            g2.drawString("OPTIONS", optionsX, optionsY);

            // DRAWING STATISTICS BUTTON
            g2.setFont(optionFont);
            int statisticsX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("STATISTICS")) / 2;
            int statisticsY = 440;
            g2.setColor(selectionIndex == 3 ? highlightColor : fontColor);
            g2.drawString("STATISTICS", statisticsX, statisticsY);

            // DRAWING EXIT BUTTON
            g2.setFont(optionFont);
            int exitX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("EXIT")) / 2;
            int exitY = 480;
            g2.setColor(selectionIndex == 4 ? highlightColor : fontColor);
            g2.drawString("EXIT", exitX, exitY);
        }
        else if (gameLoad) {
            // TODO: Draw loading screen
        }
    }
}
