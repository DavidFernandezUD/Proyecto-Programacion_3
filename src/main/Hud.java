package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.interfaces.Drawable;

public class Hud implements Drawable{

    //GamePanel
    public GamePanel gamePanel;

    //Data
    int health = 90;
    int stamina;
    int maxStamina = 100;
    
    //Heart Image
    int heartWidth = 40;
    int heartHeight = 40;
    Image fullHeart;
    Image halfHeart;

    //Progress Bar
    int progressBarWidth = 200;
    int progressBarHeight = 20;
    int progressBarX = 50;
    int progressBarY = 50;

    public Hud(GamePanel gamePanel) {
        
        this.gamePanel = gamePanel;
        try {
            BufferedImage IO1 = ImageIO.read(new File("src/main/res/hud/heart border sh.png"));
            this.fullHeart = IO1.getScaledInstance(heartWidth, heartHeight, Image.SCALE_SMOOTH);
            BufferedImage IO2 = ImageIO.read(new File("src/main/res/hud/heart border half.png"));
            this.halfHeart = IO2.getScaledInstance(heartWidth, heartHeight, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("ERORR LOADING HEART IMAGE");
            e.printStackTrace();
        }

    }

    public void update() {
        //health = gamePanel.player.health;
        stamina = gamePanel.player.stamina;
    }

    @Override
    public void draw(Graphics2D g2) {
        for (int i = 0; i < health / 20; i++) {
            g2.drawImage(fullHeart, 50 + (i * heartWidth), 40, null);
        }
        if (health % 20 != 0) {
            g2.drawImage(halfHeart, 50 + ((health / 20) * heartWidth), 40, null);
        }
    }
}