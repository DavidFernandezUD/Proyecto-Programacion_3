package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Utility;
import object.SuperObject;

public class TileManager {
    
    GamePanel gamePanel;

    // TODO: Implement a way of creating different types of collision areas for different tiles
    public Tile[] tiles;

    // PLACEHOLDER
    // TODO: Move this to AssetSetter
    public SuperObject[] objects;

    public int[][] groundTileNum;
    public int[][] level1TileNum;
    public int[][] level2TileNum;
    public int[][] propTileNum;
    public int[][] objTileNum;

    public TileManager(GamePanel gamePanel) {
        
        this.gamePanel = gamePanel;
        
        this.groundTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        this.level1TileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        this.level2TileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        this.propTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        this.objTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        
        getTileSprite();
        loadMap("../maps/Map2/Map_02_Ground.csv",
                "../maps/Map2/Map_02_Level1.csv",
                "../maps/Map2/Map_02_Level2.csv",
                "../maps/Map2/Map_02_Props.csv",
                "../maps/Map2/Map_02_Obj.csv");
    }

    public void getTileSprite() {

        // LOADING TILES
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("../res/tiles/tileSheet.png"));

            // Dimension of the tile-sheet in tiles
            int rows = 35;
            int cols = 24;

            // Size of the individual tiles in the sprite-sheet
            int spriteSize = 32; 

            // This will store all the possible tiles
            this.tiles = new Tile[rows * cols];

            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    BufferedImage tileImage = spriteSheet.getSubimage(j * spriteSize, i * spriteSize, spriteSize, spriteSize);
                    setUp((i * cols) + j, tileImage);
                }
            }

            // TODO: Move this to object manager
            objects = new SuperObject[1];
            
            objects[0] = new SuperObject();
            objects[0].image = ImageIO.read(getClass().getResourceAsStream("../res/objects/sign.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method for scaling images
    public void setUp(int index, BufferedImage image) {
        
        Utility util = new Utility();
        
        BufferedImage rescaledImage = util.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        this.tiles[index] = new Tile(rescaledImage);
    }

    public void loadMap(String groundMap, String level1Map, String level2Map, String propMap, String objMap) {

        // Loading floor map
        try {
            InputStream groundIs = getClass().getResourceAsStream(groundMap);
            BufferedReader groundBr = new BufferedReader(new InputStreamReader(groundIs));

            InputStream level1Is = getClass().getResourceAsStream(level1Map);
            BufferedReader level1Br = new BufferedReader(new InputStreamReader(level1Is));

            InputStream level2Is = getClass().getResourceAsStream(level2Map);
            BufferedReader level2Br = new BufferedReader(new InputStreamReader(level2Is));

            InputStream propIs = getClass().getResourceAsStream(propMap);
            BufferedReader propBr = new BufferedReader(new InputStreamReader(propIs));

            InputStream objIs = getClass().getResourceAsStream(objMap);
            BufferedReader objBr = new BufferedReader(new InputStreamReader(objIs));

            for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                String groundLine = groundBr.readLine();
                String[] groundNumbers = groundLine.split(",");

                String level1Line = level1Br.readLine();
                String[] level1Numbers = level1Line.split(",");

                String level2Line = level2Br.readLine();
                String[] level2Numbers = level2Line.split(",");

                String objLine = objBr.readLine();
                String[] objNumbers = objLine.split(",");

                String propLine = propBr.readLine();
                String[] propNumbers = propLine.split(",");
                for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int groundNum = Integer.parseInt(groundNumbers[col]);
                    int level1Num = Integer.parseInt(level1Numbers[col]);
                    int level2Num = Integer.parseInt(level2Numbers[col]);
                    int propNum = Integer.parseInt(propNumbers[col]);
                    int objNum = Integer.parseInt(objNumbers[col]);

                    groundTileNum[row][col] = groundNum;
                    level1TileNum[row][col] = level1Num;
                    level2TileNum[row][col] = level2Num;
                    propTileNum[row][col] = propNum;
                    objTileNum[row][col] = objNum;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        boolean xBlocked = playerOnEdge("X");
        boolean yBlocked = playerOnEdge("Y");

        for(int row = 0; row < gamePanel.maxWorldRow; row++) {
            for(int col = 0; col < gamePanel.maxWorldCol; col++) {

                // Camera
                // TODO: Block the camera when it touches an edge of the map
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
                
                // The tiles are only painted if they are inside the screen
                // TODO: Check if this has something to do with rendering glitches whem moving left und up
                if(tileOnScreen(worldX, worldY)) {

                    // Ground Level
                    g2.drawImage(tiles[groundTileNum[row][col]].image, screenX, screenY,
                                gamePanel.tileSize, gamePanel.tileSize, null);

                    // Level 1
                    if(level1TileNum[row][col] != -1) {
                        g2.drawImage(tiles[level1TileNum[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null); 
                    }

                    // Level 2
                    if(level2TileNum[row][col] != -1) {
                        g2.drawImage(tiles[level2TileNum[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null); 
                    }

                    // Props
                    if(propTileNum[row][col] != -1) {
                        g2.drawImage(tiles[propTileNum[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null); 
                    }

                    // Objects
                    if(objTileNum[row][col] != -1) {
                        g2.drawImage(objects[objTileNum[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null); 
                    }
                }
            }
        }
    }

    private boolean tileOnScreen(int worldX, int worldY) {

        // Accounts for the deviation of the player position in the screen from it's default position
        int screenXDelta = gamePanel.player.defaultScreenX - gamePanel.player.screenX;
        int screenYDelta = gamePanel.player.defaultScreenY - gamePanel.player.screenY;

        boolean result = worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.defaultScreenX + screenXDelta &&
                         worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.defaultScreenX + screenXDelta &&
                         worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.defaultScreenY + screenYDelta &&
                         worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.defaultScreenY + screenYDelta;
        
        return result;
    }

    // Helpper method to check if the player is on an edge of the map
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
}
