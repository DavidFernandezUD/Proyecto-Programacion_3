package main.entities;

import main.Drawable;
import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

/** Class that manages all the entities inside the game.
 * @author david.f@opendeusto.es*/
public class EntityManager implements Drawable {

    GamePanel gamePanel;
    Player player;

    public ArrayList<Entity> entities;
    protected boolean playerChangedTile = true;

    /** Creates an EntityManager, given a gamePanel and a Player.
     * @param player Player.*/
    public EntityManager(GamePanel gamePanel, Player player) {

        this.gamePanel = gamePanel;
        this.player = player;

        entities = new ArrayList<>();
        setEntities();

    }

    /** Loads entities from file and stores player
     * and entities in an entity list.*/
    private void setEntities() {

        // Player
        entities.add(player);

        // Enemies
        try {
			InputStream is = getClass().getResourceAsStream("/main/res/entities/entities.csv");
			assert is != null;
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
			while((line = br.readLine()) != null) {
				String[] cords = line.split(",");
                entities.add(new Enemy(gamePanel,
                        Integer.parseInt(cords[0]),
                        Integer.parseInt(cords[1])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /** Updates all the entities in the entity list.*/
    public void update() {

        for(Entity entity : entities) {
            entity.update();
        }

    }

    /** Draws entities in correct order.*/
    @Override
    public void draw(Graphics2D g2) {

        entities.sort(new Comparator<>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                if (o1.worldY > o2.worldY) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        for(Entity entity : entities) {
            entity.draw(g2);
        }

    }
}
