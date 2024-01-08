package main.entities;

import main.interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class EntityManager implements Drawable {

    GamePanel gamePanel;
    Player player;

    public ArrayList<Entity> entities;
    protected boolean playerChangedTile = true;

    public EntityManager(GamePanel gamePanel, Player player) {

        this.gamePanel = gamePanel;
        this.player = player;

        entities = new ArrayList<>();
        setEntities();

    }

    private void setEntities() {

        // Player
        entities.add(player);

        // Enemies
        entities.add(new Enemy(gamePanel));

    }

    public void update() {

        for(Entity entity : entities) {
            entity.update();
        }

    }

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
