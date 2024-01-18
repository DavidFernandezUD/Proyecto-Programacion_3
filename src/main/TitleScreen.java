package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Drawable title screen GUI component.
 * 
 * @author juanjose.restrepo@opendeusto.es
 */
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
    private boolean newGame = false;
    private boolean gameTitle = true;

    // LIST OF RECENT GAMES
    private HashMap<Integer, String> recentGames;
    private Integer[] recentGameCodes;

    // GAME NAME
    String gameName = "";

    // STATISTICS
    private Statistics statistics;

    /** Creates a title screen component. */
    TitleScreen(GamePanel gamePanel) {

        this.gamePanel = gamePanel;

        // TITLE
        title = "Shadows Of Despair";

        // STATISTICS
        statistics = new Statistics(gamePanel);

        // LOAD RECENT GAMES
        recentGames = gamePanel.gameManager.loadRecentGames();
        recentGameCodes = gamePanel.gameManager.loadRecentGameCodes();

    }

    /**
     * Updates the state of the title screen based on user keyboard
     * input.
     */
    public void update() {

        if (gameTitle) {

            // NEW GAME
            if (selectionIndex == 0 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                newGame = true;
                gameTitle = false;
            }

            // CONTINUE
            if (selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER) != enterToggled) {
                gameLoad = true;
                gameTitle = false;
            }

            // SETTINGS
            if (selectionIndex == 3 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.pauseState = true;
                gamePanel.titleState = false;
                statistics.setVisible(true);
            }

            // EXIT
            if (selectionIndex == 4 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                System.exit(0);
            }

            // Inventory cannot be opened while title screen
            if (gamePanel.titleState) {
                if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_I)) {
                    gamePanel.keyHandler.keyToggleStates.put(KeyEvent.VK_I, false);
                }
            }

        } else if (newGame) {

            if (selectionIndex == 0) {

                // GETTING GAME NAME
                if (gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
                    if (gameName.length() > 0) {
                        gameName = gameName.substring(0, gameName.length() - 1);
                    }
                } else if (gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                    selectionIndex = 1;
                } else {
                    gameName += gamePanel.keyHandler.getKeyPressed();
                }
            }

            // SUBMIT
            if (selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.currentGame.gameName = gameName;
                newGame = false;
                gamePanel.pauseState = false;
                gamePanel.titleState = false;
            }

            // BACK
            if (selectionIndex == 2 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                newGame = false;
                gameTitle = true;
            }

        } else if (gameLoad) {

            // LOAD SELECTED GAME
            if (selectionCol == 0 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gamePanel.gameManager.loadGame(recentGameCodes[selectionIndex]);
                gamePanel.currentGame = gamePanel.gameManager.currentGame;
                gamePanel.pauseState = false;
                gamePanel.titleState = false;
            }

            // DELETE
            if (selectionCol == 1 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gamePanel.gameManager.deleteGame(recentGameCodes[selectionIndex]);
            }

            // BACK
            if (selectionCol == 2 && gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_ENTER) != enterToggled) {
                enterToggled = !enterToggled;
                gameLoad = false;
                gameTitle = true;
            }
        }

        // UP AND DOWN ARROW KEYS
        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_W) != upToggled) {
            upToggled = !upToggled;
            selectionIndex--;
            if (selectionIndex < 0) {
                selectionIndex = 0;
            }
        }

        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_S) != downToggled) {
            downToggled = !downToggled;
            selectionIndex++;
            if (selectionIndex > 4 && gameTitle) {
                selectionIndex = 0;
            } else if (selectionIndex > 5 && gameLoad) {
                selectionIndex = 0;
            } else if (selectionIndex > 3 && newGame) {
                selectionIndex = 0;
            }
        }

        // LEFT AND RIGHT ARROW KEYS
        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_A) != leftToggled) {
            leftToggled = !leftToggled;
            selectionCol--;
            if (selectionCol < 0) {
                selectionCol = 2;
            }
        }

        if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_D) != rightToggled) {
            rightToggled = !rightToggled;
            selectionCol++;
            if (selectionCol > 2) {
                selectionCol = 0;
            }
        }

    }

    /**
     * Draws the title screen on a given Graphics2D object.
     * 
     * @param g2 Graphics2D object where the title screen will be drawn into.
     */
    @Override
    public void draw(Graphics2D g2) {

        if (newGame) {

            // BACKGROUND
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            // DRAWING TITLE
            g2.setFont(FontManager.titleFont);
            g2.setColor(FontManager.fontColor);
            int titleX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(title)) / 2;
            int titleY = 180;
            g2.drawString(title, titleX, titleY);

            // DRAWING NEW GAME NAME INPUT
            g2.setFont(FontManager.optionFont);
            int inputX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("Enter Game Name:")) / 2;
            int inputY = 320;
            g2.setColor(FontManager.fontColor);
            g2.drawString("Enter Game Name:", inputX, inputY);

            // DRAWING INPUT BOX
            int boxX = (gamePanel.screenWidth - 200) / 2; // Adjust the width of the input box as needed
            int boxY = inputY + 20;
            int boxWidth = 200; // Adjust the width of the input box as needed
            int boxHeight = 30; // Adjust the height of the input box as needed
            g2.setColor(Color.WHITE);
            g2.fillRect(boxX, boxY, boxWidth, boxHeight);
            g2.setColor(Color.BLACK);
            g2.drawRect(boxX, boxY, boxWidth, boxHeight);

            // PAINTING gameName INSIDE THE INPUT BOX
            g2.setColor(Color.BLACK);
            String gameName = "New game";
            int gameNameX = boxX + 5;
            int gameNameY = boxY + boxHeight - 10;
            g2.drawString(gameName, gameNameX, gameNameY);

            // DRAWING SUBMIT BUTTON
            g2.setFont(FontManager.optionFont);
            int submitX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("SUBMIT")) / 2;
            int submitY = boxY + boxHeight + 30;
            g2.setColor(selectionIndex == 1 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("SUBMIT", submitX, submitY);

            // DRAWING BACK BUTTON
            g2.setFont(FontManager.optionFont);
            int backX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("BACK")) / 2;
            int backY = submitY + 40;
            g2.setColor(selectionIndex == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("BACK", backX, backY);

        }

        if (gameTitle) {

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

            for (Entry<Integer, String> entry : recentGames.entrySet()) {
                g2.drawString(entry.getValue(), startX, startY);
                g2.drawString("DELETE", deleteX, deleteY);
                startY += 40;
                deleteY += 40;
            }

            g2.setColor(selectionCol == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("BACK", backX, backY);
        }
    }
}
