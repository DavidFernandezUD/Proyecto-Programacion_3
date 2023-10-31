package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
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

    public ArrayList<int[][]> map;
    final int  mapLayers = 4; // Amount of layers in a map

    public TileManager(GamePanel gamePanel) {
        
        this.gamePanel = gamePanel;

        // Initialize map layers
        map = new ArrayList<int[][]>();
        for(int layer = 0; layer < mapLayers; layer++) {
            map.add(new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol]);
        }
        
        getTileSprite();

        ArrayList<String> layerPaths = new ArrayList<String>();
        layerPaths.add("../maps/Map2/Map_02_Ground.csv");
        layerPaths.add("../maps/Map2/Map_02_Level1.csv");
        layerPaths.add("../maps/Map2/Map_02_Level2.csv");
        layerPaths.add("../maps/Map2/Map_02_Props.csv");

        layerPaths.add("../maps/Map2/Map_02_Obj.csv"); // TODO: Move this to asset setter

        loadMap(layerPaths);
    }

    public void getTileSprite() {

        // LOADING TILES
        try {
            BufferedImage spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/tiles/tileSheet.png")));

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
            objects[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../res/objects/sign.png")));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method for scaling images
    // This way, the draw method doesn't have to rescale the images every time
    public void setUp(int index, BufferedImage image) {
        
        Utility util = new Utility();
        
        BufferedImage rescaledImage = util.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        this.tiles[index] = new Tile(rescaledImage);
    }

    public void loadMap(ArrayList<String> layerPaths) {

        // Loading floor map
        for(int layer = 0; layer < mapLayers; layer++) {
            try {
                InputStream is = getClass().getResourceAsStream(layerPaths.get(layer));
                assert is != null;
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                    String line = br.readLine();
                    String[] numbers = line.split(",");

                    for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                        int tileNum = Integer.parseInt(numbers[col]);
                        map.get(layer)[row][col] = tileNum;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics2D g2) {

        boolean xBlocked = playerOnEdge("X");
        boolean yBlocked = playerOnEdge("Y");

        for(int row = 0; row < gamePanel.maxWorldRow; row++) {
            for(int col = 0; col < gamePanel.maxWorldCol; col++) {

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
                
                // The tiles are only painted if they are inside the screen
                if(tileOnScreen(worldX, worldY)) {
                    for(int[][] layer : map) {
                        if (layer[row][col] != -1) {
                            g2.drawImage(tiles[layer[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null);
                        }
                    }
                }
            }
        }
    }

    private boolean tileOnScreen(int worldX, int worldY) {

        // Accounts for the deviation of the player position in the screen from its default position
        int screenXDelta = gamePanel.player.defaultScreenX - gamePanel.player.screenX;
        int screenYDelta = gamePanel.player.defaultScreenY - gamePanel.player.screenY;

        return worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.defaultScreenX + screenXDelta &&
                         worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.defaultScreenX + screenXDelta &&
                         worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.defaultScreenY + screenYDelta &&
                         worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.defaultScreenY + screenYDelta;
    }

    // Helper method to check if the player is on an edge of the map
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
