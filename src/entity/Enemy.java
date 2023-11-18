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

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        setDefaultValues();
        getEnemySprite();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 22;
        worldY = gamePanel.tileSize * 34;
        speed = 4;
        direction = "down";
        moving = false;
        attacking = false;
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
    }

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = getSprite(direction);

        // Drawing Player
        g2.drawImage(image, tileSize, tileSize, gamePanel.tileSize, gamePanel.tileSize, null);

        // Drawing collision box
/*        if(debug) {
            g2.setColor(new Color(255, 0, 0, 150));
            g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);
        }*/
    }
}
