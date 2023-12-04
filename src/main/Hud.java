package main;

import java.awt.Color;
import java.awt.Graphics2D;

import main.interfaces.Drawable;

public class Hud implements Drawable{

    //GamePanel
    public GamePanel gamePanel;

    //Data
    public int health;
    public int maxHealth = 100;
    public int stamina;
    public int maxStamina = 100;

    //Progress Bar
    int progressBarWidth = 200;
    int progressBarHeight = 20;
    int progressBarX = 50;
    int progressBarY = 50;

    public Hud(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    }

    public void update() {
        health = gamePanel.player.health;
        stamina = gamePanel.player.stamina;
    }

    @Override
    public void draw(Graphics2D g2) {
        
        //Calculate percentage
        int progress = (int) ((double) health / maxHealth * 100);

        // Outline
        g2.setColor(Color.BLACK);
        g2.drawRect(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

        // Fill
        g2.setColor(Color.GREEN);
        int fillWidth = (int) (progressBarWidth * (progress / 100.0));
        g2.fillRect(progressBarX, progressBarY, fillWidth, progressBarHeight);

        // Text
        g2.setColor(Color.BLACK);
        g2.setFont(FontManager.optionFont);
        g2.drawString("Health: " + health + "/" + maxHealth, progressBarX, progressBarY - 10);
    }
}