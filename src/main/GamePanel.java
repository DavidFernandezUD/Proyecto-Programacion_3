package main;

import javax.swing.JPanel;

import main.assets.ASSET_Chest;
import main.assets.AssetSetter;
import main.assets.SuperAsset;
import main.collisions.CollisionChecker;
import main.entities.EntityManager;
import main.entities.PathFinder;
import main.entities.Player;
import main.items.ItemSetter;
import main.items.SuperItem;
import main.tiles.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

/**
 * Main class of the game. Contains all the manager classes
 * and is usually passed as a parameter to the manager classes
 * in order to allow interactions between them. The game loop thread
 * runs in this class.
 * 
 * @author david.f@opendeusto.es
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {

	// LOGGER
	public static Logger logger = Logger.getLogger(GamePanel.class.getName());

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

	// NAME GENERATOR
	public NameGenerator nameGenerator = new NameGenerator();

	// STATE SCREENS
	public TitleScreen titleScreen = new TitleScreen(this);
	public PauseScreen pauseScreen = new PauseScreen(this);
	public DialogueScreen dialogueScreen = new DialogueScreen(this);
	public InventoryScreen inventoryScreen = new InventoryScreen(this);
	public ChestScreen chestScreen = new ChestScreen(this);

	// STATES
	public boolean titleState = true;
	public boolean pauseState = false;
	public boolean escToggled = false;
	public boolean dialogueState = false;
	public boolean inventoryState = false;
	public boolean chestState = false;

	// FPS
	public int FPS = 60;

	// ASSETS AND ITEMS
	public SuperAsset assets[] = new SuperAsset[10];
	public AssetSetter assetSetter = new AssetSetter(this);
	public SuperItem items[] = new SuperItem[10];
	public ItemSetter itemSetter = new ItemSetter(this);

	// SOUND
	Sound sound = new Sound();

	// OTHER CLASSES
	public EventHandler eventHandler = new EventHandler(this);
	public Thread gameThread;
	public TileManager tileManager = new TileManager(this);
	public KeyHandler keyHandler = new KeyHandler();
	public FontManager fontManager = new FontManager();
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public PathFinder pathFinder = new PathFinder(this);
	public Hud hud = new Hud(this);
	public Player player = Player.getInstance(this, keyHandler);
	public EntityManager entityManager = new EntityManager(this, player);

	// GAME MANAGER
	public Game currentGame = new Game(this);
	public GameManager gameManager = new GameManager(this, currentGame);

	/** Creates a GamePamel. */
	public GamePanel() {

		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);

		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("src/logs/log_config.properties"));

			setLoggerLevel(logger, properties.getProperty("logger_level"));

			Handler h1 = new StreamHandler(System.out, new SimpleFormatter());
			setHandlerLevel(h1, properties.getProperty("console_logger"));
			logger.addHandler(h1);

			Handler h2 = new FileHandler("src/logs/program_log.log.xml");
			setHandlerLevel(h2, properties.getProperty("file_logger"));
			logger.addHandler(h2);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Logger Handler Failed", e);
		}

		logger.log(Level.INFO, "Game Started");
	}

	/**
	 * Sets the level of a logger based on a string
	 * representing the level.
	 * 
	 * @param logger Logger to modify.
	 * @param level  String representing desired level ("ALL", "WARNING",...)
	 */
	private void setLoggerLevel(Logger logger, String level) {
		switch (level) {
			case "ALL":
				logger.setLevel(Level.ALL);
				break;
			case "FINEST":
				logger.setLevel(Level.FINEST);
				break;
			case "FINER":
				logger.setLevel(Level.FINER);
				break;
			case "FINE":
				logger.setLevel(Level.FINE);
				break;
			case "INFO":
				logger.setLevel(Level.INFO);
				break;
			case "WARNING":
				logger.setLevel(Level.WARNING);
				break;
			case "SEVERE":
				logger.setLevel(Level.SEVERE);
				break;
		}
	}

	/**
	 * Sets the level of a logghandler based on
	 * a string representing the level.
	 * 
	 * @param handler Handler to modify.
	 * @param level   String representing desired level ("ALL", "WARNING",...)
	 */
	private void setHandlerLevel(Handler handler, String level) {
		switch (level) {
			case "ALL":
				handler.setLevel(Level.ALL);
				break;
			case "FINEST":
				handler.setLevel(Level.FINEST);
				break;
			case "FINER":
				handler.setLevel(Level.FINER);
				break;
			case "FINE":
				handler.setLevel(Level.FINE);
				break;
			case "INFO":
				handler.setLevel(Level.INFO);
				break;
			case "WARNING":
				handler.setLevel(Level.WARNING);
				break;
			case "SEVERE":
				handler.setLevel(Level.SEVERE);
				break;
		}
	}

	/**
	 * Initializes main.assets, main.items and the music of the game.
	 * 
	 * @author marcos.martinez@opendeusto.es
	 */
	public void setUpGame() {
		// Sets main.assets
		assetSetter.setAssets();
		// Sets main.items
		itemSetter.setItem();

		// Plays music
		if (titleState) {
			// playMusic(0);
		}
	}

	/** Creates and starts the game thread. */
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();

		logger.log(Level.INFO, "Game Thread Started");
	}

	/**
	 * Runs the game loop of the game. The game loop constantly
	 * repeats the updating and drawing of all the components of
	 * the game, including main.entities, tiles, objects, etc.
	 */
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
					pauseState = true;
				}

				// ASSETS & DIALOGUE SCREEN
				if (keyHandler.isKeyToggled(KeyEvent.VK_ENTER)) {
					if (!player.playerReading && !inventoryState) {
						keyHandler.keyToggleStates.put(KeyEvent.VK_ENTER, false);
					} else {
						SuperAsset supA = null;
						for (SuperAsset sa : assets) {
							if (sa != null) {
								if (collisionChecker.isPlayerAbleToRead(player, sa)) {
									supA = sa;
									break;
								}
							}
						}
						if (supA != null) {
							if (supA instanceof ASSET_Chest) {
								chestState = true;
							} else {
								dialogueState = true;
							}
						}
					}
				}

				if (dialogueState) {
					dialogueScreen.update();
				}
				if (chestState) {
					chestScreen.update();
				}

				// INVENTORY
				if (keyHandler.isKeyToggled(KeyEvent.VK_I)) {
					inventoryState = true;
				}
				if (inventoryState) {
					inventoryScreen.update();
				}

				// TODO: Maybe manage the title screen without update method
				if (titleState) {
					titleScreen.update();
				}

				if (pauseState) {
					pauseScreen.update();
				}

				// Only updating the game state if the game isn't paused
				if (!pauseState && !titleState && !dialogueState && !inventoryState && !chestState) {
					// 1 UPDATE: Update information like location of main.items, mobs, character,
					// etc.
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

	/** Updates the state of the different managers. */
	public void update() {
		entityManager.update();
	}

	/**
	 * Calls the draw method in all Drawable components in the game.
	 * 
	 * @param g a Graphisc object where the game screen will be drawn into.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// TILES
		tileManager.draw(g2);

		// ASSETS
		for (int i = 0; i < assets.length; i++) {
			if (assets[i] != null) {
				assets[i].draw(g2, this);
			}
		}

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
		if (pauseState) {
			g2.setColor(new Color(100, 100, 100, 150));
			g2.fillRect(0, 0, maxScreenCol * tileSize, maxScreenRow * tileSize);
			pauseScreen.draw(g2);
		}

		// DIALOGUE SCREEN
		if (dialogueState) {
			dialogueScreen.draw(g2);
		}

		// INVENTORY SCREEN
		if (inventoryState) {
			inventoryScreen.draw(g2);
		}

		// CHEST SCREEN
		if (chestState) {
			chestScreen.draw(g2);
		}

		// TITLE SCREEN
		if (titleState) {
			titleScreen.draw(g2);
		}

		g2.dispose(); // dispose helps to free some memory after the painting has ended
	}

	/**
	 * Plays the music of the game and loops it.
	 * 
	 * @param i Index that selects the audio file to play.
	 * @author marcos.martinez@opendeusto.es
	 */
	public void playMusic(int i) {

		sound.setFile(i);
		sound.setVolume(-20.0f);
		sound.play();
		sound.loop();

		logger.log(Level.INFO, "Playing Music");

	}

	/**
	 * Plays a sound stored in the sound module.
	 * 
	 * @param i Index that selects the audio file to play.
	 * @author marcos.martinez@opendeusto.es
	 */
	public void playSound(int i) {

		sound.setFile(i);
		sound.setVolume(-20.0f);
		sound.play();

	}

	/**
	 * Stops the sounds being played.
	 * 
	 * @author marcos.martinez@opendeusto.es
	 */
	public void stopMusic() {
		sound.stop();
	}

}
