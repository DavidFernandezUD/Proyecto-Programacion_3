package main.entities.pathfinding;

import main.GamePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar {

    private final GamePanel gamePanel;
    private int[][] map;
    private final ArrayList<int[]> visited;
    private final int[][] DIRECTIONS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public AStar(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadMap();

        visited = new ArrayList<>();
    }

    public void loadMap() {
        map = gamePanel.collisionChecker.collisionMap;
    }

    public Node search(int[] start, int[] end) {

        visited.clear();
        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(new Node(start, euclideanDistance(start, end), null));

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            for(Node option : getOption(current, end)) {
                if(option.position[0] == end[0] && option.position[1] == end[1]) {
                    return option;
                } else {
                    queue.add(option);
                }
            }
        }
        return null;
    }

    private ArrayList<Node> getOption(Node current, int[] end) {
        ArrayList<Node> options = new ArrayList<>();
        for(int[] direction : DIRECTIONS) {
            int[] destination = new int[] {current.position[0] + direction[0], current.position[1] + direction[1]};
            if(destinationPossible(destination)) {
                if(map[destination[0]][destination[1]] == -1) {
                    visited.add(destination);
                    Node newNode = new Node(destination, current.distance + euclideanDistance(destination, end), current);
                    options.add(newNode);
                }
            }
        }
        return options;
    }

    private boolean destinationPossible(int[] destination) {
        int x = destination[1];
        int y = destination[0];
        if(!visited.contains(new int[] {x, y})) {
            return x < map.length && x >= 0 && y < map[0].length && y >= 0;
        }
        return false;
    }

    private double manhattanDistance(int[] a, int[] b) {
        return Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]);
    }

    private double euclideanDistance(int[] a, int[] b) {
        return Math.sqrt((a[0] - b[0]) * (a[0] - b[0]) + (a[1] - b[1]) * (a[1] - b[1]));
    }
}
