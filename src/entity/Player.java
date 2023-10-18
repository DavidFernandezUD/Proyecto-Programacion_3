package entity;

import main.KeyHandler;
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

    public final int screenX;
    public final int screenY;

    // Just for debugging purposes (Displays Collision Box)
    boolean debug = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {

        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = (gamePanel.screenWidth / 2) - (gamePanel.tileSize / 2);
        screenY = (gamePanel.screenHeight / 2) - (gamePanel.tileSize / 2);

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

        try {
            BufferedImage runSpriteSheet = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\run.png"));

            upRunSprites = runSpriteSheet.getSubimage(0, 0, 128, 32);
            leftRunSprites = runSpriteSheet.getSubimage(0, 32, 128, 32);
            rightRunSprites = runSpriteSheet.getSubimage(0, 64, 128, 32);
            downRunSprites = runSpriteSheet.getSubimage(0, 96, 128, 32);

            BufferedImage idleSpriteSheet = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\idle.png"));

            upIdleSprites = idleSpriteSheet.getSubimage(0, 0, 128, 32);
            leftIdleSprites = idleSpriteSheet.getSubimage(0, 32, 128, 32);
            rightIdleSprites = idleSpriteSheet.getSubimage(0, 64, 128, 32);
            downIdleSprites = idleSpriteSheet.getSubimage(0, 96, 128, 32);
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

        spriteCounter++;
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

        if(moving) {
            switch(direction) {
            case "up":
                image = upRunSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "down":
                image = downRunSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "left":        
                image = leftRunSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "right":
                image = rightRunSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            }
        } else {
            switch(direction) {
            case "up":
                image = upIdleSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "down":
                image = downIdleSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "left":        
                image = leftIdleSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            case "right":
                image = rightIdleSprites.getSubimage((spriteNum - 1) * 32, 0, 32, 32);
                break;
            }
        }
        

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // Show collision box
        if(debug) {
            g2.setColor(new Color(255, 0, 0, 150));
            g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
        }
    }
}
