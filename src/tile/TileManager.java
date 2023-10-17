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
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // This will store all the possible tiles
        tile = new Tile[10];

        mapTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        
        getTileSprite();
        loadMap("..\\maps\\map2.txt");
    }

    public void getTileSprite() {
        
        /*
        tile[0] = new Tile("..\\res\\tiles\\grass.png");
        tile[1] = new Tile("..\\res\\tiles\\wall.png", true);
        tile[2] = new Tile("..\\res\\tiles\\water.png", true);
        */

        try {
            BufferedImage bigImage = ImageIO.read(getClass().getResourceAsStream("..\\res\\tiles\\FloorTiles.png"));

            tile[0] = new Tile(bigImage.getSubimage(128, 96, 32, 32));

            tile[1] = new Tile("..\\res\\tiles\\wall.png", true);
            tile[2] = new Tile("..\\res\\tiles\\water.png", true);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gamePanel.maxWorldRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for(int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
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

                g2.drawImage(tile[mapTileNum[row][col]].image, screenX, screenY,
                             gamePanel.tileSize, gamePanel.tileSize, null); 
            }
        }
    }
}
