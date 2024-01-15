package main;

import javax.swing.JPanel;

import main.collisions.CollisionChecker;
import main.entities.EntityManager;
import main.entities.PathFinder;
import main.entities.Player;
import main.items.ItemSetter;
import main.items.SuperItem;
import main.objects.SuperObject;
import main.tiles.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 32; // 16x16 tiles
	public final int scale = 2;
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

	// STATE SCREENS
	public TitleScreen titleScreen = new TitleScreen(this);
	public PauseScreen pauseScreen = new PauseScreen(this);
	public DialogueScreen dialogueScreen = new DialogueScreen(this);
	public InventoryScreen inventoryScreen = new InventoryScreen(this);

	// STATES
	public boolean gamePaused = false;
	public boolean titleScreenOn = true;
	public boolean escToggled = false;
	public boolean dialogueState = false;
	public boolean inventoryState = false;
	public boolean iToggled = false;

	// FPS
	public int FPS = 60;

	// OBJECTS AND ITEMS
	public SuperItem items[] = new SuperItem[10];
	public ItemSetter itemSetter = new ItemSetter(this);

	// SOUND
	Sound sound = new Sound();

	// OTHER CLASSES
	public EventHandler eventHandler = new EventHandler(this);
	public Thread gameThread;
	public TileManager tileManager = new TileManager(this);
	public KeyHandler keyHandler = new KeyHandler();
	public MouseHandler mouseHandler = new MouseHandler();
	public FontManager fontManager = new FontManager();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public PathFinder pathFinder = new PathFinder(this);
	public Hud hud = new Hud(this);
	public Player player = new Player(this, keyHandler, mouseHandler);
	public EntityManager entityManager = new EntityManager(this, player);

	// GAME MANAGER
	public Game currentGame = new Game(this);
	public GameManager gameManager = new GameManager(this, currentGame);

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);

		this.addKeyListener(keyHandler);
		this.addMouseListener(mouseHandler);
		this.setFocusable(true);
	}

	public void setUpGame() {
		// Sets items
		itemSetter.setItem();

		// Plays music
		if (titleScreenOn) {
//    		playMusic(0);
		}
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		// When start() is called, the thread initializes and the run method inside it
		// is called
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
		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += currentTime - lastTime;
			timer += currentTime - lastTime;
			lastTime = currentTime;

			if (delta >= drawInterval) {

				// Checking if the escape key has been toggled
				if (keyHandler.isKeyToggled(KeyEvent.VK_ESCAPE) != escToggled) {
					escToggled = keyHandler.isKeyToggled(KeyEvent.VK_ESCAPE);
					gamePaused = true;
				}

				if (keyHandler.isKeyToggled(KeyEvent.VK_I) != iToggled) {
					iToggled = keyHandler.isKeyToggled(KeyEvent.VK_I);
					inventoryState = true;
				}			

				// TODO: Maybe manage the title screen without update method
				if (titleScreenOn) {
					titleScreen.update();
				}

				if (gamePaused) {
					pauseScreen.update();
				}

				// DIALOGUE SCREEN
                if (dialogueState) {
                    dialogueScreen.update();
                }

				if (inventoryState) {
					inventoryScreen.update();
				}

				// Only updating the game state if the game isn't paused
				if (!gamePaused && !titleScreenOn && !dialogueState && !inventoryState) {
					// 1 UPDATE: Update information like location of items, mobs, character, etc.
					update();
					hud.update();
				}

				// 2 DRAW: Draw the screen with the updated information
				repaint(); // repaint() calls the paintComponent() method

				delta -= drawInterval;
				drawCount++;
			}

			// Every second it draws the FPS count
			if (timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);

				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {
		entityManager.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// TILES
		tileManager.draw(g2);
		
		// ITEMS
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null) {
				items[i].draw(g2, this);
			}
		}

		// Entities
		entityManager.draw(g2);

		// HUD
		hud.draw(g2);

		// PAUSE SCREEN
		if (gamePaused) {
			g2.setColor(new Color(100, 100, 100, 150));
			g2.fillRect(0, 0, maxScreenCol * tileSize, maxScreenRow * tileSize);
			pauseScreen.draw(g2);
		}

		// INVENTORY SCREEN
		if (inventoryState) {
			inventoryScreen.draw(g2);
		}

		// TITLE SCREEN
		if (titleScreenOn) {
			titleScreen.draw(g2);
		}

		// DIALOGUE SCREEN
        if(dialogueState) {
        	dialogueScreen.draw(g2); 	      	
        }

		g2.dispose(); // dispose helps to free some memory after the painting has ended
	}

	public void playMusic(int i) {

		sound.setFile(i);
		sound.setVolume(-20.0f);
		sound.play();
		sound.loop();

	}

	public void playSound(int i) {

		sound.setFile(i);
		sound.setVolume(-20.0f);
		sound.play();

	}

	public void stopMusic() {
		sound.stop();
	}

}
