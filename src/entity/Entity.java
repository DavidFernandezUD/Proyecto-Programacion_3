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

    // Used to get the vector between two entities
    protected static int[] getVector(Entity ent1, Entity ent2) {
        return new int[] {ent2.worldX - ent1.worldX, ent1.worldY - ent2.worldY};
    }

    // Used to get the direction of one entity with respect to another in degrees
    protected static double getAngle(Entity ent1, Entity ent2) {
        return Math.toDegrees(Math.atan2(ent2.worldX - ent1.worldX, ent1.worldY - ent2.worldY));
    }

    // Used to get the cardinal direction of an entity with respect to another
    protected static String getDirection(Entity ent1, Entity ent2) {
        double angle = getAngle(ent1, ent2);

        if(angle >= 45 && angle < 135) {
            return "right";
        } else if(angle >= 135 || angle < -135) {
            return "down";
        } else if(angle >= -135 && angle < -45) {
            return "left";
        } else {
            return "up";
        }
    }

    // Returns euclidean distance between two entities
    protected static double getDistance(Entity ent1, Entity ent2) {
        int[] vector = getVector(ent1, ent2);
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
    }
}
