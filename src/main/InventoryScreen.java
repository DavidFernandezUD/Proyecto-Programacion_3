package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import main.interfaces.Drawable;

public class InventoryScreen implements Drawable {

	private boolean upToggled = false;
	private boolean downToggled = false;
	private boolean leftToggled = false;
	private boolean rightToggled = false;

	// SETTINGS
	private Font optionFont;
	private final Color fontColor;
	private final Color highlightColor;

	public int slotCol = 0;
	public int slotRow = 0;

	// OPTIONS
	public GamePanel gamePanel;

	InventoryScreen(GamePanel gamePanel) {
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

		if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
			upToggled = !upToggled;

			if (slotRow != 0) {
				slotRow--;
				gamePanel.playSound(1);
			}

		}
		if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_A) != leftToggled) {
			leftToggled = !leftToggled;

			if (slotCol != 0) {
				slotCol--;
				gamePanel.playSound(1);
			}

		}
		if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
			downToggled = !downToggled;

			if (slotRow != 3) {
				slotRow++;
				gamePanel.playSound(1);
			}

		}
		if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_D) != rightToggled) {
			rightToggled = !rightToggled;

			if (slotCol != 4) {
				slotCol++;
				gamePanel.playSound(1);
			}

		}

		if (gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
			gamePanel.inventoryState = false;
		}
	}

	@Override
	public void draw(Graphics2D g2) {

		// FRAME
		int frameX = gamePanel.tileSize * 9;
		int frameY = gamePanel.tileSize;
		int frameWidth = gamePanel.tileSize * 6;
		int frameHeight = gamePanel.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

		// SLOT
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;

		// CURSOR
		int cursorX = slotXStart + (gamePanel.tileSize * slotCol);
		int cursorY = slotYStart + (gamePanel.tileSize * slotRow);
		int cursorWidth = gamePanel.tileSize;
		int cursorHeight = gamePanel.tileSize;

		// DRAW CURSOR
		g2.setColor(fontColor);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

	}

	public void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = fontColor;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

}
