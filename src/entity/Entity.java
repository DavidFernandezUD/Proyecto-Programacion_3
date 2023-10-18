package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed;

    public BufferedImage upRunSprites, upIdleSprites,
                         downRunSprites, downIdleSprites,
                         leftRunSprites, leftIdleSprites,
                         rightRunSprites, rightIdleSprites;
                         
    public String direction;
    public boolean moving;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionBox;
    public boolean collisionOn = false;
}
