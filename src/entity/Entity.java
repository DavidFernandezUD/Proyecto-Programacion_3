package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed;

    public BufferedImage upRunSprites, upIdleSprites, upAttackSprites,
                         downRunSprites, downIdleSprites, downAttackSprites,
                         leftRunSprites, leftIdleSprites, leftAttackSprites,
                         rightRunSprites, rightIdleSprites, rightAttackSprites;
                         
    public String direction;
    public String attackDirection;

    public boolean moving;
    public boolean attacking;
    public boolean attackEnded;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle collisionBox;
    public boolean collisionOn = false;

    public Rectangle hitBox;
    public boolean attackOn = false;
}
