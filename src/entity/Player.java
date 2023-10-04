package entity;

import main.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerSprite();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerSprite() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_right_2.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        
        // The spriteCounter is only incremented if a key is pressed
        if(keyHandler.upPressed || keyHandler.rightPressed || keyHandler.downPressed || keyHandler.leftPressed) {
            if(keyHandler.upPressed) {
                direction = "up";
                y -= speed;
            } else if(keyHandler.downPressed) {
                direction = "down";
                y += speed;
            } else if(keyHandler.leftPressed) {
                direction = "left";
                x -= speed;
            } else if(keyHandler.rightPressed) {
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            if(spriteCounter > 12) {
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {
            // If no key is being pressed spriteNum sets to default 1
            // spriteCounter is set to the limit so that it changes the spriteNum inmediately after pressing a key
            spriteCounter = 13;
            spriteNum = 1;
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

    }
}
