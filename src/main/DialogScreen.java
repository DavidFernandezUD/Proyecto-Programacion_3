package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import main.interfaces.Drawable;

public class DialogScreen implements Drawable {

	 
    // SETTINGS
    private Font optionFont;
    private final Color fontColor;
    private final Color highlightColor;

    // OPTIONS
    public GamePanel gamePanel;

    // INDEX
    private boolean upToggled = false;
    private boolean downToggled = false;

    DialogScreen(GamePanel gamePanel) {
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
            highlightColor = Color.YELLOW;
    }
    
    public void update() {

        if(gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            gamePanel.dialogState = false;
        }

    }
	
	
	@Override
	public void draw(Graphics2D g2) {
		
		int x = gamePanel.tileSize * 2;
		int y = gamePanel.tileSize / 2;
		int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
		int height = gamePanel.tileSize * 5;
//		drawSubWindow(x, y, width, height);
		
		g2.setColor(Color.black);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
	}
	
	public void drawSubWindow(int x, int y, int width, int height) {
	
	}

}
