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
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerSprite() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("..\\res\\player\\boy_down_1.png"));
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
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = up1;
                break;
            case "down":
                image = down1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
        }

        g2.drawImage(image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
    }
}
