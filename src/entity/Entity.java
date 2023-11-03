package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    
    public int worldX, worldY;
    public int speed;

    public BufferedImage idleSprites, runSprites, attackSprites;
                         
    public String direction;

    public boolean moving;
    public boolean attacking;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public final int ANIMATION_FRAMES = 12; // Frames per animation step

    public Rectangle collisionBox;
    public boolean collisionOn = false;


    // TODO: Implement attacking functionality
    public Rectangle hitBox;
    public boolean attackOn = false;
}
