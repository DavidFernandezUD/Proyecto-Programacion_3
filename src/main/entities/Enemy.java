package main.entities;

import main.interfaces.Drawable;
import main.GamePanel;
import main.Utility;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Enemy extends Entity implements Drawable {

    private boolean debug = false;
    private ArrayList<PathFinder.Node> path = null;
    private final int TRACKING_RANGE = 20; // Maximum tracking range in tiles
    private boolean changedTile = true;

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        setDefaultValues();
        getEnemySprite();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 22;
        worldY = gamePanel.tileSize * 34;
        speed = 2;
        direction = "down";
        moving = true;
        attacking = false;
        collisionBox = new Rectangle(11, 22, 42, 42);
    }

    public void getEnemySprite() {

        // For image scaling and optimization
        Utility util = new Utility();

        try {
            runSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/enemy/run.png")));
            runSprites = util.scaleImage(runSprites, tileSize * 4, tileSize * 4);

            idleSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/enemy/idle.png")));
            idleSprites = util.scaleImage(idleSprites, tileSize * 4, tileSize * 4);

            attackSprites = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/main/res/enemy/attack1.png")));
            attackSprites = util.scaleImage(attackSprites, tileSize * 4, tileSize * 4);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        double distance = Entity.getDistance(this, gamePanel.player);
        moving = distance > 0 && distance < tileSize * TRACKING_RANGE;

        if(moving) {

            // Past tile coordinates are stored to check for tile change
			int pastCol = worldX / tileSize;
			int pastRow = worldY / tileSize;

            // Path Only calculated if player changed tile
            if(gamePanel.entityManager.playerChangedTile || changedTile) {
                path = gamePanel.pathFinder.search(this, gamePanel.player);
            }

            if(path != null) {

                int nextX = path.get(0).col * tileSize;
                int nextY = path.get(0).row * tileSize;

                if(worldX == nextX && worldY == nextY) {
                    path.remove(0);
                }

                direction = Entity.getDirection(this, path.get(0));

                // Check collisions
                collisionOn = false;
                gamePanel.collisionChecker.checkTileCollision(this);

                // Obstacle avoidance
                if(collisionOn) {
                    if(Objects.equals(direction, "up") || Objects.equals(direction, "down")) {
                        if(nextX < worldX) {
                            direction = "left";
                        } else {
                            direction = "right";
                        }
                    } else {
                        if(nextY < worldY) {
                            direction = "up";
                        } else {
                            direction = "down";
                        }
                    }
                }

                // Moving
                switch (direction) {
				    case "up":
					    worldY -= speed;
					    break;
				    case "down":
					    worldY += speed;
					    break;
				    case "left":
					    worldX -= speed;
					    break;
				    case "right":
					    worldX += speed;
					    break;
				}
            }

            // Checking if the tile the players is at has changed
			int currentCol = worldX / tileSize;
			int currentRow = worldY / tileSize;
            changedTile = (currentRow != pastRow) || (currentCol != pastCol);

        }

        // Attacking
        attacking = false;
        if(distance < tileSize) {
            attacking = true;
            if(collides(this, gamePanel.player)) {
                gamePanel.player.damage(10);
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

        // Redrawing props if enemy is behind them
		redrawProp(g2, this, screenX, screenY);

        if(debug) {

            // Drawing Collision Box
            g2.setColor(new Color(255, 0, 0, 150));
            g2.fillRect(collisionBox.x + screenX, collisionBox.y + screenY, collisionBox.width, collisionBox.height);

            // Drawing Path
            if(path != null) {
                g2.setColor(new Color(255, 144, 0, 150));
                for(PathFinder.Node node : path) {
                    screenX = node.col * tileSize - gamePanel.player.worldX + gamePanel.player.screenX;
                    screenY = node.row * tileSize - gamePanel.player.worldY + gamePanel.player.screenY;
                    g2.fillRect(screenX, screenY, tileSize, tileSize);
                }
            }
        }
    }
}
