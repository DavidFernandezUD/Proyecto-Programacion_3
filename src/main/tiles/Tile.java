package main.tiles;

import main.GamePanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image;

    public Tile(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
        } catch (IOException e) {
            GamePanel.logger.log(Level.SEVERE, "Failed Loading Tile Sprite", e);
        }
    }

    public Tile(BufferedImage image) {
        this.image = image;
    }
}
