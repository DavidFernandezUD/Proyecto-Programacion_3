package main;

import java.util.ArrayList;

import main.entities.Entity;
import main.entities.Player;

public class Game {

    // Game panel
    public GamePanel gamePanel;

    // Game data
    public Integer gameCode;
    public String gameName;
    public String date;
    public Player player;
    public ArrayList<Entity> entities;

    public Game(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gameName = "Game";
        this.date = java.time.LocalDate.now().toString();
        this.player = gamePanel.player;
        this.entities = gamePanel.entityManager.entities;
    }
}
