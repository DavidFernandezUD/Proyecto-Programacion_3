package entity;

import main.KeyHandler;
import main.Utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
            BufferedImage runSpriteSheet = ImageIO.read(getClass().getResourceAsStream("../res/player//run.png"));
            runSpriteSheet = util.scaleImage(runSpriteSheet, tileSize * 4, tileSize * 4);

            upRunSprites = runSpriteSheet.getSubimage(0, 0, tileSize * 4, tileSize);
            leftRunSprites = runSpriteSheet.getSubimage(0, tileSize, tileSize * 4, tileSize);
            rightRunSprites = runSpriteSheet.getSubimage(0, tileSize * 2, tileSize * 4, tileSize);
            downRunSprites = runSpriteSheet.getSubimage(0, tileSize * 3, tileSize * 4, tileSize);

            BufferedImage idleSpriteSheet = ImageIO.read(getClass().getResourceAsStream("..//res//player//idle.png"));
            idleSpriteSheet = util.scaleImage(idleSpriteSheet, tileSize * 4, tileSize * 4);

            upIdleSprites = idleSpriteSheet.getSubimage(0, 0, tileSize * 4, tileSize);
            leftIdleSprites = idleSpriteSheet.getSubimage(0, tileSize, tileSize * 4, tileSize);
            rightIdleSprites = idleSpriteSheet.getSubimage(0, tileSize * 2, tileSize * 4, tileSize);
            downIdleSprites = idleSpriteSheet.getSubimage(0, tileSize * 3, tileSize * 4, tileSize);

            BufferedImage attackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("..//res//player//attack1.png"));
            attackSpriteSheet = util.scaleImage(attackSpriteSheet, tileSize * 4, tileSize * 4);

            upAttackSprites = attackSpriteSheet.getSubimage(0, 0, tileSize * 4, tileSize);
            leftAttackSprites = attackSpriteSheet.getSubimage(0, tileSize, tileSize * 4, tileSize);
            rightAttackSprites = attackSpriteSheet.getSubimage(0, tileSize * 2, tileSize * 4, tileSize);
            downAttackSprites = attackSpriteSheet.getSubimage(0, tileSize * 3, tileSize * 4, tileSize);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        
        // The spriteCounter is only incremented if a key is pressed
        if(keyHandler.upPressed || keyHandler.rightPressed || keyHandler.downPressed || keyHandler.leftPressed) {

            // If playeer has just started moving the spriteNum and counter is restarted
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
            } else if(keyHandler.rightPressed) {
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
                // If it was already attacking and the animation has already looped once it doesn`t continue attacking
                attackEnded = true;
            }

            attacking = true;

            if(keyHandler.attackUpPressed) {
                attackDirection = "up";
            } else if(keyHandler.attackDownPressed) {
                attackDirection = "down";
            } else if(keyHandler.attackLeftPressed) {
                attackDirection = "left";
            } else if(keyHandler.attackRightPressed) {
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
            switch(attackDirection) {
            case "up":
                image = upAttackSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                direction = "up";
                break;
            case "down":
                image = downAttackSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                direction = "down";
                break;
            case "left":        
                image = leftAttackSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                direction = "left";
                break;
            case "right":
                image = rightAttackSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                direction = "right";
                break;
            }
        } else if(moving) {
            switch(direction) {
            case "up":
                image = upRunSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "down":
                image = downRunSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "left":        
                image = leftRunSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "right":
                image = rightRunSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            }
        } else {
            switch(direction) {
            case "up":
                image = upIdleSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "down":
                image = downIdleSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "left":        
                image = leftIdleSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            case "right":
                image = rightIdleSprites.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
                break;
            }
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
}
