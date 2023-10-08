package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class TileManager {
    
    GamePanel gamePanel;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        // This will store all the possible tiles
        tile = new Tile[10];

        mapTileNum = new int[gamePanel.maxScreenRow][gamePanel.maxScreenCol];
        
        getTileSprite();
        loadMap("..\\maps\\map1.txt");
    }

    public void getTileSprite() {

            tile[0] = new Tile("..\\res\\tiles\\grass.png");
            tile[1] = new Tile("..\\res\\tiles\\wall.png", true);
            tile[2] = new Tile("..\\res\\tiles\\water.png", true);
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for(int row = 0; row < gamePanel.maxScreenRow; row++) {
                String line = br.readLine();
                String[] numbers = line.split(" ");
                for(int col = 0; col < gamePanel.maxScreenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[row][col] = num;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        for(int row = 0; row < gamePanel.maxScreenRow; row++) {
            for(int col = 0; col < gamePanel.maxScreenCol; col++) {
                g2.drawImage(tile[mapTileNum[row][col]].image, col * gamePanel.tileSize, row * gamePanel.tileSize,
                             gamePanel.tileSize, gamePanel.tileSize, null); 
            }
        }
    }
}
