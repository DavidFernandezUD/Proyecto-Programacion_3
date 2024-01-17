package main.entities;

import main.GamePanel;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

/** Pathfinder test.
 * @author david.f@opendeusto.es*/
public class PathFinderTest {

    private GamePanel gamePanel;
    private PathFinder pathFinder;
    private Player player;
    private Enemy enemy;

    @Before
    public void setUp() {
        gamePanel = new GamePanel();
        pathFinder = gamePanel.pathFinder;
        player = gamePanel.player;

        // Setting Player Position
        player.worldX = gamePanel.tileSize * 25;
		player.worldY = gamePanel.tileSize * 35;

        // Setting Enemy
        enemy = new Enemy(gamePanel,0, 0);
    }

    @Test
    public void testPathValid() {
        enemy.worldX = gamePanel.tileSize * 17;
		enemy.worldY = gamePanel.tileSize * 3;

        ArrayList<PathFinder.Node> path = pathFinder.search(player, enemy);

        // Solidity of last node isn't checked since the pathFinder doesn't consider it neither
        for(int i = 0; i < path.size() - 1; i++) {
            assertFalse(path.get(i).solid);
        }

    }

    @Test
    public void testPathDistance() {

        enemy.worldX = gamePanel.tileSize * 15;
		enemy.worldY = gamePanel.tileSize * 35;
        assertEquals(10, pathFinder.search(player, enemy).size()); // Straight line Distance

        enemy.worldX = gamePanel.tileSize * 28;
		enemy.worldY = gamePanel.tileSize * 32;
        assertEquals(6, pathFinder.search(player, enemy).size()); // Diagonal Distance

        enemy.worldX = gamePanel.tileSize * 28;
		enemy.worldY = gamePanel.tileSize * 12;
        assertEquals(32, pathFinder.search(player, enemy).size()); // Obstacles

    }

    @Test
    public void testPathExists() {

        player.worldX = gamePanel.tileSize * 19;
		player.worldY = gamePanel.tileSize * 20;

        enemy.worldX = gamePanel.tileSize * 17;
		enemy.worldY = gamePanel.tileSize * 3;
        assertNotNull(pathFinder.search(player, enemy)); // Path Exists

        enemy.worldX = gamePanel.tileSize * 2;
		enemy.worldY = gamePanel.tileSize * 2;
        assertNull(pathFinder.search(player, enemy)); // Path Doesn't Exist (Blocked)

        enemy.worldX = gamePanel.tileSize * 19;
		enemy.worldY = gamePanel.tileSize * 20;
        assertNull(pathFinder.search(player, enemy)); // Path Doesn't Exist (Already at destination)

    }

    @Test
    public void testHeuristic() {
        player.worldX = gamePanel.tileSize * 19;
		player.worldY = gamePanel.tileSize * 20;

        enemy.worldX = gamePanel.tileSize * 17;
		enemy.worldY = gamePanel.tileSize * 3;

        ArrayList<PathFinder.Node> path = pathFinder.search(player, enemy);

        double prevCost = 0;
        double currentCost;
        // Predicted cost must be lower than real cost so that A* finds the shortest path
        for(PathFinder.Node node : path) {
            currentCost = node.gCost + node.hCost;
            assertTrue(prevCost < currentCost);
        }
    }

}
