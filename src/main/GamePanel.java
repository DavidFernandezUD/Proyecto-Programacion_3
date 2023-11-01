package main;

import javax.swing.JPanel;
import entity.Player;
import tile.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

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

    // States
    public boolean gamePaused = false;
    public boolean titleScreenOn = true;

    // FPS
    public int FPS = 60;

    public Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public TitleScreen titleScreen = new TitleScreen(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

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

            currentTime = System.nanoTime();

            delta += currentTime - lastTime;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= drawInterval) {

                gamePaused = keyHandler.isKeyToggled(KeyEvent.VK_ESCAPE);

                if(titleScreenOn) {
                    titleScreen.update();
                    if(titleScreen.startSelected && keyHandler.isKeyPressed(KeyEvent.VK_ENTER)) {
                        titleScreenOn = false;
                    }
                }

                // TODO: Fix menu selection buttons (add toggle)
                // Only updating the game state if the game isn't paused
                if(!gamePaused && !titleScreenOn) {
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
        if(titleScreenOn) {
            titleScreen.draw(g2);
        }

        g2.dispose(); // dispose helps to free some memory after the painting has ended
    }
}
