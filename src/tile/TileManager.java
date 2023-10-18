package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;

public class TileManager {
    
    GamePanel gamePanel;

    // TODO: Implement a way of creating different types of collision areas for different tiles
    // TODO: Maybe make floorTiles an static image instead of tile array for eficiency
    public Tile[] floorTile; // Floor tiles (no collisions)
    public Tile[] solidTile; // Collision tiles (placeholder name) (with collisions)

    public int[][] mapFloorTileNum; // Numeric values of the floor tiles
    public int[][] mapSolidTileNum; // Numeric values of the solid tiles

    public TileManager(GamePanel gamePanel) {
        
        this.gamePanel = gamePanel;
        
        this.mapFloorTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        this.mapSolidTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        
        getTileSprite();
        loadMap("..\\maps\\test_map_01_Grass.txt", "..\\maps\\test_map_01_Solid.txt");
    }

    public void getTileSprite() {

        // LOADING FLOOR TILES
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("..\\res\\tiles\\FloorTiles.png"));

            // Dimension of the tile-sheet in tiles
            int rows = 8;
            int cols = 8;

            // Size of the individual tiles in the sprite-sheet
            int spriteSize = 32; 

            // This will store all the possible tiles
            this.floorTile = new Tile[rows * cols];

            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    BufferedImage tileImage = spriteSheet.getSubimage(j * spriteSize, i * spriteSize, spriteSize, spriteSize);
                    this.floorTile[(i * cols) + j] = new Tile(tileImage);
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        // LOADING SOLID TILES
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("..\\res\\tiles\\SolidTiles.png"));

            // Dimension of the tile-sheet in tiles
            int rows = 16;
            int cols = 16;

            // Size of the individual tiles in the sprite-sheet
            int spriteSize = 32; 

            // This will store all the possible tiles
            this.solidTile = new Tile[rows * cols];

            for(int i = 0; i < rows; i++) {
                for(int j = 0; j < cols; j++) {
                    BufferedImage tileImage = spriteSheet.getSubimage(j * spriteSize, i * spriteSize, spriteSize, spriteSize);
                    this.solidTile[(i * cols) + j] = new Tile(tileImage);
                }
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String floorFilePath, String solidFilePath) {

        // Loading floor map
        try {
            InputStream is = getClass().getResourceAsStream(floorFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(",");
                for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);

                    mapFloorTileNum[row][col] = num;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Loding solid map
        try {
            InputStream is = getClass().getResourceAsStream(solidFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(",");
                for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);

                    mapSolidTileNum[row][col] = num;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        for(int row = 0; row < gamePanel.maxWorldRow; row++) {
            for(int col = 0; col < gamePanel.maxWorldCol; col++) {

                int worldX = col * gamePanel.tileSize;
                int worldY = row * gamePanel.tileSize;
                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                // The tiles are only painted if they are inside the screen
                if(screenX > -gamePanel.tileSize && screenX < gamePanel.screenWidth && screenY > -gamePanel.tileSize && screenY < gamePanel.screenHeight) {

                    // First drawing floor tiles
                    g2.drawImage(floorTile[mapFloorTileNum[row][col]].image, screenX, screenY,
                                gamePanel.tileSize, gamePanel.tileSize, null);

                    // Then we draw the solid tiles on top (only if it exists "not -1")
                    if(mapSolidTileNum[row][col] != -1) {
                        g2.drawImage(solidTile[mapSolidTileNum[row][col]].image, screenX, screenY,
                                    gamePanel.tileSize, gamePanel.tileSize, null); 
                    }
                }
            }
        }
    }
}
