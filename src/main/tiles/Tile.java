package main.tiles;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Tile {
    
    public BufferedImage image;

    public Tile(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Tile(BufferedImage image) {
        this.image = image;
    }
}
