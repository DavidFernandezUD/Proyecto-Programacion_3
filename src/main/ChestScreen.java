package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import main.assets.SuperAsset;

/** Drawable chest inventory GUI component.
 * @author marcos.martinez@opendeusto.es*/
public class ChestScreen implements Drawable {

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

	/** Constructor for ChestScreen.*/
	ChestScreen(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		// FONTS
		optionFont = FontManager.optionFont;

		// COLORS
		fontColor = Color.WHITE;
		highlightColor = Color.YELLOW;
	}

	/** Updates the inventory in response to user input,
	 * and triggers sounds when necessary.*/
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
		if (gamePanel.chestState) {
			gamePanel.inventoryState = false;
			if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_I)) {
				gamePanel.keyHandler.keyToggleStates.put(KeyEvent.VK_I, false);
			}
		}	
		// TODO: Add close button on chest
		if (gamePanel.player.playerReading && !gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER)) {
			for (SuperAsset sa : gamePanel.assets) {
				if (gamePanel.collisionChecker.isPlayerAbleToRead(gamePanel.player, sa)) {
					gamePanel.chestState = false;
					return;
				}
			}
			gamePanel.chestState = false;
			gamePanel.player.playerReading = false;
		}
	}

	/** Draws a graphical representation of the chests inventory.
	 * @param g2 Graphics2D object to draw the inventory to.*/
	@Override
	public void draw(Graphics2D g2) {
		// FRAME
		int frameX = gamePanel.tileSize * 3;
		int frameY = gamePanel.tileSize * 3;
		int frameWidth = gamePanel.tileSize * 6;
		int frameHeight = gamePanel.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);
		drawInventory(g2);

		// SLOT
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		
		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
			g2.drawImage(gamePanel.player.inventory.get(i).image, slotX+8, slotY+8, null);
			
			slotX += gamePanel.tileSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += gamePanel.tileSize;
			}
		}

		// CURSOR
		int cursorX = slotXStart + (gamePanel.tileSize * slotCol);
		int cursorY = slotYStart + (gamePanel.tileSize * slotRow);
		int cursorWidth = gamePanel.tileSize;
		int cursorHeight = gamePanel.tileSize;

		// DRAW CURSOR
		g2.setColor(fontColor);
		g2.setStroke(new BasicStroke(3));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// DESCRIPTION FRAME
		int dFrameX = frameX;
		int dFrameY = frameY + frameHeight;
		int dFrameWidth = frameWidth;
		int dFrameHeight = gamePanel.tileSize*3;
		
		// DRAW DESCRIPTION TEXT
		int textX = dFrameX + 20;
		int textY = dFrameY + gamePanel.tileSize;
		g2.setFont(optionFont);
		
		int itemIndex = getItemIndexOnSlot();
		
		if (itemIndex < gamePanel.player.inventory.size()) {
			
			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, g2);
			
			for (String line: gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
				g2.drawString(line, textX, textY);
				textY += 32;
			}			
		}
	}

	/** Helper method that flattens row col indexes
	 * into a single index.
	 * @return Flattened index.*/
	private int getItemIndexOnSlot() {
		int itemIndex = slotCol + (slotRow*5);
		return itemIndex;
	}

	/** Helper method that draws the main frame of the inventory,
	 * where the item slots will then be drawn on top of.
	 * @param x      The x coordinate of the inventory.
	 * @param y 	 The y coordinate of the inventory.
	 * @param width  Width of the inventory frame.
	 * @param height Height of the inventory frame.
	 * @param g2	 Graphics2D where the frame will be drawn into.*/
	private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = fontColor;
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}
	
	/** Draws the inventory window in a given Graphics2D object.
	 * @param g2 Graphisc2D object where the inventory window will be drawn into.*/
	public void drawInventory(Graphics2D g2) {

		// FRAME
		int frameX = gamePanel.tileSize * 9;
		int frameY = gamePanel.tileSize * 3;
		int frameWidth = gamePanel.tileSize * 6;
		int frameHeight = gamePanel.tileSize * 5;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight, g2);

		// SLOT
		final int slotXStart = frameX + 20;
		final int slotYStart = frameY + 20;
		int slotX = slotXStart;
		int slotY = slotYStart;
		
		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
			g2.drawImage(gamePanel.player.inventory.get(i).image, slotX+8, slotY+8, null);
			
			slotX += gamePanel.tileSize;
			
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotXStart;
				slotY += gamePanel.tileSize;
			}
		}

		// CURSOR
//		int cursorX = slotXStart + (gamePanel.tileSize * slotCol);
//		int cursorY = slotYStart + (gamePanel.tileSize * slotRow);
//		int cursorWidth = gamePanel.tileSize;
//		int cursorHeight = gamePanel.tileSize;

		// DRAW CURSOR
//		g2.setColor(fontColor);
//		g2.setStroke(new BasicStroke(3));
//		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
		
		// WEAPONS FRAME
		int wFrameX = gamePanel.tileSize * 9;
		int wFrameY = gamePanel.tileSize;
		int wFrameWidth = gamePanel.tileSize * 4;
		int wFrameHeight = gamePanel.tileSize * 2;
		drawSubWindow(wFrameX, wFrameY, wFrameWidth, wFrameHeight, g2);

		// WEAPONS SLOT
		final int wSlotXStart = wFrameX + 20;
		final int wSlotYStart = wFrameY + 20;
		int wSlotX = wSlotXStart;
		int wSlotY = wSlotYStart;
		
		// DRAW PLAYER'S WEAPONS
		if (gamePanel.player.weapons[0] != null) {
			g2.drawImage(gamePanel.player.weapons[0].image, wSlotX+8, wSlotY+8, null);
		}
		if (gamePanel.player.weapons[1] != null) {
			g2.drawImage(gamePanel.player.weapons[1].image, wSlotX+8 + gamePanel.tileSize, wSlotY+8, null);
		}
		if (gamePanel.player.weapons[2] != null) {
			g2.drawImage(gamePanel.player.weapons[2].image, wSlotX+8 + 2*gamePanel.tileSize, wSlotY+8, null);
		}

		// DESCRIPTION FRAME
//		int dFrameX = frameX;
//		int dFrameY = frameY + frameHeight;
//		int dFrameWidth = frameWidth;
//		int dFrameHeight = gamePanel.tileSize*3;
		
		// DRAW DESCRIPTION TEXT
//		int textX = dFrameX + 20;
//		int textY = dFrameY + gamePanel.tileSize;
//		g2.setFont(optionFont);
//		
//		int itemIndex = getItemIndexOnSlot();
//		
//		if (itemIndex < gamePanel.player.inventory.size()) {
//			
//			drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight, g2);
//			
//			for (String line: gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
//				g2.drawString(line, textX, textY);
//				textY += 32;
//			}			
//		}
	}
}
