package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;

/** Drawable HUD GUI component.
 * @author juanjose.restrepo@opendeusto.es*/
public class Hud implements Drawable{

    //GamePanel
    public GamePanel gamePanel;

    //Data
    int health;
    int stamina;
    
    //Heart Image
    int heartWidth = 40;
    int heartHeight = 40;
    Image fullHeart;
    Image halfHeart;

    //Progress Bar
    int progressBarWidth = 180;
    int progressBarHeight = 20;
    int progressBarX = 50;
    int progressBarY = 100;

    /** Creates a HUD component.*/
    public Hud(GamePanel gamePanel) {
        
        // Load Heart Images
        this.gamePanel = gamePanel;
        try {
            BufferedImage IO1 = ImageIO.read(new File("src/main/res/hud/heart border sh.png"));
            this.fullHeart = IO1.getScaledInstance(heartWidth, heartHeight, Image.SCALE_SMOOTH);
            BufferedImage IO2 = ImageIO.read(new File("src/main/res/hud/heart border half.png"));
            this.halfHeart = IO2.getScaledInstance(heartWidth, heartHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            gamePanel.logger.log(Level.SEVERE, "Failed Loading Heart Sprites", e);
        }

    }

    /** Updates the HUD based on players stamina and health*/
    public void update() {
        health = gamePanel.player.health;
        stamina = gamePanel.player.stamina;
    }

    /** Draws the HUD on a given Graphics2D object.
     * @param g2 Graphics2D object where the HUD will be drawn into.*/
    @Override
    public void draw(Graphics2D g2) {
        
        // Draw Health
        for (int i = 0; i < health / 20; i++) {
            g2.drawImage(fullHeart, 50 + (i * heartWidth), 40, null);
        }
        if (health % 20 != 0) {
            g2.drawImage(halfHeart, 50 + ((health / 20) * heartWidth), 40, null);
        }

        // Draw Stamina
        int remaining = 5 - stamina ;
        int x = progressBarX;
        int y = progressBarY;
        g2.setColor(Color.GREEN);
        for (int i = 0; i < 5; i++) {
            g2.fillRoundRect(x, y, progressBarWidth / 5, progressBarHeight, 10, 10);
            x += progressBarWidth / 5 + 5;
        }
        if (remaining > 0) {
            g2.fillRoundRect(x, y, (progressBarWidth / 5) * remaining / 20, progressBarHeight, 10, 10);
        }

        // Draw borders
        x = progressBarX;
        y = progressBarY;
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 5; i++) {
            g2.drawRoundRect(x, y, progressBarWidth / 5, progressBarHeight, 10, 10);
            x += progressBarWidth / 5 + 5;
        }
        if (remaining > 0) {
            g2.drawRoundRect(x, y, (progressBarWidth / 5) * remaining / 20, progressBarHeight, 10, 10);
        }
    }

}