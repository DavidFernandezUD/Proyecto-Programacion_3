package main;

import main.interfaces.Drawable;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TitleScreen implements Drawable {

    // SETTINGS
    private final String title;
    private final String startText;
    private final Font titleFont;
    private final Font optionFont;
    private final Color fontColor;
    private final Color highlightColor;

    // OPTIONS
    public boolean startSelected;

    public GamePanel gamePanel;


    TitleScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
        startSelected = false;

        // TITLE
        title = "SHADOWS OF DESPAIR";

        // OPTIONS
        startText = "Start";

        // FONTS
        titleFont = new Font("Arial", Font.BOLD, 36);
        optionFont = new Font("Arial", Font.PLAIN, 24);

        // COLORS
        fontColor = Color.WHITE;
        highlightColor = Color.YELLOW;
    }

    public void update() {
        // System.out.println(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W));
        startSelected = gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W);
    }

    @Override
    public void draw(Graphics2D g2) {

        // DRAWING TITLE
        g2.setFont(titleFont);
        g2.setColor(fontColor);
        int titleX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(title)) / 2;
        int titleY = 150;
        g2.drawString(title, titleX, titleY);

        // DRAWING START BUTTON
        g2.setFont(optionFont);
        int startX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(startText)) / 2;
        int startY = 320;
        g2.setColor(startSelected ? highlightColor : fontColor);
        g2.drawString(startText, startX, startY);
    }
}
