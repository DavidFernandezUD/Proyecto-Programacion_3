package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import main.assets.SuperAsset;

/** Drawable dialog screen GUI component.
 * @author marcos.martinez@opendeusto.es*/
public class DialogueScreen implements Drawable {

	// SETTINGS
	private Font optionFont;
	private final Color fontColor;
	private final Color highlightColor;

	public String currentDialogue = "";

	// OPTIONS
	public GamePanel gamePanel;

	/** Constructor for Dialog screen.*/
	DialogueScreen(GamePanel gamePanel) {
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

	/** Updates the state of the game via the gamePanel.*/
	public void update() {
		// Inventory cannot be opened while dialogue screen
		if (gamePanel.dialogueState) {
			gamePanel.inventoryState = false;
			if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_I)) {
				gamePanel.keyHandler.keyToggleStates.put(KeyEvent.VK_I, false);
			}
		}

		if (gamePanel.player.playerReading && !gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER)) {
			for (SuperAsset sa : gamePanel.assets) {
				if (gamePanel.collisionChecker.isPlayerAbleToRead(gamePanel.player, sa)) {
					gamePanel.dialogueState = false;
					return;
				}
			}
			gamePanel.dialogueState = false;
			gamePanel.player.playerReading = false;
		}
	}

	/** Draws a dialog screen.
	 * @param g2 Graphics2D object to draw the dialog to.*/
	@Override
	public void draw(Graphics2D g2) {
		int x = gamePanel.tileSize * 2;
		int y = gamePanel.tileSize / 2;
		int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
		int height = gamePanel.tileSize * 5;
		drawSubWindow(x, y, width, height, g2);
		g2.setFont(optionFont);
		x += gamePanel.tileSize;
		y += gamePanel.tileSize;
		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}

	/** Helper method that draws the main frame of a dialog window.
	 * @param x      The x coordinate of dialog window.
	 * @param y 	 The y coordinate of dialog window.
	 * @param width  Width of the dialog window.
	 * @param height Height of the dialog window.
	 * @param g2	 Graphics2D where the dialog window will be drawn into.*/
	private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c = fontColor;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
	}
}
