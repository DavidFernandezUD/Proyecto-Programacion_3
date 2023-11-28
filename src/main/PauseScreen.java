package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import main.interfaces.Drawable;

public class PauseScreen implements Drawable{
    
    // SETTINGS
    private Font optionFont;
    private final Color fontColor;
    private final Color highlightColor;

    // OPTIONS
    public GamePanel gamePanel;

    // INDEX
    private boolean upToggled = false;
    private boolean downToggled = false;
    private int selectionIndex = 0;

    PauseScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        // FONTS
        try {
            // Load the font from a file
            Font customOptionFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/res/fonts/digitany.ttf"));
            // Create the title and option fonts using the custom font
            optionFont = customOptionFont.deriveFont(Font.BOLD, 24);

        } catch (FontFormatException | IOException e) {
            // Fallback to default fonts if the custom font could not be loaded
            optionFont = new Font("Arial", Font.PLAIN, 24);
        }

        // COLORS
        fontColor = Color.WHITE;
        highlightColor = Color.RED;
    }

    public void update() {

        // CONTINUE
        if(selectionIndex == 0 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            gamePanel.gamePaused = false;
        }

        // SAVE GAME
        if(selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            // gamePanel.gameManager.saveGame();
        }

        // BACK TO TITLE
        if(selectionIndex == 2 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gamePanel.gamePaused = false;
            gamePanel.titleScreenOn = true;
        }

        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
            upToggled = !upToggled;
            selectionIndex--;
            if(selectionIndex < 0) {
                selectionIndex = 2;
            }
        }

        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
            downToggled = !downToggled;
            selectionIndex++;
            if(selectionIndex > 2) {
                selectionIndex = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        
        // DRAWING BACKGROUND
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.maxScreenCol * gamePanel.tileSize, gamePanel.maxScreenRow * gamePanel.tileSize);

        // DRAWING CONTINUE BUTTON
        g2.setFont(optionFont);
        int startX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("NEW GAME")) / 2;
        int startY = 320;
        g2.setColor(selectionIndex == 0 ? highlightColor : fontColor);
        g2.drawString("CONTINUE", startX, startY);

        // DRAWING SAVE GAME BUTTON
        g2.setFont(optionFont);
        int saveX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("SAVE GAME")) / 2;
        int saveY = 360;
        g2.setColor(selectionIndex == 1 ? highlightColor : fontColor);
        g2.drawString("SAVE GAME", saveX, saveY);

        // DRAWING BACK TO TITLE BUTTON
        g2.setFont(optionFont);
        int continueX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("CONTINUE GAME")) / 2;
        int continueY = 400;
        g2.setColor(selectionIndex == 2 ? highlightColor : fontColor);
        g2.drawString("BACK TO TITLE", continueX, continueY);
    }
}
