package entity;

import main.KeyHandler;
import main.MouseHandler;
import main.Utility;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;
    MouseHandler mouseHandler;

    final int tileSize;

    public final int defaultScreenX;
    public final int defaultScreenY;

    // For camera locking
    public int screenX;
    public int screenY;

    public boolean screenXLocked;
    public boolean screenYLocked;

    // Just for debugging purposes (Displays Collision Box)
    boolean debug = true;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, MouseHandler mouseHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.mouseHandler = mouseHandler;

        tileSize = gamePanel.tileSize;

        defaultScreenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        defaultScreenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

        collisionBox = new Rectangle(11, 22, 42, 42);

        setDefaultValues();
        getPlayerSprite();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 25;
        worldY = gamePanel.tileSize * 35;
        speed = 4;
        direction = "down";
        moving = false;
    }

    public void getPlayerSprite() {

        // For image scaling and optimization
        Utility util = new Utility();

        try {
            runSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/player//run.png")));
            runSprites = util.scaleImage(runSprites, tileSize * 4, tileSize * 4);

            idleSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("..//res//player//idle.png")));
            idleSprites = util.scaleImage(idleSprites, tileSize * 4, tileSize * 4);

            attackSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("..//res//player//attack1.png")));
            attackSprites = util.scaleImage(attackSprites, tileSize * 4, tileSize * 4);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: Rethink this method
    // FIXME: Fix direction bug when attacking
    public void update() {
        
        // MOVING
        if(keyHandler.isMoveKeyPressed()) {

            // If player has just started moving the spriteNum and counter is restarted
            if(!moving) {
                moving = true;
                spriteNum = 1;
                spriteCounter = 13;
            }

            speed = (attacking ? 2 : 4); // Moving speed is reduced when attacking

            if(keyHandler.isLastMoveKeyPressed(KeyEvent.VK_W)) {
                direction = "up";
            } else if(keyHandler.isLastMoveKeyPressed(KeyEvent.VK_S)) {
                direction = "down";
            } else if(keyHandler.isLastMoveKeyPressed(KeyEvent.VK_A)) {
                direction = "left";
            } else {
                direction = "right";
            }

            // Check collisions
            collisionOn = false;
            gamePanel.collisionChecker.checkTileCollision(this);

            // If collision is false the player can move
            if(!collisionOn) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
        }
        else {
            moving = false;
        }

        // ATTACKING
        // FIXME: Fix attacking
        attacking = mouseHandler.isAttackPressed();

        spriteCounter += (attacking ? 2 : 1); // Attack animation runs faster than moving and idle

        if(spriteCounter > ANIMATION_FRAMES) {
            if(spriteNum < 4) {
                spriteNum++;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = getSprite(direction);
        
        // Camera System
        screenX = defaultScreenX;
        screenY = defaultScreenY;

        if(!screenXLocked) {
            if(worldX < gamePanel.screenWidth) {
                screenX = worldX;
            } else {
                screenX = worldX - gamePanel.worldWidth + gamePanel.screenWidth;
            }
        }
        if(!screenYLocked) {
            if(worldY < gamePanel.screenHeight) {
                screenY = worldY;
            } else {
                screenY = worldY - gamePanel.worldHeight + gamePanel.screenHeight;
            }
        }

        // Drawing Player
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // Drawing collision box
        if(debug) {
            g2.setColor(new Color(255, 0, 0, 150));
            g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
        }
    }

    private BufferedImage getSprite(String direction) {

        BufferedImage spriteSheet;

        if(attacking) {
            spriteSheet = attackSprites;
        } else if(moving) {
            spriteSheet = runSprites;
        } else {
            spriteSheet = idleSprites;
        }

        return switch (direction) {
            case "up" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
            case "left" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize, tileSize, tileSize);
            case "right" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 2, tileSize, tileSize);
            case "down" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 3, tileSize, tileSize);
            default -> null;
        };
    }
}
