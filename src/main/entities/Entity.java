package main.entities;

import main.interfaces.Drawable;
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
    
    // For items
    public int collisionBoxDefaultX, collisionBoxDefaultY;
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

    protected static int[] getVector(Entity ent, PathFinder.Node node) {
        return new int[] {node.col * ent.tileSize - ent.worldX, ent.worldY - node.row * ent.tileSize};
    }

    // Used to get the direction of one main.entity with respect to another in degrees
    protected static double getAngle(Entity ent1, Entity ent2) {
        return Math.toDegrees(Math.atan2(ent2.worldX - ent1.worldX, ent1.worldY - ent2.worldY));
    }

    protected static double getAngle(Entity ent, PathFinder.Node node) {
        return Math.toDegrees(Math.atan2(node.col * ent.tileSize - ent.worldX, ent.worldY - node.row * ent.tileSize));
    }

    // Used to get the cardinal direction of a main.entity with respect to another
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

    protected static String getDirection(Entity ent, PathFinder.Node node) {
        double angle = getAngle(ent, node);

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

    protected static double getDistance(Entity ent, PathFinder.Node node) {
        int[] vector = getVector(ent, node);
        return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1]);
    }

    // Utility method for redrawing props on top of entities
    protected void redrawProp(Graphics2D g2, Entity entity, int screenX, int screenY) {

		// Checking if the left and right tiles under the player are prop tiles
		// The -1 is to avoid the lower main.tile to change to the next lower one when
		// the player is just on the top edge of the main.tile
		int propLeft = gamePanel.tileManager.map.get(3)[(entity.worldY + tileSize - 1)
				/ tileSize][entity.worldX / tileSize];
		int propRight = gamePanel.tileManager.map.get(3)[(entity.worldY + tileSize - 1)
				/ tileSize][(entity.worldX + tileSize) / tileSize];

		// Calculating offsets with respect to the player to redraw the tiles at that
		// position
		int offsetX = entity.worldX % tileSize;
		int offsetY = (entity.worldY - 1) % tileSize + 1;

		// Special cases
		int COLUMN = 619;
		int COLUMN_TOP = 595;
		int STONE_THRESHOLD = 824; // All the stone props are above this value (we don't want to repaint them)

		// Redrawing lower left main.tile (if necessary)
		if (propLeft != -1 && propLeft < STONE_THRESHOLD) { //
			g2.drawImage(gamePanel.tileManager.tiles[propLeft].image, screenX - offsetX, screenY + tileSize - offsetY,
					gamePanel.tileSize, gamePanel.tileSize, null);

			// If propLeft is a "column" the top part is automatically drawn on top too
			if (propLeft == COLUMN) {
				g2.drawImage(gamePanel.tileManager.tiles[COLUMN_TOP].image, screenX - offsetX, screenY - offsetY,
						gamePanel.tileSize, gamePanel.tileSize, null);
			}
		}

		// Redrawing lower right main.tile (if necessary)
		if (propRight != -1 && propRight < STONE_THRESHOLD) {
			g2.drawImage(gamePanel.tileManager.tiles[propRight].image, screenX + tileSize - offsetX,
					screenY + tileSize - offsetY, gamePanel.tileSize, gamePanel.tileSize, null);

			// If propRight is a "column" the top part is automatically drawn on top too
			if (propRight == COLUMN) {
				g2.drawImage(gamePanel.tileManager.tiles[COLUMN_TOP].image, screenX + tileSize - offsetX,
						screenY - offsetY, gamePanel.tileSize, gamePanel.tileSize, null);
			}
		}
	}

    protected boolean collides(Entity ent1, Entity ent2) {
        Rectangle collision1 = new Rectangle(
                ent1.worldX + ent1.collisionBox.x,
                ent1.worldY + ent1.collisionBox.y,
                ent1.collisionBox.width,
                ent1.collisionBox.height
        );

        Rectangle collision2 = new Rectangle(
                ent2.worldX + ent2.collisionBox.x,
                ent2.worldY + ent2.collisionBox.y,
                ent2.collisionBox.width,
                ent2.collisionBox.height
        );

        return collision1.intersects(collision2);
    }
}
