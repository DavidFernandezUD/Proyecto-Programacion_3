package main.entities;

import main.interfaces.Drawable;
import main.items.ITEM_goldenSword;
import main.items.ITEM_ironSword;
import main.items.ITEM_woodenSword;
import main.items.SuperItem;
import main.KeyHandler;
import main.MouseHandler;
import main.Utility;
import main.assets.ASSET_Grave;
import main.assets.ASSET_Sign;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity implements Drawable {

	KeyHandler keyHandler;
	MouseHandler mouseHandler;

	public final int defaultScreenX;
	public final int defaultScreenY;

	// FOR ASSETS (If the player can read the asset)
	public boolean playerReading = false;
	
	// FOR ITEMS (Inventory)
	public ArrayList<SuperItem> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;
	public SuperItem[] weapons = {null, null, null};
	public boolean hasWoodenSword = false;
	public boolean hasIronSword = false;
	public boolean hasGoldenSword = false;
	public boolean hasBloodySword = false;
	boolean hasBow = false;
	boolean hasShield = false;
	int hasApple = 0;
	int hasRedPotion = 0;
	int hasPurplePotion = 0;

	// For camera locking
	public int screenX;
	public int screenY;

	public boolean screenXLocked;
	public boolean screenYLocked;


	// Payer Status
	public int health = 100;
	public int stamina = 5;
	public final int MAX_HEALTH = 100;
	public final int MAX_STAMINA = 5;
	public int I_FRAMES = 60; // invulnerability frames
	public int i_counter = 60;
	public boolean invulnerable = true;

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
		setItems();
	}

	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 25;
		worldY = gamePanel.tileSize * 35;
		speed = 4;
		direction = "down";
		moving = false;
		attacking = false;
		collisionBox = new Rectangle(11, 22, 42, 42);
		// FOR ASSETS
		collisionBoxDefaultX = collisionBox.x;
		collisionBoxDefaultY = collisionBox.y;

	}

	public void setItems() {

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

			// Past tile coordinates are stored to check for tile change
			int pastCol = worldX / tileSize;
			int pastRow = worldY / tileSize;

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

			// CHECK ASSET COLLISIONS
			int assetIndex = gamePanel.collisionChecker.checkAsset(this, true);
			readAsset(assetIndex, this);
			
			// CHECK ITEM COLLISION
			int itemIndex = gamePanel.collisionChecker.checkItem(this, true);
			pickUpItem(itemIndex);
			
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

			// Checking if the tile the players is at has changed
			int currentCol = worldX / tileSize;
			int currentRow = worldY / tileSize;
            gamePanel.entityManager.playerChangedTile = (currentRow != pastRow) || (currentCol != pastCol);

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

		// Invulnerability Frames
		invulnerable = i_counter < I_FRAMES;
		if (invulnerable) {
			i_counter++;
		}
	}

	public void damage(int damage) {
		if (!gamePanel.player.invulnerable) {
			i_counter = 0;
			health -= damage;
		}

		// TODO: Make the player die when getting to 0 health points
		if (health <= 0) {
			health = MAX_HEALTH;
			gamePanel.pauseState = true;
		}
	}
	
	//FOR ASSETS
	public void readAsset(int i, Entity player) {
		if (i != 999) {
			String assetName = gamePanel.assets[i].name;
			switch (assetName) {
			case "Sign":		
				playerReading = gamePanel.collisionChecker.isPlayerAbleToRead(player, gamePanel.assets[i]);
				gamePanel.dialogueScreen.currentDialogue = ((ASSET_Sign) gamePanel.assets[i]).text;
				break;			
			case "Grave":
				playerReading = gamePanel.collisionChecker.isPlayerAbleToRead(player, gamePanel.assets[i]);		
				gamePanel.dialogueScreen.currentDialogue = ((ASSET_Grave) gamePanel.assets[i]).text;
				break;
			}			
		}
	}
	
	// FOR ITEMS
	public void pickUpItem(int i) {

		if (i != 999) {

			String AssetName = gamePanel.items[i].name;

			switch (AssetName) {
			case "Wooden Sword":
				if (!hasBloodySword && !hasGoldenSword && !hasIronSword && !hasWoodenSword) {
					weapons[0] = gamePanel.items[i];
					hasWoodenSword = true;
					gamePanel.items[i] = null;
					System.out.println("You found a Wooden Sword!");
					break;
				}
				break;
			case "Iron Sword":			
				if (!hasBloodySword && !hasGoldenSword && !hasIronSword) {
					if (hasWoodenSword) {
						ITEM_woodenSword ws = (ITEM_woodenSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasIronSword = true;
						hasWoodenSword = false;
						ws.worldX = gamePanel.items[i].worldX;
						ws.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = ws;								
						System.out.println("You found an Iron Sword!");
						break;				
					} else {
						weapons[0] = gamePanel.items[i];
						hasIronSword = true;
						gamePanel.items[i] = null;
						System.out.println("You found an Iron Sword!");
						break;
					}
				}	
				break;

			case "Golden Sword":
				if (!hasBloodySword && !hasGoldenSword) {
					if (hasWoodenSword) {
						ITEM_woodenSword ws = (ITEM_woodenSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasGoldenSword = true;
						hasWoodenSword = false;
						ws.worldX = gamePanel.items[i].worldX;
						ws.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = ws;								
						System.out.println("You found a Golden Sword!");
						break;				
					} else if (hasIronSword) {
						ITEM_ironSword is = (ITEM_ironSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasGoldenSword = true;
						hasIronSword = false;
						is.worldX = gamePanel.items[i].worldX;
						is.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = is;								
						System.out.println("You found a Golden Sword!");
						break;	
					} else {
						weapons[0] = gamePanel.items[i];
						hasGoldenSword = true;
						gamePanel.items[i] = null;
						System.out.println("You found a Golden Sword!");
						break;
					}
				}	
				break;

			case "Bloody Sword":
				if (!hasBloodySword) {
					if (hasWoodenSword) {
						ITEM_woodenSword ws = (ITEM_woodenSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasBloodySword = true;
						hasWoodenSword = false;
						ws.worldX = gamePanel.items[i].worldX;
						ws.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = ws;								
						System.out.println("You found a Bloody Sword!");
						break;				
					} else if (hasIronSword) {
						ITEM_ironSword is = (ITEM_ironSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasBloodySword = true;
						hasIronSword = false;
						is.worldX = gamePanel.items[i].worldX;
						is.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = is;								
						System.out.println("You found a Bloody Sword!");
						break;							
					} else if (hasGoldenSword) {					
						ITEM_goldenSword gs = (ITEM_goldenSword) weapons[0];
						weapons[0] = gamePanel.items[i];
						hasBloodySword = true;
						hasGoldenSword = false;
						gs.worldX = gamePanel.items[i].worldX;
						gs.worldY = gamePanel.items[i].worldY;
						gamePanel.items[i] = gs;								
						System.out.println("You found a Bloody Sword!");
						break;	
					} else {
						weapons[0] = gamePanel.items[i];
						hasBloodySword = true;
						gamePanel.items[i] = null;
						System.out.println("You found a Bloody Sword!");
						break;
					}
				}	
				break;			
			case "Shield":
				if (!hasShield) {
					weapons[1] = gamePanel.items[i];
					hasShield = true;
					gamePanel.items[i] = null;
					System.out.println("You found a shield!");
					break;
				}
				break;
			case "Bow":
				if (!hasBow) {
					weapons[2] = gamePanel.items[i];
					hasBow = true;
					gamePanel.items[i] = null;
					System.out.println("You found a bow!");
					break;
				}
				break;
			case "Apple":

				if (inventory.size() != maxInventorySize) {
					inventory.add(gamePanel.items[i]);
					hasApple++;
					gamePanel.items[i] = null;
					health += 20;
					System.out.println("Apple: " + hasApple);
					break;
				}
				break;

			case "Red Potion":

				if (inventory.size() != maxInventorySize) {
					inventory.add(gamePanel.items[i]);
					hasRedPotion++;
					gamePanel.items[i] = null;
					health += MAX_HEALTH / 2;
					System.out.println("Red potion: " + hasRedPotion);
					break;
				}
				break;

			case "Purple Potion":

				if (inventory.size() != maxInventorySize) {
					inventory.add(gamePanel.items[i]);
					hasPurplePotion++;
					gamePanel.items[i] = null;
					health = MAX_HEALTH;
					System.out.println("Purple potion: " + hasPurplePotion);
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
		redrawProp(g2, this, screenX, screenY);

		// Drawing collision box
		if (debug) {
			g2.setColor(new Color(255, 0, 0, 150));
			g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
		}
	}
}
