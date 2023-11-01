package main;

import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    
    // SCREEN SETTINGS
    final int originalTileSize = 32; // 16x16 tiles
    final int scale = 2;
    public final int tileSize = originalTileSize * scale; // 64x64 tiles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 1024 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 768 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 40;
    public final int maxWorldRow = 40;
    public final int worldHeight = tileSize * maxWorldCol;
    public final int worldWidth = tileSize * maxWorldRow;

    //States
    public boolean gamePaused = false;
    public boolean titleScreen = true;
    public boolean startSelected = false;
    public boolean gameStarted = false;

    // FPS
    public int FPS = 60;

    public Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);

        // When start() is called, the thread initializes and the run method inside it is called
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000. / FPS; // Nanoseconds per frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        // The game loop will be running in the run() method
        while(gameThread != null) {

            gamePaused = keyHandler.escToggled;

            currentTime = System.nanoTime();

            delta += currentTime - lastTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= drawInterval) {

                // TITLE SCREEN LOOP

                // TODO: Fix menu selection buttons (add toggle)
                if(titleScreen  && !gameStarted && !gamePaused){
                    if (keyHandler.upPressed || keyHandler.downPressed) {
                        startSelected = !startSelected;
                        repaint();
                    } else if (keyHandler.enterPressed && startSelected) {
                        // Start the game
                        titleScreen = false;
                    }
                }

                // Only updating the game state if the game isn't paused
                if(!gamePaused && !titleScreen) {
                    // 1 UPDATE: Update information like location of items, mobs, character, etc.
                    update();
                }

                // 2 DRAW: Draw the screen with the updated information
                repaint(); // repaint() calls the paintComponent() method

                delta-= drawInterval;
                drawCount++;
            }

            // Every second it draws the FPS count
            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);

                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILES
        tileManager.draw(g2);
        
        // OBJECTS
        // TODO: Implement separate drawing method on AssetSetter

        // PLAYER
        player.draw(g2);

        // PAUSE SCREEN
        if(gamePaused) {
            g2.setColor(new Color(100, 100, 100, 150));
            g2.fillRect(0, 0, maxScreenCol * tileSize, maxScreenRow * tileSize);
        }

        // TITLE SCREEN
        if(titleScreen) {
            // Draw the title text
            String title = "SHADOWS OF DESPAIR";
            g2.setFont(new Font("Arial", Font.BOLD, 36));
            g2.setColor(Color.WHITE);
            int titleX = (getWidth() - g2.getFontMetrics().stringWidth(title)) / 2;
            int titleY = 150;
            g2.drawString(title, titleX, titleY);

            // Draw the start button
            String startText = "Start";
            g2.setFont(new Font("Arial", Font.PLAIN, 24));
            int startX = (getWidth() - g2.getFontMetrics().stringWidth(startText)) / 2;
            int startY = 320;
            g2.setColor(startSelected ? Color.YELLOW : Color.WHITE);
            g2.drawString(startText, startX, startY);
        }

        g2.dispose(); // dispose helps to free some memory after the painting has ended
    }
}
