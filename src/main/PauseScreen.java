package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

/**
 * Drawable pause screen GUI component.
 * 
 * @author juanjose.restrepo@opendeusto.es
 */
public class PauseScreen implements Drawable {

    // GAME PANEL
    public GamePanel gamePanel;

    // INDEX
    private boolean upToggled = false;
    private boolean downToggled = false;
    private int selectionIndex = 0;

    /** Creates a PauseScreen component. */
    PauseScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;
    }

    /** Updates the state of the pause screen based on user keyboard input. */
    public void update() {

        // CONTINUE
        if (selectionIndex == 0 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            gamePanel.pauseState = false;
        }

        // SAVE GAME
        if (selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                gamePanel.logger.log(Level.SEVERE, "Thread.sleep() Failed", e);
            }
            gamePanel.gameManager.saveGame();
            gamePanel.pauseState = false;
        }

        // BACK TO TITLE
        if (selectionIndex == 2 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                gamePanel.logger.log(Level.SEVERE, "Thread.sleep() Failed", e);
            }
            gamePanel.titleScreen.resetTitle();
            gamePanel.pauseState = false;
            gamePanel.titleState = true;
        }

        // UP & DOWN KEYS
        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
            upToggled = !upToggled;
            selectionIndex--;
            if (selectionIndex < 0) {
                selectionIndex = 2;
            }
        }

        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
            downToggled = !downToggled;
            selectionIndex++;
            if (selectionIndex > 2) {
                selectionIndex = 0;
            }
        }
    }

    /**
     * Draws the pause screen in a given Graphics2D object.
     * 
     * @param g2 Graphics2D object where the pause screen will be drawn into.
     */
    @Override
    public void draw(Graphics2D g2) {

        // DRAWING BACKGROUND
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gamePanel.maxScreenCol * gamePanel.tileSize, gamePanel.maxScreenRow * gamePanel.tileSize);

        // DRAWING CONTINUE BUTTON
        g2.setFont(FontManager.optionFont);
        int startX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("NEW GAME")) / 2;
        int startY = 320;
        g2.setColor(selectionIndex == 0 ? FontManager.highlightColor : FontManager.fontColor);
        g2.drawString("CONTINUE", startX, startY);

        // DRAWING SAVE GAME BUTTON
        g2.setFont(FontManager.optionFont);
        int saveX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("SAVE GAME")) / 2;
        int saveY = 360;
        g2.setColor(selectionIndex == 1 ? FontManager.highlightColor : FontManager.fontColor);
        g2.drawString("SAVE GAME", saveX, saveY);

        // DRAWING BACK TO TITLE BUTTON
        g2.setFont(FontManager.optionFont);
        int continueX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("CONTINUE GAME")) / 2;
        int continueY = 400;
        g2.setColor(selectionIndex == 2 ? FontManager.highlightColor : FontManager.fontColor);
        g2.drawString("BACK TO TITLE", continueX, continueY);
    }
}
