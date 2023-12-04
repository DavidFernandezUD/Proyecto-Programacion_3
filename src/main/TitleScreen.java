package main;

import main.interfaces.Drawable;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TitleScreen implements Drawable {

    // SETTINGS
    private final String title;

    // GAME PANEL
    public GamePanel gamePanel;

    // INDEX
    private boolean upToggled = false;
    private boolean downToggled = false;
    private boolean leftToggled = false;
    private boolean rightToggled = false;
    private boolean enterToggled = false;
    private int selectionIndex = 0;
    private int selectionCol = 0;

    // STATES
    private boolean gameLoad = false;
    private boolean gameTitle = true;
    private boolean characterSelection = false;

    TitleScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        // TITLE
        title = "Shadows Of Despair";

    }

    public void update() {

        if (gameTitle) {

            // NEW GAME
            if(selectionIndex == 0 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.titleScreenOn = false;
            }

            // CONTINUE
            if (selectionIndex == 1 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gamePanel.gameManager.loadGames();
                gameLoad = true;
                gameTitle = false;
            }

            // EXIT
            if(selectionIndex == 4 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                System.exit(0);
            }

        } else if (gameLoad) {
            
            // DELETE
            if(selectionCol == 1 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gamePanel.gameManager.deleteGame(selectionIndex);
            }

            // BACK
            if(selectionCol == 2 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gameLoad = false;
                gameTitle = true;
            }
        } else if (characterSelection) {
            //TODO: Character Selection
        }

        // UP AND DOWN ARROW KEYS
        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
            upToggled = !upToggled;
            selectionIndex--;
            if(selectionIndex < 0) {
                selectionIndex = 4;
            } 
        }

        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
            downToggled = !downToggled;
            selectionIndex++;
            if(selectionIndex > 4 && gameTitle) {
                selectionIndex = 0;
            } else if (selectionIndex > gamePanel.gameManager.games.size() && gameLoad) {
                selectionIndex = gamePanel.gameManager.games.size();
            }
        }

        // LEFT AND RIGHT ARROW KEYS
        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_A) != leftToggled) {
            leftToggled = !leftToggled;
            selectionCol--;
            if(selectionCol < 0) {
                selectionCol = 2;
            }
        }
        
        if(gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_D) != rightToggled) {
            rightToggled = !rightToggled;
            selectionCol++;
            if(selectionCol > 2) {
                selectionCol = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {

        if(gameTitle) {
            
            // BACKGROUND
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // DRAWING TITLE
            g2.setFont(FontManager.titleFont);
            g2.setColor(FontManager.fontColor);
            int titleX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            int titleY = 180;
            g2.drawString(title, titleX, titleY);

            // DRAWING NEW GAME BUTTON
            g2.setFont(FontManager.optionFont);
            int startX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("NEW GAME")) / 2;
            int startY = 320;
            g2.setColor(selectionIndex == 0 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("NEW GAME", startX, startY);

            // DRAWING CONTINUE BUTTON
            g2.setFont(FontManager.optionFont);
            int continueX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("CONTINUE GAME")) / 2;
            int continueY = 360;
            g2.setColor(selectionIndex == 1 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("CONTINUE GAME", continueX, continueY);

            // DRAWING OPTIONS BUTTON
            g2.setFont(FontManager.optionFont);
            int optionsX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("OPTIONS")) / 2;
            int optionsY = 400;
            g2.setColor(selectionIndex == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("OPTIONS", optionsX, optionsY);

            // DRAWING STATISTICS BUTTON
            g2.setFont(FontManager.optionFont);
            int statisticsX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("STATISTICS")) / 2;
            int statisticsY = 440;
            g2.setColor(selectionIndex == 3 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("STATISTICS", statisticsX, statisticsY);

            // DRAWING EXIT BUTTON
            g2.setFont(FontManager.optionFont);
            int exitX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("EXIT")) / 2;
            int exitY = 480;
            g2.setColor(selectionIndex == 4 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("EXIT", exitX, exitY);
        }
        
        else if (gameLoad) {
            // BACKGROUND
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // Draw the saved games and delete options
            g2.setFont(FontManager.optionFont);
            g2.setColor(FontManager.fontColor);
            int startX = gamePanel.screenWidth / 4;
            int startY = 200;
            int deleteX = gamePanel.screenWidth / 4 * 2;
            int deleteY = startY;
            int backX = gamePanel.screenWidth / 4 * 3;
            int backY = startY;

            for (int i = 0; i < 5; i++) {
                g2.setColor(selectionIndex == i && selectionCol == 0? FontManager.highlightColor : FontManager.fontColor);
                g2.drawString(gamePanel.gameManager.games.get(i).gameName, startX, startY + i * 50);
                g2.setColor(selectionIndex == i && selectionCol == 1? FontManager.highlightColor : FontManager.fontColor);
                g2.drawString("DELETE", deleteX, deleteY + i * 50);
            }

            g2.setColor(selectionCol == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("BACK", backX, backY);
        }

        else if (characterSelection) {
            //TODO: Character Selection
        }
    }
}
