package entity;

import interfaces.Drawable;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager implements Drawable {

    GamePanel gamePanel;
    Player player;

    private final ArrayList<Entity> entities;

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

        for(Entity entity : entities) {
            entity.draw(g2);
        }

    }
}
