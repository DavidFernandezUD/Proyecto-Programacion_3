package main.entities;

import main.GamePanel;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class PathFinder {

    private Node[][] map;
    private final int[][] DIRECTIONS = new int[][] {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    GamePanel gamePanel;

    public PathFinder(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadMap();
    }

    public void loadMap() {

        if(map == null) {
            map = new Node[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
        }

        int[][] collisionMap = gamePanel.collisionChecker.collisionMap;
        for(int row = 0; row < collisionMap.length; row++) {
            for(int col = 0; col < collisionMap[0].length; col++) {
                boolean solid = collisionMap[row][col] != -1;
                this.map[row][col] = new Node(row, col, solid);
            }
        }

        resetNodes();
    }

    public ArrayList<Node> search(Entity origin, Entity destination) {

        resetNodes(); // Resetting modes from last search

        int startRow = (origin.worldY + gamePanel.tileSize/2) / gamePanel.tileSize;
        int startCol = (origin.worldX + gamePanel.tileSize/2) / gamePanel.tileSize;
        int endRow = (destination.worldY + gamePanel.tileSize/2) / gamePanel.tileSize;
        int endCol = (destination.worldX + gamePanel.tileSize/2) / gamePanel.tileSize;

        Node start = map[startRow][startCol]
                .setCost(0, euclideanDistance(startRow, startCol, endRow, endCol))
                .setVisited();

        Node end = map[endRow][endCol]
                .setCost(0, 0);

        PriorityQueue<Node> queue = new PriorityQueue<>();
        queue.offer(start);

        while(!queue.isEmpty()) {
            Node current = queue.poll();
            for(Node option : getOption(current, end)) {
                if(option == end) {
                    return getPath(option, new ArrayList<>());
                } else {
                    queue.add(option);
                }
            }
        }
        return null;
    }

    private ArrayList<Node> getOption(Node current, Node end) {
        ArrayList<Node> options = new ArrayList<>();
        for(int[] direction : DIRECTIONS) {
            int destinationRow = current.row + direction[0];
            int destinationCol = current.col + direction[1];
            if(inBounds(destinationRow, destinationCol)) {
                Node newNode = map[destinationRow][destinationCol];
                if((!newNode.solid || newNode == end) && !newNode.visited) { // Doesn't check if end node is solid
                    newNode
                            .setPrev(current)
                            .setVisited()
                            .setCost(
                                    current.gCost + 1,
                                    euclideanDistance(
                                        newNode.row, newNode.col,
                                        end.row, end.col));
                    options.add(map[destinationRow][destinationCol]);
                }
            }
        }
        return options;
    }

    private boolean inBounds(int row, int col) {
        return row < map.length && row >= 0 && col < map[0].length && col >= 0;
    }

    // Manhattan Distance heuristic
    private double manhattanDistance(int aRow, int aCol, int bRow, int bCol) {
        return Math.abs(aRow - bRow) + Math.abs(aCol - bCol);
    }

    // Euclidean Distance Heuristic
    private double euclideanDistance(int aRow, int aCol, int bRow, int bCol) {
        return Math.sqrt((aRow - bRow) * (aRow - bRow) + (aCol - bCol) * (aCol - bCol));
    }

    // Returns a list with the nodes in the path to get to the given node
    // FIXME: RECURSIVE METHOD!!
    private ArrayList<Node> getPath(Node node, ArrayList<Node> path) {
        if(node.prev == null) {
            return path; // The returned path excludes the first node
        }
        path.add(0, node);
        return getPath(node.prev, path);
    }

    // Resets all nodes cost, visited status and prev node
    // TODO: Avoid resetting the hCost if the destination hasn't changed between searches
    private void resetNodes() {
        for(Node[] row : map) {
            for(Node node : row) {
                node.visited = false;
                node.hCost = Double.POSITIVE_INFINITY;
                node.gCost = 0;
                node.prev = null;
            }
        }
    }

    public class Node implements Comparable<Node> {

        int row;
        int col;
        boolean solid;
        boolean visited;
        double gCost;
        double hCost;
        Node prev;

        Node(int row, int col, boolean solid) {
            this.row = row;
            this.col = col;
            this.solid = solid;
        }

        protected Node setCost(double g, double h) {
            this.gCost = g;
            this.hCost = h;
            return this;
        }

        protected Node setVisited() {
            this.visited = true;
            return this;
        }

        protected Node setPrev(Node prev) {
            this.prev = prev;
            return this;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(gCost + hCost, o.gCost + o.hCost);
        }
    }
}
