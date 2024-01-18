package main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;

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

        // New Game name
        NameGenerator nameGenerator = new NameGenerator();
        gameName = nameGenerator.getRandomName();

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
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    gamePanel.logger.log(Level.SEVERE, "Thread.sleep() Failed", e);
                }
                newGame = true;
                gameTitle = false;
            }

            // CONTINUE
            if (selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gameLoad = true;
                gameTitle = false;
            }

            // SETTINGS
            if (selectionIndex == 2 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.pauseState = true;
                gamePanel.titleState = false;
                statistics.setVisible(true);
            }

            // EXIT
            if (selectionIndex == 3 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                System.exit(0);
            }

            // Inventory cannot be opened while title screen
            if (gamePanel.titleState) {
                if (gamePanel.keyHandler.isKeyToggled(KeyEvent.VK_I)) {
                    gamePanel.keyHandler.keyToggleStates.put(KeyEvent.VK_I, false);
                }
            }

        } else if (newGame) {

            // SUBMIT
            if (selectionIndex == 1 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                gamePanel.currentGame.gameName = gameName;
                newGame = false;
                gamePanel.pauseState = false;
                gamePanel.titleState = false;
            }

            // BACK
            if (selectionIndex == 2 && gamePanel.keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    gamePanel.logger.log(Level.SEVERE, "Thread.sleep() Failed", e);
                }
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

            // DRAWING NEW GAME
            g2.setFont(FontManager.optionFont);
            int newGameX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("YOUR NAME IS:")) / 2;
            int newGameY = 320;
            g2.drawString("YOUR NAME IS:", newGameX, newGameY);
            // DRAWING NEW GAME NAME
            g2.setFont(FontManager.optionFont);
            int nameX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth(gameName)) / 2;
            int nameY = newGameY + 80;
            g2.drawString(gameName, nameX, nameY);

            // DRAWING SUBMIT BUTTON
            g2.setFont(FontManager.optionFont);
            int submitX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("SUBMIT")) / 2;
            int submitY = nameY + 80;
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

            // DRAWING STATISTICS BUTTON
            g2.setFont(FontManager.optionFont);
            int statisticsX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("STATISTICS")) / 2;
            int statisticsY = 400;
            g2.setColor(selectionIndex == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("STATISTICS", statisticsX, statisticsY);

            // DRAWING EXIT BUTTON
            g2.setFont(FontManager.optionFont);
            int exitX = (gamePanel.screenWidth - g2.getFontMetrics().stringWidth("EXIT")) / 2;
            int exitY = 480;
            g2.setColor(selectionIndex == 3 ? FontManager.highlightColor : FontManager.fontColor);
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
                g2.setColor(selectionCol == 0 ? FontManager.highlightColor : FontManager.fontColor);
                g2.drawString(entry.getValue(), startX, startY);
                g2.setColor(selectionCol == 1 ? FontManager.highlightColor : FontManager.fontColor);
                g2.drawString("DELETE", deleteX, deleteY);
                startY += 40;
                deleteY += 40;
            }

            g2.setColor(selectionCol == 2 ? FontManager.highlightColor : FontManager.fontColor);
            g2.drawString("BACK", backX, backY);
        }
    }
}
