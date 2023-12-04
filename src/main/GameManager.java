package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GameManager {

    public ArrayList<Game> games = new ArrayList<>();
    public GamePanel gamePanel;
    public Game currentGame;

    public GameManager(GamePanel gamePanel) {
        loadGames();
    }

    public void saveGame() {
        games.add(currentGame);
        saveCSV();

    }

    public void loadGames() {

        try {
            File file = new File("src/main/saves/saves.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] fields = line.split(",");

                // Process the fields
                Game g = new Game(fields[0], Integer.parseInt(fields[1]), Integer.parseInt(fields[2]));
                // Add the game to the list
                games.add(g);
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR LOADING GAMES");
            e.printStackTrace();
        }
    }
    
    public void saveCSV() {
        
        try {
            PrintWriter writer = new PrintWriter(new File("src/main/saves/saves.csv"));
            writer.println(currentGame.toString());
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("ERROR SAVING GAME");
            e.printStackTrace();
        }
    }

    public void loadGame(Integer index) {
        this.currentGame = games.get(index);
    }

    public void deleteGame(Integer index) {
        games.remove(games.get(index));
    }
}
