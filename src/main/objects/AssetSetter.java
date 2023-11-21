package main.objects;

import main.interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetSetter implements Drawable {

    GamePanel gamePanel;
    
    public int[][] objectMap;
    
    Rectangle[] objects;
    
    Rectangle objectRectangle; // Just to represent objects visually
    

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        
        loadObjects();
    }
    
    

    private void loadObjects() {
		
    	objectMap = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
    	
    	try {
    		
    		InputStream is = getClass().getResourceAsStream("/main/res/maps/Map2.2/Map_02_Objects.csv");
    		assert is != null;
    		BufferedReader br = new BufferedReader(new InputStreamReader(is));
    		
    		for (int row = 0; row < gamePanel.maxWorldRow; row++) {
    			String line = br.readLine();
    			String[] numbers = line.split(",");
    			
    			for (int col = 0; col < gamePanel.maxWorldCol; col++) {
    				int tileNum = Integer.parseInt(numbers[col]);
    				objectMap[row][col] = tileNum;
    			}
    			
    		}
    		
    	} catch (Exception e) {
            e.printStackTrace();
    	}
		
	}

	@Override
    public void draw(Graphics2D g2) {
		g2.setColor(new Color(0, 0, 139));
		
		boolean xBlocked = playerOnEdge("X");
        boolean yBlocked = playerOnEdge("Y");
		
		for (int row = 0; row < gamePanel.maxWorldRow; row++) {
			for (int col = 0; col < gamePanel.maxWorldCol; col++) {
				
				// Camera
                int worldX = col * gamePanel.tileSize;
                int worldY = row * gamePanel.tileSize;

                int screenX;
                int screenY;

                if(xBlocked) {
                    if(gamePanel.player.worldX < gamePanel.screenWidth) {
                        screenX = worldX;
                    } else {
                        screenX = worldX - gamePanel.worldWidth + gamePanel.screenWidth;
                    }
                    gamePanel.player.screenXLocked = false;
                } else {
                    screenX = worldX - gamePanel.player.worldX + gamePanel.player.defaultScreenX;
                    gamePanel.player.screenXLocked = true;
                }

                if(yBlocked) {
                    if(gamePanel.player.worldY < gamePanel.screenHeight) {
                        screenY = worldY;
                    } else {
                        screenY = worldY - gamePanel.worldHeight + gamePanel.screenHeight;
                    }
                    gamePanel.player.screenYLocked = false;
                } else {
                    screenY = worldY - gamePanel.player.worldY + gamePanel.player.defaultScreenY;
                    gamePanel.player.screenYLocked = true;
                }
				
				if (objectMap[row][col] != -1) {
					g2.drawRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);
					
				}
			}
		}

    }
	
	private boolean playerOnEdge(String axis) {

        int playerX = gamePanel.player.worldX;
        int playerY = gamePanel.player.worldY;

        switch(axis) {
        case "X":
            if(playerX < gamePanel.player.defaultScreenX) {
                return true;
            }
            if(playerX > gamePanel.worldWidth - gamePanel.player.defaultScreenX - gamePanel.tileSize) {
                return true;
            }
            break;
        case "Y":
            if(playerY < gamePanel.player.defaultScreenY) {
                return true;
            }
            if(playerY > gamePanel.worldHeight - gamePanel.player.defaultScreenY - gamePanel.tileSize) {
                return true;
            }
            break;
        }

        return false;
    }
	
	// TODO: think how to make each object act differently
	
}