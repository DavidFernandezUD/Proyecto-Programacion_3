package entity;

import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Drawable {

    GamePanel gamePanel;
    final int tileSize;
    
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

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tileSize = gamePanel.tileSize;
    }

    public abstract void update();

    @Override
    public abstract void draw(Graphics2D g2);

    protected BufferedImage getSprite(String direction) {

        BufferedImage spriteSheet;

        if(attacking) {
            spriteSheet = attackSprites;
        } else if(moving) {
            spriteSheet = runSprites;
        } else {
            spriteSheet = idleSprites;
        }

        return switch (direction) {
            case "up" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, 0, tileSize, tileSize);
            case "left" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize, tileSize, tileSize);
            case "right" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 2, tileSize, tileSize);
            case "down" -> spriteSheet.getSubimage((spriteNum - 1) * tileSize, tileSize * 3, tileSize, tileSize);
            default -> null;
        };
    }
}
