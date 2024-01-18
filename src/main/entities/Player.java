package main.entities;

import main.*;
import main.items.ITEM_apple;
import main.items.ITEM_goldenSword;
import main.items.ITEM_ironSword;
import main.items.ITEM_woodenSword;
import main.items.SuperItem;
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
import java.util.logging.Level;

import javax.imageio.ImageIO;

/** Main player class.
 * @author david.f@opendeusto.es*/
public class Player extends Entity implements Drawable {


	private static Player instance;
	private final KeyHandler keyHandler;

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
	public boolean hasBow = false;
	public boolean hasShield = false;
	int hasApple = 0;
	int hasRedPotion = 0;
	int hasPurplePotion = 0;

	// For camera locking
	public int screenX;
	public int screenY;

	public boolean screenXLocked;
	public boolean screenYLocked;

	// Player Attack
	private final Rectangle hitBoxUp = new Rectangle(0, -10, 64, 74);
	private final Rectangle hitBoxDown = new Rectangle(0, 22, 64, 74);
	private final Rectangle hitBoxLeft = new Rectangle(-21, 11, 74, 64);
	private final Rectangle hitBoxRight = new Rectangle(11, 11, 74, 64);
	private Rectangle hitBox;


	// Payer Status
	public int health = 100;
	public int stamina = 5;
	public final int MAX_HEALTH = 100;
	public final int MAX_STAMINA = 5;
	public final int I_FRAMES = 60; // invulnerability frames
	public int i_counter = 60;
	public boolean invulnerable = true;

	// Just for debugging purposes (Displays Collision Box)
	private boolean debugCollision = false;
	private	boolean debugHit = false;

	/** Creates a new player object with a keyHandler to manage user input.*/
	private Player(GamePanel gamePanel, KeyHandler keyHandler) {
		super(gamePanel);

		this.keyHandler = keyHandler;

		defaultScreenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
		defaultScreenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

		setDefaultValues();
		getPlayerSprite();
		setItems();

		GamePanel.logger.log(Level.INFO, "Player Created at");

	}

	/** Returns a now Player object if a previous one didn't already
	 * exist, other-ways returns the previous instance.*/
	public static Player getInstance(GamePanel gamePanel, KeyHandler keyHandler) {
		if(instance == null) {
			instance = new Player(gamePanel, keyHandler);
		}
		return instance;
	}

	/** Initializes the player state and position with
	 * default values.*/
	public void setDefaultValues() {
		worldX = gamePanel.tileSize * 90;
		worldY = gamePanel.tileSize * 85;
		speed = 4;
		direction = "down";
		moving = false;
		attacking = false;
		collisionBox = new Rectangle(11, 22, 42, 42);
		hitBox = hitBoxRight;

		// FOR ASSETS
		collisionBoxDefaultX = collisionBox.x;
		collisionBoxDefaultY = collisionBox.y;

	}

	/** Sets the main.items of the player.
	 * @author marcos.martinez@opendeusto.es*/
	public void setItems() {

	}

	/** Loads spriteSheets of the player.*/
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
			GamePanel.logger.log(Level.SEVERE, "Failed Loading Player Sprites", e);
		}
	}

	/** Updates the state of the player based on user input,
	 * and other external factors.*/
	public void update() {

		// Temp collision box variable
		Rectangle playerCollision = collisionBox;

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
				hitBox = hitBoxUp;
			} else if (keyHandler.isLastMoveKeyPressed(KeyEvent.VK_S)) {
				direction = "down";
				hitBox = hitBoxDown;
			} else if (keyHandler.isLastMoveKeyPressed(KeyEvent.VK_A)) {
				direction = "left";
				hitBox = hitBoxLeft;
			} else {
				direction = "right";
				hitBox = hitBoxRight;
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
		if(keyHandler.isKeyToggled(KeyEvent.VK_SPACE) != attackToggle && hasSword()) {
			attackToggle = !attackToggle;
			if(!attacking) {
				attacking = true;
				spriteCounter = 0;
				spriteNum = 1;
			}
		}

		if(attacking && spriteNum >= 4) {
			attacking = false;
		}

		if(attacking) {
			collisionBox = hitBox;
			for(Entity ent : gamePanel.entityManager.entities) {
				if(ent instanceof Enemy) {
					if(collides(this, ent)) {
						if(hasBloodySword) {
							((Enemy) ent).damage(100);
						} else if(hasGoldenSword) {
							 ((Enemy) ent).damage(50);
						} else if(hasIronSword) {
							((Enemy) ent).damage(34);
						} else if(hasWoodenSword) {
							((Enemy) ent).damage(25);
						}
					}
				}
			}
		}
		collisionBox = playerCollision;

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

	/** Returns true if the player possesses any sword.*/
	private boolean hasSword() {
		return hasBloodySword || hasGoldenSword || hasIronSword || hasWoodenSword;
	}

	/** Subtracts the specified amount from the players' health
	 * if the player is vulnerable. After receiving damage ane time
	 * the player has a certain amount of invulnerability frames until
	 * it can again receive damage.
	 * @param damage Amount of health to subtract.*/
	public void damage(int damage) {
		if (!invulnerable) {
			i_counter = 0;
			health -= damage;
		}

		if (health <= 0) {
			worldX = gamePanel.tileSize * 90;
			worldY = gamePanel.tileSize * 85;
			health = MAX_HEALTH;
			gamePanel.pauseState = true;
		}
	}
	
	/** Enables reading of readable objects if they are in reach of the
	 * player.
	 * @author marcos.martinez@opendeusto.es*/
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
			case "Chest":
				playerReading = gamePanel.collisionChecker.isPlayerAbleToRead(player, gamePanel.assets[i]);
				
			}			
		}
	}
	
	/** Picks an item specified by an id if no higher tier
	 * main.items are owned by the player.
	 * @author marcos.martinez@opendeusto.es*/
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

	/** Draws the player on a given Graphics2D object.
	 * @param g2 Graphics2D object where the player will be drawn.*/
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
		if(debugCollision) {
			g2.setColor(new Color(255, 0, 0, 150));
			g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
		}

		if(debugHit) {
			g2.setColor(new Color(255, 235, 0, 150));
			g2.fillRect(hitBox.x + screenX, hitBox.y + screenY, hitBox.width, hitBox.height);
		}
	}

}
