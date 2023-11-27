package main.collisions;

import main.entities.Entity;
import main.objects.SuperObject;
import main.GamePanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CollisionChecker {
    
    GamePanel gamePanel;

    // main.Collisions
    public int[][] collisionMap;

    Rectangle[] collisions;

    // The pair of tiles the player is facing
    Rectangle tile1Collision;
    Rectangle tile2Collision;

    public CollisionChecker(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        loadCollisions();

        tile1Collision = new Rectangle();
        tile2Collision = new Rectangle();
    }

    private void loadCollisions() {

        collisionMap = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];

        // Loading collision map
        try {
            InputStream is = getClass().getResourceAsStream("/main/res/maps/Map2.2/Map_02_Collisions.csv");
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
        collisions = new Rectangle[16];

        collisions[0] = new Rectangle(0, 0, 64, 64);

        collisions[1] = new Rectangle(16, 16, 32, 32);

        collisions[2] = new Rectangle(0, 0, 6, 64);
        collisions[3] = new Rectangle(58, 0, 6, 64);

        collisions[4] = new Rectangle(0, 0, 64, 6);
        collisions[5] = new Rectangle(0, 58, 64, 6);

        // Little trick to make the inner corner collisions
        collisions[6] = new Rectangle(0, 0, 20, 24); // TODO: Implement Corner
        collisions[7] = new Rectangle(44, 0, 20, 24); // TODO: Implement Corner

        collisions[8] = new Rectangle(0, 0, 32, 32);
        collisions[9] = new Rectangle(32, 0, 32, 32);
        collisions[10] = new Rectangle(0, 32, 32, 32);
        collisions[11] = new Rectangle(32, 32, 32, 32);

        collisions[12] = new Rectangle(0, 0, 32, 64);
        collisions[13] = new Rectangle(32, 0, 32, 64);
        collisions[14] = new Rectangle(0, 0, 64, 32);
        collisions[15] = new Rectangle(0, 32, 64, 32);
    }

    // TODO: CLEAN THIS MESS
    public void checkTileCollision(Entity entity) {

        // Calculating the four edges of the collision box
        int collisionLeftBound = entity.worldX + entity.collisionBox.x;
        int collisionRightBound = collisionLeftBound + entity.collisionBox.width;
        int collisionTopBound = entity.worldY + entity.collisionBox.y;
        int collisionBottomBound = collisionTopBound + entity.collisionBox.height;

        // Calculating the row and column in the main.tile matrix
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
                    tile1Collision = new Rectangle(collision);
                    tile1Collision.setLocation(
                            leftCol * gamePanel.tileSize + collision.x,
                            topRow * gamePanel.tileSize + collision.y);
                }

                if(collisionMap[topRow][rightCol] != -1) {
                    collision = collisions[collisionMap[topRow][rightCol]];
                    tile2Collision = new Rectangle(collision);
                    tile2Collision.setLocation(
                        rightCol * gamePanel.tileSize + collision.x,
                        topRow * gamePanel.tileSize + collision.y);
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
    
    // for objects
    public int checkObject(Entity entity, boolean player) {
    	int index = 999;
    	
    	for (int i = 0; i < gamePanel.obj.length; i++) {
    		
    		if (gamePanel.obj[i] != null) {

    			entity.collisionBox.x = entity.worldX +  entity.collisionBox.x;
    			entity.collisionBox.y = entity.worldY +  entity.collisionBox.y;
    			
    			gamePanel.obj[i].solidArea.x = gamePanel.obj[i].worldX +
    					gamePanel.obj[i].solidArea.x;
    			gamePanel.obj[i].solidArea.y = gamePanel.obj[i].worldY +
    					gamePanel.obj[i].solidArea.y;
    			
    			
    			switch(entity.direction) {
    			case "up":
    				entity.collisionBox.y -= entity.speed;
    				if (entity.collisionBox.intersects(gamePanel.obj[i].solidArea)) {
    					if (gamePanel.obj[i].collision) {
    						entity.collisionOn = true;
    					}
    					if (player) {
    						index = i;
    					}
    				}
    				break;
    			case "down":
    				entity.collisionBox.y += entity.speed;
    				if (entity.collisionBox.intersects(gamePanel.obj[i].solidArea)) {
    					if (gamePanel.obj[i].collision) {
    						entity.collisionOn = true;
    					}
    					if (player) {
    						index = i;
    					}
    				}
    				break;
    			case "left":
    				entity.collisionBox.x -= entity.speed;
    				if (entity.collisionBox.intersects(gamePanel.obj[i].solidArea)) {
    					if (gamePanel.obj[i].collision) {
    						entity.collisionOn = true;
    					}
    					if (player) {
    						index = i;
    					}
    				}
    				break;
    			case "right":
    				entity.collisionBox.x += entity.speed;
    				if (entity.collisionBox.intersects(gamePanel.obj[i].solidArea)) {
    					if (gamePanel.obj[i].collision) {
    						entity.collisionOn = true;
    					}
    					if (player) {
    						index = i;
    					}
    				}
    				break;
    			}
    			
    			entity.collisionBox.x = entity.collisionBoxDefaultX;
    			entity.collisionBox.y = entity.collisionBoxDefaultY;
    			gamePanel.obj[i].solidArea.x = gamePanel.obj[i].solidAreaDefaultX;
    			gamePanel.obj[i].solidArea.y = gamePanel.obj[i].solidAreaDefaultY;
    		}
    		
    		
    		
    	}
    	
    	return index;
    	
    }
    
    public boolean isPlayerAbleToRead(Entity player, SuperObject object) {
    	
    	boolean isAble = false;
    	if (player.direction.equals("up")) {
    		// Ajusta las coordenadas de los cuadros de colisión basándote en las posiciones en el mundo del objeto y el jugador
            player.collisionBox.x = player.worldX + player.collisionBox.x;
            player.collisionBox.y = player.worldY + player.collisionBox.y;

            object.solidArea.x = object.worldX + object.solidArea.x;
            object.solidArea.y = object.worldY + object.solidArea.y;

            // Verifica si el jugador está dentro del rectángulo de colisión del objeto
            isAble = object.solidArea.contains(player.collisionBox);
    	}

        // Restablece las coordenadas a sus valores originales
        player.collisionBox.x = player.collisionBoxDefaultX;
        player.collisionBox.y = player.collisionBoxDefaultY;

        object.solidArea.x = object.solidAreaDefaultX;
        object.solidArea.y = object.solidAreaDefaultY;

        return isAble;
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
