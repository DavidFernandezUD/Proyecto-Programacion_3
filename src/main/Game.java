package main;

import java.util.ArrayList;

import main.entities.Entity;
import main.entities.Player;

/** Class that stores the state of a game in a given time.
 * @author juanjose.restrepo@opendeusto.es */
public class Game {

    // Game panel
    public GamePanel gamePanel;

    // Game data
    public Integer gameCode;
    public String gameName;
    public String date;
    public Player player;
    public ArrayList<Entity> entities;

    /** Creates a Game object from a GamePanel object.
     * @param gamePanel The state of the game is extracted from the gamePanel.*/
    public Game(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gameName = "Game";
        this.date = java.time.LocalDate.now().toString();
        this.player = gamePanel.player;
        this.entities = gamePanel.entityManager.entities;
    }
}
