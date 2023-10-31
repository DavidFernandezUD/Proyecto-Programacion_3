package entity;

import main.KeyHandler;
import main.Utility;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    final int tileSize;

    public final int defaultScreenX;
    public final int defaultScreenY;

    // For camera locking
    public int screenX;
    public int screenY;

    public boolean screenXLocked;
    public boolean screenYLocked;

    // Just for debugging purposes (Displays Collision Box)
    boolean debug = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        tileSize = gamePanel.tileSize;

        defaultScreenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        defaultScreenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

        collisionBox = new Rectangle(11, 22, 42, 42);
        hitBox = new Rectangle(-10, -20, 84, 43);

        setDefaultValues();
        getPlayerSprite();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 25;
        worldY = gamePanel.tileSize * 35;
        speed = 4;
        direction = "down";
        moving = false;
        attackDirection = "down";
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

    public void update() {
        
        // The spriteCounter is only incremented if a key is pressed
        if(keyHandler.upPressed || keyHandler.rightPressed || keyHandler.downPressed || keyHandler.leftPressed) {

            // If player has just started moving the spriteNum and counter is restarted
            if(!moving) {
                spriteNum = 1;
                spriteCounter = 13;
            }

            if(attacking) {
                speed = 2;
            } else {
                speed = 4;
            }

            moving = true;

            if(keyHandler.upPressed) {
                direction = "up";
            } else if(keyHandler.downPressed) {
                direction = "down";
            } else if(keyHandler.leftPressed) {
                direction = "left";
            } else {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // If collision is false the player can move
            if(!collisionOn) {
                switch(direction) {
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
        }
        else {
            moving = false;
        }

        // Attack
        if(keyHandler.attackUpPressed || keyHandler.attackRightPressed || keyHandler.attackDownPressed || keyHandler.attackLeftPressed) {
            
            if(!attacking) {
                spriteNum = 1;
                spriteCounter = 13;
                attackEnded = false;
            } else if(spriteNum == 1) {
                // If it was already attacking and the animation has already looped once it doesn't continue attacking
                attackEnded = true;
            }

            attacking = true;

            if(keyHandler.attackUpPressed) {
                attackDirection = "up";
            } else if(keyHandler.attackDownPressed) {
                attackDirection = "down";
            } else if(keyHandler.attackLeftPressed) {
                attackDirection = "left";
            } else {
                attackDirection = "right";
            }

        } else if(spriteNum == 1) {
            attacking = false;
        }

        if(attacking && !attackEnded) {
            spriteCounter += 2;
        } else {
            spriteCounter++;
        }

        if(spriteCounter > 12) {
            if(spriteNum == 1) {
                spriteNum = 2;
            } else if(spriteNum == 2) {
                spriteNum = 3;
            } else if(spriteNum == 3) {
                spriteNum = 4;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage image = null;

        if(attacking && !attackEnded) {
            image = getBufferedImage(image, attackSprites);
            direction = attackDirection; // Direction changes when attacking
        } else if(moving) {
            image = getBufferedImage(image, runSprites);
        } else {
            image = getBufferedImage(image, idleSprites);
        }
        
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

            g2.setColor(new Color(0, 255, 0, 150));
            g2.fillRect(hitBox.x + screenX, hitBox.y + screenY, hitBox.width, hitBox.height);
        }
    }
    private BufferedImage getBufferedImage(BufferedImage image, BufferedImage spriteSheet) {
        image = switch (direction) {
            case "up" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
            case "left" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize, tileSize, tileSize);
            case "right" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 2, tileSize, tileSize);
            case "down" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 3, tileSize, tileSize);
            default -> image;
        };
        return image;
    }
}
