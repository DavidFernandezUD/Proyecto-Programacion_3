package main.entities;

import main.GamePanel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Entity static method test.
 * 
 * @author david.f@opendeusto.es
 */
public class EntityTest {

    GamePanel gamePanel;
    Enemy ent1;
    Enemy ent2;

    @Before
    public void setUp() {
        gamePanel = new GamePanel();
        ent1 = new Enemy(gamePanel, 0, 0);
        ent2 = new Enemy(gamePanel, 0, 0);
    }

    @Test
    public void getVectorTest() {

        assertArrayEquals(new int[] { 0, 0 }, Entity.getVector(ent1, ent2));

        ent2.worldX = gamePanel.tileSize * 10;
        ent2.worldY = gamePanel.tileSize * 10;
        assertArrayEquals(new int[] { gamePanel.tileSize * 10, -gamePanel.tileSize * 10 },
                Entity.getVector(ent1, ent2));

        ent2.worldX = gamePanel.tileSize * 10;
        ent2.worldY = gamePanel.tileSize * -20;
        assertArrayEquals(new int[] { gamePanel.tileSize * 10, gamePanel.tileSize * 20 }, Entity.getVector(ent1, ent2));

    }

    @Test
    public void getAngleTest() {

        assertEquals(0, Entity.getAngle(ent1, ent2), 1e-10);

        ent2.worldX = 0;
        ent2.worldY = gamePanel.tileSize * 10;
        assertEquals(180, Entity.getAngle(ent1, ent2), 1e-10);

        ent2.worldX = gamePanel.tileSize * 10;
        ent2.worldY = 0;
        assertEquals(90, Entity.getAngle(ent1, ent2), 1e-10);

        ent2.worldX = gamePanel.tileSize * -10;
        ent2.worldY = 0;
        assertEquals(-90, Entity.getAngle(ent1, ent2), 1e-10);

        ent2.worldX = -1;
        ent2.worldY = gamePanel.tileSize * 10;
        assertEquals(-180, Entity.getAngle(ent1, ent2), 0.1);

    }

    @Test
    public void getDirectionTest() {

        assertEquals("up", Entity.getDirection(ent1, ent2));

        ent2.worldX = gamePanel.tileSize * 10;
        ent2.worldY = 0;
        assertEquals("right", Entity.getDirection(ent1, ent2));

        ent2.worldX = gamePanel.tileSize * 10;
        ent2.worldY = gamePanel.tileSize * 10 + 1;
        assertEquals("down", Entity.getDirection(ent1, ent2));

        ent2.worldX = gamePanel.tileSize * -10;
        ent2.worldY = gamePanel.tileSize * 10 - 1;
        assertEquals("left", Entity.getDirection(ent1, ent2));

    }

    @Test
    public void getDistanceTest() {

        assertEquals(0, Entity.getDistance(ent1, ent2), 1e-10);

        ent2.worldX = gamePanel.tileSize * 5;
        ent2.worldY = 0;
        assertEquals(gamePanel.tileSize * 5, Entity.getDistance(ent1, ent2), 1e-10);

        ent2.worldX = gamePanel.tileSize;
        ent2.worldY = gamePanel.tileSize;
        assertEquals(gamePanel.tileSize * Math.sqrt(2), Entity.getDistance(ent1, ent2), 1e-10);

    }

}
