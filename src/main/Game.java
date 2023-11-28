package main;

import java.util.ArrayList;
import main.entities.Entity;

public class Game {

    // Game data, at the moment very simple
    public String gameName;
    public int playerPositionX;
    public int playerPositionY;
    public ArrayList<Entity> gameEntities;

    public Game(String gameName, int playerPositionX, int playerPositionY) {
        this.gameName = gameName;
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
    }

    public String toString() {
        return gameName + "," + playerPositionX + "," + playerPositionY;
    }

}
