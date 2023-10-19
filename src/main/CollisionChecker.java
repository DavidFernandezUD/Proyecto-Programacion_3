package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    // TODO: FIX THIS MESS
    public void checkTile(Entity entity) {
        
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

        // You only need to check 2 tiles when moving in a single direction
        int tileNum1, tileNum2;
        
        switch(entity.direction) {
            case "up":
                topRow = (collisionTopBound - entity.speed) / gamePanel.tileSize;

                tileNum1 = gamePanel.tileManager.level1TileNum[topRow][leftCol] +
                           gamePanel.tileManager.level2TileNum[topRow][leftCol];

                tileNum2 = gamePanel.tileManager.level1TileNum[topRow][rightCol] +
                           gamePanel.tileManager.level2TileNum[topRow][rightCol];
                
                // Only checking for collisions if a solid tile exist at those positions
                if(tileNum1 != -2 || tileNum2 != -2) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                bottomRow = (collisionBottomBound + entity.speed) / gamePanel.tileSize;

                tileNum1 = gamePanel.tileManager.level1TileNum[bottomRow][leftCol] +
                           gamePanel.tileManager.level2TileNum[bottomRow][leftCol];

                tileNum2 = gamePanel.tileManager.level1TileNum[bottomRow][rightCol] + 
                           gamePanel.tileManager.level2TileNum[bottomRow][rightCol];

                if(tileNum1 != -2 || tileNum2 != -2) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                leftCol = (collisionLeftBound - entity.speed) / gamePanel.tileSize;

                tileNum1 = gamePanel.tileManager.level1TileNum[topRow][leftCol] +
                           gamePanel.tileManager.level2TileNum[topRow][leftCol];

                tileNum2 = gamePanel.tileManager.level1TileNum[bottomRow][leftCol] + 
                           gamePanel.tileManager.level2TileNum[bottomRow][leftCol];

                if(tileNum1 != -2 || tileNum2 != -2) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                rightCol = (collisionRightBound + entity.speed) / gamePanel.tileSize;

                tileNum1 = gamePanel.tileManager.level1TileNum[topRow][rightCol] +
                           gamePanel.tileManager.level2TileNum[topRow][rightCol];

                tileNum2 = gamePanel.tileManager.level1TileNum[bottomRow][rightCol] + 
                           gamePanel.tileManager.level2TileNum[bottomRow][rightCol];

                if(tileNum1 != -2 || tileNum2 != -2) {
                    entity.collisionOn = true;
                }
                break;
        }
    }
}
