package Collisions;

import entity.Entity;
import main.GamePanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CollisionChecker {
    
    GamePanel gamePanel;

    // Collisions
    public int[][] collisionMap;

    Rectangle[] collisions;

    // The pair of tiles the player is facing
    Rectangle tile1Collision;
    Rectangle tile2Collision;

    public CollisionChecker(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        loadCollisions("../maps/Map2.2/Map_02_Collisions.csv");

        tile1Collision = new Rectangle();
        tile2Collision = new Rectangle();
    }

    private void loadCollisions(String collisionsPath) {

        // Loading collision map
        try {
            InputStream is = getClass().getResourceAsStream(collisionsPath);
            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(",");

                for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int tileNum = Integer.parseInt(numbers[col]);
                    collisionMap[row][col] = tileNum;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Custom collisions
        collisions = new Rectangle[4];

        collisions[0] = new Rectangle(0, 0, 64, 64);
        collisions[1] = new Rectangle(16, 16, 32, 32);
        collisions[2] = new Rectangle(0, 0, 6, 64);
        collisions[3] = new Rectangle(58, 0, 6, 64);

    }

    // TODO: CLEAN THIS MESS
    public void checkTileCollision(Entity entity) {

        // Calculating the four edges of the collision box
        int collisionLeftBound = entity.worldX + entity.collisionBox.x;
        int collisionRightBound = collisionLeftBound + entity.collisionBox.width;
        int collisionTopBound = entity.worldY + entity.collisionBox.y;
        int collisionBottomBound = collisionTopBound + entity.collisionBox.height;

        // Calculating the row and column in the tile matrix
        int leftCol = collisionLeftBound / gamePanel.tileSize;
        int rightCol = collisionRightBound / gamePanel.tileSize;
        int topRow = collisionTopBound / gamePanel.tileSize;
        int bottomRow = collisionBottomBound / gamePanel.tileSize;

        tile1Collision = new Rectangle();
        tile2Collision = new Rectangle();
        Rectangle entityCollision = new Rectangle();
        Rectangle collision;
        switch(entity.direction) {
            case "up":
                topRow = (collisionTopBound - entity.speed) / gamePanel.tileSize;
                if (collisionMap[topRow][leftCol] != -1) {
                    collision = collisions[collisionMap[topRow][leftCol]];
                    tile1Collision = new Rectangle(
                        leftCol * gamePanel.tileSize + collision.x,
                        topRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                if(collisionMap[topRow][rightCol] != -1) {
                    collision = collisions[collisionMap[topRow][rightCol]];
                    tile2Collision = new Rectangle(
                        rightCol * gamePanel.tileSize + collision.x,
                        topRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                entityCollision = new Rectangle(
                        entity.worldX + entity.collisionBox.x,
                        entity.worldY + entity.collisionBox.y - entity.speed,
                         entity.collisionBox.width, entity.collisionBox.height);
                break;
            case "down":
                bottomRow = (collisionBottomBound + entity.speed) / gamePanel.tileSize;
                if (collisionMap[bottomRow][leftCol] != -1) {
                    collision = collisions[collisionMap[bottomRow][leftCol]];
                    tile1Collision = new Rectangle(
                        leftCol * gamePanel.tileSize + collision.x,
                        bottomRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                if(collisionMap[bottomRow][rightCol] != -1) {
                    collision = collisions[collisionMap[bottomRow][rightCol]];
                    tile2Collision = new Rectangle(
                        rightCol * gamePanel.tileSize + collision.x,
                        bottomRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                entityCollision = new Rectangle(
                        entity.worldX + entity.collisionBox.x,
                        entity.worldY + entity.collisionBox.y + entity.speed,
                         entity.collisionBox.width, entity.collisionBox.height);
                break;
            case "left":
                leftCol = (collisionLeftBound - entity.speed) / gamePanel.tileSize;
                if (collisionMap[bottomRow][leftCol] != -1) {
                    collision = collisions[collisionMap[bottomRow][leftCol]];
                    tile1Collision = new Rectangle(
                        leftCol * gamePanel.tileSize + collision.x,
                        bottomRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                if(collisionMap[topRow][leftCol] != -1) {
                    collision = collisions[collisionMap[topRow][leftCol]];
                    tile2Collision = new Rectangle(
                        leftCol * gamePanel.tileSize + collision.x,
                        topRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                entityCollision = new Rectangle(
                        entity.worldX + entity.collisionBox.x - entity.speed,
                        entity.worldY + entity.collisionBox.y,
                         entity.collisionBox.width, entity.collisionBox.height);
                break;
            case "right":
                rightCol = (collisionRightBound + entity.speed) / gamePanel.tileSize;
                if (collisionMap[topRow][rightCol] != -1) {
                    collision = collisions[collisionMap[topRow][rightCol]];
                    tile1Collision = new Rectangle(
                        rightCol * gamePanel.tileSize + collision.x,
                        topRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                if(collisionMap[bottomRow][rightCol] != -1) {
                    collision = collisions[collisionMap[bottomRow][rightCol]];
                    tile2Collision = new Rectangle(
                        rightCol * gamePanel.tileSize + collision.x,
                        bottomRow * gamePanel.tileSize + collision.y,
                        collision.width, collision.height);
                }

                entityCollision = new Rectangle(
                        entity.worldX + entity.collisionBox.x + entity.speed,
                        entity.worldY + entity.collisionBox.y,
                         entity.collisionBox.width, entity.collisionBox.height);
                break;
        }

        entity.collisionOn = entityCollision.intersects(tile1Collision) || entityCollision.intersects(tile2Collision);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(new Color(255, 0, 0, 100));
        g2.fillRect(
                tile1Collision.x - gamePanel.player.worldX + gamePanel.player.screenX,
                tile1Collision.y - gamePanel.player.worldY + gamePanel.player.screenY,
                tile1Collision.width, tile1Collision.height);
        g2.fillRect(
                tile2Collision.x - gamePanel.player.worldX + gamePanel.player.screenX,
                tile2Collision.y - gamePanel.player.worldY + gamePanel.player.screenY,
                tile2Collision.width, tile2Collision.height);
    }
}
