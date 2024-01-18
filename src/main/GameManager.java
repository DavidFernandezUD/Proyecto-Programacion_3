package main;

import main.entities.Enemy;
import main.items.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Class to manage game files in a database
 * 
 * @author juanjose.restrepo@opendeusto.es
 */
public class GameManager {

    public GamePanel gamePanel;
    public Game currentGame;

    /**
     * Creates a game manager given the current game state.
     * 
     * @param currentGame Current state of the game.
     */
    public GameManager(GamePanel gamePanel, Game currentGame) {
        this.gamePanel = gamePanel;
        this.currentGame = currentGame;
    }

    /** Saves the state of the game in a database. */
    public void saveGame() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // SAVE GAME
                stmt.executeUpdate(
                        "INSERT INTO GAMES (NAME, DATE) VALUES ('" + currentGame.gameName + "', '" + currentGame.date
                                + "');");

                // SET GAME CODE
                ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() AS id FROM GAMES;");
                currentGame.gameCode = rs.getInt("id");

                // SAVE PLAYER
                stmt.executeUpdate(
                        "INSERT INTO PLAYER (GAME_CODE, POSX, POSY, HEALTH, STAMINA, ITEM) VALUES ('"
                                + currentGame.gameCode + "', '" + currentGame.player.worldX + "', '"
                                + currentGame.player.worldY + "', '" + currentGame.player.health + "', '"
                                + currentGame.player.stamina + "', 'SWORD');");

                // SAVE ENTITIES
                for (int i = 0; i < currentGame.entities.size(); i++) {
                    stmt.executeUpdate(
                            "INSERT INTO ENTITIES (GAME_CODE, TYPE, POSX, POSY) VALUES ('" + currentGame.gameCode
                                    + "', 'ENEMY', '" + currentGame.entities.get(i).worldX + "', '"
                                    + currentGame.entities.get(i).worldY + "');");
                }

                // SAVE PLAYER INVENTORY
                for (SuperItem item : currentGame.player.inventory) {
                    stmt.executeUpdate(
                            "INSERT INTO ITEMS (GAME_CODE, TYPE, POSX, POSY) VALUES ('" + currentGame.gameCode
                                    + "', '" + item.name + "', 0, 0);");
                }

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Game saved");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
    }

    /**
     * Loads a previously stored game, with a given
     * name code from a database.
     * 
     * @param code Code of the game to be loaded.
     */
    public void loadGame(Integer code) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // LOAD GAME
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES WHERE CODE = '" + code + "';");
                currentGame.gameCode = rs.getInt("CODE");
                currentGame.gameName = rs.getString("NAME");
                currentGame.date = rs.getString("DATE");

                // LOAD PLAYER
                rs = stmt.executeQuery("SELECT * FROM PLAYER WHERE GAME_CODE = '" + code + "';");
                currentGame.player.worldX = rs.getInt("POSX");
                currentGame.player.worldY = rs.getInt("POSY");
                currentGame.player.health = rs.getInt("HEALTH");
                currentGame.player.stamina = rs.getInt("STAMINA");
                // currentGame.player.item = rs.getString("ITEM");

                // LOAD ENTITIES
                rs = stmt.executeQuery("SELECT * FROM ENTITIES WHERE GAME_CODE = '" + code + "';");
                while (rs.next()) {
                    Enemy enemy = new Enemy(gamePanel, -666, -666); // This must be updated
                    enemy.worldX = rs.getInt("POSX");
                    enemy.worldY = rs.getInt("POSY");
                    currentGame.entities.add(enemy);
                }

                // LOAD PLAYER INVENTORY
                rs = stmt.executeQuery("SELECT * FROM ITEMS WHERE GAME_CODE = '" + code + "';");
                while (rs.next()) {
                    switch (rs.getString("TYPE")) {
                        case "Apple":
                            ITEM_apple apple = new ITEM_apple();
                            currentGame.player.inventory.add(apple);
                        case "Bloody Sword":
                            ITEM_bloodySword bloodySword = new ITEM_bloodySword();
                            currentGame.player.inventory.add(bloodySword);
                        case "Bow":
                            ITEM_bow bow = new ITEM_bow();
                            currentGame.player.inventory.add(bow);
                        case "Golden Sword":
                            ITEM_goldenSword goldenSword = new ITEM_goldenSword();
                            currentGame.player.inventory.add(goldenSword);
                        case "Iron Sword":
                            ITEM_ironSword ironSword = new ITEM_ironSword();
                            currentGame.player.inventory.add(ironSword);
                        case "Purple Potion":
                            ITEM_purplePotion purplePotion = new ITEM_purplePotion();
                            currentGame.player.inventory.add(purplePotion);
                        case "Red Potion":
                            ITEM_redPotion redPotion = new ITEM_redPotion();
                            currentGame.player.inventory.add(redPotion);
                        case "Shield":
                            ITEM_shield shield = new ITEM_shield();
                            currentGame.player.inventory.add(shield);
                        case "Wooden Sword":
                            ITEM_woodenSword woodenSword = new ITEM_woodenSword();
                            currentGame.player.inventory.add(woodenSword);
                        default:
                            break;
                    }
                }

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Game Loaded");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
    }

    /**
     * Deletes a game with a given code from a database.
     * 
     * @param code Code of the game to be deleted.
     */
    public void deleteGame(Integer code) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // DELETE GAME (ON DELETE CASCADE)
                stmt.executeUpdate("DELETE FROM GAMES WHERE CODE = '" + code + "';");

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Game deleted");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
    }

    /**
     * Returns a map with the codes of the most recent 5 games and their names.
     * 
     * @return a HashMap with the codes and names of the recent games.
     */
    public HashMap<Integer, String> loadRecentGames() {

        HashMap<Integer, String> recentGames = new HashMap<>();
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // LOAD RECENT GAMES
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES ORDER BY DATE DESC LIMIT 5;");
                while (rs.next()) {
                    recentGames.put(rs.getInt("CODE"), rs.getString("NAME"));
                }

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Recent Games Loaded");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
        return recentGames;
    }

    /**
     * Returns an array with the codes of the most recent 5 games.
     * 
     * @return an array with the codes of the recent games.
     */
    public Integer[] loadRecentGameCodes() {
        Integer[] recentGameCodes = new Integer[5];
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // LOAD RECENT GAMES
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES ORDER BY DATE DESC LIMIT 5;");
                int i = 0;
                while (rs.next()) {
                    recentGameCodes[i] = rs.getInt("CODE");
                    i++;
                }

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Recent Games Codes Loaded");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
        return recentGameCodes;
    }

    public ArrayList<Game> loadGames() {
        ArrayList<Game> games = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/main/dataBase/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) {
                Statement stmt = conn.createStatement();

                // LOAD GAMES
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES;");
                while (rs.next()) {
                    Game game = new Game(gamePanel);
                    game.gameCode = rs.getInt("CODE");
                    game.gameName = rs.getString("NAME");
                    game.date = rs.getString("DATE");
                    games.add(game);
                }

                stmt.close();
                conn.close();
                GamePanel.logger.log(Level.INFO, "Games Loaded");

            } catch (SQLException e) {
                gamePanel.logger.log(Level.SEVERE, "SQLite connection failed: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            gamePanel.logger.log(Level.SEVERE, "SQLite driver not found: " + e.getMessage());
        }
        return games;
    }
}
