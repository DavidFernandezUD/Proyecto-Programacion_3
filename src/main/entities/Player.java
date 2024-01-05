package main.entities;

import main.interfaces.Drawable;
//import main.objects.OBJ_Grave;
//import main.objects.OBJ_Sign;
import main.KeyHandler;
import main.MouseHandler;
import main.Utility;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import java.util.Objects;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity implements Drawable {

	KeyHandler keyHandler;
	MouseHandler mouseHandler;

	public final int defaultScreenX;
	public final int defaultScreenY;

	// For camera locking
	public int screenX;
	public int screenY;

	public boolean screenXLocked;
	public boolean screenYLocked;

	// For objects
	public boolean playerReading = false;

	// For items
	boolean hasWoodenSword = false;
	boolean hasIronSword = false;
	boolean hasGoldenSword = false;
	boolean hasBloodySword = false;

	// Payer Status
	public int health = 100;
	public int stamina = 5;
	public int maxHealth = 100;
	public int maxStamina = 5;

	// Just for debugging purposes (Displays Collision Box)
	private boolean debug = false;

	public Player(GamePanel gamePanel, KeyHandler keyHandler, MouseHandler mouseHandler) {
		super(gamePanel);

		this.keyHandler = keyHandler;
		this.mouseHandler = mouseHandler;

		defaultScreenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
		defaultScreenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

		setDefaultValues();
		getPlayerSprite();
	}

	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 25;
		worldY = gamePanel.tileSize * 35;
		speed = 4;
		direction = "down";
		moving = false;
		attacking = false;
		collisionBox = new Rectangle(11, 22, 42, 42);

		// For objects
		collisionBoxDefaultX = collisionBox.x;
		collisionBoxDefaultY = collisionBox.y;

	}

	public void getPlayerSprite() {

		// For image scaling and optimization
		Utility util = new Utility();

		try {
			runSprites = ImageIO
					.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/player/run.png")));
			runSprites = util.scaleImage(runSprites, tileSize * 4, tileSize * 4);

			idleSprites = ImageIO
					.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/player/idle.png")));
			idleSprites = util.scaleImage(idleSprites, tileSize * 4, tileSize * 4);

			attackSprites = ImageIO
					.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/player/attack1.png")));
			attackSprites = util.scaleImage(attackSprites, tileSize * 4, tileSize * 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// TODO: Rethink this method
	// FIXME: Fix direction bug when attacking
	public void update() {

		// MOVING
		if (keyHandler.isMoveKeyPressed()) {

			// If player has just started moving the spriteNum and counter is restarted
			if (!moving) {
				moving = true;
				spriteNum = 1;
				spriteCounter = 13;
			}

			speed = (attacking ? 2 : 4); // Moving speed is reduced when attacking

			if (keyHandler.isLastMoveKeyPressed(KeyEvent.VK_W)) {
				direction = "up";
			} else if (keyHandler.isLastMoveKeyPressed(KeyEvent.VK_S)) {
				direction = "down";
			} else if (keyHandler.isLastMoveKeyPressed(KeyEvent.VK_A)) {
				direction = "left";
			} else {
				direction = "right";
			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gamePanel.collisionChecker.checkTileCollision(this);

			// CHECK ITEM COLLISION
			int itemIndex = gamePanel.collisionChecker.checkItem(this, true);
			pickUpItem(itemIndex);

			// Check object collisions
//			int objIndex = gamePanel.collisionChecker.checkObject(this, true);
//			readObject(objIndex, this);

			// If collision is false the player can move
			if (!collisionOn) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
		} else {
			moving = false;
		}

		// ATTACKING
		// FIXME: Fix attacking
		attacking = mouseHandler.isAttackPressed();

		spriteCounter += (attacking ? 2 : 1); // Attack animation runs faster than moving and idle

		// Animation
		if (spriteCounter > ANIMATION_FRAMES) {
			if (spriteNum < 4) {
				spriteNum++;
			} else {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}

//	public void readObject(int i, Entity player) {
//
//		if (i != 999) {
//			String objectName = gamePanel.obj[i].name;
//
//			switch (objectName) {
//			case "Sign":
//				playerReading = gamePanel.collisionChecker.isPlayerAbleToRead(player, gamePanel.obj[i]);
//				gamePanel.dialogueScreen.currentDialogue = ((OBJ_Sign) gamePanel.obj[i]).text;
//				break;
//				
//			case "Grave":
//				playerReading = gamePanel.collisionChecker.isPlayerAbleToRead(player, gamePanel.obj[i]);
//				gamePanel.dialogueScreen.currentDialogue = ((OBJ_Grave) gamePanel.obj[i]).text;
//				break;
//				
//			case "Chest":			
//				// TODO: implement inventory
//			}
//			
//			
//		}
//	}

	public void pickUpItem(int i) {

		if (i != 999) {
			String objectName = gamePanel.items[i].name;

			switch (objectName) {
			// SWORDS
			// TODO: drop swords
			case "Wooden Sword":

				if (!hasBloodySword && !hasGoldenSword && !hasIronSword && !hasWoodenSword) {
					hasWoodenSword = true;
					gamePanel.items[i] = null;
					System.out.println("Wooden Sword: " + hasWoodenSword);
					break;
				}
				break;
			case "Iron Sword":

				if (!hasBloodySword && !hasGoldenSword && !hasIronSword) {
					hasIronSword = true;
					gamePanel.items[i] = null;
					hasWoodenSword = false;
					System.out.println("Iron Sword: " + hasIronSword);
					break;
				}
				break;

			case "Golden Sword":
				if (!hasBloodySword && !hasGoldenSword ) {
					hasGoldenSword = true;
					gamePanel.items[i] = null;
					hasWoodenSword = false;
					hasIronSword = false;
					break;
				}
				break;

			case "Bloody Sword":
				if (!hasBloodySword) {
					hasBloodySword = true;
					gamePanel.items[i] = null;
					hasWoodenSword = false;
					hasIronSword = false;
					hasGoldenSword = false;
					break;
				}
				break;
			}
		}
	}

	@Override
	public void draw(Graphics2D g2) {

		BufferedImage image = getSprite(direction);

		// Camera System
		screenX = defaultScreenX;
		screenY = defaultScreenY;

		if (!screenXLocked) {
			if (worldX < gamePanel.screenWidth) {
				screenX = worldX;
			} else {
				screenX = worldX - gamePanel.worldWidth + gamePanel.screenWidth;
			}
		}
		if (!screenYLocked) {
			if (worldY < gamePanel.screenHeight) {
				screenY = worldY;
			} else {
				screenY = worldY - gamePanel.worldHeight + gamePanel.screenHeight;
			}
		}

		// Drawing Player
		g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

		// Redrawing props if player is behind them
		redrawProp(g2);

		// Drawing collision box
		if (debug) {
			g2.setColor(new Color(255, 0, 0, 150));
			g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
		}
	}

	private void redrawProp(Graphics2D g2) {

		// Checking if the left and right tiles under the player are prop tiles
		// The -1 is to avoid the lower main.tile to change to the next lower one when
		// the player is just on the top edge of the main.tile
		int propLeft = gamePanel.tileManager.map.get(3)[(worldY + tileSize - 1) / tileSize][worldX / tileSize];
		int propRight = gamePanel.tileManager.map.get(3)[(worldY + tileSize - 1) / tileSize][(worldX + tileSize)
				/ tileSize];

		// Calculating offsets with respect to the player to redraw the tiles at that
		// position
		int offsetX = worldX % tileSize;
		int offsetY = (worldY - 1) % tileSize + 1;

		// Special cases
		int COLUMN = 619;
		int COLUMN_TOP = 595;
		int STONE_THRESHOLD = 824; // All the stone props are above this value (we don't want to repaint them)

		// Redrawing lower left main.tile (if necessary)
		if (propLeft != -1 && propLeft < STONE_THRESHOLD) { //
			g2.drawImage(gamePanel.tileManager.tiles[propLeft].image, screenX - offsetX, screenY + tileSize - offsetY,
					gamePanel.tileSize, gamePanel.tileSize, null);

			// If propLeft is a "column" the top part is automatically drawn on top too
			if (propLeft == COLUMN) {
				g2.drawImage(gamePanel.tileManager.tiles[COLUMN_TOP].image, screenX - offsetX, screenY - offsetY,
						gamePanel.tileSize, gamePanel.tileSize, null);
			}
		}

		// Redrawing lower right main.tile (if necessary)
		if (propRight != -1 && propRight < STONE_THRESHOLD) {
			g2.drawImage(gamePanel.tileManager.tiles[propRight].image, screenX + tileSize - offsetX,
					screenY + tileSize - offsetY, gamePanel.tileSize, gamePanel.tileSize, null);

			// If propRight is a "column" the top part is automatically drawn on top too
			if (propRight == COLUMN) {
				g2.drawImage(gamePanel.tileManager.tiles[COLUMN_TOP].image, screenX + tileSize - offsetX,
						screenY - offsetY, gamePanel.tileSize, gamePanel.tileSize, null);
			}
		}
	}
}
