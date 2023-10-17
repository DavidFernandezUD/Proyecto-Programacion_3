package tile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image;
    public boolean collision = false;

    public Tile(String filePath) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tile(String filePath, boolean collision) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.collision = collision;
    }

    public Tile(BufferedImage image) {
        this.image = image;
    }

    public Tile(BufferedImage image, boolean collision) {
        
        this.image = image;
        this.collision = collision;
    }
}
