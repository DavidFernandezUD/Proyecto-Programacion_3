package main;

import main.entities.Enemy;

import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GameManager {

    public GamePanel gamePanel;
    public Game currentGame;

    public GameManager(GamePanel gamePanel, Game currentGame) {
        this.gamePanel = gamePanel;
        this.currentGame = currentGame;
    }

    public void saveGame() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/data base/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) { 
                Statement stmt = conn.createStatement(); 
                
                //SAVE GAME
                stmt.executeUpdate(
                    "INSERT INTO GAMES (NAME, DATE) VALUES ('" + currentGame.gameName + "', '" + currentGame.date + "');"
                );

                //SET GAME CODE
                ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid() AS id FROM GAMES;");
                currentGame.gameCode = rs.getInt("id");

                //SAVE PLAYER
                stmt.executeUpdate(
                    "INSERT INTO PLAYER (GAME_CODE, POSX, POSY, HEALTH, STAMINA, ITEM) VALUES ('" + currentGame.gameCode + "', '" + currentGame.player.worldX + "', '" + currentGame.player.worldY + "', '" + currentGame.player.health + "', '" + currentGame.player.stamina + "', 'SWORD');"
                );

                //SAVE ENTITIES
                for (int i = 0; i < currentGame.entities.size(); i++) {
                    stmt.executeUpdate(
                        "INSERT INTO ENTITIES (GAME_CODE, TYPE, POSX, POSY) VALUES ('" + currentGame.gameCode + "', 'ENEMY', '" + currentGame.entities.get(i).worldX + "', '" + currentGame.entities.get(i).worldY + "');"
                    );
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        }
    }

    public void loadGame(Integer code) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/data base/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) { 
                Statement stmt = conn.createStatement(); 
                
                //LOAD GAME
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES WHERE CODE = '" + code + "';");
                currentGame.gameCode = rs.getInt("CODE");
                currentGame.gameName = rs.getString("NAME");
                currentGame.date = rs.getString("DATE");

                //LOAD PLAYER
                rs = stmt.executeQuery("SELECT * FROM PLAYER WHERE GAME_CODE = '" + currentGame.gameCode + "';");
                currentGame.player.worldX = rs.getInt("POSX");
                currentGame.player.worldY = rs.getInt("POSY");
                currentGame.player.health = rs.getInt("HEALTH");
                currentGame.player.stamina = rs.getInt("STAMINA");
                //currentGame.player.item = rs.getString("ITEM");

                //LOAD ENTITIES
                rs = stmt.executeQuery("SELECT * FROM ENTITIES WHERE GAME_CODE = '" + currentGame.gameCode + "';");
                while (rs.next()) {
                    Enemy enemy = new Enemy(gamePanel);
                    enemy.worldX = rs.getInt("POSX");
                    enemy.worldY = rs.getInt("POSY");
                    currentGame.entities.add(enemy);
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        }
    }

    public void deleteGame(Integer code) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/data base/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) { 
                Statement stmt = conn.createStatement(); 
                
                //DELETE GAME
                stmt.executeUpdate("DELETE FROM GAMES WHERE CODE = '" + code + "';");

                //DELETE PLAYER
                stmt.executeUpdate("DELETE FROM PLAYER WHERE GAME_CODE = '" + code + "';");

                //DELETE ENTITIES
                stmt.executeUpdate("DELETE FROM ENTITIES WHERE GAME_CODE = '" + code + "';");

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        }
    }

    public HashMap<Integer, String> loadRecentGames() {
        
        HashMap<Integer, String> recentGames = new HashMap<>();
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:src/data base/DB.db";

            try (Connection conn = DriverManager.getConnection(url)) { 
                Statement stmt = conn.createStatement(); 
                
                //LOAD RECENT GAMES
                ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES ORDER BY DATE DESC LIMIT 5;");
                while (rs.next()) {
                    recentGames.put(rs.getInt("CODE") , rs.getString("NAME"));
                }

                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite driver not found: " + e.getMessage());
        }
        return recentGames;
    }
}
