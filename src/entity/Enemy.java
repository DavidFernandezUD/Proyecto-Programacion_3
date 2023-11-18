package entity;

import interfaces.Drawable;
import main.GamePanel;
import main.Utility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Enemy extends Entity implements Drawable {

    private boolean debug = true;

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        setDefaultValues();
        getEnemySprite();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 22;
        worldY = gamePanel.tileSize * 34;
        speed = 2;
        direction = "up";
        moving = true;
        attacking = false;
        collisionBox = new Rectangle(11, 22, 42, 42);
    }

    public void getEnemySprite() {

        // For image scaling and optimization
        Utility util = new Utility();

        try {
            runSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/enemy/run.png")));
            runSprites = util.scaleImage(runSprites, tileSize * 4, tileSize * 4);

            idleSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/enemy/idle.png")));
            idleSprites = util.scaleImage(idleSprites, tileSize * 4, tileSize * 4);

            attackSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/enemy/attack1.png")));
            attackSprites = util.scaleImage(attackSprites, tileSize * 4, tileSize * 4);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(moving) {
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

        spriteCounter++;

        if(spriteCounter > ANIMATION_FRAMES) {
            if(spriteNum < 4) {
                spriteNum++;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = getSprite(direction);

        int screenX;
        int screenY;

        if(!gamePanel.player.screenXLocked) {
            if(gamePanel.player.worldX < gamePanel.screenWidth) {
                screenX = worldX;
            } else {
                screenX = worldX - gamePanel.worldWidth + gamePanel.screenWidth;
            }
        } else {
            screenX = worldX - gamePanel.player.worldX + gamePanel.player.defaultScreenX;
        }

        if(!gamePanel.player.screenYLocked) {
            if(gamePanel.player.worldY < gamePanel.screenHeight) {
                screenY = worldY;
            } else {
                screenY = worldY - gamePanel.worldHeight + gamePanel.screenHeight;
            }
        }  else {
            screenY = worldY - gamePanel.player.worldY + gamePanel.player.defaultScreenY;
        }

        // Drawing Player
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        // Drawing collision box
        if(debug) {
            g2.setColor(new Color(255, 0, 0, 150));
            g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
        }
    }
}
