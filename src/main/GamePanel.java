package main;

import javax.swing.JPanel;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
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

    // FPS
    public int FPS = 60;

    public Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this, keyHandler);
    public TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        // keyHandler = new KeyHandler();
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);

        // When start() is called, the thread initializes and the run method inside it is called
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // Nanoseconds per frame
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        // The game loop will be running in the run() method
        while(gameThread != null) {

            if(keyHandler.escToggled) {
                gamePaused = true;
            } else {
                gamePaused = false;
            }

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if(delta >= 1) {
                
                // Only updating the game state if the game isn't paused
                if(!gamePaused) {
                    // 1 UPDATE: Update information like location of items, mobs, character, etc.
                    update();
                }

                // 2 DRAW: Draw the screen with the updated infomation
                repaint(); // repaint() calls the paintComponent() method

                delta--;
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
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // PLAYER
        player.draw(g2);

        // PAUSE SCREEN
        if(gamePaused) {
            g2.setColor(new Color(100, 100, 100, 150));
            g2.fillRect(0, 0, maxScreenCol * tileSize, maxScreenRow * tileSize);
        }

        g2.dispose(); // dispose helps to free some memory after the painting has ended
    }
}
